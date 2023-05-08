package com.buncord.kirbyessentials.events;

import com.buncord.kirbyessentials.KirbyEssentials;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
		});
	}
}
