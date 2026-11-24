package net.kenji.keep_important_inventory.events;

import net.kenji.keep_important_inventory.KeepImportantInventory;
import net.kenji.keep_important_inventory.api.KeepInventoryOverride;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = KeepImportantInventory.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEvents {

    // Slot mods (HIGHEST/NORMAL) have run by now; grave mods (LOWEST) see keepInventory off
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void endKeepOverride(LivingDropsEvent event) {
        KeepInventoryOverride.disable();
    }

    // If anything throws mid-death, the flag never stays on past the end of the tick
    @SubscribeEvent
    public static void resetOverride(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) KeepInventoryOverride.disable();
    }
}