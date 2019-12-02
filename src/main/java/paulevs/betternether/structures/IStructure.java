package paulevs.betternether.structures;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IStructure
{
	public void generate(World world, BlockPos pos, Random random);
}
