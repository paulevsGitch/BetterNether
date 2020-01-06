package paulevs.betternether.registers;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.BetterNether;
import paulevs.betternether.items.ItemBlackApple;

public class ItemsRegister
{
	public static final Item BLACK_APPLE = new ItemBlackApple();
	
	public static void register()
	{
		RegisterItem("black_apple", BLACK_APPLE);
	}
	
	public static void RegisterItem(String name, Item item)
	{
		Registry.register(Registry.ITEM, new Identifier(BetterNether.MOD_ID, name), item);
	}
}
