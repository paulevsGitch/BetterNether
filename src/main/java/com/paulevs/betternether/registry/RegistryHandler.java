package com.paulevs.betternether.registry;

import com.paulevs.betternether.BetterNether;
import com.paulevs.betternether.blocks.*;
import com.paulevs.betternether.config.Configs;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
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
