package net.frozenblock.lightsOn.datagen;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.registry.RegisterBlocks;
import net.frozenblock.lightsOn.registry.RegisterItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ENLangGenerator extends LanguageProvider {
    public ENLangGenerator(PackOutput output) {
        super(output, LightsOnConstants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //CREATIVE MODE TABS
        add("itemGroup.lights_on_tab", "Lights On");

        //ITEMS
        add(RegisterItems.BLOCKNET_WRENCH, "BlockNet Wrench");
        add(RegisterItems.BLUE_FLOPPY_DISK, "Blue Floppy Disk");
        add(RegisterItems.RED_FLOPPY_DISK, "Red Floppy Disk");
        add(RegisterItems.YELLOW_FLOPPY_DISK, "Yellow Floppy Disk");

        //BLOCKS
        add(RegisterBlocks.LIGHT_BEAM, "Light Beam");
        add(RegisterBlocks.BLOCKNET_INTERFACE, "BlockNet Interface");
        add(RegisterBlocks.BLOCKNET_LINK, "BlockNet Link");
        add(RegisterBlocks.WORKLIGHT_STAND, "Worklight Stand");
        add(RegisterBlocks.BLUE_NEON, "Blue Neon");
        add(RegisterBlocks.RED_NEON, "Red Neon");
        add(RegisterBlocks.YELLOW_NEON, "Yellow Neon");
        add(RegisterBlocks.GREEN_NEON, "Green Neon");
        add(RegisterBlocks.WHITE_NEON, "White Neon");
        add(RegisterBlocks.LIGHT_GRAY_NEON, "Light Gray Neon");
        add(RegisterBlocks.GRAY_NEON, "Gray Neon");
        add(RegisterBlocks.BLACK_NEON, "Black Neon");
        add(RegisterBlocks.BROWN_NEON, "Brown Neon");
        add(RegisterBlocks.ORANGE_NEON, "Orange Neon");
        add(RegisterBlocks.LIME_NEON, "Lime Neon");
        add(RegisterBlocks.CYAN_NEON, "Cyan Neon");
        add(RegisterBlocks.LIGHT_BLUE_NEON, "Light Blue Neon");
        add(RegisterBlocks.PURPLE_NEON, "Purple Neon");
        add(RegisterBlocks.MAGENTA_NEON, "Magenta Neon");
        add(RegisterBlocks.PINK_NEON, "Pink Neon");

        //WRENCH
        add("item.blocknet_wrench.unbindable", "§c§lThis block is not linkable");
        add("item.blocknet_wrench.binding", "§e§lBinding BlockPos %s");
        add("item.blocknet_wrench.failed", "§c§lConnection failed");
        add("item.blocknet_wrench.success", "§a§lConnection established");
        add("item.blocknet_wrench.already_bound", "§e§lBlocks already bound");

        //BLOCKNET CONFIG
        add("blockNet.configScreen.save", "Save");

        add("blocknet.settingType.color", "Color");
        add("blocknet.settingType.beam_size", "Beam Size");
        add("blocknet.settingType.beam_length", "Beam Length");
        add("blocknet.settingType.yaw", "Yaw");
        add("blocknet.settingType.pitch", "Pitch");
        add("blocknet.settingType.right_pitch", "Right Pitch");
        add("blocknet.settingType.left_pitch", "Left Pitch");
        add("blocknet.settingType.height", "Height");

        //BLOCKNET
        add("blocknet.file.save", "Save");
        add("blocknet.file.project", "Project");
        add("blocknet.file.settings", "Settings");

        add("blocknet.edit.undo", "Undo");
        add("blocknet.edit.redo", "Redo");

        add("blocknet.select.all", "Select All");
        add("blocknet.select.deselect", "Deselect");

        add("blocknet.view.fullscreen", "Fullscreen");

        add("blocknet.help.wiki", "Open the Wiki");
        add("blocknet.help.issue", "Report an Issue");
    }
}
