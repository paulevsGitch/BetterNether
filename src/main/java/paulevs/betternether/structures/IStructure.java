package paulevs.betternether.structures;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;

import java.util.Random;

public interface IStructure {
	public void generate(ServerWorldAccess world, BlockPos pos, Random random);
}
