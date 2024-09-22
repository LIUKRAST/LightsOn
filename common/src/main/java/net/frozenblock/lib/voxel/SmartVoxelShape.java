package net.frozenblock.lib.voxel;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;

public class SmartVoxelShape {
    private ArrayList<Box> boxes = new ArrayList<>();

    public SmartVoxelShape() {}

    private SmartVoxelShape(ArrayList<Box> boxes) {
        this.boxes = boxes;
    }

    public static SmartVoxelShape box(int x, int y, int z, int x1, int y1, int z1) {
        SmartVoxelShape shape = new SmartVoxelShape();
        shape.add(x,y,z,x1,y1,z1);
        return shape;
    }

    public SmartVoxelShape rotateHorizontal(Direction direction) {
        translate(-8,0,-8);
        rotateRaw(direction);
        translate(8,0,8);
        return this;
    }

    public SmartVoxelShape rotateAxis(Direction.Axis axis) {
        translate(-8,-8,-8);
        return this;
    }

    public SmartVoxelShape copy() {
        ArrayList<Box> copyableBox = new ArrayList<>();
        for(Box box : this.boxes) {
            copyableBox.add(new Box(box.x, box.y, box.z, box.x1, box.y1, box.z1));
        }

        return new SmartVoxelShape(copyableBox);
    }

    public SmartVoxelShape facing(Direction direction) {
        if(direction.getAxis().isHorizontal()) {
            rotateHorizontal(direction);
        } else {
            if(direction == Direction.UP) {
                for(int i = 0; i < boxes.size(); i++) {
                    Box box = boxes.get(i);
                    int y = 16 - box.z;
                    int z = box.y;
                    int y1 = 16 - box.z1;
                    int z1 = box.y1;
                    boxes.set(i, new Box(box.x, y, z, box.x1, y1, z1));
                }
            } else {
                for(int i = 0; i < boxes.size(); i++) {
                    Box box = boxes.get(i);
                    int y = box.z;
                    int z = 16 - box.y;
                    int y1 = box.z1;
                    int z1 = 16 - box.y1;
                    boxes.set(i, new Box(box.x, y, z, box.x1, y1, z1));
                }
            }
        }
        return this;
    }

    public static SmartVoxelShape rotateHorizontal(SmartVoxelShape shape, Direction direction) {
        SmartVoxelShape smart = shape.copy();
        smart.rotateHorizontal(direction);
        return smart;
    }


    /**Direction.NORD = 0 Degrees
     * Direction.EAST = 90 Degrees
     * Direction.SOUTH = 180 Degrees
     * Direction.WEST = 270 Degrees
     * */
    public void rotateRaw(Direction direction) {
        int s = 0;
        int c = 1;
        switch (direction) {
            case EAST -> {s = 1;c = 0;}
            case SOUTH -> c = -1;
            case WEST -> {s = -1;c = 0;}
        }

        for(int i = 0; i < boxes.size(); i++) {
            Box box = boxes.get(i);
            int x = box.x;
            int z = box.z;
            int fx = x * c - z * s;
            int fz = x * s + z * c;

            int x1 = box.x1;
            int z1 = box.z1;
            int fx1 = x1 * c - z1 * s;
            int fz1 = x1 * s + z1 * c;
            boxes.set(i, new Box(fx, box.y, fz, fx1, box.y1, fz1));
        }
    }

    public void translate(int x, int y, int z) {
        for(int i = 0; i < boxes.size(); i++) {
            Box o = boxes.get(i);
            Box n = new Box(o.x + x, o.y + y, o.z + z, o.x1 + x, o.y1 + y, o.z1 + z);
            boxes.set(i, n);
        }
    }

    private void add(Box box) {
        boxes.add(box);
    }

    public void add(int x, int y, int z, int x1, int y1, int z1) {
        add(new Box(x,y,z,x1,y1,z1));
    }

    public VoxelShape build() {
        ArrayList<VoxelShape> shapes = new ArrayList<>();
        for(Box box : boxes) {
            shapes.add(VoxelShapes.smartBox(box.x,box.y,box.z,box.x1,box.y1,box.z1));
        }
        VoxelShape f = null;
        for(VoxelShape shape : shapes) {
            if(f == null) {
                f = shape;
                continue;
            }
            f = Shapes.or(f, shape);
        }
        return f;
    }



    public void addAll(SmartVoxelShape t) {
        ArrayList<Box> tBoxes = t.getBoxes();
        for(Box box : tBoxes) {
            this.add(box);
        }
    }

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    public record Box(int x, int y, int z, int x1, int y1, int z1) {
        public Box fixedBoxCopy() {
            int x = Math.min(this.x,this.x1);
            int y = Math.min(this.y,this.y1);
            int z = Math.min(this.z,this.z1);
            int x1 = Math.max(this.x,this.x1);
            int y1 = Math.max(this.y,this.y1);
            int z1 = Math.max(this.z,this.z1);
            return new Box(x,y,z,x1,y1,z1);
        }

        public FloatBox floatCopy() {
            return new FloatBox((float)x/16,(float)y/16,(float)z/16,(float)x1/16,(float)y1/16,(float)z1/16);
        }
    }

    public record FloatBox(float x, float y, float z, float x1, float y1, float z1) {}
}
