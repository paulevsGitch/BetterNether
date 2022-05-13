package paulevs.betternether.world;

import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.biome.NetherBiomes;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import paulevs.betternether.BetterNether;
import paulevs.betternether.registry.NetherEntities.KnownSpawnTypes;
import paulevs.betternether.registry.NetherFeatures;
import paulevs.betternether.registry.NetherStructures;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.api.tag.TagAPI;
import ru.bclib.world.biomes.BCLBiome;

public class NetherBiomeBuilder {
	private static Biome BASE_BIOME;
	static final SurfaceRules.RuleSource BEDROCK = SurfaceRules.state(Blocks.BEDROCK.defaultBlockState());
	//(ResourceLocation randomName, VerticalAnchor trueAtAndBelow, VerticalAnchor falseAtAndAbove)
	static final SurfaceRules.VerticalGradientConditionSource BEDROCK_BOTTOM =
		new SurfaceRules.VerticalGradientConditionSource(BetterNether.makeID("bedrock_floor"), VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5));
	static final SurfaceRules.VerticalGradientConditionSource BEDROCK_TOP =
		new SurfaceRules.VerticalGradientConditionSource(BetterNether.makeID("bedrock_roof"), VerticalAnchor.belowTop(5), VerticalAnchor.top());

	private static void addVanillaStructures(BCLBiomeBuilder builder) {
		builder.carver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE);
		builder.structure(BiomeTags.HAS_RUINED_PORTAL_NETHER);
	}
	
	private static void addVanillaFeatures(BCLBiomeBuilder builder) {
		builder
			.feature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.SPRING_LAVA)
			.defaultMushrooms()
			.feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_OPEN)
			.feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE)
			.feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_SOUL_FIRE)
			.feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE_EXTRA)
			.feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE)
			.feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.BROWN_MUSHROOM_NETHER)
			.feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.RED_MUSHROOM_NETHER)
			.feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA)
			.feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_CLOSED);
	}
	
	public static NetherBiome create(NetherBiomeConfig data){
		return create(data, null);
	}
	

	
	public static NetherBiome create(NetherBiomeConfig data, BCLBiome edgeBiome){
		if (BASE_BIOME==null) {
			BASE_BIOME = NetherBiomes.netherWastes();
		}
		final ResourceLocation ID = data.ID;
		
		BCLBiomeBuilder builder = BCLBiomeBuilder
			.start(ID)
			//.category(BiomeCategory.NETHER)
			.surface(data.surface().build())
			.temperature(BASE_BIOME.getBaseTemperature())
			.wetness(BASE_BIOME.getDownfall())
			.precipitation(Precipitation.NONE)
			.waterColor(BASE_BIOME.getWaterColor())
			.waterFogColor(BASE_BIOME.getWaterFogColor())
			.skyColor(BASE_BIOME.getSkyColor())
			.music(SoundEvents.MUSIC_BIOME_NETHER_WASTES)
			.mood(SoundEvents.AMBIENT_NETHER_WASTES_MOOD)
			.loop(SoundEvents.AMBIENT_NETHER_WASTES_LOOP)
			.additions(SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS)
			.edge(edgeBiome);
		
		
		if (data.hasVanillaStructures()) addVanillaStructures(builder);
		if (data.hasVanillaFeatures()) addVanillaFeatures(builder);
		if (data.hasVanillaOres()) builder.netherDefaultOres();
		
		for (KnownSpawnTypes spawnType : KnownSpawnTypes.values()) {
			spawnType.addSpawn(builder, data);
		}
		
		NetherFeatures.addDefaultFeatures(builder);
		if (data.hasDefaultOres()) NetherFeatures.addDefaultOres(builder);
		NetherStructures.addDefaultFeatures(builder);
		data.addCustomBuildData(builder);
		
		NetherBiome b = builder.build(data.getSupplier());
		TagAPI.addBiomeTag(BiomeTags.IS_NETHER, b.getBiome());
		return b;
	}
}
