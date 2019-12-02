package paulevs.betternether.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemCloth;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import paulevs.betternether.BetterNether;
import paulevs.betternether.config.ConfigLoader;

public class BlocksRegister
{
	private static List<Block> additional;
	
	public static Block BLOCK_EYEBALL;
	public static Block BLOCK_EYEBALL_SMALL;
	public static Block BLOCK_EYE_VINE;
	public static Block BLOCK_EYE_SEED;
	public static Block BLOCK_NETHERRACK_MOSS;
	public static Block BLOCK_NETHER_GRASS;
	public static Block BLOCK_NETHER_REED;
	public static Block BLOCK_STALAGNATE_STEM;
	public static Block BLOCK_STALAGNATE_BOTTOM;
	public static Block BLOCK_STALAGNATE_MIDDLE;
	public static Block BLOCK_STALAGNATE_TOP;
	public static Block BLOCK_STALAGNATE_SEED;
	public static Block BLOCK_STALAGNATE_SEED_BOTTOM;
	public static Block BLOCK_STALAGNATE_BARK;
	public static Block BLOCK_STALAGNATE_PLANKS;
	public static Block BLOCK_STALAGNATE_PLANKS_STAIRS;
	public static Block BLOCK_STALAGNATE_PLANKS_SLAB_DOUBLE;
	public static Block BLOCK_STALAGNATE_PLANKS_SLAB_HALF;
	public static Block BLOCK_STALAGNATE_PLANKS_FENCE;
	public static Block BLOCK_STALAGNATE_PLANKS_GATE;
	public static Block BLOCK_STALAGNATE_PLANKS_BUTTON;
	public static Block BLOCK_STALAGNATE_PLANKS_PLATE;
	public static Block BLOCK_REEDS_BLOCK;
	public static Block BLOCK_REEDS_STAIRS;
	public static Block BLOCK_REEDS_SLAB_DOUBLE;
	public static Block BLOCK_REEDS_SLAB_HALF;
	public static Block BLOCK_REEDS_FENCE;
	public static Block BLOCK_REEDS_GATE;
	public static Block BLOCK_REEDS_BUTTON;
	public static Block BLOCK_REEDS_PLATE;
	public static Block BLOCK_LUCIS_MUSHROOM;
	public static Block BLOCK_LUCIS_SPORE;
	public static Block BLOCK_NETHER_CACTUS;
	public static Block BLOCK_WART_SEED;
	public static Block BLOCK_BARREL_CACTUS;
	public static Block BLOCK_AGAVE;
	public static Block BLOCK_BLACK_BUSH;
	public static Block BLOCK_INK_BUSH;
	public static Block BLOCK_INK_BUSH_SEED;
	public static Block BLOCK_SMOKER;
	public static Block BLOCK_EGG_PLANT;
	public static Block BLOCK_BLACK_APPLE;
	public static Block BLOCK_BLACK_APPLE_SEED;
	public static Block BLOCK_MAGMA_FLOWER;
	public static Block BLOCK_NETHER_MYCELIUM;
	public static Block BLOCK_RED_LARGE_MUSHROOM;
	public static Block BLOCK_BROWN_LARGE_MUSHROOM;
	public static Block BLOCK_ORANGE_MUSHROOM;
	public static Block BLOCK_RED_MOLD;
	public static Block BLOCK_GRAY_MOLD;
	public static Block BLOCK_CINCINNASITE_ORE;
	public static Block BLOCK_CINCINNASITE;
	public static Block BLOCK_CINCINNASITE_FORGED;
	public static Block BLOCK_CINCINNASITE_PILLAR;
	public static Block BLOCK_CINCINNASITE_BRICKS;
	public static Block BLOCK_CINCINNASITE_BRICK_PLATE;
	public static Block BLOCK_CINCINNASITE_STAIRS;
	public static Block BLOCK_CINCINNASITE_SLAB_DOUBLE;
	public static Block BLOCK_CINCINNASITE_SLAB_HALF;
	public static Block BLOCK_CINCINNASITE_BUTTON;
	public static Block BLOCK_CINCINNASITE_PLATE;
	public static Block BLOCK_CINCINNASITE_LANTERN;
	public static Block BLOCK_CINCINNASITE_TILE_LARGE;
	public static Block BLOCK_CINCINNASITE_TILE_SMALL;
	public static Block BLOCK_CINCINNASITE_CARVED;
	public static Block BLOCK_NETHER_BRICK_TILE_LARGE;
	public static Block BLOCK_NETHER_BRICK_TILE_SMALL;
	public static Block BLOCK_CINCINNASITE_WALL;
	public static Block BLOCK_BONE;
	public static Block BLOCK_BONE_STAIRS;
	public static Block BLOCK_BONE_SLAB_DOUBLE;
	public static Block BLOCK_BONE_SLAB_HALF;
	public static Block BLOCK_BONE_BUTTON;
	public static Block BLOCK_BONE_PLATE;
	public static Block BLOCK_CINCINNASITE_FIRE_BOWL;
	public static Block BLOCK_BONE_WALL;
	public static Block BLOCK_NETHER_BRICK_WALL;
	public static Block BLOCK_NETHER_BRICK_TILE_SLAB_HALF;
	public static Block BLOCK_NETHER_BRICK_TILE_SLAB_DOUBLE;
	public static Block BLOCK_NETHER_BRICK_TILE_STAIRS;
	public static Block BLOCK_BONE_MUSHROOM;
	public static Block BLOCK_REEDS_LADDER;
	public static Block BLOCK_CINCINNASITE_BRICKS_PILLAR;
	public static Block BLOCK_BONE_TILE;
	public static Block BLOCK_CINCINNASITE_PEDESTAL;
	public static Block BLOCK_PIG_STATUE_RESPAWNER;
	public static Block BLOCK_CINCINNASITE_POT;
	public static Block BLOCK_POTTED_PLANT;
	public static Block BLOCK_CINCINNASITE_FRAME;
	public static Block BLOCK_QUARTZ_GLASS;
	public static Block BLOCK_QUARTZ_GLASS_FRAMED;
	public static Block BLOCK_QUARTZ_STAINED_GLASS;
	public static Block BLOCK_CINCINNASITE_BARS;
	public static Block BLOCK_QUARTZ_GLASS_PANE;
	public static Block BLOCK_QUARTZ_GLASS_FRAMED_PANE;
	public static Block BLOCK_CINCINNASITE_FORGE;
	public static Block BLOCK_NETHERRACK_FURNACE;
	public static Block BLOCK_BONE_REED_DOOR;
	public static Block BLOCK_BONE_CINCINNASITE_DOOR;
	public static Block BLOCK_QUARTZ_STAINED_GLASS_FRAMED;
	//public static Block BLOCK_QUARTZ_STAINED_GLASS_PANE;
	public static Block BLOCK_STALAGNATE_BOWL;
	public static Block BLOCK_CHEST_OF_DRAWERS;
	
