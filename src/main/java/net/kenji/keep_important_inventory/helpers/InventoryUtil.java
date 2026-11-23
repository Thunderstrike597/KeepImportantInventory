package net.kenji.keep_important_inventory.helpers;

import net.kenji.keep_important_inventory.ConfigCommon;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;

import java.util.List;

public class InventoryUtil {

    public static final TagKey<Item> ALWAYS_KEEP =
            ItemTags.create(ResourceLocation.fromNamespaceAndPath("keep_important_inventory", "always_keep"));
    public static final TagKey<Item> ALWAYS_DROP =
            ItemTags.create(ResourceLocation.fromNamespaceAndPath("keep_important_inventory", "always_drop"));

    public static void dropItems(Player player, List<NonNullList<ItemStack>> compartments) {
        if(BossUtil.diedToBoss(player) && ConfigCommon.KEEP_BOSS_DEATH_ITEMS.get())return;
        RandomSource random = player.getRandom();
        for (NonNullList<ItemStack> list : compartments) {
            for (int i = 0; i < list.size(); i++) {
                ItemStack stack = list.get(i);
                if (stack.isEmpty() || isImportant(stack)) continue;

                int toDrop = amountToDrop(stack, random);
                if (toDrop <= 0) continue;

                ItemStack dropped = stack.split(toDrop); // keeps NBT, shrinks the slot's stack
                player.drop(dropped, true, false);
                if (stack.isEmpty()) list.set(i, ItemStack.EMPTY);
            }
        }
    }

    private static boolean isImportant(ItemStack stack) {
        if (stack.is(ALWAYS_DROP)) return false;
        if (stack.is(ALWAYS_KEEP)) return true;
        return stack.getItem() instanceof TieredItem
                || Equipable.get(stack) != null   // armour, elytra, shields, heads, pumpkins
                || stack.isDamageableItem();      // bows, tridents, shears, modded gear...
    }

    private static int amountToDrop(ItemStack stack, RandomSource random) {
        int count = stack.getCount();
        if (count <= ConfigCommon.MIN_ITEMS_COUNT.get()) return count;


        double exact = count * (!ConfigCommon.DROP_LOOT_RANGED.get() ? ConfigCommon.LOOT_DROPPED_PORTION.get() : Mth.randomBetween(random, (float) (double)ConfigCommon.MIN_LOOT_DROPPED_AMOUNT.get(), (float) (double)ConfigCommon.MAX_LOOT_DROPPED_AMOUNT.get()));
        int amount = (int) exact;
        if (random.nextDouble() < exact - amount) amount++; // random rounding
        return Math.min(amount, count);
    }
}