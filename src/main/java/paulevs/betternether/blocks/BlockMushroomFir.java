package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.registry.BlocksRegistry;

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
		super(Materials.makeWood(MaterialColor.CYAN).nonOpaque());
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
		BOTTOM("bottom"),
		MIDDLE("middle"),
		TOP("top"),
		SIDE_BIG_N("side_big_n"),
		SIDE_BIG_S("side_big_s"),
		SIDE_BIG_E("side_big_e"),
		SIDE_BIG_W("side_big_w"),
		SIDE_SMALL_N("side_small_n"),
		SIDE_SMALL_S("side_small_s"),
		SIDE_SMALL_E("side_small_e"),
		SIDE_SMALL_W("side_small_w"),
		END("end");
		
		final String name;

		MushroomFirShape(String name)
		{
			this.name = name;
		}

		@Override
		public String asString()
		{
			return name;
		}

		@Override
		public String toString()
		{
			return name;
		}
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state)
	{
		MushroomFirShape shape = state.get(SHAPE);
		return shape == MushroomFirShape.BOTTOM || shape == MushroomFirShape.MIDDLE ? new ItemStack(BlocksRegistry.MUSHROOM_FIR_STEM) : new ItemStack(BlocksRegistry.MUSHROOM_FIR_SAPLING);
	}
	
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
	{
		MushroomFirShape shape = state.get(SHAPE);
		if (shape == MushroomFirShape.SIDE_BIG_N || shape == MushroomFirShape.SIDE_SMALL_N)
			return world.getBlockState(pos.north()).getBlock() == this;
		else if (shape == MushroomFirShape.SIDE_BIG_S || shape == MushroomFirShape.SIDE_SMALL_S)
			return world.getBlockState(pos.south()).getBlock() == this;
		else if (shape == MushroomFirShape.SIDE_BIG_E || shape == MushroomFirShape.SIDE_SMALL_E)
			return world.getBlockState(pos.east()).getBlock() == this;
		else if (shape == MushroomFirShape.SIDE_BIG_W || shape == MushroomFirShape.SIDE_SMALL_W)
			return world.getBlockState(pos.west()).getBlock() == this;
		BlockState down = world.getBlockState(pos.down());
		return down.getBlock() == this || down.isSideSolidFullSquare(world, pos.down(), Direction.UP);
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos)
	{
		return canPlaceAt(state, world, pos) ? state : Blocks.AIR.getDefaultState();
	}
}