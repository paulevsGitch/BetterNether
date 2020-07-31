package paulevs.betternether.registry;

import java.util.ArrayList;
import java.util.List;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.PressurePlateBlock.ActivationRule;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blocks.*;
import paulevs.betternether.blocks.complex.ColoredGlassMaterial;
import paulevs.betternether.config.Config;
import paulevs.betternether.recipes.RecipesHelper;
import paulevs.betternether.structures.plants.StructureGoldenLumabusVine;
import paulevs.betternether.structures.plants.StructureLumabusVine;
import paulevs.betternether.tab.CreativeTab;

public class BlocksRegistry
{
	private static final List<String> BLOCKS = new ArrayList<String>();
	
	// Stalagnate //
	public static final Block STALAGNATE = registerBlockNI("stalagnate", new BlockStalagnate());
	public static final Block STALAGNATE_STEM = registerBlock("stalagnate_stem", new BlockStalagnateStem());
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
	
	// Reed //
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
	
	// Cincinnasite //
	public static final Block CINCINNASITE_ORE = registerBlock("cincinnasite_ore", new BlockOre(ItemsRegistry.CINCINNASITE, 1, 3));
	public static final Block CINCINNASITE_BLOCK = registerBlock("cincinnasite_block", new BlockCincinnasite());
	public static final Block CINCINNASITE_FORGED = registerBlock("cincinnasite_forged", new BlockCincinnasite());
	public static final Block CINCINNASITE_PILLAR = registerBlock("cincinnasite_pillar", new BlockCincinnasitPillar());
	public static final Block CINCINNASITE_BRICKS = registerBlock("cincinnasite_bricks", new BlockCincinnasite());
	public static final Block CINCINNASITE_BRICK_PLATE = registerBlock("cincinnasite_brick_plate", new BlockCincinnasite());
	public static final Block CINCINNASITE_STAIRS = registerStairs("cincinnasite_stairs", CINCINNASITE_FORGED);
	public static final Block CINCINNASITE_SLAB = registerSlab("cincinnasite_slab", CINCINNASITE_FORGED);
	public static final Block CINCINNASITE_BUTTON = registerBlock("cincinnasite_button", new BNButton(CINCINNASITE_FORGED));
	public static final Block CINCINNASITE_PLATE = registerPlate("cincinnasite_plate", CINCINNASITE_FORGED, ActivationRule.MOBS);
	public static final Block CINCINNASITE_LANTERN = registerBlock("cincinnasite_lantern", new BlockCincinnasiteLantern());
	public static final Block CINCINNASITE_TILE_LARGE = registerBlock("cincinnasite_tile_large", new BlockCincinnasite());
	public static final Block CINCINNASITE_TILE_SMALL = registerBlock("cincinnasite_tile_small", new BlockCincinnasite());
	public static final Block CINCINNASITE_CARVED = registerBlock("cincinnasite_carved", new BlockCincinnasite());
	public static final Block CINCINNASITE_WALL = registerWall("cincinnasite_wall", CINCINNASITE_FORGED);
	public static final Block CINCINNASITE_FIRE_BOWL = registerBlock("cincinnasite_fire_bowl", new BlockCincinnasiteFireBowl());
	public static final Block CINCINNASITE_BRICKS_PILLAR = registerBlock("cincinnasite_bricks_pillar", new BNPillar(CINCINNASITE_FORGED));
	public static final Block CINCINNASITE_BARS = registerBlock("cincinnasite_bars", new BNPane(CINCINNASITE_FORGED, true));
	public static final Block CINCINNASITE_PEDESTAL = registerBlock("cincinnasite_pedestal", new BlockCincinnasitePedestal());
	public static final Block CINCINNASITE_FRAME = registerBlock("cincinnasite_frame", new BlockCincinnasiteFrame());
	public static final Block CINCINNASITE_LANTERN_SMALL = registerBlock("cincinnasite_lantern_small", new BlockSmallLantern());
	public static final Block CINCINNASITE_CHAIN = registerBlock("cincinnasite_chain", new BNChain());
	
	// Ruby //
	public static final Block NETHER_RUBY_ORE = registerBlock("nether_ruby_ore", new BlockOre(ItemsRegistry.NETHER_RUBY, 1, 2));
	public static final Block NETHER_RUBY_BLOCK = registerBlock("nether_ruby_block", new BlockNetherRuby());
	public static final Block NETHER_RUBY_STAIRS = registerStairs("nether_ruby_stairs", NETHER_RUBY_BLOCK);
	public static final Block NETHER_RUBY_SLAB = registerSlab("nether_ruby_slab", NETHER_RUBY_BLOCK);
	
	// Lapis
	public static final Block NETHER_LAPIS_ORE = registerBlock("nether_lapis_ore", new BlockOre(ItemsRegistry.LAPIS_PILE, 3, 6));
	
	// Bricks //
	public static final Block NETHER_BRICK_TILE_LARGE = registerBlock("nether_brick_tile_large", new BNNetherBrick());
	public static final Block NETHER_BRICK_TILE_SMALL = registerBlock("nether_brick_tile_small", new BNNetherBrick());
	public static final Block NETHER_BRICK_WALL = registerWall("nether_brick_wall", NETHER_BRICK_TILE_LARGE);
	public static final Block NETHER_BRICK_TILE_SLAB = registerSlab("nether_brick_tile_slab", NETHER_BRICK_TILE_SMALL);
	public static final Block NETHER_BRICK_TILE_STAIRS = registerStairs("nether_brick_tile_stairs", NETHER_BRICK_TILE_SMALL);
	
	// Bone //
	public static final Block BONE_BLOCK = registerBlock("bone_block", new BNBoneBlock());
	public static final Block BONE_STAIRS = registerStairs("bone_stairs", BONE_BLOCK);
	public static final Block BONE_SLAB = registerSlab("bone_slab", BONE_BLOCK);
	public static final Block BONE_BUTTON = registerButton("bone_button", BONE_BLOCK);
	public static final Block BONE_PLATE = registerPlate("bone_plate", BONE_BLOCK);
	public static final Block BONE_WALL = registerWall("bone_wall", BONE_BLOCK);
	public static final Block BONE_TILE = registerBlock("bone_tile", new BNBoneBlock());
	public static final Block BONE_REED_DOOR = registerBlock("bone_reed_door", new BNDoor(BONE_BLOCK));
	public static final Block BONE_CINCINNASITE_DOOR = registerBlock("bone_cincinnasite_door", new BNDoor(BONE_BLOCK));
	
	// Quartz Glass //
	public static final Block QUARTZ_GLASS = registerBlock("quartz_glass", new BNGlass(Blocks.GLASS));
	public static final Block QUARTZ_GLASS_FRAMED = registerBlock("quartz_glass_framed", new BNGlass(CINCINNASITE_BLOCK));
	public static final Block QUARTZ_GLASS_PANE = registerBlock("quartz_glass_pane", new BNPane(QUARTZ_GLASS, true));
	public static final Block QUARTZ_GLASS_FRAMED_PANE = registerBlock("quartz_glass_framed_pane", new BNPane(CINCINNASITE_BLOCK, true));
	
