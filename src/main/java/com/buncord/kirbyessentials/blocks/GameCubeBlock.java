package com.buncord.kirbyessentials.blocks;

import com.buncord.kirbyessentials.events.GameCubeEvent;
import com.buncord.kirbyessentials.sounds.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GameCubeBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE = Block.box(5, 0, 5, 11, 4, 11);
    private static final String DESCRIPTION_KEY = "block.kirbyessentials.game_cube";
    private static final long STARTUP_SOUND_DURATION = 5 * 1_000; // 5 s
    private static final float STARTUP_SOUND_VOLUME = 4f;
    private final DyeColor color;
    private final Type type;
    private long soundStartTime = 0;

    public GameCubeBlock(DyeColor color) {
        this(color, Type.REGULAR);
    }

    public GameCubeBlock(DyeColor color, Type type) {
        super(BlockBehaviour.Properties.of(Material.METAL, color)
                .strength(0.5f)
                .explosionResistance(1f)
                .noOcclusion()
                .sound(SoundType.CANDLE));
        this.color = color;
        this.type = type;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public @NotNull BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @NotNull MutableComponent getName() {
        String translationKey;
        switch (type) {
            case GOLDEN -> translationKey = "material.kirbyessentials.gold";
            case PIKACHU -> translationKey = "material.kirbyessentials.pikachu";
            case TETRIS -> translationKey = "material.kirbyessentials.tetris";
            default -> translationKey = "color.minecraft." + color.getName();
        }
        return new TranslatableComponent(
                DESCRIPTION_KEY,
                new TranslatableComponent(translationKey)
        );
    }

    @Override
    public @NotNull VoxelShape getShape(
            @NotNull BlockState blockState,
            @NotNull BlockGetter blockGetter,
            @NotNull BlockPos blockPos,
            @NotNull CollisionContext collisionContext
    ) {
        return SHAPE;
    }

    @Override
    public @NotNull InteractionResult use(
            @NotNull BlockState blockState,
            @NotNull Level level,
            @NotNull BlockPos blockPos,
            @NotNull Player player,
            @NotNull InteractionHand hand,
            @NotNull BlockHitResult hitResult) {
        if ((System.currentTimeMillis() - soundStartTime) < STARTUP_SOUND_DURATION) {
            return InteractionResult.FAIL;
        }
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            level.blockEvent(blockPos, this, 0, 0);
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public boolean triggerEvent(BlockState blockState, Level level, BlockPos blockPos, int intVar1, int intVar2) {
        GameCubeEvent.Play e = new GameCubeEvent.Play(level, blockPos, blockState);
        if (MinecraftForge.EVENT_BUS.post(e)) return false;
        SoundEvent soundEvent;
        switch (type) {
            case PIKACHU -> soundEvent = ModSounds.GAME_CUBE_STARTUP_SOUND_PIKACHU.get();
            case TETRIS -> soundEvent = ModSounds.GAME_CUBE_STARTUP_SOUND_TETRIS.get();
            default -> soundEvent = ModSounds.GAME_CUBE_STARTUP_SOUND.get();
        }
        level.playSound(null, blockPos, soundEvent, SoundSource.RECORDS, STARTUP_SOUND_VOLUME, 1f);
        soundStartTime = System.currentTimeMillis();
        return true;
    }

    public enum Type {
        REGULAR,
        GOLDEN,
        PIKACHU,
        TETRIS
    }
}
