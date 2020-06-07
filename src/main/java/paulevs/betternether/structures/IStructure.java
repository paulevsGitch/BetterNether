package paulevs.betternether.structures;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

public interface IStructure
{
	public void generate(WorldAccess world, BlockPos pos, Random random);
}
