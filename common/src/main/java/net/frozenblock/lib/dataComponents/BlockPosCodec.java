package net.frozenblock.lib.dataComponents;

import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.*;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BlockPosCodec implements Codec<BlockPos> {
    @Override
    public <T> DataResult<T> encode(BlockPos input, DynamicOps<T> ops, T prefix) {
        if(input == null) return ops.mergeToPrimitive(prefix,ops.createString("null"));
        final ListBuilder<T> builder = ops.listBuilder();
        builder.add(Codec.INT.encodeStart(ops, input.getX()));
        builder.add(Codec.INT.encodeStart(ops, input.getY()));
        builder.add(Codec.INT.encodeStart(ops, input.getZ()));
        return builder.build(prefix);
    }


    //TODO: Handle null values
    @Override
    public <T> DataResult<Pair<BlockPos, T>> decode(DynamicOps<T> ops, T input) {
        return ops.getList(input).setLifecycle(Lifecycle.stable()).flatMap(stream -> {
            final DecoderState<T> decoder = new DecoderState<>(ops);
            stream.accept(decoder::accept);
            return decoder.build();
        });
    }

    @Override
    public String toString() {
        return "BlockPosCodec";
    }

    private static class DecoderState<T> {
        private static final DataResult<Unit> INITIAL_RESULT = DataResult.success(Unit.INSTANCE, Lifecycle.stable());

        private final DynamicOps<T> ops;
        private final Stream.Builder<T> failed = Stream.builder();
        private DataResult<Unit> result = INITIAL_RESULT;
        final List<Integer> list = new ArrayList<>();

        private DecoderState(final DynamicOps<T> ops) {
            this.ops = ops;
        }

        public void accept(final T value) {
            final DataResult<Pair<Integer, T>> elementResult = Codec.INT.decode(ops, value);
            elementResult.error().ifPresent(error -> failed.add(value));
            elementResult.resultOrPartial().ifPresent(pair -> list.add(pair.getFirst()));
            result = result.apply2stable((result, element) -> result, elementResult);
        }

        public DataResult<Pair<BlockPos, T>> build() {
            final T errors = ops.createList(failed.build());
            final Pair<BlockPos, T> pair = Pair.of(new BlockPos(list.get(0), list.get(1), list.get(2)), errors);
            return result.map(ignored -> pair).setPartial(pair);
        }
    }
}
