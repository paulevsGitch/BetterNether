package paulevs.betternether.registry;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.level.LevelAccessor;
import paulevs.betternether.BetterNether;
import paulevs.betternether.config.Configs;
import paulevs.betternether.entity.EntityChair;
import paulevs.betternether.entity.EntityFirefly;
import paulevs.betternether.entity.EntityFlyingPig;
import paulevs.betternether.entity.EntityHydrogenJellyfish;
import paulevs.betternether.entity.EntityJungleSkeleton;
import paulevs.betternether.entity.EntityNaga;
import paulevs.betternether.entity.EntityNagaProjectile;
import paulevs.betternether.entity.EntitySkull;

public class EntityRegistry {
	public static final Map<EntityType<? extends LivingEntity>, AttributeSupplier> ATTRIBUTES = Maps.newHashMap();
	private static final List<EntityType<?>> NETHER_ENTITIES = Lists.newArrayList();

	public static final EntityType<EntityChair> CHAIR = FabricEntityTypeBuilder.create(MobCategory.MISC, EntityChair::new).dimensions(EntityDimensions.fixed(0.0F, 0.0F)).fireImmune().disableSummon().build();
	public static final EntityType<EntityNagaProjectile> NAGA_PROJECTILE = FabricEntityTypeBuilder.create(MobCategory.MISC, EntityNagaProjectile::new).dimensions(EntityDimensions.fixed(1F, 1F)).disableSummon().build();

	public static final EntityType<EntityFirefly> FIREFLY = FabricEntityTypeBuilder.create(MobCategory.AMBIENT, EntityFirefly::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).fireImmune().build();
	public static final EntityType<EntityHydrogenJellyfish> HYDROGEN_JELLYFISH = FabricEntityTypeBuilder.create(MobCategory.AMBIENT, EntityHydrogenJellyfish::new).dimensions(EntityDimensions.scalable(2F, 5F)).fireImmune().build();
	public static final EntityType<EntityNaga> NAGA = FabricEntityTypeBuilder.create(MobCategory.MONSTER, EntityNaga::new).dimensions(EntityDimensions.fixed(0.625F, 2.75F)).fireImmune().build();
	public static final EntityType<EntityFlyingPig> FLYING_PIG = FabricEntityTypeBuilder.create(MobCategory.AMBIENT, EntityFlyingPig::new).dimensions(EntityDimensions.fixed(1.0F, 1.25F)).fireImmune().build();
	public static final EntityType<EntityJungleSkeleton> JUNGLE_SKELETON = FabricEntityTypeBuilder.create(MobCategory.MONSTER, EntityJungleSkeleton::new).dimensions(EntityDimensions.fixed(0.6F, 1.99F)).fireImmune().build();
	public static final EntityType<EntitySkull> SKULL = FabricEntityTypeBuilder.create(MobCategory.MONSTER, EntitySkull::new).dimensions(EntityDimensions.fixed(0.625F, 0.625F)).fireImmune().build();

	public static void register() {
		NETHER_ENTITIES.add(EntityType.GHAST);
		NETHER_ENTITIES.add(EntityType.ZOMBIFIED_PIGLIN);
		NETHER_ENTITIES.add(EntityType.PIGLIN);
		NETHER_ENTITIES.add(EntityType.HOGLIN);
		NETHER_ENTITIES.add(EntityType.BLAZE);
		NETHER_ENTITIES.add(EntityType.STRIDER);

		registerEntity("chair", CHAIR, EntityChair.getAttributeContainer());
		registerEntity("naga_projectile", NAGA_PROJECTILE);

		registerEntity("firefly", FIREFLY, EntityFirefly.getAttributeContainer());
		registerEntity("hydrogen_jellyfish", HYDROGEN_JELLYFISH, EntityHydrogenJellyfish.getAttributeContainer());
		registerEntity("naga", NAGA, EntityNaga.getAttributeContainer());
		registerEntity("flying_pig", FLYING_PIG, EntityFlyingPig.getAttributeContainer());
		registerEntity("jungle_skeleton", JUNGLE_SKELETON, AbstractSkeleton.createAttributes().build());
		registerEntity("skull", SKULL, EntitySkull.getAttributeContainer());
	}

	public static void registerEntity(String name, EntityType<? extends LivingEntity> entity) {
		Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(BetterNether.MOD_ID, name), entity);
		ATTRIBUTES.put(entity, Mob.createMobAttributes().build());
	}

	public static void registerEntity(String name, EntityType<? extends LivingEntity> entity, AttributeSupplier container) {
		if (Configs.MOBS.getBoolean("mobs", name, true)) {
			Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(BetterNether.MOD_ID, name), entity);
			ATTRIBUTES.put(entity, container);
		}
	}

	public static boolean isNetherEntity(Entity entity) {
		return NETHER_ENTITIES.contains(entity.getType());
	}

	public static boolean alwaysSpawn(EntityType<?> type, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, Random random) {
		return true;
	}
}
