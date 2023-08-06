package com.buncord.kirbyessentials.models;

import com.buncord.kirbyessentials.KirbyEssentials;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ElytraBrionyModel<T extends LivingEntity> extends AgeableListModel<T>  {
  public static final ModelLayerLocation LAYER_LOCATION =
      new ModelLayerLocation(
          new ResourceLocation(KirbyEssentials.MOD_ID, "elytra_briony"),
          "main"
      );

  private final ModelPart rightWing;
  private final ModelPart leftWing;

  public ElytraBrionyModel(ModelPart modelPart) {
    this.leftWing = modelPart.getChild("left_wing");
    this.rightWing = modelPart.getChild("right_wing");
  }

  public static LayerDefinition createLayer() {
    MeshDefinition meshdefinition = new MeshDefinition();
    PartDefinition partdefinition = meshdefinition.getRoot();
    CubeDeformation cubedeformation = new CubeDeformation(1.0F);

    partdefinition.addOrReplaceChild(
        "left_wing",
        CubeListBuilder.create()
                       .mirror()
                       .addBox(
                           -18.0F, 0.0F, 0.0F,
                           18.0F, 30.0F, 1.0F,
                           cubedeformation
                       ),
        PartPose.offsetAndRotation(
            -1.5F, -5.0F, -0.5F,
            0.0F, 0.2617994F, 0.0F
        )
    );

    partdefinition.addOrReplaceChild(
        "right_wing",
        CubeListBuilder.create()
                       .addBox(
                           0.0F, 0.0F, -0.5F,
                           18.0F, 30.0F, 1.0F,
                           cubedeformation
                       ),
        PartPose.offsetAndRotation(
            1.5F, -5.0F, 0.0F,
            0.0F, -0.2617994F, 0.F
        )
    );

    return LayerDefinition.create(meshdefinition, 38, 31);
  }

  protected @NotNull Iterable<ModelPart> headParts() {
    return ImmutableList.of();
  }

  protected @NotNull Iterable<ModelPart> bodyParts() {
    return ImmutableList.of(this.leftWing, this.rightWing);
  }

  public void setupAnim(
      @NotNull T entity,
      float animationPosition,
      float animationSpeed,
      float bob,
      float headRot,
      float xRot
  ) {
    float rotX = entity.isCrouching() ? 0.2617994F : 0.0F;
    float rotY = 0.2617994F;
    float rotZ = entity.isCrouching() ? (0.2617994F / 4.0F) : 0.0F;

    if (entity.isFallFlying()) {
      float speedFactor = 1.0F;
      Vec3 vec3 = entity.getDeltaMovement();
      if (vec3.y < 0.0D) {
        Vec3 vec31 = vec3.normalize();
        speedFactor = 1.0F - (float)Math.pow(-vec31.y, 1.5D);
      }

      rotY = speedFactor * 0.34906584F + (1.0F - speedFactor) * rotY;
    }

    if (entity instanceof AbstractClientPlayer abstractclientplayer) {
      abstractclientplayer.elytraRotX = (float)((double)abstractclientplayer.elytraRotX + (double)(rotX - abstractclientplayer.elytraRotX) * 0.1D);
      abstractclientplayer.elytraRotY = (float)((double)abstractclientplayer.elytraRotY + (double)(rotY - abstractclientplayer.elytraRotY) * 0.1D);
      abstractclientplayer.elytraRotZ = (float)((double)abstractclientplayer.elytraRotZ + (double)(rotZ - abstractclientplayer.elytraRotZ) * 0.1D);
      this.leftWing.xRot = abstractclientplayer.elytraRotX;
      this.leftWing.yRot = abstractclientplayer.elytraRotY;
      this.leftWing.zRot = abstractclientplayer.elytraRotZ;
    } else {
      this.leftWing.xRot = rotX;
      this.leftWing.yRot = rotY;
      this.leftWing.zRot = rotZ;
    }

    this.rightWing.xRot = this.leftWing.xRot;
    this.rightWing.yRot = -this.leftWing.yRot;
    this.rightWing.zRot = -this.leftWing.zRot;
  }

}
