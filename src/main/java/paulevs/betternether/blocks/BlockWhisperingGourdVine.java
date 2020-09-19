package paulevs.betternether.blocks;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.shapes.TripleShape;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockWhisperingGourdVine extends BlockBaseNotFull implements Fertilizable
{
	private static final VoxelShape SELECTION = Block.createCuboidShape(2, 0, 2, 14, 16, 14);
	public static final EnumProperty<TripleShape> SHAPE = EnumProperty.of("shape", TripleShape.class);

	public BlockWhisperingGourdVine()
	{
		super(FabricBlockSettings.of(Material.PLANT)
				.materialColor(MaterialColor.RED)
				.sounds(BlockSoundGroup.CROP)
				.noCollision()
				.breakInstantly()
				.nonOpaque()
				.ticksRandomly()
				.breakByTool(FabricToolTags.SHEARS)
				.breakInstantly());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDropItself(false);
		this.setDefaultState(getStateManager().getDefaultState().with(SHAPE, TripleShape.BOTTOM));
	}
	
	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
        stateManager.add(SHAPE);
    }

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		return SELECTION;
	}
	
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
	{
		BlockState upState = world.getBlockState(pos.up());
		return upState.getBlock() == this || upState.isSideSolidFullSquare(world, pos, Direction.DOWN);
	}

	@Environment(EnvType.CLIENT)
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView view, BlockPos pos)
	{
		return 1.0F;
	}

	@Override
	public boolean isTranslucent(BlockState state, BlockView view, BlockPos pos)
	{
		return true;
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos)
	{
		if (!canPlaceAt(state, world, pos))
			return Blocks.AIR.getDefaultState();
		else if (world.getBlockState(pos.down()).getBlock() != this)
			return state.with(SHAPE, TripleShape.BOTTOM);
		else if (state.get(SHAPE) == TripleShape.BOTTOM)
			return state.with(SHAPE, TripleShape.TOP);
		else
			return state;
	}

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient)
	{
		return world.getBlockState(pos.up(3)).getBlock() != this && world.getBlockState(pos.down()).getBlock() != this;
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state)
	{
		return world.getBlockState(pos.up(3)).getBlock() != this && world.getBlockState(pos.down()).getMaterial().isReplaceable();
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state)
	{
		BlocksHelper.setWithoutUpdate(world, pos, state.with(SHAPE, TripleShape.TOP));
		BlocksHelper.setWithoutUpdate(world, pos.down(), getDefaultState());
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		ItemStack tool = builder.get(LootContextParameters.TOOL);
		if (tool != null && tool.getItem().isIn(FabricToolTags.SHEARS))
			return Lists.newArrayList(new ItemStack(this.asItem()));
		else if (state.get(SHAPE) == TripleShape.BOTTOM || MHelper.RANDOM.nextBoolean())
			return Lists.newArrayList(new ItemStack(this.asItem()));
		else
			return Lists.newArrayList();
	}
	
	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
	{
		super.scheduledTick(state, world, pos, random);
		if (canGrow(world, random, pos, state))
		{
			grow(world, random, pos, state);
		}
		if (state.get(SHAPE) != TripleShape.MIDDLE && state.get(SHAPE) != TripleShape.BOTTOM && random.nextInt(16) == 0)
		{
			BlocksHelper.setWithoutUpdate(world, pos, state.with(SHAPE, TripleShape.MIDDLE));
		}
	}
	
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
	{
		ItemStack tool = player.getStackInHand(hand);
		if (tool.getItem().isIn(FabricToolTags.SHEARS) && state.get(SHAPE) == TripleShape.MIDDLE)
		{
			if (!world.isClient)
			{
				BlocksHelper.setWithoutUpdate(world, pos, state.with(SHAPE, TripleShape.BOTTOM));
				world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(BlocksRegistry.WHISPERING_GOURD)));
				if (world.random.nextBoolean())
				{
					world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(BlocksRegistry.WHISPERING_GOURD)));
				}
			}
			return ActionResult.success(world.isClient);
		}
		else
		{
			return super.onUse(state, world, pos, player, hand, hit);
		}
	}
}
