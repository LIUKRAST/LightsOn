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
        add("itemGroup.lights_on_tab", "Lights On");
        add(RegisterBlocks.LIGHT_BEAM, "Testa rotante");
        add("light_beam.color", "Colore: §8[0->16777216]");
        add("light_beam.pitch", "Beccheggio: §8[0->90]");
        add("light_beam.yaw", "Imbardata:");
        add("light_beam.duration", "Durata transizione: §8[Ticks]");
        add("light_beam.size", "Dimensione: §8[0->100]");
        add("light_beam.length", "Lunghezza: §8[0->100]");
        add("light_beam.start", "Inizio");
        add(RegisterItems.BLOCKNET_WRENCH, "Chiave pole pappagallo di BlockNet");
        add(RegisterBlocks.BLOCKNET_INTERFACE, "Interfaccia BlockNet");
        add(RegisterBlocks.BLOCKNET_LINK, "Link BlockNet");
        add(RegisterItems.BLUE_FLOPPY_DISK, "Dischetto floppy blu");
        add(RegisterItems.RED_FLOPPY_DISK, "Dischetto floppy rosso");
        add(RegisterItems.YELLOW_FLOPPY_DISK, "Dischetto floppy giallo");
        add(RegisterBlocks.WORKLIGHT_STAND, "Faro da lavoro con supporto");



        add("item.blocknet_wrench.unbindable", "§c§lQuesto blocco non è linkabile");
        add("item.blocknet_wrench.binding", "§e§lCollegando le posizioni %s");
        add("item.blocknet_wrench.failed", "§c§lConnessione fallita");
        add("item.blocknet_wrench.success", "§pole§lConnessione stabilita");
        add("item.blocknet_wrench.already_bound", "§e§lBlocchi già connessi");



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
