package paulevs.betternether;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.material.LavaFluid;
import paulevs.betternether.blocks.BlockFarmland;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.registry.NetherTags;
import ru.bclib.api.TagAPI;

public class BlocksHelper {
	public static final int FLAG_UPDATE_BLOCK = 1;
	public static final int FLAG_SEND_CLIENT_CHANGES = 2;
	public static final int FLAG_NO_RERENDER = 4;
	public static final int FORSE_RERENDER = 8;
	public static final int FLAG_IGNORE_OBSERVERS = 16;

	public static final int SET_SILENT = FLAG_UPDATE_BLOCK | FLAG_IGNORE_OBSERVERS | FLAG_SEND_CLIENT_CHANGES;
	public static final int SET_UPDATE = FLAG_UPDATE_BLOCK | FLAG_SEND_CLIENT_CHANGES;
	public static final Direction[] HORIZONTAL = new Direction[] { Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST };

	private static final Vec3i[] OFFSETS = new Vec3i[] {
			new Vec3i(-1, -1, -1), new Vec3i(-1, -1, 0), new Vec3i(-1, -1, 1),
			new Vec3i(-1, 0, -1), new Vec3i(-1, 0, 0), new Vec3i(-1, 0, 1),
			new Vec3i(-1, 1, -1), new Vec3i(-1, 1, 0), new Vec3i(-1, 1, 1),

			new Vec3i(0, -1, -1), new Vec3i(0, -1, 0), new Vec3i(0, -1, 1),
			new Vec3i(0, 0, -1), new Vec3i(0, 0, 0), new Vec3i(0, 0, 1),
			new Vec3i(0, 1, -1), new Vec3i(0, 1, 0), new Vec3i(0, 1, 1),

			new Vec3i(1, -1, -1), new Vec3i(1, -1, 0), new Vec3i(1, -1, 1),
			new Vec3i(1, 0, -1), new Vec3i(1, 0, 0), new Vec3i(1, 0, 1),
			new Vec3i(1, 1, -1), new Vec3i(1, 1, 0), new Vec3i(1, 1, 1)
	};

	public static BoundingBox chunkBounds(LevelAccessor world, BlockPos pos){
		final int minBuildHeight = world.getMinBuildHeight()+1;
		final int maxBuildHeight = world.getMaxBuildHeight()-1;
		return chunkBounds(world, pos, minBuildHeight, maxBuildHeight);
	}

	public static BoundingBox chunkBounds(LevelAccessor world, BlockPos pos, int minY, int maxY){
		final int chunkStartX = (pos.getX() >> 4) << 4;
		final int chunkStartZ = (pos.getZ() >> 4) << 4;

		return new BoundingBox(chunkStartX, minY, chunkStartZ, chunkStartX+31, maxY, chunkStartZ+31);
	}

	public static BoundingBox decorationBounds(LevelAccessor world, BlockPos pos){
		final int minBuildHeight = world.getMinBuildHeight()+1;
		final int maxBuildHeight = world.getMaxBuildHeight()-1;
		return decorationBounds(world, pos, minBuildHeight, maxBuildHeight);
	}

	public static BoundingBox decorationBounds(LevelAccessor world, BlockPos pos, int minY, int maxY){
		final int chunkStartX = (pos.getX() >> 4) << 4;
		final int chunkStartZ = (pos.getZ() >> 4) << 4;

		return new BoundingBox(chunkStartX-16, minY, chunkStartZ-16, chunkStartX+31, maxY, chunkStartZ+31);
	}

	public static boolean isLava(BlockState state) {
		return state.getFluidState().getType() instanceof LavaFluid;
	}

	public static boolean isNetherrack(BlockState state) {
		return state.is(TagAPI.BLOCK_NETHERRACK);
	}

	public static boolean isSoulSand(BlockState state) {
		return state.is(TagAPI.BLOCK_SOUL_GROUND);
	}

	public static boolean isNetherGround(BlockState state) {
		return isNetherrack(state) || isSoulSand(state) || isNetherMycelium(state) || isNylium(state);
		// return state.isIn(NetherTags.NETHER_GROUND);
	}

	public static boolean isNetherGroundMagma(BlockState state) {
		return isNetherGround(state) || state.getBlock() == Blocks.MAGMA_BLOCK;
	}

