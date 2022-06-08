package org.betterx.betternether.entity.model;

import org.betterx.betternether.entity.EntitySkull;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

import com.google.common.collect.ImmutableList;

public class ModelSkull extends AgeableListModel<EntitySkull> {
    private final ModelPart head;
    private float pitch;

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addOrReplaceChild(
                PartNames.HEAD,
                CubeListBuilder.create()
                               .texOffs(0, 0)
                               .addBox(-4, -4, -4, 8, 8, 8),
                PartPose.offset(0, 20, 0)
        );
		/*head = ModelPart new (this, 0, 0);
		head.setPivot(0, 20, 0);
		head.addCuboid(-4, -4, -4, 8, 8, 8);*/

		/* textureHeight = 16;
		textureWidth = 32; */
        return LayerDefinition.create(modelData, 32, 16);
    }

    public ModelSkull(ModelPart root) {
        this.head = root.getChild(PartNames.HEAD);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of();
    }

    @Override
    public void prepareMobModel(EntitySkull livingEntity, float f, float g, float h) {
        this.pitch = livingEntity.getSwimAmount(h);
        super.prepareMobModel(livingEntity, f, g, h);
    }

    @Override
    public void setupAnim(
            EntitySkull entity,
            float limbAngle,
            float limbDistance,
            float animationProgress,
            float headYaw,
            float headPitch
    ) {
        // head.pitch = (float) Math.toRadians(headPitch);

        boolean rollTooBig = entity.getFallFlyingTicks() > 4;
        this.head.yRot = headYaw * 0.017453292F;
        if (rollTooBig) {
            this.head.xRot = -0.7853982F;
        } else if (this.pitch > 0.0F) {
            this.head.xRot = this.lerpAngle(this.head.xRot, headPitch * 0.017453292F, this.pitch);
        } else {
            this.head.xRot = headPitch * 0.017453292F;
        }
    }

    protected float lerpAngle(float from, float to, float position) {
        float angle = (to - from) % 6.2831855F;

        if (angle < -3.1415927F) {
            angle += 6.2831855F;
        }

        if (angle >= 3.1415927F) {
            angle -= 6.2831855F;
        }

        return from + position * angle;
    }
}
