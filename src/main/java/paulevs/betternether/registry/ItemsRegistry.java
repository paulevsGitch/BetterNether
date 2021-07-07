package paulevs.betternether.registry;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import paulevs.betternether.BetterNether;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.BlockProperties.FoodShape;
import paulevs.betternether.config.Configs;
import paulevs.betternether.integrations.VanillaExcavatorsIntegration;
import paulevs.betternether.integrations.VanillaHammersIntegration;
import paulevs.betternether.items.BNArmor;
import paulevs.betternether.items.BNItemAxe;
import paulevs.betternether.items.BNItemHoe;
import paulevs.betternether.items.BNItemPickaxe;
import paulevs.betternether.items.BNItemShovel;
import paulevs.betternether.items.BNSword;
import paulevs.betternether.items.ItemBlackApple;
import paulevs.betternether.items.ItemBowlFood;
import paulevs.betternether.items.materials.BNItemMaterials;
import paulevs.betternether.tab.CreativeTab;

public class ItemsRegistry {
	private static final List<String> ITEMS = new ArrayList<String>();
	public static final ArrayList<Item> MOD_BLOCKS = new ArrayList<Item>();
	public static final ArrayList<Item> MOD_ITEMS = new ArrayList<Item>();

	private static final int ALPHA = 255 << 24;

	public static final Item BLACK_APPLE = registerItem("black_apple", new ItemBlackApple());

	public static final Item STALAGNATE_BOWL = registerItem("stalagnate_bowl", new ItemBowlFood(null, FoodShape.NONE));
	public static final Item STALAGNATE_BOWL_WART = registerItem("stalagnate_bowl_wart", new ItemBowlFood(Foods.COOKED_CHICKEN, FoodShape.WART));
	public static final Item STALAGNATE_BOWL_MUSHROOM = registerItem("stalagnate_bowl_mushroom", new ItemBowlFood(Foods.MUSHROOM_STEW, FoodShape.MUSHROOM));
	public static final Item STALAGNATE_BOWL_APPLE = registerItem("stalagnate_bowl_apple", new ItemBowlFood(Foods.APPLE, FoodShape.APPLE));
	public static final Item HOOK_MUSHROOM = registerFood("hook_mushroom_cooked", 4, 0.4F);

	public static final Item CINCINNASITE = registerItem("cincinnasite", new Item(defaultSettings()));
	public static final Item CINCINNASITE_INGOT = registerItem("cincinnasite_ingot", new Item(defaultSettings()));

	public static final Item CINCINNASITE_PICKAXE = registerItem("cincinnasite_pickaxe", new BNItemPickaxe(BNItemMaterials.CINCINNASITE_TOOLS, 512, 1F));
	public static final Item CINCINNASITE_PICKAXE_DIAMOND = registerItem("cincinnasite_pickaxe_diamond", new BNItemPickaxe(BNItemMaterials.CINCINNASITE_DIAMOND_TOOLS, 2048, 1.5F));
	public static final Item CINCINNASITE_AXE = registerItem("cincinnasite_axe", new BNItemAxe(BNItemMaterials.CINCINNASITE_TOOLS, 512, 1F));
	public static final Item CINCINNASITE_AXE_DIAMOND = registerItem("cincinnasite_axe_diamond", new BNItemAxe(BNItemMaterials.CINCINNASITE_DIAMOND_TOOLS, 2048, 1.5F));
	public static final Item CINCINNASITE_SHOVEL = registerItem("cincinnasite_shovel", new BNItemShovel(BNItemMaterials.CINCINNASITE_TOOLS, 512, 1F));
	public static final Item CINCINNASITE_SHOVEL_DIAMOND = registerItem("cincinnasite_shovel_diamond", new BNItemShovel(BNItemMaterials.CINCINNASITE_DIAMOND_TOOLS, 2048, 1.5F));
	public static final Item CINCINNASITE_HOE = registerItem("cincinnasite_hoe", new BNItemHoe(BNItemMaterials.CINCINNASITE_TOOLS, 512, 1F));
	public static final Item CINCINNASITE_HOE_DIAMOND = registerItem("cincinnasite_hoe_diamond", new BNItemHoe(BNItemMaterials.CINCINNASITE_DIAMOND_TOOLS, 2048, 1.5F));
	public static final Item CINCINNASITE_SHEARS = registerItem("cincinnasite_shears", new ShearsItem(defaultSettings().durability(380)));

