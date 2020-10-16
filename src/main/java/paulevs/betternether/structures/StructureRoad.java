package paulevs.betternether.structures;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ServerWorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.noise.OpenSimplexNoise;

public class StructureRoad implements IStructure {
	private static final boolean[][][] MASK = new boolean[16][64][16];
	private static final Mutable B_POS = new Mutable();
	private static final int OFFSET = 32;
	private OpenSimplexNoise heightNoise;
	private OpenSimplexNoise rigidNoise;
	private OpenSimplexNoise distortX;
	private OpenSimplexNoise distortY;

	public StructureRoad(long seed) {
		Random random = new Random(seed);
		heightNoise = new OpenSimplexNoise(random.nextLong());
		rigidNoise = new OpenSimplexNoise(random.nextLong());
		distortX = new OpenSimplexNoise(random.nextLong());
		distortY = new OpenSimplexNoise(random.nextLong());
	}

	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random) {
		// int h = (int) getHeight(pos.getX() + 8, pos.getY() + 8);

		for (int x = 0; x < 16; x++) {
			int wx = pos.getX() + x;
			for (int z = 0; z < 16; z++) {
				int wz = pos.getZ() + z;

				double height = getHeight(wx, wz);
				double rigid = getRigid(wx, wz);

				for (int y = 0; y < 64; y++) {
					int wy = OFFSET + y;

					double hRigid = Math.abs(wy - height);
					double sdf = -opSmoothUnion(-hRigid / 80, -rigid, 0.1);

					if (sdf < 0.1 && wy > height)
						MASK[x][y][z] = true;
					else
						MASK[x][y][z] = false;
				}
			}
		}

		for (int x = 0; x < 16; x++) {
			int wx = pos.getX() + x;
			for (int z = 0; z < 16; z++) {
				int wz = pos.getZ() + z;
				for (int y = 62; y >= 1; y--) {
					int wy = OFFSET + y;
					B_POS.set(wx, wy, wz);
					// if (!world.isAir(B_POS))
					{
						if (MASK[x][y][z]) {
							if (!world.isAir(B_POS))
								BlocksHelper.setWithoutUpdate(world, B_POS, Blocks.AIR.getDefaultState());
						}
						else if (MASK[x][y + 1][z]) {
							boolean slab = (getHeight(wx, wz) - wy) > 0.5;

							if (slab)
								BlocksHelper.setWithoutUpdate(world, B_POS.up(), Blocks.NETHER_BRICK_SLAB.getDefaultState());
							BlocksHelper.setWithoutUpdate(world, B_POS, Blocks.NETHER_BRICKS.getDefaultState());

							if (world.isAir(B_POS.down()) || world.isAir(B_POS.down(2))) {
								for (int py = 5; py < wy; py++) {
									B_POS.setY(py);
									BlocksHelper.setWithoutUpdate(world, B_POS, Blocks.NETHER_BRICKS.getDefaultState());
								}
							}
						}
						/*
						 * else if (MASK[x][y - 1][z]) {
						 * BlocksHelper.setWithoutUpdate(world, bPos,
						 * Blocks.GLOWSTONE.getDefaultState()); }
						 */
					}
				}
			}
		}
	}

	private double getHeight(int x, int z) {
		return heightNoise.eval(x * 0.01, z * 0.01) * 16 + 64;
	}

	private double getRigid(int x, int z) {
		return Math.abs(rigidNoise.eval(
				x * 0.002 + distortX.eval(x * 0.01, z * 0.01) * 0.001,
				z * 0.002 + distortY.eval(x * 0.01, z * 0.01) * 0.001)) * 10;
	}

	private double mix(double dist1, double dist2, double blend) {
		return dist1 * (1 - blend) + dist2 * blend;
	}

	private double opSmoothUnion(double dist1, double dist2, double blend) {
		double h = 0.5 + 0.5 * (dist2 - dist1) / blend;
		h = h > 1 ? 1 : h < 0 ? 0 : h;
		return mix(dist2, dist1, h) - blend * h * (1 - h);
	}
}
