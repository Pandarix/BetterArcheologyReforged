package net.Pandarix.betterarcheology.item;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModItemGroup {
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "BetterArcheology" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BetterArcheology.MOD_ID);

    // Creates a creative tab with the id "BetterArcheology:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> BETTER_ARCHEOLOGY_ITEMGROUP = CREATIVE_MODE_TABS.register("betterarcheology", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.UNIDENTIFIED_ARTIFACT.get())).title(Component.translatable("itemGroup." + BetterArcheology.MOD_ID))
            .displayItems((parameters, output) -> {
                output.accept(ModItems.IRON_BRUSH.get());
            }).build());
}
