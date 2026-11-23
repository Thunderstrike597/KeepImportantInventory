package net.kenji.keep_important_inventory;

import net.minecraftforge.common.ForgeConfigSpec;

// Client-side config, registered in the main class as ModConfig.Type.CLIENT
public class ConfigCommon {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<Boolean> KEEP_BOSS_DEATH_ITEMS;
    public static final ForgeConfigSpec.ConfigValue<Boolean> DROP_XP_RANGED;
    public static final ForgeConfigSpec.ConfigValue<Boolean> DROP_LOOT_RANGED;

    public static final ForgeConfigSpec.ConfigValue<Integer> MIN_ITEMS_COUNT;
    public static final ForgeConfigSpec.ConfigValue<Double> LOOT_DROPPED_PORTION;
    public static final ForgeConfigSpec.ConfigValue<Double> MIN_LOOT_DROPPED_AMOUNT;
    public static final ForgeConfigSpec.ConfigValue<Double> MAX_LOOT_DROPPED_AMOUNT;
    public static final ForgeConfigSpec.ConfigValue<Double> DROPPED_XP_AMOUNT;
    public static final ForgeConfigSpec.ConfigValue<Double> XP_LOST_AMOUNT;

    public static final ForgeConfigSpec.ConfigValue<Double> MIN_XP_DROPPED_AMOUNT;
    public static final ForgeConfigSpec.ConfigValue<Double> MAX_XP_DROPPED_AMOUNT;


    public static final ForgeConfigSpec SPEC;

    static {


        BUILDER.push("Death Rules");

        KEEP_BOSS_DEATH_ITEMS = BUILDER.comment("Whether or not you keep all loot when you die to a boss (If true, no loot drops on death from boss)")
                .define("Keep All Loot On Boss Death",
                        true);
        DROP_XP_RANGED = BUILDER.comment("Whether or not XP drop amount is chosen by a random amount within an in/out range, dictated by <Min Xp Drop Amount>/<Max Xp Drop Amount> (If true, the xp dropped on death will be random between a minimum & maximum value, eg. Min = 0.50, Max = 0.90 - Death could drop anywhere between 50% to 90% of your current Xp)")
                .define("Drop Ranged XP",
                        true);
        DROP_LOOT_RANGED = BUILDER.comment("Whether or not dropped item amount is chosen by a random amount within an in/out range, dictated by <Min Loot Drop Amount>/<Max Loot Drop Amount> (If true, the items dropped on death will be random between a minimum & maximum value, eg. Min = 0.50, Max = 0.90 - Death could drop anywhere between 50% to 90% of each item in each stack)")
                .define("Drop Ranged Loot",
                        true);
        BUILDER.pop();
        BUILDER.push("Loot & Items");

        MIN_ITEMS_COUNT = BUILDER.comment("The percentage of(0.0 represent 0 and 1.0 represents 100)").define("Min Items Count", 5);

        LOOT_DROPPED_PORTION = BUILDER.comment("The percentage of valid loot dropped on death. For instance, if you have 64 diamonds in your inventory 0.5 would drop extactly 50% or Half of the diamonds (32 would be dropped) (0.0 represent 0 and 1.0 represents 100)")
                .defineInRange("Loot Dropped Portion",
                        0.42, 0.0, 1.0);
        MIN_LOOT_DROPPED_AMOUNT = BUILDER.comment("The minimum loot percentage that can drop on Death (This only applies if the config option 'Drop Ranged Loot' is enabled)")
                .defineInRange("Min Loot Dropped Amount",
                        0.34, 0.0, 1.0);
        MAX_LOOT_DROPPED_AMOUNT = BUILDER.comment("The maximum loot percentage that can drop on Death (This only applies if the config option 'Drop Ranged Loot' is enabled)")
                .defineInRange("Min Loot Dropped Amount",
                        0.62, 0.0, 1.0);
        BUILDER.pop();
        BUILDER.push("Xp & Levels");

        DROPPED_XP_AMOUNT = BUILDER.comment("The percentage of XP you drop when you die (0.0 represent 0 and 1.0 represents 100)")
                .defineInRange("Reobtainable Xp Lost Amount",
                        0.82, 0.0, 1.0);

        XP_LOST_AMOUNT = BUILDER.comment("The percentage of XP lost on death which is unable to be re-obtained when returning to you place of death (0.0 represent 0 and 1.0 represents 100)")
                .defineInRange("Xp Lost Amount",
                        0.75, 0.0, 1.0);
        MIN_XP_DROPPED_AMOUNT = BUILDER.comment("The minimum XP percentage that can drop on Death (This only applies if the config option 'Drop Ranged XP' is enabled)")
                .defineInRange("Min Xp Drop Amount",
                        0.60, 0.0, 1.0);
        MAX_XP_DROPPED_AMOUNT = BUILDER.comment("The maximum XP percentage that can drop on Death (This only applies if the config option 'Drop Ranged XP' is enabled)")
                .defineInRange("Min Xp Drop Amount",
                        0.90, 0.0, 1.0);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }


}
