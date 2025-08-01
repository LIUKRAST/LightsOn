package net.liukrast.lights.on.world.level.block.entity;

import net.liukrast.lib.blocknet.BlockNetConfigurable;
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@NonnullDefault
public class BlockNetInterface extends BlockEntity implements BlockNetPole {

    private boolean playing = false;
    private long currentTime = 0;
    @Nullable
    private String currentProjectName;
    @Nullable
    private BlockNetProject currentProject;
    private final Map<String, BlockNetProject> savedProjects = new HashMap<>();

    public final ContainerData container = new ContainerData() {
        @Override
        public int get(int index) {
            if(index == 0) return Math.toIntExact(currentTime);
            if(index == 1) return playing ? 1 : 0;
            return 0;
        }

        @Override
        public void set(int index, int value) {
            /*if(index == 0) currentTime = (long) ((value/256f)*blockNetProject.duration);
            if(index == 1) playing = value > 0;
            if(index == 2) currentTime = Math.clamp(value, 0, blockNetProject.duration);*/
            //setChanged();
        }

        @Override
        public int getCount() {
            return 2;
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
        CompoundTag projects = new CompoundTag();
        int i = 0;
        for(String key : savedProjects.keySet()) {
            if(i >= 8) break;
            projects.put(key, savedProjects.get(key).saveAdditional());
            i++;
        }
        tag.put("SavedProjects", projects);
        if(currentProjectName != null) tag.putString("CurrentProject", currentProjectName);
        tag.putLong("CurrentTime", currentTime);
        tag.putBoolean("Playing", playing);
        saveBlockPosList(tag);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if(tag.contains("CurrentProject")) this.currentProjectName = tag.getString("CurrentProject");
        CompoundTag projects = tag.getCompound("SavedProjects");
        savedProjects.clear();
        int i = 0;
        for(String key : projects.getAllKeys()) {
            if(i>=8) break;
            var project = new BlockNetProject();
            project.loadAdditional(projects.getCompound(key));
            savedProjects.put(key,project);
            if(key.equals(currentProjectName)) currentProject = project;
            i++;
        }
        this.currentTime = tag.getLong("CurrentTime");
        this.playing = tag.getBoolean("Playing");
        loadBlockPosList(tag);
    }

    public void tick() {
        if(!playing || level == null || level.isClientSide || currentProject == null) return;
        var frame = currentProject.get(currentTime);
        if(frame != null) {
            frame.forEach((pos, tag) -> {
                if(!(level.getBlockEntity(pos) instanceof BlockNetConfigurable bnc)) return;
                bnc.updateSettings(tag);
            });
        }
        setCurrentTime(Math.clamp(currentTime+1, 0, currentProject.getDuration()));
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
