package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockStalagnate;
import paulevs.betternether.blocks.shapes.TripleShape;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.structures.IStructure;

public class StructureStalagnate implements IStructure
{
	public static final int MAX_LENGTH = 25; // 27
	public static final int MIN_LENGTH = 3; // 5
	
	@Override
	public void generate(ServerWorld world, BlockPos pos, Random random)
	{
		int length = BlocksHelper.upRay(world, pos, MAX_LENGTH);
		if (length > MIN_LENGTH && BlocksHelper.isNetherrack(world.getBlockState(pos.up(length + 1))))
		{
			BlockState bottom = BlocksRegister.BLOCK_STALAGNATE.getDefaultState().with(BlockStalagnate.SHAPE, TripleShape.BOTTOM);
			BlockState middle = BlocksRegister.BLOCK_STALAGNATE.getDefaultState();
			BlockState top = BlocksRegister.BLOCK_STALAGNATE.getDefaultState().with(BlockStalagnate.SHAPE, TripleShape.TOP);
			
			world.setBlockState(pos, bottom);
			world.setBlockState(pos.up(length), top);
			for (int y = 1; y < length; y++)
				world.setBlockState(pos.up(y), middle);
		}
	}
	
	public void generateDown(World world, BlockPos pos, Random random)
	{
		int length = BlocksHelper.downRay(world, pos, MAX_LENGTH);
		if (length > MIN_LENGTH && BlocksHelper.isNetherrack(world.getBlockState(pos.down(length + 1))))
		{
			BlockState bottom = BlocksRegister.BLOCK_STALAGNATE.getDefaultState().with(BlockStalagnate.SHAPE, TripleShape.BOTTOM);
			BlockState middle = BlocksRegister.BLOCK_STALAGNATE.getDefaultState();
			BlockState top = BlocksRegister.BLOCK_STALAGNATE.getDefaultState().with(BlockStalagnate.SHAPE, TripleShape.TOP);
			
			world.setBlockState(pos.down(length), bottom);
			world.setBlockState(pos, top);
			for (int y = 1; y < length; y++)
				world.setBlockState(pos.down(y), middle);
		}
	}
}
