package net.Pandarix.betterarcheology.datagen.provider;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.ModBlocks;
import net.Pandarix.betterarcheology.block.custom.FossilBaseBlock;
import net.Pandarix.betterarcheology.block.custom.FossilBaseBodyBlock;
import net.Pandarix.betterarcheology.block.custom.FossilBaseHeadBlock;
import net.Pandarix.betterarcheology.block.custom.FossilBaseWithEntityBlock;
import net.Pandarix.betterarcheology.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider
{
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper)
    {
        super(output, BetterArcheology.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        ModBlocks.BLOCKS.getEntries().stream()
                .filter(block -> block != ModBlocks.RADIANCE_TOTEM && isFossil(block.get()))
                .forEach(fossil -> prefixedBlockParent(fossil, "fossils"));

        basicItem(ModItems.ARTIFACT_SHARDS.getId());
        basicItem(ModBlocks.GROWTH_TOTEM.getId());
        basicItem(ModBlocks.RADIANCE_TOTEM.getId());
        basicItem(ModItems.TORRENT_TOTEM.getId());
        basicItem(ModItems.UNIDENTIFIED_ARTIFACT.getId());
        basicItem(ModBlocks.ROTTEN_DOOR.getId());

        brushParent(ModItems.IRON_BRUSH);
        brushParent(ModItems.DIAMOND_BRUSH);
        brushParent(ModItems.NETHERITE_BRUSH);

        simpleBlockParent(ModBlocks.ARCHEOLOGY_TABLE);
        simpleBlockParent(ModBlocks.CHISELED_BONE_BLOCK);
        simpleBlockParent(ModBlocks.CRACKED_MUD_BRICKS);
        simpleBlockParent(ModBlocks.INFESTED_MUD_BRICKS);
        simpleBlockParent(ModBlocks.CRACKED_MUD_BRICK_SLAB);
        simpleBlockParent(ModBlocks.CRACKED_MUD_BRICK_STAIRS);
        simpleBlockParent(ModBlocks.EVOKER_TRAP);
        simpleBlockParent(ModBlocks.ROTTEN_LOG);
        simpleBlockParent(ModBlocks.ROTTEN_PLANKS);
        prefixedBlockParent(ModBlocks.ROTTEN_PRESSURE_PLATE, "rotten_wood");
        simpleBlockParent(ModBlocks.ROTTEN_SLAB);
        simpleBlockParent(ModBlocks.ROTTEN_STAIRS);

        prefixedBlockParent(ModBlocks.FOSSILIFEROUS_DIRT, "fossiliferous");
        prefixedBlockParent(ModBlocks.ROTTEN_FENCE_GATE, "rotten_wood");
        prefixedBlockParent(ModBlocks.SUSPICIOUS_DIRT, "suspicious");
        prefixedBlockParent(ModBlocks.SUSPICIOUS_RED_SAND, "suspicious");

        withExistingParent(ModItems.SOUL_TOTEM.getId().getPath(), mcLoc("item/handheld")).texture("layer0",
                modLoc("item/" + ModItems.SOUL_TOTEM.getId().getPath()));

        withExistingParent(ModBlocks.LOOT_VASE.getId().getPath(), modLoc("block/loot_vase_0"));
        withExistingParent(ModBlocks.VASE.getId().getPath(), modLoc("block/loot_vase_0"));
        withExistingParent(ModBlocks.LOOT_VASE_CREEPER.getId().getPath(),
                modLoc("block/loot_vase_3"));
        withExistingParent(ModBlocks.VASE_CREEPER.getId().getPath(),                 modLoc("block/loot_vase_3"));
        withExistingParent(ModBlocks.LOOT_VASE_GREEN.getId().getPath(),
                modLoc("block/loot_vase_6"));
        withExistingParent(ModBlocks.VASE_GREEN.getId().getPath(),
                modLoc("block/loot_vase_6"));
        withExistingParent(ModBlocks.ROTTEN_FENCE.getId().getPath(),
                modLoc("block/rotten_wood/rotten_fence_inventory"));
        withExistingParent(ModBlocks.ROTTEN_TRAPDOOR.getId().getPath(),
                modLoc("block/rotten_wood/rotten_trapdoor_bottom"));
    }

    private ItemModelBuilder simpleBlockParent(RegistryObject<Block> block)
    {
        return prefixedBlockParent(block, "");
    }

    private ItemModelBuilder prefixedBlockParent(RegistryObject<Block> block, String prefix)
    {
        return withExistingParent(block.getId().getPath(),
                modLoc(
                        "block/"
                                + (prefix.isBlank() ? "" : (prefix + "/"))
                                + block.getId().getPath()
                                + (hasMultipleVariants(prefix) ? "_0" : "")
        ));
    }

    private ItemModelBuilder brushParent(RegistryObject<Item> item)
    {
        return withExistingParent(item.getId().getPath(),
                mcLoc("item/brush")).texture("layer0",
                modLoc("item/brushes/" + item.getId().getPath()));
    }

    private static boolean isFossil(Block block)
    {
        return block instanceof FossilBaseBodyBlock
                || block instanceof FossilBaseWithEntityBlock
                || block instanceof FossilBaseHeadBlock
                || block instanceof FossilBaseBlock;
    }

    private static boolean hasMultipleVariants(String path)
    {
        return path.equals("fossils") || path.equals("fossiliferous") || path.equals("suspicious");
    }
}