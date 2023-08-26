package com.buncord.kirbyessentials.block_entities;

import com.buncord.kirbyessentials.blocks.MegaChestBlock;
import com.buncord.kirbyessentials.containers.MegaChestContainerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class MegaChestBlockEntity extends RandomizableContainerBlockEntity {

  private static final int SIZE = 54;

  private static NonNullList<ItemStack> ITEMS = NonNullList.withSize(SIZE, ItemStack.EMPTY);

  private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
    protected void onOpen(
        @NotNull Level level,
        @NotNull BlockPos blockPos,
        @NotNull BlockState blockState
    ) {
      MegaChestBlockEntity.this.playSound(blockState, SoundEvents.CHEST_OPEN);
      MegaChestBlockEntity.this.updateBlockState(blockState, true);
    }

    protected void onClose(
        @NotNull Level level,
        @NotNull BlockPos blockPos,
        @NotNull BlockState blockState
    ) {
      MegaChestBlockEntity.this.playSound(blockState, SoundEvents.CHEST_CLOSE);
      MegaChestBlockEntity.this.updateBlockState(blockState, false);
    }

    protected void openerCountChanged(
        @NotNull Level level,
        @NotNull BlockPos blockPos,
        @NotNull BlockState blockState,
        int oldOpenCount,
        int newOpenCount
    ) {
    }

    protected boolean isOwnContainer(Player player) {
      if (player.containerMenu instanceof MegaChestContainerMenu) {
        Container container = ((MegaChestContainerMenu)player.containerMenu).getContainer();
        return container == MegaChestBlockEntity.this;
      } else {
        return false;
      }
    }
  };

  public MegaChestBlockEntity(BlockPos blockPos, BlockState blockState) {
    super(ModBlockEntities.MEGA_CHEST.get(), blockPos, blockState);
  }

  protected void saveAdditional(@NotNull CompoundTag compoundTag) {
    super.saveAdditional(compoundTag);
    if (!this.trySaveLootTable(compoundTag)) {
      ContainerHelper.saveAllItems(compoundTag, ITEMS);
    }
  }

  public void load(@NotNull CompoundTag compoundTag) {
    super.load(compoundTag);
    ITEMS = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    if (!this.tryLoadLootTable(compoundTag)) {
      ContainerHelper.loadAllItems(compoundTag, ITEMS);
    }
  }

  public int getContainerSize() {
    return SIZE;
  }

  protected @NotNull NonNullList<ItemStack> getItems() {
    return ITEMS;
  }

  protected void setItems(@NotNull NonNullList<ItemStack> items) {
    ITEMS = items;
  }

  protected @NotNull Component getDefaultName() {
    return new TranslatableComponent("block.kirbyessentials.mega_chest");
  }

  protected @NotNull AbstractContainerMenu createMenu(
      int containerID,
      @NotNull Inventory inventory
  ) {
    return new MegaChestContainerMenu(containerID, inventory, this);
  }

  public void startOpen(@NotNull Player player) {
    if (!this.remove && !player.isSpectator() && this.getLevel() != null) {
      this.openersCounter.incrementOpeners(
          player,
          this.getLevel(),
          this.getBlockPos(),
          this.getBlockState()
      );
    }
  }

  public void stopOpen(@NotNull Player player) {
    if (!this.remove && !player.isSpectator() && this.getLevel() != null) {
      this.openersCounter.decrementOpeners(
          player,
          this.getLevel(),
          this.getBlockPos(),
          this.getBlockState()
      );
    }
  }

  public void recheckOpen() {
    if (!this.remove && this.getLevel() != null) {
      this.openersCounter.recheckOpeners(
          this.getLevel(),
          this.getBlockPos(),
          this.getBlockState()
      );
    }
  }

  void updateBlockState(BlockState blockState, boolean state) {
    if (this.level != null) {
      this.level.setBlock(
          this.getBlockPos(),
          blockState.setValue(MegaChestBlock.OPEN, state),
          3
      );
    }
  }

  void playSound(BlockState blockState, SoundEvent soundEvent) {
    if (this.level != null) {
      Vec3i vec3i = blockState.getValue(MegaChestBlock.FACING).getNormal();
      double d0 = (double) this.worldPosition.getX() + 0.5D + (double) vec3i.getX() / 2.0D;
      double d1 = (double) this.worldPosition.getY() + 0.5D + (double) vec3i.getY() / 2.0D;
      double d2 = (double) this.worldPosition.getZ() + 0.5D + (double) vec3i.getZ() / 2.0D;
      this.level.playSound(
          null,
          d0,
          d1,
          d2,
          soundEvent,
          SoundSource.BLOCKS,
          0.5F,
          this.level.random.nextFloat() * 0.1F + 0.9F
      );
    }
  }

}
