package net.Pandarix.betterarcheology.util;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModItemTags
{
    public static class Blocks {
        public static final TagKey<Block> ROTTEN_LOGS = tag("rotten_logs");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(BetterArcheology.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> BRUSHES = ItemTags.create(new ResourceLocation("c", "brushes"));

        public static final TagKey<Item> ROTTEN_LOGS = tag("rotten_logs");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(BetterArcheology.MOD_ID, name));
        }
    }
}
