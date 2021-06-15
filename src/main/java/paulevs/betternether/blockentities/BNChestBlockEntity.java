package paulevs.betternether.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.math.BlockPos;
import paulevs.betternether.registry.BlockEntitiesRegistry;

public class BNChestBlockEntity extends ChestBlockEntity {
	public BNChestBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntitiesRegistry.CHEST, pos, state);
	}
}
