package paulevs.betternether.recipes;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.integrations.VanillaExcavatorsIntegration;
import paulevs.betternether.integrations.VanillaHammersIntegration;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.registry.ItemsRegistry;

public class IntegrationRecipes
{
	public static void register()
	{
		if (VanillaHammersIntegration.hasHammers())
		{
			makeHammerRecipe(ItemsRegistry.CINCINNASITE_HAMMER, BlocksRegistry.CINCINNASITE_FORGED, ItemsRegistry.CINCINNASITE_INGOT);
			makeHammerRecipe(ItemsRegistry.NETHER_RUBY_HAMMER, BlocksRegistry.NETHER_RUBY_BLOCK, ItemsRegistry.NETHER_RUBY);
			
			Identifier id = Registry.ITEM.getId(ItemsRegistry.CINCINNASITE_HAMMER_DIAMOND);
			boolean register = id != Registry.ITEM.getDefaultId() && Registry.ITEM.getId(ItemsRegistry.CINCINNASITE_HAMMER) != Registry.ITEM.getDefaultId();
			if (register)
			{
				String[] shape = new String[] {"#I#"};
				Map<String, ItemStack> materials = ImmutableMap.of(
					"#", new ItemStack(Items.DIAMOND),
					"I", new ItemStack(ItemsRegistry.CINCINNASITE_HAMMER)
				);
				ItemStack result = new ItemStack(ItemsRegistry.CINCINNASITE_HAMMER_DIAMOND);
				BNRecipeManager.addCraftingRecipe("cincinnasite_hammer_diamond", shape, materials, result);
			}
		}
		
		if (VanillaExcavatorsIntegration.hasExcavators())
		{
			makeExcavatorRecipe(ItemsRegistry.CINCINNASITE_EXCAVATOR, BlocksRegistry.CINCINNASITE_FORGED, ItemsRegistry.CINCINNASITE_INGOT);
			makeExcavatorRecipe(ItemsRegistry.NETHER_RUBY_EXCAVATOR, BlocksRegistry.NETHER_RUBY_BLOCK, ItemsRegistry.NETHER_RUBY);
			
			Identifier id = Registry.ITEM.getId(ItemsRegistry.CINCINNASITE_EXCAVATOR_DIAMOND);
			boolean register = id != Registry.ITEM.getDefaultId() && Registry.ITEM.getId(ItemsRegistry.CINCINNASITE_EXCAVATOR) != Registry.ITEM.getDefaultId();
			if (register)
			{
				String[] shape = new String[] {"#I#"};
				Map<String, ItemStack> materials = ImmutableMap.of(
					"#", new ItemStack(Items.DIAMOND),
					"I", new ItemStack(ItemsRegistry.CINCINNASITE_EXCAVATOR)
				);
				ItemStack result = new ItemStack(ItemsRegistry.CINCINNASITE_EXCAVATOR_DIAMOND);
				BNRecipeManager.addCraftingRecipe("cincinnasite_excavator_diamond", shape, materials, result);
			}
		}
	}
	
	private static void makeHammerRecipe(Item hammer, Block block, Item item)
	{
		Identifier id = Registry.ITEM.getId(hammer);
		boolean register = id != Registry.ITEM.getDefaultId() &&
				Registry.BLOCK.getId(BlocksRegistry.NETHER_REED) != Registry.BLOCK.getDefaultId() &&
				Registry.BLOCK.getId(block) != Registry.BLOCK.getDefaultId() &&
				Registry.ITEM.getId(item) != Registry.ITEM.getDefaultId();
		if (register)
		{
			Map<String, ItemStack> materials = ImmutableMap.of(
					"#", new ItemStack(block),
					"I", new ItemStack(item),
					"S", new ItemStack(BlocksRegistry.NETHER_REED)
					);
			ItemStack result = new ItemStack(hammer);
			BNRecipeManager.addCraftingRecipe(id.getPath(), new String[] {"#I#", " S ", " S "}, materials, result);
		}
	}
	
	private static void makeExcavatorRecipe(Item excavator, Block block, Item item)
	{
		Identifier id = Registry.ITEM.getId(excavator);
		boolean register = id != Registry.ITEM.getDefaultId() &&
				Registry.BLOCK.getId(BlocksRegistry.NETHER_REED) != Registry.BLOCK.getDefaultId() &&
				Registry.BLOCK.getId(block) != Registry.BLOCK.getDefaultId() &&
				Registry.ITEM.getId(item) != Registry.ITEM.getDefaultId();
		if (register)
		{
			Map<String, ItemStack> materials = ImmutableMap.of(
					"#", new ItemStack(block),
					"I", new ItemStack(item),
					"S", new ItemStack(BlocksRegistry.NETHER_REED)
					);
			ItemStack result = new ItemStack(excavator);
			BNRecipeManager.addCraftingRecipe(id.getPath(), new String[] {" I ", "#S#", " S "}, materials, result);
		}
	}
}
