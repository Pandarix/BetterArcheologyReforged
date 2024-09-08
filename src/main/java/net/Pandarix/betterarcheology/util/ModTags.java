package net.Pandarix.betterarcheology.util;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags
{
    public static class Blocks
    {
        public static final TagKey<Block> ROTTEN_LOGS = tag("rotten_logs");

        private static TagKey<Block> tag(String name)
        {
            return BlockTags.create(BetterArcheology.createResource(name));
        }
    }

    public static class Items
    {
        public static final TagKey<Item> BRUSHES = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "brushes"));

        public static final TagKey<Item> ELYTRAS = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "elytras"));

        public static final TagKey<Item> ROTTEN_LOGS = tag("rotten_logs");

        public static final TagKey<Item> TUNNELING_ITEMS = tag("tunneling_items");

        private static TagKey<Item> tag(String name)
        {
            return ItemTags.create(BetterArcheology.createResource(name));
        }
    }
}
