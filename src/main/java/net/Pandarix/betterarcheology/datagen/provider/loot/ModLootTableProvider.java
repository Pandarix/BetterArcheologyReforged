package net.Pandarix.betterarcheology.datagen.provider.loot;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetStewEffectFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModLootTableProvider implements LootTableSubProvider
{
    private final HolderLookup.Provider provider;

    public ModLootTableProvider(HolderLookup.Provider provider)
    {
        this.provider = provider;
    }

    public static LootTableProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> pRegistries)
    {
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(ModLootTableProvider::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)
        ), pRegistries);
    }

    //references for other loot tables
    protected static final ResourceKey<LootTable> SUPPLY_LOOTTABLE_KEY = ResourceKey.create(Registries.LOOT_TABLE, BetterArcheology.createResource("blocks/supply_loot_from_loot_vase"));
    protected static final ResourceKey<LootTable> TREASURE_LOOTTABLE_KEY = ResourceKey.create(Registries.LOOT_TABLE, BetterArcheology.createResource("blocks/treasure_loot_from_loot_vase"));
    protected static final ResourceKey<LootTable> GREEN_TREASURE_LOOTTABLE_KEY = ResourceKey.create(Registries.LOOT_TABLE, BetterArcheology.createResource("blocks/treasure_loot_from_green_loot_vase"));

    //universal shard drop rate
    protected static final LootPool.Builder SHARD_POOL_BUILDER = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
            .add(LootItem.lootTableItem(ModItems.ARTIFACT_SHARDS.get()).when(LootItemRandomChanceCondition.randomChance(.5F)));

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> pOutput)
    {
        //LOOT VASE SUPPLY LOOT-------
        pOutput.accept(SUPPLY_LOOTTABLE_KEY, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2, 3))
                        .add(LootItem.lootTableItem(Items.BONE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                        .add(LootItem.lootTableItem(Items.MELON_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                        .add(LootItem.lootTableItem(Items.PUMPKIN_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                        .add(LootItem.lootTableItem(Items.BEETROOT_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                        .add(LootItem.lootTableItem(Items.SUSPICIOUS_STEW).setWeight(7).apply(SetStewEffectFunction.stewEffect()
                                        .withEffect(MobEffects.NIGHT_VISION, UniformGenerator.between(7.0F, 10.0F))
                                        .withEffect(MobEffects.JUMP, UniformGenerator.between(7.0F, 10.0F))
                                        .withEffect(MobEffects.WEAKNESS, UniformGenerator.between(6.0F, 8.0F))
                                        .withEffect(MobEffects.BLINDNESS, UniformGenerator.between(5.0F, 7.0F))
                                        .withEffect(MobEffects.SATURATION, UniformGenerator.between(7.0F, 10.0F))
                                        .withEffect(MobEffects.DIG_SPEED, UniformGenerator.between(10.0F, 15.0F))
                                        .withEffect(MobEffects.LEVITATION, UniformGenerator.between(2.0F, 4.0F))
                                        .withEffect(MobEffects.MOVEMENT_SPEED, UniformGenerator.between(4.0F, 7.0F))
                                )
                        )
                        .add(LootItem.lootTableItem(Items.POTATO).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                        .add(LootItem.lootTableItem(Items.CARROT).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
                )
        );

        //LOOT VASE TREASURE LOOT-------
        //NORMAL
        pOutput.accept(TREASURE_LOOTTABLE_KEY, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2, 3))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8))))
                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8))))
                        .add(LootItem.lootTableItem(Items.REDSTONE).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 9))))
                        .add(LootItem.lootTableItem(Items.LAPIS_LAZULI).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 9))))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(2))
                        .add(LootItem.lootTableItem(Items.COAL).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8))))
                )
                .withPool(SHARD_POOL_BUILDER)
        );

        //GREEN
        pOutput.accept(GREEN_TREASURE_LOOTTABLE_KEY, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(2))
                        .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                        .add(LootItem.lootTableItem(Items.CLAY_BALL).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 7))))
                        .add(LootItem.lootTableItem(Items.LAPIS_LAZULI).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 9))))
                        .add(LootItem.lootTableItem(Items.QUARTZ).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(2))
                        .add(LootItem.lootTableItem(Items.RABBIT_FOOT).setWeight(2))
                )
                .withPool(SHARD_POOL_BUILDER)
        );
    }
}
