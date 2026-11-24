package net.kenji.keep_important_inventory.mixins;

import net.kenji.keep_important_inventory.api.KeepInventoryOverride;
import net.kenji.keep_important_inventory.api.interfaces.XpDeathData;
import net.kenji.keep_important_inventory.helpers.ModHelper;
import net.kenji.keep_important_inventory.helpers.XpUtil;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class MixinServerPlayer {

    @Inject(method = "restoreFrom", at = @At("HEAD"))
    private void kii$beginRestore(ServerPlayer oldPlayer, boolean keepEverything, CallbackInfo ci) {
        if (keepEverything || oldPlayer.isSpectator()) return;
        if (ModHelper.isKeepImportantInventory((ServerPlayer) (Object) this)) KeepInventoryOverride.enable();
    }

    @Inject(method = "restoreFrom", at = @At("TAIL"))
    private void kii$endRestore(ServerPlayer oldPlayer, boolean keepEverything, CallbackInfo ci) {
        if (!KeepInventoryOverride.isActive()) return;
        KeepInventoryOverride.disable();

        // Vanilla has now copied the inventory and the FULL XP, so apply your XP rule on top
        ServerPlayer self = (ServerPlayer) (Object) this;
        int total = XpUtil.totalPoints(oldPlayer);
        int dropped = ((XpDeathData) oldPlayer).kii$getDroppedXp();
        if (dropped < 0) dropped = XpUtil.droppedPoints(total, oldPlayer.getRandom());
        XpUtil.setTotalPoints(self, total - dropped);
    }
}