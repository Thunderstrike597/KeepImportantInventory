package net.kenji.keep_important_inventory.mixins;

import net.kenji.keep_important_inventory.api.KeepInventoryOverride;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRules.class)
public abstract class MixinGameRules {
    @Inject(method = "getBoolean", at = @At("HEAD"), cancellable = true)
    private void kii$forceKeepInventory(GameRules.Key<GameRules.BooleanValue> key, CallbackInfoReturnable<Boolean> cir) {
        if (key == GameRules.RULE_KEEPINVENTORY && KeepInventoryOverride.isActive()) {
            cir.setReturnValue(true);
        }
    }
}