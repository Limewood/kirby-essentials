package com.buncord.kirbyessentials.paintings;

import com.buncord.kirbyessentials.KirbyEssentials;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPaintings {
    public static final DeferredRegister<Motive> PAINTING_MOTIVES =
            DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, KirbyEssentials.MOD_ID);

    public static final RegistryObject<Motive> CRIME = registerPainting("crime", 48,32);
    public static final RegistryObject<Motive> EASTER = registerPainting("easter",48,32);
    public static final RegistryObject<Motive> ELYTRA = registerPainting("elytra",48,32);
    public static final RegistryObject<Motive> MUGSHOTS = registerPainting("mugshots",64,32);
    public static final RegistryObject<Motive> PRIDE = registerPainting("pride",32,32);
    public static final RegistryObject<Motive> SHEEPDANCE = registerPainting("sheepdance",48,32);
    public static final RegistryObject<Motive> UNDERWATER = registerPainting("underwater",48,32);

    private static RegistryObject<Motive> registerPainting(String name, int width, int height) {
        return PAINTING_MOTIVES.register(name, () -> new Motive(width, height));
    }

    public static void register(IEventBus eventBus) {
        PAINTING_MOTIVES.register(eventBus);
    }
}
