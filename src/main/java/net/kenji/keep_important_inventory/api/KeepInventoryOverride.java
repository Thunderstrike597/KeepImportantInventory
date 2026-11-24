package net.kenji.keep_important_inventory.api;

public final class KeepInventoryOverride {
    private static final ThreadLocal<Boolean> ACTIVE = ThreadLocal.withInitial(() -> false);
    public static boolean isActive() { return ACTIVE.get(); }
    public static void enable()  { ACTIVE.set(true); }
    public static void disable() { ACTIVE.set(false); }
}