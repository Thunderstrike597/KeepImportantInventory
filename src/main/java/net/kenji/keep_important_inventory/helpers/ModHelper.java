package net.kenji.keep_important_inventory.helpers;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.*;
import net.minecraft.world.level.GameRules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ModHelper {

    public static boolean isKeepInventory(ServerPlayer player){
        return player.serverLevel().getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).get();
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
