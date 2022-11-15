package org.betterx.betternether.registry;

import org.betterx.bclib.items.boat.BoatTypeOverride;
import org.betterx.bclib.registry.ItemRegistry;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blocks.BNBlockProperties.FoodShape;
import org.betterx.betternether.config.Configs;
import org.betterx.betternether.integrations.VanillaExcavatorsIntegration;
import org.betterx.betternether.integrations.VanillaHammersIntegration;
import org.betterx.betternether.items.ItemBlackApple;
import org.betterx.betternether.items.ItemBowlFood;
import org.betterx.betternether.items.complex.DiamondSet;
import org.betterx.betternether.items.complex.NetherSet;
import org.betterx.betternether.items.materials.BNArmorMaterial;
import org.betterx.betternether.items.materials.BNToolMaterial;
import org.betterx.betternether.tab.CreativeTabs;
import org.betterx.worlds.together.tag.v3.TagManager;

import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class NetherItems extends ItemRegistry {
    private static final List<String> ITEMS = new ArrayList<String>();
    private static final ArrayList<Item> MOD_BLOCKS = new ArrayList<Item>();
    private static final ArrayList<Item> MOD_ITEMS = new ArrayList<Item>();

    public static final Item BLACK_APPLE = registerItem("black_apple", new ItemBlackApple());

    public static final Item STALAGNATE_BOWL = registerItem("stalagnate_bowl", new ItemBowlFood(null, FoodShape.NONE));
    public static final Item STALAGNATE_BOWL_WART = registerItem(
            "stalagnate_bowl_wart",
            new ItemBowlFood(
                    Foods.COOKED_CHICKEN,
                    FoodShape.WART
            )
    );
    public static final Item STALAGNATE_BOWL_MUSHROOM = registerItem(
            "stalagnate_bowl_mushroom",
            new ItemBowlFood(
                    Foods.MUSHROOM_STEW,
                    FoodShape.MUSHROOM
            )
    );
    public static final Item STALAGNATE_BOWL_APPLE = registerItem(
            "stalagnate_bowl_apple",
            new ItemBowlFood(Foods.APPLE, FoodShape.APPLE)
    );
    public static final Item HOOK_MUSHROOM_COOKED = registerFood("hook_mushroom_cooked", 4, 0.4F);

    public static final Item CINCINNASITE = registerItem("cincinnasite", new Item(defaultSettings()));
    public static final Item CINCINNASITE_INGOT = registerItem("cincinnasite_ingot", new Item(defaultSettings()),
            org.betterx.worlds.together.tag.v3.CommonItemTags.IRON_INGOTS
    );
    public static final Item NETHER_RUBY = registerItem("nether_ruby", new Item(defaultSettings()));

    public static final NetherSet CINCINNASITE_SET = new NetherSet(
            "cincinnasite",
            BNToolMaterial.CINCINNASITE,
            BNArmorMaterial.CINCINNASITE,
            true
    ).init();
    public static final DiamondSet CINCINNASITE_DIAMOND_SET = new DiamondSet(CINCINNASITE_SET).init();


    public static final NetherSet NETHER_RUBY_SET = new NetherSet(
            "nether_ruby",
            BNToolMaterial.NETHER_RUBY,
            BNArmorMaterial.NETHER_RUBY,
            false
    ).init();

    public static final Item CINCINNASITE_HAMMER = registerItem(
            "cincinnasite_hammer",
            VanillaHammersIntegration.makeHammer(
                    BNToolMaterial.CINCINNASITE,
                    4,
                    -2.0F
            )
    );
    public static final Item CINCINNASITE_HAMMER_DIAMOND = registerItem(
            "cincinnasite_hammer_diamond",
            VanillaHammersIntegration.makeHammer(
                    BNToolMaterial.CINCINNASITE_DIAMOND,
                    5,
                    -2.0F
            )
    );
    public static final Item NETHER_RUBY_HAMMER = registerItem(
            "nether_ruby_hammer",
            VanillaHammersIntegration.makeHammer(
                    BNToolMaterial.NETHER_RUBY,
                    5,
                    -2.0F
            )
    );

    public static final Item CINCINNASITE_EXCAVATOR = registerItem(
            "cincinnasite_excavator",
            VanillaExcavatorsIntegration.makeExcavator(
                    BNToolMaterial.CINCINNASITE,
                    4,
                    -1.6F
            )
    );
    public static final Item CINCINNASITE_EXCAVATOR_DIAMOND = registerItem(
            "cincinnasite_excavator_diamond",
            VanillaExcavatorsIntegration.makeExcavator(
                    BNToolMaterial.CINCINNASITE_DIAMOND,
                    5,
                    -2.0F
            )
    );
    public static final Item NETHER_RUBY_EXCAVATOR = registerItem(
            "nether_ruby_excavator",
            VanillaExcavatorsIntegration.makeExcavator(
                    BNToolMaterial.NETHER_RUBY,
                    5,
                    -2.0F
            )
    );

    public static final Item GLOWSTONE_PILE = registerItem("glowstone_pile", new Item(defaultSettings()));
    public static final Item LAPIS_PILE = registerItem("lapis_pile", new Item(defaultSettings()));

    public static final Item AGAVE_LEAF = registerItem("agave_leaf", new Item(defaultSettings()));
    public static final Item AGAVE_MEDICINE = registerMedicine("agave_medicine", 40, 2, true);
    public static final Item HERBAL_MEDICINE = registerMedicine("herbal_medicine", 10, 5, true);

    public static final BoatTypeOverride WARPED_BOAT_TYPE = BoatTypeOverride
            .create(BetterNether.MOD_ID, "warped", Blocks.WARPED_PLANKS);
    public static final Item WARPED_BOAT = registerItem("warped_boat", WARPED_BOAT_TYPE.createItem(false));
    public static final Item WARPED_CHEST_BOAT = registerItem("warped_chest_boat", WARPED_BOAT_TYPE.createItem(true)
    );
    public static final BoatTypeOverride CRIMSON_BOAT_TYPE = BoatTypeOverride
            .create(BetterNether.MOD_ID, "crimson", Blocks.CRIMSON_PLANKS);
    public static final Item CRIMSON_BOAT = registerItem("crimson_boat", CRIMSON_BOAT_TYPE.createItem(false));
    public static final Item CRIMSON_CHEST_BOAT = registerItem(
            "crimson_chest_boat",
            CRIMSON_BOAT_TYPE.createItem(true)
    );

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
                TagManager.ITEMS.add(item, tags);

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
                TagManager.ITEMS.add(item, tags);

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
        return registerItem(
                name,
                new Item(defaultSettings().food(new FoodProperties.Builder().nutrition(hunger)
                                                                            .saturationMod(
                                                                                    saturationMultiplier)
                                                                            .build()))
        );
    }

    public static Item registerMedicine(String name, int ticks, int power, boolean bowl) {
        if (bowl) {
            Item item = new Item(defaultSettings().stacksTo(16)
                                                  .food(new FoodProperties.Builder().effect(new MobEffectInstance(
                                                          MobEffects.REGENERATION,
                                                          ticks,
                                                          power
                                                  ), 1).build())) {
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
        return registerItem(
                name,
                new Item(defaultSettings().food(new FoodProperties.Builder().effect(new MobEffectInstance(
                        MobEffects.REGENERATION,
                        ticks,
                        power
                ), 1).build()))
        );
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
                    entityType.spawn(
                            pointer.getLevel(),
                            stack,
                            null,
                            pointer.getPos().relative(direction),
                            MobSpawnType.DISPENSER,
                            direction != Direction.UP,
                            false
                    );
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
}
