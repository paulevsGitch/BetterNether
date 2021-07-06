package paulevs.betternether.structures.plants;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ServerWorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.BlockAnchorTreeVine;
import paulevs.betternether.blocks.BlockPlantWall;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

import java.util.*;

public class StructureAnchorTreeRoot implements IStructure {
	private static final Set<BlockPos> BLOCKS = new HashSet<BlockPos>(2048);
	private static final Mutable POS = new Mutable();
	private Block[] wallPlants;
	private static final StructureLucis LUCIS = new StructureLucis();

	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random) {
		if (pos.getY() < 96) return;

		double angle = random.nextDouble() * Math.PI * 2;
		double dx = Math.sin(angle);
		double dz = Math.cos(angle);
		double size = MHelper.randRange(10, 25, random) * 0.5;
		int count = MHelper.floor(size * 2);
		if (count < 3) count = 3;
		if ((count & 1) == 0) count++;
		POS.set(pos.getX() - dx * size, pos.getY() + 10, pos.getZ() - dz * size);
		BlockPos start = POS.up(BlocksHelper.upRay(world, POS, 64));
		if (start.getY() < pos.getY()) start = POS.set(start).add(0, 10, 0).toImmutable();
		POS.set(pos.getX() + dx * size, pos.getY() + 10, pos.getZ() + dz * size);
		BlockPos end = POS.up(BlocksHelper.upRay(world, POS, 64));
		if (end.getY() < pos.getY()) end = POS.set(end).add(0, 10, 0).toImmutable();
		List<BlockPos> blocks = lineParable(start, end, count, random, 0.2);

		BLOCKS.clear();
		buildLine(blocks, 1.3 + random.nextDouble());

		BlockState state;
		if (wallPlants == null) {
			wallPlants = new Block[] { BlocksRegistry.JUNGLE_MOSS, BlocksRegistry.JUNGLE_MOSS, BlocksRegistry.WALL_MUSHROOM_BROWN, BlocksRegistry.WALL_MUSHROOM_RED };
		}
		BlockState vine = BlocksRegistry.ANCHOR_TREE_VINE.getDefaultState();
		for (BlockPos bpos : BLOCKS) {
			if (bpos.getY() < 1 || bpos.getY() > 126) continue;
			if (!BlocksHelper.isNetherGround(state = world.getBlockState(bpos)) && !canReplace(state)) continue;
			boolean blockUp = true;
			boolean blockDown = true;
			if ((blockUp = BLOCKS.contains(bpos.up())) && (blockDown = BLOCKS.contains(bpos.down())))
				BlocksHelper.setWithoutUpdate(world, bpos, BlocksRegistry.ANCHOR_TREE.log.getDefaultState());
			else
				BlocksHelper.setWithoutUpdate(world, bpos, BlocksRegistry.ANCHOR_TREE.bark.getDefaultState());

			if (!blockUp && world.getBlockState(bpos.up()).getMaterial().isReplaceable()) {
				BlocksHelper.setWithoutUpdate(world, bpos.up(), BlocksRegistry.MOSS_COVER.getDefaultState());
			}

			if ((bpos.getY() & 3) == 0 && StructureAnchorTree.NOISE.eval(bpos.getX() * 0.1, bpos.getY() * 0.1, bpos.getZ() * 0.1) > 0) {
				if (random.nextInt(32) == 0 && !BLOCKS.contains(bpos.north()))
					if (random.nextBoolean())
						StructureAnchorTree.makeMushroom(world, bpos.north(), random.nextDouble() + 1.5);
					else
					LUCIS.generate(world, bpos, random);
				if (random.nextInt(32) == 0 && !BLOCKS.contains(bpos.south()))
					if (random.nextBoolean())
						StructureAnchorTree.makeMushroom(world, bpos.south(), random.nextDouble() + 1.5);
					else
					LUCIS.generate(world, bpos, random);
				if (random.nextInt(32) == 0 && !BLOCKS.contains(bpos.east()))
					if (random.nextBoolean())
						StructureAnchorTree.makeMushroom(world, bpos.east(), random.nextDouble() + 1.5);
					else
					LUCIS.generate(world, bpos, random);
				if (random.nextInt(32) == 0 && !BLOCKS.contains(bpos.west()))
					if (random.nextBoolean())
						StructureAnchorTree.makeMushroom(world, bpos.west(), random.nextDouble() + 1.5);
					else
					LUCIS.generate(world, bpos, random);
			}

			state = wallPlants[random.nextInt(wallPlants.length)].getDefaultState();
			if (random.nextInt(8) == 0 && !BLOCKS.contains(bpos.north()) && world.isAir(bpos.north()))
				BlocksHelper.setWithoutUpdate(world, bpos.north(), state.with(BlockPlantWall.FACING, Direction.NORTH));
			if (random.nextInt(8) == 0 && !BLOCKS.contains(bpos.south()) && world.isAir(bpos.south()))
				BlocksHelper.setWithoutUpdate(world, bpos.south(), state.with(BlockPlantWall.FACING, Direction.SOUTH));
			if (random.nextInt(8) == 0 && !BLOCKS.contains(bpos.east()) && world.isAir(bpos.east()))
				BlocksHelper.setWithoutUpdate(world, bpos.east(), state.with(BlockPlantWall.FACING, Direction.EAST));
			if (random.nextInt(8) == 0 && !BLOCKS.contains(bpos.west()) && world.isAir(bpos.west()))
				BlocksHelper.setWithoutUpdate(world, bpos.west(), state.with(BlockPlantWall.FACING, Direction.WEST));

			if (blockUp && !blockDown && random.nextInt(16) == 0) {
				bpos = bpos.down();
				int length = BlocksHelper.downRay(world, bpos, 17);
				if (length > 4) {
					length = MHelper.randRange(3, length, random);
					for (int i = 0; i < length - 2; i++) {
						BlocksHelper.setWithoutUpdate(world, bpos.down(i), vine);
					}
					BlocksHelper.setWithoutUpdate(world, bpos.down(length - 2), vine.with(BlockAnchorTreeVine.SHAPE, TripleShape.MIDDLE));
					BlocksHelper.setWithoutUpdate(world, bpos.down(length - 1), vine.with(BlockAnchorTreeVine.SHAPE, TripleShape.BOTTOM));
				}
			}
		}
	}

	private boolean canReplace(BlockState state) {
		return state.getMaterial().isReplaceable()
				|| state.getBlock() == BlocksRegistry.GIANT_LUCIS
				|| state.getBlock() == BlocksRegistry.LUCIS_MUSHROOM
				|| state.getBlock() instanceof BlockPlantWall;
	}

	private void buildLine(List<BlockPos> blocks, double radius) {
		for (int i = 0; i < blocks.size() - 1; i++) {
			BlockPos a = blocks.get(i);
			BlockPos b = blocks.get(i + 1);
			if (b.getY() < a.getY()) {
				BlockPos c = b;
				b = a;
				a = c;
			}
			int count = (int) Math.ceil(Math.sqrt(b.getSquaredDistance(a)));
			for (int j = 0; j < count; j++)
				sphere(lerpCos(a, b, (double) j / count), radius);
		}
	}

	private BlockPos lerpCos(BlockPos start, BlockPos end, double mix) {
		double v = lcos(mix);
		double x = MathHelper.lerp(v, start.getX(), end.getX());
		double y = MathHelper.lerp(v, start.getY(), end.getY());
		double z = MathHelper.lerp(v, start.getZ(), end.getZ());
		return new BlockPos(x, y, z);
	}

	private double lcos(double mix) {
		return MathHelper.clamp(0.5 - Math.cos(mix * Math.PI) * 0.5, 0, 1);
	}

	private List<BlockPos> lineParable(BlockPos start, BlockPos end, int count, Random random, double range) {
		List<BlockPos> result = new ArrayList<BlockPos>(count);
		int max = count - 1;
		int middle = count / 2;
		result.add(start);
		double size = Math.sqrt(start.getSquaredDistance(end)) * 0.8;
		for (int i = 1; i < max; i++) {
			double offset = (double) (i - middle) / middle;
			offset = 1 - offset * offset;
			double delta = (double) i / max;
			double x = MathHelper.lerp(delta, start.getX(), end.getX()) + random.nextGaussian() * range;
			double y = MathHelper.lerp(delta, start.getY(), end.getY()) - offset * size;
			double z = MathHelper.lerp(delta, start.getZ(), end.getZ()) + random.nextGaussian() * range;
			result.add(new BlockPos(x, y, z));
		}
		result.add(end);
		return result;
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
}