	public static boolean isBone(BlockState state) {
		Block b = state.getBlock();
		return b == Blocks.BONE_BLOCK || b == NetherBlocks.BONE_BLOCK;
	}

	public static boolean isNetherMycelium(BlockState state) {
		return state.is(TagAPI.BLOCK_NETHER_MYCELIUM);
	}

	public static void setWithUpdate(LevelAccessor world, BlockPos pos, BlockState state, BoundingBox bounds) {
		if (bounds.isInside(pos))
            world.setBlock(pos, state, SET_UPDATE);
	}

	public static void setWithUpdate(LevelAccessor world, BlockPos pos, BlockState state) {
		world.setBlock(pos, state, SET_UPDATE);
	}

	public static void setWithoutUpdate(LevelAccessor world, BlockPos pos, BlockState state, BoundingBox bounds) {
		if (bounds.isInside(pos))
			world.setBlock(pos, state, SET_SILENT);
	}
	
	public static void setWithoutUpdate(LevelAccessor world, BlockPos pos, BlockState state) {
		world.setBlock(pos, state, SET_SILENT);
	}

	public static int upRay(LevelAccessor world, BlockPos pos, int maxDist) {
		int length = 0;
		for (int j = 1; j < maxDist && (world.isEmptyBlock(pos.above(j))); j++)
			length++;
		return length;
	}

	public static int downRay(LevelAccessor world, BlockPos pos, int maxDist) {
		int length = 0;
		for (int j = 1; j < maxDist && (world.isEmptyBlock(pos.below(j))); j++)
			length++;
		return length;
	}

	public static BlockState rotateHorizontal(BlockState state, Rotation rotation, Property<Direction> facing) {
		return (BlockState) state.setValue(facing, rotation.rotate((Direction) state.getValue(facing)));
	}

	public static BlockState mirrorHorizontal(BlockState state, Mirror mirror, Property<Direction> facing) {
		return state.rotate(mirror.getRotation((Direction) state.getValue(facing)));
	}

	public static int getLengthDown(ServerLevel world, BlockPos pos, Block block) {
		int count = 1;
		while (world.getBlockState(pos.below(count)).getBlock() == block)
			count++;
		return count;
	}

	public static boolean isFertile(BlockState state) {
		return state.getBlock() instanceof BlockFarmland;
	}

	public static void cover(LevelAccessor world, BlockPos center, Block ground, BlockState cover, int radius, Random random) {
		HashSet<BlockPos> points = new HashSet<BlockPos>();
		HashSet<BlockPos> points2 = new HashSet<BlockPos>();
		if (world.getBlockState(center).getBlock() == ground) {
			points.add(center);
			points2.add(center);
			for (int i = 0; i < radius; i++) {
				Iterator<BlockPos> iterator = points.iterator();
				while (iterator.hasNext()) {
					BlockPos pos = iterator.next();
					for (Vec3i offset : OFFSETS) {
						if (random.nextBoolean()) {
							BlockPos pos2 = pos.offset(offset);
							if (random.nextBoolean() && world.getBlockState(pos2).getBlock() == ground && !points.contains(pos2))
								points2.add(pos2);
						}
					}
				}
				points.addAll(points2);
				points2.clear();
			}
			Iterator<BlockPos> iterator = points.iterator();
			while (iterator.hasNext()) {
				BlockPos pos = iterator.next();
				BlocksHelper.setWithoutUpdate(world, pos, cover);
			}
		}
	}

	public static boolean isNylium(BlockState state) {
		return state.is(NetherTags.NYLIUM);
	}
	
	public static boolean createLogIfFree(LevelAccessor world, BlockPos pos, BlockState anchorBlock, Direction[] directions, MutableBlockPos mutableBlockPos) {
		boolean hasNeighbor = false;
		for (Direction dir : directions){
			mutableBlockPos.setWithOffset(pos, dir);
			BlockState currentState = world.getBlockState(mutableBlockPos);
			if (currentState.hasProperty(BlockStateProperties.DISTANCE) || currentState.is(BlockTags.LOGS)) {
				hasNeighbor = true;
				break;
			}
		}
		
		if (!hasNeighbor){
			setWithoutUpdate(world, pos.above(), anchorBlock);
			return true;
		}
		
		return false;
	}
}
