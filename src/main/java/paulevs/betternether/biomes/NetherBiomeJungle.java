package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.config.Config;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.structures.plants.StructureEggPlant;
import paulevs.betternether.structures.plants.StructureEye;
import paulevs.betternether.structures.plants.StructureLucis;
import paulevs.betternether.structures.plants.StructureMagmaFlower;
import paulevs.betternether.structures.plants.StructureNetherGrass;
import paulevs.betternether.structures.plants.StructureReeds;
import paulevs.betternether.structures.plants.StructureStalagnate;

public class NetherBiomeJungle extends NetherBiome
{
	public NetherBiomeJungle(String name)
	{
		super(name);
		this.addStructure(
				new StructureReeds(),
				StructureType.FLOOR,
				Config.getFloat("generator_jungle", "nether_reed_density", 0.2F),
				Config.getBoolean("generator_jungle", "nether_reed_limit", false));
		this.addStructure(
				new StructureStalagnate(),
				StructureType.FLOOR,
				Config.getFloat("generator_jungle", "stalagnate_density", 0.2F),
				Config.getBoolean("generator_jungle", "stalagnate_limit", false));
		this.addStructure(
				new StructureMagmaFlower(),
				StructureType.FLOOR,
				Config.getFloat("generator_jungle", "magma_flower_density", 0.5F),
				Config.getBoolean("generator_jungle", "magma_flower_limit", false));
		this.addStructure(
				new StructureEggPlant(),
				StructureType.FLOOR,
				Config.getFloat("generator_jungle", "egg_plant_density", 0.05F),
				Config.getBoolean("generator_jungle", "egg_plant_limit", false));
		this.addStructure(
				new StructureNetherGrass(),
				StructureType.FLOOR,
				Config.getFloat("generator_jungle", "nether_grass_density", 0.1F),
				Config.getBoolean("generator_jungle", "nether_grass_limit", false));
		
		this.addStructure(
				new StructureLucis(),
				StructureType.WALL,
				Config.getFloat("generator_jungle", "lucis_density", 0.2F),
				Config.getBoolean("generator_jungle", "lucis_limit", false));
		
		this.addStructure(
				new StructureEye(),
				StructureType.CEIL,
				Config.getFloat("generator_jungle", "eye_density", 0.1F),
				Config.getBoolean("generator_jungle", "eye_limit", true));
	}

	@Override
	public void genSurfColumn(IWorld world, BlockPos pos, Random random)
	{
		switch(random.nextInt(3))
		{
		case 0:
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SAND.getDefaultState());
			break;
		case 1:
			BlocksHelper.setWithoutUpdate(world, pos, BlocksRegister.BLOCK_NETHERRACK_MOSS.getDefaultState());
			break;
		}
	}
}
