package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureEye implements IStructure
{
	@Override
	public void generate(IWorld world, BlockPos pos, Random random)
	{
		int h = random.nextInt(19) + 5;
		int h2 = pos.getY() - h;
		if (h2 < 33)
		{
			return;
		}
		h2 = h;
		boolean interrupted = false;
		for (int y = 1; y < h; y++)
		{
			if (world.getBlockState(pos.down(y)).getBlock() != Blocks.AIR)
			{
				h2 = y;
				interrupted = true;
				break;
			}
		}
		if (interrupted)
		{
			if (h2 < 5)
				return;
			h = 4 + random.nextInt(h2 / 3);
		}
		
		BlockState vineState = BlocksRegistry.EYE_VINE.getDefaultState();
		BlockState eyeState = random.nextBoolean() ? BlocksRegistry.EYEBALL.getDefaultState() : BlocksRegistry.EYEBALL_SMALL.getDefaultState();
		
		for (int y = 0; y < h; y++)
			BlocksHelper.setWithoutUpdate(world, pos.down(y), vineState);
		
		BlocksHelper.setWithoutUpdate(world, pos.down(h), eyeState);
	}
}