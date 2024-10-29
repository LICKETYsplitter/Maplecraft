package net.licketysplitter.maplecraft.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class CattailBlock extends TallFlowerBlock {
    public CattailBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if(pState.getValue(HALF) == DoubleBlockHalf.LOWER){
            BlockState soil = pLevel.getBlockState(pPos.below());
            return canSustainPlant(soil, pLevel, pPos.below(), Direction.UP, this);
        }
        else{
            BlockState blockstate = pLevel.getBlockState(pPos.below());
            if (pState.getBlock() != this) return super.canSurvive(pState, pLevel, pPos); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            return blockstate.is(this) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        boolean hasWater = false;
        for (Direction face : Direction.Plane.HORIZONTAL) {
            BlockState adjacentBlockState = world.getBlockState(pos.relative(face));
            var adjacentFluidState = world.getFluidState(pos.relative(face));
            hasWater = hasWater || adjacentBlockState.is(Blocks.FROSTED_ICE) || adjacentFluidState.is(net.minecraft.tags.FluidTags.WATER);
            if (hasWater) {
                break; //No point continuing.
            }
        }
        return state.is(Blocks.MUD) || (hasWater && super.canSustainPlant(state, world, pos, facing, plantable));
    }
}
