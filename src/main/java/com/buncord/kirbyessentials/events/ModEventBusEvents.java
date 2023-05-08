package com.buncord.kirbyessentials.events;

import com.buncord.kirbyessentials.KirbyEssentials;
import com.buncord.kirbyessentials.events.loot.PikachuGameCubeInFortressAdditionModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = KirbyEssentials.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerModifierSerializers(
            @Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event
    ) {
        event.getRegistry().registerAll(
                new PikachuGameCubeInFortressAdditionModifier.Serializer().setRegistryName
                        (new ResourceLocation(KirbyEssentials.MOD_ID,"pikachu_game_cube_in_fortress"))
        );
    }
}
