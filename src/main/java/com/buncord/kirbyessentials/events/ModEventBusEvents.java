package com.buncord.kirbyessentials.events;

import com.buncord.kirbyessentials.KirbyEssentials;
import com.buncord.kirbyessentials.events.loot.ColdplayCDAdditionModifier;
import com.buncord.kirbyessentials.events.loot.PikachuGameCubeInFortressAdditionModifier;
import com.buncord.kirbyessentials.events.loot.TetrisGameCubeInFortressAdditionModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod.EventBusSubscriber(modid = KirbyEssentials.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerModifierSerializers(
            @Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event
    ) {
        event.getRegistry().registerAll(
                new PikachuGameCubeInFortressAdditionModifier.Serializer().setRegistryName
                        (new ResourceLocation(KirbyEssentials.MOD_ID,"pikachu_game_cube_locations")),
                new TetrisGameCubeInFortressAdditionModifier.Serializer().setRegistryName
                        (new ResourceLocation(KirbyEssentials.MOD_ID,"tetris_game_cube_locations")),
                new ColdplayCDAdditionModifier.Serializer().setRegistryName
                        (new ResourceLocation(KirbyEssentials.MOD_ID,"coldplay_cd_locations"))
        );
    }

    @SubscribeEvent
    public static void enqueue(final InterModEnqueueEvent evt) {
        InterModComms.sendTo(
            CuriosApi.MODID,
            SlotTypeMessage.REGISTER_TYPE,
            () -> SlotTypePreset.BACK.getMessageBuilder().build()
        );
    }

}
