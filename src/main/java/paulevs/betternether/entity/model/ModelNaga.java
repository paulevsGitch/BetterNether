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
	public ModelPart[] spikes;
	private float pitch;
	//private double animation;
	//private long preTime;
	private float maxAngle = 0.1F;

	public ModelNaga()
	{
		textureHeight = 64;
		textureWidth = 64;
		
		head = new ModelPart(this, 0, 0);
		head.addCuboid(-5.0F, -10.0F, -5.0F - 2F, 10.0F, 10.0F, 10.0F);
		head.setPivot(0.0F, -9.0F, 0.0F);
		
		body = new ModelPart(this, 40, 0);
		body.addCuboid(-2.0F, 0.0F, -1.0F, 4.0F, 20.0F, 2.0F);
		body.setTextureOffset(0, 20);
		body.addCuboid(-5.0F, 3.0F, -6.0F, 10.0F, 16.0F, 6.0F);
		body.setPivot(0.0F, -10F, 0.0F);
		
		spikes = new ModelPart[8];
		
		spikes[0] = new ModelPart(this, 33, 25);
		spikes[0].addCuboid(0, 0, 0, 10, 18.0F, 0);
		spikes[0].setPivot(0.0F, 0.0F, 0.0F);
		spikes[0].yaw = (float) Math.toRadians(-40);
		body.addChild(spikes[0]);
		
		spikes[1] = new ModelPart(this, 33, 25);
		spikes[1].addCuboid(0, 0, 0, 10, 18.0F, 0);
		spikes[1].setPivot(0.0F, 0, 0.0F);
		spikes[1].yaw = (float) Math.toRadians(-140);
		body.addChild(spikes[1]);
		
		tail = new ModelPart[4];
		
		int last = tail.length - 1;
		for (int i = 0; i < tail.length; i++)
		{
			int height = (tail.length - i) * 4 / tail.length;
			if (height < 2)
				height = 2;
			int width = Math.round((float) height / 2);
			if (width < 1)
				width = 1;
			
			tail[i] = new ModelPart(this, 40, 0);
			tail[i].addCuboid(-height * 0.5F, 0.0F, -width * 0.5F, height, 20.0F, width);
			tail[i].setPivot(0.0F, 19.0F, 0.0F);
			
			if (i < last)
			{
				int px = 32 + (12 - height * 3);
				
				int index = (i << 1) + 2;
				spikes[index] = new ModelPart(this, px, 22);
				spikes[index].addCuboid(0, 0, 0, height * 3, 20.0F, 0);
				spikes[index].setPivot(0.0F, 0, 0.0F);
				spikes[index].yaw = (float) Math.toRadians(-60);
				tail[i].addChild(spikes[index]);
				
				index ++;
				spikes[index] = new ModelPart(this, px, 22);
				spikes[index].addCuboid(0, 0, 0, height * 3, 20.0F, 0);
				spikes[index].setPivot(0.0F, 0, 0.0F);
				spikes[index].yaw = (float) Math.toRadians(-120);
				tail[i].addChild(spikes[index]);
			}
			
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
	public void animateModel(EntityNaga livingEntity, float f, float g, float h)
	{
		this.pitch = livingEntity.getLeaningPitch(h);
		super.animateModel(livingEntity, f, g, h);
	}

	@Override
	public void setAngles(EntityNaga entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch)
	{
		boolean rollTooBig = entity.getRoll() > 4;
		boolean isSwimming = entity.isInSwimmingPose();
		this.head.yaw = headYaw * 0.017453292F;
		if (rollTooBig)
		{
			this.head.pitch = -0.7853982F;
		}
		else if (this.pitch > 0.0F)
		{
			if (isSwimming)
			{
				this.head.pitch = this.lerpAngle(this.head.pitch, -0.7853982F, this.pitch);
			}
			else
			{
				this.head.pitch = this.lerpAngle(this.head.pitch, headPitch * 0.017453292F, this.pitch);
			}
		}
		else
		{
			this.head.pitch = headPitch * 0.017453292F;
		}
		
		//long time = System.currentTimeMillis();
		double speed = (entity.isOnGround() && (entity.getVelocity().x != 0 || entity.getVelocity().z != 0) && !entity.hasVehicle()) ? 6 : 0.5;
		maxAngle = this.lerpAngle(maxAngle, speed > 1 ? 0.1F : 0.5F, 0.03F);
		//animation += (time - preTime) * speed / 1000.0;
		double animation = animationProgress * speed / 20;
		float angle = (float) Math.sin(animation) * maxAngle * 0.3F;
		float start_angle = angle;
		tail[0].yaw = angle;
		for (int i = 1; i < tail.length; i++)
		{
			angle = (float) Math.atan(Math.sin(i * 1.7 + animation)) * maxAngle;
			tail[i].roll = angle - start_angle;
			start_angle += angle;
		}
		
		for (int i = 0; i < spikes.length; i++)
		{
			float yaw = ((i & 1) == 0) ? (float) Math.toRadians(-50 + Math.sin(animation * 0.4 + i / 2) * 10) : (float) Math.toRadians(-110 - Math.sin(animation * 0.4 + i / 2) * 10);
			spikes[i].yaw = yaw;
		}
		
		//preTime = time;
	}

	protected float lerpAngle(float from, float to, float position)
	{
		float angle = (to - from) % 6.2831855F;

		if (angle < -3.1415927F)
		{
			angle += 6.2831855F;
		}

		if (angle >= 3.1415927F)
		{
			angle -= 6.2831855F;
		}

		return from + position * angle;
	}
}
