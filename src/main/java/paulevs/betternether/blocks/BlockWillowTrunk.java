package paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.blocks.shapes.TripleShape;

public class BlockWillowTrunk extends BlockBaseNotFull
{
	public static final EnumProperty<TripleShape> SHAPE = EnumProperty.of("shape", TripleShape.class);
	private static final VoxelShape SHAPE_BOTTOM = Block.createCuboidShape(4, 0, 4, 12, 16, 12);
	private static final VoxelShape SHAPE_TOP = Block.createCuboidShape(4, 0, 4, 12, 12, 12);
	
	public BlockWillowTrunk()
	{
		super(Materials.makeWood(MaterialColor.RED_TERRACOTTA).nonOpaque().build());
		this.setDropItself(false);
		this.setDefaultState(getStateManager().getDefaultState().with(SHAPE, TripleShape.TOP));
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
		stateManager.add(SHAPE);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		return state.get(SHAPE) == TripleShape.TOP ? SHAPE_TOP : SHAPE_BOTTOM;
	}
}
