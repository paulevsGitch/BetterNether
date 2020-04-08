package paulevs.betternether.entity.render;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.util.Identifier;
import paulevs.betternether.entity.EntityNagaProjectile;

public class RenderNagaProjectile extends EntityRenderer<EntityNagaProjectile>
{
	private static final Identifier TEXTURE = new Identifier("minecraft:textures/block/stone.png");
	
	public RenderNagaProjectile(EntityRenderDispatcher renderManager)
	{
		super(renderManager);
	}

	@Override
	public Identifier getTexture(EntityNagaProjectile entity)
	{
		return TEXTURE;
	}
}