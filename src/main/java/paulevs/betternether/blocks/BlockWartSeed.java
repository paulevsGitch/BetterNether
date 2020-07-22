package paulevs.betternether.blocks;

import java.util.EnumMap;
import java.util.Random;

import com.google.common.collect.Maps;

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
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.plants.StructureWartTree;

public class BlockWartSeed extends BlockBaseNotFull implements Fertilizable
{
	private static final EnumMap<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(Direction.class);
	private static final StructureWartTree STRUCTURE = new StructureWartTree();
	public static final DirectionProperty FACING = Properties.FACING;
	
	static
	{
		BOUNDING_SHAPES.put(Direction.UP, VoxelShapes.cuboid(0.25, 0.0, 0.25, 0.75, 0.5, 0.75));
		BOUNDING_SHAPES.put(Direction.DOWN, VoxelShapes.cuboid(0.25, 0.5, 0.25, 0.75, 1.0, 0.75));
		BOUNDING_SHAPES.put(Direction.NORTH, VoxelShapes.cuboid(0.25, 0.25, 0.5, 0.75, 0.75, 1.0));
		BOUNDING_SHAPES.put(Direction.SOUTH, VoxelShapes.cuboid(0.25, 0.25, 0.0, 0.75, 0.75, 0.5));
		BOUNDING_SHAPES.put(Direction.WEST, VoxelShapes.cuboid(0.5, 0.25, 0.25, 1.0, 0.75, 0.75));
		BOUNDING_SHAPES.put(Direction.EAST, VoxelShapes.cuboid(0.0, 0.25, 0.25, 0.5, 0.75, 0.75));
	}
	
	public BlockWartSeed()
	{
		super(FabricBlockSettings.of(Material.WOOD)
				.materialColor(MaterialColor.RED_TERRACOTTA)
				.sounds(BlockSoundGroup.WART_BLOCK)
				.hardness(1F)
				.nonOpaque()
				.noCollision());
		this.setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.UP));
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
		stateManager.add(FACING);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		return BOUNDING_SHAPES.get(state.get(FACING));
	}
	
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		BlockState blockState = this.getDefaultState();
		WorldView worldView = ctx.getWorld();
		BlockPos blockPos = ctx.getBlockPos();
		Direction[] directions = ctx.getPlacementDirections();
		for(int i = 0; i < directions.length; ++i) 
		{
			Direction direction = directions[i];
			Direction direction2 = direction.getOpposite();
			blockState = (BlockState) blockState.with(FACING, direction2);
			if (blockState.canPlaceAt(worldView, blockPos))
			{
				return blockState;
			}
		}
		return null;
	}
	
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
	{
		Direction direction = (Direction) state.get(FACING);
		BlockPos blockPos = pos.offset(direction.getOpposite());
		return sideCoversSmallSquare(world, blockPos, direction) || world.getBlockState(pos.down()).getBlock() == Blocks.SOUL_SAND;
	}

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient)
	{
		Direction direction = (Direction) state.get(FACING);
		return direction == Direction.UP && BlocksHelper.isSoulSand(world.getBlockState(pos.down()));
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state)
	{
		return random.nextInt(8) == 0;
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state)
	{
		STRUCTURE.grow(world, pos, random);
	}
	
	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation)
	{
		return BlocksHelper.rotateHorizontal(state, rotation, FACING);
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror)
	{
		return BlocksHelper.mirrorHorizontal(state, mirror, FACING);
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos)
	{
		if (!canPlaceAt(state, world, pos))
			return Blocks.AIR.getDefaultState();
		else
			return state;
	}
}