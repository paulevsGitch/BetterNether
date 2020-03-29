package paulevs.betternether.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.entity.EntityType;
import paulevs.betternether.entity.render.RenderChair;
import paulevs.betternether.entity.render.RenderFirefly;

@Environment(EnvType.CLIENT)
public class EntityRenderRegistry
{
	public static void register()
	{
		registerRender(EntityRegistry.FIREFLY, RenderFirefly.class);
		registerRender(EntityRegistry.CHAIR, RenderChair.class);
	}

	private static void registerRender(EntityType<?> entity, Class<? extends MobEntityRenderer<?, ?>> renderer)
	{
		EntityRendererRegistry.INSTANCE.register(entity, (entityRenderDispatcher, context) -> {
			MobEntityRenderer<?, ?> render = null;
			try
			{
				render = renderer.getConstructor(entityRenderDispatcher.getClass()).newInstance(entityRenderDispatcher);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return render;
		});
	}
}
