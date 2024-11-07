package net.licketysplitter.maplecraft.datagen.loot;

import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.MAPLE_SYRUP_BLOCK.get());

        this.add(ModBlocks.SUGAR_GLASS.get(),
                block -> createSugarGlassDrop(ModBlocks.SUGAR_GLASS.get(), ModItems.SUGAR_GLASS_SHARD.get()));

        this.dropSelf(ModBlocks.MAPLE_LOG.get());
        this.dropSelf(ModBlocks.MAPLE_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_MAPLE_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_MAPLE_WOOD.get());
        this.dropSelf(ModBlocks.MAPLE_PLANKS.get());

        this.add(ModBlocks.SUGAR_MAPLE_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.SUGAR_MAPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(ModBlocks.RED_MAPLE_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.RED_MAPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        this.dropSelf(ModBlocks.MAPLE_STAIRS.get());
        this.dropSelf(ModBlocks.MAPLE_BUTTON.get());
        this.dropSelf(ModBlocks.MAPLE_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.MAPLE_TRAPDOOR.get());
        this.dropSelf(ModBlocks.MAPLE_FENCE.get());
        this.dropSelf(ModBlocks.MAPLE_FENCE_GATE.get());

        this.add(ModBlocks.MAPLE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.MAPLE_SLAB.get()));
        this.add(ModBlocks.MAPLE_DOOR.get(),
                block -> createDoorTable(ModBlocks.MAPLE_DOOR.get()));

        this.dropSelf(ModBlocks.RED_MAPLE_SAPLING.get());
        this.dropSelf(ModBlocks.SUGAR_MAPLE_SAPLING.get());

        this.dropSelf(ModBlocks.PILE_OF_LEAVES.get());
        this.dropSelf(ModBlocks.POISON_IVY.get());
        this.dropSelf(ModBlocks.EVAPORATOR.get());

        this.dropSelf(ModBlocks.ASTER.get());
        this.dropSelf(ModBlocks.CATTAIL.get());

        this.dropSelf(ModBlocks.APPLE_LOG.get());
        this.dropSelf(ModBlocks.APPLE_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_APPLE_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_APPLE_WOOD.get());
        this.dropSelf(ModBlocks.APPLE_PLANKS.get());
        this.dropSelf(ModBlocks.APPLE_STAIRS.get());
        this.dropSelf(ModBlocks.APPLE_BUTTON.get());
        this.dropSelf(ModBlocks.APPLE_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.APPLE_TRAPDOOR.get());
        this.dropSelf(ModBlocks.APPLE_FENCE.get());
        this.dropSelf(ModBlocks.APPLE_FENCE_GATE.get());
        this.add(ModBlocks.APPLE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.APPLE_SLAB.get()));
        this.add(ModBlocks.APPLE_DOOR.get(),
                block -> createDoorTable(ModBlocks.APPLE_DOOR.get()));
        this.add(ModBlocks.APPLE_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.APPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.dropSelf(ModBlocks.APPLE_SAPLING.get());

        this.dropSelf(ModBlocks.SINKING_MUD.get());
    }

    protected LootTable.Builder createSugarGlassDrop(Block pBlock, Item item) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                                .apply(LimitCount.limitCount(IntRange.range(3, 4)))));
    }
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
