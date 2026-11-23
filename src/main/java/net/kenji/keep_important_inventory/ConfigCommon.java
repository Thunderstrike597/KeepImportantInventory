package net.kenji.keep_important_inventory;

import net.minecraftforge.common.ForgeConfigSpec;

// Client-side config, registered in the main class as ModConfig.Type.CLIENT
public class ConfigCommon {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<Boolean> KEEP_BOSS_DEATH_ITEMS;

    public static final ForgeConfigSpec.ConfigValue<Integer> MIN_ITEMS_COUNT;
    public static final ForgeConfigSpec.ConfigValue<Double> LOOT_DROPPED_PORTION;
    public static final ForgeConfigSpec.ConfigValue<Double> DROPPED_XP_AMOUNT;
    public static final ForgeConfigSpec.ConfigValue<Double> XP_LOST_AMOUNT;

    public static final ForgeConfigSpec SPEC;

    static {
        MIN_ITEMS_COUNT = BUILDER.comment("The percentage of(0.0 represent 0 and 1.0 represents 100)").define("Min Items Count", 5);


        KEEP_BOSS_DEATH_ITEMS = BUILDER.comment("Whether or not you keep all loot when you die to a boss (If true, no loot drops on death from boss)")
                .define("Keep All Loot On Boss Death",
                        true);

        LOOT_DROPPED_PORTION = BUILDER.comment("The percentage of valid loot dropped on death. For instance, if you have 64 diamonds in your inventory 0.5 would drop extactly 50% or Half of the diamonds (32 would be dropped) (0.0 represent 0 and 1.0 represents 100)")
                .defineInRange("Loot Dropped Portion",
                        0.42, 0.0, 1.0);

        DROPPED_XP_AMOUNT = BUILDER.comment("The percentage of XP you drop when you die (0.0 represent 0 and 1.0 represents 100)")
                .defineInRange("Reobtainable Xp Lost Amount",
                        0.72, 0.0, 1.0);

        XP_LOST_AMOUNT = BUILDER.comment("The percentage of XP lost on death which is unable to be re-obtained when returning to you place of death (0.0 represent 0 and 1.0 represents 100)")
                .defineInRange("Xp Lost Amount",
                        0.8, 0.0, 1.0);

        SPEC = BUILDER.build();
    }


}
