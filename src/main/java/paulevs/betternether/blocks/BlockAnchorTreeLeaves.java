package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import paulevs.betternether.blocks.materials.Materials;

public class BlockAnchorTreeLeaves extends BlockBaseNotFull
{
	public BlockAnchorTreeLeaves()
	{
		super(Materials.makeLeaves(MaterialColor.GREEN));
		this.setDropItself(false);
		this.setRenderLayer(BNRenderLayer.CUTOUT);
	}
	
	public AbstractBlock.OffsetType getOffsetType()
	{
		return AbstractBlock.OffsetType.XZ;
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
	public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos)
	{
		return VoxelShapes.empty();
	}
}