package net.Pandarix.betterarcheology.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ArtifactEnchantment extends Enchantment
{
    protected ArtifactEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots)
    {
        super(pRarity, pCategory, pApplicableSlots);
    }

    //Restricting any other access to the Enchantment than Archeology-----------------------------------//
    @Override
    public boolean isAllowedOnBooks()
    {
        return false;
    }

    @Override
    public boolean isDiscoverable()
    {
        return false;
    }

    @Override
    public boolean isTradeable()
    {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
        return false;
    }

    @Override
    public boolean allowedInCreativeTab(Item book, Set<EnchantmentCategory> allowedCategories)
    {
        return false;
    }

    @Override
    public @NotNull Rarity getRarity()
    {
        return Rarity.VERY_RARE;
    }
}
