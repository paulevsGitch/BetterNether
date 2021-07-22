package paulevs.betternether.blocks.complex;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.blocks.BNLogStripable;
import paulevs.betternether.blocks.BNPillar;
import paulevs.betternether.blocks.BNPlanks;
import paulevs.betternether.recipes.RecipesHelper;
import paulevs.betternether.registry.NetherBlocks;

public class WoodenMaterialOld {
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

	public WoodenMaterialOld(String name, MaterialColor woodColor, MaterialColor planksColor) {
		log_striped = NetherBlocks.registerBlock("striped_log_" + name, new BNPillar(woodColor));
		bark_striped = NetherBlocks.registerBlock("striped_bark_" + name, new BNPillar(woodColor));

		log = NetherBlocks.registerBlock(name + "_log", new BNLogStripable(woodColor, log_striped));
		bark = NetherBlocks.registerBark(name + "_bark", new BNLogStripable(woodColor, bark_striped), log);

		planks = NetherBlocks.registerPlanks(name + "_planks", new BNPlanks(planksColor), log_striped, bark_striped, log, bark);
		planks_stairs = NetherBlocks.registerStairs(name + "_stairs", planks);
		planks_slab = NetherBlocks.registerSlab(name + "_slab", planks);

		fence = NetherBlocks.registerFence(name + "_fence", planks);
		gate = NetherBlocks.registerGate(name + "_gate", planks);
		button = NetherBlocks.registerButton(name + "_button", planks);
		pressure_plate = NetherBlocks.registerPlate(name + "_plate", planks);
		trapdoor = NetherBlocks.registerTrapdoor(name + "_trapdoor", planks);
		door = NetherBlocks.registerDoor(name + "_door", planks);

		taburet = NetherBlocks.registerTaburet("taburet_" + name, planks_slab);
		chair = NetherBlocks.registerChair("chair_" + name, planks_slab);
		bar_stool = NetherBlocks.registerBarStool("bar_stool_" + name, planks_slab);

		crafting_table = NetherBlocks.registerCraftingTable("crafting_table_" + name, planks);
		ladder = NetherBlocks.registerLadder(name + "_ladder", planks);
		sign = NetherBlocks.registerSign("sign_" + name, planks);

		chest = NetherBlocks.registerChest("chest_" + name, planks);
		barrel = NetherBlocks.registerBarrel("barrel_" + name, planks, planks_slab);

		RecipesHelper.makeSimpleRecipe2(log_striped, bark_striped, 3, "nether_bark_striped");
	}

	public boolean isTreeLog(Block block) {
		return block == log || block == bark;
	}
}