package net.licketysplitter.maplecraft.datagen;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.worldgen.ModBiomeModifiers;
import net.licketysplitter.maplecraft.worldgen.ModConfiguredFeatures;
import net.licketysplitter.maplecraft.worldgen.ModPlacedFeatures;
import net.licketysplitter.maplecraft.worldgen.biome.ModBiomes;
import net.licketysplitter.maplecraft.worldgen.biome.ModFeature;
import net.licketysplitter.maplecraft.worldgen.tree.ModTreePlacements;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap)
            .add(Registries.BIOME, ModBiomes::bootstrap);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(MaplecraftMod.MOD_ID));
    }
}
