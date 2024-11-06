package net.licketysplitter.maplecraft.worldgen.tree;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.worldgen.tree.custom.AppleTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTrunkPlacerTypes {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, MaplecraftMod.MOD_ID);

    public static final RegistryObject<TrunkPlacerType<AppleTrunkPlacer>> APPLE_TRUNK_PLACER =
            TRUNK_PLACER.register("apple_trunk_placer", () -> new TrunkPlacerType<>(AppleTrunkPlacer.CODEC));

    public static void register(IEventBus eventBus){
        TRUNK_PLACER.register(eventBus);
    }
}
