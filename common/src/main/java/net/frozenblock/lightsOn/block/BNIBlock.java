package net.frozenblock.lightsOn.block;

import com.mojang.serialization.MapCodec;
import net.frozenblock.lib.voxel.VoxelShapes;
import net.frozenblock.lightsOn.item.BlockNetWrench;
import net.frozenblock.lightsOn.item.FloppyDiskItem;
import net.frozenblock.lightsOn.screen.BlockNetInterfaceScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.lwjgl.system.NonnullDefault;

/**
 * BlockNet Interface block
 * @since 1.0
 * */
@NonnullDefault
public class BNIBlock extends BaseEntityBlock {
    public static final MapCodec<BNIBlock> CODEC = simpleCodec(BNIBlock::new);
    public static final BooleanProperty CONTAINS_FLOPPY = BooleanProperty.create("contains_floppy");
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public BNIBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BNIBlockEntity(pos, state);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(CONTAINS_FLOPPY);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext ctx) {
        return defineShape(state);
    }

    private VoxelShape defineShape(BlockState state) {
        var a = VoxelShapes.javaBB(0,0,0, 16,4,16);
        var b = VoxelShapes.javaBB(2,4,10, 12,10,6);
        var c = VoxelShapes.javaBB(0,4,0, 16,12,10);
        return VoxelShapes.union(a,b,c).rotateHorizontal(state.getValue(FACING)).build();
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return defineShape(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult result) {
        if(world.getBlockEntity(pos) instanceof  BNIBlockEntity bni) {
            if (world.isClientSide()) {
                final Runnable runnable = () -> Minecraft.getInstance().setScreen(new BlockNetInterfaceScreen(bni));
                runnable.run();
            }
            return InteractionResult.sidedSuccess(world.isClientSide());
        }
        return InteractionResult.PASS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack itemStack = player.getItemInHand(hand);
        if(itemStack.getItem() instanceof BlockNetWrench) {
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        } else if(player.getMainHandItem().getItem() instanceof FloppyDiskItem && level.getBlockEntity(pos) instanceof BNIBlockEntity bni) {
            ItemStack copy = itemStack.copy();
            copy.setCount(1);
            itemStack.shrink(1);
            bni.setItem(copy);
            level.setBlock(pos, state.setValue(CONTAINS_FLOPPY, true), 3);
            return ItemInteractionResult.CONSUME;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        if(level.getBlockEntity(pos) instanceof BNIBlockEntity bni) {
            bni.ejectDisk();
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}