package paulevs.betternether.entity.model;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import paulevs.betternether.entity.EntityFirefly;

public class ModelEntityFirefly extends AnimalModel<EntityFirefly> {
	private final ModelPart body;
	// private final ModelPart legs;
	private final ModelPart glow;
	private final static String GLOW ="glow";
	private final static String GLOW_PLANE ="glow_plane";

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData modelPartData_BODY = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create()
				.uv(0, 0)
				.cuboid(0F, 0F, 0F, 5, 5, 5), ModelTransform.pivot(-2.5F, 18F, -2.5F));
		/* body = new ModelPart(this, 0, 0);
		body.addCuboid(0F, 0F, 0F, 5, 5, 5);
		body.setPivot(-2.5F, 18F, -2.5F);*/

		modelPartData_BODY.addChild(EntityModelPartNames.TAIL, ModelPartBuilder.create()
				.uv(0, 22)
				.cuboid(0F, 0F, 0F, 3F, 3F, 4F), ModelTransform.pivot(1.0F, 5F, 0.5F));
		/*legs = new ModelPart(this, 0, 22);
		legs.addCuboid(0F, 0F, 0F, 3F, 3F, 4F);
		legs.setPivot(1.0F, 5F, 0.5F);

		body.addChild(legs);*/

		/*modelPartData_BODY.addChild(GLOW, ModelPartBuilder.create()
				.uv(0, 10)
				.cuboid(0F, 0F, 0F, 6F, 6F, 6F), ModelTransform.pivot(-0.5F, -0.5F, -0.5F));*/

		modelPartData.addChild(GLOW_PLANE, ModelPartBuilder.create()
				.uv(0, 32), ModelTransform.NONE)
				.addChild(GLOW, ModelPartBuilder.create()
					.uv(0, 10)
					.cuboid(0F, 0F, 0F, 5F, 5F, 5F, Dilation.NONE, 6.0f/5.0f, 6.0f/5.0f), ModelTransform.pivot(-2.5F, 18F, -2.5F));;
		/*glow = new ModelPart(this, 0, 10);
		glow.addCuboid(0F, 0F, 0F, 6F, 6F, 6F);
		glow.setPivot(-0.5F, -0.5F, -0.5F);

		body.addChild(glow);*/

		/*textureHeight = 64;
		textureWidth = 32;*/
		return TexturedModelData.of(modelData, 32, 64);
	}

	public ModelEntityFirefly(ModelPart root) {
		this.body = root.getChild(EntityModelPartNames.BODY);
		// this.legs = this.body.getChild(EntityModelPartNames.TAIL);
		this.glow = root.getChild(GLOW_PLANE);
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
	public void setAngles(EntityFirefly entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {

	}

	public void syncTransform(){
		this.glow.setTransform(this.body.getTransform());
	}

	public ModelPart getGlowPart(){
		return this.glow;
	}
}