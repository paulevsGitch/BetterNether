package paulevs.betternether.registry;

import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import paulevs.betternether.BetterNether;
import paulevs.betternether.config.Configs;
import paulevs.betternether.world.structures.CityFeature;
import ru.bclib.api.BiomeAPI;

public class NetherStructures {
	// Nether City
	public static final CityFeature CITY = new CityFeature();
	public static final ConfiguredStructureFeature<NoneFeatureConfiguration, ? extends StructureFeature<NoneFeatureConfiguration>> CITY_CONFIGURED = CITY.configured(NoneFeatureConfiguration.NONE);
	
	public static void register() {
		if (Configs.GENERATOR.getBoolean("generator.world.cities", "generate", true)) {
			int distance = Configs.GENERATOR.getInt("generator.world.cities", "distance", 64);
			int separation = distance >> 1;
			
			BiomeAPI.registerNetherBiomeModification((biomeID, biome) -> BiomeAPI.addBiomeStructure(biome, CITY_CONFIGURED));
			
			FabricStructureBuilder.create(new ResourceLocation(BetterNether.MOD_ID, "nether_city"), CITY)
								  .step(Decoration.RAW_GENERATION)
								  .defaultConfig(new StructureFeatureConfiguration(distance, separation, 1234))
								  .superflatFeature(CITY_CONFIGURED)
								  .register();
			
			BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(BetterNether.MOD_ID, "nether_city"), CITY_CONFIGURED);
		}
	}
}
