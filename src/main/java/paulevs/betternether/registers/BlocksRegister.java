package paulevs.betternether.registers;

import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock.ActivationRule;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.blocks.BNButtom;
import paulevs.betternether.blocks.BNFence;
import paulevs.betternether.blocks.BNGate;
import paulevs.betternether.blocks.BNPlate;
import paulevs.betternether.blocks.BNSlab;
import paulevs.betternether.blocks.BNStairs;
import paulevs.betternether.blocks.BNTrapdoor;
import paulevs.betternether.blocks.BlockEyeSeed;
import paulevs.betternether.blocks.BlockEyeVine;
import paulevs.betternether.blocks.BlockEyeball;
import paulevs.betternether.blocks.BlockEyeballSmall;
import paulevs.betternether.blocks.BlockNetherGrass;
import paulevs.betternether.blocks.BlockNetherReed;
import paulevs.betternether.blocks.BlockNetherrackMoss;
import paulevs.betternether.blocks.BlockStalagnate;
import paulevs.betternether.blocks.BlockStalagnateBark;
import paulevs.betternether.blocks.BlockStalagnatePlanks;
import paulevs.betternether.blocks.BlockStalagnateSeed;
import paulevs.betternether.blocks.BlockStalagnateStem;
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
	public static final Block BLOCK_STALAGNATE_PLANKS_BUTTON = new BNButtom(BLOCK_STALAGNATE_PLANKS);
	public static final Block BLOCK_STALAGNATE_PLANKS_PLATE = new BNPlate(ActivationRule.EVERYTHING, BLOCK_STALAGNATE_PLANKS);
	public static final Block BLOCK_STALAGNATE_PLANKS_TRAPDOOR = new BNTrapdoor(BLOCK_STALAGNATE_PLANKS);
	
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
	}
	
	private static void registerBlock(String name, Block block)
	{
		if (Config.getBoolean("blocks", name, true))
		{
			Registry.register(Registry.BLOCK, new Identifier("betternether", name), block);
			ItemsRegister.RegisterItem(name, new BlockItem(block, new Item.Settings().group(CreativeTab.BN_TAB)));
		}
	}
	
	private static void registerBlockNI(String name, Block block)
	{
		if (Config.getBoolean("blocks", name, true))
		{
			Registry.register(Registry.BLOCK, new Identifier("betternether", name), block);
		}
	}
}
