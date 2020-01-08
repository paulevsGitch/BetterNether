package paulevs.betternether.registers;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PressurePlateBlock.ActivationRule;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blocks.BNBoneBlock;
import paulevs.betternether.blocks.BNButton;
import paulevs.betternether.blocks.BNDoor;
import paulevs.betternether.blocks.BNFence;
import paulevs.betternether.blocks.BNGate;
import paulevs.betternether.blocks.BNNetherBrick;
import paulevs.betternether.blocks.BNPlate;
import paulevs.betternether.blocks.BNSlab;
import paulevs.betternether.blocks.BNStairs;
import paulevs.betternether.blocks.BNTrapdoor;
import paulevs.betternether.blocks.BNWall;
import paulevs.betternether.blocks.BlockAgave;
import paulevs.betternether.blocks.BlockBarrelCactus;
import paulevs.betternether.blocks.BlockBlackApple;
import paulevs.betternether.blocks.BlockBlackAppleSeed;
import paulevs.betternether.blocks.BlockBlackBush;
import paulevs.betternether.blocks.BlockBrownLargeMushroom;
import paulevs.betternether.blocks.BlockCincinnasitFireBowl;
import paulevs.betternether.blocks.BlockCincinnasitPillar;
import paulevs.betternether.blocks.BlockCincinnasite;
import paulevs.betternether.blocks.BlockCincinnasiteOre;
import paulevs.betternether.blocks.BlockEggPlant;
import paulevs.betternether.blocks.BlockEyeSeed;
import paulevs.betternether.blocks.BlockEyeVine;
import paulevs.betternether.blocks.BlockEyeball;
import paulevs.betternether.blocks.BlockEyeballSmall;
import paulevs.betternether.blocks.BlockFarmland;
import paulevs.betternether.blocks.BlockGrayMold;
import paulevs.betternether.blocks.BlockInkBush;
import paulevs.betternether.blocks.BlockInkBushSeed;
import paulevs.betternether.blocks.BlockLucisMushroom;
import paulevs.betternether.blocks.BlockLucisSpore;
import paulevs.betternether.blocks.BlockMagmaFlower;
import paulevs.betternether.blocks.BlockNetherCactus;
import paulevs.betternether.blocks.BlockNetherGrass;
import paulevs.betternether.blocks.BlockNetherMycelium;
import paulevs.betternether.blocks.BlockNetherReed;
import paulevs.betternether.blocks.BlockNetherrackMoss;
import paulevs.betternether.blocks.BlockOrangeMushroom;
import paulevs.betternether.blocks.BlockRedLargeMushroom;
import paulevs.betternether.blocks.BlockRedMold;
import paulevs.betternether.blocks.BlockReedsBlock;
import paulevs.betternether.blocks.BlockSmoker;
import paulevs.betternether.blocks.BlockStalagnate;
import paulevs.betternether.blocks.BlockStalagnateBark;
import paulevs.betternether.blocks.BlockStalagnatePlanks;
import paulevs.betternether.blocks.BlockStalagnateSeed;
import paulevs.betternether.blocks.BlockStalagnateStem;
import paulevs.betternether.blocks.BlockWartSeed;
import paulevs.betternether.config.Config;
import paulevs.betternether.tab.CreativeTab;

