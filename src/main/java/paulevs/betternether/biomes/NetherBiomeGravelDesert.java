package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.config.Config;
import paulevs.betternether.structures.plants.StructureAgave;
import paulevs.betternether.structures.plants.StructureBarrelCactus;
import paulevs.betternether.structures.plants.StructureNetherCactus;

public class NetherBiomeGravelDesert extends NetherBiome
{
	public NetherBiomeGravelDesert(String name)
	{
		super(name);
		this.addStructure(
				new StructureNetherCactus(),
				StructureType.FLOOR,
				Config.getFloat("generator_desert", "nether_cactus_density", 0.02F),
				Config.getBoolean("generator_desert", "nether_cactus_limit", true));
		this.addStructure(
				new StructureAgave(),
				StructureType.FLOOR,
				Config.getFloat("generator_desert", "agave_density", 0.02F),
				Config.getBoolean("generator_desert", "agave_limit", true));
		this.addStructure(
				new StructureBarrelCactus(),
				StructureType.FLOOR,
				Config.getFloat("generator_desert", "barrel_cactus_density", 0.02F),
				Config.getBoolean("generator_desert", "barrel_cactus_limit", true));
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
