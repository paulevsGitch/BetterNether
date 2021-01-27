package com.paulevs.betternether.blocks;

import com.paulevs.betternether.blocks.materials.MaterialBuilder;
import net.minecraft.block.material.MaterialColor;

public class BlockFarmland extends BlockBase {
	public BlockFarmland() {
		super(MaterialBuilder.makeWood(MaterialColor.LIME_TERRACOTTA));
	}
}