package com.paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.paulevs.betternether.blockentities.BNSignTileEntity;
import com.paulevs.betternether.screens.SignEditScreen;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WoodType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.network.play.server.SOpenSignMenuPacket;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BNSign extends AbstractSignBlock  {
	public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_0_15;
	public static final BooleanProperty FLOOR = BooleanProperty.create("floor");
	private static final VoxelShape[] WALL_SHAPES = new VoxelShape[] {
			Block.makeCuboidShape(0.0D, 4.5D, 14.0D, 16.0D, 12.5D, 16.0D),
			Block.makeCuboidShape(0.0D, 4.5D, 0.0D, 2.0D, 12.5D, 16.0D),
			Block.makeCuboidShape(0.0D, 4.5D, 0.0D, 16.0D, 12.5D, 2.0D),
			Block.makeCuboidShape(14.0D, 4.5D, 0.0D, 16.0D, 12.5D, 16.0D)
	};

	public BNSign(Block source) {
		super(AbstractBlock.Properties.from(source).doesNotBlockMovement().notSolid(), WoodType.OAK);
		this.setDefaultState(this.stateContainer.getBaseState().with(ROTATION, 0).with(FLOOR, true).with(WATERLOGGED, false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(ROTATION, FLOOR, WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader view, BlockPos pos, ISelectionContext ePos) {
		return state.get(FLOOR) ? SHAPE : WALL_SHAPES[state.get(ROTATION) >> 2];
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader world) {
		return new BNSignTileEntity();
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		ItemStack itemStack = player.getHeldItem(hand);
		boolean bl = itemStack.getItem() instanceof DyeItem && player.abilities.allowEdit;
		if (world.isRemote) {
			return bl ? ActionResultType.SUCCESS : ActionResultType.CONSUME;
		}
		else {
			TileEntity blockEntity = world.getTileEntity(pos);
			if (blockEntity instanceof BNSignTileEntity) {
				BNSignTileEntity signBlockEntity = (BNSignTileEntity) blockEntity;
				if (bl) {
					boolean bl2 = signBlockEntity.setTextColor(((DyeItem) itemStack.getItem()).getDyeColor());
					if (bl2 && !player.isCreative()) {
						itemStack.shrink(1);
					}
				}
				return signBlockEntity.onActivate(player) ? ActionResultType.SUCCESS : ActionResultType.PASS;
			}
			else {
				return ActionResultType.PASS;
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if (placer != null && placer instanceof PlayerEntity) {
			BNSignTileEntity sign = (BNSignTileEntity) world.getTileEntity(pos);
			if (!world.isRemote) {
				sign.setEditor((PlayerEntity) placer);
				((ServerPlayerEntity) placer).connection.sendPacket(new SOpenSignMenuPacket(pos));
			}
			else
				sign.setEditable(true);
		}
	}

	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
		if ((Boolean) state.get(WATERLOGGED)) {
			world.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}

		return super.updatePostPlacement(state, facing, neighborState, world, pos, neighborPos);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx) {
		if (ctx.getFace() == Direction.UP) {
			FluidState fluidState = ctx.getWorld().getFluidState(ctx.getPos());
			return this.getDefaultState()
					.with(FLOOR, true)
					.with(ROTATION, MathHelper.floor((180.0 + ctx.getPlacementYaw() * 16.0 / 360.0) + 0.5 - 12) & 15)
					.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
		}
		else if (ctx.getFace() != Direction.DOWN) {
			BlockState blockState = this.getDefaultState();
			FluidState fluidState = ctx.getWorld().getFluidState(ctx.getPos());
			IWorldReader worldView = ctx.getWorld();
			BlockPos blockPos = ctx.getPos();
			Direction[] directions = ctx.getNearestLookingDirections();
			Direction[] var7 = directions;
			int var8 = directions.length;

			for (int var9 = 0; var9 < var8; ++var9) {
				Direction direction = var7[var9];
				if (direction.getAxis().isHorizontal()) {
					Direction direction2 = direction.getOpposite();
					int rot = MathHelper.floor((180.0 + direction2.getHorizontalAngle() * 16.0 / 360.0) + 0.5 + 4) & 15;
					blockState = blockState.with(ROTATION, rot);
					if (blockState.isValidPosition(worldView, blockPos)) {
						return blockState.with(FLOOR, false).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
					}
				}
			}
		}

		return null;
	}
	
	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		return Collections.singletonList(new ItemStack(this));
	}
}