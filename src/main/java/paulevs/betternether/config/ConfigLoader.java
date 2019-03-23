package paulevs.betternether.config;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import paulevs.betternether.biomes.BiomeRegister;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.blocks.BlocksRegister;
import paulevs.betternether.items.ItemsRegister;
import paulevs.betternether.world.BNWorldGenerator;

public class ConfigLoader
{
	private static Configuration config;
	
	private static boolean[] registerBiomes;
	//private static boolean[] registerBlocks;
	private static boolean[] registerItems;
	
	private static int indexBiome;
	//private static int indexBlock;
	private static int indexItems;
	
	private static int biomeSizeXZ;
	private static int biomeSizeY;
	
	private static Map<String, Boolean> registerBlocks;
	
	private static boolean hasCleaningPass;
	
	public static void load(File file)
	{
		List<Boolean> items= new ArrayList<Boolean>();
		config = new Configuration(file);
		config.load();
		//Biome Size and Pass
		biomeSizeXZ = config.getInt("BiomeSizeXZ", "Generator", 100, 1, 4096, "Defines size in horisontal space");
		biomeSizeY = config.getInt("BiomeSizeY", "Generator", 32, 1, 4096, "Defines size in vertical space");
		hasCleaningPass = config.getBoolean("SecondPass", "Generator", true, "Enables|Disables second pass for smooth terrain");
		
		// Biomes
		for (Field f : BiomeRegister.class.getDeclaredFields())
			if (f.getType().isAssignableFrom(NetherBiome.class))
				items.add(config.getBoolean(f.getName().toLowerCase(), "Biomes", true, "Enables|Disables biome"));
		registerBiomes = new boolean[items.size()];
		for (int i = 0; i < items.size(); i++)
			registerBiomes[i] = items.get(i);
		items.clear();
		
		// Blocks
		registerBlocks = new HashMap<String, Boolean>();
		for (Field f : BlocksRegister.class.getDeclaredFields())
			if (f.getType().isAssignableFrom(Block.class))
				registerBlocks.put(f.getName().toLowerCase(), config.getBoolean(f.getName().toLowerCase(), "Blocks", true, "Enables|Disables block"));
				//items.add(config.getBoolean(f.getName().toLowerCase(), "Blocks", true, "Enables|Disables block"));
		//registerBlocks = new boolean[items.size()];
		
		//for (int i = 0; i < items.size(); i++)
			//registerBlocks[i] = items.get(i);
			
		//items.clear();
		
		//Items
		for (Field f : ItemsRegister.class.getDeclaredFields())
			if (f.getType().isAssignableFrom(Item.class))
				items.add(config.getBoolean(f.getName().toLowerCase(), "Items", true, "Enables|Disables item"));
		registerItems = new boolean[items.size()];
		for (int i = 0; i < items.size(); i++)
			registerItems[i] = items.get(i);
		items.clear();
		items = null;
		
		//Damage
		BNWorldGenerator.enablePlayerDamage = config.getBoolean("DamagePlayer", "EggplantDamage", true, "Damage for players");
		BNWorldGenerator.enableMobDamage = config.getBoolean("DamageMobs", "EggplantDamage", true, "Damage for mobs");
		
		resetBiomeIndex();
		//resetBlockIndex();
		resetItemIndex();
	}
	
	public static void postBiomeInit()
	{
		//Plant density
		BNWorldGenerator.setDensity(config.getFloat("GlobalDensity", "Generator", 1, 0, 1, "Global plant density, multiplied on other"));
		for (NetherBiome biome : BiomeRegister.getBiomes())
		{
			biome.setDensity(config.getFloat(biome.getName().replace(" ", "") + "Density", "Generator", 1, 0, 1, "Density for " + biome.getName() + " biome"));
		}
	}
	
	public static boolean mustInitBiome()
	{
		return registerBiomes[indexBiome++];
	}
	
	public static void resetBiomeIndex()
	{
		indexBiome = 0;
	}
	
	public static boolean mustInitBlock(Field field)
	{
		String s = field.getName().toLowerCase();
		return registerBlocks.containsKey(s) && registerBlocks.get(s);
	}
	
	public static boolean mustInitBlock(String key)
	{
		String s = key.toLowerCase();
		return registerBlocks.containsKey(s) && registerBlocks.get(s);
	}
	
	/*public static void resetBlockIndex()
	{
		indexBlock = 0;
	}*/
	
	public static boolean mustInitItem()
	{
		return registerItems[indexItems++];
	}
	
	public static void resetItemIndex()
	{
		indexItems = 0;
	}
	
	public static void dispose()
	{
		config.save();
		registerBlocks = null;
		registerBiomes = null;
		registerItems = null;
	}
	
	public static int getBiomeSizeXZ()
	{
		return biomeSizeXZ;
	}
	
	public static int getBiomeSizeY()
	{
		return biomeSizeY;
	}
	
	public static boolean hasCleaningPass()
	{
		return hasCleaningPass;
	}
}
