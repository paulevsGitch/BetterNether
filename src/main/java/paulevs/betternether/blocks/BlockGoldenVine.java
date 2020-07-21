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
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import paulevs.betternether.BlocksHelper;

public class BlockGoldenVine  extends BlockBaseNotFull implements Fertilizable
{
	private static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 2, 14, 16, 14);
	public static final BooleanProperty BOTTOM = BooleanProperty.of("bottom");

	public BlockGoldenVine()
	{
		super(FabricBlockSettings.of(Material.PLANT)
				.materialColor(MaterialColor.RED)
				.sounds(BlockSoundGroup.CROP)
				.noCollision()
				.breakInstantly()
				.nonOpaque()
				.lightLevel(15));
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDropItself(false);
		this.setDefaultState(getStateManager().getDefaultState().with(BOTTOM, true));
	}
	
	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
        stateManager.add(BOTTOM);
    }

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		return SHAPE;
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
		if (canPlaceAt(state, world, pos))
			return world.getBlockState(pos.down()).getBlock() == this ? state.with(BOTTOM, false) : state.with(BOTTOM, true);
		else
			return Blocks.AIR.getDefaultState();
	}

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient)
	{
		Mutable blockPos = new Mutable().set(pos);
		for (int y = pos.getY() - 1; y > 1; y--)
		{
			blockPos.setY(y);
			if (world.getBlockState(blockPos).getBlock() != this)
				return world.getBlockState(blockPos).getBlock() == Blocks.AIR;
		}
		return false;
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state)
	{
		return true;
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state)
	{
		Mutable blockPos = new Mutable().set(pos);
		for (int y = pos.getY(); y > 1; y--)
		{
			blockPos.setY(y);
			if (world.getBlockState(blockPos).getBlock() != this)
				break;
		}
		BlocksHelper.setWithoutUpdate(world, blockPos.up(), getDefaultState().with(BOTTOM, false));
		BlocksHelper.setWithoutUpdate(world, blockPos, getDefaultState().with(BOTTOM, true));
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		ItemStack tool = builder.get(LootContextParameters.TOOL);
		if (tool != null && tool.getItem().isIn(FabricToolTags.SHEARS))
		{
			return Lists.newArrayList(new ItemStack(this.asItem()));
		}
		else
		{
			return Lists.newArrayList();
		}
	}
}
