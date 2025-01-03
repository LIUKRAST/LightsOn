package net.frozenblock.lightsOn.blocknet;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.List;

/**
 * Defines the block entity is pole block net usable element.
 * Allows to be connected with other blocknet elements
 * */
public interface BlockNetPole {

    String POLE_KEY = "NetworkPoles";

    List<BlockPos> getPoles();

    void addPole(BlockPos pole);
    void removePole(BlockPos pole);

    default void saveBlockPosList(CompoundTag tag, List<BlockPos> blockPosList, String key) {
        ListTag outList = new ListTag();
        for(BlockPos pos : blockPosList) {
            final IntArrayTag posTag = new IntArrayTag(new int[]{pos.getX(), pos.getY(), pos.getZ()});
            outList.add(posTag);
        }
        tag.put(key, outList);
    }

    default void loadBlockPosList(CompoundTag tag, List<BlockPos> blockPosList, String key) {
        blockPosList.clear();
        if(tag.contains(key)) {
            for(Tag iat : tag.getList(key, Tag.TAG_INT_ARRAY)) {
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
