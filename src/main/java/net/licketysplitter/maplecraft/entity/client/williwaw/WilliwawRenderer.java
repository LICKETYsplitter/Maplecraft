package net.licketysplitter.maplecraft.entity.client.williwaw;

import com.mojang.blaze3d.vertex.PoseStack;
import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.entity.custom.Williwaw;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WilliwawRenderer extends MobRenderer<Williwaw, WilliwawModel<Williwaw>> {
    private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "textures/entity/williwaw.png");

    public WilliwawRenderer(EntityRendererProvider.Context p_311628_) {
        super(p_311628_, new WilliwawModel<>(p_311628_.bakeLayer(ModelLayers.BREEZE)), 0.5F);
        this.addLayer(new WilliwawWindLayer(p_311628_, this));
        //this.addLayer(new BreezeEyesLayer(this));
    }

    public void render(Williwaw pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        WilliwawModel<Williwaw> williwawModel = this.getModel();
        enable(williwawModel, williwawModel.head(), williwawModel.rods());
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    public ResourceLocation getTextureLocation(Williwaw pEntity) {
        return TEXTURE_LOCATION;
    }

    public static WilliwawModel<Williwaw> enable(WilliwawModel<Williwaw> pModel, ModelPart... pParts) {
        pModel.head().visible = false;
        pModel.eyes().visible = false;
        pModel.rods().visible = false;
        pModel.wind().visible = false;

        for (ModelPart modelpart : pParts) {
            modelpart.visible = true;
        }

        return pModel;
    }
}
