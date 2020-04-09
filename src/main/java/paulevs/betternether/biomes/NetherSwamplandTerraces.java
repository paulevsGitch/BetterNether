package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.BlocksRegistry;

public class NetherSwamplandTerraces extends NetherSwampland
{
	public NetherSwamplandTerraces(String name)
	{
		super(name);
	}
	
	@Override
	public void genSurfColumn(IWorld world, BlockPos pos, Random random)
	{
		if (validWall(world, pos.down()) && validWall(world, pos.north()) && validWall(world, pos.south()) && validWall(world, pos.east()) && validWall(world, pos.west()))
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.LAVA.getDefaultState());
		else if (random.nextInt(3) > 0)
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SOIL.getDefaultState());
		else if (random.nextBoolean())
			BlocksHelper.setWithoutUpdate(world, pos, BlocksRegistry.NETHERRACK_MOSS.getDefaultState());
		else
			super.genSurfColumn(world, pos, random);
		for (int i = 1; i < random.nextInt(3); i++)
		{
			BlockPos down = pos.down(i);
			if (random.nextInt(3) == 0 && BlocksHelper.isNetherGround(world.getBlockState(down)))
			{
				BlocksHelper.setWithoutUpdate(world, down, Blocks.SOUL_SAND.getDefaultState());
			}
		}
	}
}
