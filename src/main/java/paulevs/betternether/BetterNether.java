package paulevs.betternether;

import java.util.Iterator;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import paulevs.betternether.config.Config;
import paulevs.betternether.registers.BiomesRegister;
import paulevs.betternether.registers.BlockEntitiesRegister;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.registers.EntityRegister;
import paulevs.betternether.registers.ItemsRegister;
import paulevs.betternether.world.BNWorldGenerator;
import paulevs.betternether.world.structures.piece.StructureTypes;

public class BetterNether implements ModInitializer
{
	public static final String MOD_ID = "betternether";
	private static boolean thinArmor = true;
	private static float fogStart = 0.05F;
	private static float fogEnd = 0.5F;

	@Override
	public void onInitialize()
	{
		Config.load();
		initOptions();
		StructureTypes.init();
		BlocksRegister.register();
		BlockEntitiesRegister.register();
		ItemsRegister.register();
		BiomesRegister.register();
		BNWorldGenerator.onModInit();
		EntityRegister.register();
		Config.save();
		
		Iterator<Biome> iterator = Registry.BIOME.iterator();
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
		while (iterator.hasNext())
		{
			Biome biome = iterator.next();
			if (biome.getCategory() == Category.NETHER)
				System.out.println(biome.getName() + " " + biome.hashCode() + " " + (biome.equals(BiomesRegister.BIOME_GRASSLANDS)));
		}
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
	}
	
	private void initOptions()
	{
		thinArmor = Config.getBoolean("improvement", "smaller_armor_offset", true);
		float density = Config.getFloat("improvement", "fog_density", 1F);
		makeStart(density);
		makeEnd(density);
	}
	
	public static boolean hasThinArmor()
	{
		return thinArmor;
	}
	
	private void makeStart(float density)
	{
		fogStart = -0.45F * density + 0.5F;
	}
	
	private void makeEnd(float density)
	{
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
