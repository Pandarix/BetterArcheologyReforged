package net.Pandarix.betterarcheology.block.entity;


import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BetterArcheology.MOD_ID);

    public static final RegistryObject<BlockEntityType<ArcheologyTableBlockEntity>> ARCHEOLOGY_TABLE = BLOCK_ENTITIES.register("archeology_table", ()-> BlockEntityType.Builder.of(ArcheologyTableBlockEntity::new, ModBlocks.ARCHEOLOGY_TABLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<VillagerFossilBlockEntity>> VILLAGER_FOSSIL = BLOCK_ENTITIES.register("villager_fossil", ()-> BlockEntityType.Builder.of(VillagerFossilBlockEntity::new, ModBlocks.VILLAGER_FOSSIL.get()).build(null));

    public static final RegistryObject<BlockEntityType<ChickenFossilBlockEntity>> CHICKEN_FOSSIL = BLOCK_ENTITIES.register("chicken_fossil", ()-> BlockEntityType.Builder.of(ChickenFossilBlockEntity::new, ModBlocks.CHICKEN_FOSSIL.get()).build(null));

    public static final RegistryObject<BlockEntityType<FleeFromBlockEntity>> FLEE_FROM = BLOCK_ENTITIES.register("flee_from", ()-> BlockEntityType.Builder.of(FleeFromBlockEntity::new, ModBlocks.OCELOT_FOSSIL.get()).build(null));

    public static final RegistryObject<BlockEntityType<SkeletonFleeFromBlockEntity>> SKELETON_FLEE_FROM = BLOCK_ENTITIES.register("skeleton_flee_from", ()-> BlockEntityType.Builder.of(SkeletonFleeFromBlockEntity::new, ModBlocks.WOLF_FOSSIL.get()).build(null));

    public static final RegistryObject<BlockEntityType<SusBlockEntity>> SUSBLOCK = BLOCK_ENTITIES.register("sus_block", ()-> BlockEntityType.Builder.of(SusBlockEntity::new, ModBlocks.SUSPICIOUS_DIRT.get(), ModBlocks.SUSPICIOUS_RED_SAND.get(), ModBlocks.FOSSILIFEROUS_DIRT.get()).build(null));
}