public class BlocksRegister
{
	public static final Block BLOCK_EYEBALL = new BlockEyeball();
	public static final Block BLOCK_EYEBALL_SMALL = new BlockEyeballSmall();
	public static final Block BLOCK_EYE_VINE = new BlockEyeVine();
	public static final Block BLOCK_EYE_SEED = new BlockEyeSeed();
	public static final Block BLOCK_NETHERRACK_MOSS = new BlockNetherrackMoss();
	public static final Block BLOCK_NETHER_GRASS = new BlockNetherGrass();
	public static final Block BLOCK_NETHER_REED = new BlockNetherReed();
	public static final Block BLOCK_STALAGNATE_STEM = new BlockStalagnateStem();
	public static final Block BLOCK_STALAGNATE = new BlockStalagnate();
	public static final Block BLOCK_STALAGNATE_SEED = new BlockStalagnateSeed();
	public static final Block BLOCK_STALAGNATE_BARK = new BlockStalagnateBark();
	public static final Block BLOCK_STALAGNATE_PLANKS = new BlockStalagnatePlanks();
	public static final Block BLOCK_STALAGNATE_PLANKS_STAIRS = new BNStairs(BLOCK_STALAGNATE_PLANKS);
	public static final Block BLOCK_STALAGNATE_PLANKS_SLAB = new BNSlab(BLOCK_STALAGNATE_PLANKS);
	public static final Block BLOCK_STALAGNATE_PLANKS_FENCE = new BNFence(BLOCK_STALAGNATE_PLANKS);
	public static final Block BLOCK_STALAGNATE_PLANKS_GATE = new BNGate(BLOCK_STALAGNATE_PLANKS);
	public static final Block BLOCK_STALAGNATE_PLANKS_BUTTON = new BNButton(BLOCK_STALAGNATE_PLANKS);
	public static final Block BLOCK_STALAGNATE_PLANKS_PLATE = new BNPlate(ActivationRule.EVERYTHING, BLOCK_STALAGNATE_PLANKS);
	public static final Block BLOCK_STALAGNATE_PLANKS_TRAPDOOR = new BNTrapdoor(BLOCK_STALAGNATE_PLANKS);
	public static final Block BLOCK_STALAGNATE_PLANKS_DOOR = new BNDoor(BLOCK_STALAGNATE_PLANKS);
	public static final Block BLOCK_REEDS_BLOCK = new BlockReedsBlock();
	public static final Block BLOCK_REEDS_STAIRS = new BNStairs(BLOCK_REEDS_BLOCK);
	public static final Block BLOCK_REEDS_SLAB = new BNSlab(BLOCK_REEDS_BLOCK);
	public static final Block BLOCK_REEDS_FENCE = new BNFence(BLOCK_REEDS_BLOCK);
	public static final Block BLOCK_REEDS_GATE = new BNGate(BLOCK_REEDS_BLOCK);
	public static final Block BLOCK_REEDS_BUTTON = new BNButton(BLOCK_REEDS_BLOCK);
	public static final Block BLOCK_REEDS_PLATE = new BNPlate(ActivationRule.EVERYTHING, BLOCK_REEDS_BLOCK);
	public static final Block BLOCK_REEDS_TRAPDOOR = new BNTrapdoor(BLOCK_REEDS_BLOCK);
	public static final Block BLOCK_REEDS_DOOR = new BNDoor(BLOCK_REEDS_BLOCK);
	public static final Block BLOCK_LUCIS_MUSHROOM = new BlockLucisMushroom();
	public static final Block BLOCK_LUCIS_SPORE = new BlockLucisSpore();
	public static final Block BLOCK_NETHER_CACTUS = new BlockNetherCactus();
	public static final Block BLOCK_WART_SEED = new BlockWartSeed();
	public static final Block BLOCK_BARREL_CACTUS = new BlockBarrelCactus();
	public static final Block BLOCK_AGAVE = new BlockAgave();
	public static final Block BLOCK_BLACK_BUSH = new BlockBlackBush();
	public static final Block BLOCK_INK_BUSH = new BlockInkBush();
	public static final Block BLOCK_INK_BUSH_SEED = new BlockInkBushSeed();
	public static final Block BLOCK_SMOKER = new BlockSmoker();
	public static final Block BLOCK_EGG_PLANT = new BlockEggPlant();
	public static final Block BLOCK_BLACK_APPLE = new BlockBlackApple();
	public static final Block BLOCK_BLACK_APPLE_SEED = new BlockBlackAppleSeed();
	public static final Block BLOCK_MAGMA_FLOWER = new BlockMagmaFlower();
	public static final Block BLOCK_NETHER_MYCELIUM = new BlockNetherMycelium();
	public static final Block BLOCK_RED_LARGE_MUSHROOM = new BlockRedLargeMushroom();
	public static final Block BLOCK_BROWN_LARGE_MUSHROOM = new BlockBrownLargeMushroom();
	public static final Block BLOCK_ORANGE_MUSHROOM = new BlockOrangeMushroom();
	public static final Block BLOCK_RED_MOLD = new BlockRedMold();
	public static final Block BLOCK_GRAY_MOLD = new BlockGrayMold();
	public static final Block BLOCK_CINCINNASITE_ORE = new BlockCincinnasiteOre();
	public static final Block BLOCK_CINCINNASITE = new BlockCincinnasite();
	public static final Block BLOCK_CINCINNASITE_FORGED = new BlockCincinnasite();
	public static final Block BLOCK_CINCINNASITE_PILLAR = new BlockCincinnasitPillar();
	public static final Block BLOCK_CINCINNASITE_BRICKS = new BlockCincinnasite();
	public static final Block BLOCK_CINCINNASITE_BRICK_PLATE = new BlockCincinnasite();
	public static final Block BLOCK_CINCINNASITE_STAIRS = new BNStairs(BLOCK_CINCINNASITE);
	public static final Block BLOCK_CINCINNASITE_SLAB = new BNSlab(BLOCK_CINCINNASITE);
	public static final Block BLOCK_CINCINNASITE_BUTTON = new BNButton(BLOCK_CINCINNASITE);
	public static final Block BLOCK_CINCINNASITE_PLATE = new BNPlate(ActivationRule.MOBS, BLOCK_CINCINNASITE);
	public static final Block BLOCK_CINCINNASITE_LANTERN = new BlockCincinnasiteLantern();
	public static final Block BLOCK_CINCINNASITE_TILE_LARGE = new BlockCincinnasite();
	public static final Block BLOCK_CINCINNASITE_TILE_SMALL = new BlockCincinnasite();
	public static final Block BLOCK_CINCINNASITE_CARVED = new BlockCincinnasite();
	public static final Block BLOCK_NETHER_BRICK_TILE_LARGE = new BNNetherBrick();
	public static final Block BLOCK_NETHER_BRICK_TILE_SMALL = new BNNetherBrick();
	public static final Block BLOCK_CINCINNASITE_WALL = new BNWall(BLOCK_CINCINNASITE);
	public static final Block BLOCK_BONE = new BNBoneBlock();
	public static final Block BLOCK_BONE_STAIRS = new BNStairs(BLOCK_BONE);
	public static final Block BLOCK_BONE_SLAB = new BNSlab(BLOCK_BONE);
	public static final Block BLOCK_BONE_BUTTON = new BNButton(BLOCK_BONE);
	public static final Block BLOCK_BONE_PLATE = new BNPlate(ActivationRule.EVERYTHING, BLOCK_BONE);
	public static final Block BLOCK_CINCINNASITE_FIRE_BOWL = new BlockCincinnasitFireBowl();
	public static final Block BLOCK_BONE_WALL = new BNWall(BLOCK_BONE);
	public static final Block BLOCK_NETHER_BRICK_WALL = new BNWall(Blocks.NETHER_BRICKS);
	public static final Block BLOCK_NETHER_BRICK_TILE_SLAB = new BNSlab(BLOCK_NETHER_BRICK_TILE_SMALL);
	public static final Block BLOCK_NETHER_BRICK_TILE_STAIRS = new BNStairs(BLOCK_NETHER_BRICK_TILE_SMALL);
	
