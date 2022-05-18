package org.betterx.betternether.recipes;

import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import com.google.common.collect.ImmutableMap;

public class RecipesHelper {
    private static final String[] SHAPE_ROOF = new String[]{"# #", "###", " # "};
    private static final String[] SHAPE_STAIR = new String[]{"#  ", "## ", "###"};
    private static final String[] SHAPE_SLAB = new String[]{"###"};
    private static final String[] SHAPE_BUTTON = new String[]{"#"};
    private static final String[] SHAPE_PLATE = new String[]{"##"};
    private static final String[] SHAPE_X2 = new String[]{"##", "##"};
    private static final String[] SHAPE_3X2 = new String[]{"###", "###"};
    private static final String[] SHAPE_COLORING = new String[]{"###", "#I#", "###"};
    private static final String[] SHAPE_ROUND = new String[]{"###", "# #", "###"};
    private static final String[] SHAPE_FIRE_BOWL = new String[]{"#I#", " # ", "L L"};

    private static void makeSingleRecipe(String group, Block source, Block result, String[] shape, int count) {
        if (Registry.BLOCK.getKey(source) != Registry.BLOCK.getDefaultKey()) {
            String name = Registry.BLOCK.getKey(source).getPath() + "_" + Registry.BLOCK.getKey(result).getPath();
            ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(source));
            BNRecipeManager.addCraftingRecipe(name, group, shape, materials, new ItemStack(result, count));
        }
    }

    public static void makeRoofRecipe(Block source, Block roof) {
        makeSingleRecipe("roof_tile", source, roof, SHAPE_ROOF, 6);
    }

    public static void makeStairsRecipe(Block source, Block stairs) {
        //String group = Registry.BLOCK.getKey(stairs).getPath().contains("roof_tile") ? "roof_tile_stairs" : stairs.getSoundType(stairs.defaultBlockState()) == SoundType.WOOD ? "nether_wooden_stairs" : "nether_rock_stairs";
        //woods are now registered through different means
        String group = Registry.BLOCK.getKey(stairs).getPath().contains("roof_tile")
                ? "roof_tile_stairs"
                : "nether_rock_stairs";
        makeSingleRecipe(group, source, stairs, SHAPE_STAIR, 4);
    }

    public static void makeSlabRecipe(Block source, Block slab) {
        //String group = Registry.BLOCK.getKey(slab).getPath().contains("roof_tile") ? "roof_tile_slab" : slab.getSoundType(slab.defaultBlockState()) == SoundType.WOOD ? "nether_wooden_slab" : "nether_rock_slab";
        //woods are now registered through different means
        String group = Registry.BLOCK.getKey(slab).getPath().contains("roof_tile")
                ? "roof_tile_slab"
                : "nether_rock_slab";
        makeSingleRecipe(group, source, slab, SHAPE_SLAB, 6);
    }

    public static void makeButtonRecipe(Block source, Block button) {
        //String group = button.getSoundType(button.defaultBlockState()) == SoundType.WOOD ? "nether_wooden_button" : "nether_rock_button";
        String group = "nether_rock_plate"; //woods are now registered through different means
        makeSingleRecipe(group, source, button, SHAPE_BUTTON, 1);
    }

    public static void makePlateRecipe(Block source, Block plate) {
        //String group = plate.getSoundType(plate.defaultBlockState()) == SoundType.WOOD ? "nether_wooden_plate" : "nether_rock_plate";
        String group = "nether_rock_plate"; //woods are now registered through different means
        makeSingleRecipe(group, source, plate, SHAPE_PLATE, 1);
    }

    public static void makeSimpleRecipe2(Block source, Block result, int count, String group) {
        makeSingleRecipe(group, source, result, SHAPE_X2, count);
    }

    public static void makeWallRecipe(Block source, Block wall) {
        if (Registry.BLOCK.getKey(source) != Registry.BLOCK.getDefaultKey()) {
            String name = Registry.BLOCK.getKey(wall).getPath();
            ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(source));
            BNRecipeManager.addCraftingRecipe(name, "nether_wall", SHAPE_3X2, materials, new ItemStack(wall, 6));
        }
    }

    public static void makeColoringRecipe(Block source, Block result, Item dye, String group) {
        if (Registry.BLOCK.getKey(source) != Registry.BLOCK.getDefaultKey()) {
            String name = Registry.BLOCK.getKey(result).getPath();
            ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#",
                                                                        new ItemStack(source),
                                                                        "I",
                                                                        new ItemStack(dye));
            BNRecipeManager.addCraftingRecipe(name, group, SHAPE_COLORING, materials, new ItemStack(result, 8));
        }
    }

    public static void makeRoundRecipe(Block source, Block result, String group) {
        if (Registry.BLOCK.getKey(source) != Registry.BLOCK.getDefaultKey()) {
            String name = Registry.BLOCK.getKey(result).getPath();
            ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(source));
            BNRecipeManager.addCraftingRecipe(name, group, SHAPE_ROUND, materials, new ItemStack(result));
        }
    }

    public static void makeFireBowlRecipe(Block material, Block inside, Item leg, Block result) {
        if (Registry.BLOCK.getKey(material) != Registry.BLOCK.getDefaultKey() && Registry.BLOCK.getKey(inside) != Registry.BLOCK.getDefaultKey() && Registry.ITEM.getKey(
                leg) != Registry.ITEM.getDefaultKey()) {
            String name = Registry.BLOCK.getKey(result).getPath();
            ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#",
                                                                        new ItemStack(material),
                                                                        "I",
                                                                        new ItemStack(inside),
                                                                        "L",
                                                                        new ItemStack(leg));
            BNRecipeManager.addCraftingRecipe(name, "fire_bowl", SHAPE_FIRE_BOWL, materials, new ItemStack(result));
        }
    }
}