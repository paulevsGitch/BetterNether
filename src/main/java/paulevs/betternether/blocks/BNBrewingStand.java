package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BrewingStandBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import paulevs.betternether.blockentities.BNBrewingStandBlockEntity;
import paulevs.betternether.client.IRenderTypeable;

public class BNBrewingStand extends BrewingStandBlock implements IRenderTypeable
{
	public BNBrewingStand()
	{
		super(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS)
				.strength(0.5F, 0.5F)
				.lightLevel(1)
				.nonOpaque());
	}

	@Override
	public BlockEntity createBlockEntity(BlockView view)
	{
		return new BNBrewingStandBlockEntity();
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
	{
		if (world.isClient)
		{
			return ActionResult.SUCCESS;
		}
		else
		{
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof BNBrewingStandBlockEntity)
			{
				player.openHandledScreen((BNBrewingStandBlockEntity) blockEntity);
				player.incrementStat(Stats.INTERACT_WITH_BREWINGSTAND);
			}

			return ActionResult.SUCCESS;
		}
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack)
	{
		if (itemStack.hasCustomName())
		{
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof BNBrewingStandBlockEntity)
			{
				((BNBrewingStandBlockEntity) blockEntity).setCustomName(itemStack.getName());
			}
		}
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean notify)
	{
		if (!state.isOf(newState.getBlock()))
		{
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof BNBrewingStandBlockEntity)
			{
				ItemScatterer.spawn(world, (BlockPos)pos, (Inventory)((BNBrewingStandBlockEntity) blockEntity));
			}

			super.onStateReplaced(state, world, pos, newState, notify);
		}
	}

	@Override
	public BNRenderLayer getRenderLayer()
	{
		return BNRenderLayer.CUTOUT;
	}
}
