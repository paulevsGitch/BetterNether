package paulevs.betternether.integrations;

import java.lang.reflect.Constructor;

import org.apache.logging.log4j.LogManager;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import paulevs.betternether.registry.ItemsRegistry;

public class VanillaExcavatorsIntegration
{
	private static boolean hasExcavators;
	private static Constructor<?> excavatorConstructor;
	
	public static Item makeExcavator(ToolMaterial material, int attackDamage, float attackSpeed)
	{
		if (!hasExcavators)
			return Items.AIR;
		try
		{
			return (Item) excavatorConstructor.newInstance(material, attackDamage, attackSpeed, ItemsRegistry.defaultSettings());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return Items.AIR;
		}
	}
	
	static
	{
		hasExcavators = FabricLoader.getInstance().isModLoaded("vanillaexcavators");
		try
		{
			if (hasExcavators)
			{
				LogManager.getLogger().info("Enabled Vanilla Hammers Integration");
				Class<?> itemClass = Class.forName("draylar.magna.item.ExcavatorItem");
				if (itemClass != null)
					for (Constructor<?> c: itemClass.getConstructors())
						if (c.getParameterCount() == 4)
						{
							excavatorConstructor = c;
							break;
						}
				hasExcavators = (excavatorConstructor != null);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static boolean hasExcavators()
	{
		return hasExcavators;
	}
}
