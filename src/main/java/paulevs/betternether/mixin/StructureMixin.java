package paulevs.betternether.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;

@Mixin(StructureStart.class)
public class StructureMixin
{
	@Shadow
	protected BlockBox boundingBox;
	
	/*@Inject(method = "generateStructure", at = @At(value = "RETURN"))
	public void afterSetBB(IWorld world, ChunkGenerator<?> chunkGenerator, Random random, BlockBox blockBox, ChunkPos chunkPos, CallbackInfo info)
	{
		StructureStart start = (StructureStart) (Object) this;
		if (start instanceof NetherFortressFeature.Start || start instanceof CityFeature.CityStart)
		{
			if (boundingBox != BlockBox.empty())
			{
				int x1 = chunkPos.x << 4;
				int z1 = chunkPos.z << 4;
				int x2 = x1 + 15;
				int z2 = z1 + 15;
				x1 = Math.max(x1, boundingBox.minX);
				x2 = Math.min(x2, boundingBox.maxX);
				z1 = Math.max(z1, boundingBox.minZ);
				z2 = Math.min(z2, boundingBox.maxZ);
				BNWorldGenerator.fortressPass(world, x1, z1, x2, z2, boundingBox.maxY);
			}
		}
	}*/
}
