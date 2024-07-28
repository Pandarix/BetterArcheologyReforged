package net.Pandarix.betterarcheology.enchantment;

import net.Pandarix.betterarcheology.BetterArcheologyConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import javax.annotation.ParametersAreNonnullByDefault;

public class SoaringWindsEnchantment extends ArtifactEnchantment
{
    protected SoaringWindsEnchantment(Rarity weight, EnchantmentCategory target, EquipmentSlot... slotTypes)
    {
        super(weight, target, slotTypes);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean canEnchant(ItemStack pStack)
    {
        if (BetterArcheologyConfig.soaringWindsEnabled.get() && BetterArcheologyConfig.artifactsEnabled.get())
        {
            return pStack.getItem() instanceof ElytraItem;
        }
        return false;
    }
}
