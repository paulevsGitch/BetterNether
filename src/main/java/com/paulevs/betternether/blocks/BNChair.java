package com.paulevs.betternether.blocks;



import com.paulevs.betternether.BlocksHelper;
import com.paulevs.betternether.entity.EntityChair;
import com.paulevs.betternether.registry.RegistryHandler;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;


public class BNChair extends BlockBaseNotFull {
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	private float height;

	public BNChair(Block block, int height) {
		super(AbstractBlock.Properties.from(block).notSolid());
		this.height = (float) (height - 3F) / 16F;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> StateContainer) {
		StateContainer.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlacementHorizontalFacing().getOpposite());
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		if (world.isRemote) {
			return ActionResultType.FAIL;
		}
		else {
			if (player.isPassenger() || player.isSpectator())
				return ActionResultType.FAIL;

			double px = pos.getX() + 0.5;
			double py = pos.getY() + height;
			double pz = pos.getZ() + 0.5;

			List<EntityChair> active = world.getEntitiesWithinAABB(EntityChair.class, new AxisAlignedBB(pos), new Predicate<EntityChair>() {
				@Override
				public boolean test(EntityChair entity) {
					return entity.isOnePlayerRiding();
				}
			});
			if (!active.isEmpty())
				return ActionResultType.FAIL;

			float yaw = state.get(FACING).getOpposite().getHorizontalAngle();
			EntityChair entity = RegistryHandler.CHAIR.create(world);
				entity.setLocationAndAngles(px, py, pz, yaw, 0);
			entity.setNoGravity(true);
			entity.setSilent(true);
			entity.setInvisible(true);
			entity.setRotationYawHead(yaw);
			entity.setRenderYawOffset(yaw);
			if (world.addEntity(entity)) {
				player.startRiding(entity, true);
				player.setRenderYawOffset(yaw);
				player.setRotationYawHead(yaw);
				return ActionResultType.SUCCESS;
			}
			return ActionResultType.FAIL;
		}
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return BlocksHelper.rotateHorizontal(state, rotation, FACING);
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return BlocksHelper.mirrorHorizontal(state, mirror, FACING);
	}
}
