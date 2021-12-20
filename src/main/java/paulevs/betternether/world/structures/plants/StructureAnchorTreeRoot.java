package paulevs.betternether.world.structures.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.BlockAnchorTreeVine;
import paulevs.betternether.blocks.BlockPlantWall;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StructureAnchorTreeRoot implements IStructure {
	private static final StructureLucis LUCIS = new StructureLucis();

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		if (pos.getY() < MAX_HEIGHT*0.75) return;

		double angle = random.nextDouble() * Math.PI * 2;
		double dx = Math.sin(angle);
		double dz = Math.cos(angle);
		double size = MHelper.randRange(10, 25, random) * 0.5;
		int count = MHelper.floor(size * 2);
		if (count < 3) count = 3;
		if ((count & 1) == 0) count++;
		context.POS.set(pos.getX() - dx * size, pos.getY() + 10, pos.getZ() - dz * size);
		BlockPos start = context.POS.above(BlocksHelper.upRay(world, context.POS, (int)(MAX_HEIGHT*0.75)));
		if (start.getY() < pos.getY()) start = context.POS.set(start).offset(0, 10, 0).immutable();
		context.POS.set(pos.getX() + dx * size, pos.getY() + 10, pos.getZ() + dz * size);
		BlockPos end = context.POS.above(BlocksHelper.upRay(world, context.POS, (int)(MAX_HEIGHT*0.75)));
		if (end.getY() < pos.getY()) end = context.POS.set(end).offset(0, 10, 0).immutable();
		List<BlockPos> blocks = lineParable(start, end, count, random, 0.2);

		context.BLOCKS.clear();
		buildLine(blocks, 1.3 + random.nextDouble(), context);

		BlockState state;
		BlockState vine = NetherBlocks.ANCHOR_TREE_VINE.defaultBlockState();
		final int minBuildHeight = world.getMinBuildHeight()+1;
		final BoundingBox blockBox = BlocksHelper.decorationBounds(world, pos, minBuildHeight, MAX_HEIGHT-2);
		for (BlockPos bpos : context.BLOCKS) {
			//if (!blockBox.contains(bpos)) continue;
			if (bpos.getY() < minBuildHeight || bpos.getY() > MAX_HEIGHT-2) continue;
			if (!BlocksHelper.isNetherGround(state = world.getBlockState(bpos)) && !canReplace(state)) continue;
			boolean blockUp;
			boolean blockDown = true;
			if ((blockUp = context.BLOCKS.contains(bpos.above())) && (blockDown = context.BLOCKS.contains(bpos.below())))
				BlocksHelper.setWithoutUpdate(world, bpos, NetherBlocks.MAT_ANCHOR_TREE.getLog().defaultBlockState());
			else
				BlocksHelper.setWithoutUpdate(world, bpos, NetherBlocks.MAT_ANCHOR_TREE.getBark().defaultBlockState());

			if (!blockUp && world.getBlockState(bpos.above()).getMaterial().isReplaceable()) {
				BlocksHelper.setWithoutUpdate(world, bpos.above(), NetherBlocks.MOSS_COVER.defaultBlockState());
			}

			if ((bpos.getY() & 3) == 0 && StructureAnchorTree.NOISE.eval(bpos.getX() * 0.1, bpos.getY() * 0.1, bpos.getZ() * 0.1) > 0) {
				if (random.nextInt(32) == 0 && !context.BLOCKS.contains(bpos.north()))
					if (random.nextBoolean())
						StructureAnchorTree.makeMushroom(world, bpos.north(), random.nextDouble() + 1.5, blockBox);
					else
					LUCIS.generate(world, bpos, random, MAX_HEIGHT, context);
				if (random.nextInt(32) == 0 && !context.BLOCKS.contains(bpos.south()))
					if (random.nextBoolean())
						StructureAnchorTree.makeMushroom(world, bpos.south(), random.nextDouble() + 1.5, blockBox);
					else
					LUCIS.generate(world, bpos, random, MAX_HEIGHT, context);
				if (random.nextInt(32) == 0 && !context.BLOCKS.contains(bpos.east()))
					if (random.nextBoolean())
						StructureAnchorTree.makeMushroom(world, bpos.east(), random.nextDouble() + 1.5, blockBox);
					else
					LUCIS.generate(world, bpos, random, MAX_HEIGHT, context);
				if (random.nextInt(32) == 0 && !context.BLOCKS.contains(bpos.west()))
					if (random.nextBoolean())
						StructureAnchorTree.makeMushroom(world, bpos.west(), random.nextDouble() + 1.5, blockBox);
					else
					LUCIS.generate(world, bpos, random, MAX_HEIGHT, context);
			}

			state = StructureAnchorTree.wallPlants[random.nextInt(StructureAnchorTree.wallPlants.length)].defaultBlockState();
			BlockPos _pos = bpos.north();
			if (random.nextInt(8) == 0 && !context.BLOCKS.contains(_pos) && world.isEmptyBlock(_pos))
				BlocksHelper.setWithUpdate(world, _pos, state.setValue(BlockPlantWall.FACING, Direction.NORTH));

			_pos = bpos.south();
			if (random.nextInt(8) == 0 && !context.BLOCKS.contains(_pos) && world.isEmptyBlock(_pos))
				BlocksHelper.setWithUpdate(world, _pos, state.setValue(BlockPlantWall.FACING, Direction.SOUTH));

			_pos = bpos.east();
			if (random.nextInt(8) == 0 && !context.BLOCKS.contains(_pos) && world.isEmptyBlock(_pos))
				BlocksHelper.setWithUpdate(world, _pos, state.setValue(BlockPlantWall.FACING, Direction.EAST));

			_pos = bpos.west();
			if (random.nextInt(8) == 0 && !context.BLOCKS.contains(_pos) && world.isEmptyBlock(_pos))
				BlocksHelper.setWithUpdate(world, _pos, state.setValue(BlockPlantWall.FACING, Direction.WEST));

			if (blockUp && !blockDown && random.nextInt(16) == 0) {
				bpos = bpos.below();
				int length = BlocksHelper.downRay(world, bpos, 17);
				if (length > 4) {
					length = MHelper.randRange(3, length, random);
					for (int i = 0; i < length - 2; i++) {
						BlocksHelper.setWithoutUpdate(world, bpos.below(i), vine);
					}
					BlocksHelper.setWithoutUpdate(world, bpos.below(length - 2), vine.setValue(BlockAnchorTreeVine.SHAPE, TripleShape.MIDDLE));
					BlocksHelper.setWithoutUpdate(world, bpos.below(length - 1), vine.setValue(BlockAnchorTreeVine.SHAPE, TripleShape.BOTTOM));
				}
			}
		}
	}

	private boolean canReplace(BlockState state) {
		return state.getMaterial().isReplaceable()
				|| state.getBlock() == NetherBlocks.GIANT_LUCIS
				|| state.getBlock() == NetherBlocks.LUCIS_MUSHROOM
				|| state.getBlock() instanceof BlockPlantWall;
	}

	private void buildLine(List<BlockPos> blocks, double radius, StructureGeneratorThreadContext context) {
		for (int i = 0; i < blocks.size() - 1; i++) {
			BlockPos a = blocks.get(i);
			BlockPos b = blocks.get(i + 1);
			if (b.getY() < a.getY()) {
				BlockPos c = b;
				b = a;
				a = c;
			}
			int count = (int) Math.ceil(Math.sqrt(b.distSqr(a)));
			for (int j = 0; j < count; j++)
				sphere(lerpCos(a, b, (double) j / count), radius, context);
		}
	}

	private BlockPos lerpCos(BlockPos start, BlockPos end, double mix) {
		double v = lcos(mix);
		double x = Mth.lerp(v, start.getX(), end.getX());
		double y = Mth.lerp(v, start.getY(), end.getY());
		double z = Mth.lerp(v, start.getZ(), end.getZ());
		return new BlockPos(x, y, z);
	}

	private double lcos(double mix) {
		return Mth.clamp(0.5 - Math.cos(mix * Math.PI) * 0.5, 0, 1);
	}

	private List<BlockPos> lineParable(BlockPos start, BlockPos end, int count, Random random, double range) {
		List<BlockPos> result = new ArrayList<>(count);
		int max = count - 1;
		int middle = count / 2;
		result.add(start);
		double size = Math.sqrt(start.distSqr(end)) * 0.8;
		for (int i = 1; i < max; i++) {
			double offset = (double) (i - middle) / middle;
			offset = 1 - offset * offset;
			double delta = (double) i / max;
			double x = Mth.lerp(delta, start.getX(), end.getX()) + random.nextGaussian() * range;
			double y = Mth.lerp(delta, start.getY(), end.getY()) - offset * size;
			double z = Mth.lerp(delta, start.getZ(), end.getZ()) + random.nextGaussian() * range;
			result.add(new BlockPos(x, y, z));
		}
		result.add(end);
		return result;
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
}
