package com.buncord.kirbyessentials.entities;

import com.buncord.kirbyessentials.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TelevisionEntity extends HangingEntity implements IEntityAdditionalSpawnData {

  private static final EntityDataAccessor<Boolean> DATA_ON_OFF =
      SynchedEntityData.defineId(TelevisionEntity.class, EntityDataSerializers.BOOLEAN);
  private boolean isOn = false;

  public TelevisionEntity(
      EntityType<? extends HangingEntity> entityType,
      Level level
  )
  {
    super(entityType, level);
  }

  public TelevisionEntity(Level level, BlockPos blockPos, Direction direction) {
    super(ModEntities.TELEVISION_ENTITY_TYPE, level, blockPos);

    this.setDirection(direction);
  }

  @Override
  public void readSpawnData(FriendlyByteBuf buffer) {
    this.direction = Direction.from2DDataValue(buffer.readByte());
    this.setDirection(this.direction);
  }

  @Override
  public void writeSpawnData(FriendlyByteBuf buffer) {
    buffer.writeByte((byte) this.direction.get2DDataValue());
  }

  public void addAdditionalSaveData(CompoundTag compoundTag) {
    compoundTag.putByte("Facing", (byte)this.direction.get2DDataValue());
    compoundTag.putBoolean("IsOn", this.isOn);
    super.addAdditionalSaveData(compoundTag);
  }

  public void readAdditionalSaveData(CompoundTag compoundTag) {
    this.direction = Direction.from2DDataValue(compoundTag.getByte("Facing"));
    this.isOn = compoundTag.getBoolean("IsOn");
    super.readAdditionalSaveData(compoundTag);
    this.setDirection(this.direction);
  }

  @Override public int getWidth() {
    return 3 * 16;
  }

  @Override public int getHeight() {
    return 2 * 16;
  }

  @Override public void dropItem(@Nullable Entity entity) {
    if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
      this.playSound(SoundEvents.PAINTING_BREAK, 1.0F, 1.0F);
      if (entity instanceof Player player) {
        if (player.getAbilities().instabuild) {
          return;
        }
      }

      this.spawnAtLocation(new ItemStack(ModItems.TELEVISION.get()));
    }
  }

  @Override public void playPlacementSound() {
    this.playSound(SoundEvents.PAINTING_PLACE, 1.0F, 1.0F);
  }

  public void moveTo(double x, double y, double z, float yRot, float xRot) {
    this.setPos(x, y, z);
  }

  public void lerpTo(
      double x,
      double y,
      double z,
      float yRot,
      float xRot,
      int p_31922_,
      boolean p_31923_
  ) {
    BlockPos blockpos = this.pos.offset(
        x - this.getX(),
        y - this.getY(),
        z - this.getZ()
    );
    this.setPos(blockpos.getX(), blockpos.getY(), blockpos.getZ());
  }

  @Override public @NotNull Packet<?> getAddEntityPacket() {
    return NetworkHooks.getEntitySpawningPacket(this);
  }

  public ItemStack getPickResult() {
    return new ItemStack(ModItems.TELEVISION.get());
  }

  protected void defineSynchedData() {
    this.getEntityData().define(DATA_ON_OFF, this.isOn);
  }

  public InteractionResult interact(
      @NotNull Player player,
      @NotNull InteractionHand interactionHand
  ) {
    if (!this.level.isClientSide && interactionHand == InteractionHand.OFF_HAND) {
      this.toggleOnOff();

      return InteractionResult.CONSUME;
    }

    return InteractionResult.PASS;
  }

  public boolean isOn() {
    return this.getEntityData().get(DATA_ON_OFF);
  }

  public void toggleOnOff() {
    this.isOn = !this.isOn;

    this.getEntityData().set(DATA_ON_OFF, this.isOn);
  }

  @Override
  protected void recalculateBoundingBox() {
    if (this.direction != null) { // this check is necessary
      double d0 = (double)this.pos.getX() + 0.5D;
      double d1 = (double)this.pos.getY() + 0.5D;
      double d2 = (double)this.pos.getZ() + 0.5D;

      d0 -= (double)this.direction.getStepX() * 0.46875D;
      d2 -= (double)this.direction.getStepZ() * 0.46875D;

      this.setPosRaw(d0, d1, d2);

      double d6 = this.getWidth();
      double d7 = this.getHeight();
      double d8 = this.getWidth();

      if (this.direction.getAxis() == Direction.Axis.Z) {
        d8 = 1.0D;
      } else {
        d6 = 1.0D;
      }

      d6 /= 32.0D;
      d7 /= 32.0D;
      d8 /= 32.0D;

      this.setBoundingBox(new AABB(
          d0 - d6,
          d1 - d7,
          d2 - d8,
          d0 + d6,
          d1 + d7,
          d2 + d8
      ));
    }
  }

}
