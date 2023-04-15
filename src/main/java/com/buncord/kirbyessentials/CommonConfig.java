package com.buncord.kirbyessentials;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Boolean> KEEP_XP_ON_DEATH;

    static {
        BUILDER.push("Configs for Kirby essentials");

        KEEP_XP_ON_DEATH = BUILDER.comment("Whether a player will keep their experience when they die or drop it as normal")
                .define("keepXPonDeath", false);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}