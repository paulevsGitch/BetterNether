package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.materials.Materials;

public class BlockNetherGrass extends BlockBase
{
	private static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 2, 14, 12, 14);
	
	public BlockNetherGrass()
	{
		super(Materials.makeGrass(MaterialColor.GRAY_TERRACOTTA).ticksRandomly());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDropItself(false);
	}
	
	@Environment(EnvType.CLIENT)
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView view, BlockPos pos)
	{
		return 1.0F;
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		Vec3d vec3d = state.getModelOffset(view, pos);
		return SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
	}
	
	@Override
	public Block.OffsetType getOffsetType()
	{
		return Block.OffsetType.XZ;
	}
	
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
	{
		return BlocksHelper.isNetherrack(world.getBlockState(pos.down())) || BlocksHelper.isNylium(world.getBlockState(pos.down()));
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos)
	{
		if (!canPlaceAt(state, world, pos))
			return Blocks.AIR.getDefaultState();
		else
			return state;
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		if (builder.get(LootContextParameters.TOOL).getItem() instanceof ShearsItem)
			return Collections.singletonList(new ItemStack(this.asItem()));
		else
			return super.getDroppedStacks(state, builder);
	}
}
