package net.frozenblock.lightsOn.block;

import com.mojang.serialization.MapCodec;
import net.frozenblock.lightsOn.blockentity.WorklightStandBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class WorklightStandBlock extends BaseEntityBlock {
    private static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
    public WorklightStandBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {return simpleCodec(WorklightStandBlock::new);}

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WorklightStandBlockEntity(pos, state);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    @SuppressWarnings("deprecation")
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }
}
