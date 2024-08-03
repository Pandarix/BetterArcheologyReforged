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

        brushParent(ModItems.IRON_BRUSH);
        brushParent(ModItems.DIAMOND_BRUSH);
        brushParent(ModItems.NETHERITE_BRUSH);

        simpleBlockParent(ModBlocks.ARCHEOLOGY_TABLE);
        simpleBlockParent(ModBlocks.CHISELED_BONE_BLOCK);
        simpleBlockParent(ModBlocks.CRACKED_MUD_BRICKS);
        simpleBlockParent(ModBlocks.CRACKED_MUD_BRICK_SLAB);
        simpleBlockParent(ModBlocks.CRACKED_MUD_BRICK_STAIRS);
        simpleBlockParent(ModBlocks.EVOKER_TRAP);
        prefixedBlockParent(ModBlocks.FOSSILIFEROUS_DIRT, "fossiliferous");
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
                        + (prefix.isBlank() ?  "" : (prefix + "/"))
                        + block.getId().getPath()
                        + (prefix.equals("fossils") || prefix.equals("fossiliferous") ? "_0" : "")
                ));
    }

    private ItemModelBuilder brushParent(RegistryObject<Item> item) {
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
}
