package paulevs.betternether.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import paulevs.betternether.BetterNether;

public class EntityRegister
{
	private static int id = 0;
	
	public static void register()
	{
		RegisterEntity("firefly", EntityFirefly.class);
		EntityRegistry.addSpawn(EntityFirefly.class, 100, 5, 10, EnumCreatureType.AMBIENT, Biomes.HELL);
	}
	
	private static void RegisterEntity(String name, Class<? extends Entity> entityClass)
	{
		EntityRegistry.registerModEntity(
				new ResourceLocation("betternether", name),
				entityClass,
				name,
				id++,
				BetterNether.getMod(),
				128,
				2,
				true,
				3121381,
				3140052);
	}
}
