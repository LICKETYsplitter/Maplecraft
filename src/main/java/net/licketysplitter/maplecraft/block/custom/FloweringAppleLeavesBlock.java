package net.licketysplitter.maplecraft.block.custom;

import net.licketysplitter.maplecraft.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeHooks;

public class FloweringAppleLeavesBlock extends LeavesBlock implements BonemealableBlock {
    private static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public FloweringAppleLeavesBlock(Properties p_54422_, boolean inert) {
        super(p_54422_);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(AGE);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState pState) {
        return super.isRandomlyTicking(pState) ||
                (!pState.getValue(PERSISTENT) && pState.getValue(AGE) != MAX_AGE );
    }

    @Override
    protected void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if(!pState.getValue(PERSISTENT)) {
            super.randomTick(pState, pLevel, pPos, pRandom);
            double growthMultiplier = getGrowthMultiplier(pState, pLevel, pPos);
            if (growthMultiplier >= 1.0) {
                if (pLevel.getRawBrightness(pPos.above(), 0) >= 9 &&
                        ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int) (10)) == 0)) {
                    grow(pState, pLevel, pPos);
                }
            }
        }
    }

    private void grow(BlockState pState, ServerLevel pLevel, BlockPos pPos){
        BlockState blockstate = pState.setValue(AGE, Integer.valueOf(pState.getValue(AGE) + 1));
        pLevel.setBlock(pPos, blockstate, 2);
        pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(blockstate));
        ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
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

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if(pState.getValue(AGE) >= 2 && !pState.getValue(PERSISTENT)) {
            ItemLike apple;
            if (pState.getValue(AGE) == 2) {
                apple = ModItems.GREEN_APPLE.get();
            } else{
                apple = Items.APPLE;
            }
            popResource(pLevel, pPos, new ItemStack(apple, 1));
            pLevel.playSound(null, pPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);
            BlockState blockstate = pState.setValue(AGE, Integer.valueOf(0));
            pLevel.setBlock(pPos, blockstate, 2);
            pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pPlayer, blockstate));
            return InteractionResult.sidedSuccess(pLevel.isClientSide);

        }
        else{
            return super.useWithoutItem(pState, pLevel, pPos, pPlayer, pHitResult);
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        int i = pState.getValue(AGE);
        boolean flag = i == 3;
        return !flag && pStack.is(Items.BONE_MEAL)
                ? ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION
                : super.useItemOn(pStack, pState, pLevel, pPos, pPlayer, pHand, pHitResult);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        return pState.getValue(AGE) < 3 && pState.getValue(PERSISTENT);
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        grow(pState, pLevel, pPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.getStateForPlacement(pContext).setValue(AGE, 1);
    }
}
