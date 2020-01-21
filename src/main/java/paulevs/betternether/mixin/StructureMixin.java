package paulevs.betternether.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.NetherFortressFeature;
import paulevs.betternether.world.BNWorldGenerator;

//@Mixin(StructureFeature.class)
@Mixin(StructureStart.class)
public class StructureMixin//<C extends FeatureConfig>
{
	/*@Inject(method = "generate", at = @At(value = "RETURN"))
	private void generateAfter(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, C config, CallbackInfoReturnable<Boolean> info)
	{
		StructureFeature<?> feature = (StructureFeature<?>) (Object) this;
		if (info.getReturnValue() && !world.isClient() && feature instanceof NetherFortressFeature)
		{
			//int cx = pos.getX() >> 4;
			//int cz = pos.getZ() >> 4;
			//BNWorldGenerator.fortressPass(world, cx, cz);
			world.setBlockState(pos, state, flags)
		}
	}*/
	
	@Shadow
	protected BlockBox boundingBox;
	
	@Inject(method = "generateStructure", at = @At(value = "RETURN"))
	public void afterSetBB(IWorld world, ChunkGenerator<?> chunkGenerator, Random random, BlockBox blockBox, ChunkPos chunkPos, CallbackInfo info)
	{
		
		if ((Object) this instanceof NetherFortressFeature.Start)
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
				/*for (int x = x1; x <= x2; x++)
					for (int y = boundingBox.minY; y <= boundingBox.maxY; y++)
						for (int z = z1; z <= z2; z++)
							BlocksHelper.setWithoutUpdate(world, new BlockPos(x, y, z), Blocks.DIAMOND_BLOCK.getDefaultState());*/
				BNWorldGenerator.fortressPass(world, x1, 24, z1, x2, boundingBox.maxY, z2);
			}
		}
	}
}
