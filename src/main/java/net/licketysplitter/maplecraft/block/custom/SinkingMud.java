package net.licketysplitter.maplecraft.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MudBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SinkingMud extends MudBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
    public SinkingMud(Properties p_221545_) {
        super(p_221545_);
    }

    @Override
    protected void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (!(pEntity instanceof LivingEntity) || pEntity.getInBlockState().is(this)) {
            pEntity.makeStuckInBlock(pState, new Vec3(0.9F, 1.5, 0.9F));
            if (pLevel.isClientSide) {
                RandomSource randomsource = pLevel.getRandom();
                boolean flag = pEntity.xOld != pEntity.getX() || pEntity.zOld != pEntity.getZ();
                if (flag && randomsource.nextBoolean()) {
                    pLevel.addParticle(
                            ParticleTypes.MYCELIUM,
                            pEntity.getX(),
                            (double)(pPos.getY() + 1),
                            pEntity.getZ(),
                            (double)(Mth.randomBetween(randomsource, -1.0F, 1.0F) * 0.083333336F),
                            0.05F,
                            (double)(Mth.randomBetween(randomsource, -1.0F, 1.0F) * 0.083333336F)
                    );
                }
            }
        }
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState p_221561_, BlockGetter p_221562_, BlockPos p_221563_, CollisionContext p_221564_) {
        return SHAPE;
    }
}