	// -- NEW --
	public static final Block FARMLAND = new BlockFarmland();
	
	public static void register()
	{
		registerBlock("block_eyeball", BLOCK_EYEBALL);
		registerBlock("block_eyeball_small", BLOCK_EYEBALL_SMALL);
		registerBlockNI("eye_vine", BLOCK_EYE_VINE);
		registerBlock("eye_seed", BLOCK_EYE_SEED);
		registerBlock("netherrack_moss", BLOCK_NETHERRACK_MOSS);
		registerBlock("nether_grass", BLOCK_NETHER_GRASS);
		registerBlock("nether_reed", BLOCK_NETHER_REED);
		registerBlock("stalagnate_stem", BLOCK_STALAGNATE_STEM);
		registerBlockNI("stalagnate", BLOCK_STALAGNATE);
		registerBlock("stalagnate_seed", BLOCK_STALAGNATE_SEED);
		registerBlock("stalagnate_bark", BLOCK_STALAGNATE_BARK);
		registerBlock("stalagnate_planks", BLOCK_STALAGNATE_PLANKS);
		registerBlock("stalagnate_planks_stairs", BLOCK_STALAGNATE_PLANKS_STAIRS);
		registerBlock("stalagnate_planks_slab", BLOCK_STALAGNATE_PLANKS_SLAB);
		registerBlock("stalagnate_planks_fence", BLOCK_STALAGNATE_PLANKS_FENCE);
		registerBlock("stalagnate_planks_gate", BLOCK_STALAGNATE_PLANKS_GATE);
		registerBlock("stalagnate_planks_button", BLOCK_STALAGNATE_PLANKS_BUTTON);
		registerBlock("stalagnate_planks_plate", BLOCK_STALAGNATE_PLANKS_PLATE);
		registerBlock("stalagnate_planks_trapdoor", BLOCK_STALAGNATE_PLANKS_TRAPDOOR);
		registerBlock("stalagnate_planks_door", BLOCK_STALAGNATE_PLANKS_DOOR);
		registerBlock("reeds_block", BLOCK_REEDS_BLOCK);
		registerBlock("reeds_stairs", BLOCK_REEDS_STAIRS);
		registerBlock("reeds_slab", BLOCK_REEDS_SLAB);
		registerBlock("reeds_fence", BLOCK_REEDS_FENCE);
		registerBlock("reeds_gate", BLOCK_REEDS_GATE);
		registerBlock("reeds_button", BLOCK_REEDS_BUTTON);
		registerBlock("reeds_plate", BLOCK_REEDS_PLATE);
		registerBlock("reeds_trapdoor", BLOCK_REEDS_TRAPDOOR);
		registerBlock("reeds_door", BLOCK_REEDS_DOOR);
		registerBlockNI("lucis_mushroom", BLOCK_LUCIS_MUSHROOM);
		registerBlock("lucis_spore", BLOCK_LUCIS_SPORE);
		registerBlock("nether_cactus", BLOCK_NETHER_CACTUS);
		registerBlock("wart_seed", BLOCK_WART_SEED);
		registerBlock("barrel_cactus", BLOCK_BARREL_CACTUS);
		registerBlock("agave", BLOCK_AGAVE);
		registerBlock("black_bush", BLOCK_BLACK_BUSH);
		registerBlockNI("ink_bush", BLOCK_INK_BUSH);
		registerBlock("ink_bush_seed", BLOCK_INK_BUSH_SEED);
		registerBlock("smoker", BLOCK_SMOKER);
		registerBlock("egg_plant", BLOCK_EGG_PLANT);
		registerBlockNI("black_apple", BLOCK_BLACK_APPLE);
		registerBlock("black_apple_seed", BLOCK_BLACK_APPLE_SEED);
		registerBlock("magma_flower", BLOCK_MAGMA_FLOWER);
		registerBlock("nether_mycelium", BLOCK_NETHER_MYCELIUM);
		registerBlockNI("red_large_mushroom", BLOCK_RED_LARGE_MUSHROOM);
		registerBlockNI("brown_large_mushroom", BLOCK_BROWN_LARGE_MUSHROOM);
		registerBlock("orange_mushroom", BLOCK_ORANGE_MUSHROOM);
		registerBlock("red_mold", BLOCK_RED_MOLD);
		registerBlock("gray_mold", BLOCK_GRAY_MOLD);
		registerBlock("cincinnasite_ore", BLOCK_CINCINNASITE_ORE);
		registerBlock("cincinnasite_block", BLOCK_CINCINNASITE);
		registerBlock("cincinnasite_forged", BLOCK_CINCINNASITE_FORGED);
		registerBlock("cincinnasite_pillar", BLOCK_CINCINNASITE_PILLAR);
		registerBlock("cincinnasite_bricks", BLOCK_CINCINNASITE_BRICKS);
		registerBlock("cincinnasite_brick_plate", BLOCK_CINCINNASITE_BRICK_PLATE);
		registerBlock("cincinnasite_stairs", BLOCK_CINCINNASITE_STAIRS);
		registerBlock("cincinnasite_slab", BLOCK_CINCINNASITE_SLAB);
		registerBlock("cincinnasite_button", BLOCK_CINCINNASITE_BUTTON);
		registerBlock("cincinnasite_plate", BLOCK_CINCINNASITE_PLATE);
		registerBlock("cincinnasite_lantern", BLOCK_CINCINNASITE_LANTERN);
		registerBlock("cincinnasite_tile_large", BLOCK_CINCINNASITE_TILE_LARGE);
		registerBlock("cincinnasite_tile_small", BLOCK_CINCINNASITE_TILE_SMALL);
		registerBlock("cincinnasite_carved", BLOCK_CINCINNASITE_CARVED);
		registerBlock("nether_brick_tile_large", BLOCK_NETHER_BRICK_TILE_LARGE);
		registerBlock("nether_brick_tile_small", BLOCK_NETHER_BRICK_TILE_SMALL);
		registerBlock("cincinnasite_wall", BLOCK_CINCINNASITE_WALL);
		registerBlock("bone_block", BLOCK_BONE);
		registerBlock("bone_stairs", BLOCK_BONE_STAIRS);
		registerBlock("bone_slab", BLOCK_BONE_SLAB);
		registerBlock("bone_button", BLOCK_BONE_BUTTON);
		registerBlock("bone_plate", BLOCK_BONE_PLATE);
		registerBlock("cincinnasite_fire_bowl", BLOCK_CINCINNASITE_FIRE_BOWL);
		registerBlock("bone_wall", BLOCK_BONE_WALL);
		registerBlock("nether_brick_wall", BLOCK_NETHER_BRICK_WALL);
		registerBlock("nether_brick_tile_slab", BLOCK_NETHER_BRICK_TILE_SLAB);
		registerBlock("nether_brick_tile_stairs", BLOCK_NETHER_BRICK_TILE_STAIRS);
		
		registerBlock("farmland", FARMLAND);
	}
	
	private static void registerBlock(String name, Block block)
	{
		if (Config.getBoolean("blocks", name, true))
		{
			Registry.register(Registry.BLOCK, new Identifier(BetterNether.MOD_ID, name), block);
			ItemsRegister.RegisterItem(name, new BlockItem(block, new Item.Settings().group(CreativeTab.BN_TAB)));
		}
	}
	
	private static void registerBlockNI(String name, Block block)
	{
		if (Config.getBoolean("blocks", name, true))
		{
			Registry.register(Registry.BLOCK, new Identifier(BetterNether.MOD_ID, name), block);
		}
	}
}
