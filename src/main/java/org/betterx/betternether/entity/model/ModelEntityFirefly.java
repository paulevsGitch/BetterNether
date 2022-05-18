package org.betterx.betternether.entity.model;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

import com.google.common.collect.ImmutableList;
import org.betterx.betternether.entity.EntityFirefly;

public class ModelEntityFirefly extends AgeableListModel<EntityFirefly> {
    private final ModelPart body;
    // private final ModelPart legs;
    private final ModelPart glow;
    private final static String GLOW = "glow";
    private final static String GLOW_PLANE = "glow_plane";

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition modelPartData_BODY = modelPartData.addOrReplaceChild(PartNames.BODY,
                                                                            CubeListBuilder.create()
                                                                                           .texOffs(0,
                                                                                                    0)
                                                                                           .addBox(0F,
                                                                                                   0F,
                                                                                                   0F,
                                                                                                   5,
                                                                                                   5,
                                                                                                   5),
                                                                            PartPose.offset(-2.5F, 18F, -2.5F));
		/* body = new ModelPart(this, 0, 0);
		body.addCuboid(0F, 0F, 0F, 5, 5, 5);
		body.setPivot(-2.5F, 18F, -2.5F);*/

        modelPartData_BODY.addOrReplaceChild(PartNames.TAIL,
                                             CubeListBuilder.create()
                                                            .texOffs(0, 22)
                                                            .addBox(0F, 0F, 0F, 3F, 3F, 4F),
                                             PartPose.offset(1.0F, 5F, 0.5F));
		/*legs = new ModelPart(this, 0, 22);
		legs.addCuboid(0F, 0F, 0F, 3F, 3F, 4F);
		legs.setPivot(1.0F, 5F, 0.5F);

		body.addChild(legs);*/

		/*modelPartData_BODY.addChild(GLOW, ModelPartBuilder.create()
				.uv(0, 10)
				.cuboid(0F, 0F, 0F, 6F, 6F, 6F), ModelTransform.pivot(-0.5F, -0.5F, -0.5F));*/

        modelPartData.addOrReplaceChild(GLOW,
                                        CubeListBuilder.create()
                                                       .texOffs(0, 10)
                                                       .addBox(0F, 0F, 0F, 5.2F, 5.2F, 5.2F),
                                        PartPose.offset(-2.6F, 18.1F, -2.6F));
		/*glow = new ModelPart(this, 0, 10);
		glow.addCuboid(0F, 0F, 0F, 6F, 6F, 6F);
		glow.setPivot(-0.5F, -0.5F, -0.5F);

		body.addChild(glow);*/

		/*textureHeight = 64;
		textureWidth = 32;*/
        return LayerDefinition.create(modelData, 32, 64);
    }

    public ModelEntityFirefly(ModelPart root) {
        this.body = root.getChild(PartNames.BODY);
        // this.legs = this.body.getChild(EntityModelPartNames.TAIL);
        this.glow = root.getChild(GLOW);
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
    public void setupAnim(EntityFirefly entity,
                          float limbAngle,
                          float limbDistance,
                          float customAngle,
                          float headYaw,
                          float headPitch) {

    }

    public void syncTransform() {
        this.glow.loadPose(this.body.storePose());
    }

    public ModelPart getGlowPart() {
        return this.glow;
    }
}