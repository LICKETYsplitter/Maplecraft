package net.licketysplitter.maplecraft.block.custom;

import com.google.common.collect.ImmutableMap;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CattailBlock extends TallFlowerBlock {
    public static int MAX_CATTAILS = 4;
    public static IntegerProperty CATTAILS = IntegerProperty.create("cattails", 1, MAX_CATTAILS);
    public static DirectionProperty FACING = DirectionProperty.create("facing");
    private static final VoxelShape ONE_LOWER_AABB = Block.box(6.5, 0.0, 5.5, 10.5, 16.0, 9.5);
    private static final VoxelShape ONE_UPPER_AABB = Block.box(6.5, 0.0, 5.5, 10.5, 14.0, 9.5);
    private static final VoxelShape TWO_LOWER_AABB = Block.box(4.5, 0.0, 3.5, 12.5, 16.0, 11.5);
    private static final VoxelShape TWO_UPPER_AABB = Block.box(4.5, 0.0, 3.5, 12.5, 14.0, 11.5);
    private static final VoxelShape THREE_LOWER_AABB = Block.box(2.5, 0.0, 2.5, 13.5, 16.0, 13.5);
    private static final VoxelShape THREE_UPPER_AABB = Block.box(2.5, 0.0, 2.5, 13.5, 14.0, 13.5);
    private static final VoxelShape FOUR_LOWER_AABB = Block.box(2.5, 0.0, 2.5, 13.5, 16.0, 13.5);
    private static final VoxelShape FOUR_UPPER_AABB = Block.box(2.5, 0.0, 2.5, 13.5, 14.0, 13.5);
    private final Map<BlockState, VoxelShape> shapesCache;

    public CattailBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(CATTAILS, 1)
                .setValue(FACING, Direction.NORTH));
        this.shapesCache = ImmutableMap.copyOf(this.stateDefinition.getPossibleStates().stream().collect(Collectors.toMap(Function.identity(), CattailBlock::calculateShape)));
    }

    private static VoxelShape calculateShape(BlockState pState) {
        if(pState.getValue(HALF) == DoubleBlockHalf.LOWER){
            return switch (pState.getValue(CATTAILS)) {
                default -> ONE_LOWER_AABB;
                case 2 -> TWO_LOWER_AABB;
                case 3 -> THREE_LOWER_AABB;
                case 4 -> FOUR_LOWER_AABB;
            };
        }
        else{
            return switch (pState.getValue(CATTAILS)) {
                default -> ONE_UPPER_AABB;
                case 2 -> TWO_UPPER_AABB;
                case 3 -> THREE_UPPER_AABB;
                case 4 -> FOUR_UPPER_AABB;
            };
        }
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        if(blockState.getValue(CATTAILS) < MAX_CATTAILS) {
            if (blockState.getValue(HALF) == DoubleBlockHalf.LOWER) {
                level.setBlock(blockPos,
                        this.defaultBlockState()
                                .setValue(FACING, blockState.getValue(FACING))
                                .setValue(HALF, DoubleBlockHalf.LOWER)
                                .setValue(CATTAILS, Integer.valueOf(Math.min(MAX_CATTAILS, blockState.getValue(CATTAILS) + 1))), 3);
                level.setBlock(blockPos.above(),
                        this.defaultBlockState()
                                .setValue(FACING, blockState.getValue(FACING))
                                .setValue(HALF, DoubleBlockHalf.UPPER)
                                .setValue(CATTAILS, Integer.valueOf(Math.min(MAX_CATTAILS, blockState.getValue(CATTAILS) + 1))), 3);
            }
            else {
                level.setBlock(blockPos,
                        this.defaultBlockState()
                                .setValue(FACING, blockState.getValue(FACING))
                                .setValue(HALF, DoubleBlockHalf.UPPER)
                                .setValue(CATTAILS, Integer.valueOf(Math.min(MAX_CATTAILS, blockState.getValue(CATTAILS) + 1))), 3);
                level.setBlock(blockPos.below(),
                        this.defaultBlockState()
                                .setValue(FACING, blockState.getValue(FACING))
                                .setValue(HALF, DoubleBlockHalf.LOWER)
                                .setValue(CATTAILS, Integer.valueOf(Math.min(MAX_CATTAILS, blockState.getValue(CATTAILS) + 1))), 3);
            }
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos());
        if (blockstate.is(this)) {
            return blockstate.setValue(CATTAILS, Integer.valueOf(Math.min(MAX_CATTAILS, blockstate.getValue(CATTAILS) + 1)));
        }
        BlockPos blockpos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        return blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(pContext) ?
                this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()) : null;
    }

    @Override
    protected BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        BlockState otherHalf = pLevel.getBlockState(pState.getValue(HALF) == DoubleBlockHalf.UPPER ? pCurrentPos.below() : pCurrentPos.above());
        if(otherHalf.is(this)) {
            if (otherHalf.getValue(CATTAILS) != pState.getValue(CATTAILS))
                return pState.setValue(CATTAILS, otherHalf.getValue(CATTAILS));
        }

        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    protected boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return !pUseContext.isSecondaryUseActive() &&
                pUseContext.getItemInHand().is(this.asItem())
                && pState.getValue(CATTAILS) < 4 ||
                super.canBeReplaced(pState, pUseContext);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        if (pState.is(this)){
            if(pState.getValue(HALF) == DoubleBlockHalf.UPPER)
                return;
        }
        BlockPos blockpos = pPos.above();
        pLevel.setBlock(blockpos,
                copyWaterloggedFrom(pLevel, blockpos,
                        this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER)).setValue(FACING, pState.getValue(FACING)), 3);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Vec3 vec3 = pState.getOffset(pLevel, pPos);
        return this.shapesCache.get(pState).move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if(pState.getValue(HALF) == DoubleBlockHalf.LOWER){
            BlockState soil = pLevel.getBlockState(pPos.below());
            return canSustainPlant(soil, pLevel, pPos.below(), Direction.UP, this);
        }
        else{
            BlockState lower = pLevel.getBlockState(pPos.below());
            if (pState.getBlock() != this) return super.canSurvive(pState, pLevel, pPos); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            return lower.is(this) && lower.getValue(HALF) == DoubleBlockHalf.LOWER;
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
        return state.is(Blocks.MUD) || state.is(ModBlocks.SINKING_MUD.get()) || (hasWater && super.canSustainPlant(state, world, pos, facing, plantable));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(CATTAILS, FACING);
        pBuilder.add(HALF);
    }
}