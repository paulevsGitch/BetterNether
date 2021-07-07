package paulevs.betternether.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityHydrogenJellyfish;
import paulevs.betternether.entity.model.ModelEntityHydrogenJellyfish;
import paulevs.betternether.registry.EntityRenderRegistry;

public class RenderHydrogenJellyfish extends MobRenderer<EntityHydrogenJellyfish, AgeableListModel<EntityHydrogenJellyfish>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterNether.MOD_ID, "textures/entity/jellyfish.png");

	public RenderHydrogenJellyfish(EntityRendererProvider.Context ctx) {
		super(ctx, new ModelEntityHydrogenJellyfish(ctx.bakeLayer(EntityRenderRegistry.HYDROGEN_JELLYFISH_MODEL)), 1);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityHydrogenJellyfish entity) {
		return TEXTURE;
	}

	@Override
	protected int getBlockLightLevel(EntityHydrogenJellyfish entity, BlockPos pos) {
		return 15;
	}

	@Override
	protected void scale(EntityHydrogenJellyfish entity, PoseStack matrixStack, float f) {
		float scale = entity.getScale();
		matrixStack.scale(scale, scale, scale);
	}
}
