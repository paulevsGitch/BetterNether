package paulevs.betternether.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.blocks.BNPane;
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
		RenderLayer translucent = RenderLayer.getTranslucent();
		Registry.BLOCK.forEach(block -> {
			if (block instanceof BlockBase)
			{
				BlockBase baseBlock = (BlockBase) block;
				if (baseBlock.getRenderLayer() == BNRenderLayer.CUTOUT)
					BlockRenderLayerMap.INSTANCE.putBlock(block, cutout);
				else if (baseBlock.getRenderLayer() == BNRenderLayer.TRANSLUCENT)
					BlockRenderLayerMap.INSTANCE.putBlock(block, translucent);
			}
			else if (block instanceof BNPane)
				BlockRenderLayerMap.INSTANCE.putBlock(block, translucent);
		});
	}
}
