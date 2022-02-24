package paulevs.betternether.integrations.wthit;

import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import net.minecraft.resources.ResourceLocation;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blocks.BlockCommonPlant;

public class WailaPlugin implements IWailaPlugin {
	static class Options {
		public static final ResourceLocation CROP_PROGRESS = new ResourceLocation("crop_progress");
	}
	@Override
	public void register(IRegistrar registrar) {
		BetterNether.LOGGER.info("Registering Waila-/Wthit-Plugin.");
		
		registrar.addComponent(NetherPlantProvider.INSTANCE, TooltipPosition.BODY, BlockCommonPlant.class);
	}
}
