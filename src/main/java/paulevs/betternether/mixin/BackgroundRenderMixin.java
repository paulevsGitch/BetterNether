package paulevs.betternether.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import paulevs.betternether.BetterNether;

@Mixin(BackgroundRenderer.class)
public class BackgroundRenderMixin
{
	@Inject(method = "applyFog", at = @At(value = "HEAD"), cancellable = true)
	private static void applyThickFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo info)
	{
		FluidState fluidState = camera.getSubmergedFluidState();
		Entity entity = camera.getFocusedEntity();
		if (thickFog)
		{
			boolean bl = fluidState.getFluid() != Fluids.EMPTY;
			float o;
			if (bl)
			{
				o = 1.0F;
				if (fluidState.matches(FluidTags.WATER))
				{
					o = 0.05F;
					if (entity instanceof ClientPlayerEntity)
					{
						ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) entity;
						o -= clientPlayerEntity.method_3140() * clientPlayerEntity.method_3140() * 0.03F;
						Biome biome = clientPlayerEntity.world.getBiome(new BlockPos(clientPlayerEntity.getSenseCenterPos()));
						if (biome == Biomes.SWAMP || biome == Biomes.SWAMP_HILLS)
						{
							o += 0.005F;
						}
					}
				}
				else if (fluidState.matches(FluidTags.LAVA))
				{
					o = 2.0F;
				}

				RenderSystem.fogDensity(o);
				RenderSystem.fogMode(GlStateManager.FogMode.EXP2);
			}
			else
			{
				float r;
				if (entity instanceof LivingEntity && ((LivingEntity) entity).hasStatusEffect(StatusEffects.BLINDNESS))
				{
					int i = ((LivingEntity) entity).getStatusEffect(StatusEffects.BLINDNESS).getDuration();
					float g = MathHelper.lerp(Math.min(1.0F, (float) i / 20.0F), viewDistance, 5.0F);
					if (fogType == BackgroundRenderer.FogType.FOG_SKY)
					{
						o = 0.0F;
						r = g * 0.8F;
					}
					else
					{
						o = g * 0.25F;
						r = g;
					}
				}
				else
				{
					o = viewDistance * BetterNether.getFogStart();
					r = viewDistance * BetterNether.getFogEnd();
				}

				RenderSystem.fogStart(o);
				RenderSystem.fogEnd(r);
				RenderSystem.fogMode(GlStateManager.FogMode.LINEAR);
				RenderSystem.setupNvFogDistance();
			}
			info.cancel();
		}
	}
}
