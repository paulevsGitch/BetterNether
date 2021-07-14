package paulevs.betternether.registry;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

public class BrewingRegistry {
	private static final List<BrewingRecipe> RECIPES = new ArrayList<BrewingRecipe>();

	public static void register() {
		register(new ItemStack(BlocksRegistry.BARREL_CACTUS), new ItemStack(Items.GLASS_BOTTLE), makePotion(Potions.WATER));
		register(new ItemStack(BlocksRegistry.HOOK_MUSHROOM), makePotion(Potions.AWKWARD), makePotion(Potions.HEALING));
	}

	private static void register(ItemStack source, ItemStack bottle, ItemStack result) {
		RECIPES.add(new BrewingRecipe(source, bottle, result));
	}

	private static ItemStack makePotion(Potion potion) {
		return PotionUtils.setPotion(new ItemStack(Items.POTION), potion);
	}

	public static class BrewingRecipe {
		private final ItemStack source;
		private final ItemStack bottle;
		private final ItemStack result;

		public BrewingRecipe(ItemStack source, ItemStack bottle, ItemStack result) {
			this.source = source;
			this.bottle = bottle;
			this.result = result;
		}

		public boolean isValid(ItemStack source, ItemStack bottle) {
			return ItemStack.isSame(this.source, source) && ItemStack.isSame(this.bottle, bottle);
		}

		public boolean isValid(ItemStack source) {
			return ItemStack.isSame(this.source, source);
		}

		public ItemStack getResult() {
			return result;
		}
	}

	public static ItemStack getResult(ItemStack source, ItemStack bottle) {
		for (BrewingRecipe recipe : RECIPES)
			if (recipe.isValid(source, bottle))
				return recipe.getResult();
		return null;
	}

	public static boolean isValidIngridient(ItemStack source) {
		for (BrewingRecipe recipe : RECIPES)
			if (recipe.isValid(source))
				return true;
		return false;
	}
}
