package paulevs.betternether.entity.model;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import paulevs.betternether.entity.EntityHydrogenJellyfish;

public class ModelEntityHydrogenJellyfish extends AnimalModel<EntityHydrogenJellyfish> {
	public final static String BODY_TOP = "body_top";

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData bodyPart = modelPartData.addChild(
			EntityModelPartNames.BODY,
			ModelPartBuilder.create()
			.uv(0, 0)
			.cuboid(-14F, 0F, -14F, 28, 18, 28),
			ModelTransform.pivot(0F, -48, 0F)
		);

		bodyPart.addChild(
			BODY_TOP,
			ModelPartBuilder.create()
			.uv(0, 46)
			.cuboid(-10F, 0F, -10F, 20, 6, 20),
			ModelTransform.pivot(0F, -6F, 0F)
		);

		for (int i = 0; i < LEGS; i++) {
			int li = i * 3;
			float angle = (float) (i * Math.PI * 2 / LEGS);
			float x = (float) Math.sin(angle) * 10;
			float z = (float) Math.cos(angle) * 10;

			ModelPartData modelPartData_LEG_1 = bodyPart.addChild(
				"leg_1_"+i, ModelPartBuilder.create()
				.uv(60, 46)
				.cuboid(-3F, 0F, -3F, 6, 14, 6),
				ModelTransform.of(x, 18, z, 0, angle, 0)
			);

			modelPartData_LEG_1.addChild("leg_det_"+li,
				ModelPartBuilder.create()
				.uv(97, 46)
				.cuboid(-8F, 0F, 0F, 16, 14, 0)
				.uv(97, 30)
				.cuboid(0F, 0F, -8F, 0, 14, 16),
				ModelTransform.of(0,0,0, 0, angle, 0)
			);

			ModelPartData modelPartData_LEG_2 = modelPartData_LEG_1.addChild(
				"leg_2_"+i,
				ModelPartBuilder.create()
				.uv(0, 72)
				.cuboid(-2F, -24F, -2F, 4, 28, 4),
				ModelTransform.pivot(0F, 14F, 0F)
			);

			li++;
			modelPartData_LEG_2.addChild(
				"leg_det_"+li,
				ModelPartBuilder.create()
				.uv(98, 60)
				.cuboid(-7F, -24F, 0F, 14, 28, 0)
				.uv(98, 48)
				.cuboid(0F, -24F, -7F, 0, 28, 14),
				ModelTransform.of(0,0,0, 0, (float)Math.toRadians(45), 0)
			);

			ModelPartData modelPartData_LEG_3 = modelPartData_LEG_2.addChild(
				"leg_3_"+i,
				ModelPartBuilder.create()
				.uv(16, 72)
				.cuboid(-1F, -24F, -1F, 2, 28, 2),
				ModelTransform.pivot(0F, -28F, 0F)
			);

			li++;
			modelPartData_LEG_3.addChild(
				"leg_det_"+li,
				ModelPartBuilder.create()
				.uv(99, 88)
				.cuboid(-6F, -28F, 0F, 12, 32, 0)
				.uv(99, 76)
				.cuboid(0F, -28F, -6F, 0, 32, 12),
				ModelTransform.of(0,0,0, 0, (float)Math.toRadians(45), 0)
			);

			x = (float) Math.sin(angle);
			z = (float) Math.cos(angle);
			x = Math.signum(x) * (float) Math.pow(Math.abs(x), 0.25F);
			z = Math.signum(z) * (float) Math.pow(Math.abs(z), 0.25F);
			x *= 12;
			z *= 12;

			bodyPart.addChild(
				"wing_"+i, ModelPartBuilder.create()
				.uv(60, 4)
				.cuboid(-12F, 0F, 0F, 24F, 0F, 24F),
				ModelTransform.of(x, 12 - (i & 1) * 6, z, 0, angle, 0)
			);
		}
		return TexturedModelData.of(modelData, 128, 128);
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
		this.body = root.getChild(EntityModelPartNames.BODY);

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
	protected Iterable<ModelPart> getHeadParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelPart> getBodyParts() {
		return ImmutableList.of(this.body);
	}

	@Override
	public void setAngles(EntityHydrogenJellyfish entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		double time = animationProgress * 0.03;

		body.setPivot(0, (float) Math.sin(time) * 8 - 42, 0);

		double sin1 = Math.sin(time);
		double sin2 = Math.sin(time + HALF_PI);
		for (int i = 0; i < LEGS; i++) {
			double sinIn = (i & 1) == 0 ? sin1 : sin2;
			double rot = 10 + ((i + 1) & 1) * 10;
			sinIn *= 10;
			legs_1[i].pitch = (float) Math.toRadians(rot + sinIn + 10);
			legs_2[i].pitch = (float) Math.toRadians(180 - rot + sinIn + 5);
			legs_3[i].pitch = (float) Math.toRadians(sinIn);
		}

		time = System.currentTimeMillis() * 0.0006;
		sin1 = Math.sin(time);
		sin2 = Math.sin(time + Math.PI * 0.5);
		for (int i = 0; i < LEGS; i++) {
			double sinIn = (i & 1) == 0 ? sin1 : sin2;
			wings[i].pitch = (float) Math.toRadians(sinIn * 20 - 20);
		}
	}
}
