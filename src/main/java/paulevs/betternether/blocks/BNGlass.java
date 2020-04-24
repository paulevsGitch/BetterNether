package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class BNGlass extends BlockBaseNotFull
{
	public BNGlass(Block block)
	{
		super(FabricBlockSettings.copy(block)
				.resistance(0.3F)
				.nonOpaque()
				.breakByTool(FabricToolTags.PICKAXES)
				.build());
		this.setRenderLayer(BNRenderLayer.TRANSLUCENT);
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
	
	@Environment(EnvType.CLIENT)
	public boolean isSideInvisible(BlockState state, BlockState neighbor, Direction facing)
	{
		return neighbor.getBlock() == this ? true : super.isSideInvisible(state, neighbor, facing);
	}
}