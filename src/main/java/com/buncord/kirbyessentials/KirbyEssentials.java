package com.buncord.kirbyessentials;

import com.buncord.kirbyessentials.block_entities.ModBlockEntities;
import com.buncord.kirbyessentials.blocks.ModBlocks;
import com.buncord.kirbyessentials.effect.ModEffects;
import com.buncord.kirbyessentials.entities.ModEntities;
import com.buncord.kirbyessentials.items.ModItems;
import com.buncord.kirbyessentials.paintings.ModPaintings;
import com.buncord.kirbyessentials.potion.ModPotions;
import com.buncord.kirbyessentials.sounds.ModSounds;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(KirbyEssentials.MOD_ID)
public class KirbyEssentials {
	public static final String MOD_ID = "kirbyessentials";

	public KirbyEssentials() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC, "kirby-essentials-common.toml");
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModEntities.register(eventBus);
		ModBlockEntities.register(eventBus);
		ModBlocks.register(eventBus);
		ModItems.register(eventBus);
		ModSounds.register(eventBus);
		ModPaintings.register(eventBus);
		ModEffects.register(eventBus);
		ModPotions.register(eventBus);
	}
}