	public static final Item CINCINNASITE_HELMET = registerItem("cincinnasite_helmet", new BNArmor(BNItemMaterials.CINCINNASITE_ARMOR, EquipmentSlot.HEAD));
	public static final Item CINCINNASITE_CHESTPLATE = registerItem("cincinnasite_chestplate", new BNArmor(BNItemMaterials.CINCINNASITE_ARMOR, EquipmentSlot.CHEST));
	public static final Item CINCINNASITE_LEGGINGS = registerItem("cincinnasite_leggings", new BNArmor(BNItemMaterials.CINCINNASITE_ARMOR, EquipmentSlot.LEGS));
	public static final Item CINCINNASITE_BOOTS = registerItem("cincinnasite_boots", new BNArmor(BNItemMaterials.CINCINNASITE_ARMOR, EquipmentSlot.FEET));
	public static final Item CINCINNASITE_SWORD = registerItem("cincinnasite_sword", new BNSword(BNItemMaterials.CINCINNASITE_TOOLS, 512, 4, -2.4F));
	public static final Item CINCINNASITE_SWORD_DIAMOND = registerItem("cincinnasite_sword_diamond", new BNSword(BNItemMaterials.CINCINNASITE_DIAMOND_TOOLS, 2048, 5, -2.4F));

	public static final Item NETHER_RUBY = registerItem("nether_ruby", new Item(defaultSettings()));
	public static final Item NETHER_RUBY_PICKAXE = registerItem("nether_ruby_pickaxe", new BNItemPickaxe(BNItemMaterials.NETHER_RUBY_TOOLS, 512, 1F));
	public static final Item NETHER_RUBY_AXE = registerItem("nether_ruby_axe", new BNItemAxe(BNItemMaterials.NETHER_RUBY_TOOLS, 512, 1F));
	public static final Item NETHER_RUBY_SHOVEL = registerItem("nether_ruby_shovel", new BNItemShovel(BNItemMaterials.NETHER_RUBY_TOOLS, 512, 1F));
	public static final Item NETHER_RUBY_HOE = registerItem("nether_ruby_hoe", new BNItemHoe(BNItemMaterials.NETHER_RUBY_TOOLS, 512, 1F));
	public static final Item NETHER_RUBY_SWORD = registerItem("nether_ruby_sword", new BNSword(BNItemMaterials.NETHER_RUBY_TOOLS, 512, 4, -2.4F));
	public static final Item NETHER_RUBY_HELMET = registerItem("nether_ruby_helmet", new BNArmor(BNItemMaterials.NETHER_RUBY_ARMOR, EquipmentSlot.HEAD));
	public static final Item NETHER_RUBY_CHESTPLATE = registerItem("nether_ruby_chestplate", new BNArmor(BNItemMaterials.NETHER_RUBY_ARMOR, EquipmentSlot.CHEST));
	public static final Item NETHER_RUBY_LEGGINGS = registerItem("nether_ruby_leggings", new BNArmor(BNItemMaterials.NETHER_RUBY_ARMOR, EquipmentSlot.LEGS));
	public static final Item NETHER_RUBY_BOOTS = registerItem("nether_ruby_boots", new BNArmor(BNItemMaterials.NETHER_RUBY_ARMOR, EquipmentSlot.FEET));

	public static final Item CINCINNASITE_HAMMER = registerItem("cincinnasite_hammer", VanillaHammersIntegration.makeHammer(BNItemMaterials.CINCINNASITE_TOOLS, 4, -2.0F));
	public static final Item CINCINNASITE_HAMMER_DIAMOND = registerItem("cincinnasite_hammer_diamond", VanillaHammersIntegration.makeHammer(BNItemMaterials.CINCINNASITE_DIAMOND_TOOLS, 5, -2.0F));
	public static final Item NETHER_RUBY_HAMMER = registerItem("nether_ruby_hammer", VanillaHammersIntegration.makeHammer(BNItemMaterials.NETHER_RUBY_TOOLS, 5, -2.0F));

	public static final Item CINCINNASITE_EXCAVATOR = registerItem("cincinnasite_excavator", VanillaExcavatorsIntegration.makeExcavator(BNItemMaterials.CINCINNASITE_TOOLS, 4, -2.0F));
	public static final Item CINCINNASITE_EXCAVATOR_DIAMOND = registerItem("cincinnasite_excavator_diamond", VanillaExcavatorsIntegration.makeExcavator(BNItemMaterials.CINCINNASITE_DIAMOND_TOOLS, 5, -2.0F));
	public static final Item NETHER_RUBY_EXCAVATOR = registerItem("nether_ruby_excavator", VanillaExcavatorsIntegration.makeExcavator(BNItemMaterials.NETHER_RUBY_TOOLS, 5, -2.0F));

