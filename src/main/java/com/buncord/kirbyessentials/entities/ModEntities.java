package com.buncord.kirbyessentials.entities;

import com.buncord.kirbyessentials.KirbyEssentials;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

  public static final DeferredRegister<EntityType<?>> ENTITIES =
      DeferredRegister.create(ForgeRegistries.ENTITIES, KirbyEssentials.MOD_ID);

  public static final EntityType<TelevisionEntity> TELEVISION_ENTITY_TYPE =
      EntityType.Builder.<TelevisionEntity>of(TelevisionEntity::new, MobCategory.MISC)
                        .sized(0.5F, 0.5F)
                        .clientTrackingRange(10)
                        .updateInterval(Integer.MAX_VALUE)
                        .build(KirbyEssentials.MOD_ID + ":television_entity");

  public static final RegistryObject<EntityType<TelevisionEntity>> TELEVISION = ENTITIES.register(
      "television_entity",
      () -> TELEVISION_ENTITY_TYPE
  );

  public static void register(IEventBus eventBus) {
    ENTITIES.register(eventBus);
  }

}
