package paulevs.betternether.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.betternether.BetterNether;

import java.util.Iterator;

@Environment(EnvType.CLIENT)
@Mixin(PlayerRenderer.class)
public abstract class PlayerArmorMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
	public PlayerArmorMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> entityModel, float f) {
		super(context, entityModel, f);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Inject(method = "<init>*", at = @At(value = "RETURN"))
	private void bcl_onInit(EntityRendererProvider.Context context, boolean bl, CallbackInfo info) {
		if (BetterNether.hasThinArmor()) {
			for (RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> feature : this.layers) {
				if (feature instanceof HumanoidArmorLayer) {
					this.layers.remove(feature);
					break;
				}
			}
			this.layers.add(
					0,
					new HumanoidArmorLayer(
							this,
							new ArmorStandArmorModel(context.bakeLayer(ModelLayers.PLAYER_SLIM_INNER_ARMOR)),
							new ArmorStandArmorModel(context.bakeLayer(ModelLayers.PLAYER_SLIM_OUTER_ARMOR))
					)
			);
		}
	}
}