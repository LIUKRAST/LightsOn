package net.frozenblock.lightsOn.blocknet;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.List;
import java.util.Set;

/**
 * Defines the block entity is pole blocknet usable element.
 * Allows to be connected with other blocknet elements
 * */
public interface BlockNetPole {

    String POLE_KEY = "NetworkPoles";

    Set<BlockPos> getPoles();

    void addPole(BlockPos pole);
    void removePole(BlockPos pole);

    default void saveBlockPosList(CompoundTag tag, Set<BlockPos> blockPosList) {
        ListTag outList = new ListTag();
        for(BlockPos pos : blockPosList) {
            final IntArrayTag posTag = new IntArrayTag(new int[]{pos.getX(), pos.getY(), pos.getZ()});
            outList.add(posTag);
        }
        tag.put(POLE_KEY, outList);
    }

    default void loadBlockPosList(CompoundTag tag, Set<BlockPos> blockPosList) {
        blockPosList.clear();
        if(tag.contains(POLE_KEY)) {
            for(Tag iat : tag.getList(POLE_KEY, Tag.TAG_INT_ARRAY)) {
                final var asList = ((IntArrayTag)iat);
                BlockPos pos = new BlockPos(
                        asList.get(0).getAsInt(),
                        asList.get(1).getAsInt(),
                        asList.get(2).getAsInt()
                );
                blockPosList.add(pos);
            }
        }
    }

    default boolean hasPole(BlockPos pole) {
        return getPoles().contains(pole);
    }
}
