package net.Pandarix.betterarcheology.compat.jei.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.Pandarix.betterarcheology.enchantment.ModEnchantments;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class IdentifyingRecipe implements Recipe<SimpleContainer>
{
    private final Ingredient input;
    private final ItemStack result;
    private final CompoundTag nbt;

    public IdentifyingRecipe(Ingredient inputItems, ItemStack result, CompoundTag nbt)
    {
        this.input = inputItems;
        this.result = result;
        this.nbt = nbt;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer pContainer, Level pLevel)
    {
        if (pLevel.isClientSide())
        {
            return false;
        }

        return input.test(pContainer.getItem(0));
    }

    @Override
    @NotNull
    public NonNullList<Ingredient> getIngredients()
    {
        return NonNullList.of(Ingredient.EMPTY, input);
    }

    @NotNull
    @ParametersAreNonnullByDefault
    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess)
    {
        return this.getResultItem(pRegistryAccess);
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight)
    {
        return true;
    }

    @NotNull
    @ParametersAreNonnullByDefault
    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess)
    {
        return this.getResult();
    }

    /**
     * Extra method instead of {@link #getResultItem} for use without unnecessary parameter
     * @return ItemStack to be crafted when done
     */
    public ItemStack getResult()
    {
        //Adding the Enchantment Tags
        ItemStack modifiedResultBook = result.copy();
        modifiedResultBook.setTag(this.nbt);

        //Adding the Custom Name Tags
        CompoundTag nameModification = new CompoundTag();
        nameModification.putString("Name", "{\"translate\":\"item.betterarcheology.identified_artifact\"}");

        //Adding Chance as Lore Tag
        ListTag lore = new ListTag();
        lore.add(StringTag.valueOf(String.format("{\"text\":\"Chance: 1/%d\",\"color\":\"aqua\"}", ModEnchantments.ENCHANTMENTS.getEntries().size())));
        nameModification.put("Lore", lore);

        //output the book with the modifications
        modifiedResultBook.addTagElement("display", nameModification);
        return modifiedResultBook;
    }

    public CompoundTag getNbt()
    {
        return this.nbt;
    }

    @Override
    @NotNull
    public RecipeSerializer<?> getSerializer()
    {
        return Serializer.INSTANCE;
    }

    @Override
    @NotNull
    public RecipeType<?> getType()
    {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<IdentifyingRecipe>
    {
        public static final Type INSTANCE = new Type();
    }

    public static class Serializer implements RecipeSerializer<IdentifyingRecipe>
    {
        private static final Codec<IdentifyingRecipe> CODEC = RecordCodecBuilder.create(
                (builder) -> builder.group(
                        Ingredient.CODEC.fieldOf("input").forGetter((IdentifyingRecipe recipe) -> recipe.input),
                        ItemStack.CODEC.fieldOf("result").forGetter((IdentifyingRecipe recipe) -> recipe.result),
                        CompoundTag.CODEC.fieldOf("nbt").forGetter((IdentifyingRecipe recipe) -> recipe.nbt)
                ).apply(builder, IdentifyingRecipe::new));

        public static final Serializer INSTANCE = new Serializer();

        @Override
        public @NotNull Codec<IdentifyingRecipe> codec()
        {
            return CODEC;
        }

        @Override
        public @org.jetbrains.annotations.Nullable IdentifyingRecipe fromNetwork(@NotNull FriendlyByteBuf friendlyByteBuf)
        {
            Ingredient input = Ingredient.fromNetwork(friendlyByteBuf);
            ItemStack result = friendlyByteBuf.readItem();
            CompoundTag nbt = friendlyByteBuf.readNbt();

            return new IdentifyingRecipe(input, result, nbt);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf pBuffer, IdentifyingRecipe pRecipe)
        {
            pRecipe.getIngredients().get(0).toNetwork(pBuffer);
            pBuffer.writeItemStack(pRecipe.result, false);
            pBuffer.writeNbt(pRecipe.getNbt());
        }
    }
}