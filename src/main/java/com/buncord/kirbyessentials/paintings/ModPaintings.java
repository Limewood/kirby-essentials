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
    public static final RegistryObject<Motive> BLELEH = registerPainting("bleleh",48,32);
    public static final RegistryObject<Motive> KRB_LOGO = registerPainting("krb_logo",32,32);
    public static final RegistryObject<Motive> KRB = registerPainting("krb",48,32);
    public static final RegistryObject<Motive> FRIENDERMAN = registerPainting("frienderman",32,64);
    public static final RegistryObject<Motive> ENDPORTAL = registerPainting("endportal",64,48);
    public static final RegistryObject<Motive> TELEVISION = registerPainting("television",48,32);
    public static final RegistryObject<Motive> BABY = registerPainting("baby",64,48);
    public static final RegistryObject<Motive> BRIONY_ELYTRA = registerPainting("briony_elytra",64,48);
    public static final RegistryObject<Motive> MILK_BUCKET = registerPainting("milk_bucket",64,48);
    public static final RegistryObject<Motive> NEVER_ARGUE = registerPainting("never_argue",64,48);
    public static final RegistryObject<Motive> NO_THOUGHTS = registerPainting("no_thoughts",64,48);
    public static final RegistryObject<Motive> NOT_SHITTING = registerPainting("not_shitting",64,48);
    public static final RegistryObject<Motive> STINKY_OUT = registerPainting("stinky_out",64,48);

    public static final RegistryObject<Motive> BABY2 = registerPainting("baby2",64,48);

    private static RegistryObject<Motive> registerPainting(String name, int width, int height) {
        return PAINTING_MOTIVES.register(name, () -> new Motive(width, height));
    }

    public static void register(IEventBus eventBus) {
        PAINTING_MOTIVES.register(eventBus);
    }
}
