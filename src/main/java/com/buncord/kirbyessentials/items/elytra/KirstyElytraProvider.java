package com.buncord.kirbyessentials.items.elytra;

import com.buncord.kirbyessentials.items.ModItems;
import com.buncord.kirbyessentials.renderers.ElytraBrionyLayer;
import com.buncord.kirbyessentials.renderers.ElytraKirstyLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class KirstyElytraProvider implements IElytraProvider {

  @Override
  public ResourceLocation getTexture(ItemStack stack) {
    return ElytraKirstyLayer.WINGS_LOCATION;
  }

  @Override
  public boolean matches(ItemStack stack) {
    return stack.getItem() == ModItems.ELYTRA_KIRSTY.get();
  }

}
