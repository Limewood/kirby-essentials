package com.buncord.kirbyessentials.entities;

import com.buncord.kirbyessentials.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TelevisionEntity extends HangingEntity implements IEntityAdditionalSpawnData {

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
    super.addAdditionalSaveData(compoundTag);
  }

  public void readAdditionalSaveData(CompoundTag compoundTag) {
    this.direction = Direction.from2DDataValue(compoundTag.getByte("Facing"));
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

}
