package paulevs.betternether.blocks;

import java.util.List;
import java.util.function.Predicate;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.entity.EntityChair;
import paulevs.betternether.registry.EntityRegistry;

public class BNChair extends BlockBaseNotFull {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	private float height;

	public BNChair(Block block, int height) {
		super(FabricBlockSettings.copyOf(block).noOcclusion());
		this.height = (float) (height - 3F) / 16F;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
		stateManager.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (world.isClientSide) {
			return InteractionResult.FAIL;
		}
		else {
			if (player.isPassenger() || player.isSpectator())
				return InteractionResult.FAIL;

			double px = pos.getX() + 0.5;
			double py = pos.getY() + height;
			double pz = pos.getZ() + 0.5;

			List<EntityChair> active = world.getEntitiesOfClass(EntityChair.class, new AABB(pos), new Predicate<EntityChair>() {
				@Override
				public boolean test(EntityChair entity) {
					return entity.hasExactlyOnePlayerPassenger();
				}
			});
			if (!active.isEmpty())
				return InteractionResult.FAIL;

			float yaw = state.getValue(FACING).getOpposite().toYRot();
			EntityChair entity = EntityRegistry.CHAIR.create(world);
			entity.moveTo(px, py, pz, yaw, 0);
			entity.setNoGravity(true);
			entity.setSilent(true);
			entity.setInvisible(true);
			entity.setYHeadRot(yaw);
			entity.setYBodyRot(yaw);
			if (world.addFreshEntity(entity)) {
				player.startRiding(entity, true);
				player.setYBodyRot(yaw);
				player.setYHeadRot(yaw);
				return InteractionResult.SUCCESS;
			}
			return InteractionResult.FAIL;
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
