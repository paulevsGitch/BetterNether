package paulevs.betternether.mixin.common;

import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RuinedPortalConfiguration;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import paulevs.betternether.interfaces.IStructureFeatures;

@Mixin(StructureFeatures.class)
public class StructureFeaturesAcessor implements IStructureFeatures {
	@Shadow @Final private static ConfiguredStructureFeature<NoneFeatureConfiguration, ? extends StructureFeature<NoneFeatureConfiguration>> NETHER_BRIDGE;
	@Shadow @Final private static ConfiguredStructureFeature<RangeConfiguration, ? extends StructureFeature<RangeConfiguration>> NETHER_FOSSIL;
	@Shadow @Final private static ConfiguredStructureFeature<JigsawConfiguration, ? extends StructureFeature<JigsawConfiguration>> BASTION_REMNANT;
	@Shadow @Final private static ConfiguredStructureFeature<RuinedPortalConfiguration, ? extends StructureFeature<RuinedPortalConfiguration>> RUINED_PORTAL_NETHER;
	
	@Override
	public ConfiguredStructureFeature<RuinedPortalConfiguration, ? extends StructureFeature<RuinedPortalConfiguration>> getRUINED_PORTAL_NETHER() {
		return RUINED_PORTAL_NETHER;
	}
	
	@Override
	public ConfiguredStructureFeature<NoneFeatureConfiguration, ? extends StructureFeature<NoneFeatureConfiguration>> getNETHER_BRIDGE(){
		return NETHER_BRIDGE;
	}
	
	@Override
	public ConfiguredStructureFeature<JigsawConfiguration, ? extends StructureFeature<JigsawConfiguration>> getBASTION_REMNANT() {
		return BASTION_REMNANT;
	}
	
	@Override
	public ConfiguredStructureFeature<RangeConfiguration, ? extends StructureFeature<RangeConfiguration>> getNETHER_FOSSILE() {
		return NETHER_FOSSIL;
	}
}
