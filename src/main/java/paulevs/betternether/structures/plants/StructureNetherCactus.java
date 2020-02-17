package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockNetherCactus;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.structures.IStructure;

public class StructureNetherCactus implements IStructure
{
	private Mutable npos = new Mutable();
	
	private boolean canPlaceAt(IWorld world, BlockPos pos)
	{
		return world.getBlockState(pos.down()).getBlock() == Blocks.GRAVEL;
	}
	
	@Override
	public void generate(IWorld world, BlockPos pos, Random random)
	{
		if (canPlaceAt(world, pos))
		{
			BlockState top = BlocksRegister.NETHER_CACTUS.getDefaultState();
			BlockState bottom = BlocksRegister.NETHER_CACTUS.getDefaultState().with(BlockNetherCactus.TOP, false);
			for (int i = 0; i < 16; i++)
			{
				int x = pos.getX() + (int) (random.nextGaussian() * 4);
				int z = pos.getZ() + (int) (random.nextGaussian() * 4);
				int y = pos.getY() + random.nextInt(8);
				for (int j = 0; j < 8; j++)
				{
					npos.set(x, y - j, z);
					if (world.isAir(npos) && canPlaceAt(world, npos))
					{
						int h = random.nextInt(3);
						for (int n = 0; n < h; n++)
							BlocksHelper.setWithoutUpdate(world, npos.up(n), bottom);
						BlocksHelper.setWithoutUpdate(world, npos.up(h), top);
						break;
					}
				}
			}
		}
	}
}