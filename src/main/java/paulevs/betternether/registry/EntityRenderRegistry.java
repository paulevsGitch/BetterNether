package paulevs.betternether.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.model.*;
import paulevs.betternether.entity.render.RenderChair;
import paulevs.betternether.entity.render.RenderFirefly;
import paulevs.betternether.entity.render.RenderFlyingPig;
import paulevs.betternether.entity.render.RenderHydrogenJellyfish;
import paulevs.betternether.entity.render.RenderJungleSkeleton;
import paulevs.betternether.entity.render.RenderNaga;
import paulevs.betternether.entity.render.RenderNagaProjectile;
import paulevs.betternether.entity.render.RenderSkull;

@Environment(EnvType.CLIENT)
public class EntityRenderRegistry {

	public static final EntityModelLayer FIREFLY_LAYER = registerMain("firefly");
	public static final EntityModelLayer NAGA_LAYER = registerMain("naga");
	public static final EntityModelLayer JUNGLE_SKELETON_LAYER = registerMain("jungle_skeleton");
	public static final EntityModelLayer FLYING_PIG_LAYER = registerMain("flying_pig");
	public static final EntityModelLayer HYDROGEN_JELLYFISH_LAYER = registerMain("hydrogen_jelly");


	public static EntityModelLayer registerMain(String id){
		System.out.println("Register Entity: " + id);
		return new EntityModelLayer(new Identifier(BetterNether.MOD_ID, id), "main");
		//return EntityModelLayersMixin.callRegisterMain(key);
	}

	public static void register() {
		registerRenderMob(EntityRegistry.FIREFLY, RenderFirefly.class);
		registerRenderMob(EntityRegistry.CHAIR, RenderChair.class);
		registerRenderMob(EntityRegistry.HYDROGEN_JELLYFISH, RenderHydrogenJellyfish.class);
		registerRenderMob(EntityRegistry.NAGA, RenderNaga.class);
		registerRenderAny(EntityRegistry.NAGA_PROJECTILE, RenderNagaProjectile.class);
		registerRenderMob(EntityRegistry.FLYING_PIG, RenderFlyingPig.class);
		registerRenderMob(EntityRegistry.JUNGLE_SKELETON, RenderJungleSkeleton.class);
		registerRenderMob(EntityRegistry.SKULL, RenderSkull.class);

		EntityModelLayerRegistry.registerModelLayer(FIREFLY_LAYER, ModelEntityFirefly::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(NAGA_LAYER, ModelNaga::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(JUNGLE_SKELETON_LAYER, ModelJungleSkeleton::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(FLYING_PIG_LAYER, ModelEntityFlyingPig::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(HYDROGEN_JELLYFISH_LAYER, ModelEntityHydrogenJellyfish::getTexturedModelData);

	}

	private static void registerRenderMob(EntityType<?> entity, Class<? extends MobEntityRenderer<?, ?>> renderer) {
		EntityRendererRegistry.INSTANCE.register(entity, (context) -> {
			MobEntityRenderer render = null;
			try {
				render = renderer.getConstructor(context.getClass()).newInstance(context);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return render;
		});
	}

	private static void registerRenderAny(EntityType<?> entity, Class<? extends EntityRenderer<?>> renderer) {
		EntityRendererRegistry.INSTANCE.register(entity, (context) -> {
			EntityRenderer render = null;
			try {
				render = renderer.getConstructor(context.getClass()).newInstance(context);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return render;
		});
	}
}
