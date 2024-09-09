package net.licketysplitter.maplecraft.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.licketysplitter.maplecraft.MaplecraftMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class EvaporatorScreen extends AbstractContainerScreen<EvaporatorMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "textures/gui/evaporator_gui.png");

    public EvaporatorScreen(EvaporatorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelX += 12;
        this.inventoryLabelY += 2;
        this.titleLabelX += 52;
        this.titleLabelY -= 2;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderEmptyBucket(pGuiGraphics, x, y);
        renderBottleFill(pGuiGraphics, x, y);
        renderLitProgress(pGuiGraphics, x, y);
        for(int i = 0; i < 4; i++)
            renderPanProgress(pGuiGraphics, x, y, i);
    }

    private void renderEmptyBucket(GuiGraphics guiGraphics, int x, int y){
        if(menu.isBucketEmptying()){
            guiGraphics.blit(TEXTURE, x + 18, y + 37, 176, 14, 14, menu.getBucketScaledProgress());
        }
    }
    private void renderBottleFill(GuiGraphics guiGraphics, int x, int y){
        if(menu.isBottleFilling()){
            guiGraphics.blit(TEXTURE, x + 144, y + 37, 176, 14, 14, menu.getBottleScaledProgress());
        }
    }

    private void renderLitProgress(GuiGraphics guiGraphics, int x, int y){
        if(menu.isLit()){
            int progress = menu.getLitScaledProgress();
            guiGraphics.blit(TEXTURE, x + 80, y + 47 + progress, 176, progress, 14, 14 - progress);
        }
    }

    private void renderPanProgress(GuiGraphics guiGraphics, int x, int y, int panID){
        if(menu.panHasContents(panID)){
            int fullness = menu.getPanMB(panID);
            float progress = menu.getPanProgress(panID);

            RenderSystem.enableBlend();

            /*SYRUP*/
            guiGraphics.setColor(1.0f, 1.0f, 1.0f, progress);
            guiGraphics.blit(TEXTURE, x + 53 + (18 * panID), y + 14 + (31 - fullness),
                    176, 29, 16, fullness);

            /*SAP*/
            guiGraphics.setColor(1.0f, 1.0f, 1.0f, (1.0f - progress));
            guiGraphics.blit(TEXTURE, x + 53 + (18 * panID), y + 14 + (31 - fullness),
                    176, 60, 16, fullness);
            guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
