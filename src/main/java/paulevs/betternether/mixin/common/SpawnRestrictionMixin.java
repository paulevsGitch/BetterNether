package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Heightmap;
import paulevs.betternether.entity.EntityFirefly;
import paulevs.betternether.entity.EntityHydrogenJellyfish;
import paulevs.betternether.entity.EntityNaga;
import paulevs.betternether.registry.EntityRegistry;

@Mixin(SpawnRestriction.class)
public class SpawnRestrictionMixin
{
	@Shadow
	private static <T extends MobEntity> void register(EntityType<T> type, SpawnRestriction.Location location, Heightmap.Type heightmapType, SpawnRestriction.SpawnPredicate<T> predicate) {}
	
	static
	{
		register(EntityRegistry.NAGA, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EntityNaga::canSpawn);
		register(EntityRegistry.FIREFLY, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING, EntityFirefly::canSpawn);
		register(EntityRegistry.HYDROGEN_JELLYFISH, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EntityHydrogenJellyfish::canSpawn);
	}
}
