package paulevs.betternether.entity.model;

import java.util.Random;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import paulevs.betternether.MHelper;
import paulevs.betternether.entity.EntityJungleSkeleton;
import paulevs.betternether.mixin.client.TexturedModelDataMixin;

public class ModelJungleSkeleton extends SkeletonEntityModel<EntityJungleSkeleton> {
	private static final float ANGLE45 = (float) Math.PI * 0.25F;
	private static final float ANGLE90 = (float) Math.PI * 0.5F;
	private static final Random RANDOM = new Random();
	private static final float BOUND_MIN = ANGLE90 * 2F / 3F;
	private static final float BOUND_MAX = ANGLE90 * 4F / 5F;

	public static TexturedModelData getTexturedModelData() {
		TexturedModelData texturedModelData = SkeletonEntityModel.getTexturedModelData();
		ModelData modelData = ((TexturedModelDataMixin)texturedModelData).getData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData modelPartData_HEAD = modelPartData.getChild(EntityModelPartNames.HEAD);

		for (int i = 0; i < 4; i++) {
			float angle = ANGLE45 + (i / 4F) * MHelper.PI2;

			modelPartData_HEAD.addChild("leave_"+i, ModelPartBuilder.create()
					.uv(24, 0)
					.cuboid(-3.0F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F), ModelTransform.of((float) -Math.sin(angle), -8, (float) -Math.cos(angle),MHelper.randRange(BOUND_MIN, BOUND_MAX, RANDOM),angle,0));
			/*ModelPart leaf = new ModelPart(this, 24, 0);
			leaf.setPivot((float) -Math.sin(angle), -8, (float) -Math.cos(angle));
			leaf.addCuboid(-3.0F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F);
			leaf.pitch = MHelper.randRange(BOUND_MIN, BOUND_MAX, RANDOM);
			leaf.yaw = angle;
			this.head.addChild(leaf);*/
		}
		return texturedModelData;
	}

	public ModelJungleSkeleton(ModelPart modelPart) {
		super(modelPart);
	}
}