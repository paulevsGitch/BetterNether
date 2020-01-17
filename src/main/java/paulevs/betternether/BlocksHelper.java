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
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import paulevs.betternether.blocks.BlockFarmland;
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
				b == BlocksRegister.BLOCK_CINCINNASITE_ORE ||
				b == BlocksRegister.BLOCK_NETHERRACK_MOSS;
	}
	
	public static boolean isNetherGround(BlockState state)
	{
		Block b = state.getBlock();
		return  b == Blocks.NETHERRACK ||
				b == Blocks.NETHER_QUARTZ_ORE ||
				b == Blocks.SOUL_SAND ||
				b == BlocksRegister.FARMLAND ||
				b == BlocksRegister.BLOCK_CINCINNASITE_ORE ||
				b == BlocksRegister.BLOCK_NETHERRACK_MOSS;
	}
	
	public static boolean isNetherGroundMagma(BlockState state)
	{
		Block b = state.getBlock();
		return  b == Blocks.NETHERRACK ||
				b == Blocks.NETHER_QUARTZ_ORE ||
				b == Blocks.SOUL_SAND ||
				b == Blocks.MAGMA_BLOCK ||
				b == BlocksRegister.FARMLAND ||
				b == BlocksRegister.BLOCK_CINCINNASITE_ORE ||
				b == BlocksRegister.BLOCK_NETHERRACK_MOSS;
	}
	
	public static boolean isBone(BlockState state)
	{
		Block b = state.getBlock();
		return  b == Blocks.BONE_BLOCK ||
				b == BlocksRegister.BLOCK_BONE;
	}
	
	public static boolean isGroundOrModContent(BlockState state)
	{
		Block b = state.getBlock();
		return  b == Blocks.NETHERRACK ||
				b == Blocks.NETHER_QUARTZ_ORE ||
				b == Blocks.SOUL_SAND ||
				b == BlocksRegister.FARMLAND ||
				b == BlocksRegister.BLOCK_CINCINNASITE_ORE ||
				b == BlocksRegister.BLOCK_NETHERRACK_MOSS ||
				Registry.BLOCK.getId(b).getNamespace().equals(BetterNether.MOD_ID);
	}
	
	public static void setWithoutUpdate(IWorld world, BlockPos pos, BlockState state)
	{
		world.setBlockState(pos, state, 19);
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
