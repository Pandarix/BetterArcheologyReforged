package net.Pandarix.betterarcheology.datagen.provider;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.ModBlocks;
import net.Pandarix.betterarcheology.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider
{
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, BetterArcheology.MOD_ID, existingFileHelper);
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void addTags(HolderLookup.Provider pProvider)
    {
        this.tag(ModTags.Blocks.ROTTEN_LOGS)
                .add(ModBlocks.ROTTEN_LOG.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(
                        ModBlocks.CRACKED_MUD_BRICKS.get(),
                        ModBlocks.CRACKED_MUD_BRICK_SLAB.get(),
                        ModBlocks.CRACKED_MUD_BRICK_STAIRS.get(),
                        ModBlocks.VILLAGER_FOSSIL.get(),
                        ModBlocks.VILLAGER_FOSSIL_BODY.get(),
                        ModBlocks.VILLAGER_FOSSIL_HEAD.get(),
                        ModBlocks.OCELOT_FOSSIL.get(),
                        ModBlocks.OCELOT_FOSSIL_BODY.get(),
                        ModBlocks.OCELOT_FOSSIL_HEAD.get(),
                        ModBlocks.SHEEP_FOSSIL.get(),
                        ModBlocks.SHEEP_FOSSIL_BODY.get(),
                        ModBlocks.SHEEP_FOSSIL_HEAD.get(),
                        ModBlocks.CHICKEN_FOSSIL.get(),
                        ModBlocks.CHICKEN_FOSSIL_BODY.get(),
                        ModBlocks.CHICKEN_FOSSIL_HEAD.get(),
                        ModBlocks.CREEPER_FOSSIL.get(),
                        ModBlocks.CREEPER_FOSSIL_BODY.get(),
                        ModBlocks.CREEPER_FOSSIL_HEAD.get(),
                        ModBlocks.WOLF_FOSSIL.get(),
                        ModBlocks.WOLF_FOSSIL_BODY.get(),
                        ModBlocks.WOLF_FOSSIL_HEAD.get(),
                        ModBlocks.GUARDIAN_FOSSIL.get(),
                        ModBlocks.GUARDIAN_FOSSIL_BODY.get(),
                        ModBlocks.GUARDIAN_FOSSIL_HEAD.get(),
                        ModBlocks.EVOKER_TRAP.get(),
                        ModBlocks.CHISELED_BONE_BLOCK.get(),
                        ModBlocks.RADIANCE_TOTEM.get()
                );

        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(
                        ModBlocks.ROTTEN_LOG.get(),
                        ModBlocks.ROTTEN_PLANKS.get(),
                        ModBlocks.ROTTEN_STAIRS.get(),
                        ModBlocks.ROTTEN_SLAB.get(),
                        ModBlocks.ROTTEN_FENCE.get(),
                        ModBlocks.ROTTEN_FENCE_GATE.get(),
                        ModBlocks.ROTTEN_TRAPDOOR.get(),
                        ModBlocks.ROTTEN_DOOR.get(),
                        ModBlocks.ROTTEN_PRESSURE_PLATE.get(),
                        ModBlocks.ARCHEOLOGY_TABLE.get()
                );

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.FOSSILIFEROUS_DIRT.get());
        this.tag(BlockTags.BAMBOO_PLANTABLE_ON)
                .add(ModBlocks.FOSSILIFEROUS_DIRT.get());
        this.tag(BlockTags.DIRT)
                .add(ModBlocks.FOSSILIFEROUS_DIRT.get());

        this.tag(BlockTags.LUSH_GROUND_REPLACEABLE)
                .add(ModBlocks.FOSSILIFEROUS_DIRT.get());
        this.tag(BlockTags.FLOWERS)
                .add(ModBlocks.GROWTH_TOTEM.get());
        this.tag(BlockTags.SMALL_FLOWERS)
                .add(ModBlocks.GROWTH_TOTEM.get());

        this.tag(BlockTags.DOORS)
                .add(ModBlocks.ROTTEN_DOOR.get());
        this.tag(BlockTags.WOODEN_DOORS)
                .add(ModBlocks.ROTTEN_DOOR.get());
        this.tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.ROTTEN_FENCE_GATE.get());
        this.tag(BlockTags.FENCES)
                .add(ModBlocks.ROTTEN_FENCE.get());
        this.tag(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.ROTTEN_FENCE.get());
        this.tag(BlockTags.LOGS)
                .add(ModBlocks.ROTTEN_LOG.get());
        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.ROTTEN_LOG.get());
        this.tag(BlockTags.PLANKS)
                .add(ModBlocks.ROTTEN_PLANKS.get());
        this.tag(BlockTags.PRESSURE_PLATES)
                .add(ModBlocks.ROTTEN_PRESSURE_PLATE.get());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.ROTTEN_PRESSURE_PLATE.get());
        this.tag(BlockTags.WALL_POST_OVERRIDE)
                .add(ModBlocks.ROTTEN_PRESSURE_PLATE.get());
        this.tag(BlockTags.SLABS)
                .add(ModBlocks.ROTTEN_SLAB.get());
        this.tag(BlockTags.WOODEN_SLABS)
                .add(ModBlocks.ROTTEN_SLAB.get());
        this.tag(BlockTags.STAIRS)
                .add(ModBlocks.ROTTEN_STAIRS.get());
        this.tag(BlockTags.TRAPDOORS)
                .add(ModBlocks.ROTTEN_TRAPDOOR.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.ROTTEN_TRAPDOOR.get());
        this.tag(BlockTags.UNSTABLE_BOTTOM_CENTER)
                .add(ModBlocks.ROTTEN_FENCE_GATE.get());
    }
}