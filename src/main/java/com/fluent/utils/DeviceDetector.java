package com.fluent.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Detects the first connected Android device or emulator via adb.
 * Used by AndroidDriverFactory when udid is not set in android.properties.
 */
public final class DeviceDetector {

    private static final Logger log = LoggerFactory.getLogger(DeviceDetector.class);

    public record DeviceInfo(String udid, String model, String platformVersion) {}

    private DeviceDetector() {}

    public static DeviceInfo detect() {
        String udid = findFirstDeviceUdid();
        String model = readDeviceProp(udid, "ro.product.model");
        String version = readDeviceProp(udid, "ro.build.version.release");
        log.info("Auto-detected device: udid={}, model={}, android={}", udid, model, version);
        return new DeviceInfo(udid, model, version);
    }

    private static String findFirstDeviceUdid() {
        try {
            Process proc = Runtime.getRuntime().exec(new String[]{"adb", "devices"});
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("List of devices")) continue;
                    if (line.contains("offline") || line.contains("unauthorized")) continue;
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 2 && "device".equals(parts[1])) {
                        return parts[0];
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to run 'adb devices': " + e.getMessage(), e);
        }
        throw new RuntimeException(
            "No connected Android device or emulator found. " +
            "Connect a device via USB (with USB debugging enabled) or start an emulator, then retry."
        );
    }

    private static String readDeviceProp(String udid, String prop) {
        try {
            Process proc = Runtime.getRuntime().exec(
                new String[]{"adb", "-s", udid, "shell", "getprop", prop}
            );
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
                String line = reader.readLine();
                return line != null ? line.trim() : "";
            }
        } catch (Exception e) {
            log.warn("Could not read device property '{}': {}", prop, e.getMessage());
            return "";
        }
    }
}
