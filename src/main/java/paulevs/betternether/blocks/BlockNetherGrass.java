package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class BlockNetherGrass extends BlockBase
{
	public BlockNetherGrass()
	{
		super(FabricBlockSettings.copy(Blocks.TALL_GRASS).build());
		this.setRenderLayer(BlockRenderLayer.CUTOUT);
	}
	
	@Environment(EnvType.CLIENT)
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView view, BlockPos pos)
	{
		return 1.0F;
	}
}
