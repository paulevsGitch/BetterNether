package paulevs.betternether.recipes;

import com.google.common.collect.ImmutableMap;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.registry.ItemsRegistry;

public class ItemRecipes
{
	public static void register()
	{
		if (Registry.ITEM.getId(ItemsRegistry.GLOWSTONE_PILE) != Registry.ITEM.getDefaultId())
		{
			String line = "###";
			String[] shape = new String[] {line, line, line};
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(ItemsRegistry.GLOWSTONE_PILE));
			BNRecipeManager.addCraftingRecipe("glowstone_pile_to_dust", "", shape, materials, new ItemStack(Items.GLOWSTONE_DUST));
		}
	}
}
