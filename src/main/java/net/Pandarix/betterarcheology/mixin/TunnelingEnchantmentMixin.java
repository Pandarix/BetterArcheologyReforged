package net.Pandarix.betterarcheology.mixin;

import net.Pandarix.betterarcheology.enchantment.ModEnchantments;
import net.Pandarix.betterarcheology.util.ModConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DiggerItem.class)
public class TunnelingEnchantmentMixin
{
    @Inject(method = "mineBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V", shift = At.Shift.AFTER))
    private void injectMethod(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cir)
    {
        //if it is enabled in the config and the stack exists, has Enchantments & is Tunneling
        if (ModConfigs.ARTIFACT_ENCHANTMENTS_ENABLED.get() && !stack.isEmpty() && stack.isEnchanted() && EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.TUNNELING.get(), stack) == 1)
        {
            //if the tool is right for the block that should be broken
            //if the difference of the hardness of the block below is not more than 3,75
            BlockState blockBelow = level.getBlockState(pos.below());

            if (stack.isCorrectToolForDrops(state) && stack.isCorrectToolForDrops(blockBelow) && Math.abs(blockBelow.getDestroySpeed(level, pos.below()) - state.getDestroySpeed(level, pos)) <= 3.75)
            {
                //break the block below and damage the tool
                level.destroyBlock(pos.below(), true, miner);
                stack.hurtAndBreak(1, miner, (p) ->
                {
                    p.broadcastBreakEvent(miner.getUsedItemHand());
                });
            }
        }
    }
}
