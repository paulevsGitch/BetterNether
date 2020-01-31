package paulevs.betternether.registers;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityFirefly;
import paulevs.betternether.entity.render.RenderFirefly;

public class EntityRegister
{
	public static final EntityType<?> FIREFLY = FabricEntityTypeBuilder.create(EntityCategory.AMBIENT, EntityFirefly::new).size(EntityDimensions.fixed(0.5F, 0.5F)).setImmuneToFire().build();
	
	public static void register()
	{
		RegisterBlockEntity("firefly", FIREFLY, RenderFirefly.class);
	}
	
	public static void RegisterBlockEntity(String name, EntityType<?> entity, Class<? extends MobEntityRenderer<?, ?>> renderer)
	{
		Registry.register(Registry.ENTITY_TYPE, new Identifier(BetterNether.MOD_ID, name), entity);
		EntityRendererRegistry.INSTANCE.register(entity, (entityRenderDispatcher, context) -> {
			MobEntityRenderer<?, ?> render = null;
			try
			{
				render = renderer.getConstructor(entityRenderDispatcher.getClass()).newInstance(entityRenderDispatcher);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return render;
		});
		//Biomes.NETHER.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ENDERMAN, 10, 4, 4));
	}
}
