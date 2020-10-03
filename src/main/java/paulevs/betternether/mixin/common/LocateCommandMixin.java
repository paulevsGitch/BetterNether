package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import net.minecraft.server.command.LocateCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.StructureFeature;
import paulevs.betternether.world.BNWorldGenerator;
import paulevs.betternether.world.CityHelper;

@Mixin(LocateCommand.class)
public class LocateCommandMixin
{
	@Shadow
	@Final
	private static SimpleCommandExceptionType FAILED_EXCEPTION;
	
	@Shadow
	public static int sendCoordinates(ServerCommandSource source, String structure, BlockPos sourcePos, BlockPos structurePos, String successMessage)
	{
		return 0;
	}

	@Inject(method = "execute", at = @At("HEAD"), cancellable = true)
	private static void execute(ServerCommandSource source, StructureFeature<?> structureFeature, CallbackInfoReturnable<Integer> info) throws CommandSyntaxException
	{
		if (structureFeature == BNWorldGenerator.CITY)
		{
			BlockPos blockPos = new BlockPos(source.getPosition());
			BlockPos blockPos2 = CityHelper.getNearestCity(blockPos, source.getWorld());
			if (blockPos2 == null)
			{
				throw FAILED_EXCEPTION.create();
			}
			else
			{
				info.setReturnValue(sendCoordinates(source, structureFeature.getName(), blockPos, blockPos2, "commands.locate.success"));
				info.cancel();
			}
		}
	}
}
