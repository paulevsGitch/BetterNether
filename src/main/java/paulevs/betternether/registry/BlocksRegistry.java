package paulevs.betternether.registry;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.PressurePlateBlock.ActivationRule;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blocks.BNBarStool;
import paulevs.betternether.blocks.BNBoneBlock;
import paulevs.betternether.blocks.BNBrewingStand;
import paulevs.betternether.blocks.BNButton;
import paulevs.betternether.blocks.BNDoor;
import paulevs.betternether.blocks.BNFence;
import paulevs.betternether.blocks.BNGate;
import paulevs.betternether.blocks.BNGlass;
import paulevs.betternether.blocks.BNLadder;
import paulevs.betternether.blocks.BNLogStripable;
import paulevs.betternether.blocks.BNNetherBrick;
import paulevs.betternether.blocks.BNNormalChair;
import paulevs.betternether.blocks.BNObsidian;
import paulevs.betternether.blocks.BNPane;
import paulevs.betternether.blocks.BNPillar;
import paulevs.betternether.blocks.BNPlanks;
import paulevs.betternether.blocks.BNPlate;
import paulevs.betternether.blocks.BNSlab;
import paulevs.betternether.blocks.BNStairs;
import paulevs.betternether.blocks.BNTaburet;
import paulevs.betternether.blocks.BNTrapdoor;
import paulevs.betternether.blocks.BNWall;
import paulevs.betternether.blocks.BlockAgave;
import paulevs.betternether.blocks.BlockBNPot;
import paulevs.betternether.blocks.BlockBarrelCactus;
import paulevs.betternether.blocks.BlockBlackApple;
import paulevs.betternether.blocks.BlockBlackAppleSeed;
import paulevs.betternether.blocks.BlockBlackBush;
import paulevs.betternether.blocks.BlockBlackVine;
import paulevs.betternether.blocks.BlockBoneMushroom;
import paulevs.betternether.blocks.BlockBrownLargeMushroom;
import paulevs.betternether.blocks.BlockChestOfDrawers;
import paulevs.betternether.blocks.BlockCincinnasitPillar;
import paulevs.betternether.blocks.BlockCincinnasite;
import paulevs.betternether.blocks.BlockCincinnasiteFireBowl;
import paulevs.betternether.blocks.BlockCincinnasiteForge;
import paulevs.betternether.blocks.BlockCincinnasiteFrame;
import paulevs.betternether.blocks.BlockCincinnasiteLantern;
import paulevs.betternether.blocks.BlockCincinnasiteOre;
import paulevs.betternether.blocks.BlockCincinnasitePedestal;
import paulevs.betternether.blocks.BlockEggPlant;
import paulevs.betternether.blocks.BlockEyeSeed;
import paulevs.betternether.blocks.BlockEyeVine;
import paulevs.betternether.blocks.BlockEyeball;
import paulevs.betternether.blocks.BlockEyeballSmall;
import paulevs.betternether.blocks.BlockFarmland;
import paulevs.betternether.blocks.BlockGeyser;
import paulevs.betternether.blocks.BlockGiantMold;
import paulevs.betternether.blocks.BlockGiantMoldSapling;
import paulevs.betternether.blocks.BlockGoldenVine;
import paulevs.betternether.blocks.BlockGrayMold;
import paulevs.betternether.blocks.BlockInkBush;
import paulevs.betternether.blocks.BlockInkBushSeed;
import paulevs.betternether.blocks.BlockLucisMushroom;
import paulevs.betternether.blocks.BlockLucisSpore;
import paulevs.betternether.blocks.BlockMagmaFlower;
import paulevs.betternether.blocks.BlockMushroomFir;
import paulevs.betternether.blocks.BlockMushroomFirSapling;
import paulevs.betternether.blocks.BlockNetherCactus;
import paulevs.betternether.blocks.BlockNetherGrass;
import paulevs.betternether.blocks.BlockNetherMycelium;
import paulevs.betternether.blocks.BlockNetherReed;
import paulevs.betternether.blocks.BlockNetherrackFurnace;
import paulevs.betternether.blocks.BlockNetherrackMoss;
import paulevs.betternether.blocks.BlockObsidianGlass;
import paulevs.betternether.blocks.BlockOrangeMushroom;
import paulevs.betternether.blocks.BlockPlantWall;
import paulevs.betternether.blocks.BlockPottedPlant;
import paulevs.betternether.blocks.BlockRedLargeMushroom;
import paulevs.betternether.blocks.BlockRedMold;
import paulevs.betternether.blocks.BlockReedsBlock;
import paulevs.betternether.blocks.BlockRubeusCone;
import paulevs.betternether.blocks.BlockRubeusLeaves;
import paulevs.betternether.blocks.BlockRubeusSapling;
import paulevs.betternether.blocks.BlockSmallLantern;
import paulevs.betternether.blocks.BlockSmoker;
import paulevs.betternether.blocks.BlockSoulGrass;
import paulevs.betternether.blocks.BlockSoulLily;
import paulevs.betternether.blocks.BlockSoulLilySapling;
import paulevs.betternether.blocks.BlockSoulVein;
import paulevs.betternether.blocks.BlockStalagnate;
import paulevs.betternether.blocks.BlockStalagnateBowl;
import paulevs.betternether.blocks.BlockStalagnateSeed;
import paulevs.betternether.blocks.BlockStalagnateStem;
import paulevs.betternether.blocks.BlockStatueRespawner;
import paulevs.betternether.blocks.BlockVeinedSand;
import paulevs.betternether.blocks.BlockWartRoots;
import paulevs.betternether.blocks.BlockWartSeed;
import paulevs.betternether.blocks.BlockWillowBranch;
import paulevs.betternether.blocks.BlockWillowLeaves;
import paulevs.betternether.blocks.BlockWillowSapling;
import paulevs.betternether.blocks.BlockWillowTrunk;
import paulevs.betternether.blocks.BlockRubeusLog;
import paulevs.betternether.config.Config;
import paulevs.betternether.tab.CreativeTab;

