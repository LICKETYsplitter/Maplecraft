package net.licketysplitter.maplecraft.worldgen.biome.surface;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource DIRT = makeRuleStates(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeRuleStates(Blocks.GRASS_BLOCK);

    public static SurfaceRules.RuleSource makeRules(){
        SurfaceRules.ConditionSource isAtOrAboveWater = SurfaceRules.waterBlockCheck(-1, 0);

        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWater, GRASS_BLOCK), DIRT);

        return SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface));
    }

    private static SurfaceRules.RuleSource makeRuleStates(Block block){
        return SurfaceRules.state(block.defaultBlockState());
    }
}
