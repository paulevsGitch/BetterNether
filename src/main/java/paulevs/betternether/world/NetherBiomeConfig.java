package paulevs.betternether.world;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import paulevs.betternether.BetterNether;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import ru.bclib.api.surface.SurfaceRuleBuilder;

import java.util.function.BiFunction;

public abstract class NetherBiomeConfig {
	public static final SurfaceRules.RuleSource NETHERRACK = SurfaceRules.state(Blocks.NETHERRACK.defaultBlockState());
	public static final SurfaceRules.RuleSource BEDROCK_BOTTOM = SurfaceRules.ifTrue(NetherBiomeBuilder.BEDROCK_BOTTOM, NetherBiomeBuilder.BEDROCK);
	public static final SurfaceRules.RuleSource BEDROCK_TOP = SurfaceRules.ifTrue(SurfaceRules.not(NetherBiomeBuilder.BEDROCK_TOP), NetherBiomeBuilder.BEDROCK);
	
	
	public final ResourceLocation ID;
	
	protected NetherBiomeConfig(String name) {
		this.ID = BetterNether.makeID(name.replace(' ', '_')
										  .toLowerCase());
	}
	
	public boolean spawnVanillaMobs() {
		return true;
	}
	
	public boolean hasVanillaFeatures() {
		return true;
	}
	
	public boolean hasVanillaOres() {
		return true;
	}
	
	public boolean hasDefaultOres() {
		return true;
	}
	
	public boolean hasVanillaStructures() {
		return true;
	}
	
	protected abstract void addCustomBuildData(BCLBiomeBuilder builder);
	
	public abstract BiomeSupplier<NetherBiome> getSupplier();
	
	public SurfaceRuleBuilder surface(){
		return SurfaceRuleBuilder
			.start()
			.rule(0, BEDROCK_TOP)
			.rule(0, BEDROCK_BOTTOM)
			.rule(10, NETHERRACK);
	}
}
