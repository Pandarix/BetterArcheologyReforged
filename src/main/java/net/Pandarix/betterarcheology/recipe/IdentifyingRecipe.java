package net.Pandarix.betterarcheology.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.Pandarix.betterarcheology.BetterArcheology;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class IdentifyingRecipe implements Recipe<SimpleContainer>
{
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;

    public IdentifyingRecipe(NonNullList<Ingredient> inputItems, ItemStack output, ResourceLocation id)
    {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer pContainer, Level pLevel)
    {
        if (pLevel.isClientSide())
        {
            return false;
        }

        return inputItems.get(0).test(pContainer.getItem(0));
    }

    @Override
    @NotNull
    public NonNullList<Ingredient> getIngredients()
    {
        return inputItems;
    }

    @NotNull
    @ParametersAreNonnullByDefault
    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess)
    {
        return output.copy();
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
        return output.copy();
    }

    @Override
    @NotNull
    public ResourceLocation getId()
    {
        return id;
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
        public static final String ID = "identifying";
    }

    public static class Serializer implements RecipeSerializer<IdentifyingRecipe>
    {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(BetterArcheology.MOD_ID, "identifying");

        @NotNull
        @ParametersAreNonnullByDefault
        @Override
        public IdentifyingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe)
        {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++)
            {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new IdentifyingRecipe(inputs, output, pRecipeId);
        }

        @Override
        public @Nullable IdentifyingRecipe fromNetwork(@NotNull ResourceLocation pRecipeId, FriendlyByteBuf pBuffer)
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            inputs.replaceAll(ignored -> Ingredient.fromNetwork(pBuffer));

            ItemStack output = pBuffer.readItem();
            return new IdentifyingRecipe(inputs, output, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, IdentifyingRecipe pRecipe)
        {
            pBuffer.writeInt(pRecipe.inputItems.size());

            for (Ingredient ingredient : pRecipe.getIngredients())
            {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}
