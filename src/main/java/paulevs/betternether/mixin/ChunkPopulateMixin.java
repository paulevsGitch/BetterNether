package paulevs.betternether.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.gen.chunk.ChunkGenerator;

@Mixin(ChunkGenerator.class)
public abstract class ChunkPopulateMixin
{
	private static final BlockState STONE = Blocks.STONE.getDefaultState();
	private BlockPos.Mutable pos = new BlockPos.Mutable();
	
	@Inject(method = "generateFeatures", at = @At("HEAD"))
    private void customPopulate(ChunkRegion region, CallbackInfo info)
	{
		populate(region);
    }
	
	private void populate(ChunkRegion region)
	{
		int chunkX = region.getCenterChunkX();
		int chunkZ = region.getCenterChunkZ();
		int sx = (chunkX << 4) | 8;
		int sz = (chunkZ << 4) | 8;
		for (int x = 0; x < 16; x++)
		{
			int wx = sx + x;
			int y = (int) (Math.sin(wx * 0.2) * 10 + 100);
			for (int z = 0; z < 16; z++)
			{
				int wz = sz + z;
				for (int wy = y; wy < y + 4; wy++)
					setBlockState(region, wx, wy, wz, STONE);
			}
		}
	}
	
	private void setBlockState(ChunkRegion region, int x, int y, int z, BlockState state)
	{
		pos.set(x, y, z);
		region.setBlockState(pos, state, 19);
	}
}
