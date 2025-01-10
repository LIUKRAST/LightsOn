package net.frozenblock.lib.blocknet;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

import java.util.List;
import java.util.Map;

public class BlockNetProject {

    public static final Codec<BlockNetProject> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.STRING.fieldOf("name").forGetter(project -> project.name),
                    Codec.BOOL.fieldOf("loop").forGetter(project -> project.loop),
                    Codec.unboundedMap(Codec.INT, Keyframe.CODEC).fieldOf("keyframes").forGetter(project -> project.keyframes)
            ).apply(instance, BlockNetProject::new)
    );

    private String name;
    private boolean loop;
    private final Map<Integer, Keyframe> keyframes;

    BlockNetProject(String name, boolean loop, Map<Integer, Keyframe> keyframes) {
        this.name = name;
        this.loop = loop;
        this.keyframes = keyframes;
    }

    public Keyframe getKeyFrame(int timestamp) {
        return keyframes.get(timestamp);
    }

    public static class Keyframe {

        public static final Codec<Keyframe> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(Codec.list(Element.CODEC).fieldOf("elements").forGetter(keyframe -> keyframe.elements))
                        .apply(instance, Keyframe::new)
        );

        private final List<Element> elements;

        Keyframe(List<Element> elements) {
            this.elements = elements;
        }

        public static class Element {

            public static final Codec<Element> CODEC = RecordCodecBuilder.create(
                    instance -> instance.group(
                            Codec.list(BlockPos.CODEC).fieldOf("positions").forGetter(element -> element.positions),
                            CompoundTag.CODEC.fieldOf("data").forGetter(element -> element.data)
                    ).apply(instance, Element::new)
            );

            private final List<BlockPos> positions;
            private final CompoundTag data;

            Element(List<BlockPos> positions, CompoundTag data) {
                this.positions = positions;
                this.data = data;
            }
        }
    }
}
