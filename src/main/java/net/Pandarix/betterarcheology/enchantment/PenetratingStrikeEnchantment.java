package net.Pandarix.betterarcheology.enchantment;

import net.Pandarix.betterarcheology.BetterArcheologyConfig;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import javax.annotation.ParametersAreNonnullByDefault;

public class PenetratingStrikeEnchantment extends ArtifactEnchantment
{

    public PenetratingStrikeEnchantment(Rarity weight, EquipmentSlot... slotTypes)
    {
        super(weight, EnchantmentCategory.WEAPON, slotTypes);
    }

    //also allowing axes
    @Override
    public boolean canEnchant(ItemStack pStack)
    {
        if (pStack.getItem() instanceof AxeItem)
        {
            return true;
        }
        return super.canEnchant(pStack);
    }

    //Enchantment Functionality-------------------------------------------------------------------------//
    @Override
    public int getMaxLevel()
    {
        return 1;
    }

    /*
        Decreases the damage reduction caused from the target's armor
        by dealing additional damage relative to the armor and level
        for reference, see: https://minecraft.fandom.com/wiki/Armor#Enchantments
         */
    @Override
    @ParametersAreNonnullByDefault
    public void doPostAttack(LivingEntity user, Entity target, int level)
    {
        if (!BetterArcheologyConfig.artifactsEnabled.get())
        {
            return;
        }

        if (target instanceof LivingEntity targetEntity)
        {
            if (user instanceof Player player)
            {
                int enchantmentProtectionFactor = EnchantmentHelper.getDamageProtection(target.getArmorSlots(), player.damageSources().mobAttack(targetEntity));

                //damage in % that was subtracted due to the Enchantments' protections
                double damagePercentageProtected = enchantmentProtectionFactor / 25f;

                //damagevalue of the current weapon
                float damageInflicted = 0;

                //set to value of getAttackDamage
                //method is not inherited, therefore a hard if-check is needed
                if (player.getMainHandItem().getItem() instanceof SwordItem)
                {
                    damageInflicted = ((SwordItem) player.getMainHandItem().getItem()).getDamage() + 1;
                } else if (player.getMainHandItem().getItem() instanceof AxeItem)
                {
                    damageInflicted = ((AxeItem) player.getMainHandItem().getItem()).getAttackDamage() + 1;
                }

                //calculates total damage that was reduced
                float totalProtectedDamage = (float) (damageInflicted * damagePercentageProtected);
                float damageToRedo = (float) (totalProtectedDamage * BetterArcheologyConfig.penetratingStrikeIgnorance.get());

                if (level == 1)
                {
                    targetEntity.hurt(targetEntity.damageSources().magic(), damageToRedo * 7.5f);
                }

                //Audio Feedback
                if (!user.level().isClientSide())
                {
                    user.level().playSound(null, target.getOnPos(), SoundEvents.ARMOR_EQUIP_CHAIN, SoundSource.BLOCKS);
                }
            }
        }
        super.doPostAttack(user, target, level);
    }
}

