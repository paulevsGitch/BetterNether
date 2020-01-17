package paulevs.betternether.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.ArmorStandEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorBipedFeatureRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.ArmorStandArmorEntityModel;
import net.minecraft.entity.decoration.ArmorStandEntity;
import paulevs.betternether.BetterNether;

@Environment(EnvType.CLIENT)
@Mixin(ArmorStandEntityRenderer.class)
public abstract class StandArmorMixin extends LivingEntityRenderer<ArmorStandEntity, ArmorStandArmorEntityModel>
{
	public StandArmorMixin(EntityRenderDispatcher entityRenderDispatcher, ArmorStandArmorEntityModel entityModel, float f)
	{
		super(entityRenderDispatcher, entityModel, f);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Inject(method = "<init>*", at = @At(value = "RETURN"))
	private void onInit(EntityRenderDispatcher entityRenderDispatcher, CallbackInfo info)
	{
		if (BetterNether.hasThinArmor())
		{
			this.features.clear();
			this.addFeature(new ArmorBipedFeatureRenderer(this, new ArmorStandArmorEntityModel(0.25F), new ArmorStandArmorEntityModel(0.5F)));
			this.addFeature(new HeldItemFeatureRenderer(this));
			this.addFeature(new ElytraFeatureRenderer(this));
			this.addFeature(new HeadFeatureRenderer(this));
		}
	}
}