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
public class FancyShirtModel<T extends LivingEntity> extends CosmeticArmorModel<T> {

  public static final ModelLayerLocation LAYER_LOCATION =
      new ModelLayerLocation(
          new ResourceLocation(KirbyEssentials.MOD_ID, "fancy_shirt"),
          "main"
      );

  private static final ResourceLocation TEXTURE = new ResourceLocation(
      KirbyEssentials.MOD_ID,
      "textures/models/armor/fancy_shirt.png"
  );

  public final ModelPart jacket;
  public final ModelPart leftSleeve;
  public final ModelPart rightSleeve;

  public FancyShirtModel(ModelPart modelPart) {
    super(modelPart);

    this.jacket = modelPart.getChild("jacket");
    this.leftSleeve = modelPart.getChild("left_sleeve");
    this.rightSleeve = modelPart.getChild("right_sleeve");
  }

  public static LayerDefinition createLayer()
  {
    CubeDeformation cubeDeformation = CubeDeformation.NONE;
    MeshDefinition meshdefinition = HumanoidModel.createMesh(cubeDeformation, 0.0F);
    PartDefinition partdefinition = meshdefinition.getRoot();

    partdefinition.addOrReplaceChild(
        "jacket",
        CubeListBuilder.create()
                       .texOffs(0, 64 / 4)
                       .addBox(
                           -4.0F, 0.0F, -2.0F,
                           8.0F, 12.0F, 4.0F,
                           cubeDeformation.extend(0.25F),
                           0.25F, 0.25F
                       ),
        PartPose.ZERO
    );

    partdefinition.addOrReplaceChild(
        "left_sleeve",
        CubeListBuilder.create()
                       .texOffs(0, 0)
                       .addBox(
                           -1.0F, -2.0F, -2.0F,
                           4.0F, 12.0F, 4.0F,
                           cubeDeformation.extend(0.25F),
                           0.25F, 0.25F
                       ),
        PartPose.offset(5.0F, 2.0F, 0.0F)
    );

    partdefinition.addOrReplaceChild(
        "right_sleeve",
        CubeListBuilder.create()
                       .texOffs(64 / 4, 0)
                       .addBox(
                           -3.0F, -2.0F, -2.0F,
                           4.0F, 12.0F, 4.0F,
                           cubeDeformation.extend(0.25F),
                           0.25F, 0.25F
                       ),
        PartPose.offset(-5.0F, 2.0F, 0.0F)
    );

    return LayerDefinition.create(meshdefinition, 128, 128);
  }

  @Override
  protected @NotNull Iterable<ModelPart> headParts()
  {
    return ImmutableList.of();
  }

  @Override
  protected @NotNull Iterable<ModelPart> bodyParts()
  {
    this.jacket.copyFrom(this.body);
    this.leftSleeve.copyFrom(this.leftArm);
    this.rightSleeve.copyFrom(this.rightArm);

    return ImmutableList.of(
        this.jacket,
        this.leftSleeve,
        this.rightSleeve
    );
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
