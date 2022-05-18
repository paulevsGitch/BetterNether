package org.betterx.betternether.entity.render;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

import com.mojang.blaze3d.vertex.PoseStack;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.entity.EntityHydrogenJellyfish;
import org.betterx.betternether.entity.model.ModelEntityHydrogenJellyfish;
import org.betterx.betternether.registry.EntityRenderRegistry;

public class RenderHydrogenJellyfish extends MobRenderer<EntityHydrogenJellyfish, AgeableListModel<EntityHydrogenJellyfish>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BetterNether.MOD_ID,
                                                                         "textures/entity/jellyfish.png");

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
