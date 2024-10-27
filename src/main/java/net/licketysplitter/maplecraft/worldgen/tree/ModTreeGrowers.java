package net.licketysplitter.maplecraft.worldgen.tree;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower RED_MAPLE = new TreeGrower(MaplecraftMod.MOD_ID + ":red_maple",
            0.1F, Optional.empty(), Optional.empty(),
            Optional.of(ModConfiguredFeatures.RED_MAPLE_KEY),
            Optional.of(ModConfiguredFeatures.FANCY_RED_MAPLE_KEY),
            Optional.of(ModConfiguredFeatures.RED_MAPLE_BEES_005_KEY),
            Optional.of(ModConfiguredFeatures.FANCY_RED_MAPLE_BEES_005_KEY));

    public static final TreeGrower SUGAR_MAPLE = new TreeGrower(MaplecraftMod.MOD_ID + ":sugar_maple",
            0.1F, Optional.empty(), Optional.empty(),
            Optional.of(ModConfiguredFeatures.SUGAR_MAPLE_KEY),
            Optional.of(ModConfiguredFeatures.FANCY_SUGAR_MAPLE_KEY),
            Optional.of(ModConfiguredFeatures.SUGAR_MAPLE_BEES_005_KEY),
            Optional.of(ModConfiguredFeatures.FANCY_SUGAR_MAPLE_BEES_005_KEY));
}
