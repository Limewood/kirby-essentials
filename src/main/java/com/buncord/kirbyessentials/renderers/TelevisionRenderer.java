package com.buncord.kirbyessentials.renderers;

import com.buncord.kirbyessentials.KirbyEssentials;
import com.buncord.kirbyessentials.entities.TelevisionEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TelevisionRenderer extends EntityRenderer<TelevisionEntity> {

  private static final ResourceLocation TELEVISION_LOCATION =
      new ResourceLocation(KirbyEssentials.MOD_ID, "textures/entities/television.png");

  public TelevisionRenderer(Context context) {
    super(context);
  }

  @Override public ResourceLocation getTextureLocation(TelevisionEntity entity) {
    return TELEVISION_LOCATION;
  }

}
