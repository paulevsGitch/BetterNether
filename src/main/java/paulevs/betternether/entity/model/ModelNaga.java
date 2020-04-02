package paulevs.betternether.entity.model;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import paulevs.betternether.entity.EntityNaga;

public class ModelNaga extends AnimalModel<EntityNaga>
{
	public ModelPart head;
	public ModelPart body;
	public ModelPart[] tail;

	public ModelNaga()
	{
		textureHeight = 16;
		textureWidth = 16;
		
		head = new ModelPart(this, 0, 0);
		head.addCuboid(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F);
		head.setPivot(0.0F, -10.0F, -4.0F);
		
		body = new ModelPart(this, 0, 0);
		body.addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 20.0F, 4.0F);
		body.addCuboid(-5.0F, 3.0F, -8.0F, 10.0F, 16.0F, 8.0F);
		body.setPivot(0.0F, -10F, 0.0F);
		
		tail = new ModelPart[4];
		for (int i = 0; i < tail.length; i++)
		{
			int width = (tail.length - i) * 3 / tail.length;
			if (width < 1)
				width = 1;
			
			tail[i] = new ModelPart(this, 0, 0);
			tail[i].addCuboid(-width * 0.5F, 0.0F, -width * 0.5F, width, 20.0F, width);
			tail[i].setPivot(0.0F, 19.0F, 0.0F);
			
			if (i == 0)
			{
				body.addChild(tail[i]);
			}
			else
			{
				tail[i - 1].addChild(tail[i]);
			}
		}
		
		tail[0].pitch = (float) Math.toRadians(45);
		tail[1].pitch = (float) Math.toRadians(45);
	}

	@Override
	protected Iterable<ModelPart> getHeadParts()
	{
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelPart> getBodyParts()
	{
		return ImmutableList.of(body);
	}

	@Override
	public void setAngles(EntityNaga entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch)
	{
		
	}
}
