package net.frozenblock.lightsOn.block;

import net.frozenblock.lib.blockEntity.ClientSyncedBlockEntity;
import net.frozenblock.lightsOn.blocknet.BlockNetPole;
import net.frozenblock.lightsOn.registry.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class LightBeamBlockEntity extends ClientSyncedBlockEntity implements BlockNetPole {

    private final List<BlockPos> poles = new ArrayList<>();

    private int color = 16777215; //CHANNEL 0
    private int oldColor = color;

    private float pitch = 0.0f; // CHANNEL 1
    private float oldPitch = pitch;
    private float yaw = 0.0f; // CHANNEL 2
    private float oldYaw = yaw;

    private float size = 1; // CHANNEL 3
    private float oldSize = size;

    private float length = 3; // CHANNEL 4
    private float oldLength = length;

    private int duration = 40;

    private long[] animationStart = new long[5]; //Supports 5 channels


    public LightBeamBlockEntity(BlockPos pos, BlockState blockState) {
        super(RegisterBlockEntities.LIGHT_BEAM, pos, blockState);
        for(int i = 0; i < 5; i++) {
            animationStart[i] = 0;
        }

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
        nbt.putLongArray("AnimationStart", animationStart);
        nbt.putFloat("Length", length);
        nbt.putFloat("OldLength", oldLength);
        nbt.putFloat("Size", size);
        nbt.putFloat("OldSize",oldSize);
        saveBlockPosList(nbt, poles, POLE_KEY);
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
        this.animationStart = nbt.getLongArray("AnimationStart");
        this.size = nbt.getFloat("Size");
        this.oldSize = nbt.getFloat("OldSize");
        this.length = nbt.getFloat("Length");
        this.oldLength = nbt.getFloat("OldLength");
        this.poles.clear();
        loadBlockPosList(nbt, poles, POLE_KEY);
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


    public int getDuration() {
        return duration;
    }

    public void update(CompoundTag data) {
        this.oldColor = color;
        this.oldPitch = pitch;
        this.oldYaw = yaw;
        this.oldSize = size;
        this.oldLength = length;

        this.color = data.getInt("Color");
        this.pitch = data.getFloat("Pitch");
        this.yaw = data.getFloat("Yaw");
        this.duration = data.getInt("Duration");
        this.size = data.getFloat("Size");
        this.length = data.getFloat("Length");

        if(level != null) {
            for (int i = 0; i < 5; i++) {
                this.animationStart[i] = level.getGameTime();
            }
        }

        setChanged();
        if(level != null) this.level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }

    public float[] getProgress(float partial) {
        int i = this.duration;
        if(i <= 0) {
            return new float[]{1,1,1,1,1};
        }
        float[] array = new float[5];
        for(int k = 0; k < 5; k++) {
            @SuppressWarnings("all")
            float f = level.getGameTime() - this.animationStart[k];
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
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }


    @Override
    public void addPole(BlockPos input) {
        if(input == this.getBlockPos())
            return;
        if(!this.poles.contains(input)) {
            this.poles.add(input);
        }
    }

    @Override
    public List<BlockPos> getPoles() {
        return poles;
    }

    @Override
    public void removePole(BlockPos pos) {
        if(pos == this.getBlockPos())
            return;
        if(this.poles.contains(pos)) {
            this.poles.remove(pos);
        }
    }
}
