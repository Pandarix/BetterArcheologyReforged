package net.Pandarix.betterarcheology.util;

import net.Pandarix.betterarcheology.enchantment.ModEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;

public class ArtifactEnchantmentHelper
{
    public static boolean hasSoaringWinds(Player player)
    {
        if (player == null)
        {
            return false;
        }

        //  if there is an elytra in the chestslot and it has the enchantment
        if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ElytraItem
                && EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.SOARING_WINDS.get(), player.getItemBySlot(EquipmentSlot.CHEST)) >= 1)
        {
            return true;
        }

        // IF CURIOSAPI is installed
        if (ModList.get().isLoaded("elytraslot"))
        {
            // check for back-slot (used by ElytraSlot mod)
            if (CuriosApi.getPlayerSlots().containsKey("back"))
            {
                Optional<ICuriosItemHandler> curios = CuriosApi.getCuriosInventory(player).resolve();
                // searching the backslot for the Elytra
                if (curios.isPresent())
                {
                    return curios.get().findCurios("back").stream().anyMatch(
                            (slotResult) -> slotResult.stack().getItem() instanceof ElytraItem
                                    && EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.SOARING_WINDS.get(), slotResult.stack()) >= 1);
                }
            }
        }

        return false;
    }
}
