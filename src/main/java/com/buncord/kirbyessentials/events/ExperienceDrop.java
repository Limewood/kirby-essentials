package com.buncord.kirbyessentials.events;

import com.buncord.kirbyessentials.CommonConfig;
import com.buncord.kirbyessentials.KirbyEssentials;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = KirbyEssentials.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ExperienceDrop {
	private static final Logger LOGGER = LogManager.getLogger();

	@SubscribeEvent
	public static void playerXpDrop(LivingExperienceDropEvent evt) {
		if (evt.getEntityLiving() instanceof Player player) {
			if (CommonConfig.KEEP_XP_ON_DEATH.get()) {
				LOGGER.debug("Keep xp on death");
				evt.setDroppedExperience(0);
				evt.setCanceled(true);
				return;
			}
			// Drop 100% of xp
			int lostPoints = player.totalExperience;
			player.experienceProgress = 0;
			player.totalExperience = 0;
			player.experienceLevel = 0;
			int droppedXp = getDroppedExperiencePoints(player, lostPoints);
			evt.setDroppedExperience(droppedXp);
		}
	}

	@SubscribeEvent
	public static void playerRespawn(final PlayerEvent.Clone evt) {
		if (evt.isWasDeath() && CommonConfig.KEEP_XP_ON_DEATH.get()) {
			Player playerEntity = evt.getPlayer();
			Player original = evt.getOriginal();
			playerEntity.experienceProgress = original.experienceProgress;
			playerEntity.experienceLevel = original.experienceLevel;
			playerEntity.totalExperience = original.totalExperience;
		}
	}

	private static int getDroppedExperiencePoints(Player player, int lostPoints) {
		if (!player.isSpectator()) {
			return lostPoints;
		} else {
			return 0;
		}
	}
}