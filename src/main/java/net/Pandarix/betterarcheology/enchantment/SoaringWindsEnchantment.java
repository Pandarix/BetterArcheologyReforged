package net.Pandarix.betterarcheology.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SoaringWindsEnchantment extends ArtifactEnchantment {
    protected SoaringWindsEnchantment(Rarity weight, EnchantmentCategory target, EquipmentSlot... slotTypes) {
        super(weight, target, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        return pStack.getItem() instanceof ElytraItem;
    }
}
