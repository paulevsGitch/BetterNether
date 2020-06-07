package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.server.command.LocateCommand;

@Mixin(LocateCommand.class)
public class LocateMixin
{
	/*@Shadow
    private static int execute(ServerCommandSource source, String name)
	{
        throw new AssertionError();
    }
	
	@Inject(method = "register", at = @At(value = "RETURN"))
	private static void onRegister(CommandDispatcher<ServerCommandSource> dispatcher, CallbackInfo info)
	{
	    dispatcher.register(CommandManager.literal("locate").requires(source -> source.hasPermissionLevel(2))
	          .then(CommandManager.literal("NetherCity").executes(ctx -> execute(ctx.getSource(), "nether_city"))));
	}*/
}
