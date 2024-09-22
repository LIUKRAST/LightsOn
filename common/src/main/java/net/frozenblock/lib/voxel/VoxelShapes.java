package net.frozenblock.lib.voxel;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VoxelShapes {
    public static SmartVoxelShape javaBB(int x, int y, int z, int sizeX, int sizeY, int sizeZ) {
        return SmartVoxelShape.box(x,y,z,x+sizeX,y+sizeY,z+sizeZ);
    }

    public static SmartVoxelShape union(SmartVoxelShape s, SmartVoxelShape ... shapes) {
        SmartVoxelShape shape = new SmartVoxelShape();
        shape.addAll(s);
        for(SmartVoxelShape t : shapes) {
            shape.addAll(t);
        }
        return shape;
    }

    public static VoxelShape smartBox(int x, int y, int z, int x1, int y1, int z1) {
        return Block.box(Math.min(x,x1), Math.min(y,y1), Math.min(z,z1), Math.max(x,x1), Math.max(y,y1), Math.max(z,z1));
    }
}
