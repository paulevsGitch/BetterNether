package paulevs.betternether.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.blocks.BNRenderLayer;

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
		RenderLayer translucent = RenderLayer.getTranslucent();
		Registry.BLOCK.forEach(block -> {
			if (block instanceof IRenderTypeable)
			{
				BNRenderLayer layer = ((IRenderTypeable) block).getRenderLayer();
				if (layer == BNRenderLayer.CUTOUT)
					BlockRenderLayerMap.INSTANCE.putBlock(block, cutout);
				else if (layer == BNRenderLayer.TRANSLUCENT)
					BlockRenderLayerMap.INSTANCE.putBlock(block, translucent);
			}
		});
	}
}
