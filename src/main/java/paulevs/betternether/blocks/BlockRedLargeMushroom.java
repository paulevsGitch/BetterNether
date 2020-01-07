package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.EntityContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.blocks.shapes.TripleShape;

public class BlockRedLargeMushroom extends BlockBaseNotFull
{
	private static final VoxelShape TOP_SHAPE = Block.createCuboidShape(0, 1, 0, 16, 16, 16);
	private static final VoxelShape MIDDLE_SHAPE = Block.createCuboidShape(5, 0, 5, 11, 16, 11);
	public static final EnumProperty<TripleShape> SHAPE = EnumProperty.of("shape", TripleShape.class);
	
	public BlockRedLargeMushroom()
	{
		super(FabricBlockSettings.copyOf(Materials.COMMON_WOOD)
				.materialColor(MaterialColor.RED)
				.nonOpaque()
				.build());
		this.setDropItself(false);
		this.setRenderLayer(BNRenderLayer.CUTOUT);
	}
	
	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
        stateManager.add(SHAPE);
    }
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
	{
		return state.get(SHAPE) == TripleShape.TOP ? TOP_SHAPE : MIDDLE_SHAPE;
	}
}
