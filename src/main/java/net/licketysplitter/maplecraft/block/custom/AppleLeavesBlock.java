package net.licketysplitter.maplecraft.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;

public class AppleLeavesBlock extends LeavesBlock {
    private static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public AppleLeavesBlock(Properties p_54422_) {
        super(p_54422_);
        this.registerDefaultState(this.defaultBlockState().setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(AGE);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState pState) {
        return super.isRandomlyTicking(pState) || (!pState.getValue(PERSISTENT) && pState.getValue(AGE) != MAX_AGE);
    }

    @Override
    protected void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        super.randomTick(pState, pLevel, pPos, pRandom);
        double growthMultiplier = getGrowthMultiplier(pState, pLevel, pPos);
        if(growthMultiplier >= 1.0){
            if(pLevel.getRawBrightness(pPos.above(), 0) >= 9 &&
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int)(10)) == 0)){
                grow(pState, pLevel, pPos, pState.getValue(AGE));
            }
        }
    }

    private void grow(BlockState pState, ServerLevel pLevel, BlockPos pPos, int age){
        BlockState blockstate = pState.setValue(AGE, Integer.valueOf(age + 1));
        pLevel.setBlock(pPos, blockstate, 2);
        pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(blockstate));
        net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
    }

    private double getGrowthMultiplier(BlockState pstate, ServerLevel serverLevel, BlockPos blockPos){
        boolean exposedToAir = false;
        int sameStage = 0;
        int behind = 0;
        BlockPos.MutableBlockPos mutableblockpos = blockPos.mutable();
        for (Direction direction : Direction.values()){
            mutableblockpos.setWithOffset(blockPos, direction);
            BlockState blockstate = serverLevel.getBlockState(mutableblockpos);
            if(blockstate.is(Blocks.AIR)){
                exposedToAir = true;
            }
            else if (blockstate.is(this)){
                if(blockstate.getValue(AGE) >= pstate.getValue(AGE)){
                    sameStage++;
                }
                else{
                    behind++;
                }
            }
        }
        return exposedToAir ? (double) sameStage /(sameStage+behind) : -1.0;
    }
}
