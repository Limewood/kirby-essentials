package com.buncord.kirbyessentials.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;

public class ElytraKirstyItem extends ElytraItem {

  public ElytraKirstyItem(Properties properties) {
    super(properties);
  }

  @Override
  public EquipmentSlot getEquipmentSlot(ItemStack stack)
  {
    return EquipmentSlot.CHEST;
  }

}
