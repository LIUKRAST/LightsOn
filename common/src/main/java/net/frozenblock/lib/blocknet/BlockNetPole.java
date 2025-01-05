package net.frozenblock.lib.blocknet;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.Set;

/**
 * Defines the block entity is a pole for BlockNet network,
 * allowing it to be connected with other blocknet elements.
 * To make this work,
 * we recommend invoking {@link #loadBlockPosList(CompoundTag)} & {@link #saveBlockPosList(CompoundTag)}
 * when saving/loading your block entity data so that it gets saved.
 * See an example in {@link net.frozenblock.lightsOn.blockentity.LightBeamBlockEntity#save(CompoundTag, HolderLookup.Provider)}
 * and {@link net.frozenblock.lightsOn.blockentity.LightBeamBlockEntity#load(CompoundTag, HolderLookup.Provider)}
 *
 * @since 1.0
 * @author LiukRast
 * */
public interface BlockNetPole {

    static BlockNetPole simple(Set<BlockPos> poles, Runnable setChanged) {
        return new BlockNetPole() {
            @Override
            public Set<BlockPos> getPoles() {
                return poles;
            }

            @Override
            public void addPole(BlockPos pole) {
                poles.add(pole);
                setChanged.run();
            }

            @Override
            public void removePole(BlockPos pole) {
                poles.remove(pole);
                setChanged.run();
            }
        };
    }

    /**
     * The key under which all the BlockNet Pole data will be saved
     * @since 1.0 */
    String POLE_KEY = "NetworkPoles";

    /**
     * @return The set of all blockStates which this block entity should be connected to.
     * You should include a {@link Set} in your block entity class and make this method returns it
     * @since 1.0
     * @author LiukRast
     * */
    Set<BlockPos> getPoles();

    /**
     * @param pole The pole which wants to be added to the network.
     *             This is called when a {@link net.frozenblock.lightsOn.item.BlockNetWrench} is interacting with blocks
     * @since 1.0
     * @author LiukRast
     * */
    void addPole(BlockPos pole);
    /**
     * @param pole The pole which wants to be removed from the network.
     *             This is called when another {@link BlockNetPole} is removed and so this has to be updated.
     * @since 1.0
     * @author LiukRast
     * */
    void removePole(BlockPos pole);

    /**
     * A util method to save the BlockPos list of your poles to the compound tag.
     * @param tag The compound where your data will be saved. Uses {@link #POLE_KEY} as a key
     * @since 1.0
     * @author LiukRast
     * */
    default void saveBlockPosList(CompoundTag tag) {
        ListTag outList = new ListTag();
        for(BlockPos pos : getPoles()) {
            final IntArrayTag posTag = new IntArrayTag(new int[]{pos.getX(), pos.getY(), pos.getZ()});
            outList.add(posTag);
        }
        tag.put(POLE_KEY, outList);
    }

    /**
     * A util method to load the BlockPos list of your poles to the compound tag.
     * @param tag The compound where your data will be loaded. Uses {@link #POLE_KEY} as a key
     * @since 1.0
     * @author LiukRast
     * */
    default void loadBlockPosList(CompoundTag tag) {
        Set<BlockPos> poles = getPoles();
        poles.clear();
        if(tag.contains(POLE_KEY)) {
            for(Tag iat : tag.getList(POLE_KEY, Tag.TAG_INT_ARRAY)) {
                final var asList = ((IntArrayTag)iat);
                BlockPos pos = new BlockPos(
                        asList.get(0).getAsInt(),
                        asList.get(1).getAsInt(),
                        asList.get(2).getAsInt()
                );
                poles.add(pos);
            }
        }
    }

    /**
     * A util method which checks if the BlockEntity has a precise pole.
     * Its already built and relied on other methods of this interface
     * @param pole The pole we're checking if its present.
     * @since 1.0
     * @author LiukRast
     * */
    default boolean hasPole(BlockPos pole) {
        return getPoles().contains(pole);
    }
}
