package com.buncord.kirbyessentials.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;

public class ElytraRythianItem extends ElytraItem {

  public ElytraRythianItem(Properties properties) {
    super(properties);
  }

  @Override
  public EquipmentSlot getEquipmentSlot(ItemStack stack)
  {
    return EquipmentSlot.CHEST;
  }

}
