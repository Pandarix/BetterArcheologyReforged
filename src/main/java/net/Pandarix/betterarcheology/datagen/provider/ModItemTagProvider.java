package net.Pandarix.betterarcheology.datagen.provider;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider
{
    public ModItemTagProvider(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                              CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(p_275343_, p_275729_, p_275322_, BetterArcheology.MOD_ID, existingFileHelper);
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void addTags(HolderLookup.Provider pProvider)
    {
        this.tag(ItemTags.DIRT)
                .add(ModBlocks.FOSSILIFEROUS_DIRT.get().asItem());

        this.tag(ItemTags.FLOWERS)
                .add(ModBlocks.GROWTH_TOTEM.get().asItem());
        this.tag(ItemTags.SMALL_FLOWERS)
                .add(ModBlocks.GROWTH_TOTEM.get().asItem());

        this.tag(ItemTags.DOORS)
                .add(ModBlocks.ROTTEN_DOOR.get().asItem());
        this.tag(ItemTags.WOODEN_DOORS)
                .add(ModBlocks.ROTTEN_DOOR.get().asItem());
        this.tag(ItemTags.FENCE_GATES)
                .add(ModBlocks.ROTTEN_FENCE_GATE.get().asItem());
        this.tag(ItemTags.FENCES)
                .add(ModBlocks.ROTTEN_FENCE.get().asItem());
        this.tag(ItemTags.WOODEN_FENCES)
                .add(ModBlocks.ROTTEN_FENCE.get().asItem());
        this.tag(ItemTags.LOGS)
                .add(ModBlocks.ROTTEN_LOG.get().asItem());
        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.ROTTEN_LOG.get().asItem());
        this.tag(ItemTags.PLANKS)
                .add(ModBlocks.ROTTEN_PLANKS.get().asItem());
        this.tag(ItemTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.ROTTEN_PRESSURE_PLATE.get().asItem());
        this.tag(ItemTags.SLABS)
                .add(ModBlocks.ROTTEN_SLAB.get().asItem());
        this.tag(ItemTags.WOODEN_SLABS)
                .add(ModBlocks.ROTTEN_SLAB.get().asItem());
        this.tag(ItemTags.STAIRS)
                .add(ModBlocks.ROTTEN_STAIRS.get().asItem());
        this.tag(ItemTags.TRAPDOORS)
                .add(ModBlocks.ROTTEN_TRAPDOOR.get().asItem());
        this.tag(ItemTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.ROTTEN_TRAPDOOR.get().asItem());
    }
}
