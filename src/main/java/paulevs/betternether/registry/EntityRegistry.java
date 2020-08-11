package paulevs.betternether.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
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
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.config.Config;
import paulevs.betternether.entity.EntityChair;
import paulevs.betternether.entity.EntityFirefly;
import paulevs.betternether.entity.EntityFlyingPig;
import paulevs.betternether.entity.EntityHydrogenJellyfish;
import paulevs.betternether.entity.EntityJungleSkeleton;
import paulevs.betternether.entity.EntityNaga;
import paulevs.betternether.entity.EntityNagaProjectile;
import paulevs.betternether.entity.EntitySkull;

public class EntityRegistry
{
	public static final Map<EntityType<? extends LivingEntity>, DefaultAttributeContainer> ATTRIBUTES = Maps.newHashMap();
	private static final List<EntityType<?>> NETHER_ENTITIES = Lists.newArrayList();
	
	public static final EntityType<EntityChair> CHAIR = FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, EntityChair::new).dimensions(EntityDimensions.fixed(0.0F, 0.0F)).fireImmune().disableSummon().trackable(10, 1).build();
	public static final EntityType<EntityNagaProjectile> NAGA_PROJECTILE = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EntityNagaProjectile::new).dimensions(EntityDimensions.fixed(1F, 1F)).disableSummon().trackable(60, 1).build();
	
	public static final EntityType<EntityFirefly> FIREFLY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EntityFirefly::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).fireImmune().trackable(70, 3).build();
	public static final EntityType<EntityHydrogenJellyfish> HYDROGEN_JELLYFISH = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EntityHydrogenJellyfish::new).dimensions(EntityDimensions.changing(2F, 5F)).fireImmune().trackable(150, 1).build();
	public static final EntityType<EntityNaga> NAGA = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EntityNaga::new).dimensions(EntityDimensions.fixed(0.625F, 2.75F)).fireImmune().trackable(100, 3).build();
	public static final EntityType<EntityFlyingPig> FLYING_PIG = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EntityFlyingPig::new).dimensions(EntityDimensions.fixed(1.0F, 1.25F)).fireImmune().trackable(50, 3).build();
	public static final EntityType<EntityJungleSkeleton> JUNGLE_SKELETON = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EntityJungleSkeleton::new).dimensions(EntityDimensions.fixed(0.6F, 1.99F)).fireImmune().trackable(100, 3).build();
	public static final EntityType<EntitySkull> SKULL = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EntitySkull::new).dimensions(EntityDimensions.fixed(0.625F, 0.625F)).fireImmune().trackable(50, 3).build();
	
	public static void register()
	{
		NETHER_ENTITIES.add(EntityType.GHAST);
		NETHER_ENTITIES.add(EntityType.ZOMBIFIED_PIGLIN);
		NETHER_ENTITIES.add(EntityType.PIGLIN);
		NETHER_ENTITIES.add(EntityType.HOGLIN);
		NETHER_ENTITIES.add(EntityType.BLAZE);
		NETHER_ENTITIES.add(EntityType.STRIDER);
		
		NetherBiome[] netherBiomes = getBiomes();
		List<NetherBiome> fireflyBiomes = new ArrayList<NetherBiome>(BiomesRegistry.getRegisteredBiomes());
		fireflyBiomes.remove(BiomesRegistry.BIOME_GRAVEL_DESERT);
		fireflyBiomes.remove(BiomesRegistry.BIOME_EMPTY_NETHER);
		fireflyBiomes.remove(BiomesRegistry.BIOME_BASALT_DELTAS);
		
		registerEntity("chair", CHAIR);
		registerEntity("naga_projectile", NAGA_PROJECTILE);
		
		registerEntity("firefly", FIREFLY, EntityFirefly.getAttributeContainer(), 5, 2, 6, fireflyBiomes.toArray(new NetherBiome[] {}));
		registerEntity("hydrogen_jellyfish", HYDROGEN_JELLYFISH, EntityHydrogenJellyfish.getAttributeContainer(), 5, 2, 5, netherBiomes);
		registerEntity("naga", NAGA, EntityNaga.getAttributeContainer(), 20, 2, 4, netherBiomes);
		registerEntity("flying_pig", FLYING_PIG, EntityFlyingPig.getAttributeContainer(), 20, 2, 4, BiomesRegistry.BIOME_CRIMSON_FOREST, BiomesRegistry.CRIMSON_GLOWING_WOODS, BiomesRegistry.CRIMSON_PINEWOOD);
		registerEntity("jungle_skeleton", JUNGLE_SKELETON, AbstractSkeletonEntity.createAbstractSkeletonAttributes().build(), 1000, 2, 4, BiomesRegistry.BIOME_NETHER_JUNGLE);
		registerEntity("skull", SKULL, EntitySkull.getAttributeContainer(), 2, 2, 4, netherBiomes);
	}
	
	public static void registerEntity(String name, EntityType<? extends LivingEntity> entity)
	{
		Registry.register(Registry.ENTITY_TYPE, new Identifier(BetterNether.MOD_ID, name), entity);
		ATTRIBUTES.put(entity, MobEntity.createMobAttributes().build());
	}
	
	public static void registerEntity(String name, EntityType<? extends LivingEntity> entity, DefaultAttributeContainer container, int weight, int minGroupSize, int maxGroupSize, NetherBiome... spawnBiomes)
	{
		if (Config.getBoolean("mobs", name, true))
		{
			Registry.register(Registry.ENTITY_TYPE, new Identifier(BetterNether.MOD_ID, name), entity);
			ATTRIBUTES.put(entity, container);
			if (spawnBiomes != null)
			{
				int spawnWeight = Config.getInt("mobs", name + "_" + "spawn_chance", weight);
				for (NetherBiome biome: spawnBiomes)
				{
					biome.addEntitySpawn(entity, spawnWeight, minGroupSize, maxGroupSize);
				}
				NETHER_ENTITIES.add(entity);
			}
		}
	}
	
	private static NetherBiome[] getBiomes()
	{
		return BiomesRegistry.getRegisteredBiomes().toArray(new NetherBiome[] {});
	}
	
	public static boolean isNetherEntity(Entity entity)
	{
		return NETHER_ENTITIES.contains(entity.getType());
	}
	
	public static boolean alwaysSpawn(EntityType<?> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random)
	{
		return true;
	}
}
