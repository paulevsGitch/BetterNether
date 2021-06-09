package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ServerWorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.BlockWhisperingGourdVine;
import paulevs.betternether.blocks.shapes.TripleShape;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureWhisperingGourd implements IStructure {
	private Mutable blockPos = new Mutable();

	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random) {
		if (pos.getY() < 90 || !BlocksHelper.isNetherrack(world.getBlockState(pos.up()))) return;

		int h = BlocksHelper.downRay(world, pos, 4);
		if (h < 1)
			return;
		h = MHelper.randRange(1, h, random);

		BlockState bottom = BlocksRegistry.WHISPERING_GOURD_VINE.getDefaultState().with(BlockWhisperingGourdVine.SHAPE, TripleShape.BOTTOM);
		BlockState middle = BlocksRegistry.WHISPERING_GOURD_VINE.getDefaultState().with(BlockWhisperingGourdVine.SHAPE, TripleShape.MIDDLE);
		BlockState top = BlocksRegistry.WHISPERING_GOURD_VINE.getDefaultState().with(BlockWhisperingGourdVine.SHAPE, TripleShape.TOP);

		blockPos.set(pos);
		for (int y = 0; y < h - 1; y++) {
			blockPos.setY(pos.getY() - y);
			BlocksHelper.setWithUpdate(world, blockPos, random.nextBoolean() ? top : middle);
		}
		BlocksHelper.setWithUpdate(world, blockPos.down(), bottom);
	}
}