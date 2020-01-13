package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockBlackVine;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.structures.IStructure;

public class StructureBlackVine implements IStructure
{
	private Mutable blockPos = new Mutable();
	
	@Override
	public void generate(IWorld world, BlockPos pos, Random random)
	{
		int h = BlocksHelper.downRay(world, pos, 25);
		if (h < 2)
			return;
		h = random.nextInt(h) + 1;
		
		BlockState bottom = BlocksRegister.BLOCK_BLACK_VINE.getDefaultState().with(BlockBlackVine.BOTTOM, true);
		BlockState middle = BlocksRegister.BLOCK_BLACK_VINE.getDefaultState().with(BlockBlackVine.BOTTOM, false);
		
		blockPos.set(pos);
		for (int y = 0; y < h; y++)
		{
			blockPos.setY(pos.getY() - y);
			if (world.isAir(blockPos.down()))
				BlocksHelper.setWithoutUpdate(world, blockPos, middle);
			else
			{
				BlocksHelper.setWithoutUpdate(world, blockPos, bottom);
				return;
			}
		}
		BlocksHelper.setWithoutUpdate(world, blockPos.down(), bottom);
	}
}