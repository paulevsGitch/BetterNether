package paulevs.betternether.entity.model;

import java.util.Random;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import paulevs.betternether.MHelper;
import paulevs.betternether.entity.EntityJungleSkeleton;

public class ModelJungleSkeleton extends SkeletonEntityModel<EntityJungleSkeleton> {
	private static final float ANGLE45 = (float) Math.PI * 0.25F;
	private static final float ANGLE90 = (float) Math.PI * 0.5F;
	private static final Random RANDOM = new Random();
	private static final float BOUND_MIN = ANGLE90 * 2F / 3F;
	private static final float BOUND_MAX = ANGLE90 * 4F / 5F;

	public ModelJungleSkeleton() {
		super(0.0F, false);

		for (int i = 0; i < 4; i++) {
			float angle = ANGLE45 + (i / 4F) * MHelper.PI2;
			ModelPart leaf = new ModelPart(this, 24, 0);
			leaf.setPivot((float) -Math.sin(angle), -8, (float) -Math.cos(angle));
			leaf.addCuboid(-3.0F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F);
			leaf.pitch = MHelper.randRange(BOUND_MIN, BOUND_MAX, RANDOM);
			leaf.yaw = angle;
			this.head.addChild(leaf);
		}
	}
}