package paulevs.betternether.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.blocks.BNRenderLayer;
import paulevs.betternether.blocks.BlockBase;

public class BetterNetherClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		registerRenderLayers();
	}
	
	private void registerRenderLayers()
	{
		RenderLayer cutout = RenderLayer.getCutout();
		Registry.BLOCK.forEach(block -> {
			if (block instanceof BlockBase)
			{
				BlockBase baseBlock = (BlockBase) block;
				if (baseBlock.getRenderLayer() == BNRenderLayer.CUTOUT)
					BlockRenderLayerMap.INSTANCE.putBlocks(cutout, block);
			}
		});
	}
}
