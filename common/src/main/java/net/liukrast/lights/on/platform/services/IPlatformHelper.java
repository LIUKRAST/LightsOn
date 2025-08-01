package net.liukrast.lights.on.platform.services;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.CreativeModeTab;

public interface IPlatformHelper {

    String getModVersion();

    CreativeModeTab.Builder getCreativeTabBuilder();

    Screen createExampleScreen();
}