package com.buncord.kirbyessentials.potion;

import com.buncord.kirbyessentials.KirbyEssentials;
import com.buncord.kirbyessentials.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS
            = DeferredRegister.create(ForgeRegistries.POTIONS, KirbyEssentials.MOD_ID);

    public static final RegistryObject<Potion> FLIGHT_POTION = POTIONS.register("flight_potion",
            () -> new Potion(new MobEffectInstance(
                    ModEffects.FLIGHT.get(), 1800, 0,
                    true, false, true
            ))
    );
    public static final RegistryObject<Potion> FLIGHT_POTION_EXTENDED = POTIONS.register("flight_potion_extended",
            () -> new Potion(new MobEffectInstance(
                    ModEffects.FLIGHT.get(), 3600, 0,
                    true, false, true
            ))
    );

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
