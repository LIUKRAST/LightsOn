package net.liukrast.lights.on.world.inventory;

import net.liukrast.lights.on.registry.RegisterBlocks;
import net.liukrast.lights.on.registry.RegisterMenuTypes;
import net.liukrast.lights.on.world.level.block.entity.BlockNetInterface;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class BlockNetMenu extends AbstractContainerMenu {
    private final @Nullable BlockNetInterface blockEntity;
    public final ContainerLevelAccess access;
    public final DataSlot timeSlot;
    public final DataSlot playingSlot;

    public BlockNetMenu(int containerId, Inventory ignored) {
        this(containerId, new SimpleContainerData(2), ContainerLevelAccess.NULL, null);
    }

    public BlockNetMenu(int containerId, ContainerData containerData, ContainerLevelAccess access, @Nullable BlockNetInterface blockEntity) {
        super(RegisterMenuTypes.BLOCK_NET, containerId);
        checkContainerDataCount(containerData, 2);
        this.timeSlot = addDataSlot(DataSlot.forContainer(containerData, 0));
        this.playingSlot = addDataSlot(DataSlot.forContainer(containerData, 1));
        this.access = access;
        this.blockEntity = blockEntity;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, RegisterBlocks.BLOCKNET_INTERFACE);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if(blockEntity != null) {
            blockEntity.connectedPlayer = null;
        }
    }
}
