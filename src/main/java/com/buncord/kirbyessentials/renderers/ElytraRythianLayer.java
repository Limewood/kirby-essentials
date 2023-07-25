package com.buncord.kirbyessentials.renderers;

import com.buncord.kirbyessentials.KirbyEssentials;
import com.buncord.kirbyessentials.items.ModItems;
import com.buncord.kirbyessentials.models.ElytraRythianModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ElytraRythianLayer<T extends LivingEntity, M extends EntityModel<T>>
    extends RenderLayer<T, M>
{
  private static final ResourceLocation WINGS_LOCATION =
      new ResourceLocation(KirbyEssentials.MOD_ID, "textures/entity/elytra_rythian.png");

  private final ElytraRythianModel<T> elytraModel;

  public ElytraRythianLayer(
      RenderLayerParent<T, M> renderLayerParent,
      EntityModelSet entityModelSet
  ) {
    super(renderLayerParent);
    this.elytraModel = new ElytraRythianModel<>(
        entityModelSet.bakeLayer(ElytraRythianModel.LAYER_LOCATION)
    );
  }

  public void render(
      @NotNull PoseStack poseStack,
      @NotNull MultiBufferSource multiBufferSource,
      int packedLightCoords,
      T entity,
      float animationPosition,
      float animationSpeed,
      float partialTick,
      float bob,
      float headRot,
      float xRot
  ) {
    ItemStack itemstack = entity.getItemBySlot(EquipmentSlot.CHEST);

    if (shouldRender(itemstack)) {
      ResourceLocation resourcelocation = getElytraTexture();

      poseStack.pushPose();
      poseStack.scale(10F / 19F,  20F / 30F, 1.0F);
      poseStack.translate(0.0D, 0.0D, 0.125D);

      this.getParentModel().copyPropertiesTo(this.elytraModel);

      this.elytraModel.setupAnim(entity, animationPosition, animationSpeed, bob, headRot, xRot);

      VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(
          multiBufferSource,
          RenderType.armorCutoutNoCull(resourcelocation),
          false,
          itemstack.hasFoil()
      );

      this.elytraModel.renderToBuffer(
          poseStack,
          vertexconsumer,
          packedLightCoords,
          OverlayTexture.NO_OVERLAY,
          1.0F,
          1.0F,
          1.0F,
          1.0F
      );

      poseStack.popPose();
    }
  }

  public boolean shouldRender(ItemStack stack) {
    return stack.getItem() == ModItems.ELYTRA_RYTHIAN.get();
  }

  public ResourceLocation getElytraTexture() {
    return WINGS_LOCATION;
  }

}
