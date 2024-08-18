package net.Pandarix.betterarcheology.datagen.provider.loot;

import net.Pandarix.betterarcheology.block.ModBlocks;
import net.Pandarix.betterarcheology.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider
{
    public ModBlockLootTableProvider()
    {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate()
    {
        //generate dropSelf for all Fossils
        ModBlocks.BLOCKS.getEntries().stream()
                .filter(block -> block != ModBlocks.RADIANCE_TOTEM && ModItems.isFossil(block.get()))
                .forEach(fossil -> this.dropSelf(fossil.get()));

        //GENERIC
        this.dropSelf(ModBlocks.ARCHEOLOGY_TABLE.get());
        this.dropSelf(ModBlocks.CRACKED_MUD_BRICKS.get());
        this.dropSelf(ModBlocks.CRACKED_MUD_BRICK_STAIRS.get());
        this.dropSelf(ModBlocks.CRACKED_MUD_BRICK_SLAB.get());
        this.dropSelf(ModBlocks.EVOKER_TRAP.get());
        this.dropSelf(ModBlocks.GROWTH_TOTEM.get());
        this.dropSelf(ModBlocks.RADIANCE_TOTEM.get());
        this.dropSelf(ModBlocks.VASE.get());
        this.dropSelf(ModBlocks.VASE_CREEPER.get());
        this.dropSelf(ModBlocks.VASE_GREEN.get());

        //MISC
        this.add(ModBlocks.INFESTED_MUD_BRICKS.get(), block ->
                createSilkTouchOnlyTable(ModBlocks.INFESTED_MUD_BRICKS.get()));
        this.add(ModBlocks.CHISELED_BONE_BLOCK.get(), block ->
                this.createSingleItemTableWithSilkTouch(block, Items.BONE, UniformGenerator.between(2, 3)));
        this.dropOther(ModBlocks.SUSPICIOUS_DIRT.get(), Items.AIR);
        this.dropOther(ModBlocks.SUSPICIOUS_RED_SAND.get(), Items.AIR);
        this.dropOther(ModBlocks.FOSSILIFEROUS_DIRT.get(), Items.BONE);

        //ROTTEN
        this.add(ModBlocks.ROTTEN_DOOR.get(), block ->
                this.createDoorTable(ModBlocks.ROTTEN_DOOR.get()));
        this.add(ModBlocks.ROTTEN_FENCE.get(), block ->
                this.createSingleItemTableWithSilkTouch(block, Items.STICK, ConstantValue.exactly(4)));
        this.add(ModBlocks.ROTTEN_FENCE_GATE.get(), block ->
                this.createSingleItemTableWithSilkTouch(block, Items.STICK, ConstantValue.exactly(2)));
        this.add(ModBlocks.ROTTEN_LOG.get(), block ->
                this.createSingleItemTableWithSilkTouch(block, Items.STICK, ConstantValue.exactly(8)));
        this.add(ModBlocks.ROTTEN_PLANKS.get(), block ->
                this.createSingleItemTableWithSilkTouch(block, Items.STICK, ConstantValue.exactly(2)));
        this.add(ModBlocks.ROTTEN_PRESSURE_PLATE.get(), block ->
                this.createSingleItemTableWithSilkTouch(block, Items.STICK, ConstantValue.exactly(1)));
        this.add(ModBlocks.ROTTEN_SLAB.get(), block ->
                this.createSingleItemTableWithSilkTouch(block, Items.STICK, ConstantValue.exactly(2)));
        this.add(ModBlocks.ROTTEN_STAIRS.get(), block ->
                this.createSingleItemTableWithSilkTouch(block, Items.STICK, ConstantValue.exactly(3)));
        this.add(ModBlocks.ROTTEN_TRAPDOOR.get(), block ->
                this.createSingleItemTableWithSilkTouch(block, Items.STICK, ConstantValue.exactly(4)));

        //VASES
        this.add(ModBlocks.LOOT_VASE.get(), block ->
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(ModBlocks.VASE.get()).when(HAS_SILK_TOUCH)))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootTableReference.lootTableReference(ModLootTableProvider.SUPPLY_LOOTTABLE_ID))
                                .add(LootTableReference.lootTableReference(ModLootTableProvider.TREASURE_LOOTTABLE_ID))
                                .when(HAS_NO_SILK_TOUCH))
        );

        this.add(ModBlocks.LOOT_VASE_CREEPER.get(), block ->
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(ModBlocks.VASE_CREEPER.get()).when(HAS_SILK_TOUCH)))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootTableReference.lootTableReference(ModLootTableProvider.SUPPLY_LOOTTABLE_ID))
                                .add(LootTableReference.lootTableReference(ModLootTableProvider.TREASURE_LOOTTABLE_ID))
                                .when(HAS_NO_SILK_TOUCH))
        );

        this.add(ModBlocks.LOOT_VASE_GREEN.get(), block ->
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(ModBlocks.VASE_GREEN.get()).when(HAS_SILK_TOUCH)))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootTableReference.lootTableReference(ModLootTableProvider.SUPPLY_LOOTTABLE_ID))
                                .add(LootTableReference.lootTableReference(ModLootTableProvider.GREEN_TREASURE_LOOTTABLE_ID))
                                .when(HAS_NO_SILK_TOUCH))
        );
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks()
    {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
