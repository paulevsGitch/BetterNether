package paulevs.betternether.mixin.common;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.gson.JsonElement;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
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
}