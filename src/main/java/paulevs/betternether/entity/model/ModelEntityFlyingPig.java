package paulevs.betternether.entity.model;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.math.MathHelper;
import paulevs.betternether.entity.EntityFlyingPig;

public class ModelEntityFlyingPig extends AnimalModel<EntityFlyingPig> {
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData modelPartData_HEAD = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create()
				.uv(0, 0)
				.cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), ModelTransform.NONE);
		/*this.head = new ModelPart(this, 0, 0);
		this.head.addCuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);*/

		modelPartData_HEAD.addChild(EntityModelPartNames.LEFT_EAR, ModelPartBuilder.create()
				.uv(32, 4)
				.cuboid(0.0F, 0.0F, 0.0F, 5.0F, 5.0F, 1.0F), ModelTransform.pivot(-7.0F, -7.0F, -2.0F));
		/*ModelPart modelEar = new ModelPart(this, 32, 4);
		modelEar.setPivot(-7.0F, -7.0F, -2.0F);
		modelEar.addCuboid(0.0F, 0.0F, 0.0F, 5.0F, 5.0F, 1.0F);
		this.head.addChild(modelEar);*/

		modelPartData_HEAD.addChild(EntityModelPartNames.RIGHT_EAR, ModelPartBuilder.create()
				.uv(32, 4)
				.mirrored(true)
				.cuboid(0.0F, 0.0F, 0.0F, 5.0F, 5.0F, 1.0F), ModelTransform.pivot(2.0F, -7.0F, -2.0F));
		/*modelEar = new ModelPart(this, 32, 4);
		modelEar.setPivot(2.0F, -7.0F, -2.0F);
		modelEar.mirror = true;
		modelEar.addCuboid(0.0F, 0.0F, 0.0F, 5.0F, 5.0F, 1.0F);
		this.head.addChild(modelEar);*/

		modelPartData_HEAD.addChild(EntityModelPartNames.NOSE, ModelPartBuilder.create()
				.uv(32, 0)
				.cuboid(0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 1.0F), ModelTransform.pivot(-2.0F, -1.0F, -5.0F));
		/*ModelPart piglet = new ModelPart(this, 32, 0);
		piglet.setPivot(-2.0F, -1.0F, -5.0F);
		piglet.addCuboid(0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 1.0F);
		this.head.addChild(piglet);*/

		ModelPartData modelPartData_BODY = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create()
				.uv(0, 16)
				.cuboid(-5.0F, 3.0F, -4.0F, 10.0F, 14.0F, 8.0F), ModelTransform.NONE);
		/*this.body = new ModelPart(this, 0, 16);
		this.body.addCuboid(-5.0F, 3.0F, -4.0F, 10.0F, 14.0F, 8.0F);*/

		ModelPartData modelPartData_RW = modelPartData_BODY.addChild(EntityModelPartNames.RIGHT_WING, ModelPartBuilder.create()
				.uv(36, 10)
				.cuboid(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 0.0F), ModelTransform.pivot(5, 2.5F, 0));
		/*this.rightWing = new ModelPart(this, 36, 10);
		this.rightWing.setPivot(5, 2.5F, 0);
		this.rightWing.addCuboid(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 0.0F);
		this.body.addChild(this.rightWing);*/

		modelPartData_RW.addChild(EntityModelPartNames.RIGHT_WING_TIP, ModelPartBuilder.create()
				.uv(36, 26)
				.cuboid(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 0.0F), ModelTransform.pivot(16.0F, 0.0F, 0.0F));
		/*this.rightWingTip = new ModelPart(this, 36, 26);
		this.rightWingTip.setPivot(16.0F, 0.0F, 0.0F);
		this.rightWingTip.addCuboid(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 0.0F);
		this.rightWing.addChild(this.rightWingTip);*/

		ModelPartData modelPartData_LW = modelPartData_BODY.addChild(EntityModelPartNames.LEFT_WING, ModelPartBuilder.create()
				.uv(36, 10)
				.mirrored(true)
				.cuboid(-16.0F, 0.0F, 0.0F, 16.0F, 16.0F, 0.0F), ModelTransform.pivot(-5, 2.5F, 0));
		/*this.leftWing = new ModelPart(this, 36, 10);
		this.leftWing.mirror = true;
		this.leftWing.setPivot(-5, 2.5F, 0);
		this.leftWing.addCuboid(-16.0F, 0.0F, 0.0F, 16.0F, 16.0F, 0.0F);
		this.body.addChild(this.leftWing);*/

		modelPartData_LW.addChild(EntityModelPartNames.LEFT_WING_TIP, ModelPartBuilder.create()
				.uv(36, 26)
				.mirrored(true)
				.cuboid(-16.0F, 0.0F, 0.0F, 16.0F, 16.0F, 0.0F), ModelTransform.pivot(-16.0F, 0.0F, 0.0F));
		/*this.leftWingTip = new ModelPart(this, 36, 26);
		this.leftWingTip.mirror = true;
		this.leftWingTip.setPivot(-16.0F, 0.0F, 0.0F);
		this.leftWingTip.addCuboid(-16.0F, 0.0F, 0.0F, 16.0F, 16.0F, 0.0F);
		this.leftWing.addChild(this.leftWingTip);*/

		modelPartData_BODY.addChild(EntityModelPartNames.TAIL, ModelPartBuilder.create()
				.uv(0, 40)
				.cuboid(-4.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F), ModelTransform.pivot(0, 17, 0));
		/*tail = new ModelPart(this, 0, 40);
		tail.setPivot(0, 17, 0);
		tail.addCuboid(-4.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F);
		this.body.addChild(tail);*/

		modelPartData_BODY.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create()
				.uv(0, 48)
				.cuboid(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 3.0F), ModelTransform.pivot(1.5F, 15, -4));
		/*this.legA = new ModelPart(this, 0, 48);
		legA.setPivot(1.5F, 15, -4);
		legA.addCuboid(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 3.0F);
		this.body.addChild(legA);*/

		modelPartData_BODY.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create()
				.uv(0, 48)
				.cuboid(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 3.0F), ModelTransform.pivot(-4.5F, 15, -4));
		/*this.legB = new ModelPart(this, 0, 48);
		legB.setPivot(-4.5F, 15, -4);
		legB.addCuboid(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 3.0F);
		this.body.addChild(legB);

		/*
		this.textureWidth = 128;
		this.textureHeight = 64;
		 */
		return TexturedModelData.of(modelData, 128, 64);
	}

	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart rightWing;
	private final ModelPart leftWing;
	private final ModelPart rightWingTip;
	private final ModelPart leftWingTip;
	private final ModelPart tail;
	private final ModelPart legA;
	private final ModelPart legB;

	public ModelEntityFlyingPig(ModelPart root) {
		this.head = root.getChild(EntityModelPartNames.HEAD);
		this.body = root.getChild(EntityModelPartNames.BODY);
		this.tail = this.body.getChild(EntityModelPartNames.TAIL);

		this.rightWing = this.body.getChild(EntityModelPartNames.RIGHT_WING);
		this.rightWingTip = this.rightWing.getChild(EntityModelPartNames.RIGHT_WING_TIP);
		this.legA = this.body.getChild(EntityModelPartNames.RIGHT_LEG);

		this.leftWing = this.body.getChild(EntityModelPartNames.LEFT_WING);
		this.leftWingTip = this.rightWing.getChild(EntityModelPartNames.LEFT_WING_TIP);
		this.legB = this.body.getChild(EntityModelPartNames.LEFT_LEG);
	}

	@Override
	protected Iterable<ModelPart> getHeadParts() {
		return ImmutableList.of(head);
	}

	@Override
	protected Iterable<ModelPart> getBodyParts() {
		return ImmutableList.of(body);
	}

	@Override
	public void setAngles(EntityFlyingPig entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		if (entity.isRoosting()) {
			this.head.pitch = headPitch * 0.017453292F;
			this.head.yaw = 3.1415927F - headYaw * 0.017453292F;
			this.head.roll = 3.1415927F;

			this.body.pitch = 3.1415927F;
			this.rightWing.pitch = 0;
			this.rightWing.yaw = -1.2566371F;
			this.rightWingTip.yaw = 2.8F;
			this.leftWing.pitch = this.rightWing.pitch;
			this.leftWing.yaw = -this.rightWing.yaw;
			this.leftWingTip.yaw = -this.rightWingTip.yaw;

			this.head.setPivot(0.0F, 25, 0.0F);
			this.body.setPivot(0.0F, 24, 0.0F);

			this.legA.pitch = 0;
			this.legB.pitch = 0;
			this.tail.pitch = 0.1F;

			legA.setPivot(1.5F, 15, -3);
			legB.setPivot(-4.5F, 15, -3);
		}
		else {
			this.head.pitch = headPitch * 0.017453292F;
			this.head.yaw = headYaw * 0.017453292F;
			this.head.roll = 0.0F;

			this.body.pitch = 0.7853982F + MathHelper.cos(animationProgress * 0.1F) * 0.15F;
			this.body.yaw = 0.0F;
			this.rightWing.yaw = MathHelper.cos(animationProgress * 0.4F) * 3.1415927F * 0.25F;
			this.leftWing.yaw = -this.rightWing.yaw;
			this.rightWingTip.yaw = this.rightWing.yaw * 0.75F;
			this.leftWingTip.yaw = -this.rightWing.yaw * 0.75F;
			this.tail.pitch = MathHelper.cos(animationProgress * 0.3F) * 0.25F;

			this.legA.pitch = -this.body.pitch + MathHelper.sin(animationProgress * 0.05F) * 0.1F;
			this.legB.pitch = -this.body.pitch + MathHelper.cos(animationProgress * 0.05F) * 0.1F;

			this.head.setPivot(0.0F, 8.0F, 0.0F);
			this.body.setPivot(0.0F, 8.0F, 0.0F);

			legA.setPivot(1.5F, 15, -4);
			legB.setPivot(-4.5F, 15, -4);
		}
	}
}