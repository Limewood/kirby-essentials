package com.buncord.kirbyessentials.screens;

import com.buncord.kirbyessentials.KirbyEssentials;
import com.buncord.kirbyessentials.containers.MegaChestContainerMenu;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class MegaChestScreen
    extends AbstractContainerScreen<MegaChestContainerMenu>
{
  private static final ResourceLocation CONTAINER_BACKGROUND =
      new ResourceLocation(KirbyEssentials.MOD_ID, "textures/gui/container/mega_chest.png");

  // -

  private float scrollOffs;
  private boolean scrolling;
  private EditBox searchBox;
  private boolean ignoreTextInput;

  public MegaChestScreen(
      MegaChestContainerMenu chestMenu,
      Inventory inventory,
      Component component
  ) {
    super(chestMenu, inventory, component);
    this.passEvents = false;

    this.imageHeight = 114 + chestMenu.getRowCount() * 18;
    this.imageWidth = 195;
    this.inventoryLabelY = this.imageHeight - 94;
  }

  public void containerTick() {
    super.containerTick();

    if (this.searchBox != null) {
      this.searchBox.tick();
    }
  }

  protected void slotClicked(
      @Nullable Slot p_98556_,
      int p_98557_,
      int p_98558_,
      @NotNull ClickType p_98559_
  ) {
    this.searchBox.moveCursorToEnd();
    this.searchBox.setHighlightPos(0);

    super.slotClicked(p_98556_, p_98557_, p_98558_, p_98559_);
  }

  protected void init() {
    super.init();

    if (this.minecraft != null) {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
    }

    this.searchBox = new EditBox(
        this.font,
        this.leftPos + 82,
        this.topPos + 6,
        80,
        9,
        new TranslatableComponent("itemGroup.search")
    );
    this.searchBox.setMaxLength(50);
    this.searchBox.setBordered(false);
    this.searchBox.setTextColor(16777215);
    this.searchBox.setVisible(true);
    this.searchBox.setCanLoseFocus(false);
    this.searchBox.setFocus(true);
    this.searchBox.setValue("");
    this.searchBox.setWidth(89);
    this.searchBox.x = this.leftPos + 82 + 89 - this.searchBox.getWidth();

    this.addWidget(this.searchBox);

    this.refreshSearchResults();
  }

  public void resize(@NotNull Minecraft p_98595_, int p_98596_, int p_98597_) {
    String s = this.searchBox.getValue();
    this.init(p_98595_, p_98596_, p_98597_);
    this.searchBox.setValue(s);
    if (!this.searchBox.getValue().isEmpty()) {
      this.refreshSearchResults();
    }
  }

  public void removed() {
    super.removed();

    if (this.minecraft != null) {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }
  }

  public boolean charTyped(char p_98521_, int p_98522_) {
    if (this.ignoreTextInput) {
      return false;
    } else {
      String s = this.searchBox.getValue();
      if (this.searchBox.charTyped(p_98521_, p_98522_)) {
        if (!Objects.equals(s, this.searchBox.getValue())) {
          this.refreshSearchResults();
        }

        return true;
      } else {
        return false;
      }
    }
  }

  public boolean keyPressed(int p_98547_, int p_98548_, int p_98549_) {
    this.ignoreTextInput = false;

    boolean flag = this.hoveredSlot != null && this.hoveredSlot.hasItem();
    boolean flag1 = InputConstants.getKey(p_98547_, p_98548_).getNumericKeyValue().isPresent();
    if (flag && flag1 && this.checkHotbarKeyPressed(p_98547_, p_98548_)) {
      this.ignoreTextInput = true;
      return true;
    } else {
      String s = this.searchBox.getValue();
      if (this.searchBox.keyPressed(p_98547_, p_98548_, p_98549_)) {
        if (!Objects.equals(s, this.searchBox.getValue())) {
          this.refreshSearchResults();
        }

        return true;
      } else {
        return this.searchBox.isFocused() && p_98547_ != 256 || super.keyPressed(
            p_98547_,
            p_98548_,
            p_98549_
        );
      }
    }
  }

  public boolean keyReleased(int p_98612_, int p_98613_, int p_98614_) {
    this.ignoreTextInput = false;
    return super.keyReleased(p_98612_, p_98613_, p_98614_);
  }

  private void refreshSearchResults() {
    this.scrollOffs = 0.0F;
    menu.update(this.scrollOffs, this.searchBox.getValue());
  }

  protected void renderLabels(@NotNull PoseStack p_98616_, int p_98617_, int p_98618_) {
    RenderSystem.disableBlend();
    this.font.draw(
        p_98616_,
        new TranslatableComponent("block.kirbyessentials.mega_chest"),
        8.0F,
        6.0F,
        4210752
    );
  }

  public boolean mouseClicked(double p_98531_, double p_98532_, int p_98533_) {
    if (p_98533_ == 0) {
      if (this.insideScrollbar(p_98531_, p_98532_)) {
        this.scrolling = this.canScroll();
        return true;
      }
    }

    return super.mouseClicked(p_98531_, p_98532_, p_98533_);
  }

  public boolean mouseReleased(double p_98622_, double p_98623_, int p_98624_) {
    if (p_98624_ == 0) {
      this.scrolling = false;
    }

    return super.mouseReleased(p_98622_, p_98623_, p_98624_);
  }

  private boolean canScroll() {
    return this.menu.canScroll();
  }

  public boolean mouseScrolled(double p_98527_, double p_98528_, double p_98529_) {
    if (!this.canScroll()) {
      return false;
    } else {
      int i = (this.menu.activeSlotCount + 9 - 1) / 9 - 6;
      this.scrollOffs = (float)((double)this.scrollOffs - p_98529_ / (double)i);
      this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
      this.menu.update(this.scrollOffs, this.searchBox.getValue());
      return true;
    }
  }

  protected boolean hasClickedOutside(
      double p_98541_,
      double p_98542_,
      int p_98543_,
      int p_98544_,
      int p_98545_
  ) {
    return p_98541_ < (double)p_98543_ ||
        p_98542_ < (double)p_98544_ ||
        p_98541_ >= (double)(p_98543_ + this.imageWidth) ||
        p_98542_ >= (double)(p_98544_ + this.imageHeight);
  }

  protected boolean insideScrollbar(double p_98524_, double p_98525_) {
    int i = this.leftPos;
    int j = this.topPos;
    int k = i + 175;
    int l = j + 18;
    int i1 = k + 14;
    int j1 = l + 198;
    return p_98524_ >= (double)k &&
        p_98525_ >= (double)l &&
        p_98524_ < (double)i1 && p_98525_ < (double)j1;
  }

  public boolean mouseDragged(
      double p_98535_,
      double p_98536_,
      int p_98537_,
      double p_98538_,
      double p_98539_
  ) {
    if (this.scrolling) {
      int i = this.topPos + 18;
      int j = i + 198;
      this.scrollOffs = ((float)p_98536_ - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
      this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
      this.menu.update(this.scrollOffs, this.searchBox.getValue());
      return true;
    } else {
      return super.mouseDragged(p_98535_, p_98536_, p_98537_, p_98538_, p_98539_);
    }
  }

  public void render(@NotNull PoseStack p_98577_, int p_98578_, int p_98579_, float p_98580_) {
    this.renderBackground(p_98577_);
    super.render(p_98577_, p_98578_, p_98579_, p_98580_);

    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    this.renderTooltip(p_98577_, p_98578_, p_98579_);
  }

  protected void renderTooltip(
      @NotNull PoseStack p_98590_,
      @NotNull ItemStack p_98591_,
      int p_98592_,
      int p_98593_
  ) {
    if (this.minecraft == null) {
      return;
    }

    List<Component> list = p_98591_.getTooltipLines(
        this.minecraft.player,
        this.minecraft.options.advancedItemTooltips
          ? TooltipFlag.Default.ADVANCED
          : TooltipFlag.Default.NORMAL
    );

    this.renderTooltip(p_98590_, list, p_98591_.getTooltipImage(), p_98592_, p_98593_, p_98591_);
  }

  protected void renderBg(@NotNull PoseStack p_98572_, float p_98573_, int p_98574_, int p_98575_) {
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderTexture(0, CONTAINER_BACKGROUND);

    this.blit(
        p_98572_,
        this.leftPos,
        this.topPos,
        0,
        0,
        this.imageWidth,
        this.imageHeight
    );

    int i = this.leftPos + 175;
    int j = this.topPos + 18;
    int k = j + 198;

    this.blit(
        p_98572_,
        i,
        j + (int)((float)(k - j - 17) * this.scrollOffs),
        232 + (this.canScroll() ? 0 : 12),
        0,
        12,
        15
    );

    this.searchBox.render(p_98572_, p_98574_, p_98575_, p_98573_);
  }

}
