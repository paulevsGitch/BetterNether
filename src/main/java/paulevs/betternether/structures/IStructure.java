package paulevs.betternether.structures;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;

public interface IStructure
{
	public void generate(ServerWorldAccess world, BlockPos pos, Random random);
}
