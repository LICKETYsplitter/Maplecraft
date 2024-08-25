package net.licketysplitter.maplecraft.entity.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.entity.client.animation.DeerAntlers;
import net.licketysplitter.maplecraft.entity.custom.DeerEntity;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class DeerRenderer extends MobRenderer<DeerEntity, DeerModel> {
    private static final Map<DeerAntlers, ResourceLocation> LOCATION_BY_POINTS =
            Util.make(Maps.newEnumMap(DeerAntlers.class), map -> {
                map.put(DeerAntlers.ZERO,
                        ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "textures/entity/doe.png"));
                map.put(DeerAntlers.TWO,
                        ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "textures/entity/buck2.png"));
                map.put(DeerAntlers.FOUR,
                        ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "textures/entity/buck4.png"));
                map.put(DeerAntlers.SIX,
                        ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "textures/entity/buck6.png"));
                map.put(DeerAntlers.EIGHT,
                        ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "textures/entity/buck8.png"));
            });

    public DeerRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new DeerModel(pContext.bakeLayer(ModModelLayers.DEER)), 1f);
    }

    @Override
    public ResourceLocation getTextureLocation(DeerEntity deerEntity) {
        if(deerEntity.isBaby())
            return ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "textures/entity/fawn.png");
        if(deerEntity.getVariant() == DeerVariant.DOE)
            return ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "textures/entity/doe.png");
        return LOCATION_BY_POINTS.get(deerEntity.getPoints());
    }

    @Override
    public void render(DeerEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.getVariant() == DeerVariant.BUCK){
            pPoseStack.scale(0.75f, 0.75f, 0.75f);
        }
        else {
            pPoseStack.scale(0.65f, 0.65f, 0.65f);
        }
        if(pEntity.isBaby()){
            pPoseStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
