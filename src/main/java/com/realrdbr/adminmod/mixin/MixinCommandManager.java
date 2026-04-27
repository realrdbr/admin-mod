package com.realrdbr.adminmod.mixin;

import com.realrdbr.adminmod.AdminMod;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CommandManager.class)
public class MixinCommandManager {

    /**
     * Intercepts every command sent by an admin player BEFORE dispatch.
     * If the allowedCommands list is non-empty and the root command is not in that list,
     * the execution is cancelled and the player receives an explanatory error message.
     *
     * If allowedCommands is empty every command is permitted (full OP bypass).
     */
    @Inject(method = "executeWithPrefix", at = @At("HEAD"), cancellable = true)
    private void adminMod_executeWithPrefix(ServerCommandSource source, String input, CallbackInfo ci) {
        Entity entity = source.getEntity();
        if (!(entity instanceof ServerPlayerEntity player)) {
            return; // console or command blocks are unaffected
        }
        if (!AdminMod.isAdmin(player)) {
            return; // not an admin – let vanilla handle it
        }

        // Extract the root command name (strip leading '/' if present)
        String trimmed = input.trim();
        if (trimmed.startsWith("/")) {
            trimmed = trimmed.substring(1);
        }
        if (trimmed.isEmpty()) {
            return;
        }
        String rootCommand = trimmed.split("\\s+")[0].toLowerCase();

        List<String> allowed = AdminMod.getAllowedCommands();
        if (!allowed.isEmpty() && !allowed.contains(rootCommand)) {
            source.sendError(Text.literal(
                    "[AdminMod] Du hast keinen Zugriff auf den Befehl: /" + rootCommand
            ));
            ci.cancel();
        }
    }
}
