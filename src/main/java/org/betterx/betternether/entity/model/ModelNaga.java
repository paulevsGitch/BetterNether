package org.betterx.betternether.entity.model;

import org.betterx.betternether.entity.EntityNaga;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

import com.google.common.collect.ImmutableList;

public class ModelNaga extends AgeableListModel<EntityNaga> {
    private static final int SPIKE_COUNT = 8;
    private static final int TAIL_COUNT = 4;
    public ModelPart head;
    public ModelPart body;
    public ModelPart[] tail;
    public ModelPart[] spikes;
    private float pitch;
    // private double animation;
    // private long preTime;
    private float maxAngle = 0.1F;

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
                                       -5.0F,
                                       -10.0F,
                                       -5.0F - 2F,
                                       10.0F,
                                       10.0F,
                                       10.0F
                               ),
                PartPose.offset(0.0F, -9.0F, 0.0F)
        );

		/* head = new ModelPart(this, 0, 0);
		head.addCuboid(-5.0F, -10.0F, -5.0F - 2F, 10.0F, 10.0F, 10.0F);
		head.setPivot(0.0F, -9.0F, 0.0F);*/

        PartDefinition modelPartData_BODY = modelPartData.addOrReplaceChild(
                PartNames.BODY,
                CubeListBuilder.create()
                               .texOffs(
                                       40,
                                       0
                               )
                               .addBox(
                                       -2.0F,
                                       0.0F,
                                       -1.0F,
                                       4.0F,
                                       20.0F,
                                       2.0F
                               )
                               .texOffs(
                                       0,
                                       20
                               )
                               .addBox(
                                       -5.0F,
                                       3.0F,
                                       -6.0F,
                                       10.0F,
                                       16.0F,
                                       6.0F
                               ),
                PartPose.offset(0.0F, -10F, 0.0F)
        );
		/*body = new ModelPart(this, 40, 0);
		body.addCuboid(-2.0F, 0.0F, -1.0F, 4.0F, 20.0F, 2.0F);
		body.setTextureOffset(0, 20);
		body.addCuboid(-5.0F, 3.0F, -6.0F, 10.0F, 16.0F, 6.0F);
		body.setPivot(0.0F, -10F, 0.0F*/

        modelPartData_BODY.addOrReplaceChild(
                "spike_0",
                CubeListBuilder.create()
                               .texOffs(33, 25)
                               .addBox(0, 0, 0, 10, 18.0F, 0),
                PartPose.offsetAndRotation(
                        0.0F,
                        0F,
                        0.0F,
                        0,
                        (float) Math.toRadians(-40),
                        0
                )
        );
        //spikes = new ModelPart[8];

		/*spikes[0] = new ModelPart(this, 33, 25);
		spikes[0].addCuboid(0, 0, 0, 10, 18.0F, 0);
		spikes[0].setPivot(0.0F, 0.0F, 0.0F);
		spikes[0].yaw = (float) Math.toRadians(-40);
		body.addChild(spikes[0]);*/

        modelPartData_BODY.addOrReplaceChild(
                "spike_1",
                CubeListBuilder.create()
                               .texOffs(33, 25)
                               .addBox(0, 0, 0, 10, 18.0F, 0),
                PartPose.offsetAndRotation(
                        0.0F,
                        0F,
                        0.0F,
                        0,
                        (float) Math.toRadians(-140),
                        0
                )
        );
		/*spikes[1] = new ModelPart(this, 33, 25);
		spikes[1].addCuboid(0, 0, 0, 10, 18.0F, 0);
		spikes[1].setPivot(0.0F, 0, 0.0F);
		spikes[1].yaw = (float) Math.toRadians(-140);
		body.addChild(spikes[1]);*/

        //ModelPart[] tail = new ModelPart[4];
        float[] tailPitch = {(float) Math.toRadians(45.0f), (float) Math.toRadians(45.0f), 0, 0};
        assert tailPitch.length == TAIL_COUNT;

        PartDefinition tailParent = modelPartData_BODY;
        for (int i = 0; i < TAIL_COUNT; i++) {
            int height = (TAIL_COUNT - i) * 4 / TAIL_COUNT;
            if (height < 2)
                height = 2;
            int width = Math.round((float) height / 2);
            if (width < 1)
                width = 1;

            PartDefinition modelPartData_TAIL = tailParent.addOrReplaceChild(
                    "tail_" + i,
                    CubeListBuilder.create()
                                   .texOffs(40, 0)
                                   .addBox(
                                           -height * 0.5F,
                                           0.0F,
                                           -width * 0.5F,
                                           height,
                                           20.0F,
                                           width
                                   ),
                    PartPose.offsetAndRotation(
                            0.0F,
                            19.0F,
                            0.0F,
                            tailPitch[i],
                            0,
                            0
                    )
            );
            tailParent = modelPartData_TAIL;

			/*tail[i] = new ModelPart(this, 40, 0);
			tail[i].addCuboid(-height * 0.5F, 0.0F, -width * 0.5F, height, 20.0F, width);
			tail[i].setPivot(0.0F, 19.0F, 0.0F);*/

            if (i < TAIL_COUNT - 1) {
                int px = 32 + (12 - height * 3);

                int index = (i << 1) + 2;


                modelPartData_TAIL.addOrReplaceChild(
                        "spike_" + index,
                        CubeListBuilder.create()
                                       .texOffs(px, 22)
                                       .addBox(
                                               0,
                                               0,
                                               0,
                                               height * 3,
                                               20.0F,
                                               0
                                       ),
                        PartPose.offsetAndRotation(
                                0.0F,
                                0F,
                                0.0F,
                                0,
                                (float) Math.toRadians(-60),
                                0
                        )
                );
				/*spikes[index] = new ModelPart(this, px, 22);
				spikes[index].addCuboid(0, 0, 0, height * 3, 20.0F, 0);
				spikes[index].setPivot(0.0F, 0, 0.0F);
				spikes[index].yaw = (float) Math.toRadians(-60);
				tail[i].addChild(spikes[index]);*/

                index++;
                modelPartData_TAIL.addOrReplaceChild(
                        "spike_" + index,
                        CubeListBuilder.create()
                                       .texOffs(px, 22)
                                       .addBox(
                                               0,
                                               0,
                                               0,
                                               height * 3,
                                               20.0F,
                                               0
                                       ),
                        PartPose.offsetAndRotation(
                                0.0F,
                                0F,
                                0.0F,
                                0,
                                (float) Math.toRadians(-120),
                                0
                        )
                );
				/*spikes[index] = new ModelPart(this, px, 22);
				spikes[index].addCuboid(0, 0, 0, height * 3, 20.0F, 0);
				spikes[index].setPivot(0.0F, 0, 0.0F);
				spikes[index].yaw = (float) Math.toRadians(-120);
				tail[i].addChild(spikes[index]);*/
            }

			/*if (i == 0) {
				body.addChild(tail[i]);
			}
			else {
				tail[i - 1].addChild(tail[i]);
			}*/
        }

		/*tail[0].pitch = (float) Math.toRadians(45);
		tail[1].pitch = (float) Math.toRadians(45);*/
		/*textureHeight = 64;
		textureWidth = 64;*/
        return LayerDefinition.create(modelData, 64, 64);
    }

    public ModelNaga(ModelPart root) {
        this.body = root.getChild(PartNames.BODY);
        this.head = root.getChild(PartNames.HEAD);

        this.tail = new ModelPart[TAIL_COUNT];
        this.spikes = new ModelPart[SPIKE_COUNT];

        spikes[0] = this.body.getChild("spike_0");
        spikes[1] = this.body.getChild("spike_1");
        for (int i = 0; i < TAIL_COUNT; i++) {
            if (i == 0) tail[0] = this.body.getChild("tail_0");
            else tail[i] = tail[i - 1].getChild(("tail_" + i));

            if (i < TAIL_COUNT - 1) {
                int index = (i << 1) + 2;
                spikes[index] = tail[i].getChild("spike_" + index);
                index++;
                spikes[index] = tail[i].getChild("spike_" + index);
            }
        }
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void prepareMobModel(EntityNaga livingEntity, float f, float g, float h) {
        this.pitch = livingEntity.getSwimAmount(h);
        super.prepareMobModel(livingEntity, f, g, h);
    }

    @Override
    public void setupAnim(
            EntityNaga entity,
            float limbAngle,
            float limbDistance,
            float animationProgress,
            float headYaw,
            float headPitch
    ) {
        boolean rollTooBig = entity.getFallFlyingTicks() > 4;
        boolean isSwimming = entity.isVisuallySwimming();
        this.head.yRot = headYaw * 0.017453292F;
        if (rollTooBig) {
            this.head.xRot = -0.7853982F;
        } else if (this.pitch > 0.0F) {
            if (isSwimming) {
                this.head.xRot = this.lerpAngle(this.head.xRot, -0.7853982F, this.pitch);
            } else {
                this.head.xRot = this.lerpAngle(this.head.xRot, headPitch * 0.017453292F, this.pitch);
            }
        } else {
            this.head.xRot = headPitch * 0.017453292F;
        }

        // long time = System.currentTimeMillis();
        double speed = (entity.isOnGround() && (entity.getDeltaMovement().x != 0 || entity.getDeltaMovement().z != 0) && !entity.isPassenger())
                ? 6
                : 0.5;
        maxAngle = this.lerpAngle(maxAngle, speed > 1 ? 0.1F : 0.5F, 0.03F);
        // animation += (time - preTime) * speed / 1000.0;
        double animation = animationProgress * speed / 20;
        float angle = (float) Math.sin(animation) * maxAngle * 0.3F;
        float start_angle = angle;
        tail[0].yRot = angle;
        for (int i = 1; i < tail.length; i++) {
            angle = (float) Math.atan(Math.sin(i * 1.7 + animation)) * maxAngle;
            tail[i].zRot = angle - start_angle;
            start_angle += angle;
        }

        for (int i = 0; i < spikes.length; i++) {
            float yaw = ((i & 1) == 0)
                    ? (float) Math.toRadians(-50 + Math.sin(animation * 0.4 + i / 2) * 10)
                    : (float) Math.toRadians(-110 - Math.sin(animation * 0.4 + i / 2) * 10);
            spikes[i].yRot = yaw;
        }

        // preTime = time;
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
