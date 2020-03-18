package paulevs.betternether;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import paulevs.betternether.blocks.BlockFarmland;
import paulevs.betternether.registers.BlocksRegister;

public class BlocksHelper
{
	public static final int FLAG_UPDATE_BLOCK = 1;
	public static final int FLAG_SEND_CLIENT_CHANGES = 2;
	public static final int FLAG_NO_RERENDER = 4;
	public static final int FORSE_RERENDER = 8;
	public static final int FLAG_IGNORE_OBSERVERS = 16;
	
	public static final int SET_SILENT = FLAG_UPDATE_BLOCK | FLAG_IGNORE_OBSERVERS | FLAG_SEND_CLIENT_CHANGES;
	
	public static boolean isLava(BlockState state)
	{
		return state.getMaterial() == Material.LAVA;
	}
	
	public static boolean isNetherrack(BlockState state)
	{
		Block b = state.getBlock();
		return  b == Blocks.NETHERRACK ||
				b == Blocks.NETHER_QUARTZ_ORE ||
				b == BlocksRegister.CINCINNASITE_ORE ||
				b == BlocksRegister.NETHERRACK_MOSS;
	}
	
	public static boolean isSoulSand(BlockState state)
	{
		Block b = state.getBlock();
		return  b == Blocks.SOUL_SAND ||
				b == Blocks.SOUL_SOIL;
	}
	
	public static boolean isNetherGround(BlockState state)
	{
		Block b = state.getBlock();
		return  b == Blocks.NETHERRACK ||
				b == Blocks.NETHER_QUARTZ_ORE ||
				b == Blocks.SOUL_SAND ||
				b == Blocks.SOUL_SOIL ||
				b == Blocks.CRIMSON_NYLIUM ||
				b == Blocks.WARPED_NYLIUM ||
				b == BlocksRegister.FARMLAND ||
				b == BlocksRegister.CINCINNASITE_ORE ||
				b == BlocksRegister.NETHERRACK_MOSS;
	}
	
	public static boolean isNetherGroundMagma(BlockState state)
	{
		Block b = state.getBlock();
		return  b == Blocks.NETHERRACK ||
				b == Blocks.NETHER_QUARTZ_ORE ||
				b == Blocks.SOUL_SAND ||
				b == Blocks.SOUL_SOIL ||
				b == Blocks.CRIMSON_NYLIUM ||
				b == Blocks.WARPED_NYLIUM ||
				b == Blocks.MAGMA_BLOCK ||
				b == BlocksRegister.FARMLAND ||
				b == BlocksRegister.CINCINNASITE_ORE ||
				b == BlocksRegister.NETHERRACK_MOSS;
	}
	
	public static boolean isBone(BlockState state)
	{
		Block b = state.getBlock();
		return  b == Blocks.BONE_BLOCK ||
				b == BlocksRegister.BONE;
	}
	
	public static void setWithoutUpdate(IWorld world, BlockPos pos, BlockState state)
	{
		world.setBlockState(pos, state, SET_SILENT);
	}
	
	public static int upRay(IWorld world, BlockPos pos, int maxDist)
	{
		int length = 0;
		for (int j = 1; j < maxDist && (world.isAir(pos.up(j))); j++)
			length ++;
		return length;
	}
	
	public static int downRay(IWorld world, BlockPos pos, int maxDist)
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
}
