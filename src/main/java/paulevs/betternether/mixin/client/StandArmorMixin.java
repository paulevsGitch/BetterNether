package paulevs.betternether.mixin.client;

import java.util.Iterator;

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
import net.minecraft.client.render.entity.feature.FeatureRenderer;
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
			Iterator<FeatureRenderer<ArmorStandEntity, ArmorStandArmorEntityModel>> iterator = this.features.iterator();
			while (iterator.hasNext())
			{
				FeatureRenderer<ArmorStandEntity, ArmorStandArmorEntityModel> feature = iterator.next();
				if (feature instanceof ArmorBipedFeatureRenderer)
				{
					this.features.remove(feature);
					break;
				}
			}
			this.features.add(0, new ArmorBipedFeatureRenderer(this, new ArmorStandArmorEntityModel(0.25F), new ArmorStandArmorEntityModel(0.5F)));
		}
	}
}