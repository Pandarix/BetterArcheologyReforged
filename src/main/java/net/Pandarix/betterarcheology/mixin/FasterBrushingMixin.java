package net.Pandarix.betterarcheology.mixin;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.item.BetterBrushItem;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrushableBlockEntity.class)
public abstract class FasterBrushingMixin
{
    @Shadow
    private long coolDownEndsAtTick;

    @Inject(method = "brush", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BrushableBlockEntity;unpackLootTable(Lnet/minecraft/world/entity/player/Player;)V"))
    private void injectMethod(long worldTime, Player player, Direction hitDirection, CallbackInfoReturnable<Boolean> cir)
    {
        try
        {
            if (player.getUseItem().getItem() instanceof BetterBrushItem ba$brushItem)
            {
                this.coolDownEndsAtTick -= (long) (10L - ba$brushItem.getBrushingSpeed());
            }
        } catch (Exception e)
        {
            BetterArcheology.LOGGER.info("Could not apply faster brushing due to error: " + e);
        }
    }
}
