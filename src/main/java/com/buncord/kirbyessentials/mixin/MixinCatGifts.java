package com.buncord.kirbyessentials.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mixin(targets = "net.minecraft.world.entity.animal.Cat$CatRelaxOnOwnerGoal")
abstract class MixinCatGifts {
	private static final List<String> zoofNames = new ArrayList<>();
	private static final String CAKE_NAME = "Birthday cake";

	static {
			zoofNames.add("Z0eff");
			zoofNames.add("Zoof");
			zoofNames.add("Zoeff");
	};

	@Accessor(value = "cat")
	abstract Cat getCat();

	@Accessor(value = "ownerPlayer")
	abstract Player getOwnerPlayer();

	@Accessor(value = "onBedTicks")
	abstract void setOnBedTicks(int bedTicks);

	@Inject(at = @At("HEAD"), method = "stop()V", cancellable = true)
	public void kirbyEssentials_stop(CallbackInfo ci) {
		Cat cat = getCat();
		if (cat.hasCustomName()) {
			Component catName = cat.getCustomName();
			if (catName != null) {
				if (zoofNames.contains(catName.getString())) {
					// Let Z0eff always drop a birthday cake
					cat.setLying(false);
					float f = cat.level.getTimeOfDay(1.0F);
					if (getOwnerPlayer().getSleepTimer() >= 20 && (double)f > 0.77D && (double)f < 0.8D) {
						BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
						blockpos$mutableblockpos.set(cat.blockPosition());
						Random random = cat.getRandom();
						cat.randomTeleport((double)(blockpos$mutableblockpos.getX() + random.nextInt(11) - 5), (double)(blockpos$mutableblockpos.getY() + random.nextInt(5) - 2), (double)(blockpos$mutableblockpos.getZ() + random.nextInt(11) - 5), false);
						blockpos$mutableblockpos.set(cat.blockPosition());
						ItemStack itemStack = Items.CAKE.getDefaultInstance();
						itemStack.setHoverName(new TextComponent(CAKE_NAME));
						ItemEntity cakeEntity = new ItemEntity(
								cat.level,
								(double)blockpos$mutableblockpos.getX() - (double) Mth.sin(cat.yBodyRot * ((float)Math.PI / 180F)),
								(double)blockpos$mutableblockpos.getY(), (double)blockpos$mutableblockpos.getZ() + (double)Mth.cos(cat.yBodyRot * ((float)Math.PI / 180F)),
								itemStack
						);
						cat.level.addFreshEntity(cakeEntity);
					}

					setOnBedTicks(0);
					cat.setRelaxStateOne(false);
					cat.getNavigation().stop();
					ci.cancel();
				}
			}
		}
	}
}
