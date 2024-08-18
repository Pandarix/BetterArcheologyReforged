package net.Pandarix.betterarcheology.compat.jei.recipe;

import com.google.gson.JsonObject;
import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.enchantment.ModEnchantments;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class IdentifyingRecipe implements Recipe<SimpleContainer>
{
    private final Ingredient input;
    private final ItemStack result;
    private final ResourceLocation id;

    public IdentifyingRecipe(Ingredient inputItems, ItemStack result, ResourceLocation id)
    {
        this.input = inputItems;
        this.result = result;
        this.id = id;
    }

    @Override
    public @NotNull ResourceLocation getId()
    {
        return id;
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
     *
     * @return ItemStack to be crafted when done
     */
    public ItemStack getResult()
    {
        //Adding the Enchantment Tags
        ItemStack modifiedResultBook = result.copy();

        BetterArcheology.LOGGER.info("wtf ist this: " + modifiedResultBook.getTag());

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
        public static final Serializer INSTANCE = new Serializer();

        @Override
        @ParametersAreNonnullByDefault
        @NotNull
        public IdentifyingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe)
        {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input"));

            return new IdentifyingRecipe(input, output, pRecipeId);
        }

        @Override
        @ParametersAreNonnullByDefault
        public @org.jetbrains.annotations.Nullable IdentifyingRecipe fromNetwork(ResourceLocation pRecipeId, @NotNull FriendlyByteBuf friendlyByteBuf)
        {
            Ingredient input = Ingredient.fromNetwork(friendlyByteBuf);
            ItemStack result = friendlyByteBuf.readItem();

            return new IdentifyingRecipe(input, result, pRecipeId);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf pBuffer, IdentifyingRecipe pRecipe)
        {
            pRecipe.getIngredients().get(0).toNetwork(pBuffer);
            pBuffer.writeItemStack(pRecipe.result, false);
        }
    }
}