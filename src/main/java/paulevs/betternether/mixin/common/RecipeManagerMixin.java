package paulevs.betternether.mixin.common;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import paulevs.betternether.IRecipeManager;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin implements IRecipeManager
{
	@Shadow
	private Map<RecipeType<?>, Map<Identifier, Recipe<?>>> recipes;

	@Override
	public Map<RecipeType<?>, Map<Identifier, Recipe<?>>> getMap()
	{
		return recipes;
	}

	@Override
	public void setMap(Map<RecipeType<?>, Map<Identifier, Recipe<?>>> map)
	{
		recipes = map;
	}
}