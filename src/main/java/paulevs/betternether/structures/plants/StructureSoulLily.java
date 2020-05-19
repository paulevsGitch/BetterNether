package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockSoulLily;
import paulevs.betternether.blocks.BlockSoulLily.SoulLilyShape;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureSoulLily implements IStructure
{
	Mutable npos = new Mutable();
	
	@Override
	public void generate(WorldAccess world, BlockPos pos, Random random)
	{
		Block under;
		if (world.getBlockState(pos.down()).getBlock() == Blocks.SOUL_SAND)
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
						if (under == Blocks.SOUL_SAND && world.isAir(npos))
						{
							growTree(world, npos, random);
						}
					}
					else
						break;
				}
			}
		}
	}
	
	private void growTree(WorldAccess world, BlockPos pos, Random random)
	{
		if (world.getBlockState(pos.down()).getBlock() == Blocks.SOUL_SAND)
		{
			if (world.isAir(pos.up()))
			{
				if (world.isAir(pos.up(2)) && isAirSides(world, pos.up(2)))
				{
					growBig(world, pos);
				}
				else
					growMedium(world, pos);
			}
			else
				growSmall(world, pos);
		}
	}
	
	public void growSmall(WorldAccess world, BlockPos pos)
	{
		BlocksHelper.setWithoutUpdate(world, pos, BlocksRegistry.SOUL_LILY.getDefaultState());
	}
	
	public void growMedium(WorldAccess world, BlockPos pos)
	{
		BlocksHelper.setWithoutUpdate(world, pos,
				BlocksRegistry.SOUL_LILY
				.getDefaultState()
				.with(BlockSoulLily.SHAPE, SoulLilyShape.MEDIUM_BOTTOM));
		BlocksHelper.setWithoutUpdate(world, pos.up(),
				BlocksRegistry.SOUL_LILY
				.getDefaultState()
				.with(BlockSoulLily.SHAPE, SoulLilyShape.MEDIUM_TOP));
	}
	
	public void growBig(WorldAccess world, BlockPos pos)
	{
		BlocksHelper.setWithoutUpdate(world, pos, BlocksRegistry.SOUL_LILY
				.getDefaultState()
				.with(BlockSoulLily.SHAPE, SoulLilyShape.BIG_BOTTOM));
		BlocksHelper.setWithoutUpdate(world, pos.up(),
				BlocksRegistry.SOUL_LILY
				.getDefaultState()
				.with(BlockSoulLily.SHAPE, SoulLilyShape.BIG_MIDDLE));
		BlockPos up = pos.up(2);
		BlocksHelper.setWithoutUpdate(world, up,
				BlocksRegistry.SOUL_LILY
				.getDefaultState()
				.with(BlockSoulLily.SHAPE, SoulLilyShape.BIG_TOP_CENTER));
		BlocksHelper.setWithoutUpdate(world, up.north(), BlocksRegistry.SOUL_LILY
				.getDefaultState()
				.with(BlockSoulLily.SHAPE, SoulLilyShape.BIG_TOP_SIDE_S));
		BlocksHelper.setWithoutUpdate(world, up.south(),
				BlocksRegistry.SOUL_LILY
				.getDefaultState()
				.with(BlockSoulLily.SHAPE, SoulLilyShape.BIG_TOP_SIDE_N));
		BlocksHelper.setWithoutUpdate(world, up.east(),
				BlocksRegistry.SOUL_LILY
				.getDefaultState()
				.with(BlockSoulLily.SHAPE, SoulLilyShape.BIG_TOP_SIDE_W));
		BlocksHelper.setWithoutUpdate(world, up.west(),
				BlocksRegistry.SOUL_LILY
				.getDefaultState()
				.with(BlockSoulLily.SHAPE, SoulLilyShape.BIG_TOP_SIDE_E));
	}
	
	private boolean isAirSides(WorldAccess world, BlockPos pos)
	{
		return world.isAir(pos.north()) && world.isAir(pos.south()) && world.isAir(pos.east()) && world.isAir(pos.west());
	}
}