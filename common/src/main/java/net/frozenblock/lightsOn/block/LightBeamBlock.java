package net.frozenblock.lightsOn.block;

import com.mojang.serialization.MapCodec;
import net.frozenblock.lib.voxel.SmartVoxelShape;
import net.frozenblock.lib.voxel.VoxelShapes;
import net.frozenblock.lightsOn.blockentity.LightBeamBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class LightBeamBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final SmartVoxelShape SHAPE = VoxelShapes.union(
            VoxelShapes.javaBB(0,0,13,16,16,3),
            VoxelShapes.javaBB(2,2,10,12,12,3),
            VoxelShapes.javaBB(0,0,7,16,16,3),
            VoxelShapes.javaBB(0,3,-3,16,10,10)
    );
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public LightBeamBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(LightBeamBlock::new);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LightBeamBlockEntity(pos, state);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, WATERLOGGED);
    }

    @Override
    @SuppressWarnings("deprecated")
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return defineShape(state);
    }

    public VoxelShape defineShape(BlockState state) {
        return SHAPE.copy().facing(state.getValue(FACING)).build();
    }

    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState()
                .setValue(FACING, ctx.getClickedFace())
                .setValue(POWERED, ctx.getLevel().hasNeighborSignal(ctx.getClickedPos()))
                .setValue(WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos sourcePos, boolean movedByPiston) {
        if (!world.isClientSide) {
            boolean flag = state.getValue(POWERED);
            if (flag != world.hasNeighborSignal(pos)) {
                world.setBlock(pos, state.cycle(POWERED), 2);
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return defineShape(state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }


    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }
}
