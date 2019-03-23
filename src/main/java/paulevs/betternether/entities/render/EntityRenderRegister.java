package paulevs.betternether.entities.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import paulevs.betternether.entities.EntityFirefly;

public class EntityRenderRegister
{
	public static void register()
	{
		IRenderFactory renderFactory = new IRenderFactory<EntityFirefly>()
		{
			@Override
			public Render<? super EntityFirefly> createRenderFor(RenderManager manager)
			{
				return new RenderFirefly(manager);
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(EntityFirefly.class, renderFactory);
	}
}
