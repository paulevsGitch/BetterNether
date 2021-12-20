package paulevs.betternether.world.structures.plants;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.structures.IGrowableStructure;
import paulevs.betternether.world.structures.IStructure;

public class StructureNetherSakura implements IStructure, IGrowableStructure {
	private static final Map<BlockPos, Byte> LOGS_DIST = new HashMap<>();
	
	private void updateSDFFrom(BlockPos bpos) {
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
	
	private void updateDistances(ServerLevelAccessor world) {
		for (Entry<BlockPos, Byte> entry : LOGS_DIST.entrySet()){
			final int dist = entry.getValue();
			final BlockPos logPos = entry.getKey();
			
			BlockState currentState = world.getBlockState(logPos);
			if (currentState.hasProperty(BlockStateProperties.DISTANCE) ) {
				int cDist = currentState.getValue(BlockStateProperties.DISTANCE);
				if (dist < cDist) {
					BlocksHelper.setWithoutUpdate(world, logPos, currentState.setValue(BlockStateProperties.DISTANCE, dist));
					cDist = dist;
				}
				
				if (cDist>=7){
					BlocksHelper.setWithoutUpdate(world, logPos, Blocks.AIR.defaultBlockState());
				}
			}
		}
	}

	public StructureNetherSakura() {}

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT) {
		if (pos.getY() < MAX_HEIGHT*0.75) return;
		grow(world, pos, random, true, MAX_HEIGHT);
	}
	
	
	public void grow(ServerLevelAccessor world, BlockPos pos, Random random, boolean natural, final int MAX_HEIGHT) {
		final MutableBlockPos POS = new MutableBlockPos();

		final float scale_factor = MAX_HEIGHT/128.0f;
		
		LOGS_DIST.clear();
		int l = MHelper.randRange((int)(15*scale_factor), (int)(24*scale_factor), random);
		double height = MHelper.randRange((int)(10*scale_factor), (int)(15*scale_factor), random);
		double radius = height * (0.2 + random.nextDouble() * 0.1);

		if ((l + height) - BlocksHelper.downRay(world, pos, (int) (l + height)) > 10) return;

		l = BlocksHelper.downRay(world, pos, l + 1);
		int l2 = l * 2 / 3;
		for (int x = -3; x <= 3; x++) {
			int x2 = x * x;
			POS.setX(pos.getX() + x);
			for (int z = -3; z <= 3; z++) {
				int z2 = z * z;
				double d = x2 + z2 + 1.4;
				if (d < 10) {
					if (d < 2.8 || random.nextBoolean()) {
						POS.setZ(pos.getZ() + z);
						double length = MHelper.randRange(l2, l, random) / (d > 2 ? d : 1);
						if (length < 1) length = 1;
						int start = MHelper.randRange(-2, 0, random);
						for (int y = start; y < length; y++) {
							POS.setY(pos.getY() - y);
							if (canReplace(world.getBlockState(POS))) {
								BlocksHelper.setWithUpdate(world, POS, NetherBlocks.MAT_NETHER_SAKURA.getLog().defaultBlockState());
								updateSDFFrom(POS);
							}
						}
						if (NetherBlocks.MAT_NETHER_SAKURA.isTreeLog(world.getBlockState(POS).getBlock())) {
							BlocksHelper.setWithUpdate(world, POS, NetherBlocks.MAT_NETHER_SAKURA.getBark()
																								 .defaultBlockState());
							updateSDFFrom(POS);
						}
					}

					if (d < 2) {
						crown(world, POS, radius, height, random);
					}
				}
			}
		}
		
		updateDistances(world);
		LOGS_DIST.clear();
	}

	private void crown(LevelAccessor world, BlockPos pos, double radius, double height, Random random) {
		final MutableBlockPos POS2 = new MutableBlockPos();
		BlockState leaves = NetherBlocks.NETHER_SAKURA_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true);
		double r2 = radius * radius;
		int start = (int) Math.floor(-radius);
		for (int cy = 0; cy <= radius; cy++) {
			int cy2 = cy * cy;
			POS2.setY((int) (pos.getY() + cy));
			for (int cx = start; cx <= radius; cx++) {
				int cx2 = cx * cx;
				POS2.setX(pos.getX() + cx);
				for (int cz = start; cz <= radius; cz++) {
					int cz2 = cz * cz;
					if (cx2 + cy2 + cz2 < r2) {
						POS2.setZ(pos.getZ() + cz);
						if (world.getBlockState(POS2).getMaterial().isReplaceable()) BlocksHelper.setWithUpdate(world, POS2, leaves);
					}
				}
			}
		}
		BlockState state;
		for (int cy = 0; cy <= height; cy++) {
			r2 = radius * (1 - (double) cy / height);
			r2 *= r2;
			POS2.setX(pos.getX());
			POS2.setZ(pos.getZ());
			POS2.setY(pos.getY() - cy);
			if (!(state = world.getBlockState(POS2)).getMaterial().isReplaceable() && !NetherBlocks.MAT_NETHER_SAKURA.isTreeLog(state.getBlock())) return;
			for (int cx = start; cx <= radius; cx++) {
				int cx2 = cx * cx;
				POS2.setX(pos.getX() + cx);
				for (int cz = start; cz <= radius; cz++) {
					int cz2 = cz * cz;
					if (cx2 + cz2 < r2) {
						POS2.setZ(pos.getZ() + cz);
						if (world.getBlockState(POS2).getMaterial().isReplaceable()) BlocksHelper.setWithUpdate(world, POS2, leaves);
					}
				}
			}
		}
	}

	private boolean canReplace(BlockState state) {
		return BlocksHelper.isNetherGround(state) || state.getMaterial().isReplaceable();
	}
	
	@Override
	public void grow(ServerLevelAccessor world, BlockPos pos, Random random) {
		grow(world, pos, random, false, 128);
	}
}
