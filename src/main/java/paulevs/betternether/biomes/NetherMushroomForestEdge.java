package paulevs.betternether.biomes;

import java.util.Random;
import java.util.function.BiFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureGrayMold;
import paulevs.betternether.structures.plants.StructureOrangeMushroom;
import paulevs.betternether.structures.plants.StructureRedMold;
import paulevs.betternether.structures.plants.StructureVanillaMushroom;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.world.biomes.BCLBiome;

public class NetherMushroomForestEdge extends NetherBiome {
	public static class Config extends NetherBiomeConfig {
		public Config(String name) {
			super(name);
		}
		
		@Override
		protected void addCustomBuildData(BCLBiomeBuilder builder) {
			builder.fogColor(200, 121, 157)
				   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
				   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
				   .music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST);
		}
		
		@Override
		public BiFunction<ResourceLocation, Biome, NetherBiome> getSupplier() {
			return NetherMushroomForestEdge::new;
		}
	}
	
	public NetherMushroomForestEdge(ResourceLocation biomeID, Biome biome) {
		super(biomeID, biome);
		
		addStructure("orange_mushroom", new StructureOrangeMushroom(), StructureType.FLOOR, 0.05F, true);
		addStructure("vanilla_mushrooms", new StructureVanillaMushroom(), StructureType.FLOOR, 0.1F, false);
		addStructure("red_mold", new StructureRedMold(), StructureType.FLOOR, 0.5F, false);
		addStructure("gray_mold", new StructureGrayMold(), StructureType.FLOOR, 0.5F, false);
	}

	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
		if (random.nextInt(4) > 0)
			BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.NETHER_MYCELIUM.defaultBlockState());
		else if (random.nextBoolean())
			BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.NETHERRACK_MOSS.defaultBlockState());
	}
}