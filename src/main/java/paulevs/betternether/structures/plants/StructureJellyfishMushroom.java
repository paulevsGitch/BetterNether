package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockJellyfishMushroom;
import paulevs.betternether.blocks.BlockJellyfishMushroom.JellyShape;
import paulevs.betternether.blocks.shapes.TripleShape;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureJellyfishMushroom implements IStructure
{
	Mutable npos = new Mutable();
	
	@Override
	public void generate(WorldAccess world, BlockPos pos, Random random)
	{
		Block under;
		if (world.getBlockState(pos.down()).getBlock().isIn(BlockTags.NYLIUM))
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
						if (under.isIn(BlockTags.NYLIUM) && world.isAir(npos))
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

	public void grow(WorldAccess world, BlockPos pos, Random random)
	{
		if (random.nextBoolean() && world.isAir(pos.up()))
			growMedium(world, pos);
		else
			growSmall(world, pos);
	}
	
	public void growSmall(WorldAccess world, BlockPos pos)
	{
		Block down = world.getBlockState(pos.down()).getBlock();
		JellyShape visual = down == BlocksRegistry.MUSHROOM_GRASS ? JellyShape.NORMAL : down == BlocksRegistry.SEPIA_MUSHROOM_GRASS ? JellyShape.SEPIA : JellyShape.POOR;
		BlocksHelper.setWithoutUpdate(world, pos, BlocksRegistry.JELLYFISH_MUSHROOM.getDefaultState().with(BlockJellyfishMushroom.SHAPE, TripleShape.BOTTOM).with(BlockJellyfishMushroom.VISUAL, visual));
	}
	
	public void growMedium(WorldAccess world, BlockPos pos)
	{
		Block down = world.getBlockState(pos.down()).getBlock();
		JellyShape visual = down == BlocksRegistry.MUSHROOM_GRASS ? JellyShape.NORMAL : down == BlocksRegistry.SEPIA_MUSHROOM_GRASS ? JellyShape.SEPIA : JellyShape.POOR;
		BlocksHelper.setWithoutUpdate(world, pos, BlocksRegistry.JELLYFISH_MUSHROOM.getDefaultState().with(BlockJellyfishMushroom.SHAPE, TripleShape.MIDDLE).with(BlockJellyfishMushroom.VISUAL, visual));
		BlocksHelper.setWithoutUpdate(world, pos.up(), BlocksRegistry.JELLYFISH_MUSHROOM.getDefaultState().with(BlockJellyfishMushroom.SHAPE, TripleShape.TOP).with(BlockJellyfishMushroom.VISUAL, visual));
	}
}