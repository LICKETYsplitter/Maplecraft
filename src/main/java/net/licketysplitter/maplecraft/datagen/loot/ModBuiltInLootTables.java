package net.licketysplitter.maplecraft.datagen.loot;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ModBuiltInLootTables {
    private static final Set<ResourceKey<LootTable>> LOCATIONS = new HashSet();
    private static final Set<ResourceKey<LootTable>> IMMUTABLE_LOCATIONS;
    public static final ResourceKey<LootTable> EMPTY;
    public static final ResourceKey<LootTable> DOE;
    public static final ResourceKey<LootTable> BUCK;
    public static final ResourceKey<LootTable> BUCK2;
    public static final ResourceKey<LootTable> BUCK4;
    public static final ResourceKey<LootTable> BUCK6;
    public static final ResourceKey<LootTable> BUCK8;

    public ModBuiltInLootTables() {
    }

    private static ResourceKey<LootTable> register(String pName) {
        return register(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, pName)));
    }

    private static ResourceKey<LootTable> register(ResourceKey<LootTable> pName) {
        if (LOCATIONS.add(pName)) {
            return pName;
        } else {
            throw new IllegalArgumentException(String.valueOf(pName.location()) + " is already a registered mod built-in loot table");
        }
    }

    public static Set<ResourceKey<LootTable>> all() {
        return IMMUTABLE_LOCATIONS;
    }

    static {
        IMMUTABLE_LOCATIONS = Collections.unmodifiableSet(LOCATIONS);
        EMPTY = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "empty"));
        DOE = register("entities/deer/doe");
        BUCK = register("entities/deer/buck");
        BUCK2 = register("entities/deer/buck2");
        BUCK4 = register("entities/deer/buck4");
        BUCK6 = register("entities/deer/buck6");
        BUCK8 = register("entities/deer/buck8");
    }
}