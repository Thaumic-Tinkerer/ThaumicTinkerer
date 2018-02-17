package com.nekokittygames.tinkerer.common.config;

import com.nekokittygames.tinkerer.common.Tinkerer;
import com.nekokittygames.tinkerer.common.libs.LibMisc;
import com.nekokittygames.tinkerer.common.utils.ReflectionUtil;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.io.File;
import java.lang.invoke.MethodHandle;
import java.util.Map;
import java.util.Optional;

@Config(modid = LibMisc.MOD_ID)
public class ModConfig {
    public static boolean allowTiConRepair;


    @Mod.EventBusSubscriber
    public static class ConfigurationHolder {
        private static final MethodHandle CONFIGS_GETTER = ReflectionUtil.findFieldGetter(ConfigManager.class, "CONFIGS");

        /**
         * The {@link Configuration} instance.
         */
        private static Configuration configuration;
        public static Configuration getConfiguration() {
            if (configuration == null) {
                try {
                    final String fileName = LibMisc.MOD_ID + ".cfg";

                    @SuppressWarnings("unchecked")
                    final Map<String, Configuration> configsMap = (Map<String, Configuration>) CONFIGS_GETTER.invokeExact();

                    final Optional<Map.Entry<String, Configuration>> entryOptional = configsMap.entrySet().stream()
                            .filter(entry -> fileName.equals(new File(entry.getKey()).getName()))
                            .findFirst();

                    entryOptional.ifPresent(stringConfigurationEntry -> configuration = stringConfigurationEntry.getValue());
                } catch (Throwable throwable) {
                    Tinkerer.TTLog.error("Failed to get Configuration instance",throwable);
                }
            }

            return configuration;
        }

        /**
         * Inject the new values and save to the config file when the config has been changed from the GUI.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(LibMisc.MOD_ID)) {
                ConfigManager.load(LibMisc.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}
