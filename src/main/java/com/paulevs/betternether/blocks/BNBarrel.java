package com.paulevs.betternether.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.paulevs.betternether.blockentities.BNBarrelTileEntity;
import com.paulevs.betternether.registry.RegistryHandler;
import com.paulevs.betternether.registry.TileEntitiesRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BNBarrel extends BarrelBlock {
	public BNBarrel(Block source) {
		super(AbstractBlock.Properties.from(source).notSolid());
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader world) {
		return TileEntitiesRegistry.BARREL.create();
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		List<ItemStack> drop = super.getDrops(state, builder);
		drop.add(new ItemStack(this.asItem()));
		return drop;
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		if (world.isRemote) {
			return ActionResultType.SUCCESS;
		}
		else {
			TileEntity blockEntity = world.getTileEntity(pos);
			if (blockEntity instanceof BNBarrelTileEntity) {
				player.openContainer((BNBarrelTileEntity) blockEntity);
				player.addStat(Stats.OPEN_BARREL);
				PiglinTasks.func_234478_a_(player, true);
			}

			return ActionResultType.CONSUME;
		}
	}

	@Override
	public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		TileEntity blockEntity = world.getTileEntity(pos);
		if (blockEntity instanceof BNBarrelTileEntity) {
			((BNBarrelTileEntity) blockEntity).tick();
		}
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if (itemStack.hasDisplayName()) {
			TileEntity blockEntity = world.getTileEntity(pos);
			if (blockEntity instanceof BNBarrelTileEntity) {
				((BNBarrelTileEntity) blockEntity).setCustomName(itemStack.getDisplayName());
			}
		}
	}
}
