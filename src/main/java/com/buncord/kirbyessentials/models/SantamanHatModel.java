package com.buncord.kirbyessentials.models;

import com.buncord.kirbyessentials.KirbyEssentials;
import com.google.common.collect.ImmutableList;
import com.mojang.math.Vector3f;
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

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class SantamanHatModel<T extends LivingEntity> extends CosmeticArmorModel<T> {

  public static final ModelLayerLocation LAYER_LOCATION =
      new ModelLayerLocation(
          new ResourceLocation(KirbyEssentials.MOD_ID, "santaman_hat"),
          "main"
      );

  // TODO create hat texture
  public static final ResourceLocation TEXTURE = new ResourceLocation(
      KirbyEssentials.MOD_ID,
      "textures/models/armor/santaman_hat.png"
  );

  public final ModelPart rim;
  public final ModelPart layer1;
  public final ModelPart layer2;
  public final ModelPart layer3;
  public final ModelPart bobble;
  private final Map<ModelPart, Vector3f> partPos;
  private final Map<ModelPart, Vector3f> partRot;

  private static final String RIM = "rim";
  private static final String LAYER1 = "layer1";
  private static final String LAYER2 = "layer2";
  private static final String LAYER3 = "layer3";
  private static final String BOBBLE = "bobble";

  public SantamanHatModel(ModelPart modelPart) {
    super(modelPart);

    this.rim = modelPart.getChild(RIM);
    this.layer1 = modelPart.getChild(LAYER1);
    this.layer2 = modelPart.getChild(LAYER2);
    this.layer3 = modelPart.getChild(LAYER3);
    this.bobble = modelPart.getChild(BOBBLE);
    this.partPos = new HashMap<>();
    this.partRot = new HashMap<>();
    addPosAndRot(this.rim);
    addPosAndRot(this.layer1);
    addPosAndRot(this.layer2);
    addPosAndRot(this.layer3);
    addPosAndRot(this.bobble);
  }

  private void addPosAndRot(ModelPart part) {
    this.partPos.put(part, new Vector3f(part.x, part.y, part.z));
    this.partRot.put(part, new Vector3f(part.xRot, part.yRot, part.zRot));
  }

  public static LayerDefinition createLayer()
  {
    MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0F);
    PartDefinition partdefinition = meshdefinition.getRoot();

    // Create hat model
    partdefinition.addOrReplaceChild(
            RIM,
            CubeListBuilder.create()
                    .texOffs(0, 0)
                    .addBox(-5.0F, -10.0F, -5.0F,
                            10.0F, 3.0F, 10.0F),
            PartPose.offset(0.0F, 0F, 0.0F));

    partdefinition.addOrReplaceChild(
            LAYER1,
            CubeListBuilder.create()
                    .texOffs(33, 64)
                    .addBox(-4.0F, -12.0F, -6.0F,
                            8.0F, 4.0F, 8.0F),
            PartPose.offsetAndRotation(
                    0.0F, 0F, 0.0F,
                    -0.2F, 0F, 0F
            ));

    partdefinition.addOrReplaceChild(
            LAYER2,
            CubeListBuilder.create()
                    .texOffs(33, 64)
                    .addBox(-3.0F, -13.0F, -6.8F,
                            6.0F, 4.0F, 6.0F),
            PartPose.offsetAndRotation(
                    0.0F, 0F, 0.0F,
                    -0.4F, 0F, 0F
            ));

    partdefinition.addOrReplaceChild(
            LAYER3,
            CubeListBuilder.create()
                    .texOffs(33, 64)
                    .addBox(-2.0F, -14.0F, -7.7F,
                            4.0F, 4.0F, 4.0F),
            PartPose.offsetAndRotation(
                    0.0F, 0F, 0.0F,
                    -0.6F, 0F, 0F
            ));

    partdefinition.addOrReplaceChild(
            BOBBLE,
            CubeListBuilder.create()
                    .texOffs(0, 32)
                    .addBox(-1.0F, -15.0F, -9.0F,
                            2.0F, 4.0F, 2.0F),
            PartPose.offsetAndRotation(
                    0.0F, 0F, 0.0F,
                    -0.8F, 0F, 0F
            ));

    return LayerDefinition.create(meshdefinition, 128, 128);
  }

  @Override
  protected @NotNull Iterable<ModelPart> headParts()
  {
    return ImmutableList.of(
            this.rim,
            this.layer1,
            this.layer2,
            this.layer3,
            this.bobble
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
    // Make the hat follow the head
    addHeadPositionAndRotation(this.rim);
    addHeadPositionAndRotation(this.layer1);
    addHeadPositionAndRotation(this.layer2);
    addHeadPositionAndRotation(this.layer3);
    addHeadPositionAndRotation(this.bobble);
  }

  public void addHeadPositionAndRotation(ModelPart part) {
    part.x = partPos.get(part).x() + head.x;
    part.y = partPos.get(part).y() + head.y;
    part.z = partPos.get(part).z() + head.z;
    part.xRot = partRot.get(part).x() + head.xRot;
    part.yRot = partRot.get(part).y() + head.yRot;
    part.zRot = partRot.get(part).z() + head.zRot;
  }

  @Override
  protected ResourceLocation getTexture()
  {
    return TEXTURE;
  }

}
