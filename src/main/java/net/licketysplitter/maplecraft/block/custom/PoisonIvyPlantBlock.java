package net.licketysplitter.maplecraft.block.custom;


import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.effect.ModEffects;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.Tags;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PoisonIvyPlantBlock extends GrowingPlantBodyBlock {
    public static final MapCodec<PoisonIvyPlantBlock> CODEC = simpleCodec(PoisonIvyPlantBlock::new);
    private static final VoxelShape WEST_AABB = Block.box(0.0, 0.0, 3.0, 1.0, 16.0, 13.0);
    private static final VoxelShape EAST_AABB = Block.box(15.0, 0.0, 3.0, 16.0, 16.0, 13.0);
    private static final VoxelShape NORTH_AABB = Block.box(3.0, 0.0, 0.0, 13.0, 16.0, 1.0);
    private static final VoxelShape SOUTH_AABB = Block.box(3.0, 0.0, 15.0, 13.0, 16.0, 16.0);
    private final Map<BlockState, VoxelShape> shapesCache;

    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;
    public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION
            .entrySet()
            .stream()
            .filter(p_57886_ ->  p_57886_.getKey() != Direction.DOWN && p_57886_.getKey() != Direction.UP)
            .collect(Util.toMap());

    public PoisonIvyPlantBlock(BlockBehaviour.Properties properties) {
        super(properties, Direction.UP, NORTH_AABB, false);

        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(NORTH, false)
                        .setValue(EAST, false)
                        .setValue(SOUTH, false)
                        .setValue(WEST, false));
        this.shapesCache = ImmutableMap.copyOf(this.stateDefinition.getPossibleStates().stream().collect(Collectors.toMap(Function.identity(), PoisonIvyPlantBlock::calculateShape)));

    }

    private static VoxelShape calculateShape(BlockState p_57906_) {
        VoxelShape voxelshape = Shapes.empty();

        if (p_57906_.getValue(NORTH)) {
            voxelshape = Shapes.or(voxelshape, NORTH_AABB);
        }
        else if (p_57906_.getValue(SOUTH)) {
            voxelshape = Shapes.or(voxelshape, SOUTH_AABB);
        }
        else if (p_57906_.getValue(EAST)) {
            voxelshape = Shapes.or(voxelshape, EAST_AABB);
        }
        else if (p_57906_.getValue(WEST)) {
            voxelshape = Shapes.or(voxelshape, WEST_AABB);
        }

        return voxelshape.isEmpty() ? Shapes.block() : voxelshape;
    }

    private BlockState getUpdatedState(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.above();

        BlockState blockstate = null;

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BooleanProperty booleanproperty = getPropertyForFace(direction);
            if (pState.getValue(booleanproperty)) {
                boolean flag = this.canSupportAtFace(pLevel, pPos, direction);
                if (!flag) {
                    if (blockstate == null) {
                        blockstate = pLevel.getBlockState(blockpos);
                    }

                    flag = blockstate.is(this) && blockstate.getValue(booleanproperty);
                }

                pState = pState.setValue(booleanproperty, Boolean.valueOf(flag));
            }
        }

        return pState;
    }

    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockState blockState = this.getUpdatedState(pState, pLevel, pPos);
        BlockPos belowPos = pPos.below();
        BlockState belowState = pLevel.getBlockState(belowPos);
        return canSurviveOn(pLevel, belowPos, belowState ) && this.hasFaces(blockState);
    }

    private boolean canSurviveOn(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        return pState.isFaceSturdy(pLevel, pPos, Direction.UP) ||
                pState.is(ModBlocks.POISON_IVY.get()) || pState.is(ModBlocks.POISON_IVY_PLANT.get());
    }
    private boolean hasFaces(BlockState pState) {
        return this.countFaces(pState) > 0;
    }

    private int countFaces(BlockState pState) {
        int i = 0;

        for (BooleanProperty booleanproperty : PROPERTY_BY_DIRECTION.values()) {
            if (pState.getValue(booleanproperty)) {
                i++;
            }
        }

        return i;
    }

    public static BooleanProperty getPropertyForFace(Direction pFace) {
        return PROPERTY_BY_DIRECTION.get(pFace);
    }

    public static boolean isAcceptableNeighbour(BlockGetter pBlockReader, BlockPos pNeighborPos, Direction pAttachedFace) {
        return MultifaceBlock.canAttachTo(pBlockReader, pAttachedFace, pNeighborPos, pBlockReader.getBlockState(pNeighborPos));
    }

    private boolean canSupportAtFace(BlockGetter pLevel, BlockPos pPos, Direction pDirection) {
        if (pDirection == Direction.DOWN || pDirection == Direction.UP) {
            return false;
        } else {
            BlockPos blockpos = pPos.relative(pDirection);
            if (isAcceptableNeighbour(pLevel, blockpos, pDirection)) {
                return true;
            } else if (pDirection.getAxis() == Direction.Axis.Y) {
                return false;
            } else {
                BooleanProperty booleanproperty = PROPERTY_BY_DIRECTION.get(pDirection);
                BlockState blockstate = pLevel.getBlockState(pPos.above());
                return blockstate.is(this) && blockstate.getValue(booleanproperty);
            }
        }
    }

    @Override
    protected boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        BlockState blockstate = pUseContext.getLevel().getBlockState(pUseContext.getClickedPos());
        return blockstate.is(this) ? this.countFaces(blockstate) < PROPERTY_BY_DIRECTION.size() : super.canBeReplaced(pState, pUseContext);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(NORTH, EAST, SOUTH, WEST);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return this.shapesCache.get(pState);
    }


    @Override
    protected MapCodec<PoisonIvyPlantBlock> codec() {
        return CODEC;
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) ModBlocks.POISON_IVY.get();
    }

    @Override
    protected BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pFacing == Direction.UP || pFacing == Direction.DOWN) {
            return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        } else {
            BlockState blockstate = this.getUpdatedState(pState, pLevel, pCurrentPos);
            return !this.hasFaces(blockstate) ? Blocks.AIR.defaultBlockState() : blockstate;
        }
    }

    @Override
    protected BlockState updateHeadAfterConvertedFromBody(BlockState pHead, BlockState pBody) {
        return pBody.setValue(NORTH, pHead.getValue(NORTH))
                .setValue(SOUTH, pHead.getValue(SOUTH))
                .setValue(EAST, pHead.getValue(EAST))
                .setValue(WEST, pHead.getValue(WEST));
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if(!player.getMainHandItem().is(Tags.Items.SHEARS)) {
            if(player.hasEffect(ModEffects.ITCHY_EFFECT.getHolder().get())){
                player.addEffect(new MobEffectInstance(ModEffects.ITCHY_EFFECT.getHolder().get(),
                        Math.min((player.getEffect(ModEffects.ITCHY_EFFECT.getHolder().get()).getDuration() + 6000), 36000), 0));
            }
            else{
                player.addEffect(new MobEffectInstance(ModEffects.ITCHY_EFFECT.getHolder().get(), 6000, 0));
            }
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    protected void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if(pEntity instanceof Player){
            Player player = (Player) pEntity;
            player.addEffect(new MobEffectInstance(ModEffects.ITCHY_EFFECT.getHolder().get(), 36000, 0));
        }
    }
}
