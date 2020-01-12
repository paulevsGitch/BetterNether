package paulevs.betternether.structures;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public interface IStructureWorld
{
	void generate(IWorld world, BlockPos pos);
	
	default float getAirFraction(IWorld world, BlockPos pos)
	{
		return 1;
	}
	
	default float getAirFractionBottom(IWorld world, BlockPos pos)
	{
		return 1;
	}
	
	default void randomize(Random random)
	{
		
	}
	
	default void generateSurface(IWorld world, BlockPos pos, Random random)
	{
		randomize(random);
		if (getAirFraction(world, pos) > 0.8 && getAirFractionBottom(world, pos) < 0.3)
		{
			generate(world, pos);
		}
	}
	
	default void generateSubterrain(IWorld world, BlockPos pos, Random random)
	{
		randomize(random);
		if (getAirFraction(world, pos) < 0.2)
		{
			generate(world, pos);
		}
	}
	
	default void generateLava(IWorld world, BlockPos pos, Random random)
	{
		randomize(random);
		generate(world, pos);
	}
}
