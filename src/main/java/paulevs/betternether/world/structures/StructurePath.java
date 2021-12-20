package paulevs.betternether.world.structures;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.noise.OpenSimplexNoise;
import paulevs.betternether.registry.NetherBlocks;

public class StructurePath implements IStructure {
	private final OpenSimplexNoise heightNoise;
	private final OpenSimplexNoise rigidNoise;
	private final OpenSimplexNoise distortX;
	private final OpenSimplexNoise distortY;

	public StructurePath(long seed) {
		Random random = new Random(seed);
		heightNoise = new OpenSimplexNoise(random.nextLong());
		rigidNoise = new OpenSimplexNoise(random.nextLong());
		distortX = new OpenSimplexNoise(random.nextLong());
		distortY = new OpenSimplexNoise(random.nextLong());
	}

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		for (int x = 0; x < 16; x++) {
			int wx = pos.getX() + x;
			context.POS.setX(wx);
			for (int z = 0; z < 16; z++) {
				int wz = pos.getZ() + z;
				context.POS.setZ(wz);
				double rigid = getRigid(wx, wz) + MHelper.randRange(0, 0.015F, random);

				if (rigid < 0.015) {
					int height = getHeight(wx, wz);
					context.POS.setY(height);
					height -= BlocksHelper.downRay(world, context.POS, height);
					context.POS.setY(height);
					if (world.isEmptyBlock(context.POS) && world.getBlockState(context.POS.move(Direction.DOWN)).isCollisionShapeFullBlock(world, context.POS) && isHeightValid(world, context.POS.above())) {
						Biome biome = world.getBiome(context.POS);
						BlocksHelper.setWithoutUpdate(world, context.POS, getRoadMaterial(world, context.POS, biome));
						if (needsSlab(world, context.POS.above()))
							BlocksHelper.setWithoutUpdate(world, context.POS.above(), getSlabMaterial(world, context.POS, biome));
						else if (rigid > 0.01 && ((x & 3) == 0) && ((z & 3) == 0) && random.nextInt(8) == 0)
							makeLantern(world, context.POS.above());
					}
				}
			}
		}
	}

	private int getHeight(int x, int z) {
		return (int) (heightNoise.eval(x * 0.001, z * 0.001) * 32 + 64);
	}

	private double getRigid(double x, double z) {
		x *= 0.1;
		z *= 0.1;
		return Math.abs(rigidNoise.eval(
				x * 0.02 + distortX.eval(x * 0.05, z * 0.05) * 0.2,
				z * 0.02 + distortY.eval(x * 0.05, z * 0.05) * 0.2));
	}

	private boolean isHeightValid(LevelAccessor world, BlockPos pos) {
		return Math.abs(BlocksHelper.downRay(world, pos.north(2), 5) - BlocksHelper.downRay(world, pos.south(2), 5)) < 3 &&
				Math.abs(BlocksHelper.downRay(world, pos.east(2), 5) - BlocksHelper.downRay(world, pos.west(2), 5)) < 3;
	}

	private void makeLantern(LevelAccessor world, BlockPos pos) {
		BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.NETHER_BRICK_WALL.defaultBlockState());
		BlocksHelper.setWithoutUpdate(world, pos.above(), Blocks.NETHER_BRICK_FENCE.defaultBlockState());
		BlocksHelper.setWithoutUpdate(world, pos.above(2), Blocks.NETHER_BRICK_FENCE.defaultBlockState());
		Direction dir = Direction.NORTH;
		double d = 1000;
		double v = getRigid(pos.getX(), pos.getZ());
		for (Direction face : BlocksHelper.HORIZONTAL) {
			BlockPos p = pos.relative(face);
			double v2 = getRigid(p.getX(), p.getZ());
			double d2 = v - v2;
			if (d2 < d) {
				d = d2;
				dir = face;
			}
		}

		BlockPos p = pos.above(3);
		BlocksHelper.setWithoutUpdate(world, p, Blocks.NETHER_BRICK_FENCE.defaultBlockState());
		world.getChunk(p).markPosForPostprocessing(new BlockPos(p.getX() & 15, p.getY(), p.getZ() & 15));
		p = p.relative(dir.getOpposite());
		BlocksHelper.setWithoutUpdate(world, p, Blocks.NETHER_BRICK_FENCE.defaultBlockState());
		world.getChunk(p).markPosForPostprocessing(new BlockPos(p.getX() & 15, p.getY(), p.getZ() & 15));
		BlocksHelper.setWithoutUpdate(world, p.below(), Blocks.LANTERN.defaultBlockState().setValue(LanternBlock.HANGING, true));
	}

	private BlockState getRoadMaterial(ServerLevelAccessor world, BlockPos pos, Biome biome) {
		/*
		 * if (biome == Biomes.SOUL_SAND_VALLEY || biome instanceof
		 * NetherWartForest || biome instanceof NetherWartForestEdge) { return
		 * BlocksRegistry.SOUL_SANDSTONE.getDefaultState(); }
		 */
		return Blocks.BASALT.defaultBlockState();
	}

	private BlockState getSlabMaterial(ServerLevelAccessor world, BlockPos pos, Biome biome) {
		/*
		 * if (biome == BuiltInBiomes.SOUL_SAND_VALLEY || biome instanceof
		 * NetherWartForest || biome instanceof NetherWartForestEdge) { return
		 * BlocksRegistry.SOUL_SANDSTONE_SLAB.getDefaultState(); }
		 */
		return NetherBlocks.BASALT_SLAB.defaultBlockState();
	}

	private boolean needsSlab(ServerLevelAccessor world, BlockPos pos) {
		BlockState state;
		for (Direction dir : BlocksHelper.HORIZONTAL) {
			if ((BlocksHelper.isNetherGround(state = world.getBlockState(pos.relative(dir))) ||
					state.getBlock() == Blocks.BASALT || state.getBlock() == NetherBlocks.SOUL_SANDSTONE) &&
					!world.getBlockState(pos.below().relative(dir.getOpposite())).isAir())
				return true;
		}
		return false;
	}
}