package net.licketysplitter.maplecraft.worldgen.biome;

import com.mojang.serialization.Codec;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class CattailFeature extends Feature<NoneFeatureConfiguration> {
    public CattailFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
        WorldGenLevel worldgenlevel = pContext.level();
        BlockPos blockpos = pContext.origin();
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos mutableBlockPosBelow = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos mutableBlockPosAbove = new BlockPos.MutableBlockPos();

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int xPos = blockpos.getX() + i;
                int zPos = blockpos.getZ() + j;
                int i1 = worldgenlevel.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, xPos, zPos);
                mutableBlockPos.set(xPos, i1, zPos);
                mutableBlockPosBelow.set(mutableBlockPos).move(Direction.DOWN, 1);
                mutableBlockPosAbove.set(mutableBlockPos).move(Direction.UP, 1);

                BlockState blockState = worldgenlevel.getBlockState(mutableBlockPos);
                BlockState blockStateBelow = worldgenlevel.getBlockState(mutableBlockPosBelow);
                BlockState blockStateAbove = worldgenlevel.getBlockState(mutableBlockPosAbove);
                Random random = new Random();

                if(blockState.is(Blocks.AIR) && blockStateAbove.is(Blocks.AIR) &&
                        (blockStateBelow.is(Blocks.MUD) || blockStateBelow.is(ModBlocks.SINKING_MUD.get()))){
                    if(random.nextFloat() <= 0.7f ) {
                        worldgenlevel.setBlock(mutableBlockPos, ModBlocks.CATTAIL.get().defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), 2);
                        worldgenlevel.setBlock(mutableBlockPosAbove, ModBlocks.CATTAIL.get().defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), 2);
                    } else {
                        worldgenlevel.setBlock(mutableBlockPos, Blocks.TALL_GRASS.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), 2);
                        worldgenlevel.setBlock(mutableBlockPosAbove, Blocks.TALL_GRASS.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), 2);
                    }
                }
            }
        }
        return true;
    }
}
