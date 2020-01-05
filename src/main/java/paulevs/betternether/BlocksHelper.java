package paulevs.betternether;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import paulevs.betternether.registers.BlocksRegister;

public class BlocksHelper
{
	public static boolean isLava(BlockState state)
	{
		return state.getMaterial() == Material.LAVA;
	}
	
	public static boolean isNetherrack(BlockState state)
	{
		Block b = state.getBlock();
		return  b == Blocks.NETHERRACK ||
				b == Blocks.NETHER_QUARTZ_ORE ||
				b == BlocksRegister.BLOCK_NETHERRACK_MOSS;
	}
	
	public static boolean isNetherGround(BlockState state)
	{
		Block b = state.getBlock();
		return  b == Blocks.NETHERRACK ||
				b == Blocks.SOUL_SAND ||
				b == BlocksRegister.BLOCK_NETHERRACK_MOSS;
	}
	
	public static void setWithoutUpdate(ServerWorld world, BlockPos pos, BlockState state)
	{
		world.setBlockState(pos, state, 19);
	}
	
	public static int upRay(World world, BlockPos pos, int maxDist)
	{
		int length = 0;
		for (int j = 1; j < maxDist && (world.getBlockState(pos.up(j)).getBlock() == Blocks.AIR); j++)
			length ++;
		return length;
	}
	
	public static int downRay(World world, BlockPos pos, int maxDist)
	{
		int length = 0;
		for (int j = 1; j < maxDist && (world.getBlockState(pos.down(j)).getBlock() == Blocks.AIR); j++)
			length ++;
		return length;
	}
}
