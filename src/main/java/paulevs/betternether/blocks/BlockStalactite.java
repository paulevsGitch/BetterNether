package paulevs.betternether.blocks;

import javax.annotation.Nullable;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BlockStalactite extends BlockBaseNotFull
{
	public static final IntProperty SIZE = IntProperty.of("size", 0, 7);
	private static final Mutable POS = new Mutable();
	private static final VoxelShape[] SHAPES;
	
	public BlockStalactite(Block source)
	{
		super(FabricBlockSettings.copy(source).nonOpaque());
		this.setDefaultState(getStateManager().getDefaultState().with(SIZE, 0));
	}
	
	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
        stateManager.add(SIZE);
    }
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		return SHAPES[state.get(SIZE)];
	}
	
	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack)
	{
		if (world.getBlockState(pos.down()).getBlock() == this)
		{
			POS.setX(pos.getX());
			POS.setZ(pos.getZ());
			for (int i = 1; i < 8; i++)
			{
				POS.setY(pos.getY() - i);
				if (world.getBlockState(POS).getBlock() == this)
				{
					int size = world.getBlockState(POS).get(SIZE);
					if (size < i)
					{
						world.setBlockState(POS, getDefaultState().with(SIZE, i));
					}
					else
						break;
				}
				else
					break;
			}
		}
		if (world.getBlockState(pos.up()).getBlock() == this)
		{
			POS.setX(pos.getX());
			POS.setZ(pos.getZ());
			for (int i = 1; i < 8; i++)
			{
				POS.setY(pos.getY() + i);
				if (world.getBlockState(POS).getBlock() == this)
				{
					int size = world.getBlockState(POS).get(SIZE);
					if (size < i)
					{
						world.setBlockState(POS, getDefaultState().with(SIZE, i));
					}
					else
						break;
				}
				else
					break;
			}
		}
	}
	
	static
	{
		SHAPES = new VoxelShape[8];
		for (int i = 0; i < 8; i++)
			SHAPES[i] = Block.createCuboidShape(7 - i, 0, 7 - i, 9 + i, 16, 9 + i);
	}
}