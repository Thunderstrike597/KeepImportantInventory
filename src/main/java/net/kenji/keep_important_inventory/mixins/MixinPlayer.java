package net.kenji.keep_important_inventory.mixins;

import net.kenji.keep_important_inventory.ConfigCommon;
import net.kenji.keep_important_inventory.helpers.XpUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Player.class)
public abstract class MixinPlayer {


    @Shadow
    protected abstract void destroyVanishingCursedItems();

    @Shadow
    public int experienceLevel;

    @Inject(method = "dropEquipment", at = @At("HEAD"), cancellable = true)
    private void modifyDropAll(CallbackInfo ci){
        Player self = (Player) (Object)this;
        if (self.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            this.destroyVanishingCursedItems();
            self.getInventory().dropAll();
        }
    }
    @Inject(method = "getExperienceReward", at = @At("RETURN"), cancellable = true)
    private void modifyExperienceDrop(CallbackInfoReturnable<Integer> cir) {
        Player self = (Player) (Object) this;
        if (self.isSpectator()) return;
        cir.setReturnValue(XpUtil.orbPoints(XpUtil.totalPoints(self)));
    }

}
