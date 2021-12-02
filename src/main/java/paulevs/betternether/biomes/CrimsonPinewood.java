package paulevs.betternether.biomes;

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
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.noise.OpenSimplexNoise;
import paulevs.betternether.registry.NetherEntities;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureCrimsonFungus;
import paulevs.betternether.structures.plants.StructureCrimsonPinewood;
import paulevs.betternether.structures.plants.StructureCrimsonRoots;
import paulevs.betternether.structures.plants.StructureGoldenVine;
import paulevs.betternether.structures.plants.StructureWallMoss;
import paulevs.betternether.structures.plants.StructureWallRedMushroom;
import paulevs.betternether.structures.plants.StructureWartBush;
import paulevs.betternether.structures.plants.StructureWartSeed;
import ru.bclib.api.biomes.BCLBiomeBuilder;

public class CrimsonPinewood extends NetherBiome {
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
				   .particles(ParticleTypes.CRIMSON_SPORE, 0.025F)
				   .spawn(EntityType.HOGLIN, 9, 2, 5)
				   .spawn(NetherEntities.FLYING_PIG, 20, 2, 4);
		}
		
		@Override
		public BiFunction<ResourceLocation, Biome, NetherBiome> getSupplier() {
			return CrimsonPinewood::new;
		}
	}
	
	private static final OpenSimplexNoise TERRAIN = new OpenSimplexNoise(614);
	
	public CrimsonPinewood(ResourceLocation biomeID, Biome biome) {
		super(biomeID, biome);
		
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
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
		if (TERRAIN.eval(pos.getX() * 0.1, pos.getZ() * 0.1) > MHelper.randRange(0.5F, 0.7F, random))
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.NETHER_WART_BLOCK.defaultBlockState());
		else
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.CRIMSON_NYLIUM.defaultBlockState());
	}
}
