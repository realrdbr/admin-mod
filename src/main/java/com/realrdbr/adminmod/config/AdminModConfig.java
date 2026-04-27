package com.realrdbr.adminmod.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.realrdbr.adminmod.AdminMod;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminModConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH =
            FabricLoader.getInstance().getConfigDir().resolve("admin-mod.json");

    /**
     * List of admin players identified by UUID (preferred) or by player name.
     * Example: ["550e8400-e29b-41d4-a716-446655440000", "Steve"]
     */
    private List<String> admins = new ArrayList<>();

    /**
     * Commands that admins are allowed to execute.
     * Use the root command name without the leading slash, e.g. "gamemode".
     * An empty list means ALL commands are permitted for admins.
     */
    private List<String> allowedCommands = new ArrayList<>(Arrays.asList(
            "gamemode", "give", "tp", "teleport", "time", "weather",
            "effect", "enchant", "xp", "experience", "kill", "summon",
            "fill", "setblock", "clone", "difficulty", "gamerule",
            "spawnpoint", "defaultgamemode", "seed", "locate",
            "advancement", "attribute", "data", "execute",
            "forceload", "function", "loot", "particle",
            "playsound", "recipe", "reload", "say",
            "schedule", "scoreboard", "spreadplayers", "stopsound",
            "tag", "team", "teammsg", "title", "trigger", "worldborder"
    ));

    // -------------------------------------------------------------------------

    public static AdminModConfig load() {
        if (!Files.exists(CONFIG_PATH)) {
            AdminModConfig defaults = new AdminModConfig();
            defaults.save();
            AdminMod.LOGGER.info("[AdminMod] Neue Konfigurationsdatei erstellt: {}", CONFIG_PATH);
            return defaults;
        }
        try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
            AdminModConfig config = GSON.fromJson(reader, AdminModConfig.class);
            if (config == null) {
                return new AdminModConfig();
            }
            if (config.admins == null) {
                config.admins = new ArrayList<>();
            }
            if (config.allowedCommands == null) {
                config.allowedCommands = new ArrayList<>();
            }
            return config;
        } catch (IOException e) {
            AdminMod.LOGGER.error("[AdminMod] Fehler beim Laden der Konfiguration, nutze Standardwerte.", e);
            return new AdminModConfig();
        }
    }

    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException e) {
            AdminMod.LOGGER.error("[AdminMod] Fehler beim Speichern der Konfiguration.", e);
        }
    }

    public List<String> getAdmins() {
        return admins;
    }

    public List<String> getAllowedCommands() {
        return allowedCommands;
    }
}
