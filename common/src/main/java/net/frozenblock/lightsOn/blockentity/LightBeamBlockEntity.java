package net.frozenblock.lightsOn.blockentity;

import net.frozenblock.lib.blockEntity.ClientSyncedBlockEntity;
import net.frozenblock.lib.blocknet.BlockNetConfigurable;
import net.frozenblock.lib.blocknet.BlockNetSettingBuilder;
import net.frozenblock.lib.blocknet.setting.ColorBlockNetSetting;
import net.frozenblock.lib.blocknet.setting.FloatBlockNetSetting;
import net.frozenblock.lib.blocknet.setting.RangedBlockNetSetting;
import net.frozenblock.lightsOn.render.LightBeamBlockEntityModel;
import net.frozenblock.lib.blocknet.BlockNetPole;
import net.frozenblock.lightsOn.registry.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Set;

public class LightBeamBlockEntity extends ClientSyncedBlockEntity implements BlockNetPole, BlockNetConfigurable {

    private final Set<BlockPos> poles = new HashSet<>();

    private int color = 16777215;
    private int oldColor = color;
    private float pitch = 0.0f;
    private float oldPitch = pitch;
    private float yaw = 0.0f;
    private float oldYaw = yaw;
    private float size = 1;
    private float oldSize = size;
    private float length = 3;
    private float oldLength = length;
    private int duration = 40;

    private long animationStart = 0;


    public LightBeamBlockEntity(BlockPos pos, BlockState blockState) {
        super(RegisterBlockEntities.LIGHT_BEAM, pos, blockState);
    }

    @Override
    public void save(CompoundTag nbt, HolderLookup.Provider registries) {
        nbt.putInt("Color", color);
        nbt.putInt("OldColor", oldColor);
        nbt.putFloat("Pitch", pitch);
        nbt.putFloat("OldPitch", oldPitch);
        nbt.putFloat("Yaw", yaw);
        nbt.putFloat("OldYaw", oldYaw);
        nbt.putFloat("Duration", duration);
        nbt.putLong("AnimationStart", animationStart);
        nbt.putFloat("Length", length);
        nbt.putFloat("OldLength", oldLength);
        nbt.putFloat("Size", size);
        nbt.putFloat("OldSize",oldSize);
        saveBlockPosList(nbt, poles);
    }

    @Override
    public void load(CompoundTag nbt, HolderLookup.Provider registries) {
        this.color = nbt.getInt("Color");
        this.oldColor = nbt.getInt("OldColor");
        this.pitch = nbt.getFloat("Pitch");
        this.oldPitch = nbt.getFloat("OldPitch");
        this.yaw = nbt.getFloat("Yaw");
        this.oldYaw = nbt.getFloat("OldYaw");
        this.duration = nbt.getInt("Duration");
        this.animationStart = nbt.getLong("AnimationStart");
        this.size = nbt.getFloat("Size");
        this.oldSize = nbt.getFloat("OldSize");
        this.length = nbt.getFloat("Length");
        this.oldLength = nbt.getFloat("OldLength");
        this.poles.clear();
        loadBlockPosList(nbt, poles);
    }

    public int calculateColor(float age) {
        float r = ((color>>16)&0xFF);
        float g = ((color>>8)&0xFF);
        float b = ((color)&0xFF);
        float or = ((oldColor>>16)&0xFF);
        float og = ((oldColor>>8)&0xFF);
        float ob = ((oldColor)&0xFF);
        int fr = (int) interpolate(or, r, age);
        int fg = (int) interpolate(og, g, age);
        int fb = (int) interpolate(ob, b, age);
        return (fr << 16) | (fg << 8) | fb;
    }

    public float calculatePitch(float age) {
        return interpolate(oldPitch, pitch, age);
    }

    public float calculateYaw(float age) {
        return interpolate(oldYaw, yaw, age);
    }

    public float calculateSize(float age) {
        return interpolate(oldSize, size, age);
    }

    public float calculateLength(float age) {
        return interpolate(oldLength, length, age);
    }

    public float[] getProgress(float partial) {
        int i = this.duration;
        if(i <= 0) {
            return new float[]{1,1,1,1,1};
        }
        float[] array = new float[5];
        for(int k = 0; k < 5; k++) {
            @SuppressWarnings("all")
            float f = level.getGameTime() - this.animationStart;
            float g = f + partial;
            array[k] = LightBeamBlockEntityModel.lim(getProgress(g, i), 0.0f, 1.0f);
        }
        return array;
    }

    private static float getProgress(float v, float e) {
        return (v - (float) 0.0) / (e - (float) 0.0);
    }

    private static float interpolate(float min, float max, float x) {
        float size = max - min;
        float eq;
        if(x < 0.5) {
            eq = ease(x * 2)/2;
        } else {
            eq = (1 + (1 - ease(1-(x * 2 - 1))))/2;
        }
        return min + eq * size;
    }

    private static float ease(float x) {
        return (float) Math.pow(x, 2);
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

    public int getColor() {
        return this.color;
    }

    public float getPitch() {
        return this.pitch;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getSize() {
        return this.size;
    }

    public float getLength() {
        return this.length;
    }

    @Override
    public void defineSettings(BlockNetSettingBuilder builder) {
        builder.add(new ColorBlockNetSetting("color", this::getColor));
        builder.add(new RangedBlockNetSetting("pitch", 90, this::getPitch));
        builder.add(new FloatBlockNetSetting("yaw", this::getYaw));
        builder.add(new RangedBlockNetSetting("beam_size", 100, this::getSize));
        builder.add(new RangedBlockNetSetting("beam_length", 100, this::getLength));
    }

    @Override
    public void updateData(CompoundTag tag) {
        this.oldColor = color;
        this.oldPitch = pitch;
        this.oldYaw = yaw;
        this.oldSize = size;
        this.oldLength = length;

        this.duration = tag.getInt("duration");

        this.color = tag.getInt("color");
        this.pitch = tag.getFloat("pitch");
        this.yaw = tag.getFloat("yaw");
        this.size = tag.getFloat("beam_size");
        this.length = tag.getFloat("beam_length");

        if(level != null) {
            this.animationStart = level.getGameTime();
        }

        setChanged();
        if(level != null) this.level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }
}
