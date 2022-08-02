package org.betterx.betternether.recipes;

import org.betterx.bclib.config.Configs;
import org.betterx.bclib.recipes.BCLRecipeBuilder;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blocks.complex.NetherWoodenMaterial;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherItems;

import net.minecraft.core.Registry;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import com.google.common.collect.ImmutableMap;

public class ItemRecipes {
    public static void register() {
        if (itemExists(NetherItems.GLOWSTONE_PILE)) {
            String[] shape = new String[]{"###", "###", "###"};
            ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(NetherItems.GLOWSTONE_PILE));
            BNRecipeManager.addCraftingRecipe(
                    "bn_glowstone_dust",
                    "",
                    shape,
                    materials,
                    new ItemStack(Items.GLOWSTONE_DUST)
            );
        }

        if (itemExists(NetherItems.CINCINNASITE_INGOT) && blockExists(NetherBlocks.CINCINNASITE_CHAIN)) {
            String[] shape = new String[]{"#", "#", "#"};
            ImmutableMap<String, ItemStack> materials = ImmutableMap.of(
                    "#",
                    new ItemStack(NetherItems.CINCINNASITE_INGOT)
            );
            BNRecipeManager.addCraftingRecipe(
                    "cincinnasite_chains",
                    "",
                    shape,
                    materials,
                    new ItemStack(NetherBlocks.CINCINNASITE_CHAIN, 3)
            );
        }

