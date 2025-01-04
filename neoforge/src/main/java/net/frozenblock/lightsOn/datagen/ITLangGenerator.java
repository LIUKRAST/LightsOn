package net.frozenblock.lightsOn.datagen;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.registry.RegisterBlocks;
import net.frozenblock.lightsOn.registry.RegisterItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ITLangGenerator extends LanguageProvider {
    public ITLangGenerator(PackOutput output) {
        super(output, LightsOnConstants.MOD_ID, "it_it");
    }

    @Override
    protected void addTranslations() {
        //CREATIVE MODE TABS
        add("itemGroup.lights_on_tab", "Lights On");

        //ITEMS
        add(RegisterItems.BLOCKNET_WRENCH, "Chiave pole pappagallo di BlockNet");
        add(RegisterItems.BLUE_FLOPPY_DISK, "Dischetto floppy blu");
        add(RegisterItems.RED_FLOPPY_DISK, "Dischetto floppy rosso");
        add(RegisterItems.YELLOW_FLOPPY_DISK, "Dischetto floppy giallo");

        //BLOCKS
        add(RegisterBlocks.LIGHT_BEAM, "Testa rotante");
        add(RegisterBlocks.BLOCKNET_INTERFACE, "Interfaccia BlockNet");
        add(RegisterBlocks.BLOCKNET_LINK, "Link BlockNet");
        add(RegisterBlocks.WORKLIGHT_STAND, "Faro da lavoro con supporto");

        //WRENCH
        add("item.blocknet_wrench.unbindable", "§c§lQuesto blocco non è linkabile");
        add("item.blocknet_wrench.binding", "§e§lCollegando le posizioni %s");
        add("item.blocknet_wrench.failed", "§c§lConnessione fallita");
        add("item.blocknet_wrench.success", "§pole§lConnessione stabilita");
        add("item.blocknet_wrench.already_bound", "§e§lBlocchi già connessi");

        //BLOCKNET CONFIG
        add("blockNet.configScreen.save", "Salva");

        add("blocknet.settingType.color", "Colore");
        add("blocknet.settingType.beam_size", "Dimensione Raggio");
        add("blocknet.settingType.beam_length", "Lunghezza Raggio");
        add("blocknet.settingType.yaw", "Imbardata");
        add("blocknet.settingType.pitch", "Beccheggio");
        add("blocknet.settingType.right_pitch", "Beccheggio Destro");
        add("blocknet.settingType.left_pitch", "Beccheggio Sinistro");
        add("blocknet.settingType.height", "Altezza");

        //BLOCKNET
        add("blocknet.file.save", "Salva");
        add("blocknet.file.project", "Progetto");
        add("blocknet.file.settings", "Impostazioni");

        add("blocknet.edit.undo", "Undo");
        add("blocknet.edit.redo", "Redo");

        add("blocknet.select.all", "Seleziona tutto");
        add("blocknet.select.deselect", "Deseleziona");

        add("blocknet.view.fullscreen", "Schermo Intero");

        add("blocknet.help.wiki", "Apri la Wiki");
        add("blocknet.help.issue", "Segnala un Problema");
    }
}
