package com.buncord.kirbyessentials.entities;

import com.buncord.kirbyessentials.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class TelevisionEntity extends HangingEntity {

  protected TelevisionEntity(
      EntityType<? extends HangingEntity> entityType,
      Level level
  )
  {
    super(entityType, level);
  }

  protected TelevisionEntity(
      EntityType<? extends HangingEntity> entityType,
      Level level,
      BlockPos blockPos
  )
  {
    super(entityType, level, blockPos);
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

  @Override public Packet<?> getAddEntityPacket() {
    return new ClientboundAddEntityPacket(
        this,
        this.getType(),
        this.direction.get3DDataValue(),
        this.getPos()
    );
  }

}
