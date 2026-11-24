package net.kenji.keep_important_inventory.mixins;

import net.kenji.keep_important_inventory.api.KeepInventoryOverride;
import net.kenji.keep_important_inventory.helpers.ModHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    private static final String ON_LIVING_DROPS =
        "Lnet/minecraftforge/common/ForgeHooks;onLivingDrops(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/damagesource/DamageSource;Ljava/util/Collection;IZ)Z";

    @Inject(method = "dropAllDeathLoot", at = @At(value = "INVOKE", target = ON_LIVING_DROPS, remap = false))
    private void kii$beforeDrops(DamageSource source, CallbackInfo ci) {
        if ((Object) this instanceof ServerPlayer sp && ModHelper.isKeepImportantInventory(sp)) {
            KeepInventoryOverride.enable();
        }
    }

    @Inject(method = "dropAllDeathLoot", at = @At(value = "INVOKE", target = ON_LIVING_DROPS, remap = false, shift = At.Shift.AFTER))
    private void kii$afterDrops(DamageSource source, CallbackInfo ci) {
        KeepInventoryOverride.disable();
    }
}