package org.betterx.betternether.blocks.complex;

import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import org.betterx.betternether.blocks.BNGlass;
import org.betterx.betternether.blocks.BNPane;
import org.betterx.betternether.config.Configs;
import org.betterx.betternether.recipes.RecipesHelper;
import org.betterx.betternether.registry.NetherBlocks;

public class ColoredGlassMaterial {
    public final Block white;
    public final Block orange;
    public final Block magenta;
    public final Block light_blue;
    public final Block yellow;
    public final Block lime;
    public final Block pink;
    public final Block gray;
    public final Block light_gray;
    public final Block cyan;
    public final Block purple;
    public final Block blue;
    public final Block brown;
    public final Block green;
    public final Block red;
    public final Block black;

    /**
     * Full Block Constructor
     *
     * @param name - base name of block (prefix) and it's group
     * @param base - block base for material properties and crafting
     */
    public <T extends Block> ColoredGlassMaterial(String name, Block base) {
        white = makeInstance(name, base, Items.WHITE_DYE, true, false);
        orange = makeInstance(name, base, Items.ORANGE_DYE, true, false);
        magenta = makeInstance(name, base, Items.MAGENTA_DYE, true, false);
        light_blue = makeInstance(name, base, Items.LIGHT_BLUE_DYE, true, false);
        yellow = makeInstance(name, base, Items.YELLOW_DYE, true, false);
        lime = makeInstance(name, base, Items.LIME_DYE, true, false);
        pink = makeInstance(name, base, Items.PINK_DYE, true, false);
        gray = makeInstance(name, base, Items.GRAY_DYE, true, false);
        light_gray = makeInstance(name, base, Items.LIGHT_GRAY_DYE, true, false);
        cyan = makeInstance(name, base, Items.CYAN_DYE, true, false);
        purple = makeInstance(name, base, Items.PURPLE_DYE, true, false);
        blue = makeInstance(name, base, Items.BLUE_DYE, true, false);
        brown = makeInstance(name, base, Items.BROWN_DYE, true, false);
        green = makeInstance(name, base, Items.GREEN_DYE, true, false);
        red = makeInstance(name, base, Items.RED_DYE, true, false);
        black = makeInstance(name, base, Items.BLACK_DYE, true, false);
    }

    /**
     * Pane Block Constructor
     *
     * @param name           - base name of block (prefix) and it's group
     * @param base           - block base for material properties and crafting
     * @param paneDropItself - will pane drop itself on break or not (will require silk
     *                       touch)
     */
    public <T extends Block> ColoredGlassMaterial(String name, Block base, boolean paneDropItself) {
        white = makeInstance(name, base, Items.WHITE_DYE, false, paneDropItself);
        orange = makeInstance(name, base, Items.ORANGE_DYE, false, paneDropItself);
        magenta = makeInstance(name, base, Items.MAGENTA_DYE, false, paneDropItself);
        light_blue = makeInstance(name, base, Items.LIGHT_BLUE_DYE, false, paneDropItself);
        yellow = makeInstance(name, base, Items.YELLOW_DYE, false, paneDropItself);
        lime = makeInstance(name, base, Items.LIME_DYE, false, paneDropItself);
        pink = makeInstance(name, base, Items.PINK_DYE, false, paneDropItself);
        gray = makeInstance(name, base, Items.GRAY_DYE, false, paneDropItself);
        light_gray = makeInstance(name, base, Items.LIGHT_GRAY_DYE, false, paneDropItself);
        cyan = makeInstance(name, base, Items.CYAN_DYE, false, paneDropItself);
        purple = makeInstance(name, base, Items.PURPLE_DYE, false, paneDropItself);
        blue = makeInstance(name, base, Items.BLUE_DYE, false, paneDropItself);
        brown = makeInstance(name, base, Items.BROWN_DYE, false, paneDropItself);
        green = makeInstance(name, base, Items.GREEN_DYE, false, paneDropItself);
        red = makeInstance(name, base, Items.RED_DYE, false, paneDropItself);
        black = makeInstance(name, base, Items.BLACK_DYE, false, paneDropItself);
    }

    private Block makeInstance(String group, Block base, Item dye, boolean isFullBlock, boolean paneDropItself) {
        Block block = isFullBlock ? new BNGlass(base) : new BNPane(base, paneDropItself);
        String name = group + "_" + ((DyeItem) dye).getDyeColor().getSerializedName();
        if (Configs.BLOCKS.getBoolean("blocks", name, true)) {
            NetherBlocks.registerBlockDirectly(name, block);
            RecipesHelper.makeColoringRecipe(base, block, dye, group);
        }
        return block;
    }
}