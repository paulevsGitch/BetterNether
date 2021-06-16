package paulevs.betternether.mixin.common;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.function.Supplier;

@Mixin(GenerationSettings.class)
public interface GenerationSettingsAccessor {
	@Accessor("structureFeatures")
	List<Supplier<ConfiguredStructureFeature<?, ?>>> getStructureFeatures();

	@Accessor("structureFeatures")
	void setStructureFeatures(List<Supplier<ConfiguredStructureFeature<?, ?>>> structureFeatures);
}