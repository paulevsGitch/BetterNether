package paulevs.betternether.recipes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import paulevs.betternether.BetterNether;
import paulevs.betternether.config.Config;

public class BNRecipeManager
{
	private static final Map<RecipeType<?>, Map<Identifier, Recipe<?>>> RECIPES = Maps.newHashMap();
	
	public static void addRecipe(RecipeType<?> type, Recipe<?> recipe)
	{
		if (Config.getBoolean("recipes", recipe.getId().getPath(), true))
		{
			Map<Identifier, Recipe<?>> list = RECIPES.get(type);
			if (list == null)
			{
				list = Maps.newHashMap();
				RECIPES.put(type, list);
			}
			list.put(recipe.getId(), recipe);
		}
	}
	
	public static Map<RecipeType<?>, Map<Identifier, Recipe<?>>> getMap(Map<RecipeType<?>, Map<Identifier, Recipe<?>>> recipes)
	{
		Map<RecipeType<?>, Map<Identifier, Recipe<?>>> result = Maps.newHashMap();
		
		for (RecipeType<?> type: recipes.keySet())
		{
			Map<Identifier, Recipe<?>> typeList = Maps.newHashMap();
			typeList.putAll(recipes.get(type));
			result.put(type, typeList);
		}
		
		for (RecipeType<?> type: RECIPES.keySet())
		{
			Map<Identifier, Recipe<?>> list = RECIPES.get(type);
			if (list != null)
			{
				Map<Identifier, Recipe<?>> typeList = result.get(type);
				list.forEach((id, recipe) -> {
					if (!typeList.containsKey(id))
						typeList.put(id, recipe);
				});
			}
		}
		
		return result;
	}
	
	public static DefaultedList<Ingredient> getIngredients(String[] pattern, Map<String, Ingredient> key, int width, int height)
	{
		DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(width * height, Ingredient.EMPTY);
		Set<String> set = Sets.newHashSet(key.keySet());
		set.remove(" ");

		for(int i = 0; i < pattern.length; ++i) {
			for(int j = 0; j < pattern[i].length(); ++j) {
				String string = pattern[i].substring(j, j + 1);
				Ingredient ingredient = (Ingredient)key.get(string);
				if (ingredient == null) {
					throw new JsonSyntaxException("Pattern references symbol '" + string + "' but it's not defined in the key");
				}

				set.remove(string);
				defaultedList.set(j + width * i, ingredient);
			}
		}

		if (!set.isEmpty()) {
			throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
		} else {
			return defaultedList;
		}
	}
	
	public static void addCraftingRecipe(String name, String[] shape, Map<String, ItemStack> materials, ItemStack result)
	{
		addCraftingRecipe(name, "", shape, materials, result);
	}
	
	public static void addCraftingRecipe(String name, String group, String[] shape, Map<String, ItemStack> materials, ItemStack result)
	{
		int width = shape[0].length();
		int height = shape.length;
		
		Map<String, Ingredient> mapIng = new HashMap<String, Ingredient>();
		mapIng.put(" ", Ingredient.EMPTY);
		materials.forEach((id, material) -> {
			mapIng.put(id, fromStacks(material));
		});
		
		DefaultedList<Ingredient> list = BNRecipeManager.getIngredients(shape, mapIng, width, height);
		ShapedRecipe recipe = new ShapedRecipe(new Identifier(BetterNether.MOD_ID, name), group, width, height, list, result);
		BNRecipeManager.addRecipe(RecipeType.CRAFTING, recipe);
	}
	
	private static Ingredient fromStacks(ItemStack... stacks)
	{
		return Ingredient.method_26964(Arrays.stream(stacks));
	}
	
	public static ShapelessRecipe makeEmtyRecipe(Identifier id)
	{
		ShapelessRecipe recipe = new ShapelessRecipe(id, "empty", new ItemStack(Items.AIR), DefaultedList.of());
		return recipe;
	}
}