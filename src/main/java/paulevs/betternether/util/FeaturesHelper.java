package paulevs.betternether.util;

import com.google.common.collect.Lists;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import paulevs.betternether.config.Configs;
import paulevs.betternether.mixin.common.BiomeGenerationSettingsAccessor;
import paulevs.betternether.registry.NetherFeatures;
import ru.bclib.api.BiomeAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FeaturesHelper {
	public static void addFeatures(Registry<Biome> biomeRegistry) {
		biomeRegistry.forEach((biome) -> {
			ResourceLocation key = biomeRegistry.getKey(biome);
			if (BiomeAPI.isNetherBiome(key)) {
				BiomeGenerationSettingsAccessor accessor = (BiomeGenerationSettingsAccessor) biome.getGenerationSettings();
				List<List<Supplier<ConfiguredFeature<?, ?>>>> preFeatures = accessor.be_getFeatures();
				List<List<Supplier<ConfiguredFeature<?, ?>>>> features = new ArrayList<>(preFeatures.size());
				preFeatures.forEach((list) -> features.add(Lists.newArrayList(list)));
				
				NetherFeatures.registerBiomeFeatures(key, biome, features);
				
				accessor.be_setFeatures(features);
			}
		});
		Configs.BIOMES.markToSave();
	}
}
