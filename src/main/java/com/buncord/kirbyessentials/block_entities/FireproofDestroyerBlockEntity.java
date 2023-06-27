package com.buncord.kirbyessentials.block_entities;

import com.buncord.kirbyessentials.blocks.FireproofDestroyerBlock;
import com.buncord.kirbyessentials.sounds.ModSounds;
import java.util.Random;
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
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class FireproofDestroyerBlockEntity extends RandomizableContainerBlockEntity {

  private static final int SIZE = 27;
  public static final int TICK_INTERVAL = 20 * 60;

  private NonNullList<ItemStack> items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
  private int ticks = 0;

  private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
    protected void onOpen(
        @NotNull Level level,
        @NotNull BlockPos blockPos,
        @NotNull BlockState blockState
    ) {
      FireproofDestroyerBlockEntity.this.playSound(blockState, SoundEvents.IRON_DOOR_OPEN);
      FireproofDestroyerBlockEntity.this.updateBlockState(blockState, true);
    }

    protected void onClose(
        @NotNull Level level,
        @NotNull BlockPos blockPos,
        @NotNull BlockState blockState
    ) {
      FireproofDestroyerBlockEntity.this.playSound(blockState, SoundEvents.IRON_DOOR_CLOSE);
      FireproofDestroyerBlockEntity.this.updateBlockState(blockState, false);
    }

    protected void openerCountChanged(
        @NotNull Level level,
        @NotNull BlockPos blockPos,
        @NotNull BlockState blockState,
        int oldOpenCount,
        int newOpenCount
    ) {}

    protected boolean isOwnContainer(Player player) {
      if (player.containerMenu instanceof ChestMenu) {
        Container container = ((ChestMenu)player.containerMenu).getContainer();
        return container == FireproofDestroyerBlockEntity.this;
      } else {
        return false;
      }
    }
  };

  public FireproofDestroyerBlockEntity(BlockPos blockPos, BlockState blockState) {
    super(ModBlockEntities.FIREPROOF_DESTROYER.get(), blockPos, blockState);
  }

  protected void saveAdditional(@NotNull CompoundTag compoundTag) {
    super.saveAdditional(compoundTag);
    if (!this.trySaveLootTable(compoundTag)) {
      ContainerHelper.saveAllItems(compoundTag, this.items);
    }
  }

  public void load(@NotNull CompoundTag compoundTag) {
    super.load(compoundTag);
    this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    if (!this.tryLoadLootTable(compoundTag)) {
      ContainerHelper.loadAllItems(compoundTag, this.items);
    }
  }

  public int getContainerSize() {
    return 27;
  }

  public @NotNull NonNullList<ItemStack> getItems() {
    return this.items;
  }

  protected void setItems(@NotNull NonNullList<ItemStack> items) {
    this.items = items;
  }

  protected @NotNull Component getDefaultName() {
    return new TranslatableComponent("entity.kirbyessentials.fireproof_destroyer");
  }

  protected @NotNull AbstractContainerMenu createMenu(
      int containerId,
      @NotNull Inventory inventory
  ) {
    return ChestMenu.threeRows(containerId, inventory, this);
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
      this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
    }
  }

  void updateBlockState(BlockState blockState, boolean state) {
    if (this.level != null) {
      this.level.setBlock(
          this.getBlockPos(),
          blockState.setValue(FireproofDestroyerBlock.OPEN, state),
          3
      );
    }
  }

  void playSound(BlockState blockState, SoundEvent soundEvent) {
    if (this.level != null) {
      Vec3i vec3i = blockState.getValue(FireproofDestroyerBlock.FACING).getNormal();
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

  public static void serverTick(
      Level level,
      BlockPos blockPos,
      BlockState blockState,
      FireproofDestroyerBlockEntity entity
  ) {
    if (!blockState.getValue(FireproofDestroyerBlock.OPEN)) {
      if (entity.ticks++ >= TICK_INTERVAL) {
        if (entity.getItems().stream().anyMatch(is -> !is.isEmpty())) {
          entity.getItems().clear();
          entity.setChanged();

          Random random = new Random();

          int randomInt = random.nextInt(10);

          if (randomInt == 0) {
            level.playSound(
                null,
                blockPos,
                ModSounds.FIREPROOF_DESTROYER.get(),
                SoundSource.BLOCKS,
                0.5F,
                1F
            );
          }
        }

        entity.ticks = 0;
      }
    } else {
      entity.ticks = 0;
    }
  }

}
