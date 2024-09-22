package net.frozenblock.lightsOn.platform;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.platform.services.IPacketHelper;
import net.frozenblock.lightsOn.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

public class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IPacketHelper PACKET_HELPER = load(IPacketHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        LightsOnConstants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}