package paulevs.betternether.blocks;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.plants.StructureSoulLily;

public class BlockSoulLily extends BlockBaseNotFull
{
	public static final EnumProperty<SoulLilyShape> SHAPE = EnumProperty.of("shape", SoulLilyShape.class);
	
	private static final VoxelShape SHAPE_SMALL = Block.createCuboidShape(6, 0, 6, 10, 16, 10);
	private static final VoxelShape SHAPE_MEDIUM_BOTTOM = Block.createCuboidShape(5, 0, 5, 11, 16, 11);
	private static final VoxelShape SHAPE_MEDIUM_TOP = Block.createCuboidShape(0, 0, 0, 16, 3, 16);
	private static final VoxelShape SHAPE_BIG_BOTTOM = Block.createCuboidShape(3, 0, 3, 13, 16, 13);
	private static final VoxelShape SHAPE_BIG_MIDDLE = Block.createCuboidShape(6, 0, 6, 10, 16, 10);
	private static final VoxelShape SHAPE_BIG_TOP_CENTER = Block.createCuboidShape(0, 0, 0, 16, 4, 16);
	private static final VoxelShape SHAPE_BIG_TOP_SIDE_N = Block.createCuboidShape(0, 4, 0, 16, 6, 8);
	private static final VoxelShape SHAPE_BIG_TOP_SIDE_S = Block.createCuboidShape(0, 4, 8, 16, 6, 16);
	private static final VoxelShape SHAPE_BIG_TOP_SIDE_E = Block.createCuboidShape(8, 4, 0, 16, 6, 16);
	private static final VoxelShape SHAPE_BIG_TOP_SIDE_W = Block.createCuboidShape(0, 4, 0, 8, 6, 16);
	
	private static final StructureSoulLily STRUCTURE = new StructureSoulLily();
	
	private static final SoulLilyShape[] ROT = new SoulLilyShape[] {
			SoulLilyShape.BIG_TOP_SIDE_N,
			SoulLilyShape.BIG_TOP_SIDE_E,
			SoulLilyShape.BIG_TOP_SIDE_S,
			SoulLilyShape.BIG_TOP_SIDE_W
			};
	
