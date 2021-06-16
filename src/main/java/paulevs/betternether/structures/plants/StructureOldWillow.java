package paulevs.betternether.structures.plants;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.BlockPlantWall;
import paulevs.betternether.blocks.BlockWillowBranch;
import paulevs.betternether.blocks.BlockWillowBranch.WillowBranchShape;
import paulevs.betternether.blocks.BlockWillowLeaves;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.StructureFuncScatter;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class StructureOldWillow extends StructureFuncScatter {
	private static final float[] CURVE_X = new float[] { 9F, 7F, 1.5F, 0.5F, 3F, 7F };
	private static final float[] CURVE_Y = new float[] { 20F, 17F, 12F, 4F, 0F, -2F };
	private static final Set<BlockPos> BLOCKS = new HashSet<BlockPos>();
	private Block[] wallPlants;

	public StructureOldWillow() {
		super(13);
	}

	@Override
	public void grow(ServerWorldAccess world, BlockPos pos, Random random) {
		grow(world, pos, random, true);
	}

	public void grow(ServerWorldAccess world, BlockPos pos, Random random, boolean natural) {
		world.setBlockState(pos, Blocks.AIR.getDefaultState(), 0);
		float scale = MHelper.randRange(0.7F, 1.3F, random);
		int minCount = scale < 1 ? 3 : 4;
		int maxCount = scale < 1 ? 5 : 7;
		int count = MHelper.randRange(minCount, maxCount, random);
		for (int n = 0; n < count; n++) {
			float branchSize = MHelper.randRange(0.5F, 1F, random) * scale;
			float angle = n * MHelper.PI2 / count;
			float radius = CURVE_X[0] * branchSize;
			int x1 = Math.round(pos.getX() + radius * (float) Math.cos(angle) + MHelper.randRange(-2F, 2F, random) * branchSize);
			int y1 = Math.round(pos.getY() + CURVE_Y[0] * branchSize + MHelper.randRange(-2F, 2F, random) * branchSize);
			int z1 = Math.round(pos.getZ() + radius * (float) Math.sin(angle) + MHelper.randRange(-2F, 2F, random) * branchSize);
			float crownR = 10 * branchSize;
			if (crownR < 1.5F) crownR = 1.5F;
			crown(world, new BlockPos(x1, y1 + 1, z1), crownR, random);

			boolean generate = true;
			for (int i = 1; i < CURVE_X.length && generate; i++) {
				radius = CURVE_X[i] * branchSize;
				int x2 = Math.round(pos.getX() + radius * (float) Math.cos(angle) + MHelper.randRange(-2F, 2F, random) * branchSize);
				int y2 = Math.round(pos.getY() + CURVE_Y[i] * branchSize + (CURVE_Y[i] > 0 ? MHelper.randRange(-2F, 2F, random) * branchSize : 0));
				int z2 = Math.round(pos.getZ() + radius * (float) Math.sin(angle) + MHelper.randRange(-2F, 2F, random) * branchSize);

				if (CURVE_Y[i] <= 0) {
					if (!isGround(world.getBlockState(POS.set(x2, y2, z2)))) {
						boolean noGround = true;
						for (int d = 1; d < 3; d++) {
							if (isGround(world.getBlockState(POS.set(x2, y2 - d, z2)))) {
								y2 -= d;
								noGround = false;
								break;
							}
						}
						if (noGround) {
							x2 = pos.getX();
							y2 = pos.getY();
							generate = false;
						}
					}
				}

				line(world, x1, y1, z1, x2, y2, z2, pos.getY());
				x1 = x2;
				y1 = y2;
				z1 = z2;
			}
		}

		if (wallPlants == null) {
			wallPlants = new Block[] { BlocksRegistry.WALL_MOSS, BlocksRegistry.WALL_MOSS, BlocksRegistry.WALL_MUSHROOM_BROWN, BlocksRegistry.WALL_MUSHROOM_RED };
		}

		BlockState state;
		for (BlockPos bpos : BLOCKS) {
			if (BlocksHelper.isNetherGround(state = world.getBlockState(bpos)) || state.getMaterial().isReplaceable()) {
				if (!BLOCKS.contains(bpos.up()) || !BLOCKS.contains(bpos.down()))
					BlocksHelper.setWithUpdate(world, bpos, BlocksRegistry.WILLOW_BARK.getDefaultState());
				else
					BlocksHelper.setWithUpdate(world, bpos, BlocksRegistry.WILLOW_LOG.getDefaultState());

				if (random.nextInt(8) == 0) {
					state = wallPlants[random.nextInt(wallPlants.length)].getDefaultState();
					if (random.nextInt(8) == 0 && !BLOCKS.contains(bpos.north()) && world.isAir(bpos.north()))
						BlocksHelper.setWithUpdate(world, bpos.north(), state.with(BlockPlantWall.FACING, Direction.NORTH));
					if (random.nextInt(8) == 0 && !BLOCKS.contains(bpos.south()) && world.isAir(bpos.south()))
						BlocksHelper.setWithUpdate(world, bpos.south(), state.with(BlockPlantWall.FACING, Direction.SOUTH));
					if (random.nextInt(8) == 0 && !BLOCKS.contains(bpos.east()) && world.isAir(bpos.east()))
						BlocksHelper.setWithUpdate(world, bpos.east(), state.with(BlockPlantWall.FACING, Direction.EAST));
					if (random.nextInt(8) == 0 && !BLOCKS.contains(bpos.west()) && world.isAir(bpos.west()))
						BlocksHelper.setWithUpdate(world, bpos.west(), state.with(BlockPlantWall.FACING, Direction.WEST));
				}
			}
		}

		BLOCKS.clear();
	}

	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random) {
		int length = BlocksHelper.upRay(world, pos, StructureStalagnate.MAX_LENGTH + 2);
		if (length >= StructureStalagnate.MAX_LENGTH)
			super.generate(world, pos, random);
	}

	@Override
	protected boolean isStructure(BlockState state) {
		return state.getBlock() == BlocksRegistry.RUBEUS_LOG;
	}

	@Override
	protected boolean isGround(BlockState state) {
		return BlocksHelper.isNetherGround(state);
	}

	private void line(WorldAccess world, int x1, int y1, int z1, int x2, int y2, int z2, int startY) {
		int dx = x2 - x1;
		int dy = y2 - y1;
		int dz = z2 - z1;
		int mx = Math.max(Math.max(Math.abs(dx), Math.abs(dy)), Math.abs(dz));
		float fdx = (float) dx / mx;
		float fdy = (float) dy / mx;
		float fdz = (float) dz / mx;
		float px = x1;
		float py = y1;
		float pz = z1;

		BlockPos pos = POS.set(x1, y1, z1).toImmutable();
		BLOCKS.add(pos);

		pos = POS.set(x2, y2, z2).toImmutable();
		BLOCKS.add(pos);

		for (int i = 0; i < mx; i++) {
			px += fdx;
			py += fdy;
			pz += fdz;

			POS.set(Math.round(px), Math.round(py), Math.round(pz));
			double delta = POS.getY() - startY;
			sphere(POS, MathHelper.clamp(2.3 - Math.abs(delta) * (delta > 0 ? 0.1 : 0.3), 0.5, 2.3));
		}
	}

	private void sphere(BlockPos pos, double radius) {
		int x1 = MHelper.floor(pos.getX() - radius);
		int y1 = MHelper.floor(pos.getY() - radius);
		int z1 = MHelper.floor(pos.getZ() - radius);
		int x2 = MHelper.floor(pos.getX() + radius + 1);
		int y2 = MHelper.floor(pos.getY() + radius + 1);
		int z2 = MHelper.floor(pos.getZ() + radius + 1);
		radius *= radius;

		for (int x = x1; x <= x2; x++) {
			int px2 = x - pos.getX();
			px2 *= px2;
			for (int z = z1; z <= z2; z++) {
				int pz2 = z - pos.getZ();
				pz2 *= pz2;
				for (int y = y1; y <= y2; y++) {
					int py2 = y - pos.getY();
					py2 *= py2;
					if (px2 + pz2 + py2 <= radius) BLOCKS.add(new BlockPos(x, y, z));
				}
			}
		}
	}

	private void crown(WorldAccess world, BlockPos pos, float radius, Random random) {
		BlockState leaves = BlocksRegistry.WILLOW_LEAVES.getDefaultState().with(BlockWillowLeaves.NATURAL, false);
		BlockState vine = BlocksRegistry.WILLOW_BRANCH.getDefaultState();
		float halfR = radius * 0.5F;
		float r2 = radius * radius;
		int start = (int) Math.floor(-radius);
		for (int cy = start; cy <= radius; cy++) {
			int cy2_out = cy * cy;
			float cy2_in = cy + halfR;
			cy2_in *= cy2_in;
			POS.setY((int) (pos.getY() + cy - halfR));
			for (int cx = start; cx <= radius; cx++) {
				int cx2 = cx * cx * 2;
				POS.setX(pos.getX() + cx);
				for (int cz = start; cz <= radius; cz++) {
					int cz2 = cz * cz * 2;
					if (cx2 + cy2_out + cz2 < r2 && cx2 + cy2_in + cz2 > r2) {
						POS.setZ(pos.getZ() + cz);
						if (world.getBlockState(POS).getMaterial().isReplaceable()) {
							if (random.nextBoolean()) {
								int length = BlocksHelper.downRay(world, POS, 12);
								if (length < 3) {
									BlocksHelper.setWithUpdate(world, POS, leaves);
									continue;
								} ;
								length = MHelper.randRange(3, length, random);
								for (int i = 1; i < length - 1; i++) {
									BlocksHelper.setWithUpdate(world, POS.down(i), vine);
								}
								BlocksHelper.setWithUpdate(world, POS.down(length - 1), vine.with(BlockWillowBranch.SHAPE, WillowBranchShape.END));
							}
							else if (random.nextBoolean() && world.getBlockState(POS.down()).getMaterial().isReplaceable()) {
								BlocksHelper.setWithUpdate(world, POS.down(), leaves);
							}
							BlocksHelper.setWithUpdate(world, POS, leaves);
						}
					}
				}
			}
		}
	}
}
