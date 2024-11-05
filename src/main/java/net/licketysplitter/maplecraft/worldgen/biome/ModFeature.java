package net.licketysplitter.maplecraft.worldgen.biome;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFeature{
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(ForgeRegistries.FEATURES, MaplecraftMod.MOD_ID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> LEAF_COVER = FEATURES.register("leaf_cover",
            () -> new LeafCoverFeature(NoneFeatureConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
