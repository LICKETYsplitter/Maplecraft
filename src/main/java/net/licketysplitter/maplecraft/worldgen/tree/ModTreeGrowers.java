package net.licketysplitter.maplecraft.worldgen.tree;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower RED_MAPLE = new TreeGrower(MaplecraftMod.MOD_ID + ":red_maple",
            Optional.empty(), Optional.of(ModConfiguredFeatures.RED_MAPLE_KEY), Optional.empty());
    public static final TreeGrower SUGAR_MAPLE = new TreeGrower(MaplecraftMod.MOD_ID + ":sugar_maple",
            Optional.empty(), Optional.of(ModConfiguredFeatures.SUGAR_MAPLE_KEY), Optional.empty());
}
