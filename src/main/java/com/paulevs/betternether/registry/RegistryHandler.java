package com.paulevs.betternether.registry;

import com.paulevs.betternether.BetterNether;
import com.paulevs.betternether.blocks.BlockNetherGrass;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class RegistryHandler {
    public static final List<Block> BLOCKS = new ArrayList<>();
    private static final List<String> ITEMS = new ArrayList<String>();
    private static final List<Pair<String, Item>> MODITEMS = new ArrayList<>();

    public static final ArrayList<Item> MOD_BLOCKS = new ArrayList<Item>();
    public static final ArrayList<Item> MOD_ITEMS = new ArrayList<Item>();

    // Grass //
    public static final Block NETHER_GRASS = registerBlock("nether_grass", new BlockNetherGrass());




    public static void registerAllBlocks(RegistryEvent.Register<Block> e) {
        IForgeRegistry<Block> r = e.getRegistry();

        for (Block block : BLOCKS) {
            r.register(block);
        }
    }

    public static void registerAllItems(RegistryEvent.Register<Item> e) {
        IForgeRegistry<Item> r = e.getRegistry();

        for (Pair<String, Item> item : MODITEMS) {
            r.register(item.getRight().setRegistryName(new ResourceLocation(BetterNether.MOD_ID, item.getLeft())));
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

    private static Block registerBlockDirectly(String name, Block block) {
       block.setRegistryName(new ResourceLocation(BetterNether.MOD_ID, name));
       BLOCKS.add(block);
    RegistryHandler.registerItem(name, new BlockItem(block, new Item.Properties().group(BetterNether.BN_TAB)));
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
    public static List<Block> getPossibleBlocks() {
        return BLOCKS;
    }
}
