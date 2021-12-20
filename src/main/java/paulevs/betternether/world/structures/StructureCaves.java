package paulevs.betternether.world.structures;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.noise.OpenSimplexNoise;

public class StructureCaves implements IStructure {

	private int offset = 12;
	private final OpenSimplexNoise heightNoise;
	private final OpenSimplexNoise rigidNoise;
	private final OpenSimplexNoise distortX;
	private final OpenSimplexNoise distortY;

	public StructureCaves(long seed) {
		Random random = new Random(seed);
		heightNoise = new OpenSimplexNoise(random.nextLong());
		rigidNoise = new OpenSimplexNoise(random.nextLong());
		distortX = new OpenSimplexNoise(random.nextLong());
		distortY = new OpenSimplexNoise(random.nextLong());
	}

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		boolean isVoid = true;
		offset = (int) (getHeight(pos.getX() + 8, pos.getZ() + 8) - 12);

		for (int x = 0; x < 16; x++) {
			int wx = pos.getX() + x;
			for (int z = 0; z < 16; z++) {
				int wz = pos.getZ() + z;

				double height = getHeight(wx, wz);
				double rigid = getRigid(wx, wz);

				for (int y = 0; y < 24; y++) {
					int wy = offset + y;

					double hRigid = Math.abs(wy - height);
					double sdf = -opSmoothUnion(-hRigid / 30, -rigid, 0.15);

					if (sdf < 0.15) {
						context.MASK[x][y][z] = true;
						isVoid = false;
					}
					else
						context.MASK[x][y][z] = false;
				}
			}
		}

		if (isVoid)
			return;

		for (int x = 0; x < 16; x++) {
			int wx = pos.getX() + x;
			for (int z = 0; z < 16; z++) {
				int wz = pos.getZ() + z;
				for (int y = 23; y >= 0; y--) {
					int wy = offset + y;
					context.POS.set(wx, wy, wz);
					if (context.MASK[x][y][z] && BlocksHelper.isNetherGroundMagma(world.getBlockState(context.POS))) {
						/*
						 * if (world.getBlockState(B_POS.up()).getBlock() ==
						 * Blocks.NETHER_WART_BLOCK)
						 * BlocksHelper.setWithoutUpdate(world, B_POS,
						 * Blocks.NETHER_WART_BLOCK.getDefaultState()); else
						 */
						BlocksHelper.setWithoutUpdate(world, context.POS, Blocks.AIR.defaultBlockState());
					}
				}
			}
		}
	}

	private double getHeight(int x, int z) {
		return heightNoise.eval(x * 0.01, z * 0.01) * 32 + 64;
	}

	private double getRigid(int x, int z) {
		return Math.abs(rigidNoise.eval(
				x * 0.02 + distortX.eval(x * 0.05, z * 0.05) * 0.2,
				z * 0.02 + distortY.eval(x * 0.05, z * 0.05) * 0.2)) * 0.6;

		// return Math.abs(rigidNoise.eval(x * 0.02, z * 0.02));
	}

	private double mix(double dist1, double dist2, double blend) {
		return dist1 * (1 - blend) + dist2 * blend;
	}

	private double opSmoothUnion(double dist1, double dist2, double blend) {
		double h = 0.5 + 0.5 * (dist2 - dist1) / blend;
		h = h > 1 ? 1 : h < 0 ? 0 : h;
		return mix(dist2, dist1, h) - blend * h * (1 - h);
	}

	public boolean isInCave(int x, int y, int z, StructureGeneratorThreadContext context) {
		int y2 = y - offset;
		if (y2 >= 0 && y < 24)
			return context.MASK[x][y2][z];
		else
			return false;
	}
}