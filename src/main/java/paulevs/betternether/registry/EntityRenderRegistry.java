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
import paulevs.betternether.entity.render.*;

@Environment(EnvType.CLIENT)
public class EntityRenderRegistry {

	public static final EntityModelLayer FIREFLY_MODEL = registerMain("firefly");
	public static final EntityModelLayer NAGA_MODEL = registerMain("naga");
	public static final EntityModelLayer JUNGLE_SKELETON_MODEL = registerMain("jungle_skeleton");
	public static final EntityModelLayer FLYING_PIG_MODEL = registerMain("flying_pig");
	public static final EntityModelLayer HYDROGEN_JELLYFISH_MODEL = registerMain("hydrogen_jelly");
	public static final EntityModelLayer SKULL_MODEL = registerMain("skull");


	public static EntityModelLayer registerMain(String id){
		//System.out.println("Register Entity: " + id);
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

		EntityModelLayerRegistry.registerModelLayer(FIREFLY_MODEL, ModelEntityFirefly::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(NAGA_MODEL, ModelNaga::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(JUNGLE_SKELETON_MODEL, ModelJungleSkeleton::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(FLYING_PIG_MODEL, ModelEntityFlyingPig::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(HYDROGEN_JELLYFISH_MODEL, ModelEntityHydrogenJellyfish::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(SKULL_MODEL, ModelSkull::getTexturedModelData);
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
