package com.buncord.kirbyessentials.items.elytra;

import com.buncord.kirbyessentials.items.ModItems;
import com.buncord.kirbyessentials.renderers.ElytraKirstyLayer;
import com.buncord.kirbyessentials.renderers.ElytraRythianLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class RythianElytraProvider implements IElytraProvider {

  @Override
  public ResourceLocation getTexture(ItemStack stack) {
    return ElytraRythianLayer.WINGS_LOCATION;
  }

  @Override
  public boolean matches(ItemStack stack) {
    return stack.getItem() == ModItems.ELYTRA_RYTHIAN.get();
  }

}
