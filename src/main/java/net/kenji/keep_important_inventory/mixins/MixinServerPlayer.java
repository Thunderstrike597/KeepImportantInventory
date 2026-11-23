package net.kenji.keep_important_inventory.mixins;

import net.kenji.keep_important_inventory.helpers.ModHelper;
import net.kenji.keep_important_inventory.helpers.XpUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class MixinServerPlayer {

    @Inject(method = "restoreFrom", at = @At("TAIL"))
    private void keepRemainingXp(ServerPlayer oldPlayer, boolean keepEverything, CallbackInfo ci) {
        ServerPlayer self = (ServerPlayer) (Object) this;
        if (keepEverything || oldPlayer.isSpectator()) return;
        if(!ModHelper.isKeepInventory(self)) return;

        XpUtil.setTotalPoints(self, XpUtil.keptPoints(XpUtil.totalPoints(oldPlayer), oldPlayer.getRandom()));
    }
}