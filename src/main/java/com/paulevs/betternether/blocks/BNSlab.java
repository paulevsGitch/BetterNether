package com.paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.properties.SlabType;

public class BNSlab extends SlabBlock {
	public BNSlab(Block block) {
		super(AbstractBlock.Properties.from(block));
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		return Collections.singletonList(new ItemStack(this.asItem(), state.get(TYPE) == SlabType.DOUBLE ? 2 : 1));
	}
}