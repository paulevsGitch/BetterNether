package paulevs.betternether.structures;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public interface IStructure
{
	public void generate(IWorld world, BlockPos pos, Random random);
}
