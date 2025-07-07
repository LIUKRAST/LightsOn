package net.liukrast.lights.on.world.level.block.entity;

import net.liukrast.lib.block_entity.ClientSyncedBlockEntity;
import net.liukrast.lib.block_entity.synced.SyncedDataHolder;
import net.liukrast.lib.block_entity.synced.AbstractSyncedData;
import net.liukrast.lib.block_entity.synced.ColorSyncedData;
import net.liukrast.lib.block_entity.synced.FloatSyncedData;
import net.liukrast.lib.blocknet.BlockNetConfigurable;
import net.liukrast.lib.blocknet.BlockNetSettingBuilder;
import net.liukrast.lib.blocknet.setting.ColorBlockNetSetting;
import net.liukrast.lib.blocknet.setting.FloatBlockNetSetting;
import net.liukrast.lib.blocknet.setting.RangedBlockNetSetting;
import net.liukrast.lib.blocknet.BlockNetPole;
import net.liukrast.lights.on.registry.RegisterBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class Spotlight extends ClientSyncedBlockEntity implements BlockNetPole, BlockNetConfigurable {
    private final Set<BlockPos> poles = new HashSet<>();

    private final SyncedDataHolder holder = new SyncedDataHolder(this);

    public final AbstractSyncedData<Integer> color = holder.addSyncedData("Color", ColorSyncedData::new);
    public final AbstractSyncedData<Float> pitch = holder.addSyncedData("Pitch", FloatSyncedData::new);
    public final AbstractSyncedData<Float> yaw = holder.addSyncedData("Yaw", FloatSyncedData::new);
    public final AbstractSyncedData<Float> size = holder.addSyncedData("Size", FloatSyncedData::new);
    public final AbstractSyncedData<Float> length = holder.addSyncedData("Length", FloatSyncedData::new);

    public Spotlight(BlockPos pos, BlockState blockState) {
        super(RegisterBlockEntityTypes.SPOTLIGHT, pos, blockState);
    }

    @Override
    public void save(CompoundTag nbt, HolderLookup.Provider registries) {
        holder.save(nbt);
        saveBlockPosList(nbt);
    }

    @Override
    public void load(CompoundTag nbt, HolderLookup.Provider registries) {
        holder.load(nbt);
        loadBlockPosList(nbt);
    }

    @Override
    public void addPole(BlockPos input) {
        this.poles.add(input);
        setChanged();
    }

    @Override
    public Set<BlockPos> getPoles() {
        return poles;
    }

    @Override
    public void removePole(BlockPos pos) {
        this.poles.remove(pos);
        setChanged();
    }


    @Override
    public void defineSettings(BlockNetSettingBuilder builder) {
        builder.add(new ColorBlockNetSetting("Color", color::get));
        builder.add(new RangedBlockNetSetting("Pitch", 90, pitch::get));
        builder.add(new FloatBlockNetSetting("Yaw", yaw::get));
        builder.add(new RangedBlockNetSetting("Size", 1, 100, size::get));
        builder.add(new RangedBlockNetSetting("Length", 0.1f, 100, length::get));
    }

    @Override
    public void updateData(CompoundTag tag) {
        holder.load(tag);
        setChanged();
        if(level != null) this.level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }

    @Override
    public Supplier<Integer> interpolationGetter() {
        return color::getInterpolation;
    }
}
