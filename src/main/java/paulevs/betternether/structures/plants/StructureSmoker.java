package paulevs.betternether.structures.plants;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ServerWorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import paulevs.betternether.blocks.BlockSmoker;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

import java.util.Random;

public class StructureSmoker implements IStructure {
	private Mutable npos = new Mutable();

	private boolean canPlaceAt(ServerWorldAccess world, BlockPos pos) {
		return BlocksHelper.isNetherGround(world.getBlockState(pos.down()));
	}

	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random) {
		if (canPlaceAt(world, pos)) {
			BlockState top = BlocksRegistry.SMOKER.getDefaultState();
			BlockState middle = BlocksRegistry.SMOKER.getDefaultState().with(BlockSmoker.SHAPE, TripleShape.MIDDLE);
			BlockState bottom = BlocksRegistry.SMOKER.getDefaultState().with(BlockSmoker.SHAPE, TripleShape.BOTTOM);
			for (int i = 0; i < 8; i++) {
				int x = pos.getX() + (int) (random.nextGaussian() * 2);
				int z = pos.getZ() + (int) (random.nextGaussian() * 2);
				int y = pos.getY() + random.nextInt(6);
				for (int j = 0; j < 6; j++) {
					npos.set(x, y - j, z);
					if (world.isAir(npos) && canPlaceAt(world, npos)) {
						int h = random.nextInt(5);
						BlocksHelper.setWithoutUpdate(world, npos, bottom);
						for (int n = 1; n < h; n++) {
							BlockPos up = npos.up(n);
							if (world.isAir(up.up()))
								BlocksHelper.setWithoutUpdate(world, up, middle);
							else {
								BlocksHelper.setWithoutUpdate(world, up, top);
								return;
							}
						}
						BlocksHelper.setWithoutUpdate(world, npos.up(h), top);
						break;
					}
				}
			}
		}
	}
}
