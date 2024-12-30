package net.frozenblock.lightsOn.registry;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.block.BNIBlockEntity;
import net.frozenblock.lightsOn.block.BNLBlockEntity;
import net.frozenblock.lightsOn.block.LightBeamBlockEntity;
import net.frozenblock.lightsOn.block.WorkLightQuadripodBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class RegisterBlockEntities {

    public static final BlockEntityType<LightBeamBlockEntity> LIGHT_BEAM = BlockEntityType.Builder.of(LightBeamBlockEntity::new, RegisterBlocks.LIGHT_BEAM).build(null);
    public static final BlockEntityType<BNIBlockEntity> BLOCKNET_INTERFACE = BlockEntityType.Builder.of(BNIBlockEntity::new, RegisterBlocks.BLOCKNET_INTERFACE).build(null);
    public static final BlockEntityType<BNLBlockEntity> BLOCKNET_LINK = BlockEntityType.Builder.of(BNLBlockEntity::new, RegisterBlocks.BLOCKNET_LINK).build(null);
    public static final BlockEntityType<WorkLightQuadripodBlockEntity> WORKLIGHT_QUADRIPOD = BlockEntityType.Builder.of(WorkLightQuadripodBlockEntity::new, RegisterBlocks.WORKLIGHT_QUADRIPOD).build(null);

    public static void register() {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, LightsOnConstants.id("light_beam"), LIGHT_BEAM);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, LightsOnConstants.id("blocknet_interface"), BLOCKNET_INTERFACE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, LightsOnConstants.id("blocknet_link"), BLOCKNET_LINK);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, LightsOnConstants.id("worklight_quadripod"), WORKLIGHT_QUADRIPOD);
    }
}
