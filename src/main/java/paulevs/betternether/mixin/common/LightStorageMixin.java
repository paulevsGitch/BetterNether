package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.chunk.ChunkNibbleArray;
import net.minecraft.world.chunk.light.LightStorage;

@Mixin(LightStorage.class)
public class LightStorageMixin
{
	@Shadow
	protected ChunkNibbleArray getLightArray(long sectionPos, boolean cached)
	{
		return null;
	}
	
	@Inject(method = "get", at = @At(value = "HEAD"), cancellable = true)
	private void lightFix(long blockPos, CallbackInfoReturnable<Integer> info)
	{
		try
		{
			long l = ChunkSectionPos.fromGlobalPos(blockPos);
			ChunkNibbleArray chunkNibbleArray = this.getLightArray(l, true);
			info.setReturnValue(chunkNibbleArray.get(ChunkSectionPos.getLocalCoord(BlockPos.unpackLongX(blockPos)), ChunkSectionPos.getLocalCoord(BlockPos.unpackLongY(blockPos)), ChunkSectionPos.getLocalCoord(BlockPos.unpackLongZ(blockPos))));
			info.cancel();
		}
		catch (Exception e)
		{
			info.setReturnValue(0);
			info.cancel();
		}
	}
}
