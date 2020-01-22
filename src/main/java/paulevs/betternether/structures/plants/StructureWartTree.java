package paulevs.betternether.structures.plants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
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
	public void generate(IWorld world, BlockPos pos, Random random)
	{
		if (world.isAir(pos.up(1)) && world.isAir(pos.up(2)))
		{
			if (world.isAir(pos.up(2).north()) && world.isAir(pos.up(2).south())  && world.isAir(pos.up(2).east())  && world.isAir(pos.up(2).west()))
			{
				int height = 5 + random.nextInt(5);
				int h2 = height - 1;
				int width = (height >>> 2) + 1;
				int offset = width >>> 1;
				List<BlockPos> seedBlocks = new ArrayList<BlockPos>();
				for (int x = 0; x < width; x++)
				{
					int px = x + pos.getX() - offset;
					for (int z = 0; z < width; z++)
					{
						int pz = z + pos.getZ() - offset;
						for (int y = 0; y < height; y++)
						{
							int py = y + pos.getY();
							blockPos.set(px, py, pz);
							if (isReplaceable(world.getBlockState(blockPos)))
							{
								if (y == 0 && !isReplaceable(world.getBlockState(blockPos.down())))
									BlocksHelper.setWithoutUpdate(world, blockPos, BlocksRegister.WART_ROOTS.getDefaultState());
								else if (y < h2)
									BlocksHelper.setWithoutUpdate(world, blockPos, BlocksRegister.WART_LOG.getDefaultState());
								else
									BlocksHelper.setWithoutUpdate(world, blockPos, WART_BLOCK);
								if (random.nextInt(8) == 0)
								{
									Direction dir = HORIZONTAL[random.nextInt(HORIZONTAL.length)];
									seedBlocks.add(new BlockPos(blockPos).offset(dir));
								}
							}
						}
					}
				}
				
				for (int x = 0; x < width; x++)
				{
					int px = x + pos.getX() - offset;
					for (int z = 0; z < width; z++)
					{
						int pz = z + pos.getZ() - offset;
						for (int y = 1; y < height >> 1; y++)
						{
							int py = pos.getY() - y;
							blockPos.set(px, py, pz);
							if (isReplaceable(world.getBlockState(blockPos)))
							{
								if (isReplaceable(world.getBlockState(blockPos.down())))
									BlocksHelper.setWithoutUpdate(world, blockPos, BlocksRegister.WART_LOG.getDefaultState());
								else
								{
									BlocksHelper.setWithoutUpdate(world, blockPos, BlocksRegister.WART_ROOTS.getDefaultState());
									break;
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
									BlocksHelper.setWithoutUpdate(world, blockPos, WART_BLOCK);
									for (int i = 0; i < 4; i++)
										seedBlocks.add(new BlockPos(blockPos).offset(Direction.values()[random.nextInt(6)]));
								}
							}
						}
					}
				}
				for (BlockPos pos2 : seedBlocks)
					PlaceRandomSeed(world, pos2);
			}
		}
	}

	private void PlaceRandomSeed(IWorld world, BlockPos pos)
	{
		BlockState seed = BlocksRegister.BLOCK_WART_SEED.getDefaultState();
		if (isReplaceable(world.getBlockState(pos)))
		{
			if (isWart(world.getBlockState(pos.up())))
				seed = seed.with(BlockWartSeed.FACING, Direction.DOWN);
			else if (isWart(world.getBlockState(pos.down())))
				seed = seed.with(BlockWartSeed.FACING, Direction.UP);
			else if (isWart(world.getBlockState(pos.north())))
				seed = seed.with(BlockWartSeed.FACING, Direction.SOUTH);
			else if (isWart(world.getBlockState(pos.south())))
				seed = seed.with(BlockWartSeed.FACING, Direction.NORTH);
			else if (isWart(world.getBlockState(pos.east())))
				seed = seed.with(BlockWartSeed.FACING, Direction.WEST);
			else if (isWart(world.getBlockState(pos.west())))
				seed = seed.with(BlockWartSeed.FACING, Direction.EAST);
			BlocksHelper.setWithoutUpdate(world, pos, seed);
		}
	}

	private boolean isReplaceable(BlockState state)
	{
		Block block = state.getBlock();
		return state.getMaterial().isReplaceable() ||
				block == Blocks.AIR ||
				block == BlocksRegister.BLOCK_WART_SEED ||
				block == BlocksRegister.BLOCK_BLACK_BUSH ||
				block == BlocksRegister.BLOCK_SOUL_VEIN ||
				block == BlocksRegister.BLOCK_SOUL_LILY ||
				block == BlocksRegister.BLOCK_SOUL_LILY_SAPLING ||
				block == Blocks.NETHER_WART;
	}
	
	private boolean isWart(BlockState state)
	{
		return state == WART_BLOCK || state.getBlock() == BlocksRegister.WART_LOG;
	}
}