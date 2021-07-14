package paulevs.betternether.blocks;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import paulevs.betternether.blockentities.BNBarrelBlockEntity;
import paulevs.betternether.registry.BlockEntitiesRegistry;

public class BNBarrel extends BarrelBlock {
	public BNBarrel(Block source) {
		super(FabricBlockSettings.copyOf(source).nonOpaque());
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return BlockEntitiesRegistry.BARREL.create(pos, state);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		List<ItemStack> drop = super.getDrops(state, builder);
		drop.add(new ItemStack(this.asItem()));
		return drop;
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (world.isClientSide) {
			return InteractionResult.SUCCESS;
		}
		else {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof BNBarrelBlockEntity) {
				player.openMenu((BNBarrelBlockEntity) blockEntity);
				player.awardStat(Stats.OPEN_BARREL);
				PiglinAi.angerNearbyPiglins(player, true);
			}

			return InteractionResult.CONSUME;
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof BNBarrelBlockEntity) {
			((BNBarrelBlockEntity) blockEntity).tick();
		}
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if (itemStack.hasCustomHoverName()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof BNBarrelBlockEntity) {
				((BNBarrelBlockEntity) blockEntity).setCustomName(itemStack.getHoverName());
			}
		}
	}
}
