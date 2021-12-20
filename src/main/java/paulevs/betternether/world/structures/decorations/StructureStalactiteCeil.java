package paulevs.betternether.world.structures.decorations;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.BlockStalactite;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureStalactiteCeil implements IStructure {
	private final Block block;
	private final Block upper;
	private final Block[] ground;

	public StructureStalactiteCeil(Block block, Block upper) {
		this.block = block;
		ground = null;
		this.upper = upper;
	}

	public StructureStalactiteCeil(Block block, Block upper, Block... ground) {
		this.block = block;
		this.upper = upper;
		this.ground = ground;
	}

	private boolean canPlaceAt(ServerLevelAccessor world, BlockPos pos) {
		return world.isEmptyBlock(pos) && (ground == null ? BlocksHelper.isNetherrack(world.getBlockState(pos.above())) : groundContains(world.getBlockState(pos.above()).getBlock()));
	}

	private boolean groundContains(Block block) {
		for (Block b : ground)
			if (b == block)
				return true;
		return false;
	}

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		final MutableBlockPos POS = new MutableBlockPos();

		if (canPlaceAt(world, pos)) {
			for (int i = 0; i < 16; i++) {
				int x = pos.getX() + (int) (random.nextGaussian() * 2F);
				int z = pos.getZ() + (int) (random.nextGaussian() * 2F);
				POS.set(x, pos.getY(), z);
				int y = pos.getY() + BlocksHelper.upRay(world, POS, 8);

				if (canPlaceAt(world, POS)) {
					int dx = x - pos.getX();
					int dz = z - pos.getZ();
					float dist = 4 - (float) Math.sqrt(dx * dx + dz * dz);
					if (dist < 1)
						dist = 1;
					int h = (int) (random.nextInt((int) Math.ceil(dist + 1)) + dist + random.nextInt(2));
					h = h > 8 ? 8 : h;
					POS.setY(y);
					int h2 = BlocksHelper.downRay(world, POS, h + 2) + 1;
					POS.setY(y - h2);
					boolean stalagnate = h2 <= h && BlocksHelper.isNetherrack(world.getBlockState(POS));
					if (h2 <= h)
						h = h2;
					int offset = stalagnate ? (int) (MHelper.randRange(0, 9 - h, random) * 0.7) : 0;

					if (upper != null && h > 2) {
						POS.setY(y + 1);
						BlocksHelper.setWithoutUpdate(world, POS, upper.defaultBlockState());
						if (stalagnate) {
							POS.setY(y - h);
							BlocksHelper.setWithoutUpdate(world, POS, upper.defaultBlockState());
						}
					}
					for (int n = 0; n < h; n++) {
						POS.setY(y - n);
						if (!world.isEmptyBlock(POS))
							break;
						int size = stalagnate ? (Math.abs(h / 2 - n) + offset) : (h - n - 1);
						BlocksHelper.setWithoutUpdate(world, POS, block.defaultBlockState().setValue(BlockStalactite.SIZE, size));
					}
				}
			}
		}
	}
}