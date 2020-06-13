package paulevs.betternether.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class BlockEntityRenderRegistry
{
	@Environment(EnvType.CLIENT)
	public static void register()
	{
		//BlockEntityRendererRegistry.INSTANCE.register(BlockEntitiesRegistry.NETHER_SIGN, NetherSignBlockEntityRenderer::new);
	}
}
