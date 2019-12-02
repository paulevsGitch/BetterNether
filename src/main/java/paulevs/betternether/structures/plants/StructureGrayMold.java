package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import paulevs.betternether.blocks.BlockNetherMycelium;
import paulevs.betternether.blocks.BlocksRegister;
import paulevs.betternether.structures.IStructure;

public class StructureGrayMold implements IStructure
{
	@Override
	public void generate(World world, BlockPos pos, Random random)
	{
		Block under = world.getBlockState(pos).getBlock();
		if (under instanceof BlockNetherMycelium)
		{
			for (int i = 0; i < 32; i++)
			{
				int x = pos.getX() + (int) (random.nextGaussian() * 2);
				int z = pos.getZ() + (int) (random.nextGaussian() * 2);
				int y = pos.getY() + random.nextInt(6);
				for (int j = 0; j < 6; j++)
				{
					BlockPos npos = new BlockPos(x, y - j, z);
					if (npos.getY() > 31)
					{
						under = world.getBlockState(npos.down()).getBlock();
						if (under == BlocksRegister.BLOCK_NETHER_MYCELIUM && world.getBlockState(npos).getBlock() == Blocks.AIR)
						{
							world.setBlockState(npos, BlocksRegister.BLOCK_GRAY_MOLD.getDefaultState());
						}
					}
					else
						break;
				}
			}
		}
	}
}
