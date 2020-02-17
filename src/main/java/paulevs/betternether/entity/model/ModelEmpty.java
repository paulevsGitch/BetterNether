package paulevs.betternether.entity.model;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import paulevs.betternether.entity.EntityChair;

public class ModelEmpty extends AnimalModel<EntityChair>
{
	@Override
	protected Iterable<ModelPart> getHeadParts()
	{
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelPart> getBodyParts()
	{
		return ImmutableList.of();
	}

	@Override
	public void setAngles(EntityChair entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch)
	{
		
	}
}
