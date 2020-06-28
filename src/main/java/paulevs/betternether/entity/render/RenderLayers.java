package paulevs.betternether.entity.render;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.RenderPhase.Layering;
import net.minecraft.client.render.RenderPhase.Target;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

public class RenderLayers
{
	public static RenderPhase.Transparency translucentTransparency;
	public static RenderPhase.WriteMaskState colorMask;
	public static RenderPhase.Fog fog;
	public static RenderPhase.DepthTest lEqualDepthTest;
	public static Layering polygonZLayering;
	public static Target translucentTarget;
	
	public static RenderLayer getFirefly(Identifier texture)
	{
		RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder()
				.texture(new RenderPhase.Texture(texture, false, false))
				.transparency(translucentTransparency)
				.writeMaskState(colorMask)
				.fog(fog)
				.depthTest(lEqualDepthTest)
				.layering(polygonZLayering)
				.target(translucentTarget)
				.build(true);
		return RenderLayer.of("firefly", VertexFormats.POSITION_COLOR_TEXTURE, 7, 256, false, true, multiPhaseParameters);
	}
}
