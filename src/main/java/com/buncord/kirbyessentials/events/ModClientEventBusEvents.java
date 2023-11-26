package com.buncord.kirbyessentials.events;

import com.buncord.kirbyessentials.KirbyEssentials;
import com.buncord.kirbyessentials.entities.ModEntities;
import com.buncord.kirbyessentials.models.BnuyHatModel;
import com.buncord.kirbyessentials.models.FancyShirtModel;
import com.buncord.kirbyessentials.models.ElytraBrionyModel;
import com.buncord.kirbyessentials.models.ElytraKirstyModel;
import com.buncord.kirbyessentials.models.ElytraRythianModel;
import com.buncord.kirbyessentials.models.PikaHatModel;
import com.buncord.kirbyessentials.models.SantamanHatModel;
import com.buncord.kirbyessentials.renderers.ElytraVanillaLayer;
import com.buncord.kirbyessentials.renderers.ElytraBrionyLayer;
import com.buncord.kirbyessentials.renderers.ElytraKirstyLayer;
import com.buncord.kirbyessentials.renderers.ElytraRythianLayer;
import com.buncord.kirbyessentials.renderers.MissingPosterRenderer;
import com.buncord.kirbyessentials.renderers.SantamanLayer;
import com.buncord.kirbyessentials.renderers.TelevisionRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
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

        event.registerLayerDefinition(
            FancyShirtModel.LAYER_LOCATION,
            FancyShirtModel::createLayer
        );

        event.registerLayerDefinition(
            PikaHatModel.LAYER_LOCATION,
            PikaHatModel::createLayer
        );

        event.registerLayerDefinition(
            BnuyHatModel.LAYER_LOCATION,
            BnuyHatModel::createLayer
        );

        event.registerLayerDefinition(
            SantamanHatModel.LAYER_LOCATION,
            SantamanHatModel::createLayer
        );
    }

    @SubscribeEvent
    public static void onAddLayers(AddLayers evt) {
        addPlayerLayer(evt, "default");
        addPlayerLayer(evt, "slim");
        addEntityLayer(evt, EntityType.ARMOR_STAND);
        addSantamanLayer(evt, EntityType.ENDERMAN);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void addPlayerLayer(AddLayers evt, String skin) {
        EntityRenderer<? extends Player> renderer = evt.getSkin(skin);

        if (renderer instanceof LivingEntityRenderer livingRenderer) {
            livingRenderer.addLayer(new ElytraBrionyLayer<>(livingRenderer, evt.getEntityModels()));
            livingRenderer.addLayer(new ElytraKirstyLayer<>(livingRenderer, evt.getEntityModels()));
            livingRenderer.addLayer(new ElytraRythianLayer<>(livingRenderer, evt.getEntityModels()));

            livingRenderer.addLayer(new ElytraVanillaLayer(livingRenderer, evt.getEntityModels()));
        }
    }

    private static <T extends LivingEntity, M extends HumanoidModel<T>, R extends LivingEntityRenderer<T, M>>
        void addEntityLayer(EntityRenderersEvent.AddLayers evt, EntityType<? extends T> entityType)
    {
        R renderer = evt.getRenderer(entityType);

        if (renderer != null) {
            renderer.addLayer(new ElytraBrionyLayer<>(renderer, evt.getEntityModels()));
            renderer.addLayer(new ElytraKirstyLayer<>(renderer, evt.getEntityModels()));
            renderer.addLayer(new ElytraRythianLayer<>(renderer, evt.getEntityModels()));

            renderer.addLayer(new ElytraVanillaLayer<>(renderer, evt.getEntityModels()));
        }
    }

    private static <T extends LivingEntity, M extends HumanoidModel<T>, R extends LivingEntityRenderer<T, M>>
    void addSantamanLayer(EntityRenderersEvent.AddLayers evt, EntityType<? extends T> entityType)
    {
        R renderer = evt.getRenderer(entityType);

        if (renderer != null) {
            renderer.addLayer(new SantamanLayer<>(renderer, evt.getEntityModels()));
        }
    }

    @SubscribeEvent
    public static void beforeTextureStitch(TextureStitchEvent.Pre event) {
        if (!event.getAtlas().location().equals(InventoryMenu.BLOCK_ATLAS)) {
            return;
        }

        ResourceLocation loc_missing_poster = new ResourceLocation(
            KirbyEssentials.MOD_ID,
            MissingPosterRenderer.MISSING_POSTER_SPRITE_PATH
        );
        event.addSprite(loc_missing_poster);
    }

}
