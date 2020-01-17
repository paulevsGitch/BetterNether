package paulevs.betternether.mixin;

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
import net.minecraft.client.render.entity.feature.ArmorBipedFeatureRenderer;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.render.entity.feature.Deadmau5FeatureRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.feature.ShoulderParrotFeatureRenderer;
import net.minecraft.client.render.entity.feature.StuckArrowsFeatureRenderer;
import net.minecraft.client.render.entity.feature.StuckStingersFeatureRenderer;
import net.minecraft.client.render.entity.feature.TridentRiptideFeatureRenderer;
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
			this.features.clear();
			this.addFeature(new ArmorBipedFeatureRenderer(this, new BipedEntityModel(0.25F), new BipedEntityModel(0.5F)));
			this.addFeature(new HeldItemFeatureRenderer(this));
			this.addFeature(new StuckArrowsFeatureRenderer(this));
			this.addFeature(new Deadmau5FeatureRenderer(this));
			this.addFeature(new CapeFeatureRenderer(this));
			this.addFeature(new HeadFeatureRenderer(this));
			this.addFeature(new ElytraFeatureRenderer(this));
			this.addFeature(new ShoulderParrotFeatureRenderer(this));
			this.addFeature(new TridentRiptideFeatureRenderer(this));
			this.addFeature(new StuckStingersFeatureRenderer(this));
		}
	}
}