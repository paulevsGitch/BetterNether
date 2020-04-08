package paulevs.betternether.registry;

import java.util.ArrayList;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.BetterNether;
import paulevs.betternether.VanillaHammersIntegration;
import paulevs.betternether.blocks.shapes.FoodShape;
import paulevs.betternether.config.Config;
import paulevs.betternether.items.BNArmor;
import paulevs.betternether.items.BNItemAxe;
import paulevs.betternether.items.BNItemPickaxe;
import paulevs.betternether.items.BNItemShovel;
import paulevs.betternether.items.BNSword;
import paulevs.betternether.items.ItemBlackApple;
import paulevs.betternether.items.ItemBowlFood;
import paulevs.betternether.items.materials.BNItemMaterials;
import paulevs.betternether.tab.CreativeTab;

public class ItemsRegistry
{
	public static final ArrayList<Item> MOD_BLOCKS = new ArrayList<Item>();
	public static final ArrayList<Item> MOD_ITEMS = new ArrayList<Item>();
	
	private static final int ALPHA = 255 << 24;
	
	public static final Item BLACK_APPLE = registerItem("black_apple", new ItemBlackApple());
	
	public static final Item STALAGNATE_BOWL = registerItem("stalagnate_bowl", new ItemBowlFood(0, FoodShape.NONE));
	public static final Item STALAGNATE_BOWL_WART = registerItem("stalagnate_bowl_wart", new ItemBowlFood(4, FoodShape.WART));
	public static final Item STALAGNATE_BOWL_MUSHROOM = registerItem("stalagnate_bowl_mushroom", new ItemBowlFood(6, FoodShape.MUSHROOM));
	public static final Item STALAGNATE_BOWL_APPLE = registerItem("stalagnate_bowl_apple", new ItemBowlFood(7, FoodShape.APPLE));
	
	public static final Item CINCINNASITE = registerItem("cincinnasite", new Item(defaultSettings()));
	public static final Item CINCINNASITE_INGOT = registerItem("cincinnasite_ingot", new Item(defaultSettings()));
	
	public static final Item CINCINNASITE_PICKAXE = registerItem("cincinnasite_pickaxe", new BNItemPickaxe(BNItemMaterials.CINCINNASITE_TOOLS, 512, 1F));
	public static final Item CINCINNASITE_PICKAXE_DIAMOND = registerItem("cincinnasite_pickaxe_diamond", new BNItemPickaxe(BNItemMaterials.CINCINNASITE_DIAMOND_TOOLS, 2048, 1.5F));
	public static final Item CINCINNASITE_AXE = registerItem("cincinnasite_axe", new BNItemAxe(BNItemMaterials.CINCINNASITE_TOOLS, 512, 1F));
	public static final Item CINCINNASITE_AXE_DIAMOND = registerItem("cincinnasite_axe_diamond", new BNItemAxe(BNItemMaterials.CINCINNASITE_DIAMOND_TOOLS, 2048, 1.5F));
	public static final Item CINCINNASITE_SHOVEL = registerItem("cincinnasite_shovel", new BNItemShovel(BNItemMaterials.CINCINNASITE_TOOLS, 512, 1F));
	public static final Item CINCINNASITE_SHOVEL_DIAMOND = registerItem("cincinnasite_shovel_diamond", new BNItemShovel(BNItemMaterials.CINCINNASITE_DIAMOND_TOOLS, 2048, 1.5F));
	public static final Item CINCINNASITE_HAMMER = registerItem("cincinnasite_hammer", VanillaHammersIntegration.makeHammer(BNItemMaterials.CINCINNASITE_TOOLS, 4, -2.0F));
	public static final Item CINCINNASITE_HAMMER_DIAMOND = registerItem("cincinnasite_hammer_diamond", VanillaHammersIntegration.makeHammer(BNItemMaterials.CINCINNASITE_DIAMOND_TOOLS, 5, -2.0F));
	
	public static final Item CINCINNASITE_HELMET = registerItem("cincinnasite_helmet", new BNArmor(BNItemMaterials.CINCINNASITE_ARMOR, EquipmentSlot.HEAD));
	public static final Item CINCINNASITE_CHESTPLATE = registerItem("cincinnasite_chestplate", new BNArmor(BNItemMaterials.CINCINNASITE_ARMOR, EquipmentSlot.CHEST));
	public static final Item CINCINNASITE_LEGGINGS = registerItem("cincinnasite_leggings", new BNArmor(BNItemMaterials.CINCINNASITE_ARMOR, EquipmentSlot.LEGS));
	public static final Item CINCINNASITE_BOOTS = registerItem("cincinnasite_boots", new BNArmor(BNItemMaterials.CINCINNASITE_ARMOR, EquipmentSlot.FEET));
	public static final Item CINCINNASITE_SWORD = registerItem("cincinnasite_sword", new BNSword(BNItemMaterials.CINCINNASITE_TOOLS, 512, 4, -2.4F));
	public static final Item CINCINNASITE_SWORD_DIAMOND = registerItem("cincinnasite_sword_diamond", new BNSword(BNItemMaterials.CINCINNASITE_DIAMOND_TOOLS, 2048, 5, -2.4F));
	
	public static final Item SPAWN_EGG_FIREFLY = registerItem("spawn_egg_firefly", makeEgg(EntityRegistry.FIREFLY, color(255, 223, 168), color(233, 182, 95)));
	public static final Item SPAWN_EGG_JELLYFISH = registerItem("spawn_egg_hydrogen_jellyfish", makeEgg(EntityRegistry.HYDROGEN_JELLYFISH, color(253, 164, 24), color(88, 21, 4)));
	
	public static void register() {}
	
	public static Item registerItem(String name, Item item)
	{
		if (Config.getBoolean("items", name, true) && item != Items.AIR)
		{
			Registry.register(Registry.ITEM, new Identifier(BetterNether.MOD_ID, name), item);
			if (item instanceof BlockItem)
				MOD_BLOCKS.add(item);
			else
				MOD_ITEMS.add(item);
		}
		return item;
	}

	public static Settings defaultSettings()
	{
		return new Item.Settings().group(CreativeTab.BN_TAB);
	}
	
	private static SpawnEggItem makeEgg(EntityType<?> type, int background, int dots)
	{
		return new SpawnEggItem(type, background, dots, defaultSettings());
	}
	
	private static int color(int r, int g, int b)
	{
		return ALPHA | (r << 16) | (g << 8) | b;
	}
}
