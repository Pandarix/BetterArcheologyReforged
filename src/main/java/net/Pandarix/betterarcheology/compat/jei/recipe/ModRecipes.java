package net.Pandarix.betterarcheology.compat.jei.recipe;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes
{
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BetterArcheology.MOD_ID);

    public static final RegistryObject<RecipeSerializer<IdentifyingRecipe>> IDENTIFYING_SERIALIZER =
            SERIALIZERS.register("identifying", () -> IdentifyingRecipe.Serializer.INSTANCE);
}