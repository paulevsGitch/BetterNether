package org.betterx.betternether.entity.model;

import org.betterx.betternether.entity.EntityFlyingPig;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

import com.google.common.collect.ImmutableList;

public class ModelEntityFlyingPig extends AgeableListModel<EntityFlyingPig> {
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition modelPartData_HEAD = modelPartData.addOrReplaceChild(
                PartNames.HEAD,
                CubeListBuilder.create()
                               .texOffs(
                                       0,
                                       0
                               )
                               .addBox(
                                       -4.0F,
                                       -4.0F,
                                       -4.0F,
                                       8.0F,
                                       8.0F,
                                       8.0F
                               ),
                PartPose.ZERO
        );
		/*this.head = new ModelPart(this, 0, 0);
		this.head.addCuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);*/

        modelPartData_HEAD.addOrReplaceChild(
                PartNames.LEFT_EAR,
                CubeListBuilder.create()
                               .texOffs(32, 4)
                               .addBox(
                                       0.0F,
                                       0.0F,
                                       0.0F,
                                       5.0F,
                                       5.0F,
                                       1.0F
                               ),
                PartPose.offset(-7.0F, -7.0F, -2.0F)
        );
		/*ModelPart modelEar = new ModelPart(this, 32, 4);
		modelEar.setPivot(-7.0F, -7.0F, -2.0F);
		modelEar.addCuboid(0.0F, 0.0F, 0.0F, 5.0F, 5.0F, 1.0F);
		this.head.addChild(modelEar);*/

        modelPartData_HEAD.addOrReplaceChild(
                PartNames.RIGHT_EAR,
                CubeListBuilder.create()
                               .texOffs(32, 4)
                               .mirror(true)
                               .addBox(
                                       0.0F,
                                       0.0F,
                                       0.0F,
                                       5.0F,
                                       5.0F,
                                       1.0F
                               ),
                PartPose.offset(2.0F, -7.0F, -2.0F)
        );
		/*modelEar = new ModelPart(this, 32, 4);
		modelEar.setPivot(2.0F, -7.0F, -2.0F);
		modelEar.mirror = true;
		modelEar.addCuboid(0.0F, 0.0F, 0.0F, 5.0F, 5.0F, 1.0F);
		this.head.addChild(modelEar);*/

        modelPartData_HEAD.addOrReplaceChild(
                PartNames.NOSE,
                CubeListBuilder.create()
                               .texOffs(32, 0)
                               .addBox(0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 1.0F),
                PartPose.offset(-2.0F, -1.0F, -5.0F)
        );
		/*ModelPart piglet = new ModelPart(this, 32, 0);
		piglet.setPivot(-2.0F, -1.0F, -5.0F);
		piglet.addCuboid(0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 1.0F);
		this.head.addChild(piglet);*/

        PartDefinition modelPartData_BODY = modelPartData.addOrReplaceChild(
                PartNames.BODY,
                CubeListBuilder.create()
                               .texOffs(
                                       0,
                                       16
                               )
                               .addBox(
                                       -5.0F,
                                       3.0F,
                                       -4.0F,
                                       10.0F,
                                       14.0F,
                                       8.0F
                               ),
                PartPose.ZERO
        );
		/*this.body = new ModelPart(this, 0, 16);
		this.body.addCuboid(-5.0F, 3.0F, -4.0F, 10.0F, 14.0F, 8.0F);*/

        PartDefinition modelPartData_RW = modelPartData_BODY.addOrReplaceChild(
                PartNames.RIGHT_WING,
                CubeListBuilder.create()
                               .texOffs(36, 10)
                               .addBox(
                                       0.0F,
                                       0.0F,
                                       0.0F,
                                       16.0F,
                                       16.0F,
                                       0.0F
                               ),
                PartPose.offset(5, 2.5F, 0)
        );
		/*this.rightWing = new ModelPart(this, 36, 10);
		this.rightWing.setPivot(5, 2.5F, 0);
		this.rightWing.addCuboid(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 0.0F);
		this.body.addChild(this.rightWing);*/

        modelPartData_RW.addOrReplaceChild(
                PartNames.RIGHT_WING_TIP,
                CubeListBuilder.create()
                               .texOffs(36, 26)
                               .addBox(
                                       0.0F,
                                       0.0F,
                                       0.0F,
                                       16.0F,
                                       16.0F,
                                       0.0F
                               ),
                PartPose.offset(16.0F, 0.0F, 0.0F)
        );
		/*this.rightWingTip = new ModelPart(this, 36, 26);
		this.rightWingTip.setPivot(16.0F, 0.0F, 0.0F);
		this.rightWingTip.addCuboid(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 0.0F);
		this.rightWing.addChild(this.rightWingTip);*/

        PartDefinition modelPartData_LW = modelPartData_BODY.addOrReplaceChild(
                PartNames.LEFT_WING,
                CubeListBuilder.create()
                               .texOffs(36, 10)
                               .mirror(true)
                               .addBox(
                                       -16.0F,
                                       0.0F,
                                       0.0F,
                                       16.0F,
                                       16.0F,
                                       0.0F
                               ),
                PartPose.offset(-5, 2.5F, 0)
        );
		/*this.leftWing = new ModelPart(this, 36, 10);
		this.leftWing.mirror = true;
		this.leftWing.setPivot(-5, 2.5F, 0);
		this.leftWing.addCuboid(-16.0F, 0.0F, 0.0F, 16.0F, 16.0F, 0.0F);
		this.body.addChild(this.leftWing);*/

