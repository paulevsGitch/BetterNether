package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import paulevs.betternether.client.IRenderTypeable;

public class BNDoor extends DoorBlock implements IRenderTypeable
{
	public BNDoor(Block block)
	{
		super(FabricBlockSettings.copyOf(block).nonOpaque());
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		if (state.get(HALF) == DoubleBlockHalf.LOWER)
			return Collections.singletonList(new ItemStack(this.asItem()));
		else
			return Collections.emptyList();
	}

	@Override
	public BNRenderLayer getRenderLayer()
	{
		return BNRenderLayer.CUTOUT;
	}
}
