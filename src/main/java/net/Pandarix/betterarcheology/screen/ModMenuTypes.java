package net.Pandarix.betterarcheology.screen;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, BetterArcheology.MOD_ID);

    public static final RegistryObject<MenuType<FossilInventoryMenu>> FOSSIL_MENU = registerMenuType(FossilInventoryMenu::new, "fossil");
    public static final RegistryObject<MenuType<IdentifyingMenu>> IDENTIFYING_MENU = registerMenuType(IdentifyingMenu::new, "identifying");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }
}
