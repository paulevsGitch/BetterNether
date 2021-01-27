package com.paulevs.betternether.registry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.paulevs.betternether.BetterNether;
import com.paulevs.betternether.blocks.*;
import com.paulevs.betternether.blocks.shapes.FoodShape;
import com.paulevs.betternether.config.Configs;
import com.paulevs.betternether.entity.EntityChair;
import com.paulevs.betternether.items.ItemBowlFood;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Foods;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistryHandler {
    public static final List<Block> BLOCKS = new ArrayList<>();
    private static final List<String> ITEMS = new ArrayList<String>();
    private static final List<Pair<String, Item>> MODITEMS = new ArrayList<>();
    private static Map<IItemProvider, Integer> FUELS = new HashMap<>();

    public static final ArrayList<Item> MOD_BLOCKS = new ArrayList<Item>();
    public static final ArrayList<Item> MOD_ITEMS = new ArrayList<Item>();

    // Grass //
    public static final Block NETHER_GRASS = registerBlock("nether_grass", new BlockNetherGrass());

    // Stalagnate //
    public static final Block STALAGNATE = registerBlockNI("stalagnate", new BlockStalagnate());
    public static final Block STALAGNATE_STEM = registerBlock("stalagnate_stem", new BlockStem(MaterialColor.LIME_TERRACOTTA));
    public static final Block STALAGNATE_SEED = registerBlock("stalagnate_seed", new BlockStalagnateSeed());
    public static final Block STRIPED_LOG_STALAGNATE = registerBlock("striped_log_stalagnate", new BNPillar(MaterialColor.LIME_TERRACOTTA));
    public static final Block STRIPED_BARK_STALAGNATE = registerBlock("striped_bark_stalagnate", new BNPillar(MaterialColor.LIME_TERRACOTTA));
    public static final Block STALAGNATE_LOG = registerLog("stalagnate_log", new BNLogStripable(MaterialColor.LIME_TERRACOTTA, STRIPED_LOG_STALAGNATE), STALAGNATE_STEM);
    public static final Block STALAGNATE_BARK = registerBark("stalagnate_bark", new BNLogStripable(MaterialColor.LIME_TERRACOTTA, STRIPED_BARK_STALAGNATE), STALAGNATE_LOG);
    public static final Block STALAGNATE_PLANKS = registerPlanks("stalagnate_planks", new BNPlanks(MaterialColor.LIME_TERRACOTTA), 1, STALAGNATE_STEM, STALAGNATE_LOG, STALAGNATE_BARK, STRIPED_LOG_STALAGNATE, STRIPED_BARK_STALAGNATE);
    public static final Block STALAGNATE_STAIRS = registerStairs("stalagnate_planks_stairs", STALAGNATE_PLANKS);
    public static final Block STALAGNATE_SLAB = registerSlab("stalagnate_planks_slab", STALAGNATE_PLANKS);
    public static final Block STALAGNATE_FENCE = registerFence("stalagnate_planks_fence", STALAGNATE_PLANKS);
    public static final Block STALAGNATE_GATE = registerGate("stalagnate_planks_gate", STALAGNATE_PLANKS);
    public static final Block STALAGNATE_BUTTON = registerButton("stalagnate_planks_button", STALAGNATE_PLANKS);
    public static final Block STALAGNATE_PLATE = registerPlate("stalagnate_planks_plate", STALAGNATE_PLANKS);
    public static final Block STALAGNATE_TRAPDOOR = registerTrapdoor("stalagnate_planks_trapdoor", STALAGNATE_PLANKS);
    public static final Block STALAGNATE_DOOR = registerDoor("stalagnate_planks_door", STALAGNATE_PLANKS);
    public static final Block STALAGNATE_BOWL = registerBlockNI("stalagnate_bowl", new BlockStalagnateBowl());
    public static final Block CHAIR_STALAGNATE = registerChair("chair_stalagnate", STALAGNATE_SLAB);
    public static final Block CRAFTING_TABLE_STALAGNATE = registerCraftingTable("crafting_table_stalagnate", STALAGNATE_PLANKS);
    public static final Block CHEST_STALAGNATE = registerChest("chest_stalagnate", STALAGNATE_PLANKS);
    public static final Block ROOF_TILE_STALAGNATE = registerRoof("roof_tile_stalagnate", STALAGNATE_PLANKS);
    public static final Block ROOF_TILE_STALAGNATE_STAIRS = registerStairs("roof_tile_stalagnate_stairs", ROOF_TILE_STALAGNATE);
    public static final Block ROOF_TILE_STALAGNATE_SLAB = registerSlab("roof_tile_stalagnate_slab", ROOF_TILE_STALAGNATE);
    public static final Block BARREL_STALAGNATE = registerBarrel("barrel_stalagnate", STALAGNATE_PLANKS, STALAGNATE_SLAB);
    public static final Block BAR_STOOL_STALAGNATE = registerBarStool("bar_stool_stalagnate", STALAGNATE_SLAB);
    public static final Block STALAGNATE_LADDER = registerLadder("stalagnate_ladder", STALAGNATE_PLANKS);
    public static final Block SIGN_STALAGNATE = registerSign("sign_stalagnate", STALAGNATE_PLANKS);
    public static final Item STALAGNATE_BOWL_WART = registerItem("stalagnate_bowl_wart", new ItemBowlFood(Foods.COOKED_CHICKEN, FoodShape.WART));
    public static final Item STALAGNATE_BOWL_MUSHROOM = registerItem("stalagnate_bowl_mushroom", new ItemBowlFood(Foods.MUSHROOM_STEW, FoodShape.MUSHROOM));
    public static final Item STALAGNATE_BOWL_APPLE = registerItem("stalagnate_bowl_apple", new ItemBowlFood(Foods.APPLE, FoodShape.APPLE));
    public static final Block TABURET_STALAGNATE = registerTaburet("taburet_stalagnate", STALAGNATE_SLAB);
    public static final Item STALAGNATE_BOWL_ITEM = registerItem("stalagnate_bowl", new ItemBowlFood(null, FoodShape.NONE));

    // REED //
    public static final Block NETHER_REED = registerBlock("nether_reed", new BlockNetherReed());
    public static final Block REEDS_BLOCK = registerBlock("reeds_block", new BlockReedsBlock());
    public static final Block REEDS_STAIRS = registerStairs("reeds_stairs", REEDS_BLOCK);
    public static final Block REEDS_SLAB = registerSlab("reeds_slab", REEDS_BLOCK);
    public static final Block REEDS_FENCE = registerFence("reeds_fence", REEDS_BLOCK);
    public static final Block REEDS_GATE = registerGate("reeds_gate", REEDS_BLOCK);
    public static final Block REEDS_BUTTON = registerButton("reeds_button", REEDS_BLOCK);
    public static final Block REEDS_PLATE = registerPlate("reeds_plate", REEDS_BLOCK);
    public static final Block REEDS_TRAPDOOR = registerTrapdoor("reeds_trapdoor", REEDS_BLOCK);
    public static final Block REEDS_DOOR = registerDoor("reeds_door", REEDS_BLOCK);
    public static final Block SIGN_REED = registerSign("sign_reed", REEDS_BLOCK);
    public static final Block REEDS_LADDER = registerLadder("reeds_ladder", REEDS_BLOCK);
    public static final Block BAR_STOOL_REEDS = registerBarStool("bar_stool_reeds", REEDS_SLAB);
    public static final Block CHAIR_REEDS = registerChair("chair_reeds", REEDS_SLAB);
    public static final Block TABURET_REEDS = registerTaburet("taburet_reeds", REEDS_SLAB);
    public static final Block BARREL_REED = registerBarrel("barrel_reed", REEDS_BLOCK, REEDS_SLAB);
    public static final Block CHEST_REED = registerChest("chest_reed", REEDS_BLOCK);
    public static final Block CRAFTING_TABLE_REED = registerCraftingTable("crafting_table_reed", REEDS_BLOCK);
    public static final Block ROOF_TILE_REEDS = registerRoof("roof_tile_reeds", REEDS_BLOCK);
    public static final Block ROOF_TILE_REEDS_STAIRS = registerStairs("roof_tile_reeds_stairs", ROOF_TILE_REEDS);
    public static final Block ROOF_TILE_REEDS_SLAB = registerSlab("roof_tile_reeds_slab", ROOF_TILE_REEDS);

    // Terrain //
    public static final Block FARMLAND = registerBlock("farmland", new BlockFarmland());

    // Entities //
    public static final EntityType<EntityChair> CHAIR =
            EntityType.Builder
                    .<EntityChair>create(EntityChair::new, EntityClassification.MISC)
                    .size(0, 0)
                    .setUpdateInterval(3)
                    .setTrackingRange(5)
                    .disableSummoning()
                    .immuneToFire()
                    .setShouldReceiveVelocityUpdates(true)
                    .build("");

    public static void registerAllBlocks(RegistryEvent.Register<Block> e) {
        IForgeRegistry<Block> r = e.getRegistry();

        for (Block block : BLOCKS) {
            r.register(block);
        }
    }

    public static Block registerBlock(String name, Block block) {
        //if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
        //	registerBlockDirectly(name, block);
        //}
        registerBlockDirectly(name, block);
        return block;
    }

    public static Block registerBlockNI(String name, Block block) {
        //if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
        //	Registry.register(Registry.BLOCK, new ResourceLocation(BetterNether.MOD_ID, name), block);
        //}
        block.setRegistryName(new ResourceLocation(BetterNether.MOD_ID, name));
        BLOCKS.add(block);
        return block;
    }
    public static Item registerItem(String name, Item item) {
        MODITEMS.add(Pair.of(name, item));
//		if ((item instanceof BlockItem || Configs.ITEMS.getBoolean("items", name, true)) && item != Items.AIR) {
//			DEFERRED.register(name, () -> item);
        if (item instanceof BlockItem)
            MOD_BLOCKS.add(item);
        else
            MOD_ITEMS.add(item);
//		}
        if (!(item instanceof BlockItem))
            ITEMS.add(name);
        return item;
    }

    public static void registerAll(RegistryEvent.Register<EntityType<?>> evt) {
        IForgeRegistry<EntityType<?>> r = evt.getRegistry();
        r.register(CHAIR.setRegistryName(new ResourceLocation(BetterNether.MOD_ID, "chair")));
    }

    private static Block registerBlockDirectly(String name, Block block) {
        block.setRegistryName(new ResourceLocation(BetterNether.MOD_ID, name));
        BLOCKS.add(block);
        RegistryHandler.registerItem(name, new BlockItem(block, new Item.Properties().group(BetterNether.BN_TAB)));
        return block;
    }
    public static void addFuel(IItemProvider item, int burnTime) {
        FUELS.put(item, burnTime);
    }

    private static void addFuel(Block source, Block result) {
        if (source.getDefaultState().getMaterial().isFlammable()) {
            RegistryHandler.addFuel(result, 40);
        }
    }
    public static void registerAllItems(RegistryEvent.Register<Item> e) {
        IForgeRegistry<Item> r = e.getRegistry();

        for (Pair<String, Item> item : MODITEMS) {
            r.register(item.getRight().setRegistryName(new ResourceLocation(BetterNether.MOD_ID, item.getLeft())));
        }
    }

    public static Block registerCraftingTable(String name, Block source) {
        Block block = new BNCraftingTable(source);
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, block);
        }
        return block;
    }

    public static Block registerChest(String name, Block source) {
      Block block = new BNChest(source);
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, block);
           addFuel(source, block);
        }
        return block;
   }

   public static Block registerSign(String name, Block source) {
        Block block = new BNSign(source);
       if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, block);
           addFuel(source, block);
        }
        return block;
    }

    public static Block registerBarrel(String name, Block source, Block slab) {
        Block block = new BNBarrel(source);
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, block);
            addFuel(source, block);
        }
        return block;
    }

    public static Block registerLadder(String name, Block source) {
        Block block = new BNLadder(source);
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, block);
            addFuel(source, block);
        }
        return block;
    }

    public static Block registerTaburet(String name, Block source) {
        Block block = new BNTaburet(source);
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, block);
            addFuel(source, block);
        }
        return block;
    }

    public static Block registerChair(String name, Block source) {
        Block block = new BNNormalChair(source);
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, block);
            addFuel(source, block);
        }
        return block;
    }

    public static Block registerBarStool(String name, Block source) {
        Block block = new BNBarStool(source);
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, block);
            addFuel(source, block);
        }
        return block;
    }
    public static Block registerStairs(String name, Block source) {
        Block stairs = new BNStairs(source);
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, stairs);
            addFuel(source, stairs);
        }
        return stairs;
    }

    public static Block registerSlab(String name, Block source) {
        Block slab = new BNSlab(source);
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, slab);
            addFuel(source, slab);
        }
        return slab;
    }

    private static Block registerRoof(String name, Block source) {
        Block roof = new BlockBase(AbstractBlock.Properties.from(source));
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, roof);
            addFuel(source, roof);
        }
        return roof;
    }

    public static Block registerButton(String name, Block source) {
        Block button = new BNButton(source);
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, button);
            addFuel(source, button);
        }
        return button;
    }

    public static Block registerPlate(String name, Block source) {
        Block plate = new BNPlate(PressurePlateBlock.Sensitivity.EVERYTHING, source);
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, plate);
            addFuel(source, plate);
        }
        return plate;
    }

    private static Block registerPlate(String name, Block source, PressurePlateBlock.Sensitivity rule) {
        Block plate = new BNPlate(rule, source);
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, plate);
            addFuel(source, plate);
        }
        return plate;
    }

    public static Block registerPlanks(String name, Block planks, Block... logs) {
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, planks);
        }
        return planks;
    }

    private static Block registerPlanks(String name, Block planks, int output, Block stem, Block... logs) {
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, planks);

        }
        return planks;
    }

    public static Block registerLog(String name, Block log, Block stem) {
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, log);
        }
        return log;
    }

    public static Block registerBark(String name, Block bark, Block log) {
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, bark);
        }
        return bark;
    }

    public static Block registerFence(String name, Block source) {
        Block fence = new BNFence(source);
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, fence);
            addFuel(source, fence);
        }
        return fence;
    }

    public static Block registerGate(String name, Block source) {
        Block gate = new BNGate(source);
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, gate);
            addFuel(source, gate);
        }
        return gate;
    }

    public static Block registerDoor(String name, Block source) {
        Block door = new BNDoor(source);
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, door);
            addFuel(source, door);
        }
        return door;
    }

    public static Block registerTrapdoor(String name, Block source) {
        Block trapdoor = new BNTrapdoor(source);
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            registerBlockDirectly(name, trapdoor);
            addFuel(source, trapdoor);
        }
        return trapdoor;
    }

    public static List<Block> getPossibleBlocks() {
        return BLOCKS;
    }
}
