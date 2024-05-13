package net.Pandarix.betterarcheology.villager;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.ModBlocks;
import net.Pandarix.betterarcheology.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModVillagers
{
    //REGISTRIES--------------------------------------------------------------------//
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, BetterArcheology.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, BetterArcheology.MOD_ID);

    //ENTRIES--------------------------------------------------------------------//
    public static final RegistryObject<PoiType> ARCHEOLOGY_TABLE_POI = POI_TYPES.register("archeology_table_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.ARCHEOLOGY_TABLE.get().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final RegistryObject<VillagerProfession> ARCHEOLOGIST = VILLAGER_PROFESSIONS.register("archeologist",
            () -> new VillagerProfession("archeologist", holder -> holder.value().equals(ARCHEOLOGY_TABLE_POI.get()), holder -> holder.value().equals(ARCHEOLOGY_TABLE_POI.get()), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.BRUSH_SAND));

    //TRADES--------------------------------------------------------------------//
    public static void addCustomTrades()
    {
        VillagerTrades.ItemListing[] archeologistLvl1 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.ItemsForEmeralds(ModBlocks.ROTTEN_PLANKS.get(), 1, 6, 10, 2),
                new VillagerTrades.ItemsForEmeralds(Items.BRUSH, 3, 1, 4, 5),
                new VillagerTrades.EmeraldForItems(Items.BONE, 16, 4, 4)
        };

        VillagerTrades.ItemListing[] archeologistLvl2 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.ItemsForEmeralds(Blocks.MUD_BRICKS, 1, 1, 14, 5),
                new VillagerTrades.ItemsForEmeralds(Blocks.LANTERN, 3, 1, 12, 10),
        };

        VillagerTrades.ItemListing[] archeologistLvl3 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.ItemsForEmeralds(Blocks.COBWEB, 4, 6, 10, 10),
                new VillagerTrades.ItemsForEmeralds(ModItems.IRON_BRUSH.get(), 6, 1, 4, 10),
        };

        VillagerTrades.ItemListing[] archeologistLvl4 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.ItemsForEmeralds(ModBlocks.VASE_CREEPER.get(), 4, 1, 8, 10),
                new VillagerTrades.ItemsForEmeralds(ModBlocks.VASE_GREEN.get(), 4, 1, 8, 10),
                new VillagerTrades.ItemsForEmeralds(ModBlocks.VASE.get(), 4, 1, 8, 10),
        };

        VillagerTrades.ItemListing[] archeologistLvl5 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.ItemsForEmeralds(Items.SPYGLASS, 4, 1, 8, 10),
                new VillagerTrades.ItemsForEmeralds(ModItems.BOMB_ITEM.get(), 4, 1, 3, 10),
                new VillagerTrades.ItemsForEmeralds(ModItems.DIAMOND_BRUSH.get(), 16, 1, 4, 10),
                new VillagerTrades.ItemsForEmeralds(ModItems.ARTIFACT_SHARDS.get(), 32, 1, 3, 30),
                new VillagerTrades.TreasureMapForEmeralds(
                        13,
                        TagKey.create(Registries.STRUCTURE, new ResourceLocation(BetterArcheology.MOD_ID, "on_catacombs_explorer_map")),
                        "filled_map.catacombs",
                        MapDecoration.Type.MANSION, 12, 5),
                new VillagerTrades.TreasureMapForEmeralds(
                        13,
                        TagKey.create(Registries.STRUCTURE, new ResourceLocation(BetterArcheology.MOD_ID, "on_light_explorer_map")),
                        "filled_map.light",
                        MapDecoration.Type.MANSION, 12, 5)
        };

        VillagerTrades.TRADES.put(ARCHEOLOGIST.get(),
                new Int2ObjectOpenHashMap<VillagerTrades.ItemListing[]>(ImmutableMap.of(
                        1, archeologistLvl1, 2, archeologistLvl2, 3, archeologistLvl3, 4, archeologistLvl4, 5, archeologistLvl5
                ))
        );
    }
}
