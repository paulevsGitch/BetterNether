package paulevs.betternether.entity.model;

import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import paulevs.betternether.MHelper;
import paulevs.betternether.entity.EntityJungleSkeleton;
import paulevs.betternether.mixin.client.TexturedModelDataMixin;

import java.util.Random;

public class ModelJungleSkeleton extends SkeletonModel<EntityJungleSkeleton> {
	private static final float ANGLE45 = (float) Math.PI * 0.25F;
	private static final float ANGLE90 = (float) Math.PI * 0.5F;
	private static final Random RANDOM = new Random();
	private static final float BOUND_MIN = ANGLE90 * 2F / 3F;
	private static final float BOUND_MAX = ANGLE90 * 4F / 5F;

	public static LayerDefinition createBodyLayer() {
		LayerDefinition texturedModelData = SkeletonModel.createBodyLayer();
		MeshDefinition modelData = ((TexturedModelDataMixin)texturedModelData).getMesh();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition modelPartData_HEAD = modelPartData.getChild(PartNames.HEAD);

		for (int i = 0; i < 4; i++) {
			float angle = ANGLE45 + (i / 4F) * MHelper.PI2;

			modelPartData_HEAD.addOrReplaceChild("leave_"+i, CubeListBuilder.create()
					.texOffs(24, 0)
					.addBox(-3.0F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F), PartPose.offsetAndRotation((float) -Math.sin(angle), -8, (float) -Math.cos(angle),MHelper.randRange(BOUND_MIN, BOUND_MAX, RANDOM),angle,0));
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