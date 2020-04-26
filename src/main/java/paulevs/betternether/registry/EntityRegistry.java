package paulevs.betternether.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
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
	private static final Biome[] NETHER_BIOMES;
	
	public static final EntityType<EntityChair> CHAIR = FabricEntityTypeBuilder.create(EntityCategory.MISC, EntityChair::new).size(EntityDimensions.fixed(0.0F, 0.0F)).setImmuneToFire().build();
	public static final EntityType<EntityNagaProjectile> NAGA_PROJECTILE = FabricEntityTypeBuilder.create(EntityCategory.MISC, EntityNagaProjectile::new).size(EntityDimensions.fixed(1F, 1F)).disableSummon().build();
	
	public static final EntityType<EntityFirefly> FIREFLY = FabricEntityTypeBuilder.create(EntityCategory.CREATURE, EntityFirefly::new).size(EntityDimensions.fixed(0.5F, 0.5F)).setImmuneToFire().build();
	public static final EntityType<EntityHydrogenJellyfish> HYDROGEN_JELLYFISH = FabricEntityTypeBuilder.create(EntityCategory.CREATURE, EntityHydrogenJellyfish::new).size(EntityDimensions.changing(2F, 5F)).setImmuneToFire().build();
	public static final EntityType<EntityNaga> NAGA = FabricEntityTypeBuilder.create(EntityCategory.MONSTER, EntityNaga::new).size(EntityDimensions.fixed(0.625F, 2.75F)).setImmuneToFire().build();
	
	public static void register()
	{
		registerEntity("chair", CHAIR);
		registerEntity("naga_projectile", NAGA_PROJECTILE);
		
		registerEntity("firefly", FIREFLY, EntityFirefly.getAttributeContainer(), 5, 2, 6, NETHER_BIOMES);
		registerEntity("hydrogen_jellyfish", HYDROGEN_JELLYFISH, EntityHydrogenJellyfish.getAttributeContainer(), 100, 1, 4, NETHER_BIOMES);
		registerEntity("naga", NAGA, EntityNaga.getAttributeContainer(), 10, 2, 4, NETHER_BIOMES);
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
	
	static
	{
		ArrayList<Biome> biomes = new ArrayList<Biome>();
		Iterator<Biome> iterator = Registry.BIOME.iterator();
		while (iterator.hasNext())
		{
			Biome biome = iterator.next();
			if (biome.getCategory() == Category.NETHER)
			{
				biomes.add(biome);	
			}
		}
		NETHER_BIOMES = biomes.toArray(new Biome[] {});
		
		NETHER_ENTITIES.add(EntityType.GHAST);
		NETHER_ENTITIES.add(EntityType.ZOMBIFIED_PIGLIN);
	}
	
	public static boolean isNetherEntity(Entity entity)
	{
		return NETHER_ENTITIES.contains(entity.getType());
	}
}
