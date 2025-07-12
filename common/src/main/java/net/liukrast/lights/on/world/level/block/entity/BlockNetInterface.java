package net.liukrast.lights.on.world.level.block.entity;

import net.liukrast.lib.blocknet.BlockNetConfigurable;
import net.liukrast.lights.LightsOnConstants;
import net.liukrast.lights.on.network.protocol.game.EditorUpdatePacket;
import net.liukrast.lights.on.platform.Services;
import net.liukrast.lights.on.project.BlockNetProject;
import net.liukrast.lib.blocknet.BlockNetPole;
import net.liukrast.lights.on.registry.RegisterBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.NonnullDefault;

import java.util.HashSet;
import java.util.Set;

@NonnullDefault
public class BlockNetInterface extends BlockEntity implements BlockNetPole {

    private boolean playing = false;
    private long currentTime = 0;

    public static final String DATA_KEY = "ProjectData";

    private final BlockNetProject blockNetProject = new BlockNetProject();

    public final ContainerData container = new ContainerData() {
        @Override
        public int get(int index) {
            if(index == 0) return (int) (((float)currentTime / blockNetProject.duration) * 256);
            if(index == 1) return playing ? 1 : 0; //TODO: Stop current animation!
            if(index == 2) return Math.toIntExact(currentTime);
            if(index == 3) return Math.toIntExact(blockNetProject.duration);
            return 0;
        }

        @Override
        public void set(int index, int value) {
            if(index == 0) currentTime = (long) ((value/256f)*blockNetProject.duration);
            if(index == 1) playing = value > 0;
            if(index == 2) currentTime = Math.clamp(value, 0, blockNetProject.duration);
            //TODO: Set project length
            setChanged();
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    @Nullable
    public Player connectedPlayer;

    @NotNull
    private final Set<BlockPos> poles = new HashSet<>();

    public BlockNetInterface(BlockPos pos, BlockState state) {
        super(RegisterBlockEntityTypes.BLOCKNET_INTERFACE, pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put(DATA_KEY, blockNetProject.saveAdditional());
        tag.putLong("CurrentTime", currentTime);
        tag.putBoolean("Playing", playing);
        saveBlockPosList(tag);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.blockNetProject.loadAdditional(tag.getCompound(DATA_KEY));
        this.currentTime = tag.getLong("CurrentTime");
        this.playing = tag.getBoolean("Playing");
        loadBlockPosList(tag);
    }

    public void tick() {
        if(!playing || level == null || level.isClientSide) return;
        var frame = blockNetProject.get(currentTime);
        if(frame != null) {
            frame.forEach((pos, tag) -> {
                if(!(level.getBlockEntity(pos) instanceof BlockNetConfigurable bnc)) return;
                bnc.updateSettings(tag);
            });
        }
        setCurrentTime(Math.clamp(currentTime+1, 0, blockNetProject.duration));
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
        setChanged();
    }

    @Override
    public void addPole(BlockPos input) {
        poles.add(input);
        setChanged();
    }

    @Override
    public @NotNull Set<BlockPos> getPoles() {
        return poles;
    }

    @Override
    public void removePole(BlockPos pos) {
        poles.remove(pos);
        setChanged();
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if(connectedPlayer != null && connectedPlayer instanceof ServerPlayer player && level != null)
            Services.PACKET_HELPER.send2Player(player, new EditorUpdatePacket(saveWithFullMetadata(level.registryAccess())));
    }
}
