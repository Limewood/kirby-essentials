package com.buncord.kirbyessentials.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class MegaChestBlockItem extends BlockItem {
    public MegaChestBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @NotNull Component getDescription() {
        return getBlock().getName();
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack itemStack) {
        return getBlock().getName();
    }

}
