package net.licketysplitter.maplecraft.entity.client;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation DEER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "deer"), "main");
}
