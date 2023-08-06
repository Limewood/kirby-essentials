package com.buncord.kirbyessentials.models;

import com.buncord.kirbyessentials.KirbyEssentials;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class PikaHatModel<T extends LivingEntity> extends CosmeticArmorModel<T> {

  public static final ModelLayerLocation LAYER_LOCATION =
      new ModelLayerLocation(
          new ResourceLocation(KirbyEssentials.MOD_ID, "pika_hat"),
          "main"
      );

  private static final ResourceLocation TEXTURE = new ResourceLocation(
      KirbyEssentials.MOD_ID,
      "textures/models/armor/pika_hat.png"
  );

  public final ModelPart hat;
  public final ModelPart leftEar;
  public final ModelPart rightEar;

  public PikaHatModel(ModelPart modelPart) {
    super(modelPart);

    this.hat = modelPart.getChild("hat");
    this.leftEar = modelPart.getChild("left_ear");
    this.rightEar = modelPart.getChild("right_ear");
  }

  public static LayerDefinition createLayer()
  {
    CubeDeformation cubeDeformation = CubeDeformation.NONE;
    MeshDefinition meshdefinition = HumanoidModel.createMesh(cubeDeformation, 0.0F);
    PartDefinition partdefinition = meshdefinition.getRoot();

    partdefinition.addOrReplaceChild(
        "hat",
        CubeListBuilder.create()
                       .texOffs(0, 0)
                       .addBox(
                           -4.0F, -8.0F, -4.0F,
                           8.0F, 8.0F, 8.0F,
                           cubeDeformation.extend(0.5F),
                           0.25F, 0.25F
                       ),
        PartPose.offset(0.0F, 0.0F, 0.0F)
    );

    partdefinition.addOrReplaceChild(
        "left_ear",
        CubeListBuilder.create()
                       .texOffs(0, 64 / 4)
                       .addBox(
                           -3.0F, -12.0F, -0.5F,
                           2.0F, 3.0F, 1.0F,
                           cubeDeformation.extend(0.5F),
                           0.25F, 0.25F
                       ),
        PartPose.offset(0.0F, 0.0F, 0.0F)
    );

    partdefinition.addOrReplaceChild(
        "right_ear",
        CubeListBuilder.create()
                       .texOffs(24/4, 64 / 4)
                       .addBox(
                           1.0F, -12.0F, -0.5F,
                           2.0F, 3.0F, 1.0F,
                           cubeDeformation.extend(0.5F),
                           0.25F, 0.25F
                       ),
        PartPose.offset(0.0F, 0.0F, 0.0F)
    );

    return LayerDefinition.create(meshdefinition, 128, 128);
  }

  @Override
  protected @NotNull Iterable<ModelPart> headParts()
  {
    this.hat.copyFrom(this.hat);
    this.leftEar.copyFrom(this.hat);
    this.rightEar.copyFrom(this.hat);

    return ImmutableList.of(
        this.hat,
        this.leftEar,
        this.rightEar
    );
  }

  @Override
  protected @NotNull Iterable<ModelPart> bodyParts()
  {
    return ImmutableList.of();
  }

  @Override
  public void setupAnim(
      @NotNull T entity,
      float limbSwing,
      float limbSwingAmount,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
  ) {
    super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
  }

  @Override
  protected ResourceLocation getTexture()
  {
    return TEXTURE;
  }

}
