package paulevs.betternether.world.biomes;

import java.util.Random;
import java.util.function.BiFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.noise.OpenSimplexNoise;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.registry.NetherEntities;
import paulevs.betternether.world.structures.StructureType;
import paulevs.betternether.world.structures.plants.StructureCrimsonFungus;
import paulevs.betternether.world.structures.plants.StructureCrimsonGlowingTree;
import paulevs.betternether.world.structures.plants.StructureCrimsonRoots;
import paulevs.betternether.world.structures.plants.StructureGoldenVine;
import paulevs.betternether.world.structures.plants.StructureWallMoss;
import paulevs.betternether.world.structures.plants.StructureWallRedMushroom;
import paulevs.betternether.world.structures.plants.StructureWartBush;
import paulevs.betternether.world.structures.plants.StructureWartSeed;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.api.surface.rules.SurfaceNoiseCondition;
import ru.bclib.mixin.common.SurfaceRulesContextAccessor;
import ru.bclib.world.surface.DoubleBlockSurfaceNoiseCondition;

class NoiseCondition extends SurfaceNoiseCondition {
	private static final OpenSimplexNoise TERRAIN = new OpenSimplexNoise(614);
	
	@Override
	public boolean test(SurfaceRulesContextAccessor context) {
		final int x = context.getBlockX();
		final int z = context.getBlockZ();
		
		return TERRAIN.eval(x * 0.1, z * 0.1) > MHelper.randRange(0.5F, 0.7F, MHelper.RANDOM);
	}
}

public class CrimsonGlowingWoods extends NetherBiome {
	private static final SurfaceRules.RuleSource NETHER_WART_BLOCK = SurfaceRules.state(Blocks.NETHER_WART_BLOCK.defaultBlockState());
	private static final SurfaceRules.RuleSource CRIMSON_NYLIUM = SurfaceRules.state(Blocks.CRIMSON_NYLIUM.defaultBlockState());
	
	
	public static class Config extends NetherBiomeConfig {
		public Config(String name) {
			super(name);
		}
		
		@Override
		protected void addCustomBuildData(BCLBiomeBuilder builder) {
			builder.fogColor(51, 3, 3)
				   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
				   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
				   .music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
				   .particles(ParticleTypes.CRIMSON_SPORE, 0.025F)
				   .spawn(EntityType.HOGLIN, 9, 1, 2)
				   .spawn(NetherEntities.FLYING_PIG, 20, 2, 4)
				   .structure(NetherBiomeBuilder.VANILLA_STRUCTURES.getBASTION_REMNANT())
					.surface(
						SurfaceRules.sequence(
							SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
								SurfaceRules.sequence(
									SurfaceRules.ifTrue(new NoiseCondition(), NETHER_WART_BLOCK),
									CRIMSON_NYLIUM
								)
							),
							NETHERRACK
						)
					)
			;
		}
		
		@Override
		public BiFunction<ResourceLocation, Biome, NetherBiome> getSupplier() {
			return CrimsonGlowingWoods::new;
		}
	}
	
	//private static final OpenSimplexNoise TERRAIN = new OpenSimplexNoise(614);
	
	public CrimsonGlowingWoods(ResourceLocation biomeID, Biome biome) {
		super(biomeID, biome);
				
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
//		if (TERRAIN.eval(pos.getX() * 0.1, pos.getZ() * 0.1) > MHelper.randRange(0.5F, 0.7F, random))
//			BlocksHelper.setWithoutUpdate(world, pos, Blocks.NETHER_WART_BLOCK.defaultBlockState());
//		else
//			BlocksHelper.setWithoutUpdate(world, pos, Blocks.CRIMSON_NYLIUM.defaultBlockState());
	}
}
