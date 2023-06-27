package com.buncord.kirbyessentials.block_entities;

import com.buncord.kirbyessentials.KirbyEssentials;
import com.buncord.kirbyessentials.blocks.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
      DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, KirbyEssentials.MOD_ID);

  public static final RegistryObject<BlockEntityType<FireproofDestroyerBlockEntity>> FIREPROOF_DESTROYER =
      BLOCK_ENTITIES.register(
          "fireproof_destroyer",
          () -> BlockEntityType.Builder.of(
              FireproofDestroyerBlockEntity::new,
              ModBlocks.FIREPROOF_DESTROYER.get()
          ).build(null)
  );

  public static void register(IEventBus eventBus) {
    BLOCK_ENTITIES.register(eventBus);
  }

}
