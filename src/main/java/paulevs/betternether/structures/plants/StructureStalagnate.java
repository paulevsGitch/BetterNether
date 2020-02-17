package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
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
	public void generate(IWorld world, BlockPos pos, Random random)
	{
		int length = BlocksHelper.upRay(world, pos, MAX_LENGTH);
		if (length > MIN_LENGTH && BlocksHelper.isNetherrack(world.getBlockState(pos.up(length + 1))))
		{
			BlockState bottom = BlocksRegister.STALAGNATE.getDefaultState().with(BlockStalagnate.SHAPE, TripleShape.BOTTOM);
			BlockState middle = BlocksRegister.STALAGNATE.getDefaultState();
			BlockState top = BlocksRegister.STALAGNATE.getDefaultState().with(BlockStalagnate.SHAPE, TripleShape.TOP);
			
			BlocksHelper.setWithoutUpdate(world, pos, bottom);
			BlocksHelper.setWithoutUpdate(world, pos.up(length), top);
			for (int y = 1; y < length; y++)
				BlocksHelper.setWithoutUpdate(world, pos.up(y), middle);
		}
	}
	
	public void generateDown(World world, BlockPos pos, Random random)
	{
		int length = BlocksHelper.downRay(world, pos, MAX_LENGTH);
		if (length > MIN_LENGTH && BlocksHelper.isNetherrack(world.getBlockState(pos.down(length + 1))))
		{
			BlockState bottom = BlocksRegister.STALAGNATE.getDefaultState().with(BlockStalagnate.SHAPE, TripleShape.BOTTOM);
			BlockState middle = BlocksRegister.STALAGNATE.getDefaultState();
			BlockState top = BlocksRegister.STALAGNATE.getDefaultState().with(BlockStalagnate.SHAPE, TripleShape.TOP);
			
			BlocksHelper.setWithoutUpdate(world, pos.down(length), bottom);
			BlocksHelper.setWithoutUpdate(world, pos, top);
			for (int y = 1; y < length; y++)
				BlocksHelper.setWithoutUpdate(world, pos.down(y), middle);
		}
	}
}
