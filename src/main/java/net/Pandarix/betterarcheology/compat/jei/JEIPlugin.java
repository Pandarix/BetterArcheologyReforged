package net.Pandarix.betterarcheology.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.recipe.IdentifyingRecipe;
import net.Pandarix.betterarcheology.screen.IdentifyingScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin
{
    @Override
    @NotNull
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(BetterArcheology.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new IdentifyingCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<IdentifyingRecipe> identifyingRecipes = recipeManager.getAllRecipesFor(IdentifyingRecipe.Type.INSTANCE);
        registration.addRecipes(IdentifyingCategory.IDENTIFYING_RECIPE_TYPE, identifyingRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(IdentifyingScreen.class, 51, 48, 74, 16,
                IdentifyingCategory.IDENTIFYING_RECIPE_TYPE);
    }
}