public class BlocksRegistry
{
	// Stalagnate //
	public static final Block STALAGNATE = registerBlockNI("stalagnate", new BlockStalagnate());
	public static final Block STALAGNATE_STEM = registerBlock("stalagnate_stem", new BlockStalagnateStem());
	public static final Block STALAGNATE_SEED = registerBlock("stalagnate_seed", new BlockStalagnateSeed());
	public static final Block STRIPED_LOG_STALAGNATE = registerBlock("striped_log_stalagnate", new BNPillar(MaterialColor.LIME_TERRACOTTA));
	public static final Block STRIPED_BARK_STALAGNATE = registerBlock("striped_bark_stalagnate", new BNPillar(MaterialColor.LIME_TERRACOTTA));
	public static final Block STALAGNATE_LOG = registerBlock("stalagnate_log", new BNLogStripable(MaterialColor.LIME_TERRACOTTA, STRIPED_LOG_STALAGNATE));
	public static final Block STALAGNATE_BARK = registerBlock("stalagnate_bark", new BNLogStripable(MaterialColor.LIME_TERRACOTTA, STRIPED_BARK_STALAGNATE));
	public static final Block STALAGNATE_PLANKS = registerBlock("stalagnate_planks", new BNPlanks(MaterialColor.LIME_TERRACOTTA));
	public static final Block STALAGNATE_PLANKS_STAIRS = registerBlock("stalagnate_planks_stairs", new BNStairs(STALAGNATE_PLANKS));
	public static final Block STALAGNATE_PLANKS_SLAB = registerBlock("stalagnate_planks_slab", new BNSlab(STALAGNATE_PLANKS));
	public static final Block STALAGNATE_PLANKS_FENCE = registerBlock("stalagnate_planks_fence", new BNFence(STALAGNATE_PLANKS));
	public static final Block STALAGNATE_PLANKS_GATE = registerBlock("stalagnate_planks_gate", new BNGate(STALAGNATE_PLANKS));
	public static final Block STALAGNATE_PLANKS_BUTTON = registerBlock("stalagnate_planks_button", new BNButton(STALAGNATE_PLANKS));
	public static final Block STALAGNATE_PLANKS_PLATE = registerBlock("stalagnate_planks_plate", new BNPlate(ActivationRule.EVERYTHING, STALAGNATE_PLANKS));
	public static final Block STALAGNATE_PLANKS_TRAPDOOR = registerBlock("stalagnate_planks_trapdoor", new BNTrapdoor(STALAGNATE_PLANKS));
	public static final Block STALAGNATE_PLANKS_DOOR = registerBlock("stalagnate_planks_door", new BNDoor(STALAGNATE_PLANKS));
	public static final Block STALAGNATE_BOWL = registerBlockNI("stalagnate_bowl", new BlockStalagnateBowl());
	
	// Reed //
	public static final Block NETHER_REED = registerBlock("nether_reed", new BlockNetherReed());
	public static final Block REEDS_BLOCK = registerBlock("reeds_block", new BlockReedsBlock());
	public static final Block REEDS_STAIRS = registerBlock("reeds_stairs", new BNStairs(REEDS_BLOCK));
	public static final Block REEDS_SLAB = registerBlock("reeds_slab", new BNSlab(REEDS_BLOCK));
	public static final Block REEDS_FENCE = registerBlock("reeds_fence", new BNFence(REEDS_BLOCK));
	public static final Block REEDS_GATE = registerBlock("reeds_gate", new BNGate(REEDS_BLOCK));
	public static final Block REEDS_BUTTON = registerBlock("reeds_button", new BNButton(REEDS_BLOCK));
	public static final Block REEDS_PLATE = registerBlock("reeds_plate", new BNPlate(ActivationRule.EVERYTHING, REEDS_BLOCK));
	public static final Block REEDS_TRAPDOOR = registerBlock("reeds_trapdoor", new BNTrapdoor(REEDS_BLOCK));
	public static final Block REEDS_DOOR = registerBlock("reeds_door", new BNDoor(REEDS_BLOCK));
	
	// Cincinnasite //
	public static final Block CINCINNASITE_ORE = registerBlock("cincinnasite_ore", new BlockCincinnasiteOre());
	public static final Block CINCINNASITE_BLOCK = registerBlock("cincinnasite_block", new BlockCincinnasite());
	public static final Block CINCINNASITE_FORGED = registerBlock("cincinnasite_forged", new BlockCincinnasite());
	public static final Block CINCINNASITE_PILLAR = registerBlock("cincinnasite_pillar", new BlockCincinnasitPillar());
	public static final Block CINCINNASITE_BRICKS = registerBlock("cincinnasite_bricks", new BlockCincinnasite());
	public static final Block CINCINNASITE_BRICK_PLATE = registerBlock("cincinnasite_brick_plate", new BlockCincinnasite());
	public static final Block CINCINNASITE_STAIRS = registerBlock("cincinnasite_stairs", new BNStairs(CINCINNASITE_BLOCK));
	public static final Block CINCINNASITE_SLAB = registerBlock("cincinnasite_slab", new BNSlab(CINCINNASITE_BLOCK));
	public static final Block CINCINNASITE_BUTTON = registerBlock("cincinnasite_button", new BNButton(CINCINNASITE_BLOCK));
	public static final Block CINCINNASITE_PLATE = registerBlock("cincinnasite_plate", new BNPlate(ActivationRule.MOBS, CINCINNASITE_BLOCK));
	public static final Block CINCINNASITE_LANTERN = registerBlock("cincinnasite_lantern", new BlockCincinnasiteLantern());
	public static final Block CINCINNASITE_TILE_LARGE = registerBlock("cincinnasite_tile_large", new BlockCincinnasite());
	public static final Block CINCINNASITE_TILE_SMALL = registerBlock("cincinnasite_tile_small", new BlockCincinnasite());
	public static final Block CINCINNASITE_CARVED = registerBlock("cincinnasite_carved", new BlockCincinnasite());
	public static final Block CINCINNASITE_WALL = registerBlock("cincinnasite_wall", new BNWall(CINCINNASITE_BLOCK));
	public static final Block CINCINNASITE_FIRE_BOWL = registerBlock("cincinnasite_fire_bowl", new BlockCincinnasiteFireBowl());
	public static final Block CINCINNASITE_BRICKS_PILLAR = registerBlock("cincinnasite_bricks_pillar", new BNPillar(CINCINNASITE_BLOCK));
	public static final Block CINCINNASITE_BARS = registerBlock("cincinnasite_bars", new BNPane(CINCINNASITE_BLOCK, true));
	public static final Block CINCINNASITE_PEDESTAL = registerBlock("cincinnasite_pedestal", new BlockCincinnasitePedestal());
	public static final Block CINCINNASITE_FRAME = registerBlock("cincinnasite_frame", new BlockCincinnasiteFrame());
	public static final Block CINCINNASITE_LANTERN_SMALL = registerBlock("cincinnasite_lantern_small", new BlockSmallLantern());
	
	// Bricks //
	public static final Block NETHER_BRICK_TILE_LARGE = registerBlock("nether_brick_tile_large", new BNNetherBrick());
	public static final Block NETHER_BRICK_TILE_SMALL = registerBlock("nether_brick_tile_small", new BNNetherBrick());
	public static final Block NETHER_BRICK_WALL = registerBlock("nether_brick_wall", new BNWall(Blocks.NETHER_BRICKS));
	public static final Block NETHER_BRICK_TILE_SLAB = registerBlock("nether_brick_tile_slab", new BNSlab(NETHER_BRICK_TILE_SMALL));
	public static final Block NETHER_BRICK_TILE_STAIRS = registerBlock("nether_brick_tile_stairs", new BNStairs(NETHER_BRICK_TILE_SMALL));
	
