package org.betterx.betternether.recipes;

import org.betterx.bclib.config.Configs;
import org.betterx.bclib.recipes.GridRecipe;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherItems;

import net.minecraft.world.item.Items;

public class BlockRecipes {
    public static void register() {
        GridRecipe.make(BetterNether.makeID("activator_rail"), Items.ACTIVATOR_RAIL)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("XSX", "X#X", "XSX")
                  .addMaterial('#', Items.REDSTONE_TORCH)
                  .addMaterial('S', Items.STICK)
                  .addMaterial('X', NetherItems.CINCINNASITE_INGOT)
                  .setGroup("nether_activator_rail")
                  .setOutputCount(6)
                  .build();

        GridRecipe.make(BetterNether.makeID("black_apple_seed"), NetherBlocks.BLACK_APPLE_SEED)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("#")
                  .addMaterial('#', NetherItems.BLACK_APPLE)
                  .setGroup("nether_black_apple_seed")
                  .setOutputCount(4)
                  .build();

        GridRecipe.make(BetterNether.makeID("bn_bone_block"), NetherBlocks.BONE_BLOCK)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("##", "##")
                  .addMaterial('#', Items.BONE_BLOCK)
                  .setGroup("nether_bn_bone_block")
                  .setOutputCount(4)
                  .build();
        GridRecipe.make(BetterNether.makeID("bone_cin_door"), NetherBlocks.BONE_CINCINNASITE_DOOR)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("AB", "BB", "BA")
                  .addMaterial('A', NetherBlocks.CINCINNASITE_FORGED)
                  .addMaterial('B', NetherBlocks.BONE_BLOCK)
                  .setGroup("nether_bone_cin_door")
                  .setOutputCount(3)
                  .build();

        GridRecipe.make(BetterNether.makeID("bone_tile"), NetherBlocks.BONE_TILE)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("#", "#")
                  .addMaterial('#', NetherBlocks.BONE_SLAB)
                  .setGroup("nether_bone_tile")
                  .build();
        GridRecipe.make(BetterNether.makeID("brick_pot"), NetherBlocks.BRICK_POT)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("#N#", " # ")
                  .addMaterial('#', Items.NETHER_BRICK)
                  .addMaterial('N', Items.SOUL_SAND)
                  .setGroup("nether_brick_pot")
                  .build();


        GridRecipe.make(BetterNether.makeID("cincinnasite_bars"), NetherBlocks.CINCINNASITE_BARS)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("###", "###")
                  .addMaterial('#', NetherItems.CINCINNASITE_INGOT)
                  .setGroup("nether_cincinnasite_bars")
                  .setOutputCount(16)
                  .build();
        GridRecipe.make(BetterNether.makeID("cincinnasite_block"), NetherBlocks.CINCINNASITE_BLOCK)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("##", "##")
                  .addMaterial('#', NetherItems.CINCINNASITE)
                  .setGroup("nether_cincinnasite_block")
                  .build();
        GridRecipe.make(BetterNether.makeID("cincinnasite_brick_plate"), NetherBlocks.CINCINNASITE_BRICK_PLATE)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape(" # ", "BBB", " # ")
                  .addMaterial('#', NetherBlocks.CINCINNASITE_FORGED)
                  .addMaterial('B', Items.NETHER_BRICK)
                  .setGroup("nether_cincinnasite_brick_plate")
                  .setOutputCount(5)
                  .build();
        GridRecipe.make(BetterNether.makeID("cincinnasite_bricks"), NetherBlocks.CINCINNASITE_BRICKS)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("#B", "B#")
                  .addMaterial('#', NetherBlocks.CINCINNASITE_FORGED)
                  .addMaterial('B', Items.NETHER_BRICK)
                  .setGroup("nether_cincinnasite_bricks")
                  .setOutputCount(4)
                  .build();
        GridRecipe.make(BetterNether.makeID("cincinnasite_bricks_pillar"), NetherBlocks.CINCINNASITE_BRICKS_PILLAR)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("#", "#")
                  .addMaterial('#', NetherBlocks.CINCINNASITE_BRICKS)
                  .setGroup("nether_cincinnasite_bricks_pillar")
                  .setOutputCount(2)
                  .build();
        GridRecipe.make(BetterNether.makeID("cincinnasite_button"), NetherBlocks.CINCINNASITE_BUTTON)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("#")
                  .addMaterial('#', NetherItems.CINCINNASITE_INGOT)
                  .setGroup("nether_cincinnasite_button")
                  .build();
        GridRecipe.make(BetterNether.makeID("cincinnasite_carved"), NetherBlocks.CINCINNASITE_CARVED)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("##", "##")
                  .addMaterial('#', NetherBlocks.CINCINNASITE_FORGED)
                  .setGroup("nether_cincinnasite_carved")
                  .setOutputCount(4)
                  .build();
        GridRecipe.make(BetterNether.makeID("cincinnasite_forge"), NetherBlocks.CINCINNASITE_FORGE)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("B#B", "# #", "B#B")
                  .addMaterial('#', NetherBlocks.CINCINNASITE_FORGED)
                  .addMaterial('B', Items.NETHER_BRICKS)
                  .setGroup("nether_cincinnasite_forge")
                  .build();
        GridRecipe.make(BetterNether.makeID("cincinnasite_forged_from_ingot"), NetherBlocks.CINCINNASITE_FORGED)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("##", "##")
                  .addMaterial('#', NetherItems.CINCINNASITE_INGOT)
                  .setGroup("nether_cincinnasite_forged_from_ingot")
                  .build();
        GridRecipe.make(BetterNether.makeID("cincinnasite_frame"), NetherBlocks.CINCINNASITE_FRAME)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("# #", "   ", "# #")
                  .addMaterial('#', NetherItems.CINCINNASITE_INGOT)
                  .setGroup("nether_cincinnasite_frame")
                  .setOutputCount(16)
                  .build();

        GridRecipe.make(BetterNether.makeID("cincinnasite_lantern"), NetherBlocks.CINCINNASITE_LANTERN)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape(" # ", "#G#", " # ")
                  .addMaterial('#', NetherItems.CINCINNASITE_INGOT)
                  .addMaterial('G', Items.GLOWSTONE)
                  .setGroup("nether_cincinnasite_lantern")
                  .build();
        GridRecipe.make(BetterNether.makeID("cincinnasite_lantern_small"), NetherBlocks.CINCINNASITE_LANTERN_SMALL)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("I", "L")
                  .addMaterial('I', NetherItems.CINCINNASITE_INGOT)
                  .addMaterial('L', NetherBlocks.CINCINNASITE_LANTERN)
                  .setGroup("nether_cincinnasite_lantern_small")
                  .setOutputCount(4)
                  .build();
        GridRecipe.make(BetterNether.makeID("cincinnasite_pedestal"), NetherBlocks.CINCINNASITE_PEDESTAL)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("##", "##", "##")
                  .addMaterial('#', NetherBlocks.CINCINNASITE_FORGED)
                  .setGroup("nether_cincinnasite_pedestal")
                  .setOutputCount(2)
                  .build();
        GridRecipe.make(BetterNether.makeID("cincinnasite_pillar"), NetherBlocks.CINCINNASITE_PILLAR)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("#", "#")
                  .addMaterial('#', NetherBlocks.CINCINNASITE_FORGED)
                  .setGroup("nether_cincinnasite_pillar")
                  .setOutputCount(2)
                  .build();
        GridRecipe.make(BetterNether.makeID("cincinnasite_pot"), NetherBlocks.CINCINNASITE_POT)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("#N#", " # ")
                  .addMaterial('#', NetherItems.CINCINNASITE_INGOT)
                  .addMaterial('N', Items.SOUL_SAND)
                  .setGroup("nether_cincinnasite_pot")
                  .build();
        GridRecipe.make(BetterNether.makeID("cincinnasite_tile_large"), NetherBlocks.CINCINNASITE_TILE_LARGE)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("#", "#")
                  .addMaterial('#', NetherBlocks.CINCINNASITE_SLAB)
                  .setGroup("nether_cincinnasite_tile_large")
                  .build();
        GridRecipe.make(BetterNether.makeID("cincinnasite_tile_small"), NetherBlocks.CINCINNASITE_TILE_SMALL)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("##", "##")
                  .addMaterial('#', NetherBlocks.CINCINNASITE_TILE_LARGE)
                  .setGroup("nether_cincinnasite_tile_small")
                  .setOutputCount(4)
                  .build();


        GridRecipe.make(BetterNether.makeID("nether_brewing_stand"), NetherBlocks.NETHER_BREWING_STAND)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape(" I ", " S ", "###")
                  .addMaterial('I', NetherItems.CINCINNASITE_INGOT)
                  .addMaterial('S', Items.BLAZE_ROD)
                  .addMaterial('#', Items.NETHER_BRICKS)
                  .setGroup("nether_nether_brewing_stand")
                  .build();
        GridRecipe.make(BetterNether.makeID("nether_ruby_block"), NetherBlocks.NETHER_RUBY_BLOCK)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("###", "###", "###")
                  .addMaterial('#', NetherItems.NETHER_RUBY)
                  .setGroup("nether_nether_ruby_block")
                  .build();

        GridRecipe.make(BetterNether.makeID("nether_tile_large"), NetherBlocks.NETHER_BRICK_TILE_LARGE)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("##", "##")
                  .addMaterial('#', Items.NETHER_BRICK_SLAB)
                  .setGroup("nether_nether_tile_large")
                  .setOutputCount(2)
                  .build();
        GridRecipe.make(BetterNether.makeID("nether_tile_small"), NetherBlocks.NETHER_BRICK_TILE_SMALL)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("##", "##")
                  .addMaterial('#', NetherBlocks.NETHER_BRICK_TILE_LARGE)
                  .setGroup("nether_nether_tile_small")
                  .setOutputCount(4)
                  .build();

        GridRecipe.make(BetterNether.makeID("quartz_glass_framed"), NetherBlocks.QUARTZ_GLASS_FRAMED)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("G#G", "# #", "G#G")
                  .addMaterial('#', NetherItems.CINCINNASITE_INGOT)
                  .addMaterial('G', NetherBlocks.QUARTZ_GLASS)
                  .setGroup("nether_quartz_glass_framed")
                  .setOutputCount(8)
                  .build();
        GridRecipe.make(BetterNether.makeID("quartz_glass_framed_pane"), NetherBlocks.QUARTZ_GLASS_FRAMED_PANE)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("###", "###")
                  .addMaterial('#', NetherBlocks.QUARTZ_GLASS_FRAMED)
                  .setGroup("nether_quartz_glass_framed_pane")
                  .setOutputCount(16)
                  .build();
        GridRecipe.make(BetterNether.makeID("quartz_glass_pane"), NetherBlocks.QUARTZ_GLASS_PANE)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("###", "###")
                  .addMaterial('#', NetherBlocks.QUARTZ_GLASS)
                  .setGroup("nether_quartz_glass_pane")
                  .setOutputCount(16)
                  .build();


        GridRecipe.make(BetterNether.makeID("wall_moss"), NetherBlocks.WALL_MOSS)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("#")
                  .addMaterial('#', NetherBlocks.NETHER_GRASS)
                  .setGroup("nether_wall_moss")
                  .build();

        GridRecipe.make(BetterNether.makeID("whispering_gourd_lantern"), NetherBlocks.WHISPERING_GOURD_LANTERN)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("#", "T")
                  .addMaterial('#', NetherBlocks.WHISPERING_GOURD)
                  .addMaterial('T', Items.TORCH)
                  .setGroup("nether_whispering_gourd_lantern")
                  .build();


        GridRecipe.make(BetterNether.makeID("blue_obsidian_bricks"), NetherBlocks.BLUE_OBSIDIAN_BRICKS)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("##", "##")
                  .addMaterial('#', NetherBlocks.BLUE_OBSIDIAN_TILE)
                  .setGroup("nether_blue_obsidian_bricks")
                  .setOutputCount(4)
                  .build();
        GridRecipe.make(BetterNether.makeID("blue_obsidian_glass_pane"), NetherBlocks.BLUE_OBSIDIAN_GLASS_PANE)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("###", "###")
                  .addMaterial('#', NetherBlocks.BLUE_OBSIDIAN_GLASS)
                  .setGroup("nether_blue_obsidian_glass_pane")
                  .setOutputCount(16)
                  .build();
        GridRecipe.make(BetterNether.makeID("blue_obsidian_rod_tiles"), NetherBlocks.BLUE_OBSIDIAN_ROD_TILES)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape(" ##", "## ")
                  .addMaterial('#', NetherBlocks.BLUE_OBSIDIAN_TILE)
                  .setGroup("nether_blue_obsidian_rod_tiles")
                  .setOutputCount(4)
                  .build();
        GridRecipe.make(BetterNether.makeID("blue_obsidian_tile"), NetherBlocks.BLUE_OBSIDIAN_TILE)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("##", "##")
                  .addMaterial('#', NetherBlocks.BLUE_OBSIDIAN)
                  .setGroup("nether_blue_obsidian_tile")
                  .setOutputCount(4)
                  .build();
        GridRecipe.make(BetterNether.makeID("blue_obsidian_tile_small"), NetherBlocks.BLUE_OBSIDIAN_TILE_SMALL)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("##", "##")
                  .addMaterial('#', NetherBlocks.BLUE_OBSIDIAN_BRICKS)
                  .setGroup("nether_blue_obsidian_tile_small")
                  .setOutputCount(4)
                  .build();
        GridRecipe.make(BetterNether.makeID("cincinnasite_anvil"), NetherBlocks.CINCINNASITE_ANVIL)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("###", " # ", "BBB")
                  .addMaterial('#', NetherBlocks.CINCINNASITE_FORGED)
                  .addMaterial('B', Items.NETHER_BRICKS)
                  .setGroup("nether_cincinnasite_anvil")
                  .build();
        GridRecipe.make(BetterNether.makeID("obsidian_bricks"), NetherBlocks.OBSIDIAN_BRICKS)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("##", "##")
                  .addMaterial('#', NetherBlocks.OBSIDIAN_TILE)
                  .setGroup("nether_obsidian_bricks")
                  .setOutputCount(4)
                  .build();
        GridRecipe.make(BetterNether.makeID("obsidian_glass_pane"), NetherBlocks.OBSIDIAN_GLASS_PANE)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("###", "###")
                  .addMaterial('#', NetherBlocks.OBSIDIAN_GLASS)
                  .setGroup("nether_obsidian_glass_pane")
                  .setOutputCount(16)
                  .build();
        GridRecipe.make(BetterNether.makeID("obsidian_rod_tiles"), NetherBlocks.OBSIDIAN_ROD_TILES)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape(" ##", "## ")
                  .addMaterial('#', NetherBlocks.OBSIDIAN_TILE)
                  .setGroup("nether_obsidian_rod_tiles")
                  .setOutputCount(4)
                  .build();
        GridRecipe.make(BetterNether.makeID("obsidian_tile"), NetherBlocks.OBSIDIAN_TILE)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("##", "##")
                  .addMaterial('#', Items.OBSIDIAN)
                  .setGroup("nether_obsidian_tile")
                  .setOutputCount(4)
                  .build();
        GridRecipe.make(BetterNether.makeID("obsidian_tile_small"), NetherBlocks.OBSIDIAN_TILE_SMALL)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("##", "##")
                  .addMaterial('#', NetherBlocks.OBSIDIAN_BRICKS)
                  .setGroup("nether_obsidian_tile_small")
                  .setOutputCount(4)
                  .build();

        GridRecipe.make(BetterNether.makeID("farmland"), NetherBlocks.FARMLAND)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("#S#", "#N#", "#H#")
                  .addMaterial('#', NetherBlocks.MAT_STALAGNATE.getPlanks())
                  .addMaterial('H', NetherBlocks.MAT_STALAGNATE.getSlab())
                  .addMaterial('N', Items.NETHERRACK)
                  .addMaterial('S', Items.SOUL_SAND)
                  .setGroup("nether_farmland")
                  .setOutputCount(4)
                  .build();

        GridRecipe.make(BetterNether.makeID("bone_reed_door"), NetherBlocks.BONE_REED_DOOR)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("AB", "BB", "BA")
                  .addMaterial('A', NetherBlocks.MAT_REED.getPlanks())
                  .addMaterial('B', NetherBlocks.BONE_BLOCK)
                  .setGroup("nether_bone_reed_door")
                  .setOutputCount(3)
                  .build();

        GridRecipe.make(BetterNether.makeID("chest_of_drawers"), NetherBlocks.CHEST_OF_DRAWERS)
                  .checkConfig(Configs.RECIPE_CONFIG)
                  .setShape("C#C", "# #", "C#C")
                  .addMaterial('C', NetherBlocks.CINCINNASITE_FORGED)
                  .addMaterial('#', NetherBlocks.MAT_REED.getPlanks())
                  .setGroup("nether_chest_of_drawers")
                  .build();
    }
}
