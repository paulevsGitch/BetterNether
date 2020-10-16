package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.BiomeParticleConfig;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.noise.OpenSimplexNoise;
import paulevs.betternether.registry.EntityRegistry;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureCrimsonFungus;
import paulevs.betternether.structures.plants.StructureCrimsonPinewood;
import paulevs.betternether.structures.plants.StructureCrimsonRoots;
import paulevs.betternether.structures.plants.StructureGoldenVine;
import paulevs.betternether.structures.plants.StructureWallMoss;
import paulevs.betternether.structures.plants.StructureWallRedMushroom;
import paulevs.betternether.structures.plants.StructureWartBush;
import paulevs.betternether.structures.plants.StructureWartSeed;

public class CrimsonPinewood extends NetherBiome {
	private static final OpenSimplexNoise TERRAIN = new OpenSimplexNoise(614);

	public CrimsonPinewood(String name) {
		super(new BiomeDefinition(name)
				.setFogColor(51, 3, 3)
				.setLoop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
				.setAdditions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				.setMood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
				.setParticleConfig(new BiomeParticleConfig(ParticleTypes.CRIMSON_SPORE, 0.025F))
				.addMobSpawn(EntityRegistry.FLYING_PIG, 20, 2, 4));
		addStructure("crimson_pinewood", new StructureCrimsonPinewood(), StructureType.FLOOR, 0.2F, false);
		addStructure("wart_bush", new StructureWartBush(), StructureType.FLOOR, 0.1F, false);
		addStructure("wart_seed", new StructureWartSeed(), StructureType.FLOOR, 0.05F, true);
		addStructure("crimson_fungus", new StructureCrimsonFungus(), StructureType.FLOOR, 0.05F, true);
		addStructure("crimson_roots", new StructureCrimsonRoots(), StructureType.FLOOR, 0.2F, true);
		addStructure("golden_vine", new StructureGoldenVine(), StructureType.CEIL, 0.3F, true);
		addStructure("wall_moss", new StructureWallMoss(), StructureType.WALL, 0.8F, true);
		addStructure("wall_red_mushroom", new StructureWallRedMushroom(), StructureType.WALL, 0.4F, true);
	}

	@Override
	public void genSurfColumn(WorldAccess world, BlockPos pos, Random random) {
		if (TERRAIN.eval(pos.getX() * 0.1, pos.getZ() * 0.1) > MHelper.randRange(0.5F, 0.7F, random))
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.NETHER_WART_BLOCK.getDefaultState());
		else
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.CRIMSON_NYLIUM.getDefaultState());
	}
}
