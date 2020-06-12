package paulevs.betternether.mixin.client;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.render.model.ModelLoader;

@Mixin(ModelLoader.class)
public class ModelLoaderMixin
{
	/*@Inject(method = "loadModelFromJson", at = @At(value = "HEAD"))
	private void loadModelFromJson(Identifier id, CallbackInfoReturnable<JsonUnbakedModel> info)
	{
		//System.out.println(id);
		if (id.getPath().contains("test_material"))
			id = new Identifier(id.toString().replace("test_material", "common"));
	}*/
	
	/*@Inject(method = "loadModel", at = @At(value = "HEAD"))
	private void correctModel(Identifier id, CallbackInfo info)
	{
		if (id.getPath().contains("test_material"))
			id = new Identifier(id.getNamespace(), id.getPath().replace("test_material", "common"));
		System.out.println(id);
	}*/
	
	/*@Shadow
	@Final
	private Map<Identifier, UnbakedModel> unbakedModels;
	
	@Shadow
	private JsonUnbakedModel loadModelFromJson(Identifier id) throws IOException { return null; }
	
	@Inject(method = "<init>*", at = @At(value = "RETURN"))
	private void onInit(ResourceManager resourceManager, BlockColors blockColors, Profiler profiler, int i, CallbackInfo info) throws IOException
	{
		//Identifier id = new Identifier(BetterNether.MOD_ID, "test_material_planks")
		this.unbakedModels.put(
				new Identifier(BetterNether.MOD_ID, "test_material_planks"),
				loadModelFromJson(new Identifier(BetterNether.MOD_ID, "common_planks")));
	}*/
}