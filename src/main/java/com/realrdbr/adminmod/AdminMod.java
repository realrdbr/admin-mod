package com.realrdbr.adminmod;

import com.realrdbr.adminmod.config.AdminModConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AdminMod implements ModInitializer {

    public static final String MOD_ID = "admin-mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static AdminModConfig config;

    @Override
    public void onInitialize() {
        loadConfig();

        // Reload config every time the server starts (supports /stop + restart workflow)
        ServerLifecycleEvents.SERVER_STARTING.register(server -> loadConfig());

        // Register /adminmod command (accessible by admins and real OPs)
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(
                        CommandManager.literal("adminmod")
                                .requires(source -> source.hasPermission(2))
                                .then(CommandManager.literal("reload")
                                        .executes(ctx -> {
                                            loadConfig();
                                            ctx.getSource().sendFeedback(
                                                    () -> Text.literal("[AdminMod] Konfiguration erfolgreich neu geladen!"),
                                                    false
                                            );
                                            return 1;
                                        })
                                )
                                .then(CommandManager.literal("info")
                                        .executes(ctx -> {
                                            AdminModConfig cfg = getConfig();
                                            List<String> admins = cfg.getAdmins();
                                            List<String> cmds = cfg.getAllowedCommands();
                                            ctx.getSource().sendFeedback(
                                                    () -> Text.literal("[AdminMod] Admins (" + admins.size() + "): " + admins),
                                                    false
                                            );
                                            ctx.getSource().sendFeedback(
                                                    () -> Text.literal("[AdminMod] Erlaubte Befehle: " +
                                                            (cmds.isEmpty() ? "Alle" : cmds.toString())),
                                                    false
                                            );
                                            return 1;
                                        })
                                )
                )
        );

        LOGGER.info("[AdminMod] Mod geladen! Minecraft 1.21.11 | Admins: {}", config.getAdmins());
    }

    public static void loadConfig() {
        config = AdminModConfig.load();
        LOGGER.info("[AdminMod] Konfiguration geladen. Admins: {}", config.getAdmins());
    }

    public static AdminModConfig getConfig() {
        if (config == null) {
            config = AdminModConfig.load();
        }
        return config;
    }

    /**
     * Returns true if the given player is listed as an admin in the config.
     * Matching is done by UUID (preferred) or by player name.
     */
    public static boolean isAdmin(ServerPlayerEntity player) {
        if (config == null) return false;
        String uuid = player.getUuidAsString();
        String name = player.getName().getString();
        return config.getAdmins().contains(uuid) || config.getAdmins().contains(name);
    }

    /**
     * Returns the list of commands admins are allowed to execute.
     * An empty list means all commands are permitted.
     */
    public static List<String> getAllowedCommands() {
        return getConfig().getAllowedCommands();
    }
}
