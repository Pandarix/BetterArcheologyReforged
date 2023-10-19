package net.Pandarix.betterarcheology.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class GrowthTotemBlock extends FlowerBlock {
    public GrowthTotemBlock(Supplier<MobEffect> effectSupplier, int pEffectDuration, Properties pProperties) {
        super(effectSupplier, pEffectDuration, pProperties);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);
        if (pLevel.isClientSide() && pRandom.nextIntBetweenInclusive(0, 15) == 0) {
            for (int i = -5; i <= 5; i++) {
                for (int j = -5; j <= 5; j++) {
                    if (pRandom.nextIntBetweenInclusive(0, 3) == 3) {
                        Vec3 center = pPos.offset(i, 0, j).getCenter();

                        pLevel.addParticle(ParticleTypes.INSTANT_EFFECT,
                                center.x + randomDirectionModifier(pRandom, 3),
                                pPos.getY(),
                                center.z + randomDirectionModifier(pRandom, 3),
                                0, -5, 0
                        );
                    }
                }
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if(pRandom.nextBoolean()){return;}
        for (int i = -5; i <= 5; i++) {
            for (int j = -5; j <= 5; j++) {
                BlockPos pos = pPos.offset(i, 0, j);
                BlockState state = pLevel.getBlockState(pos);

                if (state.getBlock() instanceof CropBlock cropBlock) {
                    if (cropBlock.isValidBonemealTarget(pLevel, pos, state, pLevel.isClientSide)) {
                        if (cropBlock.isBonemealSuccess(pLevel, pLevel.random, pos, state)) {
                            cropBlock.performBonemeal((ServerLevel) pLevel, pLevel.random, pos, state);
                            if (pRandom.nextBoolean()) {
                                pLevel.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS);
                            }
                        }
                    }
                }
            }
        }
        super.randomTick(pState, pLevel, pPos, pRandom);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        super.entityInside(pState, pLevel, pPos, pEntity);
        if (pEntity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100));
        }
    }

    private static float randomDirectionModifier(RandomSource pRandom, int pReduce) {
        return ((pRandom.nextFloat() / pReduce) * pRandom.nextIntBetweenInclusive(-1, 1));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        pTooltip.add(Component.translatable("block.betterarcheology.growth_totem_tooltip").withStyle(ChatFormatting.DARK_GREEN));
    }
}
