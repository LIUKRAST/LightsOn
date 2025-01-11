package net.frozenblock.lightsOn.block_entity;

import net.frozenblock.lib.block_entity.ClientSyncedBlockEntity;
import net.frozenblock.lib.blocknet.BlockNetConfigurable;
import net.frozenblock.lib.blocknet.BlockNetSettingBuilder;
import net.frozenblock.lib.blocknet.setting.RangedBlockNetSetting;
import net.frozenblock.lightsOn.registry.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class WorklightStandBlockEntity extends ClientSyncedBlockEntity implements BlockNetConfigurable {

    private float yaw;
    private float rightPitch, leftPitch;
    private float height;

    public WorklightStandBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        super(RegisterBlockEntities.WORKLIGHT_STAND, pos, state);
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

    @Override
    public void defineSettings(BlockNetSettingBuilder builder) {
        builder.add(new RangedBlockNetSetting("yaw", -180, 180, this::getYaw));
        builder.add(new RangedBlockNetSetting("right_pitch", -90, 90, this::getRightPitch));
        builder.add(new RangedBlockNetSetting("left_pitch", -90, 90, this::getLeftPitch));
        builder.add(new RangedBlockNetSetting("height", 1, this::getHeight));
    }

    @Override
    public void updateData(CompoundTag tag) {
        this.yaw = tag.getFloat("yaw");
        this.rightPitch = tag.getFloat("right_pitch");
        this.leftPitch = tag.getFloat("left_pitch");
        this.height = tag.getFloat("height");
        setChanged();
        if(level != null) this.level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }
}
