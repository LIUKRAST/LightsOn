package net.frozenblock.lightsOn.datagen;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.registry.RegisterBlocks;
import net.frozenblock.lightsOn.registry.RegisterItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class LOLangProvider extends LanguageProvider {
    public LOLangProvider(PackOutput output) {
        super(output, LightsOnConstants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.lights_on_tab", "Lights On");
        add(RegisterBlocks.LIGHT_BEAM, "Light Beam");
        add("light_beam.color", "Color: §8[0->16777216]");
        add("light_beam.pitch", "Pitch: §8[0->90]");
        add("light_beam.yaw", "Yaw:");
        add("light_beam.duration", "Duration: §8[Ticks]");
        add("light_beam.size", "Size: §8[0->100]");
        add("light_beam.length", "Length: §8[0->100]");
        add("light_beam.start", "Start");
        add(RegisterItems.BLOCKNET_WRENCH, "BlockNet Wrench");
        add(RegisterBlocks.BLOCKNET_INTERFACE, "BlockNet Interface");
        add("blocknet.file", "File");
        add("blocknet.file.new", "New");
        add("blocknet.file.open", "Open");
        add("blocknet.file.import", "Import");
        add("blocknet.file.save", "Save");
        add("blocknet.file.saveAs", "Save As");
        add("blocknet.no_project", "Create a new project,\nopen or import one\nto begin");
        add("blocknet.no_output", "No b device\nselected");
        add("blocknet.warning", "§cWarning: Auto-Save\nData is immediately\nsent to the block\n§lNo undo function");
        add("blocknet.info", "§cClick to access the\n§9§nOfficial Wiki");
        add("item.blocknet_wrench.binding", "Linking pos: [%s]");
        add("item.blocknet_wrench.unbindable", "§c§lThis block is not linkable");
        add("item.blocknet_wrench.error", "§c§lConnection failed");
        add("item.blocknet_wrench.connected", "§a§lConnection established");
        add("item.blocknet_wrench.already_bound", "§e§lBlock already bound");
    }
}
