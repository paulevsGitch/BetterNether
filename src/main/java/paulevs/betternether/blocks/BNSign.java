package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.network.packet.s2c.play.SignEditorOpenS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SignType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import paulevs.betternether.blockentities.BNSignBlockEntity;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class BNSign extends AbstractSignBlock {
	public static final IntProperty ROTATION = Properties.ROTATION;
	public static final BooleanProperty FLOOR = BooleanProperty.of("floor");
	private static final VoxelShape[] WALL_SHAPES = new VoxelShape[] {
			Block.createCuboidShape(0.0D, 4.5D, 14.0D, 16.0D, 12.5D, 16.0D),
			Block.createCuboidShape(0.0D, 4.5D, 0.0D, 2.0D, 12.5D, 16.0D),
			Block.createCuboidShape(0.0D, 4.5D, 0.0D, 16.0D, 12.5D, 2.0D),
			Block.createCuboidShape(14.0D, 4.5D, 0.0D, 16.0D, 12.5D, 16.0D)
	};
	private static final Direction[] ROT = new Direction[] {
		Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST
	};

	public BNSign(Block source) {
		super(FabricBlockSettings.copyOf(source).noCollision().nonOpaque().strength(1.0F), SignType.OAK);
		this.setDefaultState(this.stateManager.getDefaultState().with(ROTATION, 0).with(FLOOR, true).with(WATERLOGGED, false));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(ROTATION, FLOOR, WATERLOGGED);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
		return state.get(FLOOR) ? SHAPE : WALL_SHAPES[state.get(ROTATION) >> 2];
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new BNSignBlockEntity(pos, state);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);
		boolean bl = itemStack.getItem() instanceof DyeItem && player.getAbilities().allowModifyWorld;
		if (world.isClient) {
			return bl ? ActionResult.SUCCESS : ActionResult.CONSUME;
		}
		else {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof BNSignBlockEntity) {
				BNSignBlockEntity signBlockEntity = (BNSignBlockEntity) blockEntity;
				if (bl) {
					boolean bl2 = signBlockEntity.setTextColor(((DyeItem) itemStack.getItem()).getColor());
					if (bl2 && !player.isCreative()) {
						itemStack.decrement(1);
					}
				}
				return signBlockEntity.onActivate(player) ? ActionResult.SUCCESS : ActionResult.PASS;
			}
			else {
				return ActionResult.PASS;
			}
		}
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if (placer != null && placer instanceof PlayerEntity) {
			BNSignBlockEntity sign = (BNSignBlockEntity) world.getBlockEntity(pos);
			if (!world.isClient) {
				sign.setEditor((PlayerEntity) placer);
				((ServerPlayerEntity) placer).networkHandler.sendPacket(new SignEditorOpenS2CPacket(pos));
			}
			else
				sign.setEditable(true);
		}
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if ((Boolean) state.get(WATERLOGGED)) {
			world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		if (!canPlaceAt(state, world, pos)) {
			return state.get(WATERLOGGED) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
		}
		return super.getStateForNeighborUpdate(state, facing, neighborState, world, pos, neighborPos);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		if (ctx.getSide() == Direction.UP) {
			FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
			return this.getDefaultState()
					.with(FLOOR, true)
					.with(ROTATION, MathHelper.floor((180.0 + ctx.getPlayerYaw() * 16.0 / 360.0) + 0.5 - 12) & 15)
					.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
		}
		else if (ctx.getSide() != Direction.DOWN) {
			BlockState blockState = this.getDefaultState();
			FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
			WorldView worldView = ctx.getWorld();
			BlockPos blockPos = ctx.getBlockPos();
			Direction[] directions = ctx.getPlacementDirections();
			Direction[] var7 = directions;
			int var8 = directions.length;

			for (int var9 = 0; var9 < var8; ++var9) {
				Direction direction = var7[var9];
				if (direction.getAxis().isHorizontal()) {
					Direction direction2 = direction.getOpposite();
					int rot = MathHelper.floor((180.0 + direction2.asRotation() * 16.0 / 360.0) + 0.5 + 4) & 15;
					blockState = blockState.with(ROTATION, rot);
					if (blockState.canPlaceAt(worldView, blockPos)) {
						return blockState.with(FLOOR, false).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
					}
				}
			}
		}

		return null;
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		return Collections.singletonList(new ItemStack(this));
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		if (!state.get(FLOOR)) {
			int index = (state.get(ROTATION) >> 2) & 3;
			return world.getBlockState(pos.offset(ROT[index])).getMaterial().isSolid();
		}
		else {
			return world.getBlockState(pos.down()).getMaterial().isSolid();
		}
	}
}