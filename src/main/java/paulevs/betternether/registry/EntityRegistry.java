package paulevs.betternether.registry;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.impl.client.renderer.registry.EntityModelLayerImpl;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldAccess;
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
import paulevs.betternether.entity.model.*;
import paulevs.betternether.mixin.client.EntityModelLayersMixin;

public class EntityRegistry {
	public static final Map<EntityType<? extends LivingEntity>, DefaultAttributeContainer> ATTRIBUTES = Maps.newHashMap();
	private static final List<EntityType<?>> NETHER_ENTITIES = Lists.newArrayList();

	public static final EntityType<EntityChair> CHAIR = FabricEntityTypeBuilder.create(SpawnGroup.MISC, EntityChair::new).dimensions(EntityDimensions.fixed(0.0F, 0.0F)).fireImmune().disableSummon().build();
	public static final EntityType<EntityNagaProjectile> NAGA_PROJECTILE = FabricEntityTypeBuilder.create(SpawnGroup.MISC, EntityNagaProjectile::new).dimensions(EntityDimensions.fixed(1F, 1F)).disableSummon().build();

	public static final EntityType<EntityFirefly> FIREFLY = FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, EntityFirefly::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).fireImmune().build();
	public static final EntityType<EntityHydrogenJellyfish> HYDROGEN_JELLYFISH = FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, EntityHydrogenJellyfish::new).dimensions(EntityDimensions.changing(2F, 5F)).fireImmune().build();
	public static final EntityType<EntityNaga> NAGA = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EntityNaga::new).dimensions(EntityDimensions.fixed(0.625F, 2.75F)).fireImmune().build();
	public static final EntityType<EntityFlyingPig> FLYING_PIG = FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, EntityFlyingPig::new).dimensions(EntityDimensions.fixed(1.0F, 1.25F)).fireImmune().build();
	public static final EntityType<EntityJungleSkeleton> JUNGLE_SKELETON = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EntityJungleSkeleton::new).dimensions(EntityDimensions.fixed(0.6F, 1.99F)).fireImmune().build();
	public static final EntityType<EntitySkull> SKULL = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EntitySkull::new).dimensions(EntityDimensions.fixed(0.625F, 0.625F)).fireImmune().build();

	public static final EntityModelLayer FIREFLY_LAYER = registerMain("betternether_firefly");
	public static final EntityModelLayer NAGA_LAYER = registerMain("betternether_naga");
	public static final EntityModelLayer JUNGLE_SKELETON_LAYER = registerMain("betternether_jungle_skeleton");
	public static final EntityModelLayer FLYING_PIG_LAYER = registerMain("betternether_flying_pig");
	public static final EntityModelLayer HYDROGEN_JELLYFISH_LAYER = registerMain("betternether_hydrogen_jelly");

	public static EntityModelLayer registerMain(String key){
		System.out.println("Register Entity: " + key);
		return EntityModelLayersMixin.callRegisterMain(key);
	}
	public static void addModels(ImmutableMap.Builder<EntityModelLayer, TexturedModelData> builder){
		/*builder.put(FIREFLY_LAYER, ModelEntityFirefly.getTexturedModelData());
		builder.put(NAGA_LAYER, ModelNaga.getTexturedModelData());
		builder.put(JUNGLE_SKELETON_LAYER, ModelJungleSkeleton.getTexturedModelData());
		builder.put(FLYING_PIG_LAYER, ModelEntityFlyingPig.getTexturedModelData());
		builder.put(HYDROGEN_JELLYFISH_LAYER, ModelEntityHydrogenJellyfish.getTexturedModelData());*/
	}

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
		registerEntity("jungle_skeleton", JUNGLE_SKELETON, AbstractSkeletonEntity.createAbstractSkeletonAttributes().build());
		registerEntity("skull", SKULL, EntitySkull.getAttributeContainer());

		EntityModelLayerImpl.PROVIDERS.put(FIREFLY_LAYER, () -> ModelEntityFirefly.getTexturedModelData());
		EntityModelLayerImpl.PROVIDERS.put(NAGA_LAYER, () -> ModelNaga.getTexturedModelData());
		EntityModelLayerImpl.PROVIDERS.put(JUNGLE_SKELETON_LAYER, () -> ModelJungleSkeleton.getTexturedModelData());
		EntityModelLayerImpl.PROVIDERS.put(FLYING_PIG_LAYER, () -> ModelEntityFlyingPig.getTexturedModelData());
		EntityModelLayerImpl.PROVIDERS.put(HYDROGEN_JELLYFISH_LAYER, () -> ModelEntityHydrogenJellyfish.getTexturedModelData());
	}

	public static void registerEntity(String name, EntityType<? extends LivingEntity> entity) {
		Registry.register(Registry.ENTITY_TYPE, new Identifier(BetterNether.MOD_ID, name), entity);
		ATTRIBUTES.put(entity, MobEntity.createMobAttributes().build());
	}

	public static void registerEntity(String name, EntityType<? extends LivingEntity> entity, DefaultAttributeContainer container) {
		if (Configs.MOBS.getBoolean("mobs", name, true)) {
			Registry.register(Registry.ENTITY_TYPE, new Identifier(BetterNether.MOD_ID, name), entity);
			ATTRIBUTES.put(entity, container);
		}
	}

	public static boolean isNetherEntity(Entity entity) {
		return NETHER_ENTITIES.contains(entity.getType());
	}

	public static boolean alwaysSpawn(EntityType<?> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
		return true;
	}
}
