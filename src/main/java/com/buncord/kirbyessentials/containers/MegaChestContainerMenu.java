package com.buncord.kirbyessentials.containers;

import com.buncord.kirbyessentials.block_entities.MegaChestBlockEntity;
import com.buncord.kirbyessentials.mixin.SlotAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class MegaChestContainerMenu extends AbstractContainerMenu {

  private static final int CONTAINER_ROWS = 6;

  private static final int[][] SLOT_POSITIONS = {
      {8, 18}, {26, 18}, {44, 18}, {62, 18}, {80, 18}, {98, 18}, {116, 18}, {134, 18}, {152, 18},
      {8, 36}, {26, 36}, {44, 36}, {62, 36}, {80, 36}, {98, 36}, {116, 36}, {134, 36}, {152, 36},
      {8, 54}, {26, 54}, {44, 54}, {62, 54}, {80, 54}, {98, 54}, {116, 54}, {134, 54}, {152, 54},
      {8, 72}, {26, 72}, {44, 72}, {62, 72}, {80, 72}, {98, 72}, {116, 72}, {134, 72}, {152, 72},
      {8, 90}, {26, 90}, {44, 90}, {62, 90}, {80, 90}, {98, 90}, {116, 90}, {134, 90}, {152, 90},
      {8, 108}, {26, 108}, {44, 108}, {62, 108}, {80, 108}, {98, 108}, {116, 108}, {134, 108}, {152, 108},
  };

  private final Container container;

  public int activeSlotCount = MegaChestBlockEntity.SIZE;

  public final List<Slot> inventorySlots = new ArrayList<>();
  public final List<Slot> hotbarSlots = new ArrayList<>();

  public MegaChestContainerMenu(int containerID, Inventory inventory) {
    this(containerID, inventory, new SimpleContainer(MegaChestBlockEntity.SIZE));
  }

  public MegaChestContainerMenu(
      int containerID,
      Inventory inventory,
      Container container
  ) {
    super(ModContainers.MEGA_CHEST.get(), containerID);

    this.container = container;
    this.container.startOpen(inventory.player);
    int i = (CONTAINER_ROWS - 4) * 18;

    for(int iItem = 0 ; iItem < MegaChestBlockEntity.SIZE ; ++iItem) {
      if (iItem < SLOT_POSITIONS.length) {
        this.addSlot(
            new CustomSlot(
                this.container,
                iItem,
                SLOT_POSITIONS[iItem][0],
                SLOT_POSITIONS[iItem][1]
            )
        );
      } else {
        this.addSlot(
            new CustomSlot(this.container, iItem, -2000, -2000, false)
        );
      }
    }

    for(int l = 0; l < 3; ++l) {
      for(int j1 = 0; j1 < 9; ++j1) {
        Slot slot = new Slot(
            inventory,
            j1 + l * 9 + 9,
            8 + j1 * 18,
            103 + l * 18 + i
        );

        this.addSlot(slot);
        this.inventorySlots.add(slot);
      }
    }

    for(int i1 = 0; i1 < 9; ++i1) {
      Slot slot = new Slot(inventory, i1, 8 + i1 * 18, 161 + i);

      this.addSlot(slot);
      this.hotbarSlots.add(slot);
    }

    this.update(0.0F, "");
  }

  public boolean stillValid(@NotNull Player player) {
    return this.container.stillValid(player);
  }

  public void update(float scrollOffs, String search) {
    List<Slot> activeSlots = new ArrayList<>();

    for (int iSlot = 0 ; iSlot < MegaChestBlockEntity.SIZE  ; ++iSlot) {
      Slot slot = this.getSlot(iSlot);

      ItemStack itemStack = slot.getItem();

      if (search == null || search.isEmpty()) {
        activeSlots.add(slot);
      } else {
        String lowerCaseSearch = search.toLowerCase(Locale.ROOT);

        boolean matches = false;

        List<Component> tooltipLines = itemStack.getTooltipLines(
            Minecraft.getInstance().player,
            Minecraft.getInstance().options.advancedItemTooltips
              ? TooltipFlag.Default.ADVANCED
              : TooltipFlag.Default.NORMAL
        );

        for (Component line : tooltipLines) {
          String unformattedString = ChatFormatting.stripFormatting(line.getString());

          if (
              unformattedString != null &&
              unformattedString.toLowerCase(Locale.ROOT).contains(lowerCaseSearch)
          ) {
            matches = true;
            break;
          }
        }

        if (matches) {
          activeSlots.add(slot);
        }
      }
    }

    int i = (activeSlots.size() + 9 - 1) / 9 - 6;
    int j = (int)((double)(scrollOffs * (float)i) + 0.5D);
    if (j < 0) {
      j = 0;
    }

    activeSlots.subList(0, j * 9).clear();

    if (activeSlots.size() > 54) {
      activeSlots.subList(54, activeSlots.size()).clear();
    } else {
      for (int iSlot = 0 ; iSlot < MegaChestBlockEntity.SIZE && activeSlots.size() < 54 ; ++iSlot) {
        Slot slot = this.getSlot(iSlot);

        ItemStack itemStack = slot.getItem();

        if (itemStack.getItem() == Items.AIR) {
          activeSlots.add(slot);
        }
      }
    }

    int iActiveSlot = 0;
    for (int iSlot = 0 ; iSlot < MegaChestBlockEntity.SIZE ; ++iSlot) {
      CustomSlot slot = (CustomSlot) this.getSlot(iSlot);

      boolean isActive = activeSlots.contains(slot);

      slot.setActive(isActive);

      if (isActive && iActiveSlot < 54) {
        int slotIndex = activeSlots.indexOf(slot);

        ((SlotAccessor) slot).setX(SLOT_POSITIONS[slotIndex][0]);
        ((SlotAccessor) slot).setY(SLOT_POSITIONS[slotIndex][1]);

        iActiveSlot++;
      } else {
        ((SlotAccessor) slot).setX(-2000);
        ((SlotAccessor) slot).setY(-2000);
      }
    }
  }

  public boolean canScroll() {
    return this.activeSlotCount > 54 + 3 * 9 + 9;
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

  static class CustomSlot extends Slot {
    private boolean isActive;

    public CustomSlot(Container p_40223_, int p_40224_, int p_40225_, int p_40226_) {
      this(p_40223_, p_40224_, p_40225_, p_40226_, true);
    }

    public CustomSlot(
        Container p_40223_,
        int p_40224_,
        int p_40225_,
        int p_40226_,
        boolean isActive
    ) {
      super(p_40223_, p_40224_, p_40225_, p_40226_);

      this.isActive = isActive;
    }

    @Override
    public boolean isActive() {
      return isActive;
    }

    public void setActive(boolean active) {
      isActive = active;
    }

  }

}
