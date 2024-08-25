package net.licketysplitter.maplecraft.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.licketysplitter.maplecraft.entity.client.animation.DeerAnimations;
import net.licketysplitter.maplecraft.entity.custom.DeerEntity;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class DeerModel extends HierarchicalModel<DeerEntity> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;

    public DeerModel(ModelPart pRoot) {
        this.root = pRoot;
        this.body = root.getChild("root");
        this.head = body.getChild("torso").getChild("neck");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition torso = root.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-4.001F, -5.001F, -13.999F, 8.0F, 10.0F, 28.0F, new CubeDeformation(-0.001F)), PartPose.offset(0.0F, -17.0F, 0.0F));

        PartDefinition neck = torso.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(24, 38).addBox(-2.0F, -10.0F, -6.0F, 4.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.6148F, -8.661F, 0.48F, 0.0F, 0.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 38).addBox(-3.0F, -6.1148F, -0.161F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(44, 38).addBox(-2.0F, -4.1148F, -5.161F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.8852F, -5.839F));

        PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(3.1138F, -4.0818F, 5.841F));

        PartDefinition left_ear_r1 = left_ear.addOrReplaceChild("left_ear_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.9992F, -4.2252F, -2.0012F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.1136F, 0.1932F, -0.0018F, 0.0F, 0.0F, 0.5236F));

        PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(-3.1138F, -4.0818F, 5.841F));

        PartDefinition right_ear_r1 = right_ear.addOrReplaceChild("right_ear_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.0008F, -4.2252F, -2.0012F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.1136F, 0.1932F, -0.0018F, 0.0F, 0.0F, -0.5236F));

        PartDefinition antler = head.addOrReplaceChild("antler", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition two = antler.addOrReplaceChild("two", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = two.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(4, 14).mirror().addBox(0.0F, -3.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, -5.0F, 5.0F, 0.1309F, 0.0F, -0.5236F));

        PartDefinition cube_r2 = two.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(4, 14).addBox(-1.0F, -3.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -5.0F, 5.0F, 0.1309F, 0.0F, 0.5236F));

        PartDefinition four = antler.addOrReplaceChild("four", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r3 = four.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 14).mirror().addBox(0.0F, -3.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.236F, -7.0843F, 4.5237F, -0.4345F, 0.0283F, -0.3958F));

        PartDefinition cube_r4 = four.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 14).mirror().addBox(0.0F, -3.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.4872F, -7.5758F, 4.6084F, 0.3897F, -0.05F, -0.4026F));

        PartDefinition cube_r5 = four.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -3.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.236F, -7.0843F, 4.5237F, -0.4345F, -0.0283F, 0.3958F));

        PartDefinition cube_r6 = four.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -3.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.4872F, -7.5758F, 4.6084F, 0.3897F, 0.05F, 0.4026F));

        PartDefinition six = antler.addOrReplaceChild("six", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r7 = six.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(0.0F, -3.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.3053F, -9.5511F, 3.5092F, -0.4477F, -0.0137F, -0.1431F));

        PartDefinition cube_r8 = six.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(0.0F, -3.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.522F, -10.1514F, 3.4702F, 0.3762F, -0.1153F, -0.2401F));

        PartDefinition cube_r9 = six.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(16, 14).addBox(-1.0F, -3.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3053F, -9.5511F, 3.5092F, -0.4477F, 0.0137F, 0.1431F));

        PartDefinition cube_r10 = six.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(16, 14).addBox(-1.0F, -3.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.522F, -10.1514F, 3.4702F, 0.3762F, 0.1153F, 0.2401F));

        PartDefinition eight = antler.addOrReplaceChild("eight", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r11 = eight.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(0.0F, -3.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.044F, -12.9719F, 2.3462F, 0.4293F, -0.1785F, -0.0278F));

        PartDefinition cube_r12 = eight.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(0.1998F, -4.2717F, -1.5136F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.3436F, -11.2017F, 2.4257F, -0.4016F, -0.0304F, 0.1255F));

        PartDefinition cube_r13 = eight.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -3.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.044F, -12.9719F, 2.3462F, 0.4293F, 0.1785F, 0.0278F));

        PartDefinition cube_r14 = eight.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 18).addBox(-1.1998F, -4.2717F, -1.5136F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.3436F, -11.2017F, 2.4257F, -0.4016F, 0.0304F, -0.1255F));

        PartDefinition tail = torso.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 14.1854F));

        PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(16, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.002F, 0.002F, -0.1874F, 0.4363F, 0.0F, 0.0F));

        PartDefinition right_shoulder = root.addOrReplaceChild("right_shoulder", CubeListBuilder.create().texOffs(44, 0).addBox(-2.0F, -3.5F, -3.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(56, 54).addBox(-2.0F, 2.5F, -1.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -12.5F, -9.0F));

        PartDefinition rs_lower = right_shoulder.addOrReplaceChild("rs_lower", CubeListBuilder.create().texOffs(16, 54).addBox(-1.999F, 0.001F, -0.001F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 6.5F, -1.0F));

        PartDefinition left_shoulder = root.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(44, 12).addBox(-2.0F, -3.5F, -3.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 60).addBox(-2.0F, 2.5F, -1.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -12.5F, -9.0F));

        PartDefinition ls_lower = left_shoulder.addOrReplaceChild("ls_lower", CubeListBuilder.create().texOffs(32, 54).addBox(-1.999F, 0.001F, -0.001F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 6.5F, -1.0F));

        PartDefinition right_haunch = root.addOrReplaceChild("right_haunch", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -4.0F, 4.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(58, 0).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(60, 8).addBox(-2.0F, 3.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -13.0F, 10.0F));

        PartDefinition rh_lower = right_haunch.addOrReplaceChild("rh_lower", CubeListBuilder.create().texOffs(44, 48).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 2.0F));

        PartDefinition left_haunch = root.addOrReplaceChild("left_haunch", CubeListBuilder.create().texOffs(0, 14).addBox(-2.0F, -3.0F, -4.0F, 4.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(58, 38).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(60, 20).addBox(-2.0F, 3.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -13.0F, 10.0F));

        PartDefinition lh_lower = left_haunch.addOrReplaceChild("lh_lower", CubeListBuilder.create().texOffs(0, 50).addBox(-1.999F, 0.001F, -4.001F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 7.0F, 2.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return root;
    }

    @Override
    public void setupAnim(DeerEntity deerEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch);

        AnimationDefinition walkOrRun;
        if(deerEntity.isSprinting()){
            walkOrRun = DeerAnimations.SPRINT;
        } else {
            walkOrRun = DeerAnimations.WALK;
        }

        this.animateWalk(walkOrRun, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(deerEntity.idleAnimationState, DeerAnimations.IDLE, ageInTicks, 1f);
        this.animate(deerEntity.rubAnimationState, DeerAnimations.RUB, ageInTicks, 1f);
        this.animate(deerEntity.grazeAnimationState, DeerAnimations.GRAZE, ageInTicks, 1f);
    }

    private void applyHeadRotation(float headYaw, float headPitch){
        headYaw = Mth.clamp(headYaw, 15f, 30f);
        headPitch = Mth.clamp(headPitch, -30f, 45f);

        this.head.xRot = headYaw * ((float)Math.PI / 180F);
        this.head.yRot = headPitch * ((float)Math.PI / 180F);
    }
}
