package net.licketysplitter.maplecraft.worldgen.biome;


import net.licketysplitter.maplecraft.MaplecraftMod;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

public class ModTerrablender {
    public static void registerBiomes(){
        Regions.register(new ModOverworldRegions(ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "overworld"), 5));
    }
}
