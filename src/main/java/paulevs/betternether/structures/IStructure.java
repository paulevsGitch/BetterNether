package paulevs.betternether.structures;

import java.util.Random;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public interface IStructure
{
	public void generate(ServerWorld world, BlockPos pos, Random random);
}
