package com.buncord.kirbyessentials.events;

import com.buncord.kirbyessentials.KirbyEssentials;
import com.buncord.kirbyessentials.items.ModItems;
import com.buncord.kirbyessentials.items.PocketShulkerItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.buncord.kirbyessentials.Constants.*;

@Mod.EventBusSubscriber(modid = KirbyEssentials.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SetupEvents {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String PROPERTY_NAME_PLAYER = "player";

	@SubscribeEvent
	static void onClientSetup(FMLClientSetupEvent evt) {
		evt.enqueueWork(() -> {
			// Replace healing potions for the players with unique models
			ItemProperties.register(Items.POTION, new ResourceLocation(KirbyEssentials.MOD_ID, PROPERTY_NAME_PLAYER),
					(itemStack, level, entity, holdingEntityId) -> {
						List<MobEffectInstance> mobEffects = PotionUtils.getPotion(itemStack).getEffects();
						if (mobEffects.size() > 0) {
							MobEffect mobEffect = mobEffects.get(0).getEffect();
							if (mobEffect.equals(MobEffects.HEAL)) {
								if (entity != null) { // holdingEntityId 195 == main hand, 194 == off hand
									String entityName = entity.getName().getString();
									switch (entityName) {
										case USERNAME_RYTHIAN -> {
											return 0.1f;
										}
										case USERNAME_KIRSTY -> {
											return 0.2f;
										}
										case USERNAME_BRIONY -> {
											return 0.3f;
										}
									}
								}
							}
						}
						return 0;
					});

			ItemProperties.register(
					ModItems.POCKET_SHULKER.get(),
					new ResourceLocation(KirbyEssentials.MOD_ID, "shulker_open"),
					(itemStack, level, entity, holdingEntityId) -> {
						Entity actualEntity = entity != null ? entity : itemStack.getEntityRepresentation();

						CompoundTag compoundTag = itemStack.getTag();

						if (actualEntity != null && compoundTag != null) {
							if (level == null && actualEntity.level instanceof ClientLevel) {
								level = (ClientLevel)actualEntity.level;
							}

							if (level != null) {
								long lastUseTime = compoundTag.getLong(PocketShulkerItem.TAG_LAST_USE_TIME);

								if (lastUseTime > 0) {
									long gameTime = level.getGameTime();

									long timeDifference = Math.max(0, Math.min(10, gameTime - lastUseTime));

									long openState = 10 - timeDifference;

									return openState > 0 ? 0.1F : 0.0F;
								}
							}
						}

						return 0.0F;
					}
			);
		});
	}
}