	// Bone //
	public static final Block BONE = registerBlock("bone_block", new BNBoneBlock());
	public static final Block BONE_STAIRS = registerBlock("bone_stairs", new BNStairs(BONE));
	public static final Block BONE_SLAB = registerBlock("bone_slab", new BNSlab(BONE));
	public static final Block BONE_BUTTON = registerBlock("bone_button", new BNButton(BONE));
	public static final Block BONE_PLATE = registerBlock("bone_plate", new BNPlate(ActivationRule.EVERYTHING, BONE));
	public static final Block BONE_WALL = registerBlock("bone_wall", new BNWall(BONE));
	public static final Block BONE_TILE = registerBlock("bone_tile", new BNBoneBlock());
	public static final Block BONE_REED_DOOR = registerBlock("bone_reed_door", new BNDoor(BONE));
	public static final Block BONE_CINCINNASITE_DOOR = registerBlock("bone_cincinnasite_door", new BNDoor(BONE));
	
	// Quartz Glass //
	public static final Block QUARTZ_GLASS = registerBlock("quartz_glass", new BNGlass(Blocks.GLASS));
	public static final Block QUARTZ_GLASS_FRAMED = registerBlock("quartz_glass_framed", new BNGlass(CINCINNASITE_BLOCK));
	public static final Block QUARTZ_GLASS_PANE = registerBlock("quartz_glass_pane", new BNPane(QUARTZ_GLASS, true));
	public static final Block QUARTZ_GLASS_FRAMED_PANE = registerBlock("quartz_glass_framed_pane", new BNPane(CINCINNASITE_BLOCK, true));
	
	// Quartz Glass Colored //
	public static final Block QUARTZ_WHITE_GLASS = registerBlock("quartz_glass_white", new BNGlass(QUARTZ_GLASS));
	public static final Block QUARTZ_ORANGE_GLASS = registerBlock("quartz_glass_orange", new BNGlass(QUARTZ_GLASS));
	public static final Block QUARTZ_MAGENTA_GLASS = registerBlock("quartz_glass_magenta", new BNGlass(QUARTZ_GLASS));
	public static final Block QUARTZ_LIGHT_BLUE_GLASS = registerBlock("quartz_glass_light_blue", new BNGlass(QUARTZ_GLASS));
	public static final Block QUARTZ_YELLOW_GLASS = registerBlock("quartz_glass_yellow", new BNGlass(QUARTZ_GLASS));
	public static final Block QUARTZ_LIME_GLASS = registerBlock("quartz_glass_lime", new BNGlass(QUARTZ_GLASS));
	public static final Block QUARTZ_PINK_GLASS = registerBlock("quartz_glass_pink", new BNGlass(QUARTZ_GLASS));
	public static final Block QUARTZ_GRAY_GLASS = registerBlock("quartz_glass_gray", new BNGlass(QUARTZ_GLASS));
	public static final Block QUARTZ_LIGHT_GRAY_GLASS = registerBlock("quartz_glass_light_gray", new BNGlass(QUARTZ_GLASS));
	public static final Block QUARTZ_CYAN_GLASS = registerBlock("quartz_glass_cyan", new BNGlass(QUARTZ_GLASS));
	public static final Block QUARTZ_PURPLE_GLASS = registerBlock("quartz_glass_purple", new BNGlass(QUARTZ_GLASS));
	public static final Block QUARTZ_BLUE_GLASS = registerBlock("quartz_glass_blue", new BNGlass(QUARTZ_GLASS));
	public static final Block QUARTZ_BROWN_GLASS = registerBlock("quartz_glass_brown", new BNGlass(QUARTZ_GLASS));
	public static final Block QUARTZ_GREEN_GLASS = registerBlock("quartz_glass_green", new BNGlass(QUARTZ_GLASS));
	public static final Block QUARTZ_RED_GLASS = registerBlock("quartz_glass_red", new BNGlass(QUARTZ_GLASS));
	public static final Block QUARTZ_BLACK_GLASS = registerBlock("quartz_glass_black", new BNGlass(QUARTZ_GLASS));
	
	// Quartz Glass Colored Framed //
	public static final Block QUARTZ_GLASS_FRAMED_WHITE = registerBlock("quartz_glass_framed_white", new BNGlass(QUARTZ_GLASS_FRAMED));
	public static final Block QUARTZ_GLASS_FRAMED_ORANGE = registerBlock("quartz_glass_framed_orange", new BNGlass(QUARTZ_GLASS_FRAMED));
	public static final Block QUARTZ_GLASS_FRAMED_MAGENTA = registerBlock("quartz_glass_framed_magenta", new BNGlass(QUARTZ_GLASS_FRAMED));
	public static final Block QUARTZ_GLASS_FRAMED_LIGHT_BLUE = registerBlock("quartz_glass_framed_light_blue", new BNGlass(QUARTZ_GLASS_FRAMED));
	public static final Block QUARTZ_GLASS_FRAMED_YELLOW = registerBlock("quartz_glass_framed_yellow", new BNGlass(QUARTZ_GLASS_FRAMED));
	public static final Block QUARTZ_GLASS_FRAMED_LIME = registerBlock("quartz_glass_framed_lime", new BNGlass(QUARTZ_GLASS_FRAMED));
	public static final Block QUARTZ_GLASS_FRAMED_PINK = registerBlock("quartz_glass_framed_pink", new BNGlass(QUARTZ_GLASS_FRAMED));
	public static final Block QUARTZ_GLASS_FRAMED_GRAY = registerBlock("quartz_glass_framed_gray", new BNGlass(QUARTZ_GLASS_FRAMED));
	public static final Block QUARTZ_GLASS_FRAMED_LIGHT_GRAY = registerBlock("quartz_glass_framed_light_gray", new BNGlass(QUARTZ_GLASS_FRAMED));
	public static final Block QUARTZ_GLASS_FRAMED_CYAN = registerBlock("quartz_glass_framed_cyan", new BNGlass(QUARTZ_GLASS_FRAMED));
	public static final Block QUARTZ_GLASS_FRAMED_PURPLE = registerBlock("quartz_glass_framed_purple", new BNGlass(QUARTZ_GLASS_FRAMED));
	public static final Block QUARTZ_GLASS_FRAMED_BLUE = registerBlock("quartz_glass_framed_blue", new BNGlass(QUARTZ_GLASS_FRAMED));
	public static final Block QUARTZ_GLASS_FRAMED_BROWN = registerBlock("quartz_glass_framed_brown", new BNGlass(QUARTZ_GLASS_FRAMED));
	public static final Block QUARTZ_GLASS_FRAMED_GREEN = registerBlock("quartz_glass_framed_green", new BNGlass(QUARTZ_GLASS_FRAMED));
	public static final Block QUARTZ_GLASS_FRAMED_RED = registerBlock("quartz_glass_framed_red", new BNGlass(QUARTZ_GLASS_FRAMED));
	public static final Block QUARTZ_GLASS_FRAMED_BLACK = registerBlock("quartz_glass_framed_black", new BNGlass(QUARTZ_GLASS_FRAMED));
	
