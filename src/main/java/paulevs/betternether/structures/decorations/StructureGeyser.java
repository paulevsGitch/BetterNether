package paulevs.betternether.structures.decorations;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureGeyser implements IStructure
{
	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random)
	{
		if (BlocksHelper.isNetherrack(world.getBlockState(pos.down())))
			BlocksHelper.setWithoutUpdate(world, pos, BlocksRegistry.GEYSER.getDefaultState());
	}
}
