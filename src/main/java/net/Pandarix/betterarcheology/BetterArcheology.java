package net.Pandarix.betterarcheology;

import com.mojang.logging.LogUtils;
import net.Pandarix.betterarcheology.block.ModBlocks;
import net.Pandarix.betterarcheology.block.entity.ModBlockEntities;
import net.Pandarix.betterarcheology.compat.jei.recipe.ModRecipes;
import net.Pandarix.betterarcheology.enchantment.ModEnchantments;
import net.Pandarix.betterarcheology.entity.ModEntityTypes;
import net.Pandarix.betterarcheology.item.ModItemGroup;
import net.Pandarix.betterarcheology.item.ModItems;
import net.Pandarix.betterarcheology.networking.ModMessages;
import net.Pandarix.betterarcheology.screen.FossilInventoryScreen;
import net.Pandarix.betterarcheology.screen.IdentifyingScreen;
import net.Pandarix.betterarcheology.screen.ModMenuTypes;
import net.Pandarix.betterarcheology.villager.ModVillagers;
import net.Pandarix.betterarcheology.world.structure.ModStructuresMain;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BetterArcheology.MOD_ID)
public class

BetterArcheology
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "betterarcheology";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public BetterArcheology()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        BetterArcheologyConfig.init();

        // Register the Deferred Register to the mod event bus so items get registered
        ModItems.ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so blocks get registered
        ModBlocks.BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        ModItemGroup.CREATIVE_MODE_TABS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so Entities get registered
        ModEntityTypes.ENTITY_TYPES.register(modEventBus);

        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModMenuTypes.MENUS.register(modEventBus);
        ModRecipes.SERIALIZERS.register(modEventBus);

        ModVillagers.POI_TYPES.register(modEventBus);
        ModVillagers.VILLAGER_PROFESSIONS.register(modEventBus);

        ModEnchantments.ENCHANTMENTS.register(modEventBus);

        ModStructuresMain.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(ModMessages::register);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(ModMenuTypes.FOSSIL_MENU.get(), FossilInventoryScreen::new);
            MenuScreens.register(ModMenuTypes.IDENTIFYING_MENU.get(), IdentifyingScreen::new);
        }
    }
}
