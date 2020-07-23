package paulevs.betternether;

import net.fabricmc.api.ModInitializer;
import paulevs.betternether.config.Config;
import paulevs.betternether.recipes.IntegrationRecipes;
import paulevs.betternether.recipes.ItemRecipes;
import paulevs.betternether.registry.BiomesRegistry;
import paulevs.betternether.registry.BlockEntitiesRegistry;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.registry.BrewingRegistry;
import paulevs.betternether.registry.EntityRegistry;
import paulevs.betternether.registry.ItemsRegistry;
import paulevs.betternether.registry.SoundsRegistry;
import paulevs.betternether.world.BNWorldGenerator;
import paulevs.betternether.world.structures.piece.StructureTypes;

public class BetterNether implements ModInitializer
{
	public static final String MOD_ID = "betternether";
	private static boolean thinArmor = true;
	private static boolean lavafallParticles = true;
	private static float fogStart = 0.05F;
	private static float fogEnd = 0.5F;

	@Override
	public void onInitialize()
	{
		initOptions();
		SoundsRegistry.register();
		StructureTypes.init();
		BlocksRegistry.register();
		BlockEntitiesRegistry.register();
		ItemsRegistry.register();
		BiomesRegistry.register();
		EntityRegistry.register();
		BNWorldGenerator.onModInit();
		BrewingRegistry.register();
		Config.save();
		
		IntegrationRecipes.register();
		NetherTags.register();
		ItemRecipes.register();
	}
	
	private void initOptions()
	{
		thinArmor = Config.getBoolean("improvement", "smaller_armor_offset", true);
		lavafallParticles = Config.getBoolean("improvement", "lavafall_particles", true);
		float density = Config.getFloat("improvement", "fog_density[vanilla: 1.0]", 0.75F);
		changeFogDensity(density);
	}
	
	public static boolean hasThinArmor()
	{
		return thinArmor;
	}
	
	public static void setThinArmor(boolean value)
	{
		thinArmor = value;
	}
	
	public static boolean hasLavafallParticles()
	{
		return lavafallParticles;
	}
	
	public static void changeFogDensity(float density)
	{
		fogStart = -0.45F * density + 0.5F;
		fogEnd = -0.5F * density + 1;
	}
	
	public static float getFogStart()
	{
		return fogStart;
	}
	
	public static float getFogEnd()
	{
		return fogEnd;
	}
}
