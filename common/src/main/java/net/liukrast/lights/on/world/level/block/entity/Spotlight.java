package net.liukrast.lights.on.world.level.block.entity;

import net.liukrast.lib.block_entity.synced.InterpolatedDataHolder;
import net.liukrast.lib.block_entity.synced.AbstractInterpolatedData;
import net.liukrast.lib.block_entity.synced.ColorSyncedData;
import net.liukrast.lib.block_entity.synced.FloatSyncedData;
import net.liukrast.lib.blocknet.BlockNetConfigurable;
import net.liukrast.lib.blocknet.BlockNetSettings;
import net.liukrast.lib.blocknet.InterpolatedHolder;
import net.liukrast.lib.blocknet.setting.ColorBlockNetSetting;
import net.liukrast.lib.blocknet.setting.FloatBlockNetSetting;
import net.liukrast.lib.blocknet.setting.RangedBlockNetSetting;
import net.liukrast.lib.blocknet.BlockNetPole;
import net.liukrast.lights.on.registry.RegisterBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.NonnullDefault;

import java.util.HashSet;
import java.util.Set;

@NonnullDefault
public class Spotlight extends BlockEntity implements BlockNetPole, BlockNetConfigurable, InterpolatedHolder {
    private final Set<BlockPos> poles = new HashSet<>();
    private final InterpolatedDataHolder holder = new InterpolatedDataHolder(this);
    private final BlockNetSettings blockNetSettings = new BlockNetSettings();

    public final AbstractInterpolatedData<Integer> color = holder.register("Color", ColorSyncedData::new);
    public final AbstractInterpolatedData<Float> pitch = holder.register("Pitch", FloatSyncedData::new);
    public final AbstractInterpolatedData<Float> yaw = holder.register("Yaw", FloatSyncedData::new);
    public final AbstractInterpolatedData<Float> size = holder.register("Size", FloatSyncedData::new);
    public final AbstractInterpolatedData<Float> length = holder.register("Length", FloatSyncedData::new);

    public Spotlight(BlockPos pos, BlockState blockState) {
        super(RegisterBlockEntityTypes.SPOTLIGHT, pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        holder.saveAdditional(tag, registries);
        saveBlockPosList(tag);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        holder.loadAdditional(tag, registries);
        loadBlockPosList(tag);
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
    public BlockNetSettings getSettings() {
        blockNetSettings.init(() -> {
            blockNetSettings.add(new ColorBlockNetSetting("Color", color::get, color::set));
            blockNetSettings.add(new RangedBlockNetSetting("Pitch", 90, pitch::get, pitch::set));
            blockNetSettings.add(new FloatBlockNetSetting("Yaw", yaw::get, yaw::set));
            blockNetSettings.add(new RangedBlockNetSetting("Size", 0, 100, size::get, size::set));
            blockNetSettings.add(new RangedBlockNetSetting("Length", 0, 100, length::get, length::set));
        });
        return blockNetSettings;
    }

    @Override
    public void updateSettings(CompoundTag tag) {
        if(level == null) return;
        holder.set(tag, level);
    }

    @Override
    public InterpolatedDataHolder getHolder() {
        return holder;
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