	private static List<Block> renders = new ArrayList<Block>();

	public static void register()
	{
		Block[] slabs;
		BLOCK_EYEBALL = registerBlock(new BlockEyeball(), "BLOCK_EYEBALL");
		BLOCK_EYEBALL_SMALL = registerBlock(new BlockEyeballSmall(), "BLOCK_EYEBALL_SMALL");
		BLOCK_EYE_VINE = registerNoItem(new BlockEyeVine(), "BLOCK_EYE_VINE");
		BLOCK_EYE_SEED = registerBlock(new BlockEyeSeed(), "BLOCK_EYE_SEED");
		BLOCK_NETHERRACK_MOSS = registerBlock(new BlockNetherrackMoss(), "BLOCK_NETHERRACK_MOSS");
		BLOCK_NETHER_GRASS = registerBlock(new BlockNetherGrass(), "BLOCK_NETHER_GRASS");
		BLOCK_NETHER_REED = registerBlock(new BlockNetherReed(), "BLOCK_NETHER_REED");
		BLOCK_STALAGNATE_STEM = registerBlock(new BlockStalagnateStem(), "BLOCK_STALAGNATE_STEM");
		BLOCK_STALAGNATE_BOTTOM = registerNoItem(new BlockStalagnateBottom(), BLOCK_STALAGNATE_STEM, "BLOCK_STALAGNATE_BOTTOM");
		BLOCK_STALAGNATE_MIDDLE = registerNoItem(new BlockStalagnateMiddle(), BLOCK_STALAGNATE_STEM, "BLOCK_STALAGNATE_MIDDLE");
		BLOCK_STALAGNATE_TOP = registerNoItem(new BlockStalagnateTop(), BLOCK_STALAGNATE_STEM, "BLOCK_STALAGNATE_TOP");
		BLOCK_STALAGNATE_SEED = registerBlock(new BlockStalagnateSeed(), BLOCK_STALAGNATE_STEM, "BLOCK_STALAGNATE_SEED");
		BLOCK_STALAGNATE_SEED_BOTTOM = registerNoItem(new BlockStalagnateSeedBottom(), BLOCK_STALAGNATE_SEED, "BLOCK_STALAGNATE_SEED_BOTTOM");
		BLOCK_STALAGNATE_BARK = registerBlock(new BlockStalagnateBark(), BLOCK_STALAGNATE_STEM, "BLOCK_STALAGNATE_BARK");
		BLOCK_STALAGNATE_PLANKS = registerBlock(new BlockStalagnatePlanks(), BLOCK_STALAGNATE_BARK, "BLOCK_STALAGNATE_PLANKS");
		BLOCK_STALAGNATE_PLANKS_STAIRS = registerBlock(new BlockBNStairs("stalagnate_planks_stairs", BLOCK_STALAGNATE_PLANKS), BLOCK_STALAGNATE_PLANKS, "BLOCK_STALAGNATE_PLANKS_STAIRS");
		slabs = registerBlockSlab(
				new BlockBNSlabHalf("stalagnate_planks", Material.WOOD, MapColor.LIME_STAINED_HARDENED_CLAY, SoundType.WOOD),
				new BlockBNSlabDouble("stalagnate_planks", Material.WOOD, MapColor.LIME_STAINED_HARDENED_CLAY, SoundType.WOOD),
				BLOCK_STALAGNATE_PLANKS, "BLOCK_STALAGNATE_PLANKS_SLAB_DOUBLE", "BLOCK_STALAGNATE_PLANKS_SLAB_HALF");
		BLOCK_STALAGNATE_PLANKS_SLAB_DOUBLE = slabs[1];
		BLOCK_STALAGNATE_PLANKS_SLAB_HALF = slabs[0];
		BLOCK_STALAGNATE_PLANKS_FENCE = registerBlock(new WoodenFence("stalagnate_planks_fence", MapColor.CYAN_STAINED_HARDENED_CLAY), BLOCK_STALAGNATE_PLANKS, "BLOCK_STALAGNATE_PLANKS_FENCE");
		BLOCK_STALAGNATE_PLANKS_GATE = registerBlock(new BlockWoodenGate("stalagnate_planks_gate"), BLOCK_STALAGNATE_PLANKS, "BLOCK_STALAGNATE_PLANKS_GATE");
		BLOCK_STALAGNATE_PLANKS_BUTTON = registerBlock(new BlockWoodenButton("stalagnate_planks_button"), BLOCK_STALAGNATE_PLANKS, "BLOCK_STALAGNATE_PLANKS_BUTTON");
		BLOCK_STALAGNATE_PLANKS_PLATE = registerBlock(new BlockPlateWooden("stalagnate_planks_plate"), BLOCK_STALAGNATE_PLANKS, "BLOCK_STALAGNATE_PLANKS_PLATE");
		BLOCK_REEDS_BLOCK = registerBlock(new BlockReedsBlock(), "BLOCK_REEDS_BLOCK");
		BLOCK_REEDS_STAIRS = registerBlock(new BlockBNStairs("reeds_stairs", BLOCK_REEDS_BLOCK), BLOCK_REEDS_BLOCK, "BLOCK_REEDS_STAIRS");
		slabs = registerBlockSlab(
				new BlockBNSlabHalf("reeds", Material.WOOD, MapColor.CYAN, SoundType.WOOD),
				new BlockBNSlabDouble("reeds", Material.WOOD, MapColor.CYAN, SoundType.WOOD),
				BLOCK_REEDS_BLOCK, "BLOCK_REEDS_SLAB_DOUBLE", "BLOCK_REEDS_SLAB_HALF");
		BLOCK_REEDS_SLAB_DOUBLE = slabs[1];
		BLOCK_REEDS_SLAB_HALF = slabs[0];
		BLOCK_REEDS_FENCE = registerBlock(new WoodenFence("reeds_fence", MapColor.CYAN), BLOCK_REEDS_BLOCK, "BLOCK_REEDS_FENCE");
		BLOCK_REEDS_GATE = registerBlock(new BlockWoodenGate("reeds_gate"), BLOCK_REEDS_BLOCK, "BLOCK_REEDS_GATE");
		BLOCK_REEDS_BUTTON = registerBlock(new BlockWoodenButton("reeds_button"), BLOCK_REEDS_BLOCK, "BLOCK_REEDS_BUTTON");
		BLOCK_REEDS_PLATE = registerBlock(new BlockPlateWooden("reeds_plate"), BLOCK_REEDS_BLOCK, "BLOCK_REEDS_PLATE");
		BLOCK_LUCIS_MUSHROOM = registerNoItem(new BlockLucisMushroom(), "BLOCK_LUCIS_MUSHROOM");
		BLOCK_LUCIS_SPORE = registerBlock(new BlockLucisSpore(), BLOCK_LUCIS_MUSHROOM, "BLOCK_LUCIS_SPORE");
		BLOCK_NETHER_CACTUS = registerBlock(new BlockNetherCactus(), "BLOCK_NETHER_CACTUS");
		BLOCK_WART_SEED = registerBlock(new BlockWartSeed(), "BLOCK_WART_SEED");
		BLOCK_BARREL_CACTUS = registerBlock(new BlockBarrelCactus(), "BLOCK_BARREL_CACTUS");
		BLOCK_AGAVE = registerBlock(new BlockAgave(), "BLOCK_AGAVE");
		BLOCK_BLACK_BUSH = registerBlock(new BlockBlackBush(), "BLOCK_BLACK_BUSH");
		BLOCK_INK_BUSH = registerNoItem(new BlockInkBush(), "BLOCK_INK_BUSH");
		BLOCK_INK_BUSH_SEED = registerBlock(new BlockInkBushSeed(), BLOCK_INK_BUSH, "BLOCK_INK_BUSH_SEED");
		BLOCK_SMOKER = registerBlock(new BlockSmoker(), "BLOCK_SMOKER");
		BLOCK_EGG_PLANT = registerBlock(new BlockEggPlant(), "BLOCK_EGG_PLANT");
		BLOCK_BLACK_APPLE = registerNoItem(new BlockBlackApple(), "BLOCK_BLACK_APPLE");
		BLOCK_BLACK_APPLE_SEED = registerBlock(new BlockBlackAppleSeed(), BLOCK_BLACK_APPLE, "BLOCK_BLACK_APPLE_SEED");
		BLOCK_MAGMA_FLOWER = registerBlock(new BlockMagmaFlower(), "BLOCK_MAGMA_FLOWER");
		BLOCK_NETHER_MYCELIUM = registerBlock(new BlockNetherMycelium(), "BLOCK_NETHER_MYCELIUM");
		BLOCK_RED_LARGE_MUSHROOM = registerNoItem(new BlockRedLargeMushroom(), "BLOCK_RED_LARGE_MUSHROOM");
		BLOCK_BROWN_LARGE_MUSHROOM = registerNoItem(new BlockBrownLargeMushroom(), "BLOCK_BROWN_LARGE_MUSHROOM");
		BLOCK_ORANGE_MUSHROOM = registerBlock(new BlockOrangeMushroom(), "BLOCK_ORANGE_MUSHROOM");
		BLOCK_RED_MOLD = registerBlock(new BlockRedMold(), "BLOCK_RED_MOLD");
		BLOCK_GRAY_MOLD = registerBlock(new BlockGrayMold(), "BLOCK_GRAY_MOLD");
		BLOCK_CINCINNASITE_ORE = registerBlock(new BlockCincinnasitOre(), "BLOCK_CINCINNASITE_ORE");
		BLOCK_CINCINNASITE = registerBlock(new BlockCincinnasite("cincinnasite_block"), "BLOCK_CINCINNASITE");
		BLOCK_CINCINNASITE_FORGED = registerBlock(new BlockCincinnasite("cincinnasite_forged"), BLOCK_CINCINNASITE, "BLOCK_CINCINNASITE_FORGED");
		BLOCK_CINCINNASITE_PILLAR = registerBlock(new BlockCincinnasitPillar("cincinnasite_pillar"), BLOCK_CINCINNASITE_FORGED, "BLOCK_CINCINNASITE_PILLAR");
		BLOCK_CINCINNASITE_BRICKS = registerBlock(new BlockCincinnasite("cincinnasite_bricks"), BLOCK_CINCINNASITE_FORGED, "BLOCK_CINCINNASITE_BRICKS");
		BLOCK_CINCINNASITE_BRICK_PLATE = registerBlock(new BlockCincinnasite("cincinnasite_brick_plate"), BLOCK_CINCINNASITE_FORGED, "BLOCK_CINCINNASITE_BRICK_PLATE");
		BLOCK_CINCINNASITE_STAIRS = registerBlock(new BlockBNStairs("cincinnasite_stairs", BLOCK_CINCINNASITE_FORGED), BLOCK_CINCINNASITE_FORGED, "BLOCK_CINCINNASITE_STAIRS");
		slabs = registerBlockSlab(
				new BlockBNSlabHalf("cincinnasite", Material.IRON, MapColor.YELLOW, SoundType.METAL),
				new BlockBNSlabDouble("cincinnasite", Material.IRON, MapColor.YELLOW, SoundType.METAL),
				BLOCK_CINCINNASITE, "BLOCK_CINCINNASITE_SLAB_DOUBLE", "BLOCK_CINCINNASITE_SLAB_HALF");
		BLOCK_CINCINNASITE_SLAB_DOUBLE = slabs[1];
		BLOCK_CINCINNASITE_SLAB_HALF = slabs[0];
		BLOCK_CINCINNASITE_BUTTON = registerBlock(new BlockWoodenButton("cincinnasite_button"), BLOCK_CINCINNASITE_FORGED, "BLOCK_CINCINNASITE_BUTTON");
		BLOCK_CINCINNASITE_PLATE = registerBlock(new BlockCincinnasitePlate(), BLOCK_CINCINNASITE_FORGED, "BLOCK_CINCINNASITE_PLATE");
		BLOCK_CINCINNASITE_LANTERN = registerBlock(new BlockCincinnasiteLantern(), BLOCK_CINCINNASITE_FORGED, "BLOCK_CINCINNASITE_LANTERN");
		BLOCK_CINCINNASITE_TILE_LARGE = registerBlock(new BlockCincinnasite("cincinnasite_tile_large"), BLOCK_CINCINNASITE_FORGED, "BLOCK_CINCINNASITE_TILE_LARGE");
		BLOCK_CINCINNASITE_TILE_SMALL = registerBlock(new BlockCincinnasite("cincinnasite_tile_small"), BLOCK_CINCINNASITE_FORGED, "BLOCK_CINCINNASITE_TILE_SMALL");
		BLOCK_CINCINNASITE_CARVED = registerBlock(new BlockCincinnasite("cincinnasite_carved"), BLOCK_CINCINNASITE_FORGED, "BLOCK_CINCINNASITE_CARVED");
		BLOCK_NETHER_BRICK_TILE_LARGE = registerBlock(new BNNetherBrick("nether_brick_tile_large"), "BLOCK_NETHER_BRICK_TILE_LARGE");
		BLOCK_NETHER_BRICK_TILE_SMALL = registerBlock(new BNNetherBrick("nether_brick_tile_small"), "BLOCK_NETHER_BRICK_TILE_SMALL");
		BLOCK_CINCINNASITE_WALL = registerBlock(new BNWall("cincinnasite_wall", Material.IRON, MapColor.YELLOW, SoundType.METAL), BLOCK_CINCINNASITE_FORGED, "BLOCK_CINCINNASITE_WALL");
		BLOCK_BONE = registerBlock(new BNBlockBone("bone_block"), "BLOCK_BONE");
		BLOCK_BONE_STAIRS = registerBlock(new BlockBNStairs("bone_stairs", BLOCK_BONE), "BLOCK_BONE_STAIRS");
		slabs = registerBlockSlab(
				new BlockBNSlabHalf("bone", Material.ROCK, MapColor.SAND, SoundType.STONE),
				new BlockBNSlabDouble("bone", Material.ROCK, MapColor.SAND, SoundType.STONE),
				BLOCK_BONE, "BLOCK_BONE_SLAB_DOUBLE", "BLOCK_BONE_SLAB_HALF");
		BLOCK_BONE_SLAB_DOUBLE = slabs[1];
		BLOCK_BONE_SLAB_HALF = slabs[0];
		BLOCK_BONE_BUTTON = registerBlock(new BlockWoodenButton("bone_button"), BLOCK_BONE, "BLOCK_BONE_BUTTON");
		BLOCK_BONE_PLATE = registerBlock(new BlockPlateWooden("bone_plate"), BLOCK_BONE, "BLOCK_BONE_PLATE");
		BLOCK_CINCINNASITE_FIRE_BOWL = registerBlock(new BlockCincinnasitFireBowl(), BLOCK_CINCINNASITE_FORGED, "BLOCK_CINCINNASITE_FIRE_BOWL");
		BLOCK_BONE_WALL = registerBlock(new BNWall("bone_wall", Material.ROCK, MapColor.SAND, SoundType.STONE), BLOCK_BONE, "BLOCK_BONE_WALL");
		BLOCK_NETHER_BRICK_WALL = registerBlock(new BNWall("nether_brick_wall", Material.ROCK, MapColor.NETHERRACK, SoundType.STONE), "BLOCK_NETHER_BRICK_WALL");
		slabs = registerBlockSlab(
				new BlockBNSlabHalf("nether_brick_tile", Material.ROCK, MapColor.NETHERRACK, SoundType.STONE),
				new BlockBNSlabDouble("nether_brick_tile", Material.ROCK, MapColor.NETHERRACK, SoundType.STONE),
				BLOCK_NETHER_BRICK_TILE_SMALL, "BLOCK_NETHER_BRICK_TILE_SLAB_DOUBLE", "BLOCK_NETHER_BRICK_TILE_SLAB_HALF");
		BLOCK_NETHER_BRICK_TILE_SLAB_DOUBLE = slabs[1];
		BLOCK_NETHER_BRICK_TILE_SLAB_HALF = slabs[0];
		BLOCK_NETHER_BRICK_TILE_STAIRS = registerBlock(new BlockBNStairs("nether_brick_tile_stairs", BLOCK_NETHER_BRICK_TILE_SMALL), BLOCK_NETHER_BRICK_TILE_SMALL, "BLOCK_NETHER_BRICK_TILE_STAIRS");
		BLOCK_BONE_MUSHROOM = registerBlock(new BlockBoneMushroom(), BLOCK_BONE, "BLOCK_BONE_MUSHROOM");
		BLOCK_REEDS_LADDER = registerBlock(new BlockReedsLadder(), BLOCK_REEDS_BLOCK, "BLOCK_REEDS_LADDER");
		BLOCK_CINCINNASITE_BRICKS_PILLAR = registerBlock(new BlockPillar("cincinnasite_bricks_pillar"), BLOCK_CINCINNASITE_FORGED, "BLOCK_CINCINNASITE_BRICKS_PILLAR");
		BLOCK_BONE_TILE = registerBlock(new BNBlockBone("bone_tile"), BLOCK_BONE, "BLOCK_BONE_TILE");
		BLOCK_CINCINNASITE_PEDESTAL = registerBlock(new BlockCincinnasitePedestal(), BLOCK_CINCINNASITE_FORGED, "BLOCK_CINCINNASITE_PEDESTAL");
		BLOCK_PIG_STATUE_RESPAWNER = registerBlock(new BlockStatueRespawner("pig_statue_01"), "BLOCK_PIG_STATUE_RESPAWNER");
		BLOCK_CINCINNASITE_POT = registerBlock(new BlockCincinnasitePot(), "BLOCK_CINCINNASITE_POT");
		BLOCK_POTTED_PLANT = registerNoItem(new BlockPottedPlant1(), BLOCK_CINCINNASITE_POT, "BLOCK_POTTED_PLANT");
		BLOCK_CINCINNASITE_FRAME = registerBlock(new BlockCincinnasiteFrame("cincinnasite_frame"), BLOCK_CINCINNASITE_FORGED, "BLOCK_CINCINNASITE_FRAME");
		BLOCK_QUARTZ_GLASS = registerBlock(new BlockQuartzGlass("quartz_glass"), "BLOCK_QUARTZ_GLASS");
		BLOCK_QUARTZ_GLASS_FRAMED = registerBlock(new BlockQuartzGlass("quartz_glass_framed"), "BLOCK_QUARTZ_GLASS_FRAMED");
		BLOCK_QUARTZ_STAINED_GLASS = registerColoredBlock(new BlockStainedQuartzGlass("quartz_stained_glass"), "BLOCK_QUARTZ_STAINED_GLASS");
		BLOCK_CINCINNASITE_BARS = registerBlock(new CincinnasitePane("cincinnasite_bars"), "BLOCK_CINCINNASITE_BARS");
		BLOCK_QUARTZ_GLASS_PANE = registerBlock(new ClearQuartzGlassPane("quartz_glass_pane"), "BLOCK_QUARTZ_GLASS_PANE");
		BLOCK_QUARTZ_GLASS_FRAMED_PANE = registerBlock(new QuartzGlassPane("quartz_glass_framed_pane"), "BLOCK_QUARTZ_GLASS_FRAMED_PANE");
		BLOCK_CINCINNASITE_FORGE = registerBlock(new BlockCincinnasiteForge("cincinnasite_forge"), "BLOCK_CINCINNASITE_FORGE");
		BLOCK_NETHERRACK_FURNACE = registerBlock(new BlockNetherrackFurnace(), "BLOCK_NETHERRACK_FURNACE");
		BLOCK_BONE_REED_DOOR = registerBlock(new BlockBNDoor("bone_reed_door", Material.ROCK, SoundType.STONE), "BLOCK_BONE_REED_DOOR");
		BLOCK_BONE_CINCINNASITE_DOOR = registerBlock(new BlockBNDoor("bone_cincinnasite_door", Material.ROCK, SoundType.STONE), "BLOCK_BONE_CINCINNASITE_DOOR");
		BLOCK_QUARTZ_STAINED_GLASS_FRAMED = registerColoredBlock(new BlockStainedQuartzGlass("quartz_stained_glass_framed"), "BLOCK_QUARTZ_STAINED_GLASS_FRAMED");
		//BLOCK_QUARTZ_STAINED_GLASS_PANE = registerColoredBlock(new BlockStainedQuartzGlassPane("quartz_stained_glass_pane"), "BLOCK_QUARTZ_STAINED_GLASS_PANE");
		BLOCK_STALAGNATE_BOWL = registerNoItem(new BlockStalagnateBowl(), "BLOCK_STALAGNATE_BOWL");
		BLOCK_CHEST_OF_DRAWERS = registerBlock(new BlockChestOfDrawers(), "BLOCK_CHEST_OF_DRAWERS");
	}

