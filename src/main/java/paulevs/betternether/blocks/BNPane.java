package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.math.Direction;
import paulevs.betternether.client.IRenderTypeable;

public class BNPane extends PaneBlock implements IRenderTypeable
{
	private boolean dropSelf;
	
	public BNPane(Block block, boolean dropSelf)
	{
		super(FabricBlockSettings.copy(block).strength(0.3F, 0.3F).nonOpaque());
		this.dropSelf = dropSelf;
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		if (dropSelf)
			return Collections.singletonList(new ItemStack(this.asItem()));
		else
			return super.getDroppedStacks(state, builder);
	}

	@Override
	public BNRenderLayer getRenderLayer()
	{
		return BNRenderLayer.TRANSLUCENT;
	}

	@Environment(EnvType.CLIENT)
	public boolean isSideInvisible(BlockState state, BlockState neighbor, Direction facing)
	{
		if (neighbor.getBlock() == this)
		{
			if (!facing.getAxis().isHorizontal())
			{
				return false;
			}

			if (state.get(FACING_PROPERTIES.get(facing)) && neighbor.get(FACING_PROPERTIES.get(facing.getOpposite())))
			{
				return true;
			}
		}

		return super.isSideInvisible(state, neighbor, facing);
	}
}
