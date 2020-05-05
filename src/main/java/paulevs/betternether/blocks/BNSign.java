package paulevs.betternether.blocks;

import javax.annotation.Nullable;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.SignType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import paulevs.betternether.blockentities.NetherSignBlockEntity;

public class BNSign extends AbstractSignBlock
{
	public static final IntProperty ROTATION = Properties.ROTATION;
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	public static final BooleanProperty IS_FLOOR = BooleanProperty.of("is_floor");
	protected static final VoxelShape SHAPE_FLOOR = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
	
	public BNSign(Block block)
	{
		super(FabricBlockSettings.copy(block).nonOpaque(), SignType.OAK);
		this.setDefaultState(getStateManager().getDefaultState().with(ROTATION, 0).with(WATERLOGGED, false).with(IS_FLOOR, true));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		builder.add(ROTATION, WATERLOGGED, IS_FLOOR);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
	{
		return SHAPE_FLOOR;
	}

	@Override
	public BlockEntity createBlockEntity(BlockView view)
	{
		return new NetherSignBlockEntity();
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos)
	{
		if ((Boolean) state.get(WATERLOGGED))
		{
			world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
	
		return super.getStateForNeighborUpdate(state, facing, neighborState, world, pos, neighborPos);
	}
	
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		if (ctx.getSide() == Direction.UP)
		{
			FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
			return this.getDefaultState()
					.with(IS_FLOOR, true)
					.with(ROTATION, MathHelper.floor((180.0 + ctx.getPlayerYaw() * 16.0 / 360.0) + 0.5 - 12) & 15)
					.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
		}
		else if (ctx.getSide() != Direction.DOWN)
		{
			BlockState blockState = this.getDefaultState();
			FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
			WorldView worldView = ctx.getWorld();
			BlockPos blockPos = ctx.getBlockPos();
			Direction[] directions = ctx.getPlacementDirections();
			Direction[] var7 = directions;
			int var8 = directions.length;

			for (int var9 = 0; var9 < var8; ++var9)
			{
				Direction direction = var7[var9];
				if (direction.getAxis().isHorizontal())
				{
					Direction direction2 = direction.getOpposite();
					int rot = MathHelper.floor((180.0 + direction2.asRotation() * 16.0 / 360.0) + 0.5 - 4) & 15;
					blockState = blockState.with(ROTATION, rot);
					if (blockState.canPlaceAt(worldView, blockPos))
					{
						return blockState.with(IS_FLOOR, false).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
					}
				}
			}
		}
		
		return null;
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation)
	{
		return state.with(ROTATION, rotation.rotate(state.get(ROTATION), 16));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror)
	{
		return state.with(ROTATION, mirror.mirror(state.get(ROTATION), 16));
	}

	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public FluidState getFluidState(BlockState state)
	{
		return (Boolean) state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack)
	{
		if (!world.isClient() && placer != null && placer instanceof PlayerEntity)
		{
			//ServerPlayerEntity player = (ServerPlayerEntity) placer;
			//player.openEditSignScreen((NetherSignBlockEntity) world.getBlockEntity(pos));
			/*SignBlockEntity sign = (SignBlockEntity) world.getBlockEntity(pos);
			sign.setEditable(true);
			sign.setEditor(player);
			player.networkHandler.sendPacket(new SignEditorOpenS2CPacket(pos));*/
		}
	}

	@Override
	public Fluid tryDrainFluid(IWorld world, BlockPos pos, BlockState state)
	{
		// TODO Auto-generated method stub
		//return state.get(WATERLOGGED) ? Fluids.EMPTY : Fluids.WATER;
		return null;
	}

	@Override
	public boolean canFillWithFluid(BlockView view, BlockPos pos, BlockState state, Fluid fluid)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tryFillWithFluid(IWorld world, BlockPos pos, BlockState state, FluidState fluidState)
	{
		// TODO Auto-generated method stub
		return false;
	}
}
