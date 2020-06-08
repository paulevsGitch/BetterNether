package paulevs.betternether.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import paulevs.betternether.BetterNether;
import paulevs.betternether.IBiome;
import paulevs.betternether.entity.EntityChair;
import paulevs.betternether.entity.EntityFirefly;
import paulevs.betternether.entity.EntityHydrogenJellyfish;
import paulevs.betternether.entity.EntityNaga;
import paulevs.betternether.entity.EntityNagaProjectile;

public class EntityRegistry
{
	public static final Map<EntityType<? extends LivingEntity>, DefaultAttributeContainer> ATTRUBUTES = new HashMap<EntityType<? extends LivingEntity>, DefaultAttributeContainer>();
	private static final List<EntityType<?>> NETHER_ENTITIES = new ArrayList<EntityType<?>>();
	
	public static final EntityType<EntityChair> CHAIR = FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, EntityChair::new).dimensions(EntityDimensions.fixed(0.0F, 0.0F)).fireImmune().trackable(10, 1).build();
	public static final EntityType<EntityNagaProjectile> NAGA_PROJECTILE = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EntityNagaProjectile::new).dimensions(EntityDimensions.fixed(1F, 1F)).disableSummon().trackable(60, 1).build();
	
	public static final EntityType<EntityFirefly> FIREFLY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, EntityFirefly::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).fireImmune().trackable(70, 3).build();
	public static final EntityType<EntityHydrogenJellyfish> HYDROGEN_JELLYFISH = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, EntityHydrogenJellyfish::new).dimensions(EntityDimensions.changing(2F, 5F)).fireImmune().trackable(150, 1).build();
	public static final EntityType<EntityNaga> NAGA = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EntityNaga::new).dimensions(EntityDimensions.fixed(0.625F, 2.75F)).fireImmune().trackable(100, 3).build();
	
	public static void register()
	{
		NETHER_ENTITIES.add(EntityType.GHAST);
		NETHER_ENTITIES.add(EntityType.ZOMBIFIED_PIGLIN);
		NETHER_ENTITIES.add(EntityType.PIGLIN);
		NETHER_ENTITIES.add(EntityType.HOGLIN);
		NETHER_ENTITIES.add(EntityType.BLAZE);
		NETHER_ENTITIES.add(EntityType.STRIDER);
		
		Biome[] NETHER_BIOMES = getBiomes();
		
		registerEntity("chair", CHAIR);
		registerEntity("naga_projectile", NAGA_PROJECTILE);
		
		registerEntity("firefly", FIREFLY, EntityFirefly.getAttributeContainer(), 20, 2, 6, NETHER_BIOMES);
		registerEntity("hydrogen_jellyfish", HYDROGEN_JELLYFISH, EntityHydrogenJellyfish.getAttributeContainer(), 40, 1, 4, NETHER_BIOMES);
		registerEntity("naga", NAGA, EntityNaga.getAttributeContainer(), 20, 2, 4, NETHER_BIOMES);
	}
	
	public static void registerEntity(String name, EntityType<? extends LivingEntity> entity)
	{
		registerEntity(name, entity, MobEntity.createMobAttributes().build(), 0, 0, 0);
	}
	
	public static void registerEntity(String name, EntityType<? extends LivingEntity> entity, DefaultAttributeContainer container, int weight, int minGroupSize, int maxGroupSize, Biome... spawnBiomes)
	{
		Registry.register(Registry.ENTITY_TYPE, new Identifier(BetterNether.MOD_ID, name), entity);
		ATTRUBUTES.put(entity, container);
		if (spawnBiomes != null)
		{
			for (Biome b: spawnBiomes)
			{
				IBiome biome = (IBiome) b;
				biome.addEntitySpawn(entity, weight, minGroupSize, maxGroupSize);
			}
			NETHER_ENTITIES.add(entity);
		}
	}
	
	private static Biome[] getBiomes()
	{
		ArrayList<Biome> biomes = new ArrayList<Biome>(32);
		Iterator<Biome> iterator = Registry.BIOME.iterator();
		while (iterator.hasNext())
		{
			Biome biome = iterator.next();
			if (biome.getCategory() == Category.NETHER)
			{
				biomes.add(biome);	
			}
		}
		biomes.addAll(BiomesRegistry.getRegisteredBiomes());
		return biomes.toArray(new Biome[] {});
	}
	
	public static boolean isNetherEntity(Entity entity)
	{
		return NETHER_ENTITIES.contains(entity.getType());
	}
}
