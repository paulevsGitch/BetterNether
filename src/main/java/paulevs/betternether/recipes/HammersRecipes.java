package paulevs.betternether.recipes;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import paulevs.betternether.VanillaHammersIntegration;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.registry.ItemsRegistry;

public class HammersRecipes
{
	public static void register()
	{
		if (VanillaHammersIntegration.hasHammers())
		{
			String[] shape = new String[] {"#I#", " S ", " S "};
			Map<String, ItemStack> materials = ImmutableMap.of(
				"#", new ItemStack(BlocksRegistry.CINCINNASITE_FORGED),
				"I", new ItemStack(ItemsRegistry.CINCINNASITE_INGOT),
				"S", new ItemStack(BlocksRegistry.NETHER_REED)
			);
			ItemStack result = new ItemStack(ItemsRegistry.CINCINNASITE_HAMMER);
			BNRecipeManager.addCraftingRecipe("cincinnasite_hammer", shape, materials, result);
			
			shape = new String[] {"#I#"};
			materials = ImmutableMap.of(
				"#", new ItemStack(Items.DIAMOND),
				"I", new ItemStack(ItemsRegistry.CINCINNASITE_HAMMER)
			);
			result = new ItemStack(ItemsRegistry.CINCINNASITE_HAMMER_DIAMOND);
			BNRecipeManager.addCraftingRecipe("cincinnasite_hammer_diamond", shape, materials, result);
		}
	}
}
