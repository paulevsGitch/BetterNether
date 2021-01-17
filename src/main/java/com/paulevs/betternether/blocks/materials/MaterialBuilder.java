package com.paulevs.betternether.blocks.materials;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;

public class MaterialBuilder {
	public static final Material COMMON_WOOD = new Material.Builder(MaterialColor.WOOD).build();
	public static final Material COMMON_GRASS = new Material.Builder(MaterialColor.FOLIAGE).doesNotBlockMovement().notSolid().replaceable().build();
	public static final Material COMMON_LEAVES = new Material.Builder(MaterialColor.FOLIAGE).notSolid().build();
	
	public static AbstractBlock.Properties makeWood(MaterialColor color) {
		return AbstractBlock.Properties.create(COMMON_WOOD, color)
				.sound(SoundType.WOOD)
				.harvestTool(ToolType.AXE)
				.hardnessAndResistance(1);
	}
	
	public static AbstractBlock.Properties makeGrass(MaterialColor color) {
		return AbstractBlock.Properties.create(COMMON_GRASS, color)
				.setAllowsSpawn((state, world, pos, type) -> {
					return true;
				})
				.sound(SoundType.PLANT)
				.doesNotBlockMovement()
				.notSolid()
				.zeroHardnessAndResistance();
	}
	
	public static AbstractBlock.Properties makeLeaves(MaterialColor color) {
		return AbstractBlock.Properties.create(COMMON_LEAVES, color)
				.harvestTool(ToolType.HOE)
				.sound(SoundType.PLANT)
				.notSolid()
				.hardnessAndResistance(0.2F)
				.setAllowsSpawn((state, world, pos, type) -> {
					return false;
				})
				.setSuffocates((state, worls, pos) -> {
					return false;
				})
				.setBlocksVision((state, worls, pos) -> {
					return false;
				});
	}
}
