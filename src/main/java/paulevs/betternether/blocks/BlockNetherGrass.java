package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.EntityContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.WorldView;
import paulevs.betternether.BlocksHelper;

public class BlockNetherGrass extends BlockBase
{
	private static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 2, 14, 14, 14);
	
	public BlockNetherGrass()
	{
		super(FabricBlockSettings.of(Material.PLANT)
				.materialColor(MaterialColor.GRAY_TERRACOTTA)
				.sounds(BlockSoundGroup.CROP)
				.nonOpaque()
				.noCollision()
				.breakInstantly()
				.ticksRandomly()
				.build());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDropItself(false);
	}
	
	@Environment(EnvType.CLIENT)
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView view, BlockPos pos)
	{
		return 1.0F;
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
	{
		Vec3d vec3d = state.getOffsetPos(view, pos);
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
		return BlocksHelper.isNetherGround(world.getBlockState(pos.down()));
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos)
	{
		if (!canPlaceAt(state, world, pos))
			return Blocks.AIR.getDefaultState();
		else
			return state;
	}
}
