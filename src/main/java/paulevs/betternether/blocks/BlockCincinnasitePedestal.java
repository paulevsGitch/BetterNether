package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.registry.NetherBlocks;

public class BlockCincinnasitePedestal extends BlockBaseNotFull {
	private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 16, 14);

	public BlockCincinnasitePedestal() {
		super(FabricBlockSettings.copy(NetherBlocks.CINCINNASITE_BLOCK).noOcclusion());
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return SHAPE;
	}
}