	public static final Item SPAWN_EGG_FIREFLY = registerItem("spawn_egg_firefly", makeEgg("firefly", EntityRegistry.FIREFLY, color(255, 223, 168), color(233, 182, 95)));
	public static final Item SPAWN_EGG_JELLYFISH = registerItem("spawn_egg_hydrogen_jellyfish", makeEgg("hydrogen_jellyfish", EntityRegistry.HYDROGEN_JELLYFISH, color(253, 164, 24), color(88, 21, 4)));
	public static final Item SPAWN_NAGA = registerItem("spawn_egg_naga", makeEgg("naga", EntityRegistry.NAGA, MHelper.color(12, 12, 12), MHelper.color(210, 90, 26)));
	public static final Item SPAWN_FLYING_PIG = registerItem("spawn_egg_flying_pig", makeEgg("flying_pig", EntityRegistry.FLYING_PIG, MHelper.color(241, 140, 93), MHelper.color(176, 58, 47)));
	public static final Item SPAWN_JUNGLE_SKELETON = registerItem("spawn_egg_jungle_skeleton", makeEgg("jungle_skeleton", EntityRegistry.JUNGLE_SKELETON, MHelper.color(134, 162, 149), MHelper.color(6, 111, 79)));
	public static final Item SPAWN_SKULL = registerItem("spawn_egg_skull", makeEgg("skull", EntityRegistry.SKULL, MHelper.color(24, 19, 19), MHelper.color(255, 28, 18)));

	public static final Item GLOWSTONE_PILE = registerItem("glowstone_pile", new Item(defaultSettings()));
	public static final Item LAPIS_PILE = registerItem("lapis_pile", new Item(defaultSettings()));

	public static final Item AGAVE_LEAF = registerItem("agave_leaf", new Item(defaultSettings()));
	public static final Item AGAVE_MEDICINE = registerMedicine("agave_medicine", 40, 2, true);
	public static final Item HERBAL_MEDICINE = registerMedicine("herbal_medicine", 10, 5, true);

	public static void register() {}

	public static Item registerItem(String name, Item item) {
		if ((item instanceof BlockItem || Configs.ITEMS.getBoolean("items", name, true)) && item != Items.AIR) {
			Registry.register(Registry.ITEM, new ResourceLocation(BetterNether.MOD_ID, name), item);
			if (item instanceof BlockItem)
				MOD_BLOCKS.add(item);
			else
				MOD_ITEMS.add(item);
		}
		if (!(item instanceof BlockItem))
			ITEMS.add(name);
		return item;
	}

	public static Item registerFood(String name, int hunger, float saturationMultiplier) {
		return registerItem(name, new Item(defaultSettings().food(new FoodProperties.Builder().nutrition(hunger).saturationMod(saturationMultiplier).build())));
	}

	public static Item registerMedicine(String name, int ticks, int power, boolean bowl) {
		if (bowl) {
			Item item = new Item(defaultSettings().stacksTo(16).food(new FoodProperties.Builder().effect(new MobEffectInstance(MobEffects.REGENERATION, ticks, power), 1).build())) {
				@Override
				public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
					if (stack.getCount() == 1) {
						super.finishUsingItem(stack, world, user);
						return new ItemStack(ItemsRegistry.STALAGNATE_BOWL, stack.getCount());
					}
					else {
						if (user instanceof Player) {
							Player player = (Player) user;
							if (!player.isCreative())
								player.addItem(new ItemStack(ItemsRegistry.STALAGNATE_BOWL));
						}
						return super.finishUsingItem(stack, world, user);
					}
				}
			};
			return registerItem(name, item);
		}
		return registerItem(name, new Item(defaultSettings().food(new FoodProperties.Builder().effect(new MobEffectInstance(MobEffects.REGENERATION, ticks, power), 1).build())));
	}

	public static Properties defaultSettings() {
		return new Item.Properties().tab(CreativeTab.BN_TAB);
	}

	private static Item makeEgg(String name, EntityType<? extends Mob> type, int background, int dots) {
		if (Configs.MOBS.getBoolean("mobs", name, true)) {
			SpawnEggItem item = new SpawnEggItem(type, background, dots, defaultSettings());
			DefaultDispenseItemBehavior behavior = new DefaultDispenseItemBehavior() {
				public ItemStack execute(BlockSource pointer, ItemStack stack) {
					Direction direction = (Direction) pointer.getBlockState().getValue(DispenserBlock.FACING);
					EntityType<?> entityType = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
					entityType.spawn(pointer.getLevel(), stack, (Player) null, pointer.getPos().relative(direction), MobSpawnType.DISPENSER, direction != Direction.UP, false);
					stack.shrink(1);
					return stack;
				}
			};
			DispenserBlock.registerBehavior(item, behavior);
			return item;
		}
		else {
			return Items.AIR;
		}
	}

	private static int color(int r, int g, int b) {
		return ALPHA | (r << 16) | (g << 8) | b;
	}

	public static List<String> getPossibleItems() {
		return ITEMS;
	}
}
