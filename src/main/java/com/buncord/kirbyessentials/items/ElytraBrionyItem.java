package com.buncord.kirbyessentials.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;

public class ElytraBrionyItem extends ElytraItem {

  public ElytraBrionyItem(Properties properties) {
    super(properties);
  }

  @Override
  public EquipmentSlot getEquipmentSlot(ItemStack stack)
  {
    return EquipmentSlot.CHEST;
  }

}
