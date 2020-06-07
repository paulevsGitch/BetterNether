package paulevs.betternether.blocks;

import java.util.Random;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.plants.StructureStalagnate;

public class BlockStalagnateSeed extends BlockBaseNotFull implements Fertilizable
{
	protected static final VoxelShape SHAPE_TOP = Block.createCuboidShape(4, 6, 4, 12, 16, 12);
	protected static final VoxelShape SHAPE_BOTTOM = Block.createCuboidShape(4, 0, 4, 12, 12, 12);
	private static final StructureStalagnate STRUCTURE = new StructureStalagnate();
	public static final BooleanProperty TOP = BooleanProperty.of("top");
	
	public BlockStalagnateSeed()
	{
		super(FabricBlockSettings.of(Material.PLANT)
				.materialColor(MaterialColor.CYAN)
				.sounds(BlockSoundGroup.CROP)
				.nonOpaque()
				.breakInstantly()
				.noCollision()
				.ticksRandomly());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDefaultState(getStateManager().getDefaultState().with(TOP, true));
	}
	
	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
        stateManager.add(TOP);
    }

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		BlockState blockState = this.getDefaultState();
		if (ctx.getSide() == Direction.DOWN)
			return blockState;
		else if (ctx.getSide() == Direction.UP)
			return blockState.with(TOP, false);
		else
			return null;
	}
	
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		return state.get(TOP).booleanValue() ? SHAPE_TOP : SHAPE_BOTTOM;
	}

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient)
	{
		return true;
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state)
	{
		if (random.nextInt(16) == 0)
		{
			if (state.get(TOP).booleanValue())
				return BlocksHelper.downRay(world, pos, StructureStalagnate.MIN_LENGTH) > 0;
			else
				return BlocksHelper.upRay(world, pos, StructureStalagnate.MIN_LENGTH) > 0;
		}
		return false;
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state)
	{
		if (state.get(TOP).booleanValue())
			STRUCTURE.generateDown(world, pos, random);
		else
			STRUCTURE.generate(world, pos, random);
	}
	
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
	{
		return BlocksHelper.isNetherrack(world.getBlockState(pos.up())) || BlocksHelper.isNetherrack(world.getBlockState(pos.down()));
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos)
	{
		if (state.get(TOP).booleanValue())
		{
			if (BlocksHelper.isNetherrack(world.getBlockState(pos.up())))
				return state;
			else
				return Blocks.AIR.getDefaultState();
		}
		else
		{
			if (BlocksHelper.isNetherrack(world.getBlockState(pos.down())))
				return state;
			else
				return Blocks.AIR.getDefaultState();
		}
	}
	
	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
	{
		super.scheduledTick(state, world, pos, random);
		if (canGrow(world, random, pos, state))
		{
			grow(world, random, pos, state);
		}
	}
}
