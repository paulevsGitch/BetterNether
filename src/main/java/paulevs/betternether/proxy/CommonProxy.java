package paulevs.betternether.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import paulevs.betternether.biomes.BiomeRegister;
import paulevs.betternether.blocks.BlocksRegister;
import paulevs.betternether.config.ConfigLoader;
import paulevs.betternether.entities.EntityRegister;
import paulevs.betternether.events.EventsHandler;
import paulevs.betternether.items.ItemsRegister;
import paulevs.betternether.recipes.RecipeRegister;
import paulevs.betternether.sounds.SoundRegister;
import paulevs.betternether.tileentities.TileEntityRegister;
import paulevs.betternether.world.BNWorldGenerator;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent event)
	{
		ConfigLoader.load(event.getSuggestedConfigurationFile());
		EventsHandler.init();
		BlocksRegister.register();
		BlocksRegister.registerOreDictionary();
		ItemsRegister.register();
		BiomeRegister.registerBiomes();
		ConfigLoader.postBiomeInit();
		RecipeRegister.register();
		SoundRegister.register();
		EntityRegister.register();
		TileEntityRegister.register();
		MinecraftForge.EVENT_BUS.register(new EventsHandler());
	}

	public void init(FMLInitializationEvent event)
	{

	}

	public void postInit(FMLPostInitializationEvent event)
	{
		BNWorldGenerator.updateGenSettings();
		ConfigLoader.dispose();
	}
}