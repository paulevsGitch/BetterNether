package paulevs.betternether.blocks;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class BlockSoulGrass extends BlockNetherGrass
{
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
	{
		return world.getBlockState(pos.down()).getBlock() == Blocks.SOUL_SAND;
	}
	
	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		if (random.nextInt(4) == 0)
		{
			/*world.addParticle(
					new DustParticleEffect(
							0.7137F + random.nextFloat() * 0.1F,
							0.3922F + random.nextFloat() * 0.1F,
							0.8784f + random.nextFloat() * 0.1F,
							random.nextFloat() * 0.3F + 0.3F
						),
					pos.getX() + random.nextDouble(),
					pos.getY() + random.nextDouble() * 0.5,
					pos.getZ() + random.nextDouble(),
					random.nextDouble() * 0.05,
					random.nextDouble() * 0.05,
					random.nextDouble() * 0.05);*/
			
			world.addParticle(
					ParticleTypes.PORTAL,
					pos.getX() + random.nextDouble(),
					pos.getY() + random.nextDouble() * 2,
					pos.getZ() + random.nextDouble(),
					random.nextDouble() * 0.05,
					-1,
					random.nextDouble() * 0.05);
		}
	}
}
