package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.sound.BlockSoundGroup;

public class BlockStalagnateBark extends PillarBlock
{
	public BlockStalagnateBark()
	{
		super(FabricBlockSettings.of(Material.WOOD)
				.materialColor(MaterialColor.LIME_TERRACOTTA)
				.sounds(BlockSoundGroup.WOOD)
				.hardness(2F)
				.build());
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		return Collections.singletonList(new ItemStack(this));
	}
}