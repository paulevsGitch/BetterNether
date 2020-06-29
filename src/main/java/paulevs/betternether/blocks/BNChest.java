package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import paulevs.betternether.registry.BlockEntitiesRegistry;

public class BNChest extends ChestBlock
{
	public BNChest(Block source)
	{
		super(FabricBlockSettings.copyOf(source), () -> {
	         return BlockEntitiesRegistry.CHEST;
	      });
	}
	
	@Override
	public BlockEntity createBlockEntity(BlockView world)
	{
		return BlockEntitiesRegistry.CHEST.instantiate();
	}
}
