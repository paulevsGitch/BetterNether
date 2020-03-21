package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnEntry;
import paulevs.betternether.IBiome;

@Mixin(Biome.class)
public class BiomeMixin implements IBiome
{
	@Shadow
	protected void addSpawn(EntityCategory type, Biome.SpawnEntry spawnEntry) {}
	
	@Override
	public void addEntitySpawn(EntityType<?> type, int weight, int minGroupSize, int maxGroupSize)
	{
		addSpawn(type.getCategory(), new SpawnEntry(type, weight, minGroupSize, maxGroupSize));
	}
}