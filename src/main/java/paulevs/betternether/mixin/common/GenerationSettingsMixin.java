package paulevs.betternether.mixin.common;

import java.util.List;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;

@Mixin(GenerationSettings.class)
public interface GenerationSettingsMixin {
	@Accessor("structureFeatures")
	List<Supplier<ConfiguredStructureFeature<?, ?>>> getStructureFeatures();

	@Accessor("structureFeatures")
	void setStructureFeatures(List<Supplier<ConfiguredStructureFeature<?, ?>>> structureFeatures);
}