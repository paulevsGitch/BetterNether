package paulevs.betternether.blocks.complex;

import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import paulevs.betternether.blocks.BNLogStripable;
import paulevs.betternether.blocks.BNPillar;
import paulevs.betternether.blocks.BNPlanks;
import paulevs.betternether.recipes.RecipesHelper;
import paulevs.betternether.registry.BlocksRegistry;

public class WoodenMaterial {
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

	public final Block taburet;
	public final Block chair;
	public final Block bar_stool;

	public final Block crafting_table;
	public final Block ladder;
	public final Block sign;

	public final Block chest;
	public final Block barrel;

	public WoodenMaterial(String name, MapColor woodColor, MapColor planksColor) {
		log_striped = BlocksRegistry.registerBlock("striped_log_" + name, new BNPillar(woodColor));
		bark_striped = BlocksRegistry.registerBlock("striped_bark_" + name, new BNPillar(woodColor));

		log = BlocksRegistry.registerBlock(name + "_log", new BNLogStripable(woodColor, log_striped));
		bark = BlocksRegistry.registerBark(name + "_bark", new BNLogStripable(woodColor, bark_striped), log);

		planks = BlocksRegistry.registerPlanks(name + "_planks", new BNPlanks(planksColor), log_striped, bark_striped, log, bark);
		planks_stairs = BlocksRegistry.registerStairs(name + "_stairs", planks);
		planks_slab = BlocksRegistry.registerSlab(name + "_slab", planks);

		fence = BlocksRegistry.registerFence(name + "_fence", planks);
		gate = BlocksRegistry.registerGate(name + "_gate", planks);
		button = BlocksRegistry.registerButton(name + "_button", planks);
		pressure_plate = BlocksRegistry.registerPlate(name + "_plate", planks);
		trapdoor = BlocksRegistry.registerTrapdoor(name + "_trapdoor", planks);
		door = BlocksRegistry.registerDoor(name + "_door", planks);

		taburet = BlocksRegistry.registerTaburet("taburet_" + name, planks_slab);
		chair = BlocksRegistry.registerChair("chair_" + name, planks_slab);
		bar_stool = BlocksRegistry.registerBarStool("bar_stool_" + name, planks_slab);

		crafting_table = BlocksRegistry.registerCraftingTable("crafting_table_" + name, planks);
		ladder = BlocksRegistry.registerLadder(name + "_ladder", planks);
		sign = BlocksRegistry.registerSign("sign_" + name, planks);

		chest = BlocksRegistry.registerChest("chest_" + name, planks);
		barrel = BlocksRegistry.registerBarrel("barrel_" + name, planks, planks_slab);

		RecipesHelper.makeSimpleRecipe2(log_striped, bark_striped, 3, "nether_bark_striped");
	}

	public boolean isTreeLog(Block block) {
		return block == log || block == bark;
	}
}