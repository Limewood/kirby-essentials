package com.buncord.kirbyessentials.events;

import com.buncord.kirbyessentials.KirbyEssentials;
import com.buncord.kirbyessentials.entities.ModEntities;
import com.buncord.kirbyessentials.events.loot.PikachuGameCubeInFortressAdditionModifier;
import com.buncord.kirbyessentials.renderers.TelevisionRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
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

    @SubscribeEvent
    public static void entityRenderers(RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.TELEVISION.get(), TelevisionRenderer::new);
    }

}
