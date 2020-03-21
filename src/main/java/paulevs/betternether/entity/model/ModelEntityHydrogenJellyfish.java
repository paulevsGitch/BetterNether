package paulevs.betternether.entity.model;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import paulevs.betternether.entity.EntityHydrogenJellyfish;

public class ModelEntityHydrogenJellyfish extends AnimalModel<EntityHydrogenJellyfish>
{
	private final ModelPart body;
	private final ModelPart body_top;
	//private final ModelPart bottom;
	//private final ModelPart bottom_45;
	//private final ModelPart[] legs_1;
	//private final ModelPart[] legs_2;
	//private final ModelPart[] legs_3;
	private final ModelPart[] legs;
	
	public ModelEntityHydrogenJellyfish()
	{
		textureHeight = 16;
		textureWidth = 16;
		
		body = new ModelPart(this, 0, 0);
		body.addCuboid(-7F, -6F, -7F, 14, 9, 14);
		body.setPivot(0F, 0F, 0F);
		
		body_top = new ModelPart(this, 0, 0);
		body_top.addCuboid(-5F, -9F, -5F, 10, 3, 10);
		body_top.setPivot(0F, 0F, 0F);
		body.addChild(body_top);
		
		/*bottom = new ModelPart(this, 0, 0);
		bottom.addCuboid(-6F, 2F, -6F, 12, 20, 12);
		bottom.setPivot(0F, 0F, 0F);
		body.addChild(bottom);
		
		bottom_45 = new ModelPart(this, 0, 0);
		bottom_45.addCuboid(-6F, 2F, -6F, 12, 20, 12);
		bottom_45.setPivot(0F, 0F, 0F);
		body.addChild(bottom_45);*/
		
		/*legs_1 = new ModelPart[8];
		legs_2 = new ModelPart[8];
		legs_3 = new ModelPart[8];
		for (int i = 0; i < 8; i++)
		{
			legs_1[i] = new ModelPart(this, 0, 0);
			legs_1[i].addCuboid(-2F, -8F, -2F, 4, 8, 4);
			legs_1[i].setPivot(0F, 0F, 0F);
			body.addChild(legs_1[i]);
			
			legs_2[i] = new ModelPart(this, 0, 0);
			legs_2[i].addCuboid(-3F, -8F, -3F, 6, 8, 6);
			legs_2[i].setPivot(0F, -8F, 0F);
			legs_1[i].addChild(legs_2[i]);
			
			legs_3[i] = new ModelPart(this, 0, 0);
			legs_3[i].addCuboid(-2F, -8F, -2F, 4, 8, 4);
			legs_3[i].setPivot(0F, -8F, 0F);
			legs_2[i].addChild(legs_3[i]);
		}*/
		
		legs = new ModelPart[8];
		for (int i = 0; i < 8; i++)
		{
			legs[i] = new ModelPart(this, 0, 0);
			legs[i].addCuboid(-2F, -8F, -2F, 4, 8, 4);
		}
	}
	
	@Override
	protected Iterable<ModelPart> getHeadParts()
	{
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelPart> getBodyParts()
	{
		return ImmutableList.of(this.body);
	}

	@Override
	public void setAngles(EntityHydrogenJellyfish entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch)
	{
		//bottom_45.yaw = (float) Math.toRadians(45);
		/*for (int i = 0; i < 8; i++)
		{
			legs_1[i].yaw = (float) (i * Math.PI * 2 / 8.0);
			legs_1[i].pitch = (float) Math.toRadians(90 + 45 - 20 - (i & 1) * 5);
			legs_2[i].pitch = (float) Math.toRadians(45 + 20);
			legs_3[i].pitch = (float) Math.toRadians(45);
			//legs_1[i].pitch = (float) Math.toRadians(45);
			
			//legs_2[i].roll = (float) Math.toRadians(90);
		}*/
	}
}
