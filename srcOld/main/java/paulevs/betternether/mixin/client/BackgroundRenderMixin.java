package paulevs.betternether.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import paulevs.betternether.BetterNether;

@Mixin(BackgroundRenderer.class)
public class BackgroundRenderMixin {
	@Inject(method = "applyFog", at = @At(value = "HEAD"), cancellable = true)
	private static void applyThickFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo info) {
		FluidState fluidState = camera.getSubmergedFluidState();
		Entity entity = camera.getFocusedEntity();
		if (thickFog) {
			if (fluidState.getFluid() == Fluids.EMPTY) {
				if (!(entity instanceof LivingEntity && ((LivingEntity) entity).hasStatusEffect(StatusEffects.BLINDNESS))) {
					float start = viewDistance * BetterNether.getFogStart();
					float end = viewDistance * BetterNether.getFogEnd();

					RenderSystem.fogStart(start);
					RenderSystem.fogEnd(end);
					RenderSystem.fogMode(GlStateManager.FogMode.LINEAR);
					RenderSystem.setupNvFogDistance();
					info.cancel();
				}
			}
		}
	}
}