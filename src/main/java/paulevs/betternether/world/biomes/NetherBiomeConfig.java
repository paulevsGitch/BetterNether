package paulevs.betternether.world.biomes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import paulevs.betternether.BetterNether;
import ru.bclib.api.biomes.BCLBiomeBuilder;

import java.util.function.BiFunction;

public abstract class NetherBiomeConfig {
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
	
	public boolean hasVanillaStructures() {
		return true;
	}
	
	protected abstract void addCustomBuildData(BCLBiomeBuilder builder);
	
	public abstract BiFunction<ResourceLocation, Biome, NetherBiome> getSupplier();
}
