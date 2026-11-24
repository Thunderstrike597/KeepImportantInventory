package net.kenji.keep_important_inventory.mixins;

import net.kenji.keep_important_inventory.ConfigCommon;
import net.kenji.keep_important_inventory.api.interfaces.XpDeathData;
import net.kenji.keep_important_inventory.helpers.ModHelper;
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
public abstract class MixinPlayer implements XpDeathData {

    @Unique private int kii$droppedXp = -1;

    @Override public int kii$getDroppedXp() { return kii$droppedXp; }
    @Override public void kii$setDroppedXp(int value) { kii$droppedXp = value; }

    // ...dropEquipment inject unchanged...

    @Inject(method = "getExperienceReward", at = @At("RETURN"), cancellable = true)
    private void modifyExperienceDrop(CallbackInfoReturnable<Integer> cir) {
        Player self = (Player) (Object) this;
        if (self.isSpectator() || !ModHelper.isKeepImportantInventory(self)) return;

        int dropped = XpUtil.droppedPoints(XpUtil.totalPoints(self), self.getRandom());
        this.kii$droppedXp = dropped; // read back in restoreFrom
        cir.setReturnValue((int) Math.round(dropped * (1.0 - ConfigCommon.XP_LOST_AMOUNT.get())));
    }
}