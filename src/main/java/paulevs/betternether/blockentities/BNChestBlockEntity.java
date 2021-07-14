package paulevs.betternether.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.registry.BlockEntitiesRegistry;

public class BNChestBlockEntity extends ChestBlockEntity {
	public BNChestBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntitiesRegistry.CHEST, pos, state);
	}
}
