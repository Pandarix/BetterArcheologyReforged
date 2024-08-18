package net.Pandarix.betterarcheology.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.ModBlocks;
import net.Pandarix.betterarcheology.item.ModItems;
import net.Pandarix.betterarcheology.villager.ModVillagers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = BetterArcheology.MOD_ID)
public class ModEvents
{
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event)
    {
        if (event.getType() == ModVillagers.ARCHEOLOGIST.get())
        {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            //level 1
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModBlocks.ROTTEN_PLANKS.get(), 6),
                    10, 2, 0.02F));

            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 3),
                    new ItemStack(Items.BRUSH, 1),
                    4, 5, 0.02F));

            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.BONE, 16),
                    new ItemStack(Items.EMERALD, 1),
                    16, 20, 0.02F));

            //level 2
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(Blocks.MUD_BRICKS),
                    14, 5, 0.02F));

            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 3),
                    new ItemStack(Blocks.LANTERN),
                    12, 10, 0.02F));

            //level 3
            trades.get(3).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(Blocks.COBWEB, 6),
                    10, 5, 0.02F));

            trades.get(3).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 6),
                    new ItemStack(ModItems.IRON_BRUSH.get()),
                    4, 10, 0.03F));

            //level 4
            trades.get(4).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(ModBlocks.VASE_CREEPER.get(), 1),
                    8, 10, 0.025F));

            trades.get(4).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(ModBlocks.VASE.get(), 1),
                    8, 10, 0.025F));

            trades.get(4).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(ModBlocks.VASE_GREEN.get(), 1),
                    8, 10, 0.025F));

            trades.get(5).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(Items.SPYGLASS, 1),
                    8, 10, 0.02F));

            trades.get(5).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 8),
                    new ItemStack(ModItems.BOMB_ITEM.get(), 1),
                    3, 10, 0.05F));

            //level 5
            trades.get(5).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 16),
                    new ItemStack(ModItems.DIAMOND_BRUSH.get(), 1),
                    4, 10, 0.03F));

            trades.get(5).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModItems.ARTIFACT_SHARDS.get(), 1),
                    3, 30, 0.1F));

            trades.get(5).add((trader, rand) -> new VillagerTrades.TreasureMapForEmeralds(
                    13,
                    TagKey.create(Registries.STRUCTURE, new ResourceLocation(BetterArcheology.MOD_ID, "on_catacombs_explorer_map")),
                    "filled_map.catacombs",
                    MapDecoration.Type.MANSION, 12, 5).getOffer(trader, rand));

            trades.get(5).add((trader, rand) -> new VillagerTrades.TreasureMapForEmeralds(
                    13,
                    TagKey.create(Registries.STRUCTURE, new ResourceLocation(BetterArcheology.MOD_ID, "on_light_explorer_map")),
                    "filled_map.light",
                    MapDecoration.Type.MANSION, 12, 5).getOffer(trader, rand));
        }
    }
}