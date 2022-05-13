package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.registry.NetherBlocks;

public class BlockStalagnate extends BlockBaseNotFull {
	private static final VoxelShape SELECT_SHAPE = Block.box(4, 0, 4, 12, 16, 12);
	private static final VoxelShape COLLISION_SHAPE = Block.box(5, 0, 5, 11, 16, 11);
	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;

	public BlockStalagnate() {
		super(Materials.makeWood(MaterialColor.TERRACOTTA_LIGHT_GREEN).noOcclusion());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.registerDefaultState(getStateDefinition().any().setValue(SHAPE, TripleShape.MIDDLE));
		this.setDropItself(false);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return SELECT_SHAPE;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return COLLISION_SHAPE;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
		stateManager.add(SHAPE);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
		return new ItemStack(NetherBlocks.MAT_STALAGNATE.getStem());
	}
}
