package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.registry.SoundsRegistry;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureBlackVine;
import paulevs.betternether.structures.plants.StructureBloomingVine;
import paulevs.betternether.structures.plants.StructureEggPlant;
import paulevs.betternether.structures.plants.StructureEye;
import paulevs.betternether.structures.plants.StructureFeatherFern;
import paulevs.betternether.structures.plants.StructureGoldenVine;
import paulevs.betternether.structures.plants.StructureJungleMoss;
import paulevs.betternether.structures.plants.StructureJunglePlant;
import paulevs.betternether.structures.plants.StructureLucis;
import paulevs.betternether.structures.plants.StructureMagmaFlower;
import paulevs.betternether.structures.plants.StructureReeds;
import paulevs.betternether.structures.plants.StructureRubeus;
import paulevs.betternether.structures.plants.StructureRubeusBush;
import paulevs.betternether.structures.plants.StructureStalagnate;
import paulevs.betternether.structures.plants.StructureWallBrownMushroom;
import paulevs.betternether.structures.plants.StructureWallMoss;
import paulevs.betternether.structures.plants.StructureWallRedMushroom;

public class NetherBiomeJungle extends NetherBiome
{
	public NetherBiomeJungle(String name)
	{
		super(new BiomeDefenition(name)
				.setFogColor(62, 169, 61)
				.setLoop(SoundsRegistry.AMBIENT_NETHER_JUNGLE)
				.setAdditions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				.setMood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD));
		addStructure("nether_reed", new StructureReeds(), StructureType.FLOOR, 0.5F, false);
		addStructure("stalagnate", new StructureStalagnate(), StructureType.FLOOR, 0.2F, false);
		addStructure("rubeus_tree", new StructureRubeus(), StructureType.FLOOR, 0.1F, false);
		addStructure("bush_rubeus", new StructureRubeusBush(), StructureType.FLOOR, 0.1F, false);
		addStructure("magma_flower", new StructureMagmaFlower(), StructureType.FLOOR, 0.5F, false);
		addStructure("egg_plant", new StructureEggPlant(), StructureType.FLOOR, 0.05F, true);
		addStructure("feather_fern", new StructureFeatherFern(), StructureType.FLOOR, 0.05F, true);
		addStructure("jungle_plant", new StructureJunglePlant(), StructureType.FLOOR, 0.1F, false);
		addStructure("lucis", new StructureLucis(), StructureType.WALL, 0.1F, false);
		addStructure("eye", new StructureEye(), StructureType.CEIL, 0.1F, true);
		addStructure("black_vine", new StructureBlackVine(), StructureType.CEIL, 0.1F, true);
		addStructure("golden_vine", new StructureGoldenVine(), StructureType.CEIL, 0.1F, true);
		addStructure("flowered_vine", new StructureBloomingVine(), StructureType.CEIL, 0.1F, true);
		addStructure("jungle_moss", new StructureJungleMoss(), StructureType.WALL, 0.8F, true);
		addStructure("wall_moss", new StructureWallMoss(), StructureType.WALL, 0.2F, true);
		addStructure("wall_red_mushroom", new StructureWallRedMushroom(), StructureType.WALL, 0.8F, true);
		addStructure("wall_brown_mushroom", new StructureWallBrownMushroom(), StructureType.WALL, 0.8F, true);
		
		addWorldStructures(structureFormat("ruined_temple", -4, StructureType.FLOOR, 10F));
		addWorldStructures(structureFormat("jungle_temple_altar", -2, StructureType.FLOOR, 10F));
		addWorldStructures(structureFormat("jungle_temple_2", -2, StructureType.FLOOR, 10F));
		
		addWorldStructures(structureFormat("jungle_bones_1", 0, StructureType.FLOOR, 20F));
		addWorldStructures(structureFormat("jungle_bones_2", 0, StructureType.FLOOR, 20F));
		addWorldStructures(structureFormat("jungle_bones_3", 0, StructureType.FLOOR, 20F));
		
		this.setNoiseDensity(0.5F);
	}

	@Override
	public void genSurfColumn(WorldAccess world, BlockPos pos, Random random)
	{
		BlocksHelper.setWithoutUpdate(world, pos, BlocksRegistry.JUNGLE_GRASS.getDefaultState());
	}
}