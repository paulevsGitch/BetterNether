package paulevs.betternether.entity.model;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import paulevs.betternether.entity.EntityFirefly;

public class ModelEntityFirefly extends AnimalModel<EntityFirefly>
{
	private final ModelPart body;
	private final ModelPart legs;
	private final ModelPart glow;
	
	public ModelEntityFirefly()
	{
		textureHeight = 64;
		textureWidth = 32;
		
		body = new ModelPart(this, 0, 0);
		body.addCuboid(0F, 0F, 0F, 5, 5, 5);
		body.setPivot(-2.5F, 18F, -2.5F);
		
		legs = new ModelPart(this, 0, 22);
		legs.addCuboid(0F, 0F, 0F, 3F, 3F, 4F);
		legs.setPivot(1.0F, 5F, 0.5F);
		
		body.addChild(legs);
		
		glow = new ModelPart(this, 0, 10);
		glow.addCuboid(0F, 0F, 0F, 6F, 6F, 6F);
		glow.setPivot(-0.5F, -0.5F, -0.5F);
		
		body.addChild(glow);
	}

	@Override
	protected Iterable<ModelPart> getHeadParts()
	{
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelPart> getBodyParts()
	{
		return ImmutableList.of(this.body);
	}

	@Override
	public void setAngles(EntityFirefly entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch)
	{
		
	}
}