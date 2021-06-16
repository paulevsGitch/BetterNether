package paulevs.betternether.mixin.common;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(RecipeManager.class)
public interface RecipeManagerAccessor {
	@Accessor("recipes")
	Map<RecipeType<?>, Map<Identifier, Recipe<?>>> getRecipes();

	@Accessor("recipes")
	void setRecipes(Map<RecipeType<?>, Map<Identifier, Recipe<?>>> recipes);
}