	public static void registerOreDictionary()
	{
		addToDictionary("logWood", BLOCK_STALAGNATE_STEM);
		addToDictionary("plankWood", BLOCK_STALAGNATE_PLANKS);
		addToDictionary("plankWood", BLOCK_REEDS_BLOCK);
		addToDictionary("slabWood", BLOCK_STALAGNATE_PLANKS_SLAB_HALF);
		addToDictionary("slabWood", BLOCK_REEDS_SLAB_HALF);
		addToDictionary("paneQuartzGlass", BLOCK_QUARTZ_GLASS_PANE);
		addToDictionary("blockQuartzGlass", BLOCK_QUARTZ_GLASS);
		addToDictionary("blockCincinnasite", BLOCK_CINCINNASITE_FORGED);
		addToDictionary("blockCincinnasite", BLOCK_CINCINNASITE_PILLAR);
		addToDictionary("blockCincinnasite", BLOCK_CINCINNASITE_TILE_LARGE);
		addToDictionary("blockCincinnasite", BLOCK_CINCINNASITE_TILE_SMALL);
		addToDictionary("oreCincinnasite", BLOCK_CINCINNASITE_ORE);
		addToDictionary("sugarcane", BLOCK_NETHER_REED);
	}
	
	private static void addToDictionary(String name, Block block)
	{
		if (block!= Blocks.AIR)
		{
			OreDictionary.registerOre(name, new ItemStack(block));
		}
	}

