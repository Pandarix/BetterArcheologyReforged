package net.Pandarix.betterarcheology.enchantment;

import com.github.alexthe666.alexsmobs.item.ItemTarantulaHawkElytra;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.fml.ModList;

public class SoaringWindsEnchantment extends ArtifactEnchantment
{
    protected SoaringWindsEnchantment(Rarity weight, EnchantmentCategory target, EquipmentSlot... slotTypes)
    {
        super(weight, target, slotTypes);
    }

    @Override
    public int getMaxLevel()
    {
        return 1;
    }

    @Override
    public boolean canEnchant(ItemStack pStack)
    {
        if (pStack.getItem() instanceof ElytraItem)
        {
            return true;
        }
        // IF CURIOSAPI is installed
        if (ModList.get().isLoaded("alexsmobs"))
        {
            return pStack.getItem() instanceof ItemTarantulaHawkElytra;
        }

        return false;
    }
}
