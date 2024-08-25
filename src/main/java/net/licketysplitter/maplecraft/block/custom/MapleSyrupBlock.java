package net.licketysplitter.maplecraft.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.extensions.IForgeBlock;

public class MapleSyrupBlock extends HalfTransparentBlock implements IForgeBlock{

    public MapleSyrupBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isStickyBlock(BlockState state) {
        return true;
    }

    @Override
    public boolean canStickTo(BlockState state, BlockState other)
    {
        return !(other.getBlock() == Blocks.SLIME_BLOCK || other.getBlock() == Blocks.HONEY_BLOCK);
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        pEntity.playSound(SoundEvents.HONEY_BLOCK_SLIDE, 1.0F, 1.0F);
        pEntity.causeFallDamage(pFallDistance,0.0f, pLevel.damageSources().fall());
    }
}
