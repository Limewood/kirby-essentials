package com.buncord.kirbyessentials.items.elytra;

import com.buncord.kirbyessentials.items.ModItems;
import com.buncord.kirbyessentials.renderers.ElytraBrionyLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BrionyElytraProvider implements IElytraProvider {

  @Override
  public ResourceLocation getTexture(ItemStack stack) {
    return ElytraBrionyLayer.WINGS_LOCATION;
  }

  @Override
  public boolean matches(ItemStack stack) {
    return stack.getItem() == ModItems.ELYTRA_BRIONY.get();
  }

}
