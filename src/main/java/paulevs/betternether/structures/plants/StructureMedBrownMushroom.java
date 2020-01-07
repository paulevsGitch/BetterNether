package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import paulevs.betternether.blocks.BlockBrownLargeMushroom;
import paulevs.betternether.blocks.BlockBrownLargeMushroom.BrownMushroomShape;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.structures.IStructure;

public class StructureMedBrownMushroom implements IStructure
{
	Mutable npos = new Mutable();
	
	@Override
	public void generate(ServerWorld world, BlockPos pos, Random random)
	{
		Block under;
		if (world.getBlockState(pos.down()).getBlock() == BlocksRegister.BLOCK_NETHER_MYCELIUM)
		{
			for (int i = 0; i < 10; i++)
			{
				int x = pos.getX() + (int) (random.nextGaussian() * 2);
				int z = pos.getZ() + (int) (random.nextGaussian() * 2);
				int y = pos.getY() + random.nextInt(6);
				for (int j = 0; j < 6; j++)
				{
					npos.set(x, y - j, z);
					if (npos.getY() > 31)
					{
						under = world.getBlockState(npos.down()).getBlock();
						if (under == BlocksRegister.BLOCK_NETHER_MYCELIUM)
						{
							grow(world, npos, random);
						}
					}
					else
						break;
				}
			}
		}
	}
	
	public void grow(ServerWorld world, BlockPos pos, Random random)
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
					.BLOCK_BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.MIDDLE);
			world.setBlockState(pos, BlocksRegister
					.BLOCK_BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.BOTTOM));
			for (int y = 1; y < size; y++)
				world.setBlockState(pos.up(y), middle);
			pos = pos.up(size);
			world.setBlockState(pos, BlocksRegister
					.BLOCK_BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.TOP));
			world.setBlockState(pos.north(), BlocksRegister
					.BLOCK_BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.SIDE_N));
			world.setBlockState(pos.south(), BlocksRegister
					.BLOCK_BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.SIDE_S));
			world.setBlockState(pos.east(), BlocksRegister
					.BLOCK_BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.SIDE_E));
			world.setBlockState(pos.west(), BlocksRegister
					.BLOCK_BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.SIDE_W));
			world.setBlockState(pos.north().east(), BlocksRegister
					.BLOCK_BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.CORNER_N));
			world.setBlockState(pos.north().west(), BlocksRegister
					.BLOCK_BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.CORNER_W));
			world.setBlockState(pos.south().east(), BlocksRegister
					.BLOCK_BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.CORNER_E));
			world.setBlockState(pos.south().west(), BlocksRegister
					.BLOCK_BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.with(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.CORNER_S));
		}
	}
}
