package paulevs.betternether.registry;

import com.google.common.collect.Lists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import paulevs.betternether.BetterNether;
import ru.bclib.world.biomes.BCLBiomeDef;
import ru.bclib.world.features.BCLFeature;

import java.util.List;
import java.util.function.Supplier;

public class NetherFeatures {
	// Ores //
	public static final BCLFeature CINCINNASITE_ORE = registerOre("cincinnasite_ore", NetherBlocks.CINCINNASITE_ORE, 9, 8, 0, 5, 128);
	public static final BCLFeature NETHER_RUBY_ORE = registerOre("nether_ruby_ore", NetherBlocks.NETHER_RUBY_ORE, 5, 3, 0, 5, 64);
	public static final BCLFeature NETHER_LAPIS_ORE = registerOre("nether_lapis_ore", NetherBlocks.NETHER_LAPIS_ORE, 18, 4, 0, 32, 128);
	public static final BCLFeature NETHER_REDSTONE_ORE = registerOre("nether_redstone_ore", NetherBlocks.NETHER_REDSTONE_ORE, 1, 16, 0, 32, 80);
	
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
	
	public static void register() {}
}
