package net.Pandarix.betterarcheology.mixin;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.BetterArcheologyConfig;
import net.Pandarix.betterarcheology.enchantment.ModEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class TunnelingEnchantmentMixin
{
    @Inject(method = "mineBlock", at = @At(value = "RETURN"))
    private void injectMethod(ItemStack stack, net.minecraft.world.level.Level level, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cir)
    {
        try
        {
            Holder.Reference<Enchantment> tunneling = level.registryAccess().asGetterLookup().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ModEnchantments.TUNNELING_KEY);

            //if it is enabled in the config and the stack exists, has Enchantments & is Tunneling
            if (BetterArcheologyConfig.artifactsEnabled.get() && BetterArcheologyConfig.tunnelingEnabled.get() && !miner.isShiftKeyDown() && !stack.isEmpty() && stack.isEnchanted() && EnchantmentHelper.getItemEnchantmentLevel(tunneling, stack) == 1)
            {
                //if the tool is right for the block that should be broken
                //if the difference of the hardness of the block below is not more than 3,75
                BlockPos ba$downPos = pos.below();
                BlockState ba$blockStateBelow = level.getBlockState(ba$downPos);

                if (stack.isCorrectToolForDrops(state) && stack.isCorrectToolForDrops(ba$blockStateBelow) && Math.abs(ba$blockStateBelow.getDestroySpeed(level, pos.below()) - state.getDestroySpeed(level, pos)) <= 3.75)
                {
                    if (miner instanceof ServerPlayer player)
                    {
                        BlockEntity ba$blockEntityBelow = level.getBlockEntity(ba$downPos);
                        Block ba$block = ba$blockStateBelow.getBlock();
                        ItemStack ba$stackCopy = stack.copy();

                        int ba$exp = net.minecraftforge.common.ForgeHooks.onBlockBreakEvent(level, player.gameMode.getGameModeForPlayer(), player, ba$downPos);
                        if (ba$exp != -1)
                        {
                            boolean harvestable = ba$blockStateBelow.canHarvestBlock(level, ba$downPos, player);

                            if (stack.isEmpty() && !ba$stackCopy.isEmpty())
                            {
                                net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, ba$stackCopy, InteractionHand.MAIN_HAND);
                            }

                            boolean destroyed = player.gameMode.removeBlock(ba$downPos, harvestable);
                            if (destroyed && harvestable)
                            {
                                ba$block.playerDestroy(level, player, ba$downPos, ba$blockStateBelow, ba$blockEntityBelow, ba$stackCopy);
                            }

                            if (harvestable && ba$exp > 0)
                            {
                                ba$block.popExperience(player.serverLevel(), ba$downPos, ba$exp);
                            }
                        }
                    }
                }
            }
        } catch (Exception e)
        {
            BetterArcheology.LOGGER.error("Could not apply Tunneling Enchantment's effect!: ", e);
        }
    }
}
