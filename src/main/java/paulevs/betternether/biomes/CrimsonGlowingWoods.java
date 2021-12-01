package paulevs.betternether.biomes;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.noise.OpenSimplexNoise;
import paulevs.betternether.registry.EntityRegistry;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureCrimsonFungus;
import paulevs.betternether.structures.plants.StructureCrimsonGlowingTree;
import paulevs.betternether.structures.plants.StructureCrimsonRoots;
import paulevs.betternether.structures.plants.StructureGoldenVine;
import paulevs.betternether.structures.plants.StructureWallMoss;
import paulevs.betternether.structures.plants.StructureWallRedMushroom;
import paulevs.betternether.structures.plants.StructureWartBush;
import paulevs.betternether.structures.plants.StructureWartSeed;

public class CrimsonGlowingWoods extends NetherBiomeData {
	private static final OpenSimplexNoise TERRAIN = new OpenSimplexNoise(614);

	public CrimsonGlowingWoods(String name) {
		super(new BiomeDefinition(name)
				.setFogColor(51, 3, 3)
				.setLoop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
				.setAdditions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				.setMood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
				.setParticles(ParticleTypes.CRIMSON_SPORE, 0.025F)
				.setGenChance(0.3F)
				.addMobSpawn(EntityType.HOGLIN, 9, 1, 2)
				.addMobSpawn(EntityRegistry.FLYING_PIG, 20, 2, 4));
				
		addStructure("crimson_glowing_tree", new StructureCrimsonGlowingTree(), StructureType.FLOOR, 0.2F, false);
		addStructure("wart_bush", new StructureWartBush(), StructureType.FLOOR, 0.05F, false);
		addStructure("wart_seed", new StructureWartSeed(), StructureType.FLOOR, 0.02F, true);
		addStructure("crimson_fungus", new StructureCrimsonFungus(), StructureType.FLOOR, 0.05F, true);
		addStructure("crimson_roots", new StructureCrimsonRoots(), StructureType.FLOOR, 0.2F, true);
		addStructure("golden_vine", new StructureGoldenVine(), StructureType.CEIL, 0.3F, true);
		addStructure("wall_moss", new StructureWallMoss(), StructureType.WALL, 0.8F, true);
		addStructure("wall_red_mushroom", new StructureWallRedMushroom(), StructureType.WALL, 0.4F, true);
	}

	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
		if (TERRAIN.eval(pos.getX() * 0.1, pos.getZ() * 0.1) > MHelper.randRange(0.5F, 0.7F, random))
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.NETHER_WART_BLOCK.defaultBlockState());
		else
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.CRIMSON_NYLIUM.defaultBlockState());
	}
}
