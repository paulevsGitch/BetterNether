package paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import paulevs.betternether.blocks.materials.Materials;

public class BlockMushroomFir extends BlockBaseNotFull
{
	public static final EnumProperty<MushroomFirShape> SHAPE = EnumProperty.of("shape", MushroomFirShape.class);
	
	private static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(4, 0, 4, 12, 16, 12);
	private static final VoxelShape MIDDLE_SHAPE = Block.createCuboidShape(5, 0, 5, 11, 16, 11);
	private static final VoxelShape TOP_SHAPE = Block.createCuboidShape(6, 0, 6, 10, 16, 10);
	private static final VoxelShape SIDE_BIG_SHAPE = Block.createCuboidShape(0.01, 0.01, 0.01, 15.99, 13, 15.99);
	private static final VoxelShape SIDE_SMALL_N_SHAPE = Block.createCuboidShape(4, 1, 0, 12, 8, 8);
	private static final VoxelShape SIDE_SMALL_S_SHAPE = Block.createCuboidShape(4, 1, 8, 12, 8, 16);
	private static final VoxelShape SIDE_SMALL_E_SHAPE = Block.createCuboidShape(8, 1, 4, 16, 8, 12);
	private static final VoxelShape SIDE_SMALL_W_SHAPE= Block.createCuboidShape(0, 1, 4, 8, 8, 12);
	private static final VoxelShape END_SHAPE = Block.createCuboidShape(0.01, 0, 0.01, 15.99, 15.99, 15.99);
	
	public BlockMushroomFir()
	{
		super(Materials.makeWood(MaterialColor.CYAN).nonOpaque().build());
		this.setDropItself(false);
		this.setRenderLayer(BNRenderLayer.CUTOUT);
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
		stateManager.add(SHAPE);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		switch (state.get(SHAPE))
		{
		case BOTTOM:
			return BOTTOM_SHAPE;
		case END:
			return END_SHAPE;
		case MIDDLE:
		default:
			return MIDDLE_SHAPE;
		case SIDE_BIG_E:
		case SIDE_BIG_N:
		case SIDE_BIG_S:
		case SIDE_BIG_W:
			return SIDE_BIG_SHAPE;
		case SIDE_SMALL_E:
			return SIDE_SMALL_E_SHAPE;
		case SIDE_SMALL_N:
			return SIDE_SMALL_N_SHAPE;
		case SIDE_SMALL_S:
			return SIDE_SMALL_S_SHAPE;
		case SIDE_SMALL_W:
			return SIDE_SMALL_W_SHAPE;
		case TOP:
			return TOP_SHAPE;
		}
	}
	
	public enum MushroomFirShape implements StringIdentifiable
	{
		BOTTOM,
		MIDDLE,
		TOP,
		SIDE_BIG_N,
		SIDE_BIG_S,
		SIDE_BIG_E,
		SIDE_BIG_W,
		SIDE_SMALL_N,
		SIDE_SMALL_S,
		SIDE_SMALL_E,
		SIDE_SMALL_W,
		END;
		
		@Override
		public String asString()
		{
			return this.toString().toLowerCase();
		}
	}
}