	// Quartz Glass Colored Panes //
	public static final Block QUARTZ_GLASS_PANE_WHITE = registerBlock("quartz_glass_pane_white", new BNPane(QUARTZ_GLASS, false));
	public static final Block QUARTZ_GLASS_PANE_ORANGE = registerBlock("quartz_glass_pane_orange", new BNPane(QUARTZ_GLASS, false));
	public static final Block QUARTZ_GLASS_PANE_MAGENTA = registerBlock("quartz_glass_pane_magenta", new BNPane(QUARTZ_GLASS, false));
	public static final Block QUARTZ_GLASS_PANE_LIGHT_BLUE = registerBlock("quartz_glass_pane_light_blue", new BNPane(QUARTZ_GLASS, false));
	public static final Block QUARTZ_GLASS_PANE_YELLOW = registerBlock("quartz_glass_pane_yellow", new BNPane(QUARTZ_GLASS, false));
	public static final Block QUARTZ_GLASS_PANE_LIME = registerBlock("quartz_glass_pane_lime", new BNPane(QUARTZ_GLASS, false));
	public static final Block QUARTZ_GLASS_PANE_PINK = registerBlock("quartz_glass_pane_pink", new BNPane(QUARTZ_GLASS, false));
	public static final Block QUARTZ_GLASS_PANE_GRAY = registerBlock("quartz_glass_pane_gray", new BNPane(QUARTZ_GLASS, false));
	public static final Block QUARTZ_GLASS_PANE_LIGHT_GRAY = registerBlock("quartz_glass_pane_light_gray", new BNPane(QUARTZ_GLASS, false));
	public static final Block QUARTZ_GLASS_PANE_CYAN = registerBlock("quartz_glass_pane_cyan", new BNPane(QUARTZ_GLASS, false));
	public static final Block QUARTZ_GLASS_PANE_PURPLE = registerBlock("quartz_glass_pane_purple", new BNPane(QUARTZ_GLASS, false));
	public static final Block QUARTZ_GLASS_PANE_BLUE = registerBlock("quartz_glass_pane_blue", new BNPane(QUARTZ_GLASS, false));
	public static final Block QUARTZ_GLASS_PANE_BROWN = registerBlock("quartz_glass_pane_brown", new BNPane(QUARTZ_GLASS, false));
	public static final Block QUARTZ_GLASS_PANE_GREEN = registerBlock("quartz_glass_pane_green", new BNPane(QUARTZ_GLASS, false));
	public static final Block QUARTZ_GLASS_PANE_RED = registerBlock("quartz_glass_pane_red", new BNPane(QUARTZ_GLASS, false));
	public static final Block QUARTZ_GLASS_PANE_BLACK = registerBlock("quartz_glass_pane_black", new BNPane(QUARTZ_GLASS, false));
	
	// Quartz Glass Colored Panes Framed //
	public static final Block QUARTZ_GLASS_FRAMED_PANE_WHITE = registerBlock("quartz_glass_framed_pane_white", new BNPane(QUARTZ_GLASS_FRAMED, true));
	public static final Block QUARTZ_GLASS_FRAMED_PANE_ORANGE = registerBlock("quartz_glass_framed_pane_orange", new BNPane(QUARTZ_GLASS_FRAMED, true));
	public static final Block QUARTZ_GLASS_FRAMED_PANE_MAGENTA = registerBlock("quartz_glass_framed_pane_magenta", new BNPane(QUARTZ_GLASS_FRAMED, true));
	public static final Block QUARTZ_GLASS_FRAMED_PANE_LIGHT_BLUE = registerBlock("quartz_glass_framed_pane_light_blue", new BNPane(QUARTZ_GLASS_FRAMED, true));
	public static final Block QUARTZ_GLASS_FRAMED_PANE_YELLOW = registerBlock("quartz_glass_framed_pane_yellow", new BNPane(QUARTZ_GLASS_FRAMED, true));
	public static final Block QUARTZ_GLASS_FRAMED_PANE_LIME = registerBlock("quartz_glass_framed_pane_lime", new BNPane(QUARTZ_GLASS_FRAMED, true));
	public static final Block QUARTZ_GLASS_FRAMED_PANE_PINK = registerBlock("quartz_glass_framed_pane_pink", new BNPane(QUARTZ_GLASS_FRAMED, true));
	public static final Block QUARTZ_GLASS_FRAMED_PANE_GRAY = registerBlock("quartz_glass_framed_pane_gray", new BNPane(QUARTZ_GLASS_FRAMED, true));
	public static final Block QUARTZ_GLASS_FRAMED_PANE_LIGHT_GRAY = registerBlock("quartz_glass_framed_pane_light_gray", new BNPane(QUARTZ_GLASS_FRAMED, true));
	public static final Block QUARTZ_GLASS_FRAMED_PANE_CYAN = registerBlock("quartz_glass_framed_pane_cyan", new BNPane(QUARTZ_GLASS_FRAMED, true));
	public static final Block QUARTZ_GLASS_FRAMED_PANE_PURPLE = registerBlock("quartz_glass_framed_pane_purple", new BNPane(QUARTZ_GLASS_FRAMED, true));
	public static final Block QUARTZ_GLASS_FRAMED_PANE_BLUE = registerBlock("quartz_glass_framed_pane_blue", new BNPane(QUARTZ_GLASS_FRAMED, true));
	public static final Block QUARTZ_GLASS_FRAMED_PANE_BROWN = registerBlock("quartz_glass_framed_pane_brown", new BNPane(QUARTZ_GLASS_FRAMED, true));
	public static final Block QUARTZ_GLASS_FRAMED_PANE_GREEN = registerBlock("quartz_glass_framed_pane_green", new BNPane(QUARTZ_GLASS_FRAMED, true));
	public static final Block QUARTZ_GLASS_FRAMED_PANE_RED = registerBlock("quartz_glass_framed_pane_red", new BNPane(QUARTZ_GLASS_FRAMED, true));
	public static final Block QUARTZ_GLASS_FRAMED_PANE_BLACK = registerBlock("quartz_glass_framed_pane_black", new BNPane(QUARTZ_GLASS_FRAMED, true));
	
