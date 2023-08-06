/*
 * Copyright (c) 2019-2020 C4
 *
 * This file is part of Curious Elytra, a mod made for Minecraft.
 *
 * Curious Elytra is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Curious Elytra is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Curious Elytra.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.buncord.kirbyessentials.items.elytra;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nonnull;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo.Map;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

public class CurioElytra implements ICurio {

  public static final AttributeModifier ELYTRA_CURIO_MODIFIER =
      new AttributeModifier(UUID.fromString("c754faef-9926-4a77-abbe-e34ef0d735aa"),
          "Elytra curio modifier", 1.0D, AttributeModifier.Operation.ADDITION);

  private static final List<IElytraProvider> ELYTRA_PROVIDERS = new ArrayList<>();

  static {
    ELYTRA_PROVIDERS.add(new VanillaElytraProvider());
    ELYTRA_PROVIDERS.add(new BrionyElytraProvider());
    ELYTRA_PROVIDERS.add(new KirstyElytraProvider());
    ELYTRA_PROVIDERS.add(new RythianElytraProvider());
  }

  private final ItemStack stack;

  public CurioElytra(ItemStack stack) {
    this.stack = stack;
  }

  @Override
  public ItemStack getStack() {
    return this.stack;
  }

  @Override
  public void curioTick(SlotContext slotContext) {
    LivingEntity livingEntity = slotContext.entity();
    int ticks = livingEntity.getFallFlyingTicks();

    if (ticks > 0 && livingEntity.isFallFlying()) {
      this.stack.elytraFlightTick(livingEntity, ticks);
    }
  }

  @Override
  public boolean canEquip(SlotContext slotContext) {
    LivingEntity livingEntity = slotContext.entity();
    ICuriosHelper curiosHelper = CuriosApi.getCuriosHelper();
    return !(livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ElytraItem) &&
        curiosHelper.findFirstCurio(
            livingEntity,
            stack -> curiosHelper.getCurio(stack)
                                 .map(curio -> curio instanceof CurioElytra)
                                 .orElse(false)
        ).isEmpty();
  }

  @Nonnull
  @Override
  public SoundInfo getEquipSound(SlotContext slotContext) {
    return new SoundInfo(SoundEvents.ARMOR_EQUIP_ELYTRA, 1.0F, 1.0F);
  }

  @Override
  public boolean canEquipFromUse(SlotContext slotContext) {
    return true;
  }

  public static Optional<Pair<IElytraProvider, ItemStack>> getElytra(
      final LivingEntity livingEntity,
      boolean shouldFly
  ) {
    AtomicReference<IElytraProvider> atomicProvider = new AtomicReference<>();
    AtomicReference<ItemStack> atomicStack = new AtomicReference<>(ItemStack.EMPTY);

    CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).ifPresent(curios -> {
      for (Map.Entry<String, ICurioStacksHandler> entry : curios.getCurios().entrySet()) {
        IDynamicStackHandler stacks = entry.getValue().getStacks();

        for (int i = 0; i < stacks.getSlots(); i++) {
          ItemStack stack = stacks.getStackInSlot(i);

          for (IElytraProvider provider : ELYTRA_PROVIDERS) {
            if (provider.matches(stack) && (!shouldFly || provider.canFly(stack, livingEntity))) {
              atomicProvider.set(provider);
              atomicStack.set(stack);
            }
          }
        }
      }
    });

    IElytraProvider resultProvider = atomicProvider.get();
    ItemStack resultStack = atomicStack.get();

    return resultProvider != null
           ? Optional.of(new Pair<>(resultProvider, resultStack))
           : Optional.empty();
  }

}