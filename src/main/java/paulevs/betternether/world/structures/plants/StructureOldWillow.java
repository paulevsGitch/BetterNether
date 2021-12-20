package paulevs.betternether.world.structures.plants;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.BlockPlantWall;
import paulevs.betternether.blocks.BlockProperties.WillowBranchShape;
import paulevs.betternether.blocks.BlockWillowBranch;
import paulevs.betternether.blocks.BlockWillowLeaves;
import paulevs.betternether.blocks.complex.WillowMaterial;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.features.NetherChunkPopulatorFeature;
import paulevs.betternether.world.structures.StructureFuncScatter;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureOldWillow extends StructureFuncScatter {
	private static final float[] CURVE_X = new float[] { 9F, 7F, 1.5F, 0.5F, 3F, 7F };
	private static final float[] CURVE_Y = new float[] { 20F, 17F, 12F, 4F, 0F, -2F };
	private static final Block[] wallPlants = { NetherBlocks.WALL_MOSS, NetherBlocks.WALL_MOSS, NetherBlocks.WALL_MUSHROOM_BROWN, NetherBlocks.WALL_MUSHROOM_RED };

	public StructureOldWillow() {
		super(13);
	}


	public void grow(ServerLevelAccessor world, BlockPos pos, Random random) {
		grow(world, pos, random, false, NetherChunkPopulatorFeature.generatorForThread().context);
	}

	@Override
	public void grow(ServerLevelAccessor world, BlockPos pos, Random random, boolean natural, StructureGeneratorThreadContext context) {
		world.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
		float scale = MHelper.randRange(0.7F, 1.3F, random);
		int minCount = scale < 1 ? 3 : 4;
		int maxCount = scale < 1 ? 5 : 7;
		int count = MHelper.randRange(minCount, maxCount, random);
		final BoundingBox blockBox = BlocksHelper.decorationBounds(world, pos);
		for (int n = 0; n < count; n++) {
			float branchSize = MHelper.randRange(0.5F, 1F, random) * scale;
			float angle = n * MHelper.PI2 / count;
			float radius = CURVE_X[0] * branchSize;
			int x1 = Math.round(pos.getX() + radius * (float) Math.cos(angle) + MHelper.randRange(-2F, 2F, random) * branchSize);
			int y1 = Math.round(pos.getY() + CURVE_Y[0] * branchSize + MHelper.randRange(-2F, 2F, random) * branchSize);
			int z1 = Math.round(pos.getZ() + radius * (float) Math.sin(angle) + MHelper.randRange(-2F, 2F, random) * branchSize);
			float crownR = 10 * branchSize;
			if (crownR < 1.5F) crownR = 1.5F;
			crown(world, new BlockPos(x1, y1 + 1, z1), crownR, random, blockBox);

			boolean generate = true;
			for (int i = 1; i < CURVE_X.length && generate; i++) {
				radius = CURVE_X[i] * branchSize;
				int x2 = Math.round(pos.getX() + radius * (float) Math.cos(angle) + MHelper.randRange(-2F, 2F, random) * branchSize);
				int y2 = Math.round(pos.getY() + CURVE_Y[i] * branchSize + (CURVE_Y[i] > 0 ? MHelper.randRange(-2F, 2F, random) * branchSize : 0));
				int z2 = Math.round(pos.getZ() + radius * (float) Math.sin(angle) + MHelper.randRange(-2F, 2F, random) * branchSize);

				if (CURVE_Y[i] <= 0) {
					if (!isGround(world.getBlockState(context.POS.set(x2, y2, z2)))) {
						boolean noGround = true;
						for (int d = 1; d < 3; d++) {
							if (isGround(world.getBlockState(context.POS.set(x2, y2 - d, z2)))) {
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

				line(world, x1, y1, z1, x2, y2, z2, pos.getY(), context);
				x1 = x2;
				y1 = y2;
				z1 = z2;
			}
		}


		BlockState state;

		for (BlockPos bpos : context.BLOCKS) {
			//if (!blockBox.contains(bpos)) continue;
			if (BlocksHelper.isNetherGround(state = world.getBlockState(bpos)) || state.getMaterial().isReplaceable()) {
				if (!context.BLOCKS.contains(bpos.above()) || !context.BLOCKS.contains(bpos.below()))
					BlocksHelper.setWithUpdate(world, bpos, NetherBlocks.MAT_WILLOW.getBlock(WillowMaterial.BLOCK_BARK).defaultBlockState());
				else
					BlocksHelper.setWithUpdate(world, bpos, NetherBlocks.MAT_WILLOW.getBlock(WillowMaterial.BLOCK_LOG).defaultBlockState());

				if (random.nextInt(8) == 0) {
					state = wallPlants[random.nextInt(wallPlants.length)].defaultBlockState();
					if (random.nextInt(8) == 0 && !context.BLOCKS.contains(bpos.north()) && world.isEmptyBlock(bpos.north()))
						BlocksHelper.setWithUpdate(world, bpos.north(), state.setValue(BlockPlantWall.FACING, Direction.NORTH));
					if (random.nextInt(8) == 0 && !context.BLOCKS.contains(bpos.south()) && world.isEmptyBlock(bpos.south()))
						BlocksHelper.setWithUpdate(world, bpos.south(), state.setValue(BlockPlantWall.FACING, Direction.SOUTH));
					if (random.nextInt(8) == 0 && !context.BLOCKS.contains(bpos.east()) && world.isEmptyBlock(bpos.east()))
						BlocksHelper.setWithUpdate(world, bpos.east(), state.setValue(BlockPlantWall.FACING, Direction.EAST));
					if (random.nextInt(8) == 0 && !context.BLOCKS.contains(bpos.west()) && world.isEmptyBlock(bpos.west()))
						BlocksHelper.setWithUpdate(world, bpos.west(), state.setValue(BlockPlantWall.FACING, Direction.WEST));
				}
			}
		}

		context.BLOCKS.clear();
	}

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		int length = BlocksHelper.upRay(world, pos, StructureStalagnate.MAX_LENGTH + 2);
		if (length >= StructureStalagnate.MAX_LENGTH)
			super.generate(world, pos, random, MAX_HEIGHT, context);
	}

	@Override
	protected boolean isStructure(BlockState state) {
		return state.getBlock() == NetherBlocks.MAT_RUBEUS.getLog();
	}

	@Override
	protected boolean isGround(BlockState state) {
		return BlocksHelper.isNetherGround(state);
	}

	private void line(LevelAccessor world, int x1, int y1, int z1, int x2, int y2, int z2, int startY, StructureGeneratorThreadContext context) {
		final BlockPos.MutableBlockPos POS = new BlockPos.MutableBlockPos();

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

		BlockPos pos = POS.set(x1, y1, z1).immutable();
		context.BLOCKS.add(pos);

		pos = POS.set(x2, y2, z2).immutable();
		context.BLOCKS.add(pos);

		for (int i = 0; i < mx; i++) {
			px += fdx;
			py += fdy;
			pz += fdz;

			POS.set(Math.round(px), Math.round(py), Math.round(pz));
			double delta = POS.getY() - startY;
			sphere(POS, Mth.clamp(2.3 - Math.abs(delta) * (delta > 0 ? 0.1 : 0.3), 0.5, 2.3), context);
		}
	}

	private void sphere(BlockPos pos, double radius, StructureGeneratorThreadContext context) {
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
					if (px2 + pz2 + py2 <= radius) context.BLOCKS.add(new BlockPos(x, y, z));
				}
			}
		}
	}

	private void crown(LevelAccessor world, BlockPos pos, float radius, Random random, BoundingBox bounds) {
		final BlockPos.MutableBlockPos POS = new BlockPos.MutableBlockPos();
		
		BlockState leaves = NetherBlocks.WILLOW_LEAVES.defaultBlockState().setValue(BlockWillowLeaves.NATURAL, false);
		BlockState vine = NetherBlocks.MAT_WILLOW.getBranch().defaultBlockState();
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
									BlocksHelper.setWithUpdate(world, POS, leaves, bounds);
									continue;
								}
								length = MHelper.randRange(3, length, random);
								for (int i = 1; i < length - 1; i++) {
									BlocksHelper.setWithUpdate(world, POS.below(i), vine, bounds);
								}
								BlocksHelper.setWithUpdate(world, POS.below(length - 1), vine.setValue(BlockWillowBranch.SHAPE, WillowBranchShape.END), bounds);
							}
							else if (random.nextBoolean() && world.getBlockState(POS.below()).getMaterial().isReplaceable()) {
								BlocksHelper.setWithUpdate(world, POS.below(), leaves, bounds);
							}
							BlocksHelper.setWithUpdate(world, POS, leaves, bounds);
						}
					}
				}
			}
		}
	}
}
