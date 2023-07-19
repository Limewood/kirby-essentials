package com.buncord.kirbyessentials.events;

import com.buncord.kirbyessentials.KirbyEssentials;
import com.buncord.kirbyessentials.entities.ModEntities;
import com.buncord.kirbyessentials.models.ElytraBrionyModel;
import com.buncord.kirbyessentials.models.ElytraKirstyModel;
import com.buncord.kirbyessentials.models.ElytraRythianModel;
import com.buncord.kirbyessentials.renderers.ElytraBrionyLayer;
import com.buncord.kirbyessentials.renderers.ElytraKirstyLayer;
import com.buncord.kirbyessentials.renderers.ElytraRythianLayer;
import com.buncord.kirbyessentials.renderers.MissingPosterRenderer;
import com.buncord.kirbyessentials.renderers.TelevisionRenderer;
import java.util.Set;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.AddLayers;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
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
    public static void onRegisterLayerDefinitions(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(
            ElytraBrionyModel.LAYER_LOCATION,
            ElytraBrionyModel::createLayer
        );

        event.registerLayerDefinition(
            ElytraKirstyModel.LAYER_LOCATION,
            ElytraKirstyModel::createLayer
        );

        event.registerLayerDefinition(
            ElytraRythianModel.LAYER_LOCATION,
            ElytraRythianModel::createLayer
        );
    }

    @SubscribeEvent
    public static void onAddLayers(AddLayers event) {
        Set<String> skinNames = event.getSkins();

        for (String skinName : skinNames) {
            LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> playerRenderer = event.getSkin(skinName);

            if (playerRenderer != null) {
                playerRenderer.addLayer(new ElytraBrionyLayer<>(playerRenderer, event.getEntityModels()));
                playerRenderer.addLayer(new ElytraKirstyLayer<>(playerRenderer, event.getEntityModels()));
                playerRenderer.addLayer(new ElytraRythianLayer<>(playerRenderer, event.getEntityModels()));
            }
        }
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
