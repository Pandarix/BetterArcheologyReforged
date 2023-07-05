package net.Pandarix.betterarcheology.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.Pandarix.betterarcheology.BetterArcheology;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FossilInventoryScreen extends AbstractContainerScreen<FossilInventoryMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BetterArcheology.MOD_ID, "textures/gui/fossil_gui.png");

    public FossilInventoryScreen(FossilInventoryMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = 60;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - guiGraphics.guiWidth()) / 2;
        int y = (height - guiGraphics.guiHeight()) / 2;
        guiGraphics.blit(TEXTURE, x, y - 8, 0, 0, 176, 176);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}