package paulevs.betternether.items;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import paulevs.betternether.registry.ItemsRegistry;

public class BNItemSign extends BlockItem
{
	public BNItemSign(Block block)
	{
		super(block, ItemsRegistry.defaultSettings());
	}

	protected boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state)
	{
		boolean bl = super.postPlacement(pos, world, player, stack, state);
		if (!world.isClient && !bl && player != null)
		{
			player.openEditSignScreen((SignBlockEntity)world.getBlockEntity(pos));
		}

		return bl;
	}
}