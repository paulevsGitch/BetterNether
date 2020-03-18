package paulevs.betternether.registers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;

public class BrewingRegister
{
	private static final List<PotionRecipe> RECIPES = new ArrayList<PotionRecipe>();
	
	public static void register()
	{
		registerBrewingRecipe(BlocksRegister.BARREL_CACTUS.asItem(), Items.GLASS_BOTTLE, Potions.WATER);
	}
	
	public static void registerBrewingRecipe(Item source, Item potion, Potion result)
	{
		RECIPES.add(new PotionRecipe(source, potion, result));
	}
	
	public static class PotionRecipe
	{
		private final Item source;
		private final Item potion;
		private final Potion result;
		
		public PotionRecipe(Item source, Item potion, Potion result)
		{
			this.source = source;
			this.potion = potion;
			this.result = result;
		}

		public boolean canBeUsed(ItemStack stack)
		{
			return stack.getItem() == source;
		}
		
		public boolean comparePotion(ItemStack stack)
		{
			return stack.getItem() == potion;
		}

		public Potion getResult()
		{
			return result;
		}
	}
	
	public static boolean isIngredient(ItemStack stack)
	{
		for (PotionRecipe p: RECIPES)
			if (p.canBeUsed(stack))
				return true;
		return false;
	}
	
	public static PotionRecipe getRecipe(ItemStack source, ItemStack result)
	{
		for (PotionRecipe p: RECIPES)
			if (p.canBeUsed(source) && p.comparePotion(result))
				return p;
		return null;
	}
	
	public static boolean isPotion(ItemStack stack)
	{
		for (PotionRecipe p: RECIPES)
			if (p.comparePotion(stack))
				return true;
		return false;
	}
}
