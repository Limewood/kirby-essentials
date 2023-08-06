package com.buncord.kirbyessentials.events;

import static com.buncord.kirbyessentials.items.elytra.CurioElytra.getElytra;

import com.buncord.kirbyessentials.KirbyEssentials;
import com.buncord.kirbyessentials.items.elytra.CurioElytra;
import com.buncord.kirbyessentials.items.ModItems;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import top.theillusivec4.caelus.api.CaelusApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

@Mod.EventBusSubscriber(modid = KirbyEssentials.MOD_ID, bus = Bus.FORGE)
public class ForgeEventBusEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesEvent(final AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack stack = event.getObject();

        boolean attachable = stack.getItem() == Items.ELYTRA ||
            stack.getItem() == ModItems.ELYTRA_BRIONY.get() ||
            stack.getItem() == ModItems.ELYTRA_KIRSTY.get() ||
            stack.getItem() == ModItems.ELYTRA_RYTHIAN.get();

        if (attachable) {
            final LazyOptional<ICurio> elytraCurio = LazyOptional.of(() -> new CurioElytra(stack));

            event.addCapability(CuriosCapability.ID_ITEM, new ICapabilityProvider() {
                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(
                    @Nonnull Capability<T> cap,
                    @Nullable Direction side
                ) {
                    return CuriosCapability.ITEM.orEmpty(cap, elytraCurio);
                }
            });

            event.addListener(elytraCurio::invalidate);
        }
    }

    @SubscribeEvent
    public static void playerTick(final PlayerTickEvent evt) {
        Player player = evt.player;
        AttributeInstance attributeInstance =
            player.getAttribute(CaelusApi.getInstance().getFlightAttribute());

        if (attributeInstance != null) {
            attributeInstance.removeModifier(CurioElytra.ELYTRA_CURIO_MODIFIER);

            if (
                !attributeInstance.hasModifier(CurioElytra.ELYTRA_CURIO_MODIFIER) &&
                    getElytra(player, true).isPresent()
            ) {
                attributeInstance.addTransientModifier(CurioElytra.ELYTRA_CURIO_MODIFIER);
            }
        }
    }

}
