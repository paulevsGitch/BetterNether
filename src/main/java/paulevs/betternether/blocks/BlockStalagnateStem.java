package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.EntityContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class BlockStalagnateStem extends BlockBaseNotFull
{
	private static final VoxelShape SELECT_SHAPE = Block.createCuboidShape(4, 0, 4, 12, 16, 12);
	private static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(5, 0, 5, 11, 16, 11);

	public BlockStalagnateStem()
	{
		super(FabricBlockSettings.of(Material.WOOD)
				.materialColor(MaterialColor.LIME_TERRACOTTA)
				.sounds(BlockSoundGroup.WOOD)
				.hardness(2F)
				.resistance(0.5F)
				.build());
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
	{
		return SELECT_SHAPE;
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
	{
		return COLLISION_SHAPE;
	}
}
