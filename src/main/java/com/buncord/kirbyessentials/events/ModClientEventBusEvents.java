package com.buncord.kirbyessentials.events;

import com.buncord.kirbyessentials.KirbyEssentials;
import com.buncord.kirbyessentials.entities.ModEntities;
import com.buncord.kirbyessentials.renderers.MissingPosterRenderer;
import com.buncord.kirbyessentials.renderers.TelevisionRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = KirbyEssentials.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ModClientEventBusEvents {

    @SubscribeEvent
    public static void entityRenderers(RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.TELEVISION_ENTITY_TYPE, TelevisionRenderer::new);
        event.registerEntityRenderer(
            ModEntities.MISSING_POSTER_ENTITY_TYPE,
            MissingPosterRenderer::new
        );
    }

    @SubscribeEvent
    public static void beforeTextureStitch(TextureStitchEvent.Pre event) {
        if (!event.getAtlas().location().equals(InventoryMenu.BLOCK_ATLAS)) {
            return;
        }

        ResourceLocation loc_television_off = new ResourceLocation(
            KirbyEssentials.MOD_ID,
            TelevisionRenderer.TELEVISION_OFF_SPRITE_PATH
        );
        event.addSprite(loc_television_off);

        ResourceLocation loc_television_on = new ResourceLocation(
            KirbyEssentials.MOD_ID,
            TelevisionRenderer.TELEVISION_ON_SPRITE_PATH
        );
        event.addSprite(loc_television_on);

        ResourceLocation loc_missing_poster = new ResourceLocation(
            KirbyEssentials.MOD_ID,
            MissingPosterRenderer.MISSING_POSTER_SPRITE_PATH
        );
        event.addSprite(loc_missing_poster);
    }

}
