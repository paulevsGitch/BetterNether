package paulevs.betternether.registers;

import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blocks.shapes.FoodShape;
import paulevs.betternether.items.BNItemAxe;
import paulevs.betternether.items.BNItemPickaxe;
import paulevs.betternether.items.ItemBlackApple;
import paulevs.betternether.items.ItemBowlFood;
import paulevs.betternether.tab.CreativeTab;

public class ItemsRegister
{
	public static final Item BLACK_APPLE = new ItemBlackApple();
	public static final Item CINCINNASITE = new Item(defaultSettings());
    public static final Item CINCINNASITE_PICKAXE = new BNItemPickaxe(ToolMaterials.IRON, 512, 1F);
    public static final Item CINCINNASITE_PICKAXE_DIAMOND = new BNItemPickaxe(ToolMaterials.DIAMOND, 2048, 1.5F);
    public static final Item STALAGNATE_BOWL = new ItemBowlFood(0, FoodShape.NONE);
    public static final Item STALAGNATE_BOWL_WART = new ItemBowlFood(4, FoodShape.WART);
    public static final Item STALAGNATE_BOWL_MUSHROOM = new ItemBowlFood(6, FoodShape.MUSHROOM);
    public static final Item STALAGNATE_BOWL_APPLE = new ItemBowlFood(7, FoodShape.APPLE);
    public static final Item CINCINNASITE_AXE = new BNItemAxe(ToolMaterials.IRON, 512, 1F);
    public static final Item CINCINNASITE_AXE_DIAMOND = new BNItemAxe(ToolMaterials.DIAMOND, 2048, 1.5F);
	
	public static void register()
	{
		RegisterItem("black_apple", BLACK_APPLE);
		RegisterItem("cincinnasite", CINCINNASITE);
		RegisterItem("cincinnasite_pickaxe", CINCINNASITE_PICKAXE);
		RegisterItem("cincinnasite_pickaxe_diamond", CINCINNASITE_PICKAXE_DIAMOND);
		RegisterItem("stalagnate_bowl", STALAGNATE_BOWL);
		RegisterItem("stalagnate_bowl_wart", STALAGNATE_BOWL_WART);
		RegisterItem("stalagnate_bowl_mushroom", STALAGNATE_BOWL_MUSHROOM);
		RegisterItem("stalagnate_bowl_apple", STALAGNATE_BOWL_APPLE);
		RegisterItem("cincinnasite_axe", CINCINNASITE_AXE);
		RegisterItem("cincinnasite_axe_diamond", CINCINNASITE_AXE_DIAMOND);
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
