package paulevs.betternether.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import paulevs.betternether.blockentities.render.BNChestBlockEntityRenderer;

public class BlockEntityRenderRegistry
{
	@Environment(EnvType.CLIENT)
	public static void register()
	{
		BlockEntityRendererRegistry.INSTANCE.register(BlockEntitiesRegistry.CHEST, BNChestBlockEntityRenderer::new);
	}
}