        if (itemExists(NetherItems.LAPIS_PILE)) {
            String[] shape = new String[]{"###", "###", "###"};
            ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(NetherItems.LAPIS_PILE));
            BNRecipeManager.addCraftingRecipe(
                    "glowstone_pile_to_dust",
                    "",
                    shape,
                    materials,
                    new ItemStack(Items.LAPIS_LAZULI)
            );
        }

        if (blockExists(NetherBlocks.BLOOMING_VINE)) {
            ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(NetherBlocks.BLOOMING_VINE));
            BNRecipeManager.addCraftingRecipe(
                    "bn_yellow_dye",
                    "",
                    new String[]{"#"},
                    materials,
                    new ItemStack(Items.YELLOW_DYE, 2)
            );
        }

        if (blockExists(NetherBlocks.GOLDEN_VINE) && itemExists(NetherItems.GLOWSTONE_PILE)) {
            ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(NetherBlocks.GOLDEN_VINE));
            BNRecipeManager.addCraftingRecipe(
                    "bn_golden_vine",
                    "",
                    new String[]{"#"},
                    materials,
                    new ItemStack(NetherItems.GLOWSTONE_PILE, 2)
            );
        }

        if (blockExists(NetherBlocks.WALL_MUSHROOM_BROWN)) {
            ImmutableMap<String, ItemStack> materials = ImmutableMap.of(
                    "#",
                    new ItemStack(NetherBlocks.WALL_MUSHROOM_BROWN)
            );
            BNRecipeManager.addCraftingRecipe(
                    "wall_mushroom_brown_1",
                    "wall_mushroom",
                    new String[]{"#"},
                    materials,
                    new ItemStack(Items.BROWN_MUSHROOM)
            );

            materials = ImmutableMap.of("#", new ItemStack(Items.BROWN_MUSHROOM));
            BNRecipeManager.addCraftingRecipe(
                    "wall_mushroom_brown_2",
                    "wall_mushroom",
                    new String[]{"#"},
                    materials,
                    new ItemStack(NetherBlocks.WALL_MUSHROOM_BROWN)
            );
        }

        if (blockExists(NetherBlocks.WALL_MUSHROOM_RED)) {
            ImmutableMap<String, ItemStack> materials = ImmutableMap.of(
                    "#",
                    new ItemStack(NetherBlocks.WALL_MUSHROOM_RED)
            );
            BNRecipeManager.addCraftingRecipe(
                    "wall_mushroom_red_1",
                    "wall_mushroom",
                    new String[]{"#"},
                    materials,
                    new ItemStack(Items.RED_MUSHROOM)
            );

            materials = ImmutableMap.of("#", new ItemStack(Items.RED_MUSHROOM));
            BNRecipeManager.addCraftingRecipe(
                    "wall_mushroom_red_2",
                    "wall_mushroom",
                    new String[]{"#"},
                    materials,
                    new ItemStack(NetherBlocks.WALL_MUSHROOM_RED)
            );
        }

        if (itemExists(NetherItems.CRIMSON_BOAT)) {
            NetherWoodenMaterial.makeBoatRecipe(
                    Configs.RECIPE_CONFIG, BetterNether.makeID("crimson_boat"),
                    Blocks.CRIMSON_PLANKS, NetherItems.CRIMSON_BOAT, NetherItems.CRIMSON_CHEST_BOAT, false
            );
        }
        if (itemExists(NetherItems.CRIMSON_CHEST_BOAT)) {
            NetherWoodenMaterial.makeBoatRecipe(
                    Configs.RECIPE_CONFIG, BetterNether.makeID("crimson_chest_boat"),
                    Blocks.CRIMSON_PLANKS, NetherItems.CRIMSON_BOAT, NetherItems.CRIMSON_CHEST_BOAT, true
            );
        }

        if (itemExists(NetherItems.WARPED_BOAT)) {
            NetherWoodenMaterial.makeBoatRecipe(
                    Configs.RECIPE_CONFIG, BetterNether.makeID("warped_boat"),
                    Blocks.WARPED_PLANKS, NetherItems.WARPED_BOAT, NetherItems.WARPED_CHEST_BOAT, false
            );
        }
        if (itemExists(NetherItems.WARPED_CHEST_BOAT)) {
            NetherWoodenMaterial.makeBoatRecipe(
                    Configs.RECIPE_CONFIG, BetterNether.makeID("warped_chest_boat"),
                    Blocks.WARPED_PLANKS, NetherItems.WARPED_BOAT, NetherItems.WARPED_CHEST_BOAT, true
            );
        }

        BCLRecipeBuilder.crafting(BetterNether.makeID("cincinnasite_ingot"), NetherItems.CINCINNASITE_INGOT)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .setShape("#")
                        .addMaterial('#', NetherBlocks.CINCINNASITE_FORGED)
                        .setGroup("nether_cincinnasite_ingot")
                        .setOutputCount(4)
                        .build();

        BCLRecipeBuilder.crafting(BetterNether.makeID("detector_rail"), Items.DETECTOR_RAIL)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .setShape("X X", "X#X", "XRX")
                        .addMaterial('R', Items.REDSTONE)
                        .addMaterial('#', Items.STONE_PRESSURE_PLATE)
                        .addMaterial('X', NetherItems.CINCINNASITE_INGOT)
                        .setGroup("nether_detector_rail")
                        .setOutputCount(6)
                        .build();

        BCLRecipeBuilder.crafting(BetterNether.makeID("glass_bottle"), Items.GLASS_BOTTLE)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .setShape("# #", " # ")
                        .addMaterial('#', NetherBlocks.QUARTZ_GLASS)
                        .setGroup("nether_glass_bottle")
                        .setOutputCount(3)
                        .build();
        BCLRecipeBuilder.crafting(BetterNether.makeID("gray_dye"), Items.GRAY_DYE)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .setShape("#")
                        .addMaterial('#', NetherBlocks.GRAY_MOLD)
                        .setGroup("nether_gray_dye")
                        .build();

        BCLRecipeBuilder.crafting(BetterNether.makeID("nether_ruby_from_block"), NetherItems.NETHER_RUBY)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .setShape("#")
                        .addMaterial('#', NetherBlocks.NETHER_RUBY_BLOCK)
                        .setGroup("nether_nether_ruby_from_block")
                        .setOutputCount(9)
                        .build();
        BCLRecipeBuilder.crafting(BetterNether.makeID("paper"), Items.PAPER)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .setShape("###")
                        .addMaterial('#', NetherBlocks.NETHER_REED_STEM)
                        .setGroup("nether_paper")
                        .setOutputCount(3)
                        .build();
        BCLRecipeBuilder.crafting(BetterNether.makeID("rail"), Items.RAIL)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .setShape("X X", "X#X", "X X")
                        .addMaterial('#', Items.STICK)
                        .addMaterial('X', NetherItems.CINCINNASITE_INGOT)
                        .setGroup("nether_rail")
                        .setOutputCount(16)
                        .build();
        BCLRecipeBuilder.crafting(BetterNether.makeID("red_dye"), Items.RED_DYE)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .setShape("#")
                        .addMaterial('#', NetherBlocks.RED_MOLD)
                        .setGroup("nether_red_dye")
                        .setOutputCount(2)
                        .build();
        BCLRecipeBuilder.crafting(BetterNether.makeID("stalagnate_bowl_apple"), NetherItems.STALAGNATE_BOWL_APPLE)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .setShape("W", "#")
                        .addMaterial('#', NetherItems.STALAGNATE_BOWL)
                        .addMaterial('W', NetherItems.BLACK_APPLE)
                        .setGroup("nether_stalagnate_bowl_apple")
                        .build();

        BCLRecipeBuilder.crafting(BetterNether.makeID("stick"), Items.STICK)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .setShape("#", "#")
                        .addMaterial('#', NetherBlocks.NETHER_REED_STEM)
                        .setGroup("nether_stick")
                        .setOutputCount(2)
                        .build();

        BCLRecipeBuilder.crafting(BetterNether.makeID("sugar"), Items.SUGAR)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .setShape("#")
                        .addMaterial('#', NetherBlocks.NETHER_REED_STEM)
                        .setGroup("nether_sugar")
                        .build();

        BCLRecipeBuilder.crafting(BetterNether.makeID("shield"), Items.SHIELD)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .setShape("WoW", "WWW", " W ")
                        .addMaterial('W', ItemTags.PLANKS)
                        .addMaterial('o', NetherItems.CINCINNASITE_INGOT)
                        .setGroup("nether_shield")
                        .build();

        BCLRecipeBuilder.crafting(BetterNether.makeID("piston"), Items.PISTON)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .setShape("TTT", "#X#", "#R#")
                        .addMaterial('R', Items.REDSTONE)
                        .addMaterial('#', Items.COBBLESTONE)
                        .addMaterial('T', ItemTags.PLANKS)
                        .addMaterial('X', NetherItems.CINCINNASITE_INGOT)
                        .setGroup("nether_piston")
                        .build();

        BCLRecipeBuilder.crafting(BetterNether.makeID("stalagnate_bowl"), NetherItems.STALAGNATE_BOWL)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .setShape("# #", " # ")
                        .addMaterial('#', NetherBlocks.MAT_STALAGNATE.getStem())
                        .setGroup("nether_stalagnate_bowl")
                        .setOutputCount(3)
                        .build();

        registerShapeLess();
        registerSmelting();
    }

    private static void registerShapeLess() {
        BCLRecipeBuilder.crafting(BetterNether.makeID("agave_medicine"), NetherItems.AGAVE_MEDICINE)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .addMaterial('#', NetherItems.STALAGNATE_BOWL)
                        .addMaterial('A', NetherItems.AGAVE_LEAF)
                        .addMaterial('B', NetherItems.AGAVE_LEAF)
                        .addMaterial('C', NetherItems.AGAVE_LEAF)
                        .setList("#ABC")
                        .setGroup("nether_agave_medicine")
                        .build();
        BCLRecipeBuilder.crafting(BetterNether.makeID("herbal_medicine"), NetherItems.HERBAL_MEDICINE)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .addMaterial('#', NetherItems.STALAGNATE_BOWL)
                        .addMaterial('A', NetherItems.AGAVE_LEAF)
                        .addMaterial('B', NetherItems.BLACK_APPLE)
                        .addMaterial('C', NetherItems.HOOK_MUSHROOM_COOKED)
                        .addMaterial('D', Items.NETHER_WART)
                        .setList("#ABCD")
                        .setGroup("nether_herbal_medicine")
                        .build();
        BCLRecipeBuilder.crafting(BetterNether.makeID("stalagnate_bowl_mushroom"), NetherItems.STALAGNATE_BOWL_MUSHROOM)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .addMaterial('#', Items.BROWN_MUSHROOM)
                        .addMaterial('A', Items.RED_MUSHROOM)
                        .addMaterial('B', NetherItems.STALAGNATE_BOWL)
                        .setList("#AB")
                        .setGroup("nether_stalagnate_bowl_mushroom")
                        .build();

        BCLRecipeBuilder.crafting(BetterNether.makeID("stalagnate_bowl_wart"), NetherItems.STALAGNATE_BOWL_WART)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .addMaterial('#', NetherItems.STALAGNATE_BOWL)
                        .addMaterial('A', Items.NETHER_WART)
                        .addMaterial('B', Items.NETHER_WART)
                        .addMaterial('C', Items.NETHER_WART)
                        .setList("#ABC")
                        .setGroup("nether_stalagnate_bowl_wart")
                        .build();
    }

    private static void registerSmelting() {
        BCLRecipeBuilder.smelting(BetterNether.makeID("black_dye"), Items.BLACK_DYE)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .setInput(NetherBlocks.INK_BUSH_SEED)
                        .setGroup("nether_black_dye")
                        .setCookingTime(200)
                        .build();

        BCLRecipeBuilder.smelting(BetterNether.makeID("hook_mushroom_cooked"), NetherItems.HOOK_MUSHROOM_COOKED)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .setInput(NetherBlocks.HOOK_MUSHROOM)
                        .setGroup("nether_hook_mushroom_cooked")
                        .setCookingTime(200)
                        .setExperience(0.1f)
                        .build();

        BCLRecipeBuilder.smelting(BetterNether.makeID("cincinnasite_ingot_from_shard"), NetherItems.CINCINNASITE_INGOT)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .setInput(NetherItems.CINCINNASITE)
                        .setGroup("nether_cincinnasite_ingot_from_shard")
                        .setCookingTime(200)
                        .setExperience(0.5f)
                        .buildWithBlasting();

        BCLRecipeBuilder.smelting(BetterNether.makeID("cincinnasite_ingot_from_ore"), NetherItems.CINCINNASITE_INGOT)
                        .checkConfig(Configs.RECIPE_CONFIG)
                        .setInput(NetherBlocks.CINCINNASITE_ORE)
                        .setGroup("nether_cincinnasite_ingot_from_ore")
                        .setCookingTime(200)
                        .setExperience(0.5f)
                        .buildWithBlasting();

    }

    private static boolean itemExists(Item item) {
        return Registry.ITEM.getKey(item) != Registry.ITEM.getDefaultKey();
    }

    private static boolean blockExists(Block block) {
        return Registry.BLOCK.getKey(block) != Registry.BLOCK.getDefaultKey();
    }
}
