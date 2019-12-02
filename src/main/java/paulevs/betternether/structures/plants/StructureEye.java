package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import paulevs.betternether.blocks.BlocksRegister;
import paulevs.betternether.structures.IStructure;

public class StructureEye implements IStructure
{

	@Override
	public void generate(World world, BlockPos pos, Random random)
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
			h = 5 + random.nextInt(h2 / 3);
		}
		IBlockState vineState = BlocksRegister.BLOCK_EYE_VINE.getDefaultState();
		IBlockState eyeState = null;
		if (random.nextInt(2) == 0)
			eyeState = BlocksRegister.BLOCK_EYEBALL.getDefaultState();
		else
			eyeState = BlocksRegister.BLOCK_EYEBALL_SMALL.getDefaultState();
		for (int y = 0; y < h; y++)
			world.setBlockState(pos.down(y), vineState);
		world.setBlockState(pos.down(h), eyeState);
	}

}
