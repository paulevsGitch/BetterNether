package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.BiomeParticleConfig;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureCrimsonFungus;
import paulevs.betternether.structures.plants.StructureCrimsonGlowingTree;
import paulevs.betternether.structures.plants.StructureCrimsonRoots;
import paulevs.betternether.structures.plants.StructureGoldenVine;
import paulevs.betternether.structures.plants.StructureWallMoss;
import paulevs.betternether.structures.plants.StructureWallRedMushroom;

public class CrimsonGlowingWoods extends NetherBiome
{
	public CrimsonGlowingWoods(String name)
	{
		super(new BiomeDefenition(name)
				.setFogColor(51, 3, 3)
				.setLoop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
				.setAdditions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				.setMood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
				.setParticleConfig(new BiomeParticleConfig(ParticleTypes.CRIMSON_SPORE, 0.025F)));
		addStructure("crimson_glowing_tree", new StructureCrimsonGlowingTree(), StructureType.FLOOR, 0.2F, false);
		addStructure("crimson_fungus", new StructureCrimsonFungus(), StructureType.FLOOR, 0.05F, true);
		addStructure("crimson_roots", new StructureCrimsonRoots(), StructureType.FLOOR, 0.2F, true);
		addStructure("golden_vine", new StructureGoldenVine(), StructureType.CEIL, 0.3F, true);
		addStructure("wall_moss", new StructureWallMoss(), StructureType.WALL, 0.8F, true);
		addStructure("wall_red_mushroom", new StructureWallRedMushroom(), StructureType.WALL, 0.4F, true);
	}
	
	@Override
	public void genSurfColumn(WorldAccess world, BlockPos pos, Random random)
	{
		BlocksHelper.setWithoutUpdate(world, pos, Blocks.CRIMSON_NYLIUM.getDefaultState());
	}
}
