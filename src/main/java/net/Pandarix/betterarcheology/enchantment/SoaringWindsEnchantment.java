package net.Pandarix.betterarcheology.enchantment;

import com.github.alexthe666.alexsmobs.item.ItemTarantulaHawkElytra;
import net.Pandarix.betterarcheology.BetterArcheologyConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.fml.ModList;

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
            // IF CURIOSAPI is installed
            if (ModList.get().isLoaded("alexsmobs") && pStack.getItem() instanceof ItemTarantulaHawkElytra)
            {
                return true;
            }

            return pStack.getItem() instanceof ElytraItem;
        }
        return false;
    }
}
