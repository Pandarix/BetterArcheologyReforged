package net.Pandarix.betterarcheology.villager;

import com.google.common.collect.ImmutableSet;
import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.ModBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModVillagers {
    //REGISTRIES--------------------------------------------------------------------//
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, BetterArcheology.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, BetterArcheology.MOD_ID);

    //ENTRIES--------------------------------------------------------------------//
    public static final RegistryObject<PoiType> ARCHEOLOGY_TABLE_POI = POI_TYPES.register("archeology_table_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.ARCHEOLOGY_TABLE.get().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final RegistryObject<VillagerProfession> ARCHEOLOGIST = VILLAGER_PROFESSIONS.register("archeologist",
            () -> new VillagerProfession("archeologist", x -> x.get() == ARCHEOLOGY_TABLE_POI.get(),
                    x -> x.get() == ARCHEOLOGY_TABLE_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.BRUSH_SAND));

    //REGISTRATION---------------------------------------------------------------//
    public static void registerVillagers() {
        BetterArcheology.LOGGER.debug("Registering Villagers for " + BetterArcheology.MOD_ID);
    }
}
