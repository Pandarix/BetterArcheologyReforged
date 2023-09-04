package net.Pandarix.betterarcheology.mixin;

import net.Pandarix.betterarcheology.enchantment.ModEnchantments;
import net.Pandarix.betterarcheology.util.ModConfigs;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class ElytraStartupMixin {
    @Shadow public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    @Inject(method = "startFallFlying", at = @At(value = "TAIL"))
    private void injectMethod(CallbackInfo ci){
        //if it is enabled in the config and the chestslot is enchanted
        if(ModConfigs.ARTIFACT_ENCHANTMENTS_ENABLED.get() && this.getItemBySlot(EquipmentSlot.CHEST).isEnchanted()){
            //if the enchantment in the chestslot is soaring winds
            if(EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.SOARING_WINDS.get(), this.getItemBySlot(EquipmentSlot.CHEST)) == 1){

                Player betterarcheology$player = (Player) (Object) this;
                float betterarcheology$boost = ModConfigs.SOARING_WINDS_BOOST.get().floatValue() * 0.5f;
                Vec3 betterarcheology$vec3d = betterarcheology$player.getLookAngle();
                Vec3 betterarcheology$vec3d2 = betterarcheology$player.getDeltaMovement();

                //add player velocity when starting to fall-fly
                betterarcheology$player.setDeltaMovement(betterarcheology$vec3d2.add(
                        betterarcheology$vec3d.x * 0.1 + (betterarcheology$vec3d.x * 1.5 - betterarcheology$vec3d2.x) * betterarcheology$boost,
                        betterarcheology$vec3d.y * 0.1 + (betterarcheology$vec3d.y * 1.5 - betterarcheology$vec3d2.y) * betterarcheology$boost/2,
                        betterarcheology$vec3d.z * 0.1 + (betterarcheology$vec3d.z * 1.5 - betterarcheology$vec3d2.z) * betterarcheology$boost));
            }
        }
    }
}