	// Obsidian //
	public static final Block OBSIDIAN_BRICKS = registerBlock("obsidian_bricks", new BNObsidian());
	public static final Block OBSIDIAN_BRICKS_STAIRS = registerBlock("obsidian_bricks_stairs", new BNStairs(OBSIDIAN_BRICKS));
	public static final Block OBSIDIAN_BRICKS_SLAB = registerBlock("obsidian_bricks_slab", new BNSlab(OBSIDIAN_BRICKS));
	public static final Block OBSIDIAN_TILE = registerBlock("obsidian_tile", new BNObsidian());
	public static final Block OBSIDIAN_TILE_SMALL = registerBlock("obsidian_tile_small", new BNObsidian());
	public static final Block OBSIDIAN_TILE_STAIRS = registerBlock("obsidian_tile_stairs", new BNStairs(OBSIDIAN_TILE_SMALL));
	public static final Block OBSIDIAN_TILE_SLAB = registerBlock("obsidian_tile_slab", new BNSlab(OBSIDIAN_TILE_SMALL));
	public static final Block OBSIDIAN_GLASS = registerBlock("obsidian_glass", new BlockObsidianGlass());
	public static final Block OBSIDIAN_GLASS_PANE = registerBlock("obsidian_glass_pane", new BNPane(OBSIDIAN_GLASS, true));
	public static final Block BLUE_OBSIDIAN = registerBlock("blue_obsidian", new BNObsidian());
	public static final Block BLUE_OBSIDIAN_BRICKS = registerBlock("blue_obsidian_bricks", new BNObsidian());
	public static final Block BLUE_OBSIDIAN_BRICKS_STAIRS = registerBlock("blue_obsidian_bricks_stairs", new BNStairs(BLUE_OBSIDIAN_BRICKS));
	public static final Block BLUE_OBSIDIAN_BRICKS_SLAB = registerBlock("blue_obsidian_bricks_slab", new BNSlab(BLUE_OBSIDIAN_BRICKS));
	public static final Block BLUE_OBSIDIAN_TILE = registerBlock("blue_obsidian_tile", new BNObsidian());
	public static final Block BLUE_OBSIDIAN_TILE_SMALL = registerBlock("blue_obsidian_tile_small", new BNObsidian());
	public static final Block BLUE_OBSIDIAN_TILE_STAIRS = registerBlock("blue_obsidian_tile_stairs", new BNStairs(BLUE_OBSIDIAN_TILE_SMALL));
	public static final Block BLUE_OBSIDIAN_TILE_SLAB = registerBlock("blue_obsidian_tile_slab", new BNSlab(BLUE_OBSIDIAN_TILE_SMALL));
	public static final Block BLUE_OBSIDIAN_GLASS = registerBlock("blue_obsidian_glass", new BlockObsidianGlass());
	public static final Block BLUE_OBSIDIAN_GLASS_PANE = registerBlock("blue_obsidian_glass_pane", new BNPane(BLUE_OBSIDIAN_GLASS, true));
	
	// Willow //
	public static final Block WILLOW_BRANCH = registerBlockNI("willow_branch", new BlockWillowBranch());
	public static final Block WILLOW_LEAVES = registerBlockNI("willow_leaves", new BlockWillowLeaves());
	public static final Block WILLOW_TRUNK = registerBlockNI("willow_trunk", new BlockWillowTrunk());
	public static final Block STRIPED_LOG_WILLOW = registerBlock("striped_log_willow", new BNPillar(MaterialColor.PINK));
	public static final Block STRIPED_BARK_WILLOW = registerBlock("striped_bark_willow", new BNPillar(MaterialColor.PINK));
	public static final Block WILLOW_LOG = registerBlock("willow_log", new BNLogStripable(MaterialColor.RED_TERRACOTTA, STRIPED_LOG_WILLOW));
	public static final Block WILLOW_BARK = registerBlock("willow_bark", new BNLogStripable(MaterialColor.RED_TERRACOTTA, STRIPED_BARK_WILLOW));
	public static final Block WILLOW_SAPLING = registerBlock("willow_sapling", new BlockWillowSapling());
	public static final Block WILLOW_PLANKS = registerBlock("willow_planks", new BNPlanks(MaterialColor.RED_TERRACOTTA));
	public static final Block WILLOW_STAIRS = registerBlock("willow_stairs", new BNStairs(WILLOW_PLANKS));
	public static final Block WILLOW_SLAB = registerBlock("willow_slab", new BNSlab(WILLOW_PLANKS));
	public static final Block WILLOW_FENCE = registerBlock("willow_fence", new BNFence(WILLOW_PLANKS));
	public static final Block WILLOW_GATE = registerBlock("willow_gate", new BNGate(WILLOW_PLANKS));
	public static final Block WILLOW_BUTTON = registerBlock("willow_button", new BNButton(WILLOW_PLANKS));
	public static final Block WILLOW_PLATE = registerBlock("willow_plate", new BNPlate(ActivationRule.EVERYTHING, WILLOW_PLANKS));
	public static final Block WILLOW_TRAPDOOR = registerBlock("willow_trapdoor", new BNTrapdoor(WILLOW_PLANKS));
	public static final Block WILLOW_DOOR = registerBlock("willow_door", new BNDoor(WILLOW_PLANKS));
	
	// Wart //
	public static final Block STRIPED_LOG_WART = registerBlock("striped_log_wart", new BNPillar(MaterialColor.RED));
	public static final Block STRIPED_BARK_WART = registerBlock("striped_bark_wart", new BNPillar(MaterialColor.RED));
	public static final Block WART_LOG = registerBlock("wart_log", new BNLogStripable(Blocks.NETHER_WART_BLOCK, STRIPED_LOG_WART));
	public static final Block WART_BARK = registerBlock("wart_bark", new BNLogStripable(Blocks.NETHER_WART_BLOCK, STRIPED_BARK_WART));
	public static final Block WART_ROOTS = registerBlockNI("wart_roots", new BlockWartRoots());
	public static final Block WART_PLANKS = registerBlock("wart_planks", new BNPlanks(MaterialColor.RED));
	public static final Block WART_STAIRS = registerBlock("wart_stairs", new BNStairs(WART_PLANKS));
	public static final Block WART_SLAB = registerBlock("wart_slab", new BNSlab(WART_PLANKS));
	public static final Block WART_FENCE = registerBlock("wart_fence", new BNFence(WART_PLANKS));
	public static final Block WART_GATE = registerBlock("wart_gate", new BNGate(WART_PLANKS));
	public static final Block WART_BUTTON = registerBlock("wart_button", new BNButton(WART_PLANKS));
	public static final Block WART_PLATE = registerBlock("wart_plate", new BNPlate(ActivationRule.EVERYTHING, WART_PLANKS));
	public static final Block WART_TRAPDOOR = registerBlock("wart_trapdoor", new BNTrapdoor(WART_PLANKS));
	public static final Block WART_DOOR = registerBlock("wart_door", new BNDoor(WART_PLANKS));
	
