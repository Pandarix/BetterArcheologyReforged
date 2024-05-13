package net.Pandarix.betterarcheology.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
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

    // We're able to pass null here because the entity is not used in the check as of 1.20
    @Override
    public boolean canEnchant(ItemStack pStack) {
        return pStack.canElytraFly(null);
    }
}
