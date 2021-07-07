package paulevs.betternether.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import paulevs.betternether.entity.EntityChair;

public class ModelEmpty extends AgeableListModel<EntityChair> {
	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of();
	}

	@Override
	public void setAngles(EntityChair entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {

	}
}
