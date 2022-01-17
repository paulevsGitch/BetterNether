package paulevs.betternether.world.biomes;

import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.SurfaceRules;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.registry.NetherFeatures;
import paulevs.betternether.world.NetherBiome;
import paulevs.betternether.world.NetherBiomeConfig;
import paulevs.betternether.world.structures.StructureType;
import paulevs.betternether.world.structures.plants.StructureGrayMold;
import paulevs.betternether.world.structures.plants.StructureOrangeMushroom;
import paulevs.betternether.world.structures.plants.StructureRedMold;
import paulevs.betternether.world.structures.plants.StructureVanillaMushroom;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import ru.bclib.api.surface.SurfaceRuleBuilder;
import ru.bclib.api.surface.rules.RandomIntProvider;
import ru.bclib.api.surface.rules.SwitchRuleSource;
import ru.bclib.world.biomes.BCLBiomeSettings;

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
				   .music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
				   .feature(NetherFeatures.NETHER_RUBY_ORE);
		}
		
		@Override
		public BiomeSupplier<NetherBiome> getSupplier() {
			return NetherMushroomForestEdge::new;
		}
		
		@Override
		public SurfaceRuleBuilder surface() {
			return super.surface()
						.rule(
							SurfaceRules.ifTrue(
								SurfaceRules.ON_FLOOR,
								new SwitchRuleSource(
									ctx -> MHelper.RANDOM.nextInt(4) > 0 ? 0 : (MHelper.RANDOM.nextBoolean() ? 1 : 2),
									List.of(
										SurfaceRules.state(NetherBlocks.NETHER_MYCELIUM.defaultBlockState()),
										SurfaceRules.state(NetherBlocks.NETHERRACK_MOSS.defaultBlockState()),
										NETHERRACK
									)
								)
							)
						);
		}
	}
	
	public NetherMushroomForestEdge(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
		super(biomeID, biome, settings);
	}
	
	@Override
	protected void onInit(){
		addStructure("orange_mushroom", new StructureOrangeMushroom(), StructureType.FLOOR, 0.05F, true);
		addStructure("vanilla_mushrooms", new StructureVanillaMushroom(), StructureType.FLOOR, 0.1F, false);
		addStructure("red_mold", new StructureRedMold(), StructureType.FLOOR, 0.5F, false);
		addStructure("gray_mold", new StructureGrayMold(), StructureType.FLOOR, 0.5F, false);
	}
	
	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
//		if (random.nextInt(4) > 0)
//			BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.NETHER_MYCELIUM.defaultBlockState());
//		else if (random.nextBoolean())
//			BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.NETHERRACK_MOSS.defaultBlockState());
	}
}