	// Rubeus
	public static final Block RUBEUS_SAPLING = registerBlock("rubeus_sapling",  new BlockRubeusSapling());
	public static final Block RUBEUS_CONE = registerBlock("rubeus_cone",  new BlockRubeusCone());
	public static final Block STRIPED_LOG_RUBEUS = registerBlock("striped_log_rubeus", new BNPillar(MaterialColor.MAGENTA));
	public static final Block STRIPED_BARK_RUBEUS = registerBlock("striped_bark_rubeus", new BNPillar(MaterialColor.MAGENTA));
	public static final Block RUBEUS_LOG = registerBlock("rubeus_log", new BlockRubeusLog(STRIPED_LOG_WART));
	public static final Block RUBEUS_BARK = registerBlock("rubeus_bark", new BlockRubeusLog(STRIPED_BARK_WART));
	public static final Block RUBEUS_LEAVES = registerBlock("rubeus_leaves", new BlockRubeusLeaves());
	public static final Block RUBEUS_PLANKS = registerBlock("rubeus_planks", new BNPlanks(MaterialColor.MAGENTA));
	public static final Block RUBEUS_STAIRS = registerBlock("rubeus_stairs", new BNStairs(WART_PLANKS));
	public static final Block RUBEUS_SLAB = registerBlock("rubeus_slab", new BNSlab(WART_PLANKS));
	public static final Block RUBEUS_FENCE = registerBlock("rubeus_fence", new BNFence(WART_PLANKS));
	public static final Block RUBEUS_GATE = registerBlock("rubeus_gate", new BNGate(WART_PLANKS));
	public static final Block RUBEUS_BUTTON = registerBlock("rubeus_button", new BNButton(WART_PLANKS));
	public static final Block RUBEUS_PLATE = registerBlock("rubeus_plate", new BNPlate(ActivationRule.EVERYTHING, WART_PLANKS));
	public static final Block RUBEUS_TRAPDOOR = registerBlock("rubeus_trapdoor", new BNTrapdoor(WART_PLANKS));
	public static final Block RUBEUS_DOOR = registerBlock("rubeus_door", new BNDoor(WART_PLANKS));
	
	// Mushroom //
	public static final Block MUSHROOM_STEM = registerBlock("mushroom_stem", new BlockStalagnateStem());
	public static final Block MUSHROOM_PLANKS = registerBlock("mushroom_planks", new BNPlanks(MaterialColor.LIGHT_GRAY));
	public static final Block MUSHROOM_STAIRS = registerBlock("mushroom_stairs", new BNStairs(MUSHROOM_PLANKS));
	public static final Block MUSHROOM_SLAB = registerBlock("mushroom_slab", new BNSlab(MUSHROOM_PLANKS));
	public static final Block MUSHROOM_FENCE = registerBlock("mushroom_fence", new BNFence(MUSHROOM_PLANKS));
	public static final Block MUSHROOM_GATE = registerBlock("mushroom_gate", new BNGate(MUSHROOM_PLANKS));
	public static final Block MUSHROOM_BUTTON = registerBlock("mushroom_button", new BNButton(MUSHROOM_PLANKS));
	public static final Block MUSHROOM_PLATE = registerBlock("mushroom_plate", new BNPlate(ActivationRule.EVERYTHING, MUSHROOM_PLANKS));
	public static final Block MUSHROOM_TRAPDOOR = registerBlock("mushroom_trapdoor", new BNTrapdoor(MUSHROOM_STAIRS));
	public static final Block MUSHROOM_DOOR = registerBlock("mushroom_door", new BNDoor(MUSHROOM_STAIRS));
	
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
	
	// Mushroom Fir //
	public static final Block MUSHROOM_FIR = registerBlockNI("mushroom_fir",  new BlockMushroomFir());
	public static final Block MUSHROOM_FIR_SAPLING = registerBlock("mushroom_fir_sapling",  new BlockMushroomFirSapling());
	public static final Block MUSHROOM_FIR_STEM = registerBlock("mushroom_fir_stem",  new BlockStalagnateStem());
	public static final Block MUSHROOM_FIR_LOG = registerBlock("mushroom_fir_log",  new BNPillar(MaterialColor.BLUE));
	public static final Block MUSHROOM_FIR_WOOD = registerBlock("mushroom_fir_wood",  new BNPillar(MaterialColor.BLUE));
	public static final Block STRIPED_LOG_MUSHROOM_FIR = registerBlock("striped_log_mushroom_fir",  new BNLogStripable(MUSHROOM_FIR_LOG, new BNPillar(MUSHROOM_FIR_LOG)));
	public static final Block STRIPED_WOOD_MUSHROOM_FIR = registerBlock("striped_wood_mushroom_fir",  new BNLogStripable(MUSHROOM_FIR_WOOD, new BNPillar(MUSHROOM_FIR_WOOD)));
	public static final Block MUSHROOM_FIR_PLANKS = registerBlock("mushroom_fir_planks",  new BNPlanks(MaterialColor.LIGHT_GRAY));
	public static final Block MUSHROOM_FIR_STAIRS = registerBlock("mushroom_fir_stairs",  new BNStairs(MUSHROOM_FIR_PLANKS));
	public static final Block MUSHROOM_FIR_SLAB = registerBlock("mushroom_fir_slab",  new BNSlab(MUSHROOM_FIR_PLANKS));
	public static final Block MUSHROOM_FIR_FENCE = registerBlock("mushroom_fir_fence",  new BNFence(MUSHROOM_FIR_PLANKS));
	public static final Block MUSHROOM_FIR_GATE = registerBlock("mushroom_fir_gate",  new BNGate(MUSHROOM_FIR_PLANKS));
	public static final Block MUSHROOM_FIR_BUTTON = registerBlock("mushroom_fir_button",  new BNButton(MUSHROOM_FIR_PLANKS));
	public static final Block MUSHROOM_FIR_PLATE = registerBlock("mushroom_fir_plate",  new BNPlate(ActivationRule.EVERYTHING, MUSHROOM_FIR_PLANKS));
	public static final Block MUSHROOM_FIR_TRAPDOOR = registerBlock("mushroom_fir_trapdoor",  new BNTrapdoor(MUSHROOM_FIR_PLANKS));
	public static final Block MUSHROOM_FIR_DOOR = registerBlock("mushroom_fir_door",  new BNDoor(MUSHROOM_FIR_PLANKS));
	
