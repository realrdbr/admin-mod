package com.realrdbr.adminmod.mixin;

import com.realrdbr.adminmod.AdminMod;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerCommandSource.class)
public class MixinServerCommandSource {

    /**
     * Grants admins full operator permission level for any command permission check.
     * This allows the command dispatcher to expose OP-level commands to configured admins
     * during parsing and tab-completion. Actual command filtering (allowedCommands list)
     * is enforced separately in MixinCommandManager before execution.
     */
    @Inject(method = "hasPermissionLevel", at = @At("HEAD"), cancellable = true)
    private void adminMod_hasPermissionLevel(int level, CallbackInfoReturnable<Boolean> cir) {
        ServerCommandSource self = (ServerCommandSource) (Object) this;
        Entity entity = self.getEntity();
        if (entity instanceof ServerPlayerEntity player && AdminMod.isAdmin(player)) {
            cir.setReturnValue(true);
        }
    }
}
