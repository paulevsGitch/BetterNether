package paulevs.betternether.world;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import paulevs.betternether.BetterNether;
import paulevs.betternether.registry.NetherEntities.KnownSpawnTypes;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import ru.bclib.api.surface.SurfaceRuleBuilder;

public abstract class NetherBiomeConfig {
	public static final SurfaceRules.RuleSource NETHERRACK = SurfaceRules.state(Blocks.NETHERRACK.defaultBlockState());
	public static final SurfaceRules.RuleSource BEDROCK_BOTTOM = SurfaceRules.ifTrue(NetherBiomeBuilder.BEDROCK_BOTTOM, NetherBiomeBuilder.BEDROCK);
	public static final SurfaceRules.RuleSource BEDROCK_TOP = SurfaceRules.ifTrue(SurfaceRules.not(NetherBiomeBuilder.BEDROCK_TOP), NetherBiomeBuilder.BEDROCK);
	
	
	public final ResourceLocation ID;
	
	protected NetherBiomeConfig(String name) {
		this.ID = BetterNether.makeID(name.replace(' ', '_')
										  .toLowerCase());
	}
	
	/**
	 * Returns the group used in the config Files for this biome
	 *
	 * Example: {@code Configs.BIOMES_CONFIG.getFloat(configGroup(), "generation_chance", 1.0);}
	 * @return The group name
	 */
	public String configGroup() {
		return ID.getNamespace() + "." + ID.getPath();
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
	
	
	public <M extends Mob> int spawnWeight(KnownSpawnTypes type) {
		int res = type.weight;
		switch (type){
			case JUNGLE_SKELETON, FLYING_PIG, HOGLIN, PIGLIN_BRUTE-> res = 0;
		}
		return res;
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
