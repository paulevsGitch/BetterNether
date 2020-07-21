package paulevs.betternether.blocks;

import java.util.List;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.world.BlockView;
import paulevs.betternether.registry.BlockEntitiesRegistry;

public class BNChest extends ChestBlock
{
	public BNChest(Block source)
	{
		super(FabricBlockSettings.copyOf(source).nonOpaque(), () -> {
	         return BlockEntitiesRegistry.CHEST;
	      });
	}
	
	@Override
	public BlockEntity createBlockEntity(BlockView world)
	{
		return BlockEntitiesRegistry.CHEST.instantiate();
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		List<ItemStack> drop = super.getDroppedStacks(state, builder);
		drop.add(new ItemStack(this.asItem()));
		return drop;
	}
}
