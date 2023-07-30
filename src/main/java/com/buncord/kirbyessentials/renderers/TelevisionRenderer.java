package com.buncord.kirbyessentials.renderers;

import com.buncord.kirbyessentials.entities.TelevisionEntity;
import com.buncord.kirbyessentials.paintings.ModPaintings;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.PaintingTextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class TelevisionRenderer extends EntityRenderer<TelevisionEntity> {

  public TelevisionRenderer(Context context) {
    super(context);
  }

  public void render(
      @NotNull TelevisionEntity entity,
      float direction,
      float partialTick,
      PoseStack poseStack,
      @NotNull MultiBufferSource mbs,
      int packedLight
  ) {
    poseStack.pushPose();
    poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - direction));

    int width = 3 * 16;
    int height = 2 * 16;

    float f = 0.0625F;

//    if(Constants.USERNAME_RYTHIAN.equals(Minecraft.getInstance().getUser().getName())) {
//      f /= 2;
//    }

    poseStack.scale(f, f, f);

    VertexConsumer vertexconsumer = mbs.getBuffer(
        RenderType.entitySolid(this.getTextureLocation(entity))
    );

    PaintingTextureManager paintingtexturemanager = Minecraft.getInstance().getPaintingTextures();

    Motive motive = ModPaintings.TELEVISION.get();

    if (entity.isOn() && entity.getMotive() != null) {
      motive = entity.getMotive();
    }

    TextureAtlasSprite televisionFrontSprite =  paintingtexturemanager.get(motive);

    TextureAtlasSprite televisionBackSprite = paintingtexturemanager.getBackSprite();

    this.renderPainting(
        poseStack,
        vertexconsumer,
        width,
        height,
        motive.getWidth(),
        motive.getHeight(),
        televisionFrontSprite,
        televisionBackSprite
    );

    poseStack.popPose();

    super.render(entity, direction, partialTick, poseStack, mbs, packedLight);
  }

  @Override
  public @NotNull ResourceLocation getTextureLocation(@NotNull TelevisionEntity entity) {
    return Minecraft.getInstance().getPaintingTextures().getBackSprite().atlas().location();
  }

  private void renderPainting(
      PoseStack poseStack,
      VertexConsumer vertexConsumer,
      int width,
      int height,
      int motiveWidth,
      int motiveHeight,
      TextureAtlasSprite televisionSprite,
      TextureAtlasSprite televisionBackSprite
  ) {
    PoseStack.Pose posestack$pose = poseStack.last();
    Matrix4f matrix4f = posestack$pose.pose();
    Matrix3f matrix3f = posestack$pose.normal();

    float f3 = televisionBackSprite.getU0();
    float f4 = televisionBackSprite.getU1();
    float f5 = televisionBackSprite.getV0();
    float f6 = televisionBackSprite.getV1();
    float f7 = televisionBackSprite.getU0();
    float f8 = televisionBackSprite.getU1();
    float f9 = televisionBackSprite.getV0();
    float f10 = televisionBackSprite.getV(1.0D);
    float f11 = televisionBackSprite.getU0();
    float f12 = televisionBackSprite.getU(1.0D);
    float f13 = televisionBackSprite.getV0();
    float f14 = televisionBackSprite.getV1();

    float f15 = (float)(width) / 2.0F;
    float f16 = (float)(-width) / 2.0F;
    float f17 = (float)(height) / 2.0F;
    float f18 = (float)(-height) / 2.0F;

    this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f18, f4, f5, -0.5F, 0, 0, -1, 0);
    this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f18, f3, f5, -0.5F, 0, 0, -1, 0);
    this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f17, f3, f6, -0.5F, 0, 0, -1, 0);
    this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f17, f4, f6, -0.5F, 0, 0, -1, 0);

    this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f17, f4, f5, 0.5F, 0, 0, 1, 0);
    this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f17, f3, f5, 0.5F, 0, 0, 1, 0);
    this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f18, f3, f6, 0.5F, 0, 0, 1, 0);
    this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f18, f4, f6, 0.5F, 0, 0, 1, 0);

    this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f17, f7, f9, -0.5F, 0, 1, 0, 0);
    this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f17, f8, f9, -0.5F, 0, 1, 0, 0);
    this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f17, f8, f10, 0.5F, 0, 1, 0, 0);
    this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f17, f7, f10, 0.5F, 0, 1, 0, 0);

    this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f18, f7, f9, 0.5F, 0, -1, 0, 0);
    this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f18, f8, f9, 0.5F, 0, -1, 0, 0);
    this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f18, f8, f10, -0.5F, 0, -1, 0, 0);
    this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f18, f7, f10, -0.5F, 0, -1, 0, 0);

    this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f17, f12, f13, 0.5F, -1, 0, 0, 0);
    this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f18, f12, f14, 0.5F, -1, 0, 0, 0);
    this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f18, f11, f14, -0.5F, -1, 0, 0, 0);
    this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f17, f11, f13, -0.5F, -1, 0, 0, 0);

    this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f17, f12, f13, -0.5F, 1, 0, 0, 0);
    this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f18, f12, f14, -0.5F, 1, 0, 0, 0);
    this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f18, f11, f14, 0.5F, 1, 0, 0, 0);
    this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f17, f11, f13, 0.5F, 1, 0, 0, 0);

    double rWidth = (double)width / (double)motiveWidth;
    double rHeight = (double)height / (double)motiveHeight;
    double rOverall = Math.min(rWidth, rHeight);

    float motiveF15 = (float)(motiveWidth * rOverall) / 2.0F;
    float motiveF16 = (float)(-motiveWidth * rOverall) / 2.0F;
    float motiveF17 = (float)(motiveHeight * rOverall) / 2.0F;
    float motiveF18 = (float)(-motiveHeight * rOverall) / 2.0F;

    float f19 = televisionSprite.getU1();
    float f20 = televisionSprite.getU0();
    float f21 = televisionSprite.getV1();
    float f22 = televisionSprite.getV0();

    this.vertex(matrix4f, matrix3f, vertexConsumer, motiveF15, motiveF18, f20, f21, -0.51F, 0, 0, -1, 1);
    this.vertex(matrix4f, matrix3f, vertexConsumer, motiveF16, motiveF18, f19, f21, -0.51F, 0, 0, -1, 1);
    this.vertex(matrix4f, matrix3f, vertexConsumer, motiveF16, motiveF17, f19, f22, -0.51F, 0, 0, -1, 1);
    this.vertex(matrix4f, matrix3f, vertexConsumer, motiveF15, motiveF17, f20, f22, -0.51F, 0, 0, -1, 1);
  }

  private void vertex(
      Matrix4f p_115537_,
      Matrix3f p_115538_,
      VertexConsumer p_115539_,
      float p_115540_,
      float p_115541_,
      float p_115542_,
      float p_115543_,
      float p_115544_,
      int p_115545_,
      int p_115546_,
      int p_115547_,
      int lightMultiplier
  ) {
    p_115539_.vertex(p_115537_, p_115540_, p_115541_, p_115544_)
             .color(
                 255 * lightMultiplier,
                 255 * lightMultiplier,
                 255 * lightMultiplier,
                 255 * lightMultiplier
             ).uv(p_115542_, p_115543_)
             .overlayCoords(OverlayTexture.NO_OVERLAY)
             .uv2(15728880 * lightMultiplier)
             .normal(p_115538_, (float)p_115545_, (float)p_115546_, (float)p_115547_)
             .endVertex();
  }

}
