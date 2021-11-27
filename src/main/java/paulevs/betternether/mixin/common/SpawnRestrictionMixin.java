package paulevs.betternether.mixin.common;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.entity.EntityFirefly;
import paulevs.betternether.entity.EntityFlyingPig;
import paulevs.betternether.entity.EntityHydrogenJellyfish;
import paulevs.betternether.entity.EntityJungleSkeleton;
import paulevs.betternether.entity.EntityNaga;
import paulevs.betternether.entity.EntitySkull;
import paulevs.betternether.registry.EntityRegistry;
import ru.bclib.api.SpawnAPI;

@Mixin(SpawnPlacements.class)
public class SpawnRestrictionMixin {
	@Shadow
	private static <T extends Mob> void register(EntityType<T> type, SpawnPlacements.Type location, Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> predicate) {}

	static {
		register(EntityRegistry.NAGA, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new SpawnAPI<>(EntityNaga.class).notPeacefulBelowBrightness(8).maxAlive(32)::canSpawn);
		register(EntityRegistry.FIREFLY, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, new SpawnAPI<>(EntityFirefly.class).belowMaxHeight().notAboveBlock(BlocksHelper::isLava, 7).maxAlive(32)::canSpawn);
		register(EntityRegistry.HYDROGEN_JELLYFISH, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new SpawnAPI<>(EntityHydrogenJellyfish.class).maxAlive(24)::canSpawn);
		register(EntityRegistry.JUNGLE_SKELETON, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new SpawnAPI<>(EntityJungleSkeleton.class).notPeaceful().maxAlive(16)::canSpawn);
		register(EntityRegistry.SKULL, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, new SpawnAPI<>(EntitySkull.class).belowMaxHeight().notPeacefulBelowBrightness().maxAlive(16)::canSpawn);
		register(EntityRegistry.FLYING_PIG, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, new SpawnAPI<>(EntityFlyingPig.class).belowMaxHeight().maxAlive(16)::canSpawn);
	}
}
