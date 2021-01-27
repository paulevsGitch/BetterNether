package com.paulevs.betternether;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import com.paulevs.betternether.blocks.BlockFarmland;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.state.Property;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;


public class BlocksHelper {
	public static final int FLAG_UPDATE_BLOCK = 1;
	public static final int FLAG_SEND_CLIENT_CHANGES = 2;
	public static final int FLAG_NO_RERENDER = 4;
	public static final int FORSE_RERENDER = 8;
	public static final int FLAG_IGNORE_OBSERVERS = 16;

	public static final int SET_SILENT = FLAG_UPDATE_BLOCK | FLAG_IGNORE_OBSERVERS | FLAG_SEND_CLIENT_CHANGES;
	public static final int SET_UPDATE = FLAG_UPDATE_BLOCK | FLAG_SEND_CLIENT_CHANGES;
	public static final Direction[] HORIZONTAL = new Direction[] { Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST };

	private static final Vector3i[] OFFSETS = new Vector3i[] {
			new Vector3i(-1, -1, -1), new Vector3i(-1, -1, 0), new Vector3i(-1, -1, 1),
			new Vector3i(-1, 0, -1), new Vector3i(-1, 0, 0), new Vector3i(-1, 0, 1),
			new Vector3i(-1, 1, -1), new Vector3i(-1, 1, 0), new Vector3i(-1, 1, 1),

			new Vector3i(0, -1, -1), new Vector3i(0, -1, 0), new Vector3i(0, -1, 1),
			new Vector3i(0, 0, -1), new Vector3i(0, 0, 0), new Vector3i(0, 0, 1),
			new Vector3i(0, 1, -1), new Vector3i(0, 1, 0), new Vector3i(0, 1, 1),

			new Vector3i(1, -1, -1), new Vector3i(1, -1, 0), new Vector3i(1, -1, 1),
			new Vector3i(1, 0, -1), new Vector3i(1, 0, 0), new Vector3i(1, 0, 1),
			new Vector3i(1, 1, -1), new Vector3i(1, 1, 0), new Vector3i(1, 1, 1)
	};

	public static boolean isLava(BlockState state) {
		return state.getFluidState().getFluid() instanceof LavaFluid;
	}

	public static boolean isNetherrack(BlockState state) {
		return state.isIn(NetherTags.Blocks.NETHERRACK);
	}

	public static boolean isSoulSand(BlockState state) {
		return state.isIn(NetherTags.Blocks.SOUL_GROUND);
	}

	public static boolean isNetherGround(BlockState state) {
		return isNetherrack(state) || isSoulSand(state) || isNetherMycelium(state) || isNylium(state);

	}

	public static boolean isNetherGroundMagma(BlockState state) {
		return isNetherGround(state) || state.getBlock() == Blocks.MAGMA_BLOCK;
	}

	//public static boolean isBone(BlockState state) {
	//Block b = state.getBlock();
	//	return b == Blocks.BONE_BLOCK || b == BlocksRegistry.BONE_BLOCK;
	//}

	public static boolean isNetherMycelium(BlockState state) {
		return state.isIn(NetherTags.Blocks.MYCELIUM);
	}

	public static void setWithUpdate(IWorld world, BlockPos pos, BlockState state) {
		world.setBlockState(pos, state, SET_UPDATE);
	}
	
	public static void setWithoutUpdate(IWorld world, BlockPos pos, BlockState state) {
		world.setBlockState(pos, state, SET_SILENT);
	}

	public static int upRay(IWorld world, BlockPos pos, int maxDist) {
		int length = 0;
		for (int j = 1; j < maxDist && (world.isAirBlock(pos.up(j))); j++)
			length++;
		return length;
	}

	public static int downRay(IWorld world, BlockPos pos, int maxDist) {
		int length = 0;
		for (int j = 1; j < maxDist && (world.isAirBlock(pos.down(j))); j++)
			length++;
		return length;
	}

	public static BlockState rotateHorizontal(BlockState state, Rotation rotation, Property<Direction> facing) {
		return (BlockState) state.with(facing, rotation.rotate((Direction) state.get(facing)));
	}

	public static BlockState mirrorHorizontal(BlockState state, Mirror mirror, Property<Direction> facing) {
		return state.rotate(mirror.toRotation((Direction) state.get(facing)));
	}

	public static int getLengthDown(ServerWorld world, BlockPos pos, Block block) {
		int count = 1;
		while (world.getBlockState(pos.down(count)).getBlock() == block)
			count++;
		return count;
	}

	public static boolean isFertile(BlockState state) {
		return state.getBlock() instanceof BlockFarmland;
	}

	public static void cover(IWorld world, BlockPos center, Block ground, BlockState cover, int radius, Random random) {
		HashSet<BlockPos> points = new HashSet<BlockPos>();
		HashSet<BlockPos> points2 = new HashSet<BlockPos>();
		if (world.getBlockState(center).getBlock() == ground) {
			points.add(center);
			points2.add(center);
			for (int i = 0; i < radius; i++) {
				Iterator<BlockPos> iterator = points.iterator();
				while (iterator.hasNext()) {
					BlockPos pos = iterator.next();
					for (Vector3i offset : OFFSETS) {
						if (random.nextBoolean()) {
							BlockPos pos2 = pos.add(offset);
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
	return state.isIn(NetherTags.Blocks.NYLIUM);
	}
}
