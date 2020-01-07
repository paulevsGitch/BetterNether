package paulevs.betternether.registers;

import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.BetterNether;
import paulevs.betternether.items.BNItemPickaxe;
import paulevs.betternether.items.ItemBlackApple;
import paulevs.betternether.tab.CreativeTab;

public class ItemsRegister
{
	public static final Item BLACK_APPLE = new ItemBlackApple();
	public static final Item CINCINNASITE = new Item(defaultSettings());
    public static final Item CINCINNASITE_PICKAXE = new BNItemPickaxe(ToolMaterials.IRON, 512, 1F);
    public static final Item CINCINNASITE_PICKAXE_DIAMOND = new BNItemPickaxe(ToolMaterials.DIAMOND, 2048, 1.5F);
    /*public static final Item STALAGNATE_BOWL;
    public static final Item STALAGNATE_BOWL_WART;
    public static final Item STALAGNATE_BOWL_MUSHROOM;
    public static final Item STALAGNATE_BOWL_APPLE;
    public static final Item CINCINNASITE_AXE;
    public static final Item CINCINNASITE_AXE_DIAMOND;*/
	
	public static void register()
	{
		RegisterItem("black_apple", BLACK_APPLE);
		RegisterItem("cincinnasite", CINCINNASITE);
		RegisterItem("cincinnasite_pickaxe", CINCINNASITE_PICKAXE);
		RegisterItem("cincinnasite_pickaxe_diamond", CINCINNASITE_PICKAXE_DIAMOND);
	}
	
	public static void RegisterItem(String name, Item item)
	{
		Registry.register(Registry.ITEM, new Identifier(BetterNether.MOD_ID, name), item);
	}
	
	public static Settings defaultSettings()
	{
		return new Item.Settings().group(CreativeTab.BN_TAB);
	}
}
