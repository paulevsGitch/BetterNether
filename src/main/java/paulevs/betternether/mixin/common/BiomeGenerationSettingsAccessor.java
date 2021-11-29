package paulevs.betternether.mixin.common;

import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.function.Supplier;

@Deprecated(forRemoval = true)
@Mixin(BiomeGenerationSettings.class)
public interface BiomeGenerationSettingsAccessor {
	@Accessor("features")
	@Deprecated(forRemoval = true)
	List<List<Supplier<ConfiguredFeature<?, ?>>>> be_getFeatures();
	
	@Accessor("features")
	@Deprecated(forRemoval = true)
	void be_setFeatures(List<List<Supplier<ConfiguredFeature<?, ?>>>> features);
	
	@Accessor("structureStarts")
	@Deprecated(forRemoval = true)
	List<Supplier<ConfiguredStructureFeature<?, ?>>> be_getStructures();
	
	@Accessor("structureStarts")
	@Deprecated(forRemoval = true)
	void be_setStructures(List<Supplier<ConfiguredStructureFeature<?, ?>>> structures);
}
