package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.plants.StructureAgave;
import paulevs.betternether.structures.plants.StructureBarrelCactus;
import paulevs.betternether.structures.plants.StructureNetherCactus;

public class NetherBiomeGravelDesert extends NetherBiome
{
	public NetherBiomeGravelDesert(String name)
	{
		super(name);
		addStructure("nether_cactus", new StructureNetherCactus(), StructureType.FLOOR, 0.02F, true);
		addStructure("agave", new StructureAgave(), StructureType.FLOOR, 0.02F, true);
		addStructure("barrel_cactus", new StructureBarrelCactus(), StructureType.FLOOR, 0.02F, true);
	}

	@Override
	public void genSurfColumn(IWorld world, BlockPos pos, Random random)
	{
		for (int i = 0; i < 1 + random.nextInt(3); i++)
		{
			BlockPos p2 = pos.down(i);
			if (BlocksHelper.isNetherrack(world.getBlockState(p2)))
				if (world.isAir(p2.down()))
				{
					BlocksHelper.setWithoutUpdate(world, p2, Blocks.NETHERRACK.getDefaultState());
					return;
				}
				else
					BlocksHelper.setWithoutUpdate(world, p2, Blocks.GRAVEL.getDefaultState());
		}
	}
}
