package com.buncord.kirbyessentials.blocks;

import com.buncord.kirbyessentials.block_entities.MegaChestBlockEntity;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class MegaChestBlock extends BaseEntityBlock{

  public static final DirectionProperty FACING = BlockStateProperties.FACING;
  public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

  public MegaChestBlock(BlockBehaviour.Properties properties) {
    super(properties);
    this.registerDefaultState(
        this.stateDefinition.any()
                            .setValue(FACING, Direction.UP)
                            .setValue(OPEN, Boolean.FALSE)
    );
  }

  public @NotNull InteractionResult use(
      @NotNull BlockState blockState,
      Level level,
      @NotNull BlockPos blockPos,
      @NotNull Player player,
      @NotNull InteractionHand interactionHand,
      @NotNull BlockHitResult blockHitResult
  ) {
    if (level.isClientSide) {
      return InteractionResult.SUCCESS;
    } else {
      BlockEntity blockentity = level.getBlockEntity(blockPos);
      if (blockentity instanceof MegaChestBlockEntity) {
        player.openMenu((MegaChestBlockEntity)blockentity);
      }

      return InteractionResult.CONSUME;
    }
  }

  public void onRemove(
      BlockState blockState,
      @NotNull Level level,
      @NotNull BlockPos blockPos,
      BlockState blockState1,
      boolean flag
  ) {
    if (!blockState.is(blockState1.getBlock())) {
      BlockEntity blockentity = level.getBlockEntity(blockPos);
      if (blockentity instanceof Container) {
        level.updateNeighbourForOutputSignal(blockPos, this);
      }

      super.onRemove(blockState, level, blockPos, blockState1, flag);
    }
  }

  public void tick(
      @NotNull BlockState blockState,
      ServerLevel serverLevel,
      @NotNull BlockPos blockPos,
      @NotNull Random random
  ) {
    BlockEntity blockentity = serverLevel.getBlockEntity(blockPos);
    if (blockentity instanceof MegaChestBlockEntity) {
      ((MegaChestBlockEntity)blockentity).recheckOpen();
    }
  }

  @Nullable
  public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
    return new MegaChestBlockEntity(blockPos, blockState);
  }

  public @NotNull RenderShape getRenderShape(@NotNull BlockState blockState) {
    return RenderShape.MODEL;
  }

  public void setPlacedBy(
      @NotNull Level level,
      @NotNull BlockPos blockPos,
      @NotNull BlockState blockState,
      @Nullable LivingEntity livingEntity,
      ItemStack itemStack
  ) {
    if (itemStack.hasCustomHoverName()) {
      BlockEntity blockentity = level.getBlockEntity(blockPos);
      if (blockentity instanceof MegaChestBlockEntity) {
        ((MegaChestBlockEntity)blockentity).setCustomName(itemStack.getHoverName());
      }
    }
  }

  public boolean hasAnalogOutputSignal(@NotNull BlockState blockState) {
    return true;
  }

  public int getAnalogOutputSignal(
      @NotNull BlockState blockState,
      Level level,
      @NotNull BlockPos blockPos
  ) {
    return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(blockPos));
  }

  public @NotNull BlockState rotate(BlockState blockState, Rotation rotation) {
    return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
  }

  public @NotNull BlockState mirror(BlockState blockState, Mirror mirror) {
    return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
  }

  protected void createBlockStateDefinition(
      StateDefinition.Builder<Block, BlockState> blockStateBuilder
  ) {
    blockStateBuilder.add(FACING, OPEN);
  }

  public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
    return this.defaultBlockState().setValue(FACING, Direction.UP);
  }

}
