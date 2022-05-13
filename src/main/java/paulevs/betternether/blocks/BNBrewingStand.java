package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrewingStandBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import paulevs.betternether.blockentities.BNBrewingStandBlockEntity;
import paulevs.betternether.client.IRenderTypeable;
import paulevs.betternether.registry.BlockEntitiesRegistry;

public class BNBrewingStand extends BrewingStandBlock implements IRenderTypeable {
	public BNBrewingStand() {
		super(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS)
				.strength(0.5F, 0.5F)
				.luminance(1)
				.noOcclusion());
	}
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return world.isClientSide ? null : createTickerHelper(type, BlockEntitiesRegistry.NETHER_BREWING_STAND, BNBrewingStandBlockEntity::tick);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new BNBrewingStandBlockEntity(blockPos, blockState);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (world.isClientSide) {
			return InteractionResult.SUCCESS;
		}
		else {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof BNBrewingStandBlockEntity) {
				player.openMenu((BNBrewingStandBlockEntity) blockEntity);
				player.awardStat(Stats.INTERACT_WITH_BREWINGSTAND);
			}

			return InteractionResult.SUCCESS;
		}
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		if (itemStack.hasCustomHoverName()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof BNBrewingStandBlockEntity) {
				((BNBrewingStandBlockEntity) blockEntity).setCustomName(itemStack.getHoverName());
			}
		}
	}

	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean notify) {
		if (!state.is(newState.getBlock())) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof BNBrewingStandBlockEntity) {
				Containers.dropContents(world, (BlockPos) pos, (Container) ((BNBrewingStandBlockEntity) blockEntity));
			}

			super.onRemove(state, world, pos, newState, notify);
		}
	}

	@Override
	public BNRenderLayer getRenderLayer() {
		return BNRenderLayer.CUTOUT;
	}
}
