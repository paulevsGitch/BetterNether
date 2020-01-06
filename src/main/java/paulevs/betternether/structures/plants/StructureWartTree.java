package paulevs.betternether.structures.plants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockWartSeed;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.structures.IStructure;

public class StructureWartTree implements IStructure
{
	private static final BlockState WART_BLOCK = Blocks.NETHER_WART_BLOCK.getDefaultState();
	private static final Direction[] HORIZONTAL = HorizontalFacingBlock.FACING.getValues().toArray(new Direction[] {});
	private Mutable blockPos = new Mutable();
	
	@Override
	public void generate(ServerWorld world, BlockPos pos, Random random)
	{
		int height = 5 + random.nextInt(5);
		
		if (world.isAir(pos.up(1)) && world.isAir(pos.up(2)))
		{
			int h2 = height >>> 1;
			int width = (height >>> 2) + 1;
			int offset = width >>> 1;
			List<BlockPos> seedBlocks = new ArrayList<BlockPos>();
			for (int x = 0; x < width; x++)
			{
				int px = x + pos.getX() - offset;
				for (int z = 0; z < width; z++)
				{
					int pz = z + pos.getZ() - offset;
					int rh = random.nextInt(h2);
					for (int y = 0; y < height; y++)
					{
						int py = y + pos.getY();
						blockPos.set(px, py, pz);
						if (isReplaceable(world.getBlockState(blockPos)))
						{
							world.setBlockState(blockPos, WART_BLOCK);
							if (random.nextInt(3) == 0)
							{
								world.setBlockState(blockPos, WART_BLOCK);
								Direction dir = HORIZONTAL[random.nextInt(HORIZONTAL.length)];
								seedBlocks.add(new BlockPos(blockPos).offset(dir));
							}
						}
					}
				}
			}
			int headWidth = width + 2;
			offset ++;
			height = height - width - 1 + pos.getY();
			for (int x = 0; x < headWidth; x++)
			{
				int px = x + pos.getX() - offset;
				for (int z = 0; z < headWidth; z++)
				{
					if (x != z && x != (headWidth - z - 1))
					{
						int pz = z + pos.getZ() - offset;
						for (int y = 0; y < width; y++)
						{
							int py = y + height;
							blockPos.set(px, py, pz);
							if (world.isAir(blockPos))
							{
								world.setBlockState(blockPos, WART_BLOCK);
								if (random.nextInt(3) == 0)
								{
									world.setBlockState(blockPos, WART_BLOCK);
									Direction dir = HORIZONTAL[random.nextInt(HORIZONTAL.length)];
									seedBlocks.add(new BlockPos(blockPos).offset(dir));
								}
							}
						}
					}
				}
			}
			for (BlockPos pos2 : seedBlocks)
				PlaceRandomSeed(world, pos2);
		}
	}

	private void PlaceRandomSeed(ServerWorld world, BlockPos pos)
	{
		BlockState seed = BlocksRegister.BLOCK_WART_SEED.getDefaultState();
		if (isReplaceable(world.getBlockState(pos)))
		{
			if (world.getBlockState(pos.north()) == WART_BLOCK)
				seed = seed.with(BlockWartSeed.FACING, Direction.SOUTH);
			else if (world.getBlockState(pos.south()) == WART_BLOCK)
				seed = seed.with(BlockWartSeed.FACING, Direction.NORTH);
			else if (world.getBlockState(pos.east()) == WART_BLOCK)
				seed = seed.with(BlockWartSeed.FACING, Direction.WEST);
			else if (world.getBlockState(pos.west()) == WART_BLOCK)
				seed = seed.with(BlockWartSeed.FACING, Direction.EAST);
			else if (world.getBlockState(pos.up()) == WART_BLOCK)
				seed = seed.with(BlockWartSeed.FACING, Direction.DOWN);
			BlocksHelper.setWithoutUpdate(world, pos, seed);
		}
	}
	
	private boolean isReplaceable(BlockState state)
	{
		Block block = state.getBlock();
		return block == Blocks.AIR || block == BlocksRegister.BLOCK_WART_SEED;
	}
}