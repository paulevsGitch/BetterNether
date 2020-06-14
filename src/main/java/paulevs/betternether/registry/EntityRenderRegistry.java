package paulevs.betternether.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.entity.EntityType;
import paulevs.betternether.entity.render.RenderChair;
import paulevs.betternether.entity.render.RenderFirefly;
import paulevs.betternether.entity.render.RenderFlyingPig;
import paulevs.betternether.entity.render.RenderHydrogenJellyfish;
import paulevs.betternether.entity.render.RenderNaga;
import paulevs.betternether.entity.render.RenderNagaProjectile;

@Environment(EnvType.CLIENT)
public class EntityRenderRegistry
{
	public static void register()
	{
		registerRenderMob(EntityRegistry.FIREFLY, RenderFirefly.class);
		registerRenderMob(EntityRegistry.CHAIR, RenderChair.class);
		registerRenderMob(EntityRegistry.HYDROGEN_JELLYFISH, RenderHydrogenJellyfish.class);
		registerRenderMob(EntityRegistry.NAGA, RenderNaga.class);
		registerRenderAny(EntityRegistry.NAGA_PROJECTILE, RenderNagaProjectile.class);
		registerRenderMob(EntityRegistry.FLYING_PIG, RenderFlyingPig.class);
	}

	private static void registerRenderMob(EntityType<?> entity, Class<? extends MobEntityRenderer<?, ?>> renderer)
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
	
	private static void registerRenderAny(EntityType<?> entity, Class<? extends EntityRenderer<?>> renderer)
	{
		EntityRendererRegistry.INSTANCE.register(entity, (entityRenderDispatcher, context) -> {
			EntityRenderer<?> render = null;
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
