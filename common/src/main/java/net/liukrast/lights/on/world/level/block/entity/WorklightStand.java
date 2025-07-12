package net.liukrast.lights.on.world.level.block.entity;

import net.liukrast.lib.block_entity.ClientSyncedBlockEntity;
import net.liukrast.lib.blocknet.BlockNetConfigurable;
import net.liukrast.lib.blocknet.BlockNetSettings;
import net.liukrast.lib.blocknet.setting.RangedBlockNetSetting;
import net.liukrast.lights.on.registry.RegisterBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class WorklightStand extends ClientSyncedBlockEntity implements BlockNetConfigurable {

    private float yaw;
    private float rightPitch, leftPitch;
    private float height;

    public WorklightStand(@NotNull BlockPos pos, @NotNull BlockState state) {
        super(RegisterBlockEntityTypes.WORKLIGHT_STAND, pos, state);
    }

    @Override
    public void save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putFloat("Yaw", yaw);
        tag.putFloat("RightPitch", rightPitch);
        tag.putFloat("LeftPitch", leftPitch);
        tag.putFloat("Height", height);
    }

    @Override
    public void load(CompoundTag tag, HolderLookup.Provider registries) {
        yaw = tag.getFloat("Yaw");
        rightPitch = tag.getFloat("RightPitch");
        leftPitch = tag.getFloat("LeftPitch");
        height = tag.getFloat("Height");
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
        setChanged();
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getRightPitch() {
        return this.rightPitch;
    }

    public float getLeftPitch() {
        return this.leftPitch;
    }

    public float getHeight() {
        return this.height;
    }

    /*
    public void defineSettings(BlockNetSettings builder) {
        builder.add(new RangedBlockNetSetting("Yaw", -180, 180, this::getYaw));
        builder.add(new RangedBlockNetSetting("RightPitch", -90, 90, this::getRightPitch));
        builder.add(new RangedBlockNetSetting("LeftPitch", -90, 90, this::getLeftPitch));
        builder.add(new RangedBlockNetSetting("Height", 1, this::getHeight));
    }
    */

    @Override
    public BlockNetSettings getSettings() {
        return null;
    }

    @Override
    public void updateSettings(CompoundTag tag) {

    }
}
