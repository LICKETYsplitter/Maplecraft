package net.licketysplitter.maplecraft.datagen;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MaplecraftMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_HOE)
                .add(ModBlocks.PILE_OF_LEAVES.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.SUGAR_GLASS.get());

        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.MAPLE_LOG.get())
                .add(ModBlocks.MAPLE_WOOD.get())
                .add(ModBlocks.STRIPPED_MAPLE_LOG.get())
                .add(ModBlocks.STRIPPED_MAPLE_WOOD.get())

                .add(ModBlocks.APPLE_LOG.get())
                .add(ModBlocks.APPLE_WOOD.get())
                .add(ModBlocks.STRIPPED_APPLE_LOG.get())
                .add(ModBlocks.STRIPPED_APPLE_WOOD.get());

        this.tag(BlockTags.PLANKS)
                .add(ModBlocks.MAPLE_PLANKS.get())
                .add(ModBlocks.APPLE_PLANKS.get());

        this.tag(BlockTags.FENCES)
                .add(ModBlocks.MAPLE_FENCE.get())
                .add(ModBlocks.APPLE_FENCE.get());
        this.tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.MAPLE_FENCE_GATE.get())
                .add(ModBlocks.APPLE_FENCE_GATE.get());

        this.tag(ModTags.Blocks.MAPLE_LOGS)
                .add(ModBlocks.MAPLE_LOG.get())
                .add(ModBlocks.MAPLE_WOOD.get())
                .add(ModBlocks.STRIPPED_MAPLE_LOG.get())
                .add(ModBlocks.STRIPPED_MAPLE_WOOD.get());

        this.tag(ModTags.Blocks.APPLE_LOGS)
                .add(ModBlocks.APPLE_LOG.get())
                .add(ModBlocks.APPLE_WOOD.get())
                .add(ModBlocks.STRIPPED_APPLE_LOG.get())
                .add(ModBlocks.STRIPPED_APPLE_WOOD.get());
    }
}
