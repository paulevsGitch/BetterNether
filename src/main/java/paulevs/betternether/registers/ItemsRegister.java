package paulevs.betternether.registers;

import java.awt.Color;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blocks.shapes.FoodShape;
import paulevs.betternether.items.BNArmor;
import paulevs.betternether.items.BNItemAxe;
import paulevs.betternether.items.BNItemPickaxe;
import paulevs.betternether.items.BNItemShovel;
import paulevs.betternether.items.BNSword;
import paulevs.betternether.items.ItemBlackApple;
import paulevs.betternether.items.ItemBowlFood;
import paulevs.betternether.items.materials.BNItemMaterials;
import paulevs.betternether.tab.CreativeTab;

public class ItemsRegister
{
	public static final Item BLACK_APPLE = new ItemBlackApple();
	public static final Item CINCINNASITE = new Item(defaultSettings());
	public static final Item CINCINNASITE_PICKAXE = new BNItemPickaxe(BNItemMaterials.CINCINNASITE_TOOLS, 512, 1F);
	public static final Item CINCINNASITE_PICKAXE_DIAMOND = new BNItemPickaxe(BNItemMaterials.CINCINNASITE_DIAMOND_TOOLS, 2048, 1.5F);
	public static final Item STALAGNATE_BOWL = new ItemBowlFood(0, FoodShape.NONE);
	public static final Item STALAGNATE_BOWL_WART = new ItemBowlFood(4, FoodShape.WART);
	public static final Item STALAGNATE_BOWL_MUSHROOM = new ItemBowlFood(6, FoodShape.MUSHROOM);
	public static final Item STALAGNATE_BOWL_APPLE = new ItemBowlFood(7, FoodShape.APPLE);
	public static final Item CINCINNASITE_AXE = new BNItemAxe(BNItemMaterials.CINCINNASITE_TOOLS, 512, 1F);
	public static final Item CINCINNASITE_AXE_DIAMOND = new BNItemAxe(BNItemMaterials.CINCINNASITE_DIAMOND_TOOLS, 2048, 1.5F);
	public static final Item CINCINNASITE_SHOVEL = new BNItemShovel(BNItemMaterials.CINCINNASITE_TOOLS, 512, 1F);
	public static final Item CINCINNASITE_SHOVEL_DIAMOND = new BNItemShovel(BNItemMaterials.CINCINNASITE_DIAMOND_TOOLS, 2048, 1.5F);
	public static final Item CINCINNASITE_HELMET = new BNArmor(BNItemMaterials.CINCINNASITE_ARMOR, EquipmentSlot.HEAD);
	public static final Item CINCINNASITE_CHESTPLATE = new BNArmor(BNItemMaterials.CINCINNASITE_ARMOR, EquipmentSlot.CHEST);
	public static final Item CINCINNASITE_LEGGINGS = new BNArmor(BNItemMaterials.CINCINNASITE_ARMOR, EquipmentSlot.LEGS);
	public static final Item CINCINNASITE_BOOTS = new BNArmor(BNItemMaterials.CINCINNASITE_ARMOR, EquipmentSlot.FEET);
	public static final Item CINCINNASITE_SWORD = new BNSword(BNItemMaterials.CINCINNASITE_TOOLS, 512, 4, -2.4F);
	public static final Item CINCINNASITE_SWORD_DIAMOND = new BNSword(BNItemMaterials.CINCINNASITE_DIAMOND_TOOLS, 2048, 5, -2.4F);
	public static final Item SPAWN_EGG_FIREFLY = makeEgg(EntityRegister.FIREFLY, new Color(255, 223, 168), new Color(233, 182, 95));
	public static final Item CINCINNASITE_INGOT = new Item(defaultSettings());

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
		RegisterItem("cincinnasite_shovel", CINCINNASITE_SHOVEL);
		RegisterItem("cincinnasite_shovel_diamond", CINCINNASITE_SHOVEL_DIAMOND);
		RegisterItem("cincinnasite_helmet", CINCINNASITE_HELMET);
		RegisterItem("cincinnasite_chestplate", CINCINNASITE_CHESTPLATE);
		RegisterItem("cincinnasite_leggings", CINCINNASITE_LEGGINGS);
		RegisterItem("cincinnasite_boots", CINCINNASITE_BOOTS);
		RegisterItem("cincinnasite_sword", CINCINNASITE_SWORD);
		RegisterItem("cincinnasite_sword_diamond", CINCINNASITE_SWORD_DIAMOND);
		RegisterItem("spawn_egg_firefly", SPAWN_EGG_FIREFLY);
		RegisterItem("cincinnasite_ingot", CINCINNASITE_INGOT);
	}

	public static void RegisterItem(String name, Item item)
	{
		Registry.register(Registry.ITEM, new Identifier(BetterNether.MOD_ID, name), item);
	}

	public static Settings defaultSettings()
	{
		return new Item.Settings().group(CreativeTab.BN_TAB);
	}
	
	private static SpawnEggItem makeEgg(EntityType<?> type, Color background, Color dots)
	{
		return new SpawnEggItem(type, background.getRGB(), dots.getRGB(), defaultSettings());
	}
}
