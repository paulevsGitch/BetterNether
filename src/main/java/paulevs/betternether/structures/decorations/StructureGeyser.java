package paulevs.betternether.structures.decorations;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.structures.IStructure;

public class StructureGeyser implements IStructure
{
	@Override
	public void generate(IWorld world, BlockPos pos, Random random)
	{
		if (BlocksHelper.isNetherrack(world.getBlockState(pos.down())))
			BlocksHelper.setWithoutUpdate(world, pos, BlocksRegister.GEYSER.getDefaultState());
	}
}