	@SideOnly(Side.CLIENT)
	public static void registerRender()
	{
		for (Block b : renders)
			setRender(b);
	}

	private static Block registerBlock(Block block, String key)
	{
		if (ConfigLoader.mustInitBlock(key))
		{
			ForgeRegistries.BLOCKS.register(block);
			ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
			renders.add(block);
			return block;
		}
		else
			return Blocks.AIR;
	}
	
	private static Block registerBlock(Block block, Block parrent, String key)
	{
		if (parrent != Blocks.AIR)
			return registerBlock(block, key);
		else
			return Blocks.AIR;
	}
	
	private static Block registerBlockDoor(Block block, String key)
	{
		if (ConfigLoader.mustInitBlock(key))
		{
			ForgeRegistries.BLOCKS.register(block);
			ForgeRegistries.ITEMS.register(
					new ItemDoor(block)
					.setRegistryName(block.getRegistryName())
					.setCreativeTab(BetterNether.BN_TAB));
			renders.add(block);
			return block;
		}
		else
			return Blocks.AIR;
	}
	
	private static Block registerNoItem(Block block, String key)
	{
		if (ConfigLoader.mustInitBlock(key))
		{
			ForgeRegistries.BLOCKS.register(block);
			return block;
		}
		else
			return Blocks.AIR;
	}
	
