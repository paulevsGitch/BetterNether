package paulevs.betternether.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import paulevs.betternether.blocks.BlocksRegister;

public class StructureWartTree implements IStructure
{
	@Override
	public void generate(Chunk chunk, BlockPos pos, Random random)
	{
		if ((pos.getX() > 4 && pos.getX() < 11) || (pos.getZ() > 4 && pos.getZ() < 11))
		{
			World world = chunk.getWorld();
			pos = pos.add(chunk.x << 4, 0, chunk.z << 4);

			int height = 5 + random.nextInt(5);

			boolean b1 = world.getBlockState(new BlockPos(pos.getX() - 4, pos.getY() + 5, pos.getZ())).getBlock() == Blocks.AIR;
			boolean b2 = world.getBlockState(new BlockPos(pos.getX() + 4, pos.getY() + 5, pos.getZ())).getBlock() == Blocks.AIR;
			boolean b3 = world.getBlockState(new BlockPos(pos.getX(), pos.getY() + 5, pos.getZ() - 4)).getBlock() == Blocks.AIR;
			boolean b4 = world.getBlockState(new BlockPos(pos.getX(), pos.getY() + 5, pos.getZ() + 4)).getBlock() == Blocks.AIR;
			boolean b5 = world.getBlockState(new BlockPos(pos.getX(), pos.getY() + height, pos.getZ())).getBlock() == Blocks.AIR;

			if (b1 && b2 && b3 && b4 && b5)
			{
				IBlockState redBricks = Blocks.RED_NETHER_BRICK.getDefaultState();
				IBlockState wart = Blocks.NETHER_WART_BLOCK.getDefaultState();

				int h2 = height >>> 1;
				int h3 = height >>> 2;
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
						int rh2 = random.nextInt(h3);
						for (int y = 0; y < height; y++)
						{
							int py = y + pos.getY();
							BlockPos pos2 = new BlockPos(px, py, pz);
							if (y < rh &&random.nextInt(2) == 0)
								SetBlockState(world, pos2, redBricks);
							else
							{
								SetBlockState(world, pos2, wart);
								EnumFacing dir = EnumFacing.HORIZONTALS[random.nextInt(4)];
								seedBlocks.add(pos2.offset(dir));
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
								BlockPos pos2 = new BlockPos(px, py, pz);
								if (world.getBlockState(pos2) != wart)
									SetBlockState(world, pos2, wart);
							}
						}
					}
				}
				for (BlockPos pos2 : seedBlocks)
					PlaceRandomSeed(world, pos2);
			}
		}
	}

	private void PlaceRandomSeed(World world, BlockPos pos)
	{
		if (world.getBlockState(pos).getBlock() == Blocks.AIR)
		{
			IBlockState seed = BlocksRegister.BLOCK_WART_SEED.getDefaultState().withProperty(BlockDirectional.FACING, EnumFacing.UP);
			if (world.getBlockState(pos.north()).isFullBlock())
				seed = seed.withProperty(BlockDirectional.FACING, EnumFacing.SOUTH);
			else if (world.getBlockState(pos.south()).isFullBlock())
				seed = seed.withProperty(BlockDirectional.FACING, EnumFacing.NORTH);
			else if (world.getBlockState(pos.east()).isFullBlock())
				seed = seed.withProperty(BlockDirectional.FACING, EnumFacing.WEST);
			else if (world.getBlockState(pos.west()).isFullBlock())
				seed = seed.withProperty(BlockDirectional.FACING, EnumFacing.EAST);
			else if (world.getBlockState(pos.up()).isFullBlock())
				seed = seed.withProperty(BlockDirectional.FACING, EnumFacing.DOWN);
			SetBlockState(world, pos, seed);
		}
	}

	private void SetBlockState(World world, BlockPos pos, IBlockState state)
	{
		setBlockAndNotifyAdequately(world, pos, state);
	}
}
