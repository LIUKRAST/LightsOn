package net.frozenblock.lightsOn.block;

import net.frozenblock.lightsOn.block.properties.NeonType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class NeonBlock extends Block {
    public static final EnumProperty<Direction.Axis> FACING = BlockStateProperties.AXIS;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<NeonType> TYPE = EnumProperty.create("type", NeonType.class);

    public static final VoxelShape Y_SHAPE = Block.box(5, 0, 5, 11, 16, 11);
    public static final VoxelShape X_SHAPE = Block.box(0, 5, 5, 16, 11, 11);
    public static final VoxelShape Z_SHAPE = Block.box(5, 5, 0, 11, 11, 16);

    public NeonBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, TYPE);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        var level = context.getLevel();
        var blockPos = context.getClickedPos();
        var axis = context.getClickedFace().getAxis();
        var topState = level.getBlockState(blockPos.relative(axis,1));
        boolean top = topState.getBlock() instanceof NeonBlock && topState.getValue(FACING).equals(axis);
        var bottomState = level.getBlockState(blockPos.relative(axis,-1));
        boolean bottom = bottomState.getBlock() instanceof NeonBlock && bottomState.getValue(FACING).equals(axis);
        return defaultBlockState().setValue(FACING, axis).setValue(TYPE, NeonType.fromTopAndBottom(top, bottom));
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
        var axis = state.getValue(FACING);
        var topState = level.getBlockState(pos.relative(axis,1));
        boolean top = topState.getBlock() instanceof NeonBlock && topState.getValue(FACING).equals(axis);
        var bottomState = level.getBlockState(pos.relative(axis,-1));
        boolean bottom = bottomState.getBlock() instanceof NeonBlock && bottomState.getValue(FACING).equals(axis);
        var type = NeonType.fromTopAndBottom(top, bottom);
        if(type != state.getValue(TYPE)) level.setBlock(pos, state.setValue(TYPE, type), 3);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case X -> X_SHAPE;
            case Y -> Y_SHAPE;
            case Z -> Z_SHAPE;
        };
    }
}