	private static Block registerNoItem(Block block, Block parrent, String key)
	{
		if (parrent!= Blocks.AIR)
			return registerNoItem(block, key);
		else
			return Blocks.AIR;
	}
	
	private static Block[] registerBlockSlab(Block blockSingle, Block blockDouble, Block parrent, String key1, String key2)
	{
		Block[] res = new Block[2];
		if (ConfigLoader.mustInitBlock(key1) && ConfigLoader.mustInitBlock(key2) && parrent!= Blocks.AIR)
		{
			((BlockBNSlab) blockDouble).setDrop(blockSingle);
			((BlockBNSlabHalf) blockSingle).setDoubleSlab(blockDouble);
			ForgeRegistries.BLOCKS.register(blockSingle);
			ForgeRegistries.BLOCKS.register(blockDouble);
			ForgeRegistries.ITEMS.register(new ItemSlab(blockSingle, (BlockSlab) blockSingle, (BlockSlab) blockDouble)
					.setRegistryName(blockSingle.getRegistryName()));
			renders.add(blockSingle);
			res[0] = blockSingle;
			res[1] = blockDouble;
		}
		return res;
	}

	@SideOnly(Side.CLIENT)
	private static void setRender(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		if (item instanceof ItemCloth)
			for (int i = 0; i < 16; i++)
			{
				Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, i, new ModelResourceLocation(item.getRegistryName(), "color="+ EnumDyeColor.byMetadata(i).getDyeColorName()));
			}
			else
				Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
	
	private static Block registerColoredBlock(Block block, String key)
	{
		if (ConfigLoader.mustInitBlock(key))
		{
			ForgeRegistries.BLOCKS.register(block);
			ForgeRegistries.ITEMS.register(new ItemCloth(block)
					.setRegistryName(block.getRegistryName()).setUnlocalizedName(block.getUnlocalizedName()));
			renders.add(block);
			return block;
		}
		else
			return null;
	}
}
