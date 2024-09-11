package net.Pandarix.betterarcheology.mixin;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.BetterArcheologyConfig;
import net.Pandarix.betterarcheology.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class PenetratingStrikeMixin
{
    @Inject(method = "getDamageProtection", at = @At("RETURN"), cancellable = true)
    private static void injectMethod(ServerLevel pLevel, LivingEntity pEntity, DamageSource pDamageSource, CallbackInfoReturnable<Float> cir)
    {
        if (BetterArcheologyConfig.artifactsEnabled.get() && BetterArcheologyConfig.penetratingStrikeEnabled.get())
        {
            try
            {
                Holder.Reference<Enchantment> ba$penetratingStrike = pLevel.registryAccess().asGetterLookup().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ModEnchantments.PENETRATING_STRIKE_KEY);

                if (pDamageSource.getWeaponItem() != null && EnchantmentHelper.getItemEnchantmentLevel(ba$penetratingStrike, pDamageSource.getWeaponItem()) >= 1)
                {
                    BetterArcheology.LOGGER.info("Das ist ein Test von: PStrike");

                    float dmgWithReducedProt = cir.getReturnValue() * (float) (1 - BetterArcheologyConfig.penetratingStrikeIgnorance.get());
                    cir.setReturnValue(Math.max(0, dmgWithReducedProt));
                }
            } catch (Exception e)
            {
                BetterArcheology.LOGGER.error("Could not test for Penetrating Strike because EnchantmentEntry could not be found", e);
            }
        }
        cir.setReturnValue(cir.getReturnValue());
    }
}
