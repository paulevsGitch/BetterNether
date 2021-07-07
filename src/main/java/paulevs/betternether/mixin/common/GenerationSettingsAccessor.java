package paulevs.betternether.mixin.common;

import java.util.List;
import java.util.function.Supplier;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BiomeGenerationSettings.class)
public interface GenerationSettingsAccessor {
	@Accessor("structureFeatures")
	List<Supplier<ConfiguredStructureFeature<?, ?>>> getStructureFeatures();

	@Accessor("structureFeatures")
	void setStructureFeatures(List<Supplier<ConfiguredStructureFeature<?, ?>>> structureFeatures);
}