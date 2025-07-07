package net.liukrast.lights.on.platform;

import net.liukrast.lights.LightsOnConstants;
import net.liukrast.lights.on.platform.services.IPacketHelper;
import net.liukrast.lights.on.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

public class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IPacketHelper PACKET_HELPER = load(IPacketHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        LightsOnConstants.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}