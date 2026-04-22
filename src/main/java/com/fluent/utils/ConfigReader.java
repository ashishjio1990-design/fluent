package com.fluent.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads platform-specific .properties files from src/test/resources/config/.
 *
 * Usage:
 *   ConfigReader config = ConfigReader.forPlatform("android");
 *   String pkg = config.get("app.package");
 */
public final class ConfigReader {

    private static final Logger log = LoggerFactory.getLogger(ConfigReader.class);

    private final Properties props = new Properties();
    private final String platform;

    private ConfigReader(String platform) {
        this.platform = platform;
        load("config/" + platform + ".properties");
    }

    // ─────────────────────────── Factory ───────────────────────────────────

    /**
     * Returns a ConfigReader loaded with the given platform's properties file.
     *
     * @param platform e.g. "android"
     */
    public static ConfigReader forPlatform(String platform) {
        return new ConfigReader(platform.toLowerCase());
    }

    // ─────────────────────────── Public API ────────────────────────────────

    /**
     * Returns the value for the given key.
     *
     * @throws RuntimeException if the key is absent
     */
    public String get(String key) {
        // System property overrides file (useful for CI injection)
        String sysProp = System.getProperty(key);
        if (sysProp != null && !sysProp.isBlank()) {
            return sysProp;
        }

        String value = props.getProperty(key);
        if (value == null) {
            throw new RuntimeException(
                "Missing required config key '" + key + "' in " + platform + ".properties"
            );
        }
        return value.trim();
    }

    /**
     * Returns the value for the given key, or {@code defaultValue} if absent.
     */
    public String getOrDefault(String key, String defaultValue) {
        String sysProp = System.getProperty(key);
        if (sysProp != null && !sysProp.isBlank()) {
            return sysProp;
        }
        return props.getProperty(key, defaultValue).trim();
    }

    // ─────────────────────────── Private ───────────────────────────────────

    private void load(String resourcePath) {
        try (InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resourcePath)) {

            if (is == null) {
                throw new RuntimeException("Config file not found on classpath: " + resourcePath);
            }
            props.load(is);
            log.debug("Loaded config: {} ({} keys)", resourcePath, props.size());

        } catch (IOException e) {
            throw new RuntimeException("Failed to load config file: " + resourcePath, e);
        }
    }
}
