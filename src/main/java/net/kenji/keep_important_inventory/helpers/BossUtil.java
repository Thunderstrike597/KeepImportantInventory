package net.kenji.keep_important_inventory.helpers;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.Tags;

public final class BossUtil {
    private BossUtil() {}

    public static boolean isBoss(Entity e) {
        if (e == null) return false;
        if (e instanceof EnderDragonPart part) e = part.parentMob; // the dragon is several part entities
        return e.getType().is(Tags.EntityTypes.BOSSES);
    }

    public static boolean diedToBoss(Player player) {
        DamageSource source = player.getLastDamageSource();
        if (source != null && (isBoss(source.getEntity()) || isBoss(source.getDirectEntity()))) return true;

        // Indirect deaths, e.g. the Wither knocks you into lava or off a cliff
        LivingEntity credit = player.getKillCredit();
        return isBoss(credit);
    }
}