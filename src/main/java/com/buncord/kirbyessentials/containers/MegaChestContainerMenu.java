package com.buncord.kirbyessentials.containers;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MegaChestContainerMenu extends AbstractContainerMenu {

  private static final int CONTAINER_ROWS = 6;

  private final Container container;

  public MegaChestContainerMenu(int containerID, Inventory inventory) {
    this(containerID, inventory, new SimpleContainer(9 * CONTAINER_ROWS));
  }

  public MegaChestContainerMenu(
      int containerID,
      Inventory inventory,
      Container container
  ) {
    super(ModContainers.MEGA_CHEST.get(), containerID);
    checkContainerSize(container, CONTAINER_ROWS * 9);
    this.container = container;
    container.startOpen(inventory.player);
    int i = (CONTAINER_ROWS - 4) * 18;

    for(int j = 0; j < CONTAINER_ROWS; ++j) {
      for(int k = 0; k < 9; ++k) {
        this.addSlot(
            new Slot(container, k + j * 9, 8 + k * 18, 18 + j * 18)
        );
      }
    }

    for(int l = 0; l < 3; ++l) {
      for(int j1 = 0; j1 < 9; ++j1) {
        this.addSlot(new Slot(
            inventory,
            j1 + l * 9 + 9,
            8 + j1 * 18,
            103 + l * 18 + i
        ));
      }
    }

    for(int i1 = 0; i1 < 9; ++i1) {
      this.addSlot(new Slot(inventory, i1, 8 + i1 * 18, 161 + i));
    }
  }

  public boolean stillValid(@NotNull Player player) {
    return this.container.stillValid(player);
  }

  public @NotNull ItemStack quickMoveStack(@NotNull Player player, int slotID) {
    ItemStack itemstack = ItemStack.EMPTY;
    Slot slot = this.slots.get(slotID);

    if (slot.hasItem()) {
      ItemStack itemstack1 = slot.getItem();
      itemstack = itemstack1.copy();
      if (slotID < CONTAINER_ROWS * 9) {
        if (!this.moveItemStackTo(
            itemstack1,
            CONTAINER_ROWS * 9,
            this.slots.size(),
            true
        )) {
          return ItemStack.EMPTY;
        }
      } else if (!this.moveItemStackTo(
          itemstack1,
          0,
          CONTAINER_ROWS * 9,
          false
      )) {
        return ItemStack.EMPTY;
      }

      if (itemstack1.isEmpty()) {
        slot.set(ItemStack.EMPTY);
      } else {
        slot.setChanged();
      }
    }

    return itemstack;
  }

  public void removed(@NotNull Player player) {
    super.removed(player);
    this.container.stopOpen(player);
  }

  public Container getContainer() {
    return this.container;
  }

  public int getRowCount() {
    return CONTAINER_ROWS;
  }

}
