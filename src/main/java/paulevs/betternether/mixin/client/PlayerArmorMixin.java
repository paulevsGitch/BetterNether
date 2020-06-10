package paulevs.betternether.mixin.client;

import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import paulevs.betternether.BetterNether;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerArmorMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>
{
	public PlayerArmorMixin(EntityRenderDispatcher entityRenderDispatcher, PlayerEntityModel<AbstractClientPlayerEntity> entityModel, float f)
	{
		super(entityRenderDispatcher, entityModel, f);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Inject(method = "<init>*", at = @At(value = "RETURN"))
	private void onInit(EntityRenderDispatcher entityRenderDispatcher, boolean bl, CallbackInfo info)
	{
		if (BetterNether.hasThinArmor())
		{
			Iterator<FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>> iterator = this.features.iterator();
			while (iterator.hasNext())
			{
				FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> feature = iterator.next();
				if (feature instanceof ArmorFeatureRenderer)
				{
					this.features.remove(feature);
					break;
				}
			}
			this.features.add(0, new ArmorFeatureRenderer(this, new BipedEntityModel(0.25F), new BipedEntityModel(0.5F)));
		}
	}
}