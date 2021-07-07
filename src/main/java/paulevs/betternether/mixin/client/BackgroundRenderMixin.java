package paulevs.betternether.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.betternether.BetterNether;

@Mixin(FogRenderer.class)
public class BackgroundRenderMixin {
	@Inject(method = "setupFog", at = @At(value = "HEAD"), cancellable = true)
	private static void applyThickFog(Camera camera, FogRenderer.FogMode fogType, float viewDistance, boolean thickFog, CallbackInfo info) {

		FogType cameraSubmersionType = camera.getFluidInCamera();
		Entity entity = camera.getEntity();
		if (thickFog) {
			if (cameraSubmersionType == FogType.NONE) {
				if (!(entity instanceof LivingEntity && ((LivingEntity) entity).hasEffect(MobEffects.BLINDNESS))) {
					float start = viewDistance * BetterNether.getFogStart();
					float end = viewDistance * BetterNether.getFogEnd();

					RenderSystem.setShaderFogStart(start);
					RenderSystem.setShaderFogEnd(end);
					//RenderSystem.setShaderF(GlStateManager.FogMode.LINEAR);
					//RenderSystem.setupNvFogDistance();
					info.cancel();
				}
			}
		}
	}
}