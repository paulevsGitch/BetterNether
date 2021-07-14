package paulevs.betternether.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import paulevs.betternether.entity.EntityHydrogenJellyfish;

public class ModelEntityHydrogenJellyfish extends AgeableListModel<EntityHydrogenJellyfish> {
	public final static String BODY_TOP = "body_top";

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();

		PartDefinition bodyPart = modelPartData.addOrReplaceChild(
			PartNames.BODY,
			CubeListBuilder.create()
			.texOffs(0, 0)
			.addBox(-14F, 0F, -14F, 28, 18, 28),
			PartPose.offset(0F, -48, 0F)
		);

		bodyPart.addOrReplaceChild(
			BODY_TOP,
			CubeListBuilder.create()
			.texOffs(0, 46)
			.addBox(-10F, 0F, -10F, 20, 6, 20),
			PartPose.offset(0F, -6F, 0F)
		);

		for (int i = 0; i < LEGS; i++) {
			int li = i * 3;
			float angle = (float) (i * Math.PI * 2 / LEGS);
			float x = (float) Math.sin(angle) * 10;
			float z = (float) Math.cos(angle) * 10;

			PartDefinition modelPartData_LEG_1 = bodyPart.addOrReplaceChild(
				"leg_1_"+i, CubeListBuilder.create()
				.texOffs(60, 46)
				.addBox(-3F, 0F, -3F, 6, 14, 6),
				PartPose.offsetAndRotation(x, 18, z, 0, angle, 0)
			);

			modelPartData_LEG_1.addOrReplaceChild("leg_det_"+li,
				CubeListBuilder.create()
				.texOffs(97, 46)
				.addBox(-8F, 0F, 0F, 16, 14, 0)
				.texOffs(97, 30)
				.addBox(0F, 0F, -8F, 0, 14, 16),
				PartPose.offsetAndRotation(0,0,0, 0, angle, 0)
			);

			PartDefinition modelPartData_LEG_2 = modelPartData_LEG_1.addOrReplaceChild(
				"leg_2_"+i,
				CubeListBuilder.create()
				.texOffs(0, 72)
				.addBox(-2F, -24F, -2F, 4, 28, 4),
				PartPose.offset(0F, 14F, 0F)
			);

			li++;
			modelPartData_LEG_2.addOrReplaceChild(
				"leg_det_"+li,
				CubeListBuilder.create()
				.texOffs(98, 60)
				.addBox(-7F, -24F, 0F, 14, 28, 0)
				.texOffs(98, 48)
				.addBox(0F, -24F, -7F, 0, 28, 14),
				PartPose.offsetAndRotation(0,0,0, 0, (float)Math.toRadians(45), 0)
			);

			PartDefinition modelPartData_LEG_3 = modelPartData_LEG_2.addOrReplaceChild(
				"leg_3_"+i,
				CubeListBuilder.create()
				.texOffs(16, 72)
				.addBox(-1F, -24F, -1F, 2, 28, 2),
				PartPose.offset(0F, -28F, 0F)
			);

			li++;
			modelPartData_LEG_3.addOrReplaceChild(
				"leg_det_"+li,
				CubeListBuilder.create()
				.texOffs(99, 88)
				.addBox(-6F, -28F, 0F, 12, 32, 0)
				.texOffs(99, 76)
				.addBox(0F, -28F, -6F, 0, 32, 12),
				PartPose.offsetAndRotation(0,0,0, 0, (float)Math.toRadians(45), 0)
			);

			x = (float) Math.sin(angle);
			z = (float) Math.cos(angle);
			x = Math.signum(x) * (float) Math.pow(Math.abs(x), 0.25F);
			z = Math.signum(z) * (float) Math.pow(Math.abs(z), 0.25F);
			x *= 12;
			z *= 12;

			bodyPart.addOrReplaceChild(
				"wing_"+i, CubeListBuilder.create()
				.texOffs(60, 4)
				.addBox(-12F, 0F, 0F, 24F, 0F, 24F),
				PartPose.offsetAndRotation(x, 12 - (i & 1) * 6, z, 0, angle, 0)
			);
		}
		return LayerDefinition.create(modelData, 128, 128);
	}

	private static final double HALF_PI = Math.PI * 0.5;
	private static final int LEGS = 8;
	private final ModelPart body;
	//private final ModelPart body_top;
	private final ModelPart[] legs_1;
	private final ModelPart[] legs_2;
	private final ModelPart[] legs_3;
	private final ModelPart[] wings;
	//private final ModelPart[] leg_details;

	public ModelEntityHydrogenJellyfish(ModelPart root) {
		this.body = root.getChild(PartNames.BODY);

		legs_1 = new ModelPart[LEGS];
		legs_2 = new ModelPart[LEGS];
		legs_3 = new ModelPart[LEGS];
		wings = new ModelPart[LEGS];

		for (int i = 0; i < LEGS; i++) {
			legs_1[i] = this.body.getChild("leg_1_"+i);
			legs_2[i] = legs_1[i].getChild("leg_2_"+i);
			legs_3[i] = legs_2[i].getChild("leg_3_"+i);
			wings[i] = this.body.getChild("wing_"+i);
		}
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body);
	}

	@Override
	public void setupAnim(EntityHydrogenJellyfish entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		double time = animationProgress * 0.03;

		body.setPos(0, (float) Math.sin(time) * 8 - 42, 0);

		double sin1 = Math.sin(time);
		double sin2 = Math.sin(time + HALF_PI);
		for (int i = 0; i < LEGS; i++) {
			double sinIn = (i & 1) == 0 ? sin1 : sin2;
			double rot = 10 + ((i + 1) & 1) * 10;
			sinIn *= 10;
			legs_1[i].xRot = (float) Math.toRadians(rot + sinIn + 10);
			legs_2[i].xRot = (float) Math.toRadians(180 - rot + sinIn + 5);
			legs_3[i].xRot = (float) Math.toRadians(sinIn);
		}

		time = System.currentTimeMillis() * 0.0006;
		sin1 = Math.sin(time);
		sin2 = Math.sin(time + Math.PI * 0.5);
		for (int i = 0; i < LEGS; i++) {
			double sinIn = (i & 1) == 0 ? sin1 : sin2;
			wings[i].xRot = (float) Math.toRadians(sinIn * 20 - 20);
		}
	}
}
