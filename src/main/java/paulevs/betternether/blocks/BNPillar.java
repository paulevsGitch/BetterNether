package paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.materials.Materials;

import java.util.Collections;
import java.util.List;

public class BNPillar extends PillarBlock {
	public BNPillar(Settings settings) {
		super(settings);
	}

	public BNPillar(Block block) {
		super(BlocksHelper.copySettingsOf(block));
	}

	public BNPillar(MapColor color) {
		super(Materials.makeWood(color));
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		return Collections.singletonList(new ItemStack(this));
	}
}
