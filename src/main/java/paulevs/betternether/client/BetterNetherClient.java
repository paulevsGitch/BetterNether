package paulevs.betternether.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blocks.BNRenderLayer;
import paulevs.betternether.config.screen.ConfigScreen;
import paulevs.betternether.registry.EntityRenderRegistry;
import paulevs.betternether.registry.NetherParticles;
import ru.bclib.integration.modmenu.ModMenu;

public class BetterNetherClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		registerRenderLayers();
		EntityRenderRegistry.register();
		
		NetherParticles.register();
		ModMenu.addModMenuScreen(BetterNether.MOD_ID, ConfigScreen::new);
	}

	private void registerRenderLayers() {
		RenderType cutout = RenderType.cutout();
		RenderType translucent = RenderType.translucent();
		Registry.BLOCK.forEach(block -> {
			if (block instanceof IRenderTypeable) {
				BNRenderLayer layer = ((IRenderTypeable) block).getRenderLayer();
				if (layer == BNRenderLayer.CUTOUT)
					BlockRenderLayerMap.INSTANCE.putBlock(block, cutout);
				else if (layer == BNRenderLayer.TRANSLUCENT)
					BlockRenderLayerMap.INSTANCE.putBlock(block, translucent);
			}
		});
	}
}
