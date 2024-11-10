package net.licketysplitter.maplecraft.entity.client.williwaw;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.entity.custom.Williwaw;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WilliwawWindLayer extends RenderLayer<Williwaw, WilliwawModel<Williwaw>> {
    private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "textures/entity/williwaw_wind.png");
    private final WilliwawModel<Williwaw> model;

    public WilliwawWindLayer(EntityRendererProvider.Context pContext, RenderLayerParent<Williwaw, WilliwawModel<Williwaw>> pRenderer) {
        super(pRenderer);
        this.model = new WilliwawModel<>(pContext.bakeLayer(ModelLayers.BREEZE_WIND));
    }

    public void render(
            PoseStack pPoseStack,
            MultiBufferSource pBufferSource,
            int pPackedLight,
            Williwaw pLivingEntity,
            float pLimbSwing,
            float pLimbSwingAmount,
            float pPartialTick,
            float pAgeInTicks,
            float pNetHeadYaw,
            float pHeadPitch
    ) {
        float f = (float)pLivingEntity.tickCount + pPartialTick;
        VertexConsumer vertexconsumer = pBufferSource.getBuffer(RenderType.breezeWind(TEXTURE_LOCATION, this.xOffset(f) % 1.0F, 0.0F));
        this.model.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        WilliwawRenderer.enable(this.model, this.model.wind()).renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY);
    }

    private float xOffset(float pTickCount) {
        return pTickCount * 0.015F;
    }
}
