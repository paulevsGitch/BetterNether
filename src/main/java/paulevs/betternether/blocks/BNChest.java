package paulevs.betternether.blocks;

import java.util.List;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import paulevs.betternether.registry.BlockEntitiesRegistry;

public class BNChest extends ChestBlock {
	public BNChest(Block source) {
		super(FabricBlockSettings.copyOf(source).nonOpaque(), () -> {
			return BlockEntitiesRegistry.CHEST;
		});
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return BlockEntitiesRegistry.CHEST.create(pos, state);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		List<ItemStack> drop = super.getDrops(state, builder);
		drop.add(new ItemStack(this.asItem()));
		return drop;
	}
}
