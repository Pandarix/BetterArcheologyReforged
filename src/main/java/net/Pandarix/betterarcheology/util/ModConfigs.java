package net.Pandarix.betterarcheology.util;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ModConfigs
{
    public static final String CATEGORY_ARTIFACTS = "artifacts";
    public static final String CATEGORY_FOSSILS = "fossils";

    public static ForgeConfigSpec.IntValue OCELOT_FOSSIL_FLEE_RANGE;

    public static ForgeConfigSpec.BooleanValue ARTIFACT_ENCHANTMENTS_ENABLED;
    public static ForgeConfigSpec.DoubleValue PENETRATING_STRIKE_PROTECTION_IGNORANCE;
    public static ForgeConfigSpec.DoubleValue SOARING_WINDS_BOOST;

    public static void register()
    {
        registerServerConfigs();
    }

    private static void registerServerConfigs()
    {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

        SERVER_BUILDER.comment("Settings for Artifacts").push(CATEGORY_ARTIFACTS);

        ARTIFACT_ENCHANTMENTS_ENABLED = SERVER_BUILDER.comment("Set to true or false to enable or disable effects").define("artifact.enchantments.enabled", true);

        PENETRATING_STRIKE_PROTECTION_IGNORANCE = SERVER_BUILDER.comment("Set to % of damage-reduction from Protection Enchantments that should be ignored, keep in range of 0-1.00").defineInRange("penetrating.strike.protection.ignorance", 0.33f, 0, 1);

        SOARING_WINDS_BOOST = SERVER_BUILDER.comment("Set to movement speed multiplier, that should be applied when starting to fly").defineInRange("soaring.winds.boost", 0.3f, 0, 1);

        SERVER_BUILDER.pop();

        SERVER_BUILDER.comment("Settings for the Botanist Villager").push(CATEGORY_FOSSILS);

        OCELOT_FOSSIL_FLEE_RANGE = SERVER_BUILDER.comment("Range in Blocks that the Fossil scares Creepers away").defineInRange("ocelot.fossil.flee.range", 20, 10, 50);

        SERVER_BUILDER.pop();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_BUILDER.build());
    }
}