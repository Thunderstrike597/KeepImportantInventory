package net.kenji.keep_important_inventory.gamerule;

import net.minecraft.world.level.GameRules;

public class ModGameRules {


    public static final GameRules.Key<GameRules.BooleanValue> ONLY_KEEP_IMPORTANT_INVENTORY =
            GameRules.register("keepOnlyImportantInventory", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));


    public static void register() {

    }
}