        modelPartData_LW.addOrReplaceChild(
                PartNames.LEFT_WING_TIP,
                CubeListBuilder.create()
                               .texOffs(36, 26)
                               .mirror(true)
                               .addBox(
                                       -16.0F,
                                       0.0F,
                                       0.0F,
                                       16.0F,
                                       16.0F,
                                       0.0F
                               ),
                PartPose.offset(-16.0F, 0.0F, 0.0F)
        );
		/*this.leftWingTip = new ModelPart(this, 36, 26);
		this.leftWingTip.mirror = true;
		this.leftWingTip.setPivot(-16.0F, 0.0F, 0.0F);
		this.leftWingTip.addCuboid(-16.0F, 0.0F, 0.0F, 16.0F, 16.0F, 0.0F);
		this.leftWing.addChild(this.leftWingTip);*/

        modelPartData_BODY.addOrReplaceChild(PartNames.TAIL, CubeListBuilder.create()
                                                                            .texOffs(0, 40)
                                                                            .addBox(
                                                                                    -4.0F,
                                                                                    0.0F,
                                                                                    0.0F,
                                                                                    8.0F,
                                                                                    8.0F,
                                                                                    0.0F
                                                                            ), PartPose.offset(0, 17, 0));
		/*tail = new ModelPart(this, 0, 40);
		tail.setPivot(0, 17, 0);
		tail.addCuboid(-4.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F);
		this.body.addChild(tail);*/

        modelPartData_BODY.addOrReplaceChild(
                PartNames.RIGHT_LEG,
                CubeListBuilder.create()
                               .texOffs(0, 48)
                               .addBox(
                                       0.0F,
                                       0.0F,
                                       0.0F,
                                       3.0F,
                                       6.0F,
                                       3.0F
                               ),
                PartPose.offset(1.5F, 15, -4)
        );
		/*this.legA = new ModelPart(this, 0, 48);
		legA.setPivot(1.5F, 15, -4);
		legA.addCuboid(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 3.0F);
		this.body.addChild(legA);*/

        modelPartData_BODY.addOrReplaceChild(
                PartNames.LEFT_LEG,
                CubeListBuilder.create()
                               .texOffs(0, 48)
                               .addBox(
                                       0.0F,
                                       0.0F,
                                       0.0F,
                                       3.0F,
                                       6.0F,
                                       3.0F
                               ),
                PartPose.offset(-4.5F, 15, -4)
        );
		/*this.legB = new ModelPart(this, 0, 48);
		legB.setPivot(-4.5F, 15, -4);
		legB.addCuboid(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 3.0F);
		this.body.addChild(legB);

		/*
		this.textureWidth = 128;
		this.textureHeight = 64;
		 */
        return LayerDefinition.create(modelData, 128, 64);
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
        this.head = root.getChild(PartNames.HEAD);
        this.body = root.getChild(PartNames.BODY);
        this.tail = this.body.getChild(PartNames.TAIL);

        this.rightWing = this.body.getChild(PartNames.RIGHT_WING);
        this.rightWingTip = this.rightWing.getChild(PartNames.RIGHT_WING_TIP);
        this.legA = this.body.getChild(PartNames.RIGHT_LEG);

        this.leftWing = this.body.getChild(PartNames.LEFT_WING);
        this.leftWingTip = this.leftWing.getChild(PartNames.LEFT_WING_TIP);
        this.legB = this.body.getChild(PartNames.LEFT_LEG);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(body);
    }

    @Override
    public void setupAnim(
            EntityFlyingPig entity,
            float limbAngle,
            float limbDistance,
            float animationProgress,
            float headYaw,
            float headPitch
    ) {
        if (entity.isRoosting()) {
            this.head.xRot = headPitch * 0.017453292F;
            this.head.yRot = 3.1415927F - headYaw * 0.017453292F;
            this.head.zRot = 3.1415927F;

            this.body.xRot = 3.1415927F;
            this.rightWing.xRot = 0;
            this.rightWing.yRot = -1.2566371F;
            this.rightWingTip.yRot = 2.8F;
            this.leftWing.xRot = this.rightWing.xRot;
            this.leftWing.yRot = -this.rightWing.yRot;
            this.leftWingTip.yRot = -this.rightWingTip.yRot;

            this.head.setPos(0.0F, 25, 0.0F);
            this.body.setPos(0.0F, 24, 0.0F);

            this.legA.xRot = 0;
            this.legB.xRot = 0;
            this.tail.xRot = 0.1F;

            legA.setPos(1.5F, 15, -3);
            legB.setPos(-4.5F, 15, -3);
        } else {
            this.head.xRot = headPitch * 0.017453292F;
            this.head.yRot = headYaw * 0.017453292F;
            this.head.zRot = 0.0F;

            this.body.xRot = 0.7853982F + Mth.cos(animationProgress * 0.1F) * 0.15F;
            this.body.yRot = 0.0F;
            this.rightWing.yRot = Mth.cos(animationProgress * 0.4F) * 3.1415927F * 0.25F;
            this.leftWing.yRot = -this.rightWing.yRot;
            this.rightWingTip.yRot = this.rightWing.yRot * 0.75F;
            this.leftWingTip.yRot = -this.rightWing.yRot * 0.75F;
            this.tail.xRot = Mth.cos(animationProgress * 0.3F) * 0.25F;

            this.legA.xRot = -this.body.xRot + Mth.sin(animationProgress * 0.05F) * 0.1F;
            this.legB.xRot = -this.body.xRot + Mth.cos(animationProgress * 0.05F) * 0.1F;

            this.head.setPos(0.0F, 8.0F, 0.0F);
            this.body.setPos(0.0F, 8.0F, 0.0F);

            legA.setPos(1.5F, 15, -4);
            legB.setPos(-4.5F, 15, -4);
        }
    }
}