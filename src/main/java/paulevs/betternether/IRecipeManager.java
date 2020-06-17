package paulevs.betternether;

import java.util.Map;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

public interface IRecipeManager
{
	public Map<RecipeType<?>, Map<Identifier, Recipe<?>>> getMap();
	
	public void setMap(Map<RecipeType<?>, Map<Identifier, Recipe<?>>> map);
}