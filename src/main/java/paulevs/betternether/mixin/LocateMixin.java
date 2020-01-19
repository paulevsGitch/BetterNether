package paulevs.betternether.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.LocateCommand;
import net.minecraft.server.command.ServerCommandSource;

@Mixin(LocateCommand.class)
public class LocateMixin
{
	@Shadow
    private static int execute(ServerCommandSource source, String name)
	{
        throw new AssertionError();
    }
	
	@Inject(method = "register", at = @At(value = "RETURN"))
	private static void onRegister(CommandDispatcher<ServerCommandSource> dispatcher, CallbackInfo info)
	{
	    dispatcher.register(CommandManager.literal("locate").requires(source -> source.hasPermissionLevel(2))
	          .then(CommandManager.literal("NetherCity").executes(ctx -> execute(ctx.getSource(), "nether_city"))));
	}
}
