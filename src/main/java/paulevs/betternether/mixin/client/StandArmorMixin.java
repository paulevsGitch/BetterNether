package paulevs.betternether.mixin.client;

import java.util.Iterator;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.betternether.BetterNether;

@Environment(EnvType.CLIENT)
@Mixin(ArmorStandRenderer.class)
public abstract class StandArmorMixin extends LivingEntityRenderer<ArmorStand, ArmorStandArmorModel> {
	public StandArmorMixin(EntityRendererProvider.Context context, ArmorStandArmorModel entityModel, float f) {
		super(context, entityModel, f);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Inject(method = "<init>*", at = @At(value = "RETURN"))
	private void onInit(EntityRendererProvider.Context ctx, CallbackInfo info) {
		if (BetterNether.hasThinArmor()) {
			Iterator<RenderLayer<ArmorStand, ArmorStandArmorModel>> iterator = this.layers.iterator();
			while (iterator.hasNext()) {
				RenderLayer<ArmorStand, ArmorStandArmorModel> feature = iterator.next();
				if (feature instanceof HumanoidArmorLayer) {
					this.layers.remove(feature);
					break;
				}
			}
			this.layers.add(
					0,
					new HumanoidArmorLayer(
							this,
							new ArmorStandArmorModel(ctx.bakeLayer(ModelLayers.PLAYER_SLIM_INNER_ARMOR)),
							new ArmorStandArmorModel(ctx.bakeLayer(ModelLayers.PLAYER_SLIM_OUTER_ARMOR))
					)
			);
		}
	}
}