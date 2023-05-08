package com.buncord.kirbyessentials.blocks;

import com.buncord.kirbyessentials.KirbyEssentials;
import com.buncord.kirbyessentials.items.GameCubeBlockItem;
import com.buncord.kirbyessentials.items.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, KirbyEssentials.MOD_ID);

    public static final RegistryObject<Block> GAME_CUBE_PURPLE = registerBlock(
            "game_cube_purple", () -> new GameCubeBlock(DyeColor.PURPLE), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GAME_CUBE_BLACK = registerBlock(
            "game_cube_black", () -> new GameCubeBlock(DyeColor.BLACK), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GAME_CUBE_BROWN = registerBlock(
            "game_cube_brown", () -> new GameCubeBlock(DyeColor.BROWN), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GAME_CUBE_BLUE = registerBlock(
            "game_cube_blue", () -> new GameCubeBlock(DyeColor.BLUE), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GAME_CUBE_CYAN = registerBlock(
            "game_cube_cyan", () -> new GameCubeBlock(DyeColor.CYAN), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GAME_CUBE_GRAY = registerBlock(
            "game_cube_gray", () -> new GameCubeBlock(DyeColor.GRAY), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GAME_CUBE_GREEN = registerBlock(
            "game_cube_green", () -> new GameCubeBlock(DyeColor.GREEN), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GAME_CUBE_LIGHT_BLUE = registerBlock(
            "game_cube_light_blue", () -> new GameCubeBlock(DyeColor.LIGHT_BLUE), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GAME_CUBE_LIGHT_GRAY = registerBlock(
            "game_cube_light_gray", () -> new GameCubeBlock(DyeColor.LIGHT_GRAY), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GAME_CUBE_LIME = registerBlock(
            "game_cube_lime", () -> new GameCubeBlock(DyeColor.LIME), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GAME_CUBE_MAGENTA = registerBlock(
            "game_cube_magenta", () -> new GameCubeBlock(DyeColor.MAGENTA), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GAME_CUBE_ORANGE = registerBlock(
            "game_cube_orange", () -> new GameCubeBlock(DyeColor.ORANGE), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GAME_CUBE_PINK = registerBlock(
            "game_cube_pink", () -> new GameCubeBlock(DyeColor.PINK), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GAME_CUBE_RED = registerBlock(
            "game_cube_red", () -> new GameCubeBlock(DyeColor.RED), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GAME_CUBE_WHITE = registerBlock(
            "game_cube_white", () -> new GameCubeBlock(DyeColor.WHITE), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GAME_CUBE_YELLOW = registerBlock(
            "game_cube_yellow", () -> new GameCubeBlock(DyeColor.YELLOW), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GAME_CUBE_GOLD = registerBlock(
            "game_cube_gold", () -> new GameCubeBlock(DyeColor.YELLOW, GameCubeBlock.Type.GOLDEN), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GAME_CUBE_PIKACHU = registerBlock(
            "game_cube_pikachu", () -> new GameCubeBlock(DyeColor.YELLOW, GameCubeBlock.Type.PIKACHU), CreativeModeTab.TAB_DECORATIONS);

    private static <T extends Block> RegistryObject<T> registerBlock(
            String name, Supplier<T> block, CreativeModeTab tab
    ) {
        RegistryObject<T> registryObject = BLOCKS.register(name, block);
        registerBlockItem(name, registryObject, tab);
        return registryObject;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(
            String name, RegistryObject<T> block, CreativeModeTab tab
    ) {
        return ModItems.ITEMS.register(name, () -> new GameCubeBlockItem(
                block.get(),
                new Item.Properties()
                        .stacksTo(1)
                        .fireResistant()
                        .tab(tab)
        ));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
