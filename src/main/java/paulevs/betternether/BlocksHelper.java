package paulevs.betternether;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.blocks.BlockFarmland;
import paulevs.betternether.registry.BlocksRegistry;

public class BlocksHelper
{
	public static final int FLAG_UPDATE_BLOCK = 1;
	public static final int FLAG_SEND_CLIENT_CHANGES = 2;
	public static final int FLAG_NO_RERENDER = 4;
	public static final int FORSE_RERENDER = 8;
	public static final int FLAG_IGNORE_OBSERVERS = 16;
	
	public static final int SET_SILENT = FLAG_UPDATE_BLOCK | FLAG_IGNORE_OBSERVERS | FLAG_SEND_CLIENT_CHANGES;
	public static final Direction[] HORIZONTAL = new Direction[] {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
	
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
	
	public static boolean isLava(BlockState state)
	{
		return state.getFluidState().getFluid() instanceof LavaFluid;
	}
	
	public static boolean isNetherrack(BlockState state)
	{
		return state.isIn(NetherTags.NETHERRACK);
	}
	
	public static boolean isSoulSand(BlockState state)
	{
		return state.isIn(NetherTags.SOUL_GROUND_BLOCK);
	}
	
	public static boolean isNetherGround(BlockState state)
	{
		return isNetherrack(state) || isSoulSand(state) || isNetherMycelium(state)|| isNylium(state);
		//return state.isIn(NetherTags.NETHER_GROUND);
	}
	
	public static boolean isNetherGroundMagma(BlockState state)
	{
		return  isNetherGround(state) || state.getBlock() == Blocks.MAGMA_BLOCK;
	}
	
	public static boolean isBone(BlockState state)
	{
		Block b = state.getBlock();
		return b == Blocks.BONE_BLOCK || b == BlocksRegistry.BONE_BLOCK;
	}
	
	public static boolean isNetherMycelium(BlockState state)
	{
		return state.isIn(NetherTags.MYCELIUM);
	}
	
	public static void setWithoutUpdate(WorldAccess world, BlockPos pos, BlockState state)
	{
		world.setBlockState(pos, state, SET_SILENT);
	}
	
	public static int upRay(WorldAccess world, BlockPos pos, int maxDist)
	{
		int length = 0;
		for (int j = 1; j < maxDist && (world.isAir(pos.up(j))); j++)
			length ++;
		return length;
	}
	
	public static int downRay(WorldAccess world, BlockPos pos, int maxDist)
	{
		int length = 0;
		for (int j = 1; j < maxDist && (world.isAir(pos.down(j))); j++)
			length ++;
		return length;
	}

	public static BlockState rotateHorizontal(BlockState state, BlockRotation rotation, Property<Direction> facing)
	{
		return (BlockState)state.with(facing, rotation.rotate((Direction)state.get(facing)));
	}

	public static BlockState mirrorHorizontal(BlockState state, BlockMirror mirror, Property<Direction> facing)
	{
		return state.rotate(mirror.getRotation((Direction)state.get(facing)));
	}
	
	public static int getLengthDown(ServerWorld world, BlockPos pos, Block block)
	{
		int count = 1;
		while (world.getBlockState(pos.down(count)).getBlock() == block)
			count ++;
		return count;
	}
	
	public static boolean isFertile(BlockState state)
	{
		return state.getBlock() instanceof BlockFarmland;
	}
	
	public static void cover(WorldAccess world, BlockPos center, Block ground, BlockState cover, int radius, Random random)
	{
		HashSet<BlockPos> points = new HashSet<BlockPos>();
		HashSet<BlockPos> points2 = new HashSet<BlockPos>();
		if (world.getBlockState(center).getBlock() == ground)
		{
			points.add(center);
			points2.add(center);
			for (int i = 0; i < radius; i++)
			{
				Iterator<BlockPos> iterator = points.iterator();
				while (iterator.hasNext())
				{
					BlockPos pos = iterator.next();
					for (Vec3i offset: OFFSETS)
					{
						if (random.nextBoolean())
						{
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
			while (iterator.hasNext())
			{
				BlockPos pos = iterator.next();
				BlocksHelper.setWithoutUpdate(world, pos, cover);
			}
		}
	}

	public static boolean isNylium(BlockState state)
	{
		return state.isIn(NetherTags.NYLIUM);
	}
}
