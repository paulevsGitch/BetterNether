package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import paulevs.betternether.BlocksHelper;

public class BlockOrangeMushroom extends BlockCommonPlant
{
	private static final VoxelShape[] SHAPES = new VoxelShape[] {
			VoxelShapes.cuboid(0.25, 0.0, 0.25, 0.75, 0.375, 0.75),
			VoxelShapes.cuboid(0.125, 0.0, 0.125, 0.875, 0.625, 0.875),
			VoxelShapes.cuboid(0.0625, 0.0, 0.0625, 0.9375, 0.875, 0.9375),
			VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)
	};

	public BlockOrangeMushroom()
	{
		super(FabricBlockSettings.of(Material.PLANT)
				.materialColor(MaterialColor.ORANGE)
				.sounds(BlockSoundGroup.CROP)
				.nonOpaque()
				.hardness(0.5F)
				.ticksRandomly()
				.noCollision());
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
	{
		return BlocksHelper.isNetherMycelium(world.getBlockState(pos.down()));
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		return SHAPES[state.get(AGE)];
	}
}