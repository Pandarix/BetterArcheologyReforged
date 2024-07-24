package net.Pandarix.betterarcheology;

import com.supermartijn642.configlib.api.ConfigBuilders;
import com.supermartijn642.configlib.api.IConfigBuilder;

import java.util.function.Supplier;

/**
 * Config depending on supermartijn642's ConfigLib
 * Allows for easy editing and automatic reloading and syncing using suppliers
 */
public class BetterArcheologyConfig
{
    //ARTIFACTS
    public static final Supplier<Boolean> artifactsEnabled;
    public static final Supplier<Double> penetratingStrikeIgnorance;
    public static final Supplier<Double> soaringWindsBoost;
    //TOTEMS
    public static final Supplier<Boolean> radianceTotemDamageEnabled;
    public static final Supplier<Integer> radianceTotemDamage;
    public static final Supplier<Integer> radianceTotemDamageTickAverage;
    public static final Supplier<Integer> radianceTotemRadius;
    //FOSSILS
    public static final Supplier<Integer> ocelotFleeRange;

    static
    {
        IConfigBuilder builder = ConfigBuilders.newTomlConfig("betterarcheology", null, false);

        builder.push("Artifacts");
            artifactsEnabled = builder.comment("Set to true or false to enable or disable effects.").define("artifactEnchantmentsEnabled", true);

            penetratingStrikeIgnorance = builder.comment("Set to % of damage-reduction from Protection Enchantments that should be ignored, keep in range of 0-1.00").define("penetratingStrikeProtectionIgnorance", 0.33d, 0d, 1d);

            soaringWindsBoost = builder.comment("Set to movement speed multiplier, that should be applied when starting to fly").define("soaringWindsBoost", 0.3d, 0d, 1d);
        builder.pop();

        builder.push("Totems");
            radianceTotemDamageEnabled = builder.comment("En-/Disables the Radiance Totem damaging hostile mobs around it.").define("radianceTotemDamageEnabled", true);

            radianceTotemDamage = builder.comment("Sets the damage in 1/2 hearts that will be dealt to hostile mobs when a damage tick occurs.").define("radianceTotemDamage", 4, 1, 40);

            radianceTotemDamageTickAverage = builder.comment("Sets the average time between damage ticks of the Radiance Totem in seconds. The totem will still damage mobs randomly, but the average time between damage ticks will be this value.").define("radianceTotemDamageTickAverage", 3, 1, 60);

            radianceTotemRadius = builder.comment("Sets the radius around the radiance totem within which entities will be affected by the glowing and damaging effects.").define("radianceTotemRadius", 10, 1, 90);
        builder.pop();

        builder.push("Fossils");
            ocelotFleeRange = builder.comment("Range in Blocks that the Fossil scares Creepers away.").define("fossilsOcelotFleeRange", 20, 10, 50);
        builder.pop();

        builder.build();
    }

    public static void init()
    {
    }
}
