package com.paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;

import com.paulevs.betternether.client.IRenderTypeable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.properties.DoubleBlockHalf;

public class BNDoor extends DoorBlock implements IRenderTypeable {
	public BNDoor(Block block) {
		super(AbstractBlock.Properties.create(Material.NETHER_WOOD).sound(block.getSoundType(block.getDefaultState())).notSolid());
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		if (state.get(HALF) == DoubleBlockHalf.LOWER)
			return Collections.singletonList(new ItemStack(this.asItem()));
		else
			return Collections.emptyList();
	}

	@Override
	public BNRenderLayer getRenderLayer() {
		return BNRenderLayer.CUTOUT;
	}
}
