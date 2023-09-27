package com.buncord.kirbyessentials.effect;

import com.buncord.kirbyessentials.KirbyEssentials;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, KirbyEssentials.MOD_ID);

    public static final RegistryObject<MobEffect> FLIGHT = MOB_EFFECTS.register("flight_effect",
            () -> new FlightEffect(MobEffectCategory.BENEFICIAL, Color.CYAN.getRGB()));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
