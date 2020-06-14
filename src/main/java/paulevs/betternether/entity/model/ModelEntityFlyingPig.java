package paulevs.betternether.entity.model;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.math.MathHelper;
import paulevs.betternether.entity.EntityFlyingPig;

public class ModelEntityFlyingPig extends AnimalModel<EntityFlyingPig>
{
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart rightWing;
	private final ModelPart leftWing;
	private final ModelPart rightWingTip;
	private final ModelPart leftWingTip;

	public ModelEntityFlyingPig()
	{
		this.textureWidth = 68;
		this.textureHeight = 64;
		
		this.head = new ModelPart(this, 0, 0);
		this.head.addCuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
		
		ModelPart modelEar = new ModelPart(this, 32, 4);
		modelEar.setPivot(-7.0F, -7.0F, -2.0F);
		modelEar.addCuboid(0.0F, 0.0F, 0.0F, 5.0F, 5.0F, 1.0F);
		this.head.addChild(modelEar);
		
		modelEar = new ModelPart(this, 32, 4);
		modelEar.setPivot(2.0F, -7.0F, -2.0F);
		modelEar.mirror = true;
		modelEar.addCuboid(0.0F, 0.0F, 0.0F, 5.0F, 5.0F, 1.0F);
		this.head.addChild(modelEar);
		
		ModelPart piglet = new ModelPart(this, 32, 0);
		piglet.setPivot(-2.0F, -1.0F, -5.0F);
		piglet.addCuboid(0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 1.0F);
		this.head.addChild(piglet);
		
		this.body = new ModelPart(this, 0, 16);
		this.body.addCuboid(-5.0F, 3.0F, -4.0F, 10.0F, 14.0F, 8.0F);
		
		this.rightWing = new ModelPart(this, 36, 10);
		this.rightWing.setPivot(5, 2.5F, 0);
		this.rightWing.addCuboid(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 0.0F);
		this.body.addChild(this.rightWing);
		
		this.rightWingTip = new ModelPart(this, 36, 26);
		this.rightWingTip.setPivot(16.0F, 0.0F, 0.0F);
		this.rightWingTip.addCuboid(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 0.0F);
		this.rightWing.addChild(this.rightWingTip);
		
		this.leftWing = new ModelPart(this, 36, 10);
		this.leftWing.mirror = true;
		this.leftWing.setPivot(-5, 2.5F, 0);
		this.leftWing.addCuboid(-16.0F, 0.0F, 0.0F, 16.0F, 16.0F, 0.0F);
		this.body.addChild(this.leftWing);
		
		this.leftWingTip = new ModelPart(this, 36, 26);
		this.leftWingTip.mirror = true;
		this.leftWingTip.setPivot(-16.0F, 0.0F, 0.0F);
		this.leftWingTip.addCuboid(-16.0F, 0.0F, 0.0F, 16.0F, 16.0F, 0.0F);
		this.leftWing.addChild(this.leftWingTip);
	}
	   
	@Override
	protected Iterable<ModelPart> getHeadParts()
	{
		return ImmutableList.of(head);
	}

	@Override
	protected Iterable<ModelPart> getBodyParts()
	{
		return ImmutableList.of(body);
	}

	@Override
	public void setAngles(EntityFlyingPig entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch)
	{
		if (entity.isRoosting())
		{
			this.head.pitch = headPitch * 0.017453292F;
			this.head.yaw = 3.1415927F - headYaw * 0.017453292F;
			this.head.roll = 3.1415927F;
			this.head.setPivot(0.0F, 1.0F, 0.0F);
			this.body.pitch = 3.1415927F;
			this.rightWing.pitch = 0;
			this.rightWing.yaw = -1.2566371F;
			this.rightWingTip.yaw = 2.8F;
			this.leftWing.pitch = this.rightWing.pitch;
			this.leftWing.yaw = -this.rightWing.yaw;
			this.leftWingTip.yaw = -this.rightWingTip.yaw;
		}
		else
		{
			this.head.pitch = headPitch * 0.017453292F;
			this.head.yaw = headYaw * 0.017453292F;
			this.head.roll = 0.0F;
			this.head.setPivot(0.0F, 0.0F, 0.0F);
			this.body.pitch = 0.7853982F + MathHelper.cos(animationProgress * 0.1F) * 0.15F;
			this.body.yaw = 0.0F;
			this.rightWing.yaw = MathHelper.cos(animationProgress * 0.4F) * 3.1415927F * 0.25F;
			this.leftWing.yaw = -this.rightWing.yaw;
			this.rightWingTip.yaw = this.rightWing.yaw * 0.75F;
			this.leftWingTip.yaw = -this.rightWing.yaw * 0.75F;
		}
	}
}