package net.kenji.keep_important_inventory.helpers;

import net.kenji.keep_important_inventory.gamerule.ModGameRules;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.GameRules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ModHelper {

    public static boolean isKeepImportantInventory(Player player){
        if(player instanceof ServerPlayer serverPlayer)
            return serverPlayer.serverLevel().getGameRules().getRule(ModGameRules.ONLY_KEEP_IMPORTANT_INVENTORY).get() && !serverPlayer.serverLevel().getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).get();
        return false;
    }

    public static Collection<ItemStack> getDroppedItemsFromPlayer(ServerPlayer player){
        Inventory inventory = player.getInventory();
        List<ItemStack> stacks = new ArrayList<>();
        for(ItemStack stack : inventory.items){
            if(stack.isEmpty())continue;
            if(stack.getItem() instanceof Equipable)continue;
            if(stack.getItem() instanceof TieredItem)continue;

            stacks.add(stack);
        }
        return stacks;
    }
}
