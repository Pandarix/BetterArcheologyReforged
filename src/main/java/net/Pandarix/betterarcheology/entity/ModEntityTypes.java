package net.Pandarix.betterarcheology.entity;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, BetterArcheology.MOD_ID);

    public static final RegistryObject<EntityType<BombEntity>> BOMB_ENTITY =
            ENTITY_TYPES.register("bombentity",
                    () -> EntityType.Builder.<BombEntity>of(BombEntity::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4).updateInterval(10)
                            .build(BetterArcheology.createResource( "bombentity").toString()));
}
