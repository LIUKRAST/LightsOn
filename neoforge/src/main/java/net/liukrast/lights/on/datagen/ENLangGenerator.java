package net.liukrast.lights.on.datagen;

import net.liukrast.lights.LightsOnConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ENLangGenerator extends LanguageProvider {
    public ENLangGenerator(PackOutput output) {
        super(output, LightsOnConstants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //CREATIVE MODE TABS
        add("itemGroup.lights_on", "Lights On");

        BuiltInRegistries.ITEM.stream().filter(b -> BuiltInRegistries.ITEM.getKey(b).getNamespace().equals(LightsOnConstants.MOD_ID)).forEach(b -> add(b, autoName(BuiltInRegistries.ITEM.getKey(b).getPath())));

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

    public String autoName(String id) {
        String[] words = id.split("_");
        for(int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1);
        }
        return String.join(" ", words);
    }
}
