package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockBrownLargeMushroom;
import paulevs.betternether.blocks.BlockBrownLargeMushroom.BrownMushroomShape;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.structures.IStructure;

public class StructureMedBrownMushroom implements IStructure
{
	private static final Mutable POS = new Mutable();
	
	@Override
	public void generate(IWorld world, BlockPos pos, Random random)
	{
		Block under;
		if (world.getBlockState(pos.down()).getBlock() == BlocksRegister.NETHER_MYCELIUM)
		{
			for (int i = 0; i < 10; i++)
			{
				int x = pos.getX() + (int) (random.nextGaussian() * 2);
				int z = pos.getZ() + (int) (random.nextGaussian() * 2);
				if (((x + z + 1) & 1) == 0)
				{
					if (random.nextBoolean())
					{
						x += random.nextBoolean() ? 1 : -1;
					}
					else
					{
						z += random.nextBoolean() ? 1 : -1;
					}
				}
				int y = pos.getY() + random.nextInt(6);
				for (int j = 0; j < 12; j++)
				{
					POS.set(x, y - j, z);
					under = world.getBlockState(POS.down()).getBlock();
					if (under == BlocksRegister.NETHER_MYCELIUM)
					{
						grow(world, POS, random);
					}
				}
			}
		}
	}
	
	public void grow(IWorld world, BlockPos pos, Random random)
	{
		int size = 2 + random.nextInt(3);
		for (int y = 1; y <= size; y++)
			if (!world.isAir(pos.up(y)))
			{
				if (y < 3)
					return;
				size = y - 1;
				break;
			}
		boolean hasAir = true;
		for (int x = -1; x < 2; x++)
			for (int z = -1; z < 2; z++)
				hasAir = hasAir && world.isAir(pos.up(size).add(x, 0, z));
		if (hasAir)
		{
			BlockState middle = BlocksRegister
					.BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.MIDDLE);
			BlocksHelper.setWithoutUpdate(world, pos, BlocksRegister
					.BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.BOTTOM));
			for (int y = 1; y < size; y++)
				BlocksHelper.setWithoutUpdate(world, pos.up(y), middle);
			pos = pos.up(size);
			BlocksHelper.setWithoutUpdate(world, pos, BlocksRegister
					.BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.TOP));
			BlocksHelper.setWithoutUpdate(world, pos.north(), BlocksRegister
					.BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.SIDE_N));
			BlocksHelper.setWithoutUpdate(world, pos.south(), BlocksRegister
					.BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.SIDE_S));
			BlocksHelper.setWithoutUpdate(world, pos.east(), BlocksRegister
					.BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.SIDE_E));
			BlocksHelper.setWithoutUpdate(world, pos.west(), BlocksRegister
					.BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.SIDE_W));
			BlocksHelper.setWithoutUpdate(world, pos.north().east(), BlocksRegister
					.BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.CORNER_N));
			BlocksHelper.setWithoutUpdate(world, pos.north().west(), BlocksRegister
					.BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.CORNER_W));
			BlocksHelper.setWithoutUpdate(world, pos.south().east(), BlocksRegister
					.BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.CORNER_E));
			BlocksHelper.setWithoutUpdate(world, pos.south().west(), BlocksRegister
					.BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.CORNER_S));
		}
	}
}
