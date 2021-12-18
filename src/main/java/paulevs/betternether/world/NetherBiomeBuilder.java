package paulevs.betternether.world;

import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.data.worldgen.biome.NetherBiomes;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import paulevs.betternether.BetterNether;
import paulevs.betternether.interfaces.IStructureFeatures;
import paulevs.betternether.registry.NetherEntities;
import paulevs.betternether.registry.NetherFeatures;
import paulevs.betternether.registry.NetherStructures;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.api.surface.SurfaceRuleBuilder;
import ru.bclib.config.Configs;
import ru.bclib.world.biomes.BCLBiome;

public class NetherBiomeBuilder {
	private static Biome BASE_BIOME;
	static final SurfaceRules.RuleSource BEDROCK = SurfaceRules.state(Blocks.BEDROCK.defaultBlockState());
	//(ResourceLocation randomName, VerticalAnchor trueAtAndBelow, VerticalAnchor falseAtAndAbove)
	static final SurfaceRules.VerticalGradientConditionSource BEDROCK_BOTTOM =
		new SurfaceRules.VerticalGradientConditionSource(BetterNether.makeID("bedrock_floor"), VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5));
	static final SurfaceRules.VerticalGradientConditionSource BEDROCK_TOP =
		new SurfaceRules.VerticalGradientConditionSource(BetterNether.makeID("bedrock_roof"), VerticalAnchor.belowTop(5), VerticalAnchor.top());

	public static final IStructureFeatures VANILLA_STRUCTURES = (IStructureFeatures)(Object)new StructureFeatures();
	
	private static void addVanillaStructures(BCLBiomeBuilder builder) {
		builder.carver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE);
		builder.structure(VANILLA_STRUCTURES.getRUINED_PORTAL_NETHER());
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
	
	private static void addVanillaMobs(BCLBiomeBuilder builder) {
		builder
			.spawn(EntityType.GHAST, 50, 4, 4)
			.spawn(EntityType.ZOMBIFIED_PIGLIN, 100, 4, 4)
			.spawn(EntityType.MAGMA_CUBE, 2, 4, 4)
			.spawn(EntityType.ENDERMAN, 1, 4, 4)
			.spawn(EntityType.PIGLIN, 15, 4, 4)
			.spawn(EntityType.STRIDER, 60, 1, 2);
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
			.category(BiomeCategory.NETHER)
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
		
		//BN Spawns
			.spawn(NetherEntities.FIREFLY, 5, 1, 3)
			.spawn(NetherEntities.SKULL, 2, 2, 4)
			.spawn(NetherEntities.NAGA, 8, 3, 5)
			.spawn(NetherEntities.HYDROGEN_JELLYFISH, 5, 2, 6);
		
		if (data.hasVanillaStructures()) addVanillaStructures(builder);
		if (data.hasVanillaFeatures()) addVanillaFeatures(builder);
		if (data.hasVanillaOres()) builder.netherDefaultOres();
		
		if (data.spawnVanillaMobs()) addVanillaMobs(builder);
		
		
		NetherFeatures.addDefaultFeatures(builder);
		if (data.hasDefaultOres()) NetherFeatures.addDefaultOres(builder);
		NetherStructures.addDefaultFeatures(builder);
		data.addCustomBuildData(builder);
		
		NetherBiome b = builder.build(data.getSupplier());
		if (Configs.BIOMES_CONFIG.getBoolean(b.configGroup(), "vertical", data.vertical())) b.setVertical();
		
		if (edgeBiome!=null) {
			final int edgeSize = Configs.BIOMES_CONFIG.getInt(b.configGroup(), "edge_size", b.getEdgeSize());
			if (edgeSize > 0) {
				b.setEdge(edgeBiome);
			}
		}
		return b;
	}
}
