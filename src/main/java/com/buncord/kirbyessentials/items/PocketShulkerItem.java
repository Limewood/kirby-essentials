package com.buncord.kirbyessentials.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PocketShulkerItem extends Item {

  public static final String TAG_LAST_USE_TIME = "LastUseTime";

  public PocketShulkerItem(Properties properties) {
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

      CompoundTag compoundTag = itemStack.getOrCreateTag();
      compoundTag.putLong(TAG_LAST_USE_TIME, level.getGameTime());
    }

    return InteractionResultHolder.pass(itemStack);
  }

}
