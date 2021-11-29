package paulevs.betternether.registry;

import com.google.common.collect.Lists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import paulevs.betternether.BetterNether;
import paulevs.betternether.config.Configs;
import ru.bclib.world.biomes.BCLBiomeDef;
import ru.bclib.world.features.BCLFeature;

import java.util.List;
import java.util.function.Supplier;

public class NetherFeatures {
	// Ores //
	public static final BCLFeature CINCINNASITE_ORE = registerOre("cincinnasite", NetherBlocks.CINCINNASITE_ORE, 9, 8, 5, 128);
	public static final BCLFeature NETHER_RUBY_ORE = registerOre("nether_ruby", NetherBlocks.NETHER_RUBY_ORE, 5, 3, 5, 64);
	public static final BCLFeature NETHER_LAPIS_ORE = registerOre("nether_lapis", NetherBlocks.NETHER_LAPIS_ORE, 18, 4, 32, 128);
	public static final BCLFeature NETHER_REDSTONE_ORE = registerOre("nether_redstone", NetherBlocks.NETHER_REDSTONE_ORE, 2, 16, 32, 80);
	
	private static BCLFeature registerOre(String name, Block blockOre, int veins, int veinSize, int minY, int maxY){
		return registerOre(
			name+"_ore",
			blockOre,
			Configs.GENERATOR.getInt("generator.world.ores." + name, "vein_count", veins),
			Configs.GENERATOR.getInt("generator.world.ores." + name, "vein_size", veinSize),
			0,
			Configs.GENERATOR.getInt("generator.world.ores." + name, "minY", minY),
			Configs.GENERATOR.getInt("generator.world.ores." + name, "maxY", maxY)
		);
	}
	
	private static BCLFeature registerOre(String name, Block blockOre, int veins, int veinSize, int offset, int minY, int maxY) {
		return BCLFeature.makeOreFeature(BetterNether.makeID(name), blockOre, Blocks.NETHERRACK, veins, veinSize, offset, minY, maxY);
	}
	
	private static void addFeature(BCLFeature feature, List<List<Supplier<ConfiguredFeature<?, ?>>>> features) {
		int index = feature.getFeatureStep().ordinal();
		if (features.size() > index) {
			features.get(index).add(() -> feature.getFeatureConfigured());
		}
		else {
			List<Supplier<ConfiguredFeature<?, ?>>> newFeature = Lists.newArrayList();
			newFeature.add(() -> feature.getFeatureConfigured());
			features.add(newFeature);
		}
	}
	
	public static void addDefaultFeatures(BCLBiomeDef def) {
		def.addFeature(CINCINNASITE_ORE);
		def.addFeature(NETHER_RUBY_ORE);
		def.addFeature(NETHER_LAPIS_ORE);
		def.addFeature(NETHER_REDSTONE_ORE);
	}
	
	public static void registerBiomeFeatures(ResourceLocation id, Biome biome, List<List<Supplier<ConfiguredFeature<?, ?>>>> features) {
		addFeature(CINCINNASITE_ORE, features);
		addFeature(NETHER_RUBY_ORE, features);
		addFeature(NETHER_LAPIS_ORE, features);
		addFeature(NETHER_REDSTONE_ORE, features);
	}
	
	public static void register() {
	}
}
