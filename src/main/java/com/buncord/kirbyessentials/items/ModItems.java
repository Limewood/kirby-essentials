package com.buncord.kirbyessentials.items;

import com.buncord.kirbyessentials.KirbyEssentials;
import com.buncord.kirbyessentials.entities.ModEntities;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, KirbyEssentials.MOD_ID);

    public static final RegistryObject<Item> KLADDKAKA_CUPCAKE = ITEMS.register("kladdkaka_cupcake",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(ModFoods.KLADDKAKA_CUPCAKE)));

    public static RegistryObject<Item> TELEVISION = ITEMS.register("television",
        () -> new TelevisionItem(
            ModEntities.TELEVISION_ENTITY_TYPE,
            new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)
        ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
