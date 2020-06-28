package paulevs.betternether.mixin.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.World;
import paulevs.betternether.recipes.BNRecipeManager;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin
{
	@Shadow
	private Map<RecipeType<?>, Map<Identifier, Recipe<?>>> recipes;
	
	@Inject(method = "apply", at = @At(value = "RETURN"))
	private void setRecipes(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo info)
	{
		recipes = BNRecipeManager.getMap(recipes);
	}

	@Inject(method = "deserialize", at = @At(value = "HEAD"), cancellable = true)
	private static void checkMissing(Identifier id, JsonObject json, CallbackInfoReturnable<Recipe<?>> info)
	{
		if (id.getNamespace().equals("techreborn") && !FabricLoader.getInstance().isModLoaded("techreborn"))
		{
			info.setReturnValue(BNRecipeManager.makeEmtyRecipe(id));
			info.cancel();
		}
	}
	
	@Shadow
	private <C extends Inventory, T extends Recipe<C>> Map<Identifier, Recipe<C>> getAllOfType(RecipeType<T> type) { return null; }
	
	@Overwrite
	public <C extends Inventory, T extends Recipe<C>> Optional<T> getFirstMatch(RecipeType<T> type, C inventory, World world)
	{
		Collection<Recipe<C>> values = getAllOfType(type).values();
		List<Recipe<C>> list = new ArrayList<Recipe<C>>(values);
		list.sort((v1, v2) -> {
			boolean b1 = v1.getId().getNamespace().equals("minecraft");
			boolean b2 = v2.getId().getNamespace().equals("minecraft");
			return b1 ^ b2 ? (b1 ? 1 : -1) : 0;
		});
		
		return list.stream().flatMap((recipe) -> {
	         return Util.stream(type.get(recipe, world, inventory));
	      }).findFirst();
	   }
}