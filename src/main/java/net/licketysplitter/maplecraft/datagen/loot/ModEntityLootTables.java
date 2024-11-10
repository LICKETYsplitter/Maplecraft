package net.licketysplitter.maplecraft.datagen.loot;

import net.licketysplitter.maplecraft.entity.ModEntities;
import net.licketysplitter.maplecraft.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.Stream;

public class ModEntityLootTables extends EntityLootSubProvider {

    public ModEntityLootTables(HolderLookup.Provider pRegistries) {
        super(FeatureFlags.REGISTRY.allFlags(), pRegistries);
    }

    @Override
    public void generate() {
        this.add(ModEntities.DEER.get(), createDeerLootTables(this));
        this.add(ModEntities.DEER.get(), ModBuiltInLootTables.DOE, createDeerLootTables(this));
        this.add(ModEntities.DEER.get(), ModBuiltInLootTables.BUCK, createDeerLootTables(this));
        this.add(ModEntities.DEER.get(), ModBuiltInLootTables.BUCK2, createDeerLootTables(ModItems.TWO_POINT_ANTLER.get(), this));
        this.add(ModEntities.DEER.get(), ModBuiltInLootTables.BUCK4, createDeerLootTables(ModItems.FOUR_POINT_ANTLER.get(), this));
        this.add(ModEntities.DEER.get(), ModBuiltInLootTables.BUCK6, createDeerLootTables(ModItems.SIX_POINT_ANTLER.get(), this));
        this.add(ModEntities.DEER.get(), ModBuiltInLootTables.BUCK8, createDeerLootTables(ModItems.EIGHT_POINT_ANTLER.get(), this));

        this.add(ModEntities.WILLIWAW.get(), createWilliwawLootTables(this));
    }
    protected static LootTable.Builder createDeerLootTables(ModEntityLootTables modEntityLootTables){
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ModItems.VENISON.get()).apply(SetItemCountFunction.setCount(UniformGenerator
                                        .between(1.0F, 2.0F))).apply(SmeltItemFunction.smelted().when(modEntityLootTables.shouldSmeltLoot()))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(modEntityLootTables.registries, UniformGenerator.between(0.0F, 1.0F)))));
    }

    protected static LootTable.Builder createDeerLootTables(ItemLike antler, ModEntityLootTables modEntityLootTables){
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(ModItems.VENISON.get()).apply(SetItemCountFunction.setCount(UniformGenerator
                        .between(1.0F, 2.0F))).apply(SmeltItemFunction.smelted().when(modEntityLootTables.shouldSmeltLoot()))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(modEntityLootTables.registries, UniformGenerator.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(antler)))
                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)));
    }

    protected static LootTable.Builder createWilliwawLootTables(ModEntityLootTables modEntityLootTables){
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                .add(LootItem.lootTableItem(ModItems.GREEN_APPLE.get()).apply(SetItemCountFunction.setCount(UniformGenerator
                        .between(1.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(modEntityLootTables.registries, UniformGenerator.between(0.0F, 1.0F)))));
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return ModEntities.ENTITY_TYPE.getEntries().stream().map(RegistryObject::get);
    }
}
