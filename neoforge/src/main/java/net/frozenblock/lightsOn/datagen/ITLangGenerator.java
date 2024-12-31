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
        add(RegisterItems.BLOCKNET_WRENCH, "Chiave a pappagallo di BlockNet");
        add(RegisterBlocks.BLOCKNET_INTERFACE, "Interfaccia BlockNet");
        add(RegisterBlocks.BLOCKNET_LINK, "Link BlockNet");
        add(RegisterItems.FLOPPY_DISK, "Dischetto Floppy");
        add("item.blocknet_wrench.binding_tooltip", "Link della posizione: [%s]");
        add("item.blocknet_wrench.binding", "§e§lBlocco selezionato per la connessione");
        add("item.blocknet_wrench.unbindable", "§c§lQuesto blocco non può essere connesso");
        add("item.blocknet_wrench.error", "§c§lConnessione fallita");
        add("item.blocknet_wrench.connected", "§a§lConnessione stabilita");
        add("item.blocknet_wrench.already_bound", "§e§lBlocchi già connessi");

        //BLOCKNET
        add("blocknet.file.save", "Salva");
    }
}