	// Eyes //
	public static final Block EYEBALL = registerBlockNI("eyeball", new BlockEyeball());
	public static final Block EYEBALL_SMALL = registerBlockNI("eyeball_small", new BlockEyeballSmall());
	public static final Block EYE_VINE = registerBlockNI("eye_vine", new BlockEyeVine());
	public static final Block EYE_SEED = registerBlock("eye_seed", new BlockEyeSeed());
	
	// Grass //
	public static final Block NETHER_GRASS = registerBlock("nether_grass", new BlockNetherGrass());
	public static final Block SWAMP_GRASS = registerBlock("swamp_grass", new BlockNetherGrass());
	public static final Block SOUL_GRASS = registerBlock("soul_grass", new BlockSoulGrass());
	
	// Vines //
	public static final Block BLACK_VINE = registerBlock("black_vine", new BlockBlackVine());
	public static final Block BLOOMING_VINE = registerBlock("blooming_vine", new BlockBlackVine());
	public static final Block GOLDEN_VINE = registerBlock("golden_vine", new BlockGoldenVine());
	
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
	
	// Cactuses //
	public static final Block AGAVE = registerBlock("agave", new BlockAgave());
	public static final Block BARREL_CACTUS = registerBlock("barrel_cactus", new BlockBarrelCactus());
	public static final Block NETHER_CACTUS = registerBlock("nether_cactus", new BlockNetherCactus());
	
	// Wall plants
	public static final Block WALL_MOSS = registerBlock("wall_moss", new BlockPlantWall(MaterialColor.RED));
	public static final Block WALL_MUSHROOM_BROWN = registerBlock("wall_mushroom_brown", new BlockPlantWall(MaterialColor.BROWN));
	public static final Block WALL_MUSHROOM_RED = registerBlock("wall_mushroom_red", new BlockPlantWall(MaterialColor.RED));
	
	// Decorations //
	public static final Block PIG_STATUE_RESPAWNER = registerBlock("pig_statue_respawner", new BlockStatueRespawner());
	public static final Block CHEST_OF_DRAWERS = registerBlock("chest_of_drawers", new BlockChestOfDrawers());
	public static final Block CINCINNASITE_POT = registerBlock("cincinnasite_pot", new BlockBNPot(CINCINNASITE_BLOCK));
	public static final Block BRICK_POT = registerBlock("brick_pot", new BlockBNPot(Blocks.NETHER_BRICKS));
	public static final Block POTTED_PLANT = registerBlockNI("potted_plant", new BlockPottedPlant());
	public static final Block GEYSER = registerBlock("geyser", new BlockGeyser());
	
	// Terrain //
	public static final Block NETHERRACK_MOSS = registerBlock("netherrack_moss", new BlockNetherrackMoss());
	public static final Block NETHER_MYCELIUM = registerBlock("nether_mycelium", new BlockNetherMycelium());
	public static final Block VEINED_SAND = registerBlockNI("veined_sand", new BlockVeinedSand());
	public static final Block FARMLAND = registerBlock("farmland", new BlockFarmland());
	
	// Roofs //
	public static final Block ROOF_TILE_REEDS = registerBlock("roof_tile_reeds", new BNPlanks(MaterialColor.BLUE));
	public static final Block ROOF_TILE_REEDS_STAIRS = registerBlock("roof_tile_reeds_stairs", new BNStairs(ROOF_TILE_REEDS));
	public static final Block ROOF_TILE_REEDS_SLAB = registerBlock("roof_tile_reeds_slab", new BNSlab(ROOF_TILE_REEDS));
	public static final Block ROOF_TILE_STALAGNATE = registerBlock("roof_tile_stalagnate", new BNPlanks(MaterialColor.LIME));
	public static final Block ROOF_TILE_STALAGNATE_STAIRS = registerBlock("roof_tile_stalagnate_stairs", new BNStairs(ROOF_TILE_STALAGNATE));
	public static final Block ROOF_TILE_STALAGNATE_SLAB = registerBlock("roof_tile_stalagnate_slab", new BNSlab(ROOF_TILE_STALAGNATE));
	public static final Block ROOF_TILE_NETHER_BRICKS = registerBlock("roof_tile_nether_bricks", new BNNetherBrick());
	public static final Block ROOF_TILE_NETHER_BRICKS_STAIRS = registerBlock("roof_tile_nether_bricks_stairs", new BNStairs(ROOF_TILE_NETHER_BRICKS));
	public static final Block ROOF_TILE_NETHER_BRICKS_SLAB = registerBlock("roof_tile_nether_bricks_slab", new BNSlab(ROOF_TILE_NETHER_BRICKS));
	public static final Block ROOF_TILE_CINCINNASITE = registerBlock("roof_tile_cincinnasite", new BlockCincinnasite());
	public static final Block ROOF_TILE_CINCINNASITE_STAIRS = registerBlock("roof_tile_cincinnasite_stairs", new BNStairs(ROOF_TILE_NETHER_BRICKS));
	public static final Block ROOF_TILE_CINCINNASITE_SLAB = registerBlock("roof_tile_cincinnasite_slab", new BNSlab(ROOF_TILE_NETHER_BRICKS));
	public static final Block ROOF_TILE_WILLOW = registerBlock("roof_tile_willow", new BNPlanks(MaterialColor.RED_TERRACOTTA));
	public static final Block ROOF_TILE_WILLOW_STAIRS = registerBlock("roof_tile_willow_stairs", new BNStairs(ROOF_TILE_WILLOW));
	public static final Block ROOF_TILE_WILLOW_SLAB = registerBlock("roof_tile_willow_slab", new BNSlab(ROOF_TILE_WILLOW));
	public static final Block ROOF_TILE_WART = registerBlock("roof_tile_wart", new BNPlanks(MaterialColor.RED_TERRACOTTA));
	public static final Block ROOF_TILE_WART_STAIRS = registerBlock("roof_tile_wart_stairs", new BNStairs(ROOF_TILE_WART));
	public static final Block ROOF_TILE_WART_SLAB = registerBlock("roof_tile_wart_slab", new BNSlab(ROOF_TILE_WART));
	
	// Craft Stations //
	public static final Block NETHERRACK_FURNACE = registerBlock("netherrack_furnace", new BlockNetherrackFurnace());
	public static final Block CINCINNASITE_FORGE = registerBlock("cincinnasite_forge", new BlockCincinnasiteForge());
	public static final Block NETHER_BREWING_STAND = registerBlock("nether_brewing_stand", new BNBrewingStand());
	
