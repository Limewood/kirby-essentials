package com.buncord.kirbyessentials.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PockerShulkerItem extends Item {

  private long lastUseTime = 0L;

  public PockerShulkerItem(Properties properties) {
    super(properties);
  }

  public @NotNull InteractionResultHolder<ItemStack> use(
      @NotNull Level level,
      @NotNull Player player,
      @NotNull InteractionHand interactionHand
  ) {
    ItemStack itemStack = player.getItemInHand(interactionHand);

    if (!level.isClientSide) {
      level.playSound(
          null,
          player.getX(),
          player.getY(),
          player.getZ(),
          SoundEvents.SHULKER_AMBIENT,
          SoundSource.PLAYERS,
          0.5F,
          1.0F
      );

      lastUseTime = level.getGameTime();

      return InteractionResultHolder.consume(itemStack);
    }

    return InteractionResultHolder.fail(itemStack);
  }

  public long getLastUseTime() {
    return lastUseTime;
  }

}
