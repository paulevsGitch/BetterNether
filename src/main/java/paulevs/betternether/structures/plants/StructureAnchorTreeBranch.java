package paulevs.betternether.structures.plants;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.BlockAnchorTreeVine;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.structures.IStructure;

public class StructureAnchorTreeBranch implements IStructure {
	private static final float[] CURVE_X = new float[] { 9F, 7F, 1.5F, 0.5F, 3F, 7F };
	private static final float[] CURVE_Y = new float[] { -20F, -17F, -12F, -4F, 0F, 2F };
	private static final int MIDDLE_Y = 10;
	private static final Set<BlockPos> POINTS = new HashSet<BlockPos>();
	private static final Set<BlockPos> MIDDLE = new HashSet<BlockPos>();
	private static final Set<BlockPos> TOP = new HashSet<BlockPos>();
	private static final MutableBlockPos POS = new MutableBlockPos();
	private static final Map<BlockPos, Byte> LOGS_DIST = new HashMap<>();
	public StructureAnchorTreeBranch() {}

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random) {
		if (pos.getY() < 96) return;
		grow(world, pos, random, true);
	}

	public void grow(ServerLevelAccessor world, BlockPos pos, Random random, boolean natural) {
		world.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
		float scale = MHelper.randRange(0.5F, 1F, random);
		int minCount = scale < 0.75 ? 3 : 4;
		int maxCount = scale < 0.75 ? 5 : 7;
		int count = MHelper.randRange(minCount, maxCount, random);
		MutableBlockPos mutableBlockPos = new MutableBlockPos();
		
		for (int n = 0; n < count; n++) {
			float branchSize = MHelper.randRange(0.5F, 0.8F, random) * scale;
			float angle = n * MHelper.PI2 / count;
			float radius = CURVE_X[0] * branchSize;
			int x1 = Math.round(pos.getX() + radius * (float) Math.cos(angle) + MHelper.randRange(-2F, 2F, random) * branchSize);
			int y1 = Math.round(pos.getY() + CURVE_Y[0] * branchSize + MHelper.randRange(-2F, 2F, random) * branchSize);
			int z1 = Math.round(pos.getZ() + radius * (float) Math.sin(angle) + MHelper.randRange(-2F, 2F, random) * branchSize);
			float crownR = 9 * branchSize;
			if (crownR < 1.5F)
				crownR = 1.5F;
			crown(world, new BlockPos(x1, y1 + 1, z1), crownR, random);

			int middle = Math.round(pos.getY() + (MIDDLE_Y + MHelper.randRange(-2, 2, random)) * branchSize);
			boolean generate = true;
			for (int i = 1; i < CURVE_X.length && generate; i++) {
				radius = CURVE_X[i] * branchSize;
				int x2 = Math.round(pos.getX() + radius * (float) Math.cos(angle) + MHelper.randRange(-2F, 2F, random) * branchSize);
				int y2 = Math.round(pos.getY() + CURVE_Y[i] * branchSize + (CURVE_Y[i] > 0 ? MHelper.randRange(-2F, 2F, random) * branchSize : 0));
				int z2 = Math.round(pos.getZ() + radius * (float) Math.sin(angle) + MHelper.randRange(-2F, 2F, random) * branchSize);

				if (CURVE_Y[i] >= 0) {
					if (canReplace(world.getBlockState(POS.set(x2, y2, z2)))) {
						boolean noGround = true;
						for (int d = 1; d < 3; d++) {
							if (!canReplace(world.getBlockState(POS.set(x2, y2 - d, z2)))) {
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

				line(world, x1, y1, z1, x2, y2, z2, middle);
				x1 = x2;
				y1 = y2;
				z1 = z2;
			}
		}

		BlockState state;
		Iterator<BlockPos> iterator = TOP.iterator();
		while (iterator.hasNext()) {
			BlockPos bpos = iterator.next();
			if (bpos != null) {
				if (POINTS.contains(bpos.above()) && !TOP.contains(bpos.above()))
					iterator.remove();
			}
		}

		iterator = MIDDLE.iterator();
		while (iterator.hasNext()) {
			BlockPos bpos = iterator.next();
			if (bpos != null) {
				BlockPos up = bpos.above();
				if (MIDDLE.contains(up) || (!TOP.contains(up) && POINTS.contains(up)))
					iterator.remove();
			}
			else
				iterator.remove();
		}

		for (BlockPos bpos : POINTS) {
			if (POINTS.contains(bpos.above()) && POINTS.contains(bpos.below()))
				state = NetherBlocks.MAT_ANCHOR_TREE.getLog().defaultBlockState();
			else
				state = NetherBlocks.MAT_ANCHOR_TREE.getBark().defaultBlockState();
			BlocksHelper.setWithUpdate(world, bpos, state);
			
			for (int x=-7; x<=7; x++) {
				for (int y = -7; y <= 7; y++) {
					for (int z = -7; z <= 7; z++) {
						if (x == 0 && y == 0 && z == 0) continue;
						final int dist = Math.abs(x) + Math.abs(y) + Math.abs(z);
						if (dist<=7) {
							final BlockPos blPos = bpos.offset(x, y, z);
							LOGS_DIST.merge(blPos, (byte) dist, (oldDist, newDist) -> (byte) Math.min(oldDist, dist));
						}
					}
				}
			}
		}

		POINTS.clear();
		MIDDLE.clear();
		TOP.clear();
		
		for (Entry<BlockPos, Byte> entry : LOGS_DIST.entrySet()){
			final int dist = entry.getValue();
			final BlockPos logPos = entry.getKey();
			
			BlockState currentState = world.getBlockState(logPos);
			if (currentState.hasProperty(BlockStateProperties.DISTANCE) ) {
				int cDist = currentState.getValue(BlockStateProperties.DISTANCE);
				if (dist < cDist) {
					BlocksHelper.setWithoutUpdate(world, logPos, currentState.setValue(BlockStateProperties.DISTANCE, dist));
				}
			}
		}
		
		LOGS_DIST.clear();
	}

	private void line(LevelAccessor world, int x1, int y1, int z1, int x2, int y2, int z2, int middleY) {
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
		POINTS.add(pos);
		if (pos.getY() == middleY)
			MIDDLE.add(pos);
		else if (pos.getY() > middleY)
			TOP.add(pos);

		pos = POS.set(x2, y2, z2).immutable();
		POINTS.add(pos);
		if (pos.getY() == middleY)
			MIDDLE.add(pos);
		else if (pos.getY() > middleY)
			TOP.add(pos);

		for (int i = 0; i < mx; i++) {
			px += fdx;
			py += fdy;
			pz += fdz;

			POS.set(Math.round(px), Math.round(py), Math.round(pz));
			pos = POS.immutable();
			POINTS.add(pos);
			if (POS.getY() == middleY)
				MIDDLE.add(pos);
			else if (POS.getY() > middleY)
				TOP.add(pos);
		}
	}

	private void crown(LevelAccessor world, BlockPos pos, float radius, Random random) {
		BlockState leaves = NetherBlocks.ANCHOR_TREE_LEAVES.defaultBlockState();
		BlockState vine = NetherBlocks.ANCHOR_TREE_VINE.defaultBlockState();
		BlockState log = NetherBlocks.MAT_ANCHOR_TREE.getLog().defaultBlockState();
		float halfR = radius * 0.5F;
		float r2 = radius * radius;
		int start = (int) Math.floor(-radius);
		
		final Direction[] directions = Direction.values();
		MutableBlockPos mutableBlockPos = new MutableBlockPos();
		for (int cy = (int)Math.floor(radius); cy >= start; cy--) {
			int cy2_out = cy * cy;
			float cy2_in = cy + halfR;
			cy2_in *= cy2_in;
			POS.setY((int) (pos.getY() + cy - halfR));
			for (int cx = start; cx <= radius; cx++) {
				int cx2 = cx * cx;
				POS.setX(pos.getX() + cx);
				for (int cz = start; cz <= radius; cz++) {
					int cz2 = cz * cz;
					if (cx2 + cy2_out + cz2 < r2 && cx2 + cy2_in + cz2 > r2) {
						POS.setZ(pos.getZ() + cz);
						if (world.getBlockState(POS).getMaterial().isReplaceable()) {
							int length = BlocksHelper.downRay(world, POS, 17);
							if (length < 5) {
								BlocksHelper.setWithoutUpdate(world, POS, leaves);
								continue;
							} ;
							if (length > 15) length = MHelper.randRange(10, 15, random);
							else if (length > 10) length = MHelper.randRange(10, length, random);
							if (cz%2 == cx%2) {
								length/=3;
							}
							if (length>4) {
								for (int i = 1; i < length - 2; i++) {
									BlocksHelper.setWithoutUpdate(world, POS.below(i), vine);
								}
								BlocksHelper.setWithoutUpdate(world, POS.below(length - 2), vine.setValue(BlockAnchorTreeVine.SHAPE, TripleShape.MIDDLE));
								BlocksHelper.setWithoutUpdate(world, POS.below(length - 1), vine.setValue(BlockAnchorTreeVine.SHAPE, TripleShape.BOTTOM));
							}
							BlocksHelper.setWithoutUpdate(world, POS, leaves);
						}
					}
				}
			}
		}
	}
	
	private boolean canReplace(BlockState state) {
		return BlocksHelper.isNetherGround(state) || state.getMaterial().isReplaceable();
	}
}
