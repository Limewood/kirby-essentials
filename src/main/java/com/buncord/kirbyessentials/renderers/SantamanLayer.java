package com.buncord.kirbyessentials.renderers;

import com.buncord.kirbyessentials.models.SantamanHatModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class SantamanLayer<T extends LivingEntity, M extends EntityModel<T>>
    extends RenderLayer<T, M>
{
  private final SantamanHatModel<T> santamanHatModel;

  public SantamanLayer(
      RenderLayerParent<T, M> renderLayerParent,
      EntityModelSet entityModelSet
  ) {
    super(renderLayerParent);
    this.santamanHatModel = new SantamanHatModel<>(
        entityModelSet.bakeLayer(SantamanHatModel.LAYER_LOCATION)
    );
  }

  public void render(
      @NotNull PoseStack poseStack,
      @NotNull MultiBufferSource multiBufferSource,
      int packedLightCoords,
      @NotNull T entity,
      float animationPosition,
      float animationSpeed,
      float partialTick,
      float bob,
      float headRot,
      float xRot
  ) {
    poseStack.pushPose();

    // Adjust scale and translation for the final model
//    poseStack.scale(10F / 18F,  20F / 30F, 1.0F);
    poseStack.translate(0.0D, -0.9F, 0.0D);

    this.santamanHatModel.setupAnim(entity, animationPosition, animationSpeed, bob, headRot, xRot);

    VertexConsumer vertexconsumer = multiBufferSource.getBuffer(
        RenderType.armorCutoutNoCull(SantamanHatModel.TEXTURE)
    );

    this.santamanHatModel.renderToBuffer(
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