	// Quartz Glass Colored //
	public static final ColoredGlassMaterial QUARTZ_GLASS_COLORED = new ColoredGlassMaterial("quartz_glass", QUARTZ_GLASS);
	public static final ColoredGlassMaterial QUARTZ_GLASS_FRAMED_COLORED = new ColoredGlassMaterial("quartz_glass_framed", QUARTZ_GLASS_FRAMED);
	public static final ColoredGlassMaterial QUARTZ_GLASS_PANE_COLORED = new ColoredGlassMaterial("quartz_glass_pane", QUARTZ_GLASS_PANE, false);
	public static final ColoredGlassMaterial QUARTZ_GLASS_FRAMED_PANE_COLORED = new ColoredGlassMaterial("quartz_glass_framed_pane", QUARTZ_GLASS_FRAMED_PANE, true);
	
	// Obsidian //
	public static final Block OBSIDIAN_BRICKS = registerBlock("obsidian_bricks", new BNObsidian());
	public static final Block OBSIDIAN_BRICKS_STAIRS = registerStairs("obsidian_bricks_stairs", OBSIDIAN_BRICKS);
	public static final Block OBSIDIAN_BRICKS_SLAB = registerSlab("obsidian_bricks_slab", OBSIDIAN_BRICKS);
	public static final Block OBSIDIAN_TILE = registerBlock("obsidian_tile", new BNObsidian());
	public static final Block OBSIDIAN_TILE_SMALL = registerBlock("obsidian_tile_small", new BNObsidian());
	public static final Block OBSIDIAN_TILE_STAIRS = registerStairs("obsidian_tile_stairs", OBSIDIAN_TILE_SMALL);
	public static final Block OBSIDIAN_TILE_SLAB = registerSlab("obsidian_tile_slab", OBSIDIAN_TILE_SMALL);
	public static final Block OBSIDIAN_GLASS = registerBlock("obsidian_glass", new BlockObsidianGlass());
	public static final Block OBSIDIAN_GLASS_PANE = registerBlock("obsidian_glass_pane", new BNPane(OBSIDIAN_GLASS, true));
	public static final Block BLUE_OBSIDIAN = registerBlock("blue_obsidian", new BNObsidian());
	public static final Block BLUE_OBSIDIAN_BRICKS = registerBlock("blue_obsidian_bricks", new BNObsidian());
	public static final Block BLUE_OBSIDIAN_BRICKS_STAIRS = registerStairs("blue_obsidian_bricks_stairs", BLUE_OBSIDIAN_BRICKS);
	public static final Block BLUE_OBSIDIAN_BRICKS_SLAB = registerSlab("blue_obsidian_bricks_slab", BLUE_OBSIDIAN_BRICKS);
	public static final Block BLUE_OBSIDIAN_TILE = registerBlock("blue_obsidian_tile", new BNObsidian());
	public static final Block BLUE_OBSIDIAN_TILE_SMALL = registerBlock("blue_obsidian_tile_small", new BNObsidian());
	public static final Block BLUE_OBSIDIAN_TILE_STAIRS = registerStairs("blue_obsidian_tile_stairs", BLUE_OBSIDIAN_TILE_SMALL);
	public static final Block BLUE_OBSIDIAN_TILE_SLAB = registerSlab("blue_obsidian_tile_slab", BLUE_OBSIDIAN_TILE_SMALL);
	public static final Block BLUE_OBSIDIAN_GLASS = registerBlock("blue_obsidian_glass", new BlockObsidianGlass());
	public static final Block BLUE_OBSIDIAN_GLASS_PANE = registerBlock("blue_obsidian_glass_pane", new BNPane(BLUE_OBSIDIAN_GLASS, true));
	
	// Soul Sandstone //
	public static final Block SOUL_SANDSTONE = registerBlock("soul_sandstone", new BlockSoulSandstone());
	public static final Block SOUL_SANDSTONE_CUT = registerMakeable2X2("soul_sandstone_cut", new BlockSoulSandstone(), "soul_sandstone", SOUL_SANDSTONE);
	public static final Block SOUL_SANDSTONE_SMOOTH = registerMakeable2X2("soul_sandstone_smooth", new BlockBase(FabricBlockSettings.copyOf(Blocks.SANDSTONE)), "soul_sandstone", SOUL_SANDSTONE_CUT);
	public static final Block SOUL_SANDSTONE_CHISELED = registerMakeable2X2("soul_sandstone_chiseled", new BlockBase(FabricBlockSettings.copyOf(Blocks.SANDSTONE)), "soul_sandstone", SOUL_SANDSTONE_SMOOTH);
	
	public static final Block SOUL_SANDSTONE_STAIRS = registerStairs("soul_sandstone_stairs", SOUL_SANDSTONE);
	public static final Block SOUL_SANDSTONE_CUT_STAIRS = registerStairs("soul_sandstone_cut_stairs", SOUL_SANDSTONE_CUT);
	public static final Block SOUL_SANDSTONE_SMOOTH_STAIRS = registerStairs("soul_sandstone_smooth_stairs", SOUL_SANDSTONE_SMOOTH);
	
	public static final Block SOUL_SANDSTONE_SLAB = registerSlab("soul_sandstone_slab", SOUL_SANDSTONE);
	public static final Block SOUL_SANDSTONE_CUT_SLAB = registerSlab("soul_sandstone_cut_slab", SOUL_SANDSTONE_CUT);
	public static final Block SOUL_SANDSTONE_SMOOTH_SLAB = registerSlab("soul_sandstone_smooth_slab", SOUL_SANDSTONE_SMOOTH);
	
	public static final Block SOUL_SANDSTONE_WALL = registerWall("soul_sandstone_wall", SOUL_SANDSTONE_CUT);
	
	// Basalt Bricks //
	public static final Block BASALT_BRICKS = registerMakeable2X2("basalt_bricks", new BlockBase(FabricBlockSettings.copyOf(Blocks.BASALT)), "basalt_bricks", Blocks.POLISHED_BASALT);
	public static final Block BASALT_BRICKS_STAIRS = registerStairs("basalt_bricks_stairs", BASALT_BRICKS);
	public static final Block BASALT_BRICKS_SLAB = registerSlab("basalt_bricks_slab", BASALT_BRICKS);
	public static final Block BASALT_BRICKS_WALL = registerWall("basalt_bricks_wall", BASALT_BRICKS);
	
