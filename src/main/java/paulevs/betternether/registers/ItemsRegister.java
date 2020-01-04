package paulevs.betternether.registers;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.BetterNether;

public class ItemsRegister
{
	public static void register()
	{
		
	}
	
	public static void RegisterItem(String name, Item item)
	{
		Registry.register(Registry.ITEM, new Identifier(BetterNether.MOD_ID, name), item);
	}
}
