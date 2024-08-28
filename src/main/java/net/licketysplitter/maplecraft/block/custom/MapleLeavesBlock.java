package net.licketysplitter.maplecraft.block.custom;


import net.licketysplitter.maplecraft.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MapleLeavesBlock extends LeavesBlock {
    private final Boolean RED;

    public MapleLeavesBlock(Properties p_273704_, boolean pRed) {
        super(p_273704_);
        this.RED = pRed;
    }


    public void animateTick(BlockState p_272714_, Level p_272837_, BlockPos p_273218_, RandomSource p_273360_) {
        super.animateTick(p_272714_, p_272837_, p_273218_, p_273360_);
        ParticleOptions particleOptions;
        if(RED)
            particleOptions = ModParticles.RED_MAPLE_PARTICLES.get();
        else
            particleOptions = ModParticles.SUGAR_MAPLE_PARTICLES.get();

        if (p_273360_.nextInt(10) == 0) {
            BlockPos $$4 = p_273218_.below();
            BlockState $$5 = p_272837_.getBlockState($$4);
            if (!isFaceFull($$5.getCollisionShape(p_272837_, $$4), Direction.UP)) {
                ParticleUtils.spawnParticleBelow(p_272837_, p_273218_, p_273360_, particleOptions);
            }
        }
    }


}
