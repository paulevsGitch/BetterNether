package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.chunk.light.LightStorage;

@Mixin(LightStorage.class)
public class LightStorageMixin
{
	/*@Shadow
	protected ChunkNibbleArray getLightArray(long sectionPos, boolean cached)
	{
		return null;
	}*/
	
	/*@Inject(method = "get", at = @At(value = "HEAD"), cancellable = true)
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
	}*/
}
