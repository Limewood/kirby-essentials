package com.buncord.kirbyessentials.events;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Cancelable;

public class GameCubeEvent extends BlockEvent {
    public GameCubeEvent(LevelAccessor world, BlockPos pos, BlockState state) {
        super(world, pos, state);
    }

    @Cancelable
    public static class Play extends GameCubeEvent {
        public Play(LevelAccessor world, BlockPos pos, BlockState state) {
            super(world, pos, state);
        }
    }
}