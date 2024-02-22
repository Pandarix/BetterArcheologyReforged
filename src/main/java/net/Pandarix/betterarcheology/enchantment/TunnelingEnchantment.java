package net.Pandarix.betterarcheology.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class TunnelingEnchantment extends ArtifactEnchantment
{
    public TunnelingEnchantment(Rarity weight, EnchantmentCategory target, EquipmentSlot... slotTypes)
    {
        super(weight, target, slotTypes);
    }

    @Override
    public boolean canEnchant(ItemStack pStack)
    {
        return pStack.getItem() instanceof PickaxeItem || pStack.getItem() instanceof ShovelItem;
    }
}
