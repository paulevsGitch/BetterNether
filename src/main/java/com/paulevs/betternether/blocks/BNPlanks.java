package com.paulevs.betternether.blocks;

import com.paulevs.betternether.blocks.materials.MaterialBuilder;
import net.minecraft.block.material.MaterialColor;

public class BNPlanks extends BlockBase {
	public BNPlanks(MaterialColor color) {
		super(MaterialBuilder.makeWood(color));
	}
}
