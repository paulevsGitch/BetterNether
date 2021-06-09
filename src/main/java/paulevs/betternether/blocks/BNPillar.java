package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import paulevs.betternether.blocks.materials.Materials;

public class BNPillar extends PillarBlock {
	public BNPillar(Settings settings) {
		super(settings);
	}

	public BNPillar(Block block) {
		super(FabricBlockSettings.copyOf(block));
	}

	public BNPillar(MapColor color) {
		super(Materials.makeWood(color));
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		return Collections.singletonList(new ItemStack(this));
	}
}
