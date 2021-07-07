package paulevs.betternether.mixin.common;

import net.minecraft.world.level.lighting.LayerLightSectionStorage;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LayerLightSectionStorage.class)
public class LightStorageMixin {
	/*
	 * @Shadow protected ChunkNibbleArray getLightArray(long sectionPos, boolean
	 * cached) { return null; }
	 */

	/*
	 * @Inject(method = "get", at = @At(value = "HEAD"), cancellable = true)
	 * private void lightFix(long blockPos, CallbackInfoReturnable<Integer>
	 * info) { try { long l = ChunkSectionPos.fromGlobalPos(blockPos);
	 * ChunkNibbleArray chunkNibbleArray = this.getLightArray(l, true);
	 * info.setReturnValue(chunkNibbleArray.get(ChunkSectionPos.getLocalCoord(
	 * BlockPos.unpackLongX(blockPos)),
	 * ChunkSectionPos.getLocalCoord(BlockPos.unpackLongY(blockPos)),
	 * ChunkSectionPos.getLocalCoord(BlockPos.unpackLongZ(blockPos))));
	 * info.cancel(); } catch (Exception e) { info.setReturnValue(0);
	 * info.cancel(); } }
	 */
}
