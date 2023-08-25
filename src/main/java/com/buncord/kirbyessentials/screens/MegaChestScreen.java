package com.buncord.kirbyessentials.screens;

import com.buncord.kirbyessentials.containers.MegaChestContainerMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class MegaChestScreen
    extends AbstractContainerScreen<MegaChestContainerMenu>
    implements MenuAccess<MegaChestContainerMenu>
{
  private static final ResourceLocation CONTAINER_BACKGROUND =
      new ResourceLocation("textures/gui/container/generic_54.png");

  private final int containerRows;

  public MegaChestScreen(
      MegaChestContainerMenu chestMenu,
      Inventory inventory,
      Component component
  ) {
    super(chestMenu, inventory, component);
    this.passEvents = false;

    this.containerRows = chestMenu.getRowCount();
    this.imageHeight = 114 + this.containerRows * 18;
    this.inventoryLabelY = this.imageHeight - 94;
  }

  public void render(
      @NotNull PoseStack poseStack,
      int mouseX,
      int mouseY,
      float partialTicks
  ) {
    this.renderBackground(poseStack);
    super.render(poseStack, mouseX, mouseY, partialTicks);
    this.renderTooltip(poseStack, mouseX, mouseY);
  }

  protected void renderBg(
      @NotNull PoseStack poseStack,
      float partialTicks,
      int mouseX,
      int mouseY
  ) {
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShaderTexture(0, CONTAINER_BACKGROUND);
    int i = (this.width - this.imageWidth) / 2;
    int j = (this.height - this.imageHeight) / 2;
    this.blit(poseStack, i, j, 0, 0, this.imageWidth, this.containerRows * 18 + 17);
    this.blit(poseStack, i, j + this.containerRows * 18 + 17, 0, 126, this.imageWidth, 96);
  }
}
