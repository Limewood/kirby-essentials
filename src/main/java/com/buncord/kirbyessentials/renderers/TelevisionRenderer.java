package com.buncord.kirbyessentials.renderers;

import com.buncord.kirbyessentials.Constants;
import com.buncord.kirbyessentials.KirbyEssentials;
import com.buncord.kirbyessentials.entities.TelevisionEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class TelevisionRenderer extends EntityRenderer<TelevisionEntity> {

  public static final String TELEVISION_SPRITE_PATH = "entities/television";

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

    if(Constants.USERNAME_RYTHIAN.equals(Minecraft.getInstance().getUser().getName())) {
      f /= 2;
    }

    poseStack.scale(f, f, f);
    VertexConsumer vertexconsumer = mbs.getBuffer(
        RenderType.entitySolid(this.getTextureLocation(entity))
    );

    TextureAtlas textureAtlas = Minecraft.getInstance()
                                         .getModelManager()
                                         .getAtlas(InventoryMenu.BLOCK_ATLAS);

    TextureAtlasSprite televisionSprite = textureAtlas.getSprite(
        new ResourceLocation(KirbyEssentials.MOD_ID, TELEVISION_SPRITE_PATH)
    );
    TextureAtlasSprite televisionBackSprite = textureAtlas.getSprite(
        new ResourceLocation("block/black_concrete")
    );

    this.renderPainting(
        poseStack,
        vertexconsumer,
        entity,
        width,
        height,
        televisionSprite,
        televisionBackSprite
    );
    poseStack.popPose();
    super.render(entity, direction, partialTick, poseStack, mbs, packedLight);
  }

  @Override
  public @NotNull ResourceLocation getTextureLocation(@NotNull TelevisionEntity entity) {
    return Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS).location();
  }

  private void renderPainting(
      PoseStack poseStack,
      VertexConsumer vertexConsumer,
      TelevisionEntity entity,
      int width,
      int height,
      TextureAtlasSprite televisionSprite,
      TextureAtlasSprite televisionBackSprite
  ) {
    PoseStack.Pose posestack$pose = poseStack.last();
    Matrix4f matrix4f = posestack$pose.pose();
    Matrix3f matrix3f = posestack$pose.normal();
    float f = (float)(-width) / 2.0F;
    float f1 = (float)(-height) / 2.0F;
//    float f2 = 0.5F;
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
    int i = width / 16;
    int j = height / 16;
    double d0 = 16.0D / (double)i;
    double d1 = 16.0D / (double)j;

    for(int k = 0; k < i; ++k) {
      for(int l = 0; l < j; ++l) {
        float f15 = f + (float)((k + 1) * 16);
        float f16 = f + (float)(k * 16);
        float f17 = f1 + (float)((l + 1) * 16);
        float f18 = f1 + (float)(l * 16);
        int i1 = entity.getBlockX();
        int j1 = Mth.floor(entity.getY() + (double)((f17 + f18) / 2.0F / 16.0F));
        int k1 = entity.getBlockZ();
        Direction direction = entity.getDirection();
        if (direction == Direction.NORTH) {
          i1 = Mth.floor(entity.getX() + (double)((f15 + f16) / 2.0F / 16.0F));
        }

        if (direction == Direction.WEST) {
          k1 = Mth.floor(entity.getZ() - (double)((f15 + f16) / 2.0F / 16.0F));
        }

        if (direction == Direction.SOUTH) {
          i1 = Mth.floor(entity.getX() - (double)((f15 + f16) / 2.0F / 16.0F));
        }

        if (direction == Direction.EAST) {
          k1 = Mth.floor(entity.getZ() + (double)((f15 + f16) / 2.0F / 16.0F));
        }

        int l1 = LevelRenderer.getLightColor(entity.level, new BlockPos(i1, j1, k1));
        float f19 = televisionSprite.getU(d0 * (double)(i - k));
        float f20 = televisionSprite.getU(d0 * (double)(i - (k + 1)));
        float f21 = televisionSprite.getV(d1 * (double)(j - l));
        float f22 = televisionSprite.getV(d1 * (double)(j - (l + 1)));
        this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f18, f20, f21, -0.5F, 0, 0, -1, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f18, f19, f21, -0.5F, 0, 0, -1, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f17, f19, f22, -0.5F, 0, 0, -1, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f17, f20, f22, -0.5F, 0, 0, -1, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f17, f4, f5, 0.5F, 0, 0, 1, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f17, f3, f5, 0.5F, 0, 0, 1, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f18, f3, f6, 0.5F, 0, 0, 1, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f18, f4, f6, 0.5F, 0, 0, 1, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f17, f7, f9, -0.5F, 0, 1, 0, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f17, f8, f9, -0.5F, 0, 1, 0, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f17, f8, f10, 0.5F, 0, 1, 0, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f17, f7, f10, 0.5F, 0, 1, 0, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f18, f7, f9, 0.5F, 0, -1, 0, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f18, f8, f9, 0.5F, 0, -1, 0, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f18, f8, f10, -0.5F, 0, -1, 0, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f18, f7, f10, -0.5F, 0, -1, 0, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f17, f12, f13, 0.5F, -1, 0, 0, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f18, f12, f14, 0.5F, -1, 0, 0, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f18, f11, f14, -0.5F, -1, 0, 0, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f15, f17, f11, f13, -0.5F, -1, 0, 0, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f17, f12, f13, -0.5F, 1, 0, 0, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f18, f12, f14, -0.5F, 1, 0, 0, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f18, f11, f14, 0.5F, 1, 0, 0, l1);
        this.vertex(matrix4f, matrix3f, vertexConsumer, f16, f17, f11, f13, 0.5F, 1, 0, 0, l1);
      }
    }

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
      int p_115548_
  ) {
    p_115539_.vertex(p_115537_, p_115540_, p_115541_, p_115544_)
             .color(255, 255, 255, 255)
             .uv(p_115542_, p_115543_)
             .overlayCoords(OverlayTexture.NO_OVERLAY)
             .uv2(p_115548_)
             .normal(p_115538_, (float)p_115545_, (float)p_115546_, (float)p_115547_)
             .endVertex();
  }

}
