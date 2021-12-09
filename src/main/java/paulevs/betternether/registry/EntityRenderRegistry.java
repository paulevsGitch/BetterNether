package paulevs.betternether.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.model.ModelEntityFirefly;
import paulevs.betternether.entity.model.ModelEntityFlyingPig;
import paulevs.betternether.entity.model.ModelEntityHydrogenJellyfish;
import paulevs.betternether.entity.model.ModelJungleSkeleton;
import paulevs.betternether.entity.model.ModelNaga;
import paulevs.betternether.entity.model.ModelSkull;
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
	
	public static final ModelLayerLocation FIREFLY_MODEL = registerMain("firefly");
	public static final ModelLayerLocation NAGA_MODEL = registerMain("naga");
	public static final ModelLayerLocation JUNGLE_SKELETON_MODEL = registerMain("jungle_skeleton");
	public static final ModelLayerLocation FLYING_PIG_MODEL = registerMain("flying_pig");
	public static final ModelLayerLocation HYDROGEN_JELLYFISH_MODEL = registerMain("hydrogen_jelly");
	public static final ModelLayerLocation SKULL_MODEL = registerMain("skull");
	
	
	public static ModelLayerLocation registerMain(String id) {
		//System.out.println("Register Entity: " + id);
		return new ModelLayerLocation(new ResourceLocation(BetterNether.MOD_ID, id), "main");
		//return EntityModelLayersMixin.callRegisterMain(key);
	}
	
	public static void register() {
		registerRenderMob(NetherEntities.FIREFLY.type(), RenderFirefly.class);
		registerRenderMob(NetherEntities.CHAIR, RenderChair.class);
		registerRenderMob(NetherEntities.HYDROGEN_JELLYFISH.type(), RenderHydrogenJellyfish.class);
		registerRenderMob(NetherEntities.NAGA.type(), RenderNaga.class);
		registerRenderAny(NetherEntities.NAGA_PROJECTILE, RenderNagaProjectile.class);
		registerRenderMob(NetherEntities.FLYING_PIG.type(), RenderFlyingPig.class);
		registerRenderMob(NetherEntities.JUNGLE_SKELETON.type(), RenderJungleSkeleton.class);
		registerRenderMob(NetherEntities.SKULL.type(), RenderSkull.class);
		
		EntityModelLayerRegistry.registerModelLayer(FIREFLY_MODEL, ModelEntityFirefly::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(NAGA_MODEL, ModelNaga::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(JUNGLE_SKELETON_MODEL, ModelJungleSkeleton::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(FLYING_PIG_MODEL, ModelEntityFlyingPig::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(HYDROGEN_JELLYFISH_MODEL, ModelEntityHydrogenJellyfish::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(SKULL_MODEL, ModelSkull::getTexturedModelData);
	}
	
	private static void registerRenderMob(EntityType<?> entity, Class<? extends MobRenderer<?, ?>> renderer) {
		EntityRendererRegistry.register(entity, (context) -> {
			MobRenderer render = null;
			try {
				render = renderer.getConstructor(context.getClass())
								 .newInstance(context);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return render;
		});
	}
	
	private static void registerRenderAny(EntityType<?> entity, Class<? extends EntityRenderer<?>> renderer) {
		EntityRendererRegistry.register(entity, (context) -> {
			EntityRenderer render = null;
			try {
				render = renderer.getConstructor(context.getClass())
								 .newInstance(context);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return render;
		});
	}
}
