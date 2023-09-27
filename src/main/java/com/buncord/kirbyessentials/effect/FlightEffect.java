package com.buncord.kirbyessentials.effect;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FlightEffect extends MobEffect {
    protected FlightEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public void addAttributeModifiers(@NotNull LivingEntity livingEntity, @NotNull AttributeMap attributeMap, int p_19480_) {
        super.addAttributeModifiers(livingEntity, attributeMap, p_19480_);
        if (livingEntity instanceof Player player) {
            player.getAbilities().mayfly = true;
            player.getAbilities().flying = true;
            player.onUpdateAbilities();
        }
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity livingEntity, @NotNull AttributeMap attributeMap, int p_19471_) {
        super.removeAttributeModifiers(livingEntity, attributeMap, p_19471_);
        if (livingEntity instanceof Player player) {
            if (player.isCreative()) return;
            player.getAbilities().mayfly = false;
            player.getAbilities().flying = false;
            player.onUpdateAbilities();
        }
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        super.applyEffectTick(livingEntity, amplifier);
        if (livingEntity instanceof Player player) {
            if (player.level.isClientSide) return;
            if (!player.getAbilities().mayfly) {
                player.getAbilities().mayfly = true;
                player.onUpdateAbilities();
            }
            int duration = Objects.requireNonNull(player.getEffect(this)).getDuration();
            if (duration == 300 || duration == 200 || duration == 100) {
                player.sendMessage(
                        new TextComponent((duration / 20) + " seconds of flight left!").withStyle(ChatFormatting.AQUA),
                        player.getUUID()
                );
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
