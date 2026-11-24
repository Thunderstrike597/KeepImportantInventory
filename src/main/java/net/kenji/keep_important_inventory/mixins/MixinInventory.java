package net.kenji.keep_important_inventory.mixins;

import net.kenji.keep_important_inventory.helpers.InventoryUtil;
import net.kenji.keep_important_inventory.helpers.ModHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Inventory.class)
public abstract class MixinInventory {

    @Shadow
    @Final
    private List<NonNullList<ItemStack>> compartments;

    @Shadow
    @Final
    public Player player;

    @Inject(method = "dropAll", at = @At("HEAD"), cancellable = true)
    private void modifyDropAll(CallbackInfo ci){
        if (!(this.player instanceof ServerPlayer sp) || !ModHelper.isKeepImportantInventory(sp)) return;

        if(!ModHelper.isKeepImportantInventory(sp))
                return;

        ci.cancel();
        InventoryUtil.dropItems(player, this.compartments);
    }


}
