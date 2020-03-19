package paulevs.betternether.blocks;

import java.util.function.ToIntFunction;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.EntityContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import paulevs.betternether.blocks.materials.Materials;

public class BlockWillowBranch extends BlockBaseNotFull
{
	private static final VoxelShape V_SHAPE = Block.createCuboidShape(4, 0, 4, 12, 16, 12);
	public static final EnumProperty<WillowBranchShape> SHAPE = EnumProperty.of("shape", WillowBranchShape.class);
	
	public BlockWillowBranch()
	{
		super(Materials.makeWood(MaterialColor.RED_TERRACOTTA).nonOpaque().noCollision().build().lightLevel(getLuminance()));
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDropItself(false);
		this.setDefaultState(getStateManager().getDefaultState().with(SHAPE, WillowBranchShape.MIDDLE));
	}
	
	protected static ToIntFunction<BlockState> getLuminance()
	{
		return (state) -> {
			return state.get(SHAPE) == WillowBranchShape.END ? 15 : 0;
		};
	}
	
	@Environment(EnvType.CLIENT)
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView view, BlockPos pos)
	{
		return 1.0F;
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
		stateManager.add(SHAPE);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
	{
		return V_SHAPE;
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos)
	{
		if (world.isAir(pos.up()))
			return Blocks.AIR.getDefaultState();
		else
			return state;
	}
	
	public enum WillowBranchShape implements StringIdentifiable
	{
		END,
		MIDDLE;

		@Override
		public String asString()
		{
			return this.toString().toLowerCase();
		}
	}
}
