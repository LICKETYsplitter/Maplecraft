package net.licketysplitter.maplecraft.worldgen.biome;

import com.mojang.serialization.Codec;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class LeafCoverFeature extends Feature<NoneFeatureConfiguration> {
    public LeafCoverFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
        WorldGenLevel worldgenlevel = pContext.level();
        BlockPos blockpos = pContext.origin();
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos mutableBlockPosBelow = new BlockPos.MutableBlockPos();

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int xPos = blockpos.getX() + i;
                int zPos = blockpos.getZ() + j;
                int i1 = worldgenlevel.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, xPos, zPos);
                mutableBlockPos.set(xPos, i1, zPos);
                mutableBlockPosBelow.set(mutableBlockPos).move(Direction.DOWN, 1);

                BlockState blockState = worldgenlevel.getBlockState(mutableBlockPos);
                BlockState blockStateBelow = worldgenlevel.getBlockState(mutableBlockPosBelow);

                if(blockState.is(Blocks.AIR) && blockStateBelow.is(Blocks.GRASS_BLOCK)){
                    worldgenlevel.setBlock(mutableBlockPos, ModBlocks.PILE_OF_LEAVES.get().defaultBlockState(), 2);
                }
            }
        }
        return true;
    }
}
