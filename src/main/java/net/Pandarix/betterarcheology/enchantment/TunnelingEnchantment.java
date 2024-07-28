package net.Pandarix.betterarcheology.enchantment;

import net.Pandarix.betterarcheology.BetterArcheologyConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import javax.annotation.ParametersAreNonnullByDefault;

public class TunnelingEnchantment extends ArtifactEnchantment
{
    public TunnelingEnchantment(Rarity weight, EnchantmentCategory target, EquipmentSlot... slotTypes)
    {
        super(weight, target, slotTypes);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean canEnchant(ItemStack pStack)
    {
        if (BetterArcheologyConfig.tunnelingEnabled.get() && BetterArcheologyConfig.artifactsEnabled.get())
        {
            return pStack.getItem() instanceof PickaxeItem || pStack.getItem() instanceof ShovelItem || pStack.getItem() instanceof HoeItem || (pStack.getItem() instanceof AxeItem && BetterArcheologyConfig.tunnelingAxeEnabled.get());
        }
        return false;
    }
}
