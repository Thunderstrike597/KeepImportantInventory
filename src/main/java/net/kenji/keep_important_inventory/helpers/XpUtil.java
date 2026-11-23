package net.kenji.keep_important_inventory.helpers;

import net.kenji.keep_important_inventory.ConfigCommon;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;

public final class XpUtil {
    private XpUtil() {}

    /** Total points needed to reach the start of a level. */
    public static int pointsForLevel(int l) {
        if (l <= 16) return l * l + 6 * l;
        if (l <= 31) return (int) (2.5 * l * l - 40.5 * l + 360);
        return (int) (4.5 * l * l - 162.5 * l + 2220);
    }

    /** Same as vanilla Player.getXpNeededForNextLevel(), but for any level. */
    public static int xpNeededForNextLevel(int level) {
        if (level >= 30) return 112 + (level - 30) * 9;
        if (level >= 15) return 37 + (level - 15) * 5;
        return 7 + level * 2;
    }

    /** The player's real XP total: full levels plus the partial bar. */
    public static int totalPoints(Player p) {
        return pointsForLevel(p.experienceLevel)
                + Math.round(p.experienceProgress * xpNeededForNextLevel(p.experienceLevel));
    }

    public static int droppedPoints(int total, RandomSource randomSource) {
        double value = Mth.randomBetween(randomSource, (float)(double)ConfigCommon.MIN_XP_DROPPED_AMOUNT.get(), (float)(double)ConfigCommon.MAX_XP_DROPPED_AMOUNT.get());
        if(!ConfigCommon.DROP_XP_RANGED.get())
            value = ConfigCommon.DROPPED_XP_AMOUNT.get();

        return (int) Math.round(total * value);

    }

    /** The part of the dropped XP that spawns as orbs. */
    public static int orbPoints(int total, RandomSource randomSource) {
        return (int) Math.round(droppedPoints(total, randomSource) * (1.0 - ConfigCommon.XP_LOST_AMOUNT.get()));
    }

    public static int keptPoints(int total, RandomSource randomSource) {
        return total - droppedPoints(total, randomSource);
    }

    /** Sets a player's XP to an exact point total. */
    public static void setTotalPoints(Player p, int points) {
        int level = 0;
        while (pointsForLevel(level + 1) <= points) level++;
        p.experienceLevel = level;
        p.experienceProgress = (float) (points - pointsForLevel(level)) / xpNeededForNextLevel(level);
        p.totalExperience = points;
    }
}