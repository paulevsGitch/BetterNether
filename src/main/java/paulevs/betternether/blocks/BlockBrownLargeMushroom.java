package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.EntityContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import paulevs.betternether.blocks.materials.Materials;

public class BlockBrownLargeMushroom extends BlockBaseNotFull
{
	private static final VoxelShape TOP_CENTER_SHAPE = Block.createCuboidShape(0, 0.1, 0, 16, 16, 16);
	private static final VoxelShape TOP_EDGE_SHAPE = Block.createCuboidShape(0, 8, 0, 16, 16, 16);
	private static final VoxelShape MIDDLE_SHAPE = Block.createCuboidShape(4, 0, 4, 12, 16, 12);
	public static final EnumProperty<BrownMushroomShape> SHAPE = EnumProperty.of("shape", BrownMushroomShape.class);
	
	public BlockBrownLargeMushroom()
	{
		super(FabricBlockSettings.copyOf(Materials.COMMON_WOOD)
				.materialColor(MaterialColor.BROWN)
				.nonOpaque()
				.build());
	}
	
	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
        stateManager.add(SHAPE);
    }
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
	{
		BrownMushroomShape shape = state.get(SHAPE);
		if (shape == BrownMushroomShape.TOP)
			return TOP_CENTER_SHAPE;
		else if (shape == BrownMushroomShape.MIDDLE || shape == BrownMushroomShape.BOTTOM)
			return MIDDLE_SHAPE;
		else
			return TOP_EDGE_SHAPE;
	}
	
	public static enum BrownMushroomShape implements StringIdentifiable
	{
		TOP,
		SIDE_N,
		SIDE_S,
		SIDE_E,
		SIDE_W,
		CORNER_N,
		CORNER_S,
		CORNER_E,
		CORNER_W,
		MIDDLE,
		BOTTOM;
		
		@Override
		public String asString()
		{
			return this.toString().toLowerCase();
		}
	}
}
