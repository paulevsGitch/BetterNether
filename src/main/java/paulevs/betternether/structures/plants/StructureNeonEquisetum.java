package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ServerWorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.BlockNeonEquisetum;
import paulevs.betternether.blocks.shapes.TripleShape;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureNeonEquisetum implements IStructure
{
	private Mutable blockPos = new Mutable();
	
	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random)
	{
		if (!BlocksHelper.isNetherrack(world.getBlockState(pos.up()))) return;
		
		int h = BlocksHelper.downRay(world, pos, 10);
		if (h < 3)
			return;
		h = MHelper.randRange(3, h, random);
		
		BlockState bottom = BlocksRegistry.NEON_EQUISETUM.getDefaultState().with(BlockNeonEquisetum.SHAPE, TripleShape.BOTTOM);
		BlockState middle = BlocksRegistry.NEON_EQUISETUM.getDefaultState().with(BlockNeonEquisetum.SHAPE, TripleShape.MIDDLE);
		BlockState top = BlocksRegistry.NEON_EQUISETUM.getDefaultState().with(BlockNeonEquisetum.SHAPE, TripleShape.TOP);
		
		blockPos.set(pos);
		for (int y = 0; y < h - 2; y++)
		{
			blockPos.setY(pos.getY() - y);
			BlocksHelper.setWithoutUpdate(world, blockPos, top);
		}
		blockPos.setY(blockPos.getY() - 1);
		BlocksHelper.setWithoutUpdate(world, blockPos, middle);
		blockPos.setY(blockPos.getY() - 1);
		BlocksHelper.setWithoutUpdate(world, blockPos.down(), bottom);
	}
}