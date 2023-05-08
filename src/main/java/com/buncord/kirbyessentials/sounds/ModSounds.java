package com.buncord.kirbyessentials.sounds;

import com.buncord.kirbyessentials.KirbyEssentials;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, KirbyEssentials.MOD_ID);

    public static final RegistryObject<SoundEvent> GAME_CUBE_STARTUP_SOUND =
            registerSoundEvent("game_cube_startup_sound");
    public static final RegistryObject<SoundEvent> GAME_CUBE_STARTUP_SOUND_PIKACHU =
            registerSoundEvent("game_cube_startup_sound_pikachu");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(KirbyEssentials.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