	// Taburets //
	public static final Block TABURET_OAK = registerBlock("taburet_oak", new BNTaburet(Blocks.OAK_PLANKS));
	public static final Block TABURET_SPRUCE = registerBlock("taburet_spruce", new BNTaburet(Blocks.SPRUCE_PLANKS));
	public static final Block TABURET_BIRCH = registerBlock("taburet_birch", new BNTaburet(Blocks.BIRCH_PLANKS));
	public static final Block TABURET_JUNGLE = registerBlock("taburet_jungle", new BNTaburet(Blocks.JUNGLE_PLANKS));
	public static final Block TABURET_ACACIA = registerBlock("taburet_acacia", new BNTaburet(Blocks.ACACIA_PLANKS));
	public static final Block TABURET_DARK_OAK = registerBlock("taburet_dark_oak", new BNTaburet(Blocks.DARK_OAK_PLANKS));
	
	public static final Block TABURET_REEDS = registerBlock("taburet_reeds", new BNTaburet(REEDS_BLOCK));
	public static final Block TABURET_STALAGNATE = registerBlock("taburet_stalagnate", new BNTaburet(STALAGNATE_PLANKS));
	public static final Block TABURET_WILLOW = registerBlock("taburet_willow", new BNTaburet(WILLOW_PLANKS));
	public static final Block TABURET_WART = registerBlock("taburet_wart", new BNTaburet(WART_PLANKS));
	public static final Block TABURET_CINCINNASITE = registerBlock("taburet_cincinnasite", new BNTaburet(CINCINNASITE_FORGED));
	public static final Block TABURET_MUSHROOM_FIR = registerBlock("taburet_mushroom_fir", new BNTaburet(MUSHROOM_FIR_PLANKS));
	
	// Chairs
	public static final Block CHAIR_OAK = registerBlock("chair_oak", new BNNormalChair(Blocks.OAK_PLANKS));
	public static final Block CHAIR_SPRUCE = registerBlock("chair_spruce", new BNNormalChair(Blocks.SPRUCE_PLANKS));
	public static final Block CHAIR_BIRCH = registerBlock("chair_birch", new BNNormalChair(Blocks.BIRCH_PLANKS));
	public static final Block CHAIR_JUNGLE = registerBlock("chair_jungle", new BNNormalChair(Blocks.JUNGLE_PLANKS));
	public static final Block CHAIR_ACACIA = registerBlock("chair_acacia", new BNNormalChair(Blocks.ACACIA_PLANKS));
	public static final Block CHAIR_DARK_OAK = registerBlock("chair_dark_oak", new BNNormalChair(Blocks.DARK_OAK_PLANKS));
	
	public static final Block CHAIR_REEDS = registerBlock("chair_reeds", new BNNormalChair(REEDS_BLOCK));
	public static final Block CHAIR_STALAGNATE = registerBlock("chair_stalagnate", new BNNormalChair(STALAGNATE_PLANKS));
	public static final Block CHAIR_WILLOW = registerBlock("chair_willow", new BNNormalChair(WILLOW_PLANKS));
	public static final Block CHAIR_WART = registerBlock("chair_wart", new BNNormalChair(WART_PLANKS));
	public static final Block CHAIR_CINCINNASITE = registerBlock("chair_cincinnasite", new BNNormalChair(CINCINNASITE_FORGED));
	public static final Block CHAIR_MUSHROOM_FIR = registerBlock("chair_mushroom_fir", new BNNormalChair(MUSHROOM_FIR_PLANKS));
	
	// Stools //
	public static final Block BAR_STOOL_OAK = registerBlock("bar_stool_oak", new BNBarStool(Blocks.OAK_PLANKS));
	public static final Block BAR_STOOL_SPRUCE = registerBlock("bar_stool_spruce", new BNBarStool(Blocks.SPRUCE_PLANKS));
	public static final Block BAR_STOOL_BIRCH = registerBlock("bar_stool_birch", new BNBarStool(Blocks.BIRCH_PLANKS));
	public static final Block BAR_STOOL_JUNGLE = registerBlock("bar_stool_jungle", new BNBarStool(Blocks.JUNGLE_PLANKS));
	public static final Block BAR_STOOL_ACACIA = registerBlock("bar_stool_acacia", new BNBarStool(Blocks.ACACIA_PLANKS));
	public static final Block BAR_STOOL_DARK_OAK = registerBlock("bar_stool_dark_oak", new BNBarStool(Blocks.DARK_OAK_PLANKS));
	
	public static final Block BAR_STOOL_REEDS = registerBlock("bar_stool_reeds", new BNBarStool(REEDS_BLOCK));
	public static final Block BAR_STOOL_STALAGNATE = registerBlock("bar_stool_stalagnate", new BNBarStool(STALAGNATE_PLANKS));
	public static final Block BAR_STOOL_WILLOW = registerBlock("bar_stool_willow", new BNBarStool(WILLOW_PLANKS));
	public static final Block BAR_STOOL_WART = registerBlock("bar_stool_wart", new BNBarStool(WART_PLANKS));
	public static final Block BAR_STOOL_CINCINNASITE = registerBlock("bar_stool_cincinnasite", new BNBarStool(CINCINNASITE_FORGED));
	public static final Block BAR_STOOL_MUSHROOM_FIR = registerBlock("bar_stool_mushroom_fir", new BNBarStool(MUSHROOM_FIR_PLANKS));
	
	// Ladders //
	public static final Block REEDS_LADDER = registerBlock("reeds_ladder", new BNLadder(REEDS_BLOCK));
	public static final Block STALAGNATE_LADDER = registerBlock("stalagnate_ladder", new BNLadder(STALAGNATE_PLANKS));
	public static final Block WILLOW_LADDER = registerBlock("willow_ladder", new BNLadder(WILLOW_PLANKS));
	public static final Block WART_LADDER = registerBlock("wart_ladder", new BNLadder(WART_PLANKS));
	public static final Block MUSHROOM_FIR_LADDER = registerBlock("mushroom_fir_ladder", new BNLadder(MUSHROOM_FIR_PLANKS));
	
	public static void register() {}
	
	private static Block registerBlock(String name, Block block)
	{
		if (Config.getBoolean("blocks", name, true))
		{
			Registry.register(Registry.BLOCK, new Identifier(BetterNether.MOD_ID, name), block);
			ItemsRegistry.registerItem(name, new BlockItem(block, new Item.Settings().group(CreativeTab.BN_TAB)));
		}
		return block;
	}
	
	private static Block registerBlockNI(String name, Block block)
	{
		if (Config.getBoolean("blocks", name, true))
		{
			Registry.register(Registry.BLOCK, new Identifier(BetterNether.MOD_ID, name), block);
		}
		return block;
	}
}
