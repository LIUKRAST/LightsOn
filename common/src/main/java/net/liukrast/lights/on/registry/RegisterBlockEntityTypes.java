package net.liukrast.lights.on.registry;

import net.liukrast.lights.LightsOnConstants;
import net.liukrast.lights.on.world.level.block.entity.BNIBlockEntity;
import net.liukrast.lights.on.world.level.block.entity.BNLBlockEntity;
import net.liukrast.lights.on.world.level.block.entity.Spotlight;
import net.liukrast.lights.on.world.level.block.entity.WorklightStand;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class RegisterBlockEntityTypes {

    public static final BlockEntityType<Spotlight> SPOTLIGHT = BlockEntityType.Builder.of(Spotlight::new, RegisterBlocks.SPOTLIGHT).build(null);
    public static final BlockEntityType<BNIBlockEntity> BLOCKNET_INTERFACE = BlockEntityType.Builder.of(BNIBlockEntity::new, RegisterBlocks.BLOCKNET_INTERFACE).build(null);
    public static final BlockEntityType<BNLBlockEntity> BLOCKNET_LINK = BlockEntityType.Builder.of(BNLBlockEntity::new, RegisterBlocks.BLOCKNET_LINK).build(null);
    public static final BlockEntityType<WorklightStand> WORKLIGHT_STAND = BlockEntityType.Builder.of(WorklightStand::new, RegisterBlocks.WORKLIGHT_STAND).build(null);

    public static void register() {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, LightsOnConstants.id("spotlight"), SPOTLIGHT);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, LightsOnConstants.id("blocknet_interface"), BLOCKNET_INTERFACE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, LightsOnConstants.id("blocknet_link"), BLOCKNET_LINK);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, LightsOnConstants.id("worklight_stand"), WORKLIGHT_STAND);
    }
}
