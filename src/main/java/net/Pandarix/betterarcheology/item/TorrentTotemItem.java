package net.Pandarix.betterarcheology.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TorrentTotemItem extends Item
{
    public TorrentTotemItem(Item.Properties pProperties)
    {
        super(pProperties);
    }

    private static final int speed = 2;

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
    {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);

        Vec3 rotationVector = pPlayer.getLookAngle();
        Vec3 velocity = pPlayer.getDeltaMovement();

        pPlayer.setDeltaMovement(velocity.add(rotationVector.x * 0.1D + (rotationVector.x * 1.5D - velocity.x) * speed, (double) 0, rotationVector.z * 0.1D + (rotationVector.z * 1.5D - velocity.z) * speed));
        pPlayer.startAutoSpinAttack(8);

        //sounds
        pLevel.playSound(null, pPlayer, SoundEvents.WATER_AMBIENT, SoundSource.NEUTRAL, 0.1f, (float) pLevel.getRandom().nextDouble() * 0.5f + 0.5f);
        pLevel.playSound(null, pPlayer, SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.NEUTRAL, 0.25F, 0.35F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));

        pPlayer.getCooldowns().addCooldown(this, 120);
        itemStack.hurtAndBreak(1, pPlayer, (p) ->
        {
            p.broadcastBreakEvent(pPlayer.getUsedItemHand());
        });
        return InteractionResultHolder.consume(itemStack);
    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced)
    {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("item.betterarcheology.torrent_totem_description").withStyle(ChatFormatting.DARK_AQUA));
    }

    @Override
    public int getUseDuration(ItemStack pStack)
    {
        return 0;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack)
    {
        return UseAnim.BOW;
    }
}