	// Willow //
	public static final Block WILLOW_BRANCH = registerBlockNI("willow_branch", new BlockWillowBranch());
	public static final Block WILLOW_LEAVES = registerBlock("willow_leaves", new BlockWillowLeaves());
	public static final Block WILLOW_TRUNK = registerBlockNI("willow_trunk", new BlockWillowTrunk());
	public static final Block STRIPED_LOG_WILLOW = registerBlock("striped_log_willow", new BNPillar(MaterialColor.PINK));
	public static final Block STRIPED_BARK_WILLOW = registerBlock("striped_bark_willow", new BNPillar(MaterialColor.PINK));
	public static final Block WILLOW_LOG = registerBlock("willow_log", new BNLogStripable(MaterialColor.RED_TERRACOTTA, STRIPED_LOG_WILLOW));
	public static final Block WILLOW_BARK = registerBark("willow_bark", new BNLogStripable(MaterialColor.RED_TERRACOTTA, STRIPED_BARK_WILLOW), WILLOW_LOG);
	public static final Block WILLOW_SAPLING = registerBlock("willow_sapling", new BlockWillowSapling());
	public static final Block WILLOW_PLANKS = registerPlanks("willow_planks", new BNPlanks(MaterialColor.RED_TERRACOTTA), WILLOW_LOG, WILLOW_BARK, STRIPED_LOG_WILLOW, STRIPED_BARK_WILLOW);
	public static final Block WILLOW_STAIRS = registerStairs("willow_stairs", WILLOW_PLANKS);
	public static final Block WILLOW_SLAB = registerSlab("willow_slab", WILLOW_PLANKS);
	public static final Block WILLOW_FENCE = registerFence("willow_fence", WILLOW_PLANKS);
	public static final Block WILLOW_GATE = registerGate("willow_gate", WILLOW_PLANKS);
	public static final Block WILLOW_BUTTON = registerButton("willow_button", WILLOW_PLANKS);
	public static final Block WILLOW_PLATE = registerPlate("willow_plate", WILLOW_PLANKS);
	public static final Block WILLOW_TRAPDOOR = registerTrapdoor("willow_trapdoor", WILLOW_PLANKS);
	public static final Block WILLOW_DOOR = registerDoor("willow_door", WILLOW_PLANKS);
	
	// Wart //
	public static final Block STRIPED_LOG_WART = registerBlock("striped_log_wart", new BNPillar(MaterialColor.RED));
	public static final Block STRIPED_BARK_WART = registerBlock("striped_bark_wart", new BNPillar(MaterialColor.RED));
	public static final Block WART_LOG = registerBlock("wart_log", new BNLogStripable(MaterialColor.RED, STRIPED_LOG_WART));
	public static final Block WART_BARK = registerBark("wart_bark", new BNLogStripable(MaterialColor.RED, STRIPED_BARK_WART), WART_LOG);
	public static final Block WART_ROOTS = registerBlockNI("wart_roots", new BlockWartRoots());
	public static final Block WART_PLANKS = registerPlanks("wart_planks", new BNPlanks(MaterialColor.RED), WART_LOG, WART_BARK, STRIPED_LOG_WART, STRIPED_BARK_WART);
	public static final Block WART_STAIRS = registerStairs("wart_stairs", WART_PLANKS);
	public static final Block WART_SLAB = registerSlab("wart_slab", WART_PLANKS);
	public static final Block WART_FENCE = registerFence("wart_fence", WART_PLANKS);
	public static final Block WART_GATE = registerGate("wart_gate", WART_PLANKS);
	public static final Block WART_BUTTON = registerButton("wart_button", WART_PLANKS);
	public static final Block WART_PLATE = registerPlate("wart_plate", WART_PLANKS);
	public static final Block WART_TRAPDOOR = registerTrapdoor("wart_trapdoor", WART_PLANKS);
	public static final Block WART_DOOR = registerDoor("wart_door", WART_PLANKS);
	
	// Rubeus
	public static final Block RUBEUS_SAPLING = registerBlock("rubeus_sapling",  new BlockRubeusSapling());
	public static final Block RUBEUS_CONE = registerBlock("rubeus_cone",  new BlockRubeusCone());
	public static final Block STRIPED_LOG_RUBEUS = registerBlock("striped_log_rubeus", new BNPillar(MaterialColor.MAGENTA));
	public static final Block STRIPED_BARK_RUBEUS = registerBlock("striped_bark_rubeus", new BNPillar(MaterialColor.MAGENTA));
	public static final Block RUBEUS_LOG = registerBlock("rubeus_log", new RubeusLog(STRIPED_LOG_RUBEUS));
	public static final Block RUBEUS_BARK = registerBark("rubeus_bark", new RubeusLog(STRIPED_BARK_RUBEUS), RUBEUS_LOG);
	public static final Block RUBEUS_LEAVES = registerBlock("rubeus_leaves", new BlockRubeusLeaves());
	public static final Block RUBEUS_PLANKS = registerPlanks("rubeus_planks", new BNPlanks(MaterialColor.MAGENTA), RUBEUS_LOG, RUBEUS_BARK, STRIPED_LOG_RUBEUS, STRIPED_BARK_RUBEUS);
	public static final Block RUBEUS_STAIRS = registerStairs("rubeus_stairs", RUBEUS_PLANKS);
	public static final Block RUBEUS_SLAB = registerSlab("rubeus_slab", RUBEUS_PLANKS);
	public static final Block RUBEUS_FENCE = registerFence("rubeus_fence", RUBEUS_PLANKS);
	public static final Block RUBEUS_GATE = registerGate("rubeus_gate", RUBEUS_PLANKS);
	public static final Block RUBEUS_BUTTON = registerButton("rubeus_button", RUBEUS_PLANKS);
	public static final Block RUBEUS_PLATE = registerPlate("rubeus_plate", RUBEUS_PLANKS);
	public static final Block RUBEUS_TRAPDOOR = registerTrapdoor("rubeus_trapdoor", RUBEUS_PLANKS);
	public static final Block RUBEUS_DOOR = registerDoor("rubeus_door", RUBEUS_PLANKS);
	
	// Mushroom //
	public static final Block MUSHROOM_STEM = registerBlock("mushroom_stem", new BlockStalagnateStem());
	public static final Block MUSHROOM_PLANKS = registerPlanks("mushroom_planks", new BNPlanks(MaterialColor.LIGHT_GRAY), 2, MUSHROOM_STEM);
	public static final Block MUSHROOM_STAIRS = registerStairs("mushroom_stairs", MUSHROOM_PLANKS);
	public static final Block MUSHROOM_SLAB = registerSlab("mushroom_slab", MUSHROOM_PLANKS);
	public static final Block MUSHROOM_FENCE = registerFence("mushroom_fence", MUSHROOM_PLANKS);
	public static final Block MUSHROOM_GATE = registerGate("mushroom_gate", MUSHROOM_PLANKS);
	public static final Block MUSHROOM_BUTTON = registerButton("mushroom_button", MUSHROOM_PLANKS);
	public static final Block MUSHROOM_PLATE = registerPlate("mushroom_plate", MUSHROOM_PLANKS);
	public static final Block MUSHROOM_TRAPDOOR = registerTrapdoor("mushroom_trapdoor", MUSHROOM_PLANKS);
	public static final Block MUSHROOM_DOOR = registerDoor("mushroom_door", MUSHROOM_PLANKS);
	
	// Soul lily //
	public static final Block SOUL_LILY = registerBlockNI("soul_lily", new BlockSoulLily());
	public static final Block SOUL_LILY_SAPLING = registerBlock("soul_lily_sapling", new BlockSoulLilySapling());
	
	// Large & Small Mushrooms //
	public static final Block RED_LARGE_MUSHROOM = registerBlockNI("red_large_mushroom", new BlockRedLargeMushroom());
	public static final Block BROWN_LARGE_MUSHROOM = registerBlockNI("brown_large_mushroom", new BlockBrownLargeMushroom());
	public static final Block ORANGE_MUSHROOM = registerBlock("orange_mushroom", new BlockOrangeMushroom());
	public static final Block RED_MOLD = registerBlock("red_mold", new BlockRedMold());
	public static final Block GRAY_MOLD = registerBlock("gray_mold", new BlockGrayMold());
	
	// Lucis //
	public static final Block LUCIS_MUSHROOM = registerBlockNI("lucis_mushroom", new BlockLucisMushroom());
	public static final Block LUCIS_SPORE = registerBlock("lucis_spore", new BlockLucisSpore());
	
	// Giant Mold //
	public static final Block GIANT_MOLD = registerBlockNI("giant_mold", new BlockGiantMold());
	public static final Block GIANT_MOLD_SAPLING = registerBlock("giant_mold_sapling", new BlockGiantMoldSapling());
	
	public static final Block JELLYFISH_MUSHROOM = registerBlockNI("jellyfish_mushroom", new BlockJellyfishMushroom());
	public static final Block JELLYFISH_MUSHROOM_SAPLING = registerBlock("jellyfish_mushroom_sapling", new BlockJellyfishMushroomSapling());
	
	// Mushroom Fir //
	public static final Block MUSHROOM_FIR = registerBlockNI("mushroom_fir",  new BlockMushroomFir());
	public static final Block MUSHROOM_FIR_SAPLING = registerBlock("mushroom_fir_sapling",  new BlockMushroomFirSapling());
	public static final Block MUSHROOM_FIR_STEM = registerBlock("mushroom_fir_stem",  new BlockStalagnateStem());
	public static final Block STRIPED_LOG_MUSHROOM_FIR = registerBlock("striped_log_mushroom_fir",  new BNPillar(MaterialColor.BLUE));
	public static final Block STRIPED_WOOD_MUSHROOM_FIR = registerBlock("striped_wood_mushroom_fir",  new BNPillar(MaterialColor.BLUE));
	public static final Block MUSHROOM_FIR_LOG = registerLog("mushroom_fir_log",  new BNLogStripable(MaterialColor.BLUE, STRIPED_LOG_MUSHROOM_FIR), MUSHROOM_FIR_STEM);
	public static final Block MUSHROOM_FIR_WOOD = registerBark("mushroom_fir_wood",  new BNLogStripable(MaterialColor.BLUE, STRIPED_WOOD_MUSHROOM_FIR), MUSHROOM_FIR_LOG);
	public static final Block MUSHROOM_FIR_PLANKS = registerPlanks("mushroom_fir_planks", new BNPlanks(MaterialColor.LIGHT_GRAY), 1, MUSHROOM_FIR_STEM, MUSHROOM_FIR_LOG, MUSHROOM_FIR_WOOD, STRIPED_LOG_MUSHROOM_FIR, STRIPED_WOOD_MUSHROOM_FIR);
	public static final Block MUSHROOM_FIR_STAIRS = registerStairs("mushroom_fir_stairs", MUSHROOM_FIR_PLANKS);
	public static final Block MUSHROOM_FIR_SLAB = registerSlab("mushroom_fir_slab",  MUSHROOM_FIR_PLANKS);
	public static final Block MUSHROOM_FIR_FENCE = registerFence("mushroom_fir_fence", MUSHROOM_FIR_PLANKS);
	public static final Block MUSHROOM_FIR_GATE = registerGate("mushroom_fir_gate", MUSHROOM_FIR_PLANKS);
	public static final Block MUSHROOM_FIR_BUTTON = registerButton("mushroom_fir_button", MUSHROOM_FIR_PLANKS);
	public static final Block MUSHROOM_FIR_PLATE = registerPlate("mushroom_fir_plate",  MUSHROOM_FIR_PLANKS);
	public static final Block MUSHROOM_FIR_TRAPDOOR = registerTrapdoor("mushroom_fir_trapdoor", MUSHROOM_FIR_PLANKS);
	public static final Block MUSHROOM_FIR_DOOR = registerDoor("mushroom_fir_door", MUSHROOM_FIR_PLANKS);
	
	// Eyes //
	public static final Block EYEBALL = registerBlockNI("eyeball", new BlockEyeball());
	public static final Block EYEBALL_SMALL = registerBlockNI("eyeball_small", new BlockEyeballSmall());
	public static final Block EYE_VINE = registerBlockNI("eye_vine", new BlockEyeVine());
	public static final Block EYE_SEED = registerBlock("eye_seed", new BlockEyeSeed());
	
	// Grass //
	public static final Block NETHER_GRASS = registerBlock("nether_grass", new BlockNetherGrass());
	public static final Block SWAMP_GRASS = registerBlock("swamp_grass", new BlockNetherGrass());
	public static final Block SOUL_GRASS = registerBlock("soul_grass", new BlockSoulGrass());
	public static final Block JUNGLE_PLANT = registerBlock("jungle_plant", new BlockNetherGrass());
	public static final Block BONE_GRASS = registerBlock("bone_grass", new BlockNetherGrass());
	public static final Block SEPIA_BONE_GRASS = registerBlock("sepia_bone_grass", new BlockNetherGrass());
	
	// Vines //
	public static final Block BLACK_VINE = registerBlock("black_vine", new BlockBlackVine());
	public static final Block BLOOMING_VINE = registerBlock("blooming_vine", new BlockBlackVine());
	public static final Block GOLDEN_VINE = registerBlock("golden_vine", new BlockGoldenVine());
	public static final Block LUMABUS_SEED = registerBlock("lumabus_seed", new BlockLumabusSeed(new StructureLumabusVine()));
	public static final Block LUMABUS_VINE = registerBlockNI("lumabus_vine", new BlockLumabusVine(LUMABUS_SEED));
	public static final Block GOLDEN_LUMABUS_SEED = registerBlock("golden_lumabus_seed", new BlockLumabusSeed(new StructureGoldenLumabusVine()));
	public static final Block GOLDEN_LUMABUS_VINE = registerBlockNI("golden_lumabus_vine", new BlockLumabusVine(GOLDEN_LUMABUS_SEED));
	
	// Small Plants
	public static final Block SOUL_VEIN = registerBlock("soul_vein", new BlockSoulVein());
	public static final Block BONE_MUSHROOM = registerBlock("bone_mushroom", new BlockBoneMushroom());
	public static final Block WART_SEED = registerBlock("wart_seed", new BlockWartSeed());
	public static final Block BLACK_BUSH = registerBlock("black_bush", new BlockBlackBush());
	public static final Block INK_BUSH = registerBlockNI("ink_bush", new BlockInkBush());
	public static final Block INK_BUSH_SEED = registerBlock("ink_bush_seed", new BlockInkBushSeed());
	public static final Block SMOKER = registerBlock("smoker", new BlockSmoker());
	public static final Block EGG_PLANT = registerBlock("egg_plant", new BlockEggPlant());
	public static final Block BLACK_APPLE = registerBlockNI("black_apple", new BlockBlackApple());
	public static final Block BLACK_APPLE_SEED = registerBlock("black_apple_seed", new BlockBlackAppleSeed());
	public static final Block MAGMA_FLOWER = registerBlock("magma_flower", new BlockMagmaFlower());
	public static final Block FEATHER_FERN = registerBlock("feather_fern", new BlockFeatherFern());
	
	// Cactuses //
	public static final Block AGAVE = registerBlock("agave", new BlockAgave());
	public static final Block BARREL_CACTUS = registerBlock("barrel_cactus", new BlockBarrelCactus());
	public static final Block NETHER_CACTUS = registerBlock("nether_cactus", new BlockNetherCactus());
	
	// Wall plants
	public static final Block WALL_MOSS = registerBlock("wall_moss", new BlockPlantWall(MaterialColor.RED));
	public static final Block WALL_MUSHROOM_BROWN = registerBlock("wall_mushroom_brown", new BlockPlantWall(MaterialColor.BROWN));
	public static final Block WALL_MUSHROOM_RED = registerBlock("wall_mushroom_red", new BlockPlantWall(MaterialColor.RED));
	public static final Block JUNGLE_MOSS = registerBlock("jungle_moss", new BlockPlantWall(MaterialColor.LIME));
	
	// Decorations //
	public static final Block PIG_STATUE_RESPAWNER = registerBlock("pig_statue_respawner", new BlockStatueRespawner());
	public static final Block CINCINNASITE_POT = registerBlock("cincinnasite_pot", new BlockBNPot(CINCINNASITE_BLOCK));
	public static final Block BRICK_POT = registerBlock("brick_pot", new BlockBNPot(Blocks.NETHER_BRICKS));
	public static final Block POTTED_PLANT = registerBlockNI("potted_plant", new BlockPottedPlant());
	public static final Block GEYSER = registerBlock("geyser", new BlockGeyser());
	public static final Block NETHERRACK_STALACTITE = registerStalactite("netherrack_stalactite", Blocks.NETHERRACK);
	public static final Block GLOWSTONE_STALACTITE = registerStalactite("glowstone_stalactite", Blocks.GLOWSTONE);
	public static final Block BLACKSTONE_STALACTITE = registerStalactite("blackstone_stalactite", Blocks.BLACKSTONE);
	public static final Block BASALT_STALACTITE = registerStalactite("basalt_stalactite", Blocks.BASALT);
	public static final Block BONE_STALACTITE = registerStalactite("bone_stalactite", BONE_BLOCK);
	
	// Terrain //
	public static final Block NETHERRACK_MOSS = registerBlock("netherrack_moss", new BlockTerrain());
	public static final Block NETHER_MYCELIUM = registerBlock("nether_mycelium", new BlockNetherMycelium());
	public static final Block JUNGLE_GRASS = registerBlock("jungle_grass", new BlockTerrain());
	public static final Block MUSHROOM_GRASS = registerBlock("mushroom_grass", new BlockTerrain());
	public static final Block SEPIA_MUSHROOM_GRASS = registerBlock("sepia_mushroom_grass", new BlockTerrain());
	public static final Block VEINED_SAND = registerBlockNI("veined_sand", new BlockVeinedSand());
	public static final Block FARMLAND = registerBlock("farmland", new BlockFarmland());
	
	// Roofs //
	public static final Block ROOF_TILE_REEDS = registerRoof("roof_tile_reeds", REEDS_BLOCK);
	public static final Block ROOF_TILE_REEDS_STAIRS = registerStairs("roof_tile_reeds_stairs", ROOF_TILE_REEDS);
	public static final Block ROOF_TILE_REEDS_SLAB = registerSlab("roof_tile_reeds_slab", ROOF_TILE_REEDS);
	public static final Block ROOF_TILE_STALAGNATE = registerRoof("roof_tile_stalagnate", STALAGNATE_PLANKS);
	public static final Block ROOF_TILE_STALAGNATE_STAIRS = registerStairs("roof_tile_stalagnate_stairs", ROOF_TILE_STALAGNATE);
	public static final Block ROOF_TILE_STALAGNATE_SLAB = registerSlab("roof_tile_stalagnate_slab", ROOF_TILE_STALAGNATE);
	public static final Block ROOF_TILE_NETHER_BRICKS = registerRoof("roof_tile_nether_bricks", Blocks.NETHER_BRICKS);
	public static final Block ROOF_TILE_NETHER_BRICKS_STAIRS = registerStairs("roof_tile_nether_bricks_stairs", ROOF_TILE_NETHER_BRICKS);
	public static final Block ROOF_TILE_NETHER_BRICKS_SLAB = registerSlab("roof_tile_nether_bricks_slab", ROOF_TILE_NETHER_BRICKS);
	public static final Block ROOF_TILE_CINCINNASITE = registerRoof("roof_tile_cincinnasite", CINCINNASITE_FORGED);
	public static final Block ROOF_TILE_CINCINNASITE_STAIRS = registerStairs("roof_tile_cincinnasite_stairs", ROOF_TILE_CINCINNASITE);
	public static final Block ROOF_TILE_CINCINNASITE_SLAB = registerSlab("roof_tile_cincinnasite_slab", ROOF_TILE_CINCINNASITE);
	public static final Block ROOF_TILE_WILLOW = registerRoof("roof_tile_willow", WILLOW_PLANKS);
	public static final Block ROOF_TILE_WILLOW_STAIRS = registerStairs("roof_tile_willow_stairs", ROOF_TILE_WILLOW);
	public static final Block ROOF_TILE_WILLOW_SLAB = registerSlab("roof_tile_willow_slab", ROOF_TILE_WILLOW);
	public static final Block ROOF_TILE_WART = registerRoof("roof_tile_wart", WART_PLANKS);
	public static final Block ROOF_TILE_WART_STAIRS = registerStairs("roof_tile_wart_stairs", ROOF_TILE_WART);
	public static final Block ROOF_TILE_WART_SLAB = registerSlab("roof_tile_wart_slab", ROOF_TILE_WART);
	
	// Craft Stations //
	public static final Block BLACKSTONE_FURNACE = registerFurnace("blackstone_furnace", Blocks.BLACKSTONE);
	public static final Block BASALT_FURNACE = registerFurnace("basalt_furnace", Blocks.BASALT);
	public static final Block NETHERRACK_FURNACE = registerFurnace("netherrack_furnace", Blocks.NETHERRACK);
	public static final Block CINCINNASITE_FORGE = registerBlock("cincinnasite_forge", new BlockCincinnasiteForge());
	public static final Block NETHER_BREWING_STAND = registerBlock("nether_brewing_stand", new BNBrewingStand());
	public static final Block CINCINNASITE_ANVIL = registerBlock("cincinnasite_anvil", new BlockCincinnasiteAnvil());
	
	public static final Block CRAFTING_TABLE_CRIMSON = registerCraftingTable("crafting_table_crimson", Blocks.CRIMSON_PLANKS);
	public static final Block CRAFTING_TABLE_WARPED = registerCraftingTable("crafting_table_warped", Blocks.WARPED_PLANKS);
	
	public static final Block CRAFTING_TABLE_STALAGNATE = registerCraftingTable("crafting_table_stalagnate", STALAGNATE_PLANKS);
	public static final Block CRAFTING_TABLE_REED = registerCraftingTable("crafting_table_reed", REEDS_BLOCK);
	public static final Block CRAFTING_TABLE_WILLOW = registerCraftingTable("crafting_table_willow", WILLOW_PLANKS);
	public static final Block CRAFTING_TABLE_WART = registerCraftingTable("crafting_table_wart", WART_PLANKS);
	public static final Block CRAFTING_TABLE_RUBEUS = registerCraftingTable("crafting_table_rubeus", RUBEUS_PLANKS);
	public static final Block CRAFTING_TABLE_MUSHROOM = registerCraftingTable("crafting_table_mushroom", MUSHROOM_PLANKS);
	public static final Block CRAFTING_TABLE_MUSHROOM_FIR = registerCraftingTable("crafting_table_mushroom_fir", MUSHROOM_FIR_PLANKS);
	
	// Storage
	public static final Block CHEST_OF_DRAWERS = registerBlock("chest_of_drawers", new BlockChestOfDrawers());
	
	public static final Block CHEST_CRIMSON = registerChest("chest_crimson", Blocks.CRIMSON_PLANKS);
	public static final Block CHEST_WARPED = registerChest("chest_warped", Blocks.WARPED_PLANKS);
	
	public static final Block CHEST_STALAGNATE = registerChest("chest_stalagnate", STALAGNATE_PLANKS);
	public static final Block CHEST_REED = registerChest("chest_reed", REEDS_BLOCK);
	public static final Block CHEST_WILLOW = registerChest("chest_willow", WILLOW_PLANKS);
	public static final Block CHEST_WART = registerChest("chest_wart", WART_PLANKS);
	public static final Block CHEST_RUBEUS = registerChest("chest_rubeus", RUBEUS_PLANKS);
	public static final Block CHEST_MUSHROOM = registerChest("chest_mushroom", MUSHROOM_PLANKS);
	public static final Block CHEST_MUSHROOM_FIR = registerChest("chest_mushroom_fir", MUSHROOM_FIR_PLANKS);
	
	public static final Block BARREL_CRIMSON = registerBarrel("barrel_crimson", Blocks.CRIMSON_PLANKS, Blocks.CRIMSON_SLAB);
	public static final Block BARREL_WARPED = registerBarrel("barrel_warped", Blocks.WARPED_PLANKS, Blocks.WARPED_SLAB);
	
	public static final Block BARREL_STALAGNATE = registerBarrel("barrel_stalagnate", STALAGNATE_PLANKS, STALAGNATE_SLAB);
	public static final Block BARREL_REED = registerBarrel("barrel_reed", REEDS_BLOCK, REEDS_SLAB);
	public static final Block BARREL_WILLOW = registerBarrel("barrel_willow", WILLOW_PLANKS, WILLOW_SLAB);
	public static final Block BARREL_WART = registerBarrel("barrel_wart", WART_PLANKS, WART_SLAB);
	public static final Block BARREL_RUBEUS = registerBarrel("barrel_rubeus", RUBEUS_PLANKS, RUBEUS_SLAB);
	public static final Block BARREL_MUSHROOM = registerBarrel("barrel_mushroom", MUSHROOM_PLANKS, MUSHROOM_SLAB);
	public static final Block BARREL_MUSHROOM_FIR = registerBarrel("barrel_mushroom_fir", MUSHROOM_FIR_PLANKS, MUSHROOM_FIR_SLAB);
	
	// Taburets //
	public static final Block TABURET_OAK = registerTaburet("taburet_oak", Blocks.OAK_SLAB);
	public static final Block TABURET_SPRUCE = registerTaburet("taburet_spruce", Blocks.SPRUCE_SLAB);
	public static final Block TABURET_BIRCH = registerTaburet("taburet_birch", Blocks.BIRCH_SLAB);
	public static final Block TABURET_JUNGLE = registerTaburet("taburet_jungle", Blocks.JUNGLE_SLAB);
	public static final Block TABURET_ACACIA = registerTaburet("taburet_acacia", Blocks.ACACIA_SLAB);
	public static final Block TABURET_DARK_OAK = registerTaburet("taburet_dark_oak", Blocks.DARK_OAK_SLAB);
	public static final Block TABURET_CRIMSON = registerTaburet("taburet_crimson", Blocks.CRIMSON_SLAB);
	public static final Block TABURET_WARPED = registerTaburet("taburet_warped", Blocks.WARPED_SLAB);
	
	public static final Block TABURET_REEDS = registerTaburet("taburet_reeds", REEDS_SLAB);
	public static final Block TABURET_STALAGNATE = registerTaburet("taburet_stalagnate", STALAGNATE_SLAB);
	public static final Block TABURET_WILLOW = registerTaburet("taburet_willow", WILLOW_SLAB);
	public static final Block TABURET_WART = registerTaburet("taburet_wart", WART_SLAB);
	public static final Block TABURET_CINCINNASITE = registerTaburet("taburet_cincinnasite", CINCINNASITE_SLAB);
	public static final Block TABURET_MUSHROOM = registerTaburet("taburet_mushroom", MUSHROOM_SLAB);
	public static final Block TABURET_MUSHROOM_FIR = registerTaburet("taburet_mushroom_fir", MUSHROOM_FIR_SLAB);
	public static final Block TABURET_RUBEUS = registerTaburet("taburet_rubeus", RUBEUS_SLAB);
	
	// Chairs
	public static final Block CHAIR_OAK = registerChair("chair_oak", Blocks.OAK_SLAB);
	public static final Block CHAIR_SPRUCE = registerChair("chair_spruce", Blocks.SPRUCE_SLAB);
	public static final Block CHAIR_BIRCH = registerChair("chair_birch", Blocks.BIRCH_SLAB);
	public static final Block CHAIR_JUNGLE = registerChair("chair_jungle", Blocks.JUNGLE_SLAB);
	public static final Block CHAIR_ACACIA = registerChair("chair_acacia", Blocks.ACACIA_SLAB);
	public static final Block CHAIR_DARK_OAK = registerChair("chair_dark_oak", Blocks.DARK_OAK_SLAB);
	public static final Block CHAIR_CRIMSON = registerChair("chair_crimson", Blocks.CRIMSON_SLAB);
	public static final Block CHAIR_WARPED = registerChair("chair_warped", Blocks.WARPED_SLAB);
	
	public static final Block CHAIR_REEDS = registerChair("chair_reeds", REEDS_SLAB);
	public static final Block CHAIR_STALAGNATE = registerChair("chair_stalagnate", STALAGNATE_SLAB);
	public static final Block CHAIR_WILLOW = registerChair("chair_willow", WILLOW_SLAB);
	public static final Block CHAIR_WART = registerChair("chair_wart", WART_SLAB);
	public static final Block CHAIR_CINCINNASITE = registerChair("chair_cincinnasite", CINCINNASITE_SLAB);
	public static final Block CHAIR_MUSHROOM = registerChair("chair_mushroom", MUSHROOM_SLAB);
	public static final Block CHAIR_MUSHROOM_FIR = registerChair("chair_mushroom_fir", MUSHROOM_FIR_SLAB);
	public static final Block CHAIR_RUBEUS = registerChair("chair_rubeus", RUBEUS_SLAB);
	
	// Stools //
	public static final Block BAR_STOOL_OAK = registerBarStool("bar_stool_oak", Blocks.OAK_SLAB);
	public static final Block BAR_STOOL_SPRUCE = registerBarStool("bar_stool_spruce", Blocks.SPRUCE_SLAB);
	public static final Block BAR_STOOL_BIRCH = registerBarStool("bar_stool_birch", Blocks.BIRCH_SLAB);
	public static final Block BAR_STOOL_JUNGLE = registerBarStool("bar_stool_jungle", Blocks.JUNGLE_SLAB);
	public static final Block BAR_STOOL_ACACIA = registerBarStool("bar_stool_acacia", Blocks.ACACIA_SLAB);
	public static final Block BAR_STOOL_DARK_OAK = registerBarStool("bar_stool_dark_oak", Blocks.DARK_OAK_SLAB);
	public static final Block BAR_STOOL_CRIMSON = registerBarStool("bar_stool_crimson", Blocks.CRIMSON_SLAB);
	public static final Block BAR_STOOL_WARPED = registerBarStool("bar_stool_warped", Blocks.WARPED_SLAB);
	
	public static final Block BAR_STOOL_REEDS = registerBarStool("bar_stool_reeds", REEDS_SLAB);
	public static final Block BAR_STOOL_STALAGNATE = registerBarStool("bar_stool_stalagnate", STALAGNATE_SLAB);
	public static final Block BAR_STOOL_WILLOW = registerBarStool("bar_stool_willow", WILLOW_SLAB);
	public static final Block BAR_STOOL_WART = registerBarStool("bar_stool_wart", WART_SLAB);
	public static final Block BAR_STOOL_CINCINNASITE = registerBarStool("bar_stool_cincinnasite", CINCINNASITE_SLAB);
	public static final Block BAR_STOOL_MUSHROOM = registerBarStool("bar_stool_mushroom", MUSHROOM_SLAB);
	public static final Block BAR_STOOL_MUSHROOM_FIR = registerBarStool("bar_stool_mushroom_fir", MUSHROOM_FIR_SLAB);
	public static final Block BAR_STOOL_RUBEUS = registerBarStool("bar_stool_rubeus", RUBEUS_SLAB);
	
	// Ladders //
	public static final Block CRIMSON_LADDER = registerLadder("crimson_ladder", Blocks.CRIMSON_PLANKS);
	public static final Block WARPED_LADDER = registerLadder("warped_ladder", Blocks.WARPED_PLANKS);
	
	public static final Block REEDS_LADDER = registerLadder("reeds_ladder", REEDS_BLOCK);
	public static final Block STALAGNATE_LADDER = registerLadder("stalagnate_ladder", STALAGNATE_PLANKS);
	public static final Block WILLOW_LADDER = registerLadder("willow_ladder", WILLOW_PLANKS);
	public static final Block WART_LADDER = registerLadder("wart_ladder", WART_PLANKS);
	public static final Block MUSHROOM_LADDER = registerLadder("mushroom_ladder", MUSHROOM_PLANKS);
	public static final Block MUSHROOM_FIR_LADDER = registerLadder("mushroom_fir_ladder", MUSHROOM_FIR_PLANKS);
	public static final Block RUBEUS_LADDER = registerLadder("rubeus_ladder", RUBEUS_PLANKS);
	
	// Signs
	public static final Block SIGN_STALAGNATE = registerSign("sign_stalagnate", STALAGNATE_PLANKS);
	public static final Block SIGN_REED = registerSign("sign_reed", REEDS_BLOCK);
	public static final Block SIGN_WILLOW = registerSign("sign_willow", WILLOW_PLANKS);
	public static final Block SIGN_WART = registerSign("sign_wart", WART_PLANKS);
	public static final Block SIGN_RUBEUS = registerSign("sign_rubeus", RUBEUS_PLANKS);
	public static final Block SIGN_MUSHROOM = registerSign("sign_mushroom", MUSHROOM_PLANKS);
	public static final Block SIGN_MUSHROOM_FIR = registerSign("sign_mushroom_fir", MUSHROOM_FIR_PLANKS);
	
	//public static final WoodenMaterial TEST_MATERIAL = new WoodenMaterial("test_material", MaterialColor.BLACK);
	
	public static void register() {}
	
	public static Block registerBlock(String name, Block block)
	{
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, block);
		}
		BLOCKS.add(name);
		return block;
	}
	
	public static Block registerBlockNI(String name, Block block)
	{
		if (Config.getBoolean("blocks", name, true))
		{
			Registry.register(Registry.BLOCK, new Identifier(BetterNether.MOD_ID, name), block);
		}
		BLOCKS.add(name);
		return block;
	}
	
	private static void registerBlockDirectly(String name, Block block)
	{
		Registry.register(Registry.BLOCK, new Identifier(BetterNether.MOD_ID, name), block);
		ItemsRegistry.registerItem(name, new BlockItem(block, new Item.Settings().group(CreativeTab.BN_TAB)));
	}
	
	private static Block registerStairs(String name, Block source)
	{
		Block stairs = new BNStairs(source);
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, stairs);
			RecipesHelper.makeStairsRecipe(source, stairs);
		}
		BLOCKS.add(name);
		return stairs;
	}
	
	private static Block registerSlab(String name, Block source)
	{
		Block slab = new BNSlab(source);
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, slab);
			RecipesHelper.makeSlabRecipe(source, slab);
		}
		BLOCKS.add(name);
		return slab;
	}
	
	private static Block registerRoof(String name, Block source)
	{
		Block roof = new BlockBase(FabricBlockSettings.copyOf(source));
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, roof);
			RecipesHelper.makeRoofRecipe(source, roof);
		}
		BLOCKS.add(name);
		return roof;
	}
	
	private static Block registerButton(String name, Block source)
	{
		Block button = new BNButton(source);
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, button);
			RecipesHelper.makeButtonRecipe(source, button);
		}
		BLOCKS.add(name);
		return button;
	}
	
	private static Block registerPlate(String name, Block source)
	{
		Block plate = new BNPlate(ActivationRule.EVERYTHING, source);
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, plate);
			RecipesHelper.makePlateRecipe(source, plate);
		}
		BLOCKS.add(name);
		return plate;
	}
	
	private static Block registerPlate(String name, Block source, ActivationRule rule)
	{
		Block plate = new BNPlate(rule, source);
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, plate);
			RecipesHelper.makePlateRecipe(source, plate);
		}
		BLOCKS.add(name);
		return plate;
	}
	
	private static Block registerPlanks(String name, Block planks, Block... logs)
	{
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, planks);
			for (Block log: logs)
				RecipesHelper.makeSimpleRecipe(log, planks, 4, "nether_planks");
		}
		BLOCKS.add(name);
		return planks;
	}
	
	private static Block registerPlanks(String name, Block planks, int output, Block stem, Block... logs)
	{
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, planks);
			RecipesHelper.makeSimpleRecipe(stem, planks, output, "nether_planks");
			for (Block log: logs)
				RecipesHelper.makeSimpleRecipe(log, planks, 4, "nether_planks");
		}
		BLOCKS.add(name);
		return planks;
	}
	
	private static Block registerLog(String name, Block log, Block stem)
	{
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, log);
			RecipesHelper.makeSimpleRecipe2(stem, log, 1, "nether_stem_log");
		}
		BLOCKS.add(name);
		return log;
	}
	
	private static Block registerBark(String name, Block bark, Block log)
	{
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, bark);
			RecipesHelper.makeSimpleRecipe2(log, bark, 3, "nether_bark");
		}
		BLOCKS.add(name);
		return bark;
	}
	
	private static Block registerFence(String name, Block source)
	{
		Block fence = new BNFence(source);
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, fence);
			RecipesHelper.makeFenceRecipe(source, fence);
		}
		BLOCKS.add(name);
		return fence;
	}
	
	private static Block registerGate(String name, Block source)
	{
		Block gate = new BNGate(source);
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, gate);
			RecipesHelper.makeGateRecipe(source, gate);
		}
		BLOCKS.add(name);
		return gate;
	}
	
	private static Block registerDoor(String name, Block source)
	{
		Block door = new BNDoor(source);
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, door);
			RecipesHelper.makeDoorRecipe(source, door);
		}
		BLOCKS.add(name);
		return door;
	}
	
	private static Block registerTrapdoor(String name, Block source)
	{
		Block trapdoor = new BNTrapdoor(source);
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, trapdoor);
			RecipesHelper.makeTrapdoorRecipe(source, trapdoor);
		}
		BLOCKS.add(name);
		return trapdoor;
	}
	
	private static Block registerMakeable2X2(String name, Block result, String group, Block... sources)
	{
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, result);
			for (Block source: sources)
				RecipesHelper.makeSimpleRecipe2(source, result, 4, group);
		}
		BLOCKS.add(name);
		return result;
	}
	
	private static Block registerWall(String name, Block source)
	{
		Block wall = new BNWall(source);
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, wall);
			RecipesHelper.makeWallRecipe(source, wall);
		}
		BLOCKS.add(name);
		return wall;
	}
	
	private static Block registerCraftingTable(String name, Block source)
	{
		Block block = new BNCraftingTable(source);
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, block);
			RecipesHelper.makeSimpleRecipe2(source, block, 1, "nether_crafting_table");
		}
		BLOCKS.add(name);
		return block;
	}
	
	private static Block registerChest(String name, Block source)
	{
		Block block = new BNChest(source);
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, block);
			RecipesHelper.makeRoundRecipe(source, block, "nether_chest");
		}
		BLOCKS.add(name);
		return block;
	}
	
	private static Block registerSign(String name, Block source)
	{
		Block block = new BNSign(source);
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, block);
			RecipesHelper.makeSignRecipe(source, block);
		}
		BLOCKS.add(name);
		return block;
	}
	
	private static Block registerBarrel(String name, Block source, Block slab)
	{
		Block block = new BNBarrel(source);
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, block);
			RecipesHelper.makeBarrelRecipe(source, slab, block);
		}
		BLOCKS.add(name);
		return block;
	}
	
	private static Block registerLadder(String name, Block source)
	{
		Block block = new BNLadder(source);
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, block);
			RecipesHelper.makeLadderRecipe(source, block);
		}
		BLOCKS.add(name);
		return block;
	}
	
	private static Block registerTaburet(String name, Block source)
	{
		Block block = new BNTaburet(source);
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, block);
			RecipesHelper.makeTaburetRecipe(source, block);
		}
		BLOCKS.add(name);
		return block;
	}
	
	private static Block registerChair(String name, Block source)
	{
		Block block = new BNNormalChair(source);
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, block);
			RecipesHelper.makeChairRecipe(source, block);
		}
		BLOCKS.add(name);
		return block;
	}
	
	private static Block registerBarStool(String name, Block source)
	{
		Block block = new BNBarStool(source);
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, block);
			RecipesHelper.makeBarStoolRecipe(source, block);
		}
		BLOCKS.add(name);
		return block;
	}
	
	private static Block registerFurnace(String name, Block source)
	{
		Block block = new BlockNetherFurnace(source);
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, block);
			RecipesHelper.makeRoundRecipe(source, block, "nether_furnace");
		}
		BLOCKS.add(name);
		return block;
	}
	
	private static Block registerStalactite(String name, Block source)
	{
		Block block = new BlockStalactite(source);
		if (Config.getBoolean("blocks", name, true))
		{
			registerBlockDirectly(name, block);
			RecipesHelper.makeSimpleRecipe2(block, source, 1, "nether_stalactite");
		}
		BLOCKS.add(name);
		return block;
	}
	
	public static List<String> getPossibleBlocks()
	{
		return BLOCKS;
	}
}
