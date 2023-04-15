package com.buncord.kirbyessentials;

import com.buncord.kirbyessentials.items.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(KirbyEssentials.MODID)
public class KirbyEssentials {
	public static final String MODID = "kirbyessentials";

	public KirbyEssentials() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC, "kirby-essentials-common.toml");
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModItems.register(eventBus);
	}

}
