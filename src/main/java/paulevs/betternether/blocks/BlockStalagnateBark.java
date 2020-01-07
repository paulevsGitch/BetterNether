package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import paulevs.betternether.blocks.materials.Materials;

public class BlockStalagnateBark extends PillarBlock
{
	public BlockStalagnateBark()
	{
		super(FabricBlockSettings.copyOf(Materials.COMMON_WOOD)
				.materialColor(MaterialColor.LIME_TERRACOTTA)
				.build());
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		return Collections.singletonList(new ItemStack(this));
	}
}