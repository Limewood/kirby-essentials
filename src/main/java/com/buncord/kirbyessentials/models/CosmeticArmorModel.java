package com.buncord.kirbyessentials.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
abstract class CosmeticArmorModel<T extends LivingEntity> extends HumanoidModel<T> {

  public CosmeticArmorModel(ModelPart modelPart) {
    super(modelPart);
  }

  @Override
  public void renderToBuffer(
      @NotNull PoseStack poseStack,
      @NotNull VertexConsumer vertexConsumer,
      int packedLight,
      int packedOverlay,
      float red,
      float green,
      float blue,
      float alpha
  ) {
    VertexConsumer texturedVertexConsumer = Minecraft.getInstance()
                                             .renderBuffers()
                                             .bufferSource()
                                             .getBuffer(
                                                 RenderType.entityTranslucent(this.getTexture())
                                             );

      this.headParts().forEach((modelRenderer) -> modelRenderer.render(
          poseStack,
          texturedVertexConsumer,
          packedLight,
          packedOverlay,
          red,
          green,
          blue,
          alpha
      ));

      this.bodyParts().forEach((modelRenderer) -> modelRenderer.render(
          poseStack,
          texturedVertexConsumer,
          packedLight,
          packedOverlay,
          red,
          green,
          blue,
          alpha
      ));
  }

  protected abstract ResourceLocation getTexture();

}
