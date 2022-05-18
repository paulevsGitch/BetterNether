package org.betterx.betternether.registry;

import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

import org.betterx.bclib.api.tag.CommonItemTags;
import org.betterx.bclib.api.tag.TagAPI;
import org.betterx.bclib.items.tool.BaseShearsItem;
import org.betterx.bclib.registry.ItemRegistry;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blocks.BlockProperties.FoodShape;
import org.betterx.betternether.config.Configs;
import org.betterx.betternether.integrations.VanillaExcavatorsIntegration;
import org.betterx.betternether.integrations.VanillaHammersIntegration;
import org.betterx.betternether.items.*;
import org.betterx.betternether.items.materials.BNArmorMaterial;
import org.betterx.betternether.items.materials.BNToolMaterial;
import org.betterx.betternether.tab.CreativeTabs;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class NetherItems extends ItemRegistry {
    private static final List<String> ITEMS = new ArrayList<String>();
    public static final ArrayList<Item> MOD_BLOCKS = new ArrayList<Item>();
    public static final ArrayList<Item> MOD_ITEMS = new ArrayList<Item>();

    public static final Item BLACK_APPLE = registerItem("black_apple", new ItemBlackApple());

    public static final Item STALAGNATE_BOWL = registerItem("stalagnate_bowl", new ItemBowlFood(null, FoodShape.NONE));
    public static final Item STALAGNATE_BOWL_WART = registerItem("stalagnate_bowl_wart",
                                                                 new ItemBowlFood(Foods.COOKED_CHICKEN,
                                                                                  FoodShape.WART));
    public static final Item STALAGNATE_BOWL_MUSHROOM = registerItem("stalagnate_bowl_mushroom",
                                                                     new ItemBowlFood(Foods.MUSHROOM_STEW,
                                                                                      FoodShape.MUSHROOM));
    public static final Item STALAGNATE_BOWL_APPLE = registerItem("stalagnate_bowl_apple",
                                                                  new ItemBowlFood(Foods.APPLE, FoodShape.APPLE));
    public static final Item HOOK_MUSHROOM = registerFood("hook_mushroom_cooked", 4, 0.4F);

    public static final Item CINCINNASITE = registerItem("cincinnasite", new Item(defaultSettings()));
    public static final Item CINCINNASITE_INGOT = registerItem("cincinnasite_ingot", new Item(defaultSettings()),
                                                               CommonItemTags.IRON_INGOTS);

    public static final Item CINCINNASITE_PICKAXE = registerTool("cincinnasite_pickaxe", new NetherPickaxe(
            BNToolMaterial.CINCINNASITE));
    public static final Item CINCINNASITE_PICKAXE_DIAMOND = registerTool("cincinnasite_pickaxe_diamond",
                                                                         new NetherPickaxe(BNToolMaterial.CINCINNASITE_DIAMOND));
    public static final Item CINCINNASITE_AXE = registerTool("cincinnasite_axe",
                                                             new NetherAxe(BNToolMaterial.CINCINNASITE));
    public static final Item CINCINNASITE_AXE_DIAMOND = registerTool("cincinnasite_axe_diamond",
                                                                     new NetherAxe(BNToolMaterial.CINCINNASITE_DIAMOND));
    public static final Item CINCINNASITE_SHOVEL = registerTool("cincinnasite_shovel",
                                                                new NetherShovel(BNToolMaterial.CINCINNASITE));
    public static final Item CINCINNASITE_SHOVEL_DIAMOND = registerTool("cincinnasite_shovel_diamond",
                                                                        new NetherShovel(BNToolMaterial.CINCINNASITE_DIAMOND));
    public static final Item CINCINNASITE_HOE = registerTool("cincinnasite_hoe",
                                                             new NetherHoe(BNToolMaterial.CINCINNASITE));
    public static final Item CINCINNASITE_HOE_DIAMOND = registerTool("cincinnasite_hoe_diamond",
                                                                     new NetherHoe(BNToolMaterial.CINCINNASITE_DIAMOND));
    public static final Item CINCINNASITE_SHEARS = registerShears("cincinnasite_shears",
                                                                  new BaseShearsItem(defaultSettings().durability(380)));

    public static final Item CINCINNASITE_HELMET = registerItem("cincinnasite_helmet",
                                                                new NetherArmor(BNArmorMaterial.CINCINNASITE,
                                                                                EquipmentSlot.HEAD));
    public static final Item CINCINNASITE_CHESTPLATE = registerItem("cincinnasite_chestplate",
                                                                    new NetherArmor(BNArmorMaterial.CINCINNASITE,
                                                                                    EquipmentSlot.CHEST));
    public static final Item CINCINNASITE_LEGGINGS = registerItem("cincinnasite_leggings",
                                                                  new NetherArmor(BNArmorMaterial.CINCINNASITE,
                                                                                  EquipmentSlot.LEGS));
    public static final Item CINCINNASITE_BOOTS = registerItem("cincinnasite_boots",
                                                               new NetherArmor(BNArmorMaterial.CINCINNASITE,
                                                                               EquipmentSlot.FEET));
    public static final Item CINCINNASITE_SWORD = registerItem("cincinnasite_sword",
                                                               new NetherSword(BNToolMaterial.CINCINNASITE, 3, -2.4F));
    public static final Item CINCINNASITE_SWORD_DIAMOND = registerItem("cincinnasite_sword_diamond",
                                                                       new NetherSword(BNToolMaterial.CINCINNASITE_DIAMOND,
                                                                                       3,
                                                                                       -2.4F));

    public static final Item NETHER_RUBY = registerItem("nether_ruby", new Item(defaultSettings()));
    public static final Item NETHER_RUBY_PICKAXE = registerTool("nether_ruby_pickaxe",
                                                                new NetherPickaxe(BNToolMaterial.NETHER_RUBY));
    public static final Item NETHER_RUBY_AXE = registerTool("nether_ruby_axe",
                                                            new NetherAxe(BNToolMaterial.NETHER_RUBY));
    public static final Item NETHER_RUBY_SHOVEL = registerTool("nether_ruby_shovel",
                                                               new NetherShovel(BNToolMaterial.NETHER_RUBY));
    public static final Item NETHER_RUBY_HOE = registerTool("nether_ruby_hoe",
                                                            new NetherHoe(BNToolMaterial.NETHER_RUBY));
    public static final Item NETHER_RUBY_SWORD = registerItem("nether_ruby_sword",
                                                              new NetherSword(BNToolMaterial.NETHER_RUBY, 3, -2.4F));
    public static final Item NETHER_RUBY_HELMET = registerItem("nether_ruby_helmet",
                                                               new NetherArmor(BNArmorMaterial.NETHER_RUBY,
                                                                               EquipmentSlot.HEAD));
    public static final Item NETHER_RUBY_CHESTPLATE = registerItem("nether_ruby_chestplate",
                                                                   new NetherArmor(BNArmorMaterial.NETHER_RUBY,
                                                                                   EquipmentSlot.CHEST));
    public static final Item NETHER_RUBY_LEGGINGS = registerItem("nether_ruby_leggings",
                                                                 new NetherArmor(BNArmorMaterial.NETHER_RUBY,
                                                                                 EquipmentSlot.LEGS));
    public static final Item NETHER_RUBY_BOOTS = registerItem("nether_ruby_boots",
                                                              new NetherArmor(BNArmorMaterial.NETHER_RUBY,
                                                                              EquipmentSlot.FEET));

    public static final Item CINCINNASITE_HAMMER = registerItem("cincinnasite_hammer",
                                                                VanillaHammersIntegration.makeHammer(BNToolMaterial.CINCINNASITE,
                                                                                                     4,
                                                                                                     -2.0F));
    public static final Item CINCINNASITE_HAMMER_DIAMOND = registerItem("cincinnasite_hammer_diamond",
                                                                        VanillaHammersIntegration.makeHammer(
                                                                                BNToolMaterial.CINCINNASITE_DIAMOND,
                                                                                5,
                                                                                -2.0F));
    public static final Item NETHER_RUBY_HAMMER = registerItem("nether_ruby_hammer",
                                                               VanillaHammersIntegration.makeHammer(BNToolMaterial.NETHER_RUBY,
                                                                                                    5,
                                                                                                    -2.0F));

    public static final Item CINCINNASITE_EXCAVATOR = registerItem("cincinnasite_excavator",
                                                                   VanillaExcavatorsIntegration.makeExcavator(
                                                                           BNToolMaterial.CINCINNASITE,
                                                                           4,
                                                                           -2.0F));
    public static final Item CINCINNASITE_EXCAVATOR_DIAMOND = registerItem("cincinnasite_excavator_diamond",
                                                                           VanillaExcavatorsIntegration.makeExcavator(
                                                                                   BNToolMaterial.CINCINNASITE_DIAMOND,
                                                                                   5,
                                                                                   -2.0F));
    public static final Item NETHER_RUBY_EXCAVATOR = registerItem("nether_ruby_excavator",
                                                                  VanillaExcavatorsIntegration.makeExcavator(
                                                                          BNToolMaterial.NETHER_RUBY,
                                                                          5,
                                                                          -2.0F));

    public static final Item GLOWSTONE_PILE = registerItem("glowstone_pile", new Item(defaultSettings()));
    public static final Item LAPIS_PILE = registerItem("lapis_pile", new Item(defaultSettings()));

    public static final Item AGAVE_LEAF = registerItem("agave_leaf", new Item(defaultSettings()));
    public static final Item AGAVE_MEDICINE = registerMedicine("agave_medicine", 40, 2, true);
    public static final Item HERBAL_MEDICINE = registerMedicine("herbal_medicine", 10, 5, true);

    protected NetherItems(CreativeModeTab creativeTab) {
        super(creativeTab, Configs.ITEMS);
    }

    private static ItemRegistry ITEMS_REGISTRY;

    @NotNull
    public static ItemRegistry getItemRegistry() {
        if (ITEMS_REGISTRY == null) {
            ITEMS_REGISTRY = new NetherItems(CreativeTabs.TAB_ITEMS);
        }
        return ITEMS_REGISTRY;
    }

    public static List<Item> getModItems() {
        return getModItems(BetterNether.MOD_ID);
    }

    public static Item registerShears(String name, Item item) {
        if (item != Items.AIR) {
            return getItemRegistry().registerTool(BetterNether.makeID(name), item);
        }

        return item;
    }

    public static Item registerTool(String name, Item item, TagKey<Item>... tags) {
        if (item != Items.AIR) {
            getItemRegistry().registerTool(BetterNether.makeID(name), item);
            if (tags.length > 0)
                TagAPI.addItemTags(item, tags);

            MOD_ITEMS.add(item);
        }

        ITEMS.add(name);
        return item;
    }

    public static Item registerItem(String name, Item item, TagKey<Item>... tags) {
        if ((item instanceof BlockItem || Configs.ITEMS.getBoolean("items", name, true)) && item != Items.AIR) {
            getItemRegistry().register(BetterNether.makeID(name), item);
            //item = Registry.register(Registry.ITEM, new ResourceLocation(BetterNether.MOD_ID, name), item);
            if (tags.length > 0)
                TagAPI.addItemTags(item, tags);

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
        return registerItem(name,
                            new Item(defaultSettings().food(new FoodProperties.Builder().nutrition(hunger)
                                                                                        .saturationMod(
                                                                                                saturationMultiplier)
                                                                                        .build())));
    }

    public static Item registerMedicine(String name, int ticks, int power, boolean bowl) {
        if (bowl) {
            Item item = new Item(defaultSettings().stacksTo(16)
                                                  .food(new FoodProperties.Builder().effect(new MobEffectInstance(
                                                          MobEffects.REGENERATION,
                                                          ticks,
                                                          power), 1).build())) {
                @Override
                public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
                    if (stack.getCount() == 1) {
                        super.finishUsingItem(stack, world, user);
                        return new ItemStack(NetherItems.STALAGNATE_BOWL, stack.getCount());
                    } else {
                        if (user instanceof Player player) {
                            if (!player.isCreative())
                                player.addItem(new ItemStack(NetherItems.STALAGNATE_BOWL));
                        }
                        return super.finishUsingItem(stack, world, user);
                    }
                }
            };
            return registerItem(name, item);
        }
        return registerItem(name,
                            new Item(defaultSettings().food(new FoodProperties.Builder().effect(new MobEffectInstance(
                                    MobEffects.REGENERATION,
                                    ticks,
                                    power), 1).build())));
    }

    public static Properties defaultSettings() {
        return new Item.Properties().tab(CreativeTabs.BN_TAB);
    }

    public static Item makeEgg(String name, EntityType<? extends Mob> type, int background, int dots) {
        if (Configs.MOBS.getBoolean("mobs", name, true)) {
            SpawnEggItem egg = new SpawnEggItem(type, background, dots, defaultSettings());
            DefaultDispenseItemBehavior behavior = new DefaultDispenseItemBehavior() {
                public ItemStack execute(BlockSource pointer, ItemStack stack) {
                    Direction direction = pointer.getBlockState().getValue(DispenserBlock.FACING);
                    EntityType<?> entityType = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
                    entityType.spawn(pointer.getLevel(),
                                     stack,
                                     null,
                                     pointer.getPos().relative(direction),
                                     MobSpawnType.DISPENSER,
                                     direction != Direction.UP,
                                     false);
                    stack.shrink(1);
                    return stack;
                }
            };
            DispenserBlock.registerBehavior(egg, behavior);
            NetherItems.registerItem(name, egg);
            return egg;
        } else {
            return Items.AIR;
        }
    }

    public static List<String> getPossibleItems() {
        return ITEMS;
    }
}
