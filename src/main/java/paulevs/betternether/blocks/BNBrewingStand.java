package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlacementEnvironment;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.Container;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import paulevs.betternether.blockentities.BNBrewingStandBlockEntity;
import paulevs.betternether.client.IRenderTypeable;

public class BNBrewingStand extends BlockWithEntity implements IRenderTypeable
{
	public static final BooleanProperty[] BOTTLE_PROPERTIES = new BooleanProperty[] {
			Properties.HAS_BOTTLE_0,
			Properties.HAS_BOTTLE_1,
			Properties.HAS_BOTTLE_2
	};
	protected static final VoxelShape SHAPE = VoxelShapes.union(
			Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 2.0D, 15.0D),
			Block.createCuboidShape(7.0D, 0.0D, 7.0D, 9.0D, 14.0D, 9.0D));

	public BNBrewingStand()
	{
		super(FabricBlockSettings.copy(Blocks.NETHER_BRICKS)
				.strength(0.5F, 0.5F)
				.lightLevel(1)
				.nonOpaque()
				.build());
		this.setDefaultState(getStateManager().getDefaultState()
				.with(BOTTLE_PROPERTIES[0], false)
				.with(BOTTLE_PROPERTIES[1], false)
				.with(BOTTLE_PROPERTIES[2], false));
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		return Collections.singletonList(new ItemStack(this.asItem()));
	}

	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.MODEL;
	}

	@Override
	public BlockEntity createBlockEntity(BlockView view)
	{
		return new BNBrewingStandBlockEntity();
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
	{
		return SHAPE;
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
				player.openContainer((BNBrewingStandBlockEntity) blockEntity);
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
	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		double d = (double)pos.getX() + 0.4D + (double)random.nextFloat() * 0.2D;
		double e = (double)pos.getY() + 0.7D + (double)random.nextFloat() * 0.3D;
		double f = (double)pos.getZ() + 0.4D + (double)random.nextFloat() * 0.2D;
		world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved)
	{
		if (state.getBlock() != newState.getBlock())
		{
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof BNBrewingStandBlockEntity)
			{
				ItemScatterer.spawn(world, (BlockPos) pos, (Inventory) blockEntity);
			}

			super.onBlockRemoved(state, world, pos, newState, moved);
		}
	}

	@Override
	public boolean hasComparatorOutput(BlockState state)
	{
		return true;
	}

	@Override
	public int getComparatorOutput(BlockState state, World world, BlockPos pos)
	{
		return Container.calculateComparatorOutput(world.getBlockEntity(pos));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		builder.add(BOTTLE_PROPERTIES[0], BOTTLE_PROPERTIES[1], BOTTLE_PROPERTIES[2]);
	}

	@Override
	public boolean canPlaceAtSide(BlockState world, BlockView view, BlockPos pos, BlockPlacementEnvironment env)
	{
		return false;
	}

	@Override
	public BNRenderLayer getRenderLayer()
	{
		return BNRenderLayer.CUTOUT;
	}
}
