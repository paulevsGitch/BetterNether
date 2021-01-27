package com.paulevs.betternether.blocks.complex;

import com.paulevs.betternether.blocks.BNLogStripable;
import com.paulevs.betternether.blocks.BNPillar;
import com.paulevs.betternether.blocks.BNPlanks;
import com.paulevs.betternether.registry.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialColor;


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

	public WoodenMaterial(String name, MaterialColor woodColor, MaterialColor planksColor) {
		log_striped = RegistryHandler.registerBlock("striped_log_" + name, new BNPillar(woodColor));
		bark_striped = RegistryHandler.registerBlock("striped_bark_" + name, new BNPillar(woodColor));

		log = RegistryHandler.registerBlock(name + "_log", new BNLogStripable(woodColor, log_striped));
		bark = RegistryHandler.registerBark(name + "_bark", new BNLogStripable(woodColor, bark_striped), log);

		planks = RegistryHandler.registerPlanks(name + "_planks", new BNPlanks(planksColor), log_striped, bark_striped, log, bark);
		planks_stairs = RegistryHandler.registerStairs(name + "_stairs", planks);
		planks_slab = RegistryHandler.registerSlab(name + "_slab", planks);

		fence = RegistryHandler.registerFence(name + "_fence", planks);
		gate = RegistryHandler.registerGate(name + "_gate", planks);
		button = RegistryHandler.registerButton(name + "_button", planks);
		pressure_plate = RegistryHandler.registerPlate(name + "_plate", planks);
		trapdoor = RegistryHandler.registerTrapdoor(name + "_trapdoor", planks);
		door = RegistryHandler.registerDoor(name + "_door", planks);

		taburet = RegistryHandler.registerTaburet("taburet_" + name, planks_slab);
		chair = RegistryHandler.registerChair("chair_" + name, planks_slab);
		bar_stool = RegistryHandler.registerBarStool("bar_stool_" + name, planks_slab);

		crafting_table = RegistryHandler.registerCraftingTable("crafting_table_" + name, planks);
		ladder = RegistryHandler.registerLadder(name + "_ladder", planks);
		sign = RegistryHandler.registerSign("sign_" + name, planks);

		chest = RegistryHandler.registerChest("chest_" + name, planks);
		barrel = RegistryHandler.registerBarrel("barrel_" + name, planks, planks_slab);
	}

	public boolean isTreeLog(Block block) {
		return block == log || block == bark;
	}
}
