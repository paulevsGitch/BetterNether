package paulevs.betternether.blocks;

import java.util.List;
import java.util.Random;
import java.util.function.ToIntFunction;

import com.google.common.collect.Lists;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.shapes.TripleShape;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockLumabusVine extends BlockBaseNotFull
{
	private static final VoxelShape MIDDLE_SHAPE = Block.createCuboidShape(4, 0, 4, 12, 16, 12);
	private static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(2, 4, 2, 14, 16, 14);
	public static final EnumProperty<TripleShape> SHAPE = EnumProperty.of("shape", TripleShape.class);
	private static final Random RANDOM = new Random();

	public BlockLumabusVine()
	{
		super(FabricBlockSettings.of(Material.PLANT)
				.materialColor(MaterialColor.CYAN)
				.sounds(BlockSoundGroup.CROP)
				.noCollision()
				.breakInstantly()
				.nonOpaque()
				.lightLevel(getLuminance()));
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDropItself(false);
		this.setDefaultState(getStateManager().getDefaultState().with(SHAPE, TripleShape.TOP));
	}
	
	private static ToIntFunction<BlockState> getLuminance()
	{
		return (blockState) -> {
			return blockState.get(SHAPE) == TripleShape.BOTTOM ? 15 : 0;
		};
	}
	
	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
        stateManager.add(SHAPE);
    }

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		return state.get(SHAPE) == TripleShape.BOTTOM ? BOTTOM_SHAPE : MIDDLE_SHAPE;
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
		return canPlaceAt(state, world, pos) ? state : Blocks.AIR.getDefaultState();
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		if (state.get(SHAPE) == TripleShape.BOTTOM)
		{
			return Lists.newArrayList(new ItemStack(BlocksRegistry.LUMABUS_SEED, MHelper.randRange(1, 3, RANDOM)), new ItemStack(Items.GLOWSTONE_DUST));
		}
		return Lists.newArrayList();
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state)
	{
		return new ItemStack(BlocksRegistry.LUMABUS_SEED);
	}
}