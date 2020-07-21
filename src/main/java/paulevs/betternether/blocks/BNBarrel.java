package paulevs.betternether.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import paulevs.betternether.blockentities.BNBarrelBlockEntity;
import paulevs.betternether.registry.BlockEntitiesRegistry;

public class BNBarrel extends BarrelBlock
{
	public BNBarrel(Block source)
	{
		super(FabricBlockSettings.copyOf(source).nonOpaque());
	}
	
	@Override
	public BlockEntity createBlockEntity(BlockView world)
	{
		return BlockEntitiesRegistry.BARREL.instantiate();
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		List<ItemStack> drop = super.getDroppedStacks(state, builder);
		drop.add(new ItemStack(this.asItem()));
		return drop;
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
			if (blockEntity instanceof BNBarrelBlockEntity)
			{
				player.openHandledScreen((BNBarrelBlockEntity) blockEntity);
				player.incrementStat(Stats.OPEN_BARREL);
				PiglinBrain.onGuardedBlockBroken(player, true);
			}

			return ActionResult.CONSUME;
		}
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
	{
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof BNBarrelBlockEntity)
		{
			((BNBarrelBlockEntity) blockEntity).tick();
		}
	}

	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.MODEL;
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack)
	{
		if (itemStack.hasCustomName())
		{
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof BNBarrelBlockEntity)
			{
				((BNBarrelBlockEntity) blockEntity).setCustomName(itemStack.getName());
			}
		}
	}
}
