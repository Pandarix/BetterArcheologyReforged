package net.Pandarix.betterarcheology.block.entity;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.custom.RadianceTotemBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class RadianceTotemBlockEntity extends BlockEntity {
    public RadianceTotemBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RADIANCE_TOTEM.get(), pos, state);
    }

    public static void tick(Level world, BlockPos pos, BlockState state, RadianceTotemBlockEntity blockEntity) {
        if (world.getRandom().nextIntBetweenInclusive(0, 5) != 0) {return;}
        int selector = 0;
        if(state.getBlock() instanceof RadianceTotemBlock){
            selector = state.getValue(RadianceTotemBlock.SELECTOR);
        }
        //get players in bounding box of 10 blocks
        List<LivingEntity> livingEntities = world.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(pos.getCenter(), 20, 20, 20));

        if (world.getRandom().nextIntBetweenInclusive(0, 400) == 0) {
            for (LivingEntity livingEntity : livingEntities) {
                if (livingEntity instanceof Monster monster) {
                    monster.hurt(monster.damageSources().magic(), 1);
                    world.playSound(null, monster.position().x(), monster.position().y(), monster.position().z(), SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.HOSTILE, 0.5f, 0.5f);
                }
            }
            return;
        }

        //give every player in range slow-falling for 10 seconds, particles are not being displayed for ux
        for (LivingEntity livingEntity : livingEntities) {
            switch (selector) {
                case 0 -> livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0, false, false));
                case 1 -> {
                    if (livingEntity instanceof Monster) {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0, false, false));
                    }
                }
                case 2 -> {
                    if (livingEntity instanceof Animal) {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0, false, false));
                    }
                }
                case 3 -> {
                    if (livingEntity instanceof Player) {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0, false, false));
                    }
                }
            }
        }
    }
}