	public BlockSoulLily()
	{
		super(Materials.makeWood(MaterialColor.ORANGE).nonOpaque().ticksRandomly());
		this.setDefaultState(getStateManager().getDefaultState().with(SHAPE, SoulLilyShape.SMALL));
		this.setRenderLayer(BNRenderLayer.CUTOUT);
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
		stateManager.add(SHAPE);
	}
	
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		switch (state.get(SHAPE))
		{
		case BIG_BOTTOM:
			return SHAPE_BIG_BOTTOM;
		case BIG_MIDDLE:
			return SHAPE_BIG_MIDDLE;
		case BIG_TOP_CENTER:
			return SHAPE_BIG_TOP_CENTER;
		case MEDIUM_BOTTOM:
			return SHAPE_MEDIUM_BOTTOM;
		case MEDIUM_TOP:
			return SHAPE_MEDIUM_TOP;
		case BIG_TOP_SIDE_N:
			return SHAPE_BIG_TOP_SIDE_N;
		case BIG_TOP_SIDE_S:
			return SHAPE_BIG_TOP_SIDE_S;
		case BIG_TOP_SIDE_E:
			return SHAPE_BIG_TOP_SIDE_E;
		case BIG_TOP_SIDE_W:
			return SHAPE_BIG_TOP_SIDE_W;
		case SMALL:
		default:
			return SHAPE_SMALL;
		}
	}
	
	public enum SoulLilyShape implements StringIdentifiable
	{
		SMALL("small"),
		MEDIUM_BOTTOM("medium_bottom"),
		MEDIUM_TOP("medium_top"),
		BIG_BOTTOM("big_bottom"),
		BIG_MIDDLE("big_middle"),
		BIG_TOP_CENTER("big_top_center"),
		BIG_TOP_SIDE_N("big_top_side_n"),
		BIG_TOP_SIDE_S("big_top_side_s"),
		BIG_TOP_SIDE_E("big_top_side_e"),
		BIG_TOP_SIDE_W("big_top_side_w");
		
		final String name;

		SoulLilyShape(String name)
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
	
	public boolean canGrow(World world, BlockPos pos, Random random)
	{
		BlockState state = world.getBlockState(pos.down());
		if (state.getBlock() == this || state.getBlock() == Blocks.SOUL_SAND || BlocksHelper.isFertile(world.getBlockState(pos.down())))
		{
			return BlocksHelper.isFertile(world.getBlockState(pos.down())) ? (random.nextInt(8) == 0) : (random.nextInt(16) == 0);
		}
		return false;
	}
	
	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
	{
		super.scheduledTick(state, world, pos, random);
		if (canGrow(world, pos, random))
		{
			SoulLilyShape shape = state.get(SHAPE);
			if (shape == SoulLilyShape.SMALL && world.isAir(pos.up()))
			{
				STRUCTURE.growMedium(world, pos);
			}
			else if (shape == SoulLilyShape.MEDIUM_BOTTOM && world.isAir(pos.up(2)) && isAirSides(world, pos.up(2)))
			{
				STRUCTURE.growBig(world, pos);
			}
		}
	}
	
	private boolean isAirSides(World world, BlockPos pos)
	{
		return world.isAir(pos.north()) && world.isAir(pos.south()) && world.isAir(pos.east()) && world.isAir(pos.west());
	}
	
	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation)
	{
		SoulLilyShape shape = state.get(SHAPE);
		int index = getRotationIndex(shape);
		if (index < 0)
			return state;
		int offset = rotOffset(rotation);
		return state.with(SHAPE, ROT[(index + offset) & 3]);
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror)
	{
		SoulLilyShape shape = state.get(SHAPE);
		int index = getRotationIndex(shape);
		if (index < 0)
			return state;
		if (mirror == BlockMirror.FRONT_BACK)
		{
			if (shape == SoulLilyShape.BIG_TOP_SIDE_E)
				shape = SoulLilyShape.BIG_TOP_SIDE_W;
			else if (shape == SoulLilyShape.BIG_TOP_SIDE_W)
				shape = SoulLilyShape.BIG_TOP_SIDE_E;
		}
		else if (mirror == BlockMirror.LEFT_RIGHT)
		{
			if (shape == SoulLilyShape.BIG_TOP_SIDE_N)
				shape = SoulLilyShape.BIG_TOP_SIDE_S;
			else if (shape == SoulLilyShape.BIG_TOP_SIDE_S)
				shape = SoulLilyShape.BIG_TOP_SIDE_N;
		}
		return state.with(SHAPE, shape);
	}
	
	private int getRotationIndex(SoulLilyShape shape)
	{
		for (int i = 0; i < 4; i++)
		{
			if (shape == ROT[i])
				return i;
		}
		return -1;
	}
	
	private int rotOffset(BlockRotation rotation)
	{
		if (rotation == BlockRotation.NONE)
			return 0;
		else if (rotation == BlockRotation.CLOCKWISE_90)
			return 1;
		else if (rotation == BlockRotation.CLOCKWISE_180)
			return 2;
		else
			return 3;
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state)
	{
		return new ItemStack(BlocksRegistry.SOUL_LILY_SAPLING);
	}
	
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
	{
		SoulLilyShape shape = state.get(SHAPE);
		if (shape == SoulLilyShape.BIG_TOP_SIDE_N)
			return world.getBlockState(pos.north()).getBlock() == this;
		if (shape == SoulLilyShape.BIG_TOP_SIDE_S)
			return world.getBlockState(pos.south()).getBlock() == this;
		if (shape == SoulLilyShape.BIG_TOP_SIDE_E)
			return world.getBlockState(pos.east()).getBlock() == this;
		if (shape == SoulLilyShape.BIG_TOP_SIDE_W)
			return world.getBlockState(pos.west()).getBlock() == this;
		BlockState down = world.getBlockState(pos.down());
		return down.getBlock() == this || BlocksHelper.isSoulSand(down);
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos)
	{
		return canPlaceAt(state, world, pos) ? state : Blocks.AIR.getDefaultState();
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		switch (state.get(SHAPE))
		{
		case BIG_BOTTOM:
		case BIG_MIDDLE:
			return Lists.newArrayList(new ItemStack(BlocksRegistry.MUSHROOM_STEM));
		case BIG_TOP_CENTER:
			return Lists.newArrayList(new ItemStack(BlocksRegistry.MUSHROOM_STEM), new ItemStack(BlocksRegistry.SOUL_LILY_SAPLING));
		case MEDIUM_BOTTOM:
			return Lists.newArrayList(new ItemStack(BlocksRegistry.MUSHROOM_STEM));
		case BIG_TOP_SIDE_N:
		case BIG_TOP_SIDE_S:
		case BIG_TOP_SIDE_E:
		case BIG_TOP_SIDE_W:
			return Lists.newArrayList(new ItemStack(BlocksRegistry.MUSHROOM_STEM), new ItemStack(BlocksRegistry.SOUL_LILY_SAPLING, MHelper.randRange(0, 1, MHelper.RANDOM)));
		case SMALL:
		case MEDIUM_TOP:
		default:
			return Lists.newArrayList(new ItemStack(BlocksRegistry.MUSHROOM_STEM), new ItemStack(BlocksRegistry.SOUL_LILY_SAPLING));
		}
	}
}