package paulevs.betternether.world.structures.plants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.BlockPlantWall;
import paulevs.betternether.noise.OpenSimplexNoise;
import paulevs.betternether.registry.NetherBiomes;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;


public class StructureAnchorTree implements IStructure {
	protected static final OpenSimplexNoise NOISE = new OpenSimplexNoise(2145);
	public static final Block[] wallPlants = { NetherBlocks.JUNGLE_MOSS, NetherBlocks.JUNGLE_MOSS, NetherBlocks.WALL_MUSHROOM_BROWN, NetherBlocks.WALL_MUSHROOM_RED };
	final private LegacyStructureAnchorTree legacyStructure;
	
	public StructureAnchorTree(){
		legacyStructure = new LegacyStructureAnchorTree();
	}

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		if (NetherBiomes.useLegacyGeneration) {
			legacyStructure.generate(world, pos, random, MAX_HEIGHT, context);
			return;
		}
		
		BlockPos down = pos.below(BlocksHelper.downRay(world, pos, MAX_HEIGHT));
		if (canGenerate(pos)) {
			grow(world, pos, down, random, MAX_HEIGHT, context);
		}
	}

	private boolean canGenerate(BlockPos pos) {
		return (pos.getX() & 15) == 7 && (pos.getZ() & 15) == 7;
	}

	private void grow(ServerLevelAccessor level, BlockPos up, BlockPos down, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		final float scale_factor = MAX_HEIGHT/128.0f;
		final int HEIGHT_64;
		final int HEIGHT_45;
		final int HEIGHT_90;
		final int SEGMENT_LENGTH;
		if (NetherBiomes.useLegacyGeneration){
			HEIGHT_64 = MAX_HEIGHT / 2;
			HEIGHT_45 = (int)(MAX_HEIGHT * 0.36);
			HEIGHT_90 = (int)(MAX_HEIGHT * 0.7);
			SEGMENT_LENGTH = (int)(15*scale_factor);
		} else {
			HEIGHT_64 = (int) (MAX_HEIGHT / 4.0 + random.nextFloat(32));
			HEIGHT_45 = (int) (20 + random.nextFloat(20 * scale_factor));
			HEIGHT_90 = (int) (MAX_HEIGHT / 2.0 + random.nextFloat(15 * scale_factor));
			SEGMENT_LENGTH = (int) ((15 + random.nextFloat(5 * scale_factor))*scale_factor);
		}
		if (up.getY() - down.getY() < 30) return;
		int pd = BlocksHelper.downRay(level, down, MAX_HEIGHT) + 1;
		for (int i = 0; i < 5; i++) {
			Block block = level.getBlockState(down.below(pd + i)).getBlock();
			if (block == Blocks.NETHER_BRICKS || block == NetherBlocks.NETHER_BRICK_TILE_LARGE || block == NetherBlocks.NETHER_BRICK_TILE_SMALL)
				return;
		}

		final BlockPos trunkTop = lerp(down, up, 0.6);
		final BlockPos trunkBottom = lerp(down, up, 0.3);

		int count = (trunkTop.getY() - trunkBottom.getY()) / 7;
		if (count < 2) count = 2;
		List<BlockPos> blocks = line(trunkBottom, trunkTop, count, random, 2.5);

		context.BLOCKS.clear();

		
		count = Math.min(7, Math.max(3, (up.getY() - down.getY()) / (int)(10*scale_factor) - 1));
		double radius = Math.min(7, Math.max(3.5, (up.getY() - down.getY()) / 15));
		
		drawLine(level, blocks, radius+(0.5*scale_factor), MAX_HEIGHT, context);
		
		buildBigCircle(level, up, trunkTop, SEGMENT_LENGTH, count, 2, random.nextDouble() * Math.PI * 2, radius, random, MAX_HEIGHT, context);
		buildBigCircle(level, up, trunkBottom, -SEGMENT_LENGTH, count, 2, random.nextDouble() * Math.PI * 2, radius, random, MAX_HEIGHT, context);

		BlockState state;
		int offset = random.nextInt(4);
		final int minBuildHeight = level.getMinBuildHeight()+1;
		final net.minecraft.world.level.levelgen.structure.BoundingBox blockBox = BlocksHelper.decorationBounds(level, up, minBuildHeight, MAX_HEIGHT-2);
		for (BlockPos bpos : context.BLOCKS) {
			if (!blockBox.isInside(bpos)) continue;
			if (!BlocksHelper.isNetherGround(state = level.getBlockState(bpos)) && !state.getMaterial().isReplaceable()) continue;
			boolean blockUp = true;
			if ((blockUp = context.BLOCKS.contains(bpos.above())) &&  context.BLOCKS.contains(bpos.below()))
				BlocksHelper.setWithoutUpdate(level, bpos, NetherBlocks.MAT_ANCHOR_TREE.getLog().defaultBlockState());
			else
				BlocksHelper.setWithoutUpdate(level, bpos, NetherBlocks.MAT_ANCHOR_TREE.getBark().defaultBlockState());

			if (bpos.getY() > HEIGHT_45 && bpos.getY() < HEIGHT_90 && (bpos.getY() & 3) == offset && NOISE.eval(bpos.getX() * 0.1, bpos.getY() * 0.1, bpos.getZ() * 0.1) > 0) {
				if (random.nextInt((int)(32*scale_factor)) == 0 && ! context.BLOCKS.contains(bpos.north()))
					makeMushroom(level, bpos.north(), random.nextDouble() * 3 + 1.5, blockBox);
				if (random.nextInt((int)(32*scale_factor)) == 0 && ! context.BLOCKS.contains(bpos.south()))
					makeMushroom(level, bpos.south(), random.nextDouble() * 3 + 1.5, blockBox);
				if (random.nextInt((int)(32*scale_factor)) == 0 && ! context.BLOCKS.contains(bpos.east()))
					makeMushroom(level, bpos.east(), random.nextDouble() * 3 + 1.5, blockBox);
				if (random.nextInt((int)(32*scale_factor)) == 0 && ! context.BLOCKS.contains(bpos.west()))
					makeMushroom(level, bpos.west(), random.nextDouble() * 3 + 1.5, blockBox);
			}

			if (bpos.getY() > HEIGHT_64) {
				if (!blockUp && level.getBlockState(bpos.above()).getMaterial().isReplaceable()) {
					BlocksHelper.setWithoutUpdate(level, bpos.above(), NetherBlocks.MOSS_COVER.defaultBlockState());
				}

				if (NOISE.eval(bpos.getX() * 0.05, bpos.getY() * 0.05, bpos.getZ() * 0.05) > 0) {
					state = wallPlants[random.nextInt(wallPlants.length)].defaultBlockState();
					BlockPos _pos = bpos.north();
					if (random.nextInt(8) == 0 && ! context.BLOCKS.contains(_pos) && level.isEmptyBlock(_pos) && _pos.getZ() >= blockBox.minZ())
						BlocksHelper.setWithoutUpdate(level, _pos, state.setValue(BlockPlantWall.FACING, Direction.NORTH));

					_pos = bpos.south();
					if (random.nextInt(8) == 0 && ! context.BLOCKS.contains(_pos) && level.isEmptyBlock(_pos) && _pos.getZ() <= blockBox.maxZ())
						BlocksHelper.setWithoutUpdate(level, _pos, state.setValue(BlockPlantWall.FACING, Direction.SOUTH));

					_pos = bpos.east();
					if (random.nextInt(8) == 0 && ! context.BLOCKS.contains(_pos) && level.isEmptyBlock(_pos) && _pos.getX() <= blockBox.maxX())
						BlocksHelper.setWithoutUpdate(level, _pos, state.setValue(BlockPlantWall.FACING, Direction.EAST));

					_pos = bpos.west();
					if (random.nextInt(8) == 0 && ! context.BLOCKS.contains(_pos) && level.isEmptyBlock(_pos) && _pos.getX() >= blockBox.minX())
						BlocksHelper.setWithoutUpdate(level, _pos, state.setValue(BlockPlantWall.FACING, Direction.WEST));
				}
			}
		}
	}

	private void buildBigCircle(ServerLevelAccessor level, BlockPos seedPos, BlockPos pos, int length, int count, int iteration, double angle, double size, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		if (iteration < 0) return;
		List<List<BlockPos>> lines = circleLinesEnds(level, seedPos, pos, angle, count, length, Math.abs(length) * 0.7, random, iteration==0, MAX_HEIGHT);
		double sizeSmall = size * 0.8;
		length *= 0.8;
		angle += Math.PI * 4 / count;
		angle += random.nextDouble() * angle * 0.75;
		for (List<BlockPos> line : lines) {
			drawLine(level, line, size, MAX_HEIGHT, context);
			buildBigCircle(level, seedPos, line.get(1), length, count, iteration - 1, angle, sizeSmall, random, MAX_HEIGHT, context);
		}
	}

	private void drawLine(ServerLevelAccessor level, List<BlockPos> blocks, double radius, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		for (int i = 0; i < blocks.size() - 1; i++) {
			BlockPos a = blocks.get(i);
			BlockPos b = blocks.get(i + 1);
			if (b.getY() < a.getY()) {
				BlockPos c = b;
				b = a;
				a = c;
			}
			double max = b.getY() - a.getY();
			if (max < 1) max = 1;
			for (int y = a.getY(); y <= b.getY(); y++)
				cylinder(lerpCos(a, b, y, (y - a.getY()) / max), radius, MAX_HEIGHT, context);
		}
	}

	private BlockPos lerp(BlockPos start, BlockPos end, double mix) {
		double x = Mth.lerp(mix, start.getX(), end.getX());
		double y = Mth.lerp(mix, start.getY(), end.getY());
		double z = Mth.lerp(mix, start.getZ(), end.getZ());
		return new BlockPos(x, y, z);
	}

	private BlockPos lerpCos(BlockPos start, BlockPos end, int y, double mix) {
		double v = lcos(mix);
		double x = Mth.lerp(v, start.getX(), end.getX());
		double z = Mth.lerp(v, start.getZ(), end.getZ());
		return new BlockPos(x, y, z);
	}

	private double lcos(double mix) {
		return Mth.clamp(0.5 - Math.cos(mix * Math.PI) * 0.5, 0, 1);
	}

	private List<BlockPos> line(BlockPos start, BlockPos end, int count, Random random, double range) {
		List<BlockPos> result = new ArrayList<BlockPos>(count);
		int max = count - 1;
		result.add(start);
		for (int i = 1; i < max; i++) {
			double delta = (double) i / max;
			double x = Mth.lerp(delta, start.getX(), end.getX()) + random.nextGaussian() * range;
			double y = Mth.lerp(delta, start.getY(), end.getY());
			double z = Mth.lerp(delta, start.getZ(), end.getZ()) + random.nextGaussian() * range;
			result.add(new BlockPos(x, y, z));
		}
		result.add(end);
		return result;
	}

	private void cylinder(BlockPos pos, double radius, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		int x1 = MHelper.floor(pos.getX() - radius);
		int z1 = MHelper.floor(pos.getZ() - radius);
		int x2 = MHelper.floor(pos.getX() + radius + 1);
		int z2 = MHelper.floor(pos.getZ() + radius + 1);
		radius *= radius;

		for (int x = x1; x <= x2; x++) {
			int px2 = x - pos.getX();
			px2 *= px2;
			for (int z = z1; z <= z2; z++) {
				int pz2 = z - pos.getZ();
				pz2 *= pz2;
				if (px2 + pz2 <= radius * (NOISE.eval(x * 0.5, pos.getY() * 0.5, z * 0.5) * 0.25 + 0.75) && pos.getY()>2 && pos.getY()<MAX_HEIGHT-2)  context.BLOCKS.add(new BlockPos(x, pos.getY(), z));
			}
		}
	}

	private List<List<BlockPos>> circleLinesEnds(ServerLevelAccessor level, BlockPos seedPos, BlockPos pos, double startAngle, int count, int length, double inRadius, Random random, boolean findSurface, final int MAX_HEIGHT) {
		final int MAX_DIST = 16;
		List<List<BlockPos>> result = new ArrayList<List<BlockPos>>(count);
		double angle = Math.PI * 2 / count;
		for (int i = 0; i < count; i++) {
			double radius = inRadius * (random.nextDouble(0.25)+0.8); //jes the sum may be bigger than one :)
			
			double x = pos.getX() + Math.sin(startAngle) * radius;
			if (x-seedPos.getX()>MAX_DIST) x = seedPos.getX()+MAX_DIST - random.nextInt(10);
			if (x-seedPos.getX()<-MAX_DIST) x = seedPos.getX()-MAX_DIST + random.nextInt(10);
			
			double z = pos.getZ() + Math.cos(startAngle) * radius;
			if (z-seedPos.getZ()>MAX_DIST) z = seedPos.getZ()+MAX_DIST - random.nextInt(10);
			if (z-seedPos.getZ()<-MAX_DIST) z = seedPos.getZ()-MAX_DIST + random.nextInt(10);
			
			BlockPos end = new BlockPos(x, pos.getY() + length + length * random.nextDouble() * 0.5, z);
			List<BlockPos> elem = new ArrayList<>(2);
			elem.add(pos);
			elem.add(end);
			result.add(elem);
			
			if (findSurface && end.getY()>2 && end.getY()<MAX_HEIGHT-2) {
				int dist = length<0?-BlocksHelper.downRay(level, end, Math.abs(length*2)):BlocksHelper.upRay(level, end, Math.abs(length*2));
				if (dist>0){
					if (Math.abs(seedPos.getX()-x)>MAX_DIST || Math.abs(seedPos.getZ()-z)>MAX_DIST) radius = 2;
					result.addAll(circleLinesEnds(level, seedPos, end, random.nextFloat(360), radius<5?1:(count%2 + 1), dist, radius/2, random, findSurface, MAX_HEIGHT));
				}
			}
			
			startAngle += angle;
		}
		return result;
	}

	protected static void makeMushroom(ServerLevelAccessor world, BlockPos pos, double radius, BoundingBox bounds) {
		if (!world.getBlockState(pos).getMaterial().isReplaceable()) return;

		int x1 = MHelper.floor(pos.getX() - radius);
		int z1 = MHelper.floor(pos.getZ() - radius);
		int x2 = MHelper.floor(pos.getX() + radius + 1);
		int z2 = MHelper.floor(pos.getZ() + radius + 1);
		radius *= radius;

		List<BlockPos> placed = new ArrayList<BlockPos>((int) (radius * 4));
		for (int x = x1; x <= x2; x++) {
			int px2 = x - pos.getX();
			px2 *= px2;
			for (int z = z1; z <= z2; z++) {
				int pz2 = z - pos.getZ();
				pz2 *= pz2;
				if (px2 + pz2 <= radius) {
					BlockPos p = new BlockPos(x, pos.getY(), z);
					if (world.getBlockState(p).getMaterial().isReplaceable() && bounds.isInside(p)) {
						placed.add(p);
					}
				}
			}
		}

		for (BlockPos p : placed) {
			boolean north = world.getBlockState(p.north()).getBlock() != NetherBlocks.GIANT_LUCIS;
			boolean south = world.getBlockState(p.south()).getBlock() != NetherBlocks.GIANT_LUCIS;
			boolean east = world.getBlockState(p.east()).getBlock() != NetherBlocks.GIANT_LUCIS;
			boolean west = world.getBlockState(p.west()).getBlock() != NetherBlocks.GIANT_LUCIS;
			BlockState state = NetherBlocks.GIANT_LUCIS.defaultBlockState();
			BlocksHelper.setWithoutUpdate(world, p, state
					.setValue(HugeMushroomBlock.NORTH, north)
					.setValue(HugeMushroomBlock.SOUTH, south)
					.setValue(HugeMushroomBlock.EAST, east)
					.setValue(HugeMushroomBlock.WEST, west));
		}
	}
}
