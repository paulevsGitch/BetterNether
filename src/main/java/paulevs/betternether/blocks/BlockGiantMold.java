package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.registry.NetherBlocks;

public class BlockGiantMold extends BlockBaseNotFull {
	private static final VoxelShape TOP_SHAPE = Block.box(2, 2, 2, 14, 14, 14);
	private static final VoxelShape MIDDLE_SHAPE = Block.box(5, 0, 5, 11, 16, 11);
	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;

	public BlockGiantMold() {
		super(Materials.makeWood(MaterialColor.COLOR_GRAY).noOcclusion());
		this.setDropItself(false);
		this.setRenderLayer(BNRenderLayer.CUTOUT);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
		stateManager.add(SHAPE);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return state.getValue(SHAPE) == TripleShape.TOP ? TOP_SHAPE : MIDDLE_SHAPE;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
		TripleShape shape = state.getValue(SHAPE);
		return shape == TripleShape.TOP ? new ItemStack(NetherBlocks.GIANT_MOLD_SAPLING) : new ItemStack(NetherBlocks.MAT_NETHER_MUSHROOM.getStem());
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		switch (state.getValue(SHAPE)) {
			case BOTTOM:
				return state;
			case MIDDLE:
			case TOP:
			default:
				return world.getBlockState(pos.below()).getBlock() == this ? state : Blocks.AIR.defaultBlockState();
		}
	}
}
