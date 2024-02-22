package net.Pandarix.betterarcheology.enchantment;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments
{
    // Create a Deferred Register to hold Enchantments which will all be registered under the "BetterArcheology" namespace
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, BetterArcheology.MOD_ID);

    public static RegistryObject<Enchantment> PENETRATING_STRIKE = ENCHANTMENTS.register("penetrating_strike", () -> new PenetratingStrikeEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> SOARING_WINDS = ENCHANTMENTS.register("soaring_winds", () -> new SoaringWindsEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.ARMOR_CHEST, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> TUNNELING = ENCHANTMENTS.register("tunneling", () -> new TunnelingEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
}
