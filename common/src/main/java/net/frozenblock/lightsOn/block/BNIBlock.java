package net.frozenblock.lightsOn.block;

import com.mojang.serialization.MapCodec;
import net.frozenblock.lib.voxel.VoxelShapes;
import net.frozenblock.lightsOn.item.BlockNetWrench;
import net.frozenblock.lightsOn.registry.RegisterItems;
import net.frozenblock.lightsOn.screen.BlockNetInterfaceScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BNIBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public BNIBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(BNIBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new BNIBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        return defineShape(state);
    }

    private VoxelShape defineShape(BlockState state) {
        var a = VoxelShapes.javaBB(0,0,0, 16,4,16);
        var b = VoxelShapes.javaBB(2,4,10, 12,10,6);
        var c = VoxelShapes.javaBB(0,4,0, 16,12,10);
        return VoxelShapes.union(a,b,c).rotateHorizontal(state.getValue(FACING)).build();
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return defineShape(state);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext ctx) {
        return defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult result) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        final var stack = player.getMainHandItem();
        if (stack.getItem() instanceof BlockNetWrench) {
            return InteractionResult.PASS;
        } else if (blockEntity instanceof BNIBlockEntity blockNetInterface) {
            if (stack.getItem() == RegisterItems.FLOPPY_DISK) {
                ItemStack copy = stack.copy();
                copy.setCount(1);
                stack.shrink(1);
                blockNetInterface.setItem(copy);
                return InteractionResult.PASS;
            }
            if (world.isClientSide()) {
                final Runnable runnable = () -> Minecraft.getInstance().setScreen(new BlockNetInterfaceScreen(blockNetInterface));
                runnable.run();
            }
            return InteractionResult.sidedSuccess(world.isClientSide());
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        if(level.getBlockEntity(pos) instanceof BNIBlockEntity bni) {
            bni.dropStack();
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }
}