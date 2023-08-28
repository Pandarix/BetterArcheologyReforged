package net.Pandarix.betterarcheology.item;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModItemGroup {
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "BetterArcheology" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BetterArcheology.MOD_ID);

    // Creates a creative tab with the id "BetterArcheology:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> BETTER_ARCHEOLOGY_ITEMGROUP = CREATIVE_MODE_TABS.register("betterarcheology", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.UNIDENTIFIED_ARTIFACT.get())).title(Component.translatable("itemGroup." + BetterArcheology.MOD_ID))
            .displayItems((parameters, output) -> {
                //ITEMS
                output.accept(ModItems.IRON_BRUSH.get());
                output.accept(ModItems.DIAMOND_BRUSH.get());
                output.accept(ModItems.ARTIFACT_SHARDS.get());
                output.accept(ModItems.UNIDENTIFIED_ARTIFACT.get());
                //BLOCKS
                output.accept(ModBlocks.ARCHEOLOGY_TABLE.get());
                    //fossils
                output.accept(ModBlocks.CREEPER_FOSSIL.get());
                output.accept(ModBlocks.CREEPER_FOSSIL_BODY.get());
                output.accept(ModBlocks.CREEPER_FOSSIL_HEAD.get());
                output.accept(ModBlocks.VILLAGER_FOSSIL.get());
                output.accept(ModBlocks.VILLAGER_FOSSIL_BODY.get());
                output.accept(ModBlocks.VILLAGER_FOSSIL_HEAD.get());
                output.accept(ModBlocks.CHICKEN_FOSSIL.get());
                output.accept(ModBlocks.CHICKEN_FOSSIL_BODY.get());
                output.accept(ModBlocks.CHICKEN_FOSSIL_HEAD.get());
                output.accept(ModBlocks.OCELOT_FOSSIL.get());
                output.accept(ModBlocks.OCELOT_FOSSIL_BODY.get());
                output.accept(ModBlocks.OCELOT_FOSSIL_HEAD.get());
                output.accept(ModBlocks.SHEEP_FOSSIL.get());
                output.accept(ModBlocks.SHEEP_FOSSIL_BODY.get());
                output.accept(ModBlocks.SHEEP_FOSSIL_HEAD.get());
                    //blocks
                output.accept(ModBlocks.SUSPICIOUS_RED_SAND.get());
                output.accept(ModBlocks.SUSPICIOUS_RED_SAND.get());
                output.accept(ModBlocks.FOSSILIFEROUS_DIRT.get());
                    //wood
                output.accept(ModBlocks.ROTTEN_LOG.get());
                output.accept(ModBlocks.ROTTEN_PLANKS.get());
                output.accept(ModBlocks.ROTTEN_STAIRS.get());
                output.accept(ModBlocks.ROTTEN_SLAB.get());
                output.accept(ModBlocks.ROTTEN_TRAPDOOR.get());
                output.accept(ModBlocks.ROTTEN_DOOR.get());
                output.accept(ModBlocks.ROTTEN_FENCE.get());
                output.accept(ModBlocks.ROTTEN_FENCE_GATE.get());
                    //bricks
                output.accept(ModBlocks.INFESTED_MUD_BRICKS.get());
                output.accept(ModBlocks.CRACKED_MUD_BRICKS.get());
                output.accept(ModBlocks.CRACKED_MUD_BRICK_STAIRS.get());
                output.accept(ModBlocks.CRACKED_MUD_BRICK_SLAB.get());
                    //vases
                output.accept(ModBlocks.VASE.get());
                output.accept(ModBlocks.VASE_CREEPER.get());
                output.accept(ModBlocks.EVOKER_TRAP.get());
            }).build());
}