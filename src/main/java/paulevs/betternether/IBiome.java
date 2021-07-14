package paulevs.betternether;

import net.minecraft.world.entity.EntityType;

public interface IBiome {
	public void addEntitySpawn(EntityType<?> type, int weight, int minGroupSize, int maxGroupSize);
}
