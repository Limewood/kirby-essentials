package com.buncord.kirbyessentials.items;

import com.buncord.kirbyessentials.entities.MissingPosterEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HangingEntityItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class MissingPosterItem extends HangingEntityItem {

  public MissingPosterItem(
      EntityType<? extends HangingEntity> entityType,
      Properties properties
  )
  {
    super(entityType, properties);
  }

  public @NotNull InteractionResult useOn(UseOnContext ctx) {
    BlockPos blockpos = ctx.getClickedPos();
    Direction direction = ctx.getClickedFace();
    BlockPos blockpos1 = blockpos.relative(direction);
    Player player = ctx.getPlayer();
    ItemStack itemstack = ctx.getItemInHand();
    if (player != null && !this.mayPlace(player, direction, itemstack, blockpos1)) {
      return InteractionResult.FAIL;
    } else {
      Level level = ctx.getLevel();
      HangingEntity hangingentity = new MissingPosterEntity(level, blockpos1, direction);

      CompoundTag compoundtag = itemstack.getTag();
      if (compoundtag != null) {
        EntityType.updateCustomEntityTag(level, player, hangingentity, compoundtag);
      }

      if (hangingentity.survives()) {
        if (!level.isClientSide) {
          hangingentity.playPlacementSound();
          level.gameEvent(player, GameEvent.ENTITY_PLACE, blockpos);
          level.addFreshEntity(hangingentity);
        }

        itemstack.shrink(1);
        return InteractionResult.sidedSuccess(level.isClientSide);
      } else {
        return InteractionResult.CONSUME;
      }
    }
  }

}
