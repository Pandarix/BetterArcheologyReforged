package net.Pandarix.betterarcheology.world.structure;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModStructuresMain
{

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = BetterArcheology.MOD_ID;

    public ModStructuresMain()
    {
        // For registration and init stuff.
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModStructures.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
    }

    public static void register(IEventBus eventBus)
    {
        ModStructures.DEFERRED_REGISTRY_STRUCTURE.register(eventBus);
    }
}