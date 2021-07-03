package paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.math.BlockPos;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.BlockEntitiesRegistry;

import java.util.List;

public class BNChest extends ChestBlock {
	public BNChest(Block source) {
		super(BlocksHelper.copySettingsOf(source).nonOpaque(), () -> {
			return BlockEntitiesRegistry.CHEST;
		});
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return BlockEntitiesRegistry.CHEST.instantiate(pos, state);
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		List<ItemStack> drop = super.getDroppedStacks(state, builder);
		drop.add(new ItemStack(this.asItem()));
		return drop;
	}
}
