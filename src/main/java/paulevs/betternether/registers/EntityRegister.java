package paulevs.betternether.registers;

import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import paulevs.betternether.BetterNether;
import paulevs.betternether.IBiome;
import paulevs.betternether.entity.EntityChair;
import paulevs.betternether.entity.EntityFirefly;

public class EntityRegister
{
	public static final EntityType<EntityFirefly> FIREFLY = FabricEntityTypeBuilder.create(EntityCategory.CREATURE, EntityFirefly::new).size(EntityDimensions.fixed(0.5F, 0.5F)).setImmuneToFire().build();
	public static final EntityType<EntityChair> CHAIR = FabricEntityTypeBuilder.create(EntityCategory.MISC, EntityChair::new).size(EntityDimensions.fixed(0.0F, 0.0F)).setImmuneToFire().build();
	
	public static void register()
	{
		registerEntity("firefly", FIREFLY, 50, 2, 6, Biomes.NETHER);
		registerEntity("chair", CHAIR);
	}
	
	public static void registerEntity(String name, EntityType<?> entity)
	{
		registerEntity(name, entity, 0, 0, 0);
	}
	
	public static void registerEntity(String name, EntityType<?> entity, int weight, int minGroupSize, int maxGroupSize, Biome... spawnBiomes)
	{
		Registry.register(Registry.ENTITY_TYPE, new Identifier(BetterNether.MOD_ID, name), entity);
		if (spawnBiomes != null)
		{
			for (Biome b: spawnBiomes)
			{
				IBiome biome = (IBiome) b;
				biome.addEntitySpawn(entity, weight, minGroupSize, maxGroupSize);
			}
		}
	}
}
