package paulevs.betternether.mixin.client;

import net.minecraft.client.renderer.RenderStateShard;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RenderStateShard.class)
public class RenderPhaseMixin {
	@Shadow
	@Final
	protected static RenderStateShard.TransparencyStateShard TRANSLUCENT_TRANSPARENCY;

	@Shadow
	@Final
	protected static RenderStateShard.WriteMaskStateShard COLOR_MASK;

	/*@Shadow
	@Final
	protected static RenderPhase.Fog FOG;*/

	@Shadow
	@Final
	protected static RenderStateShard.DepthTestStateShard LEQUAL_DEPTH_TEST;

	@Shadow
	@Final
	protected static RenderStateShard.LayeringStateShard POLYGON_OFFSET_LAYERING;

	@Shadow
	@Final
	protected static RenderStateShard.OutputStateShard CLOUDS_TARGET;

	static {

	}
}