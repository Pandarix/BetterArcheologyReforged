package net.Pandarix.betterarcheology.mixin;

import net.Pandarix.betterarcheology.BetterArcheologyConfig;
import net.Pandarix.betterarcheology.util.ArtifactEnchantmentHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class ElytraStartupMixin
{
    @Inject(method = "startFallFlying", at = @At(value = "TAIL"))
    private void injectMethod(CallbackInfo ci)
    {
        //if it is enabled in the config and the chestslot is enchanted
        if (BetterArcheologyConfig.artifactsEnabled.get())
        {
            Player betterarcheology$player = (Player) (Object) this;
            if (ArtifactEnchantmentHelper.hasSoaringWinds(betterarcheology$player))
            {
                float betterarcheology$boost = BetterArcheologyConfig.soaringWindsBoost.get().floatValue() * 0.5f;
                Vec3 betterarcheology$vec3d = betterarcheology$player.getLookAngle();
                Vec3 betterarcheology$vec3d2 = betterarcheology$player.getDeltaMovement();

                //add player velocity when starting to fall-fly
                betterarcheology$player.setDeltaMovement(betterarcheology$vec3d2.add(
                        betterarcheology$vec3d.x * 0.1 + (betterarcheology$vec3d.x * 1.5 - betterarcheology$vec3d2.x) * betterarcheology$boost,
                        betterarcheology$vec3d.y * 0.1 + (betterarcheology$vec3d.y * 1.5 - betterarcheology$vec3d2.y) * betterarcheology$boost / 1.8,
                        betterarcheology$vec3d.z * 0.1 + (betterarcheology$vec3d.z * 1.5 - betterarcheology$vec3d2.z) * betterarcheology$boost));
            }
        }
    }
}
