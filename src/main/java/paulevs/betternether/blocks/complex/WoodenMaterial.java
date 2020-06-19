package paulevs.betternether.blocks.complex;

import net.minecraft.block.Block;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.PressurePlateBlock.ActivationRule;
import paulevs.betternether.blocks.BNButton;
import paulevs.betternether.blocks.BNDoor;
import paulevs.betternether.blocks.BNFence;
import paulevs.betternether.blocks.BNGate;
import paulevs.betternether.blocks.BNLogStripable;
import paulevs.betternether.blocks.BNPillar;
import paulevs.betternether.blocks.BNPlanks;
import paulevs.betternether.blocks.BNPlate;
import paulevs.betternether.blocks.BNSlab;
import paulevs.betternether.blocks.BNStairs;
import paulevs.betternether.blocks.BNTrapdoor;
import paulevs.betternether.registry.BlocksRegistry;

public class WoodenMaterial
{
	public final Block log;
	public final Block bark;

	public final Block log_striped;
	public final Block bark_striped;

	public final Block planks;

	public final Block planks_stairs;
	public final Block planks_slab;

	public final Block fence;
	public final Block gate;
	public final Block button;
	public final Block pressure_plate;
	public final Block trapdoor;
	public final Block door;
	
	public WoodenMaterial(String name, MaterialColor color)
	{
		log_striped = BlocksRegistry.registerBlock(name + "_log_striped", new BNPillar(MaterialColor.LIME_TERRACOTTA));
		bark_striped = BlocksRegistry.registerBlock(name + "_bark_striped", new BNPillar(MaterialColor.LIME_TERRACOTTA));
		
		log = BlocksRegistry.registerBlock(name + "_log", new BNLogStripable(MaterialColor.LIME_TERRACOTTA, log_striped));
		bark = BlocksRegistry.registerBlock(name + "_bark", new BNLogStripable(MaterialColor.LIME_TERRACOTTA, bark_striped));
		
		planks = BlocksRegistry.registerBlock(name + "_planks", new BNPlanks(MaterialColor.LIME_TERRACOTTA));
		planks_stairs = BlocksRegistry.registerBlock(name + "_stairs", new BNStairs(planks));
		planks_slab = BlocksRegistry.registerBlock(name + "_slab", new BNSlab(planks));
		fence = BlocksRegistry.registerBlock(name + "_fence", new BNFence(planks));
		gate = BlocksRegistry.registerBlock(name + "_gate", new BNGate(planks));
		button = BlocksRegistry.registerBlock(name + "_button", new BNButton(planks));
		pressure_plate = BlocksRegistry.registerBlock(name + "_plate", new BNPlate(ActivationRule.EVERYTHING, planks));
		trapdoor = BlocksRegistry.registerBlock(name + "_trapdoor", new BNTrapdoor(planks));
		door = BlocksRegistry.registerBlock(name + "_door", new BNDoor(planks));
	}
}