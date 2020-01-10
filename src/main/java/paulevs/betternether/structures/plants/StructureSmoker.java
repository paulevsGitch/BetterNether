package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockSmoker;
import paulevs.betternether.blocks.shapes.TripleShape;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.structures.IStructure;

public class StructureSmoker implements IStructure
{
	private Mutable npos = new Mutable();
	
	private boolean canPlaceAt(IWorld world, BlockPos pos)
	{
		return BlocksHelper.isNetherGround(world.getBlockState(pos.down()));
	}
	
	@Override
	public void generate(IWorld world, BlockPos pos, Random random)
	{
		if (canPlaceAt(world, pos))
		{
			BlockState top = BlocksRegister.BLOCK_SMOKER.getDefaultState();
			BlockState middle = BlocksRegister.BLOCK_SMOKER.getDefaultState().with(BlockSmoker.SHAPE, TripleShape.MIDDLE);
			BlockState bottom = BlocksRegister.BLOCK_SMOKER.getDefaultState().with(BlockSmoker.SHAPE, TripleShape.BOTTOM);
			for (int i = 0; i < 8; i++)
			{
				int x = pos.getX() + (int) (random.nextGaussian() * 2);
				int z = pos.getZ() + (int) (random.nextGaussian() * 2);
				int y = pos.getY() + random.nextInt(6);
				for (int j = 0; j < 6; j++)
				{
					npos.set(x, y - j, z);
					if (world.isAir(npos) && canPlaceAt(world, npos))
					{
						int h = random.nextInt(6);
						for (int n = 1; n < h; n++)
							BlocksHelper.setWithoutUpdate(world, npos.up(n), middle);
						if (h > 0)
							BlocksHelper.setWithoutUpdate(world, npos, bottom);
						BlocksHelper.setWithoutUpdate(world, npos.up(h), top);
						break;
					}
				}
			}
		}
	}
}
