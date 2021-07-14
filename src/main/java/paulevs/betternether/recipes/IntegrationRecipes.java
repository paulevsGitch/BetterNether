package paulevs.betternether.recipes;

import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import com.google.common.collect.ImmutableMap;
import paulevs.betternether.integrations.VanillaExcavatorsIntegration;
import paulevs.betternether.integrations.VanillaHammersIntegration;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.registry.ItemsRegistry;

public class IntegrationRecipes {
	public static void register() {
		if (VanillaHammersIntegration.hasHammers()) {
			makeHammerRecipe(ItemsRegistry.CINCINNASITE_HAMMER, BlocksRegistry.CINCINNASITE_FORGED, ItemsRegistry.CINCINNASITE_INGOT);
			makeHammerRecipe(ItemsRegistry.NETHER_RUBY_HAMMER, BlocksRegistry.NETHER_RUBY_BLOCK, ItemsRegistry.NETHER_RUBY);

			ResourceLocation id = Registry.ITEM.getKey(ItemsRegistry.CINCINNASITE_HAMMER_DIAMOND);
			boolean register = id != Registry.ITEM.getDefaultKey() && Registry.ITEM.getKey(ItemsRegistry.CINCINNASITE_HAMMER) != Registry.ITEM.getDefaultKey();
			if (register) {
				String[] shape = new String[] { "#I#" };
				Map<String, ItemStack> materials = ImmutableMap.of(
						"#", new ItemStack(Items.DIAMOND),
						"I", new ItemStack(ItemsRegistry.CINCINNASITE_HAMMER));
				ItemStack result = new ItemStack(ItemsRegistry.CINCINNASITE_HAMMER_DIAMOND);
				BNRecipeManager.addCraftingRecipe("cincinnasite_hammer_diamond", shape, materials, result);
			}
		}

		if (VanillaExcavatorsIntegration.hasExcavators()) {
			makeExcavatorRecipe(ItemsRegistry.CINCINNASITE_EXCAVATOR, BlocksRegistry.CINCINNASITE_FORGED, ItemsRegistry.CINCINNASITE_INGOT);
			makeExcavatorRecipe(ItemsRegistry.NETHER_RUBY_EXCAVATOR, BlocksRegistry.NETHER_RUBY_BLOCK, ItemsRegistry.NETHER_RUBY);

			ResourceLocation id = Registry.ITEM.getKey(ItemsRegistry.CINCINNASITE_EXCAVATOR_DIAMOND);
			boolean register = id != Registry.ITEM.getDefaultKey() && Registry.ITEM.getKey(ItemsRegistry.CINCINNASITE_EXCAVATOR) != Registry.ITEM.getDefaultKey();
			if (register) {
				String[] shape = new String[] { "#I#" };
				Map<String, ItemStack> materials = ImmutableMap.of(
						"#", new ItemStack(Items.DIAMOND),
						"I", new ItemStack(ItemsRegistry.CINCINNASITE_EXCAVATOR));
				ItemStack result = new ItemStack(ItemsRegistry.CINCINNASITE_EXCAVATOR_DIAMOND);
				BNRecipeManager.addCraftingRecipe("cincinnasite_excavator_diamond", shape, materials, result);
			}
		}
	}

	private static void makeHammerRecipe(Item hammer, Block block, Item item) {
		ResourceLocation id = Registry.ITEM.getKey(hammer);
		boolean register = id != Registry.ITEM.getDefaultKey() &&
				Registry.BLOCK.getKey(BlocksRegistry.NETHER_REED) != Registry.BLOCK.getDefaultKey() &&
				Registry.BLOCK.getKey(block) != Registry.BLOCK.getDefaultKey() &&
				Registry.ITEM.getKey(item) != Registry.ITEM.getDefaultKey();
		if (register) {
			Map<String, ItemStack> materials = ImmutableMap.of(
					"#", new ItemStack(block),
					"I", new ItemStack(item),
					"S", new ItemStack(BlocksRegistry.NETHER_REED));
			ItemStack result = new ItemStack(hammer);
			BNRecipeManager.addCraftingRecipe(id.getPath(), new String[] { "#I#", " S ", " S " }, materials, result);
		}
	}

	private static void makeExcavatorRecipe(Item excavator, Block block, Item item) {
		ResourceLocation id = Registry.ITEM.getKey(excavator);
		boolean register = id != Registry.ITEM.getDefaultKey() &&
				Registry.BLOCK.getKey(BlocksRegistry.NETHER_REED) != Registry.BLOCK.getDefaultKey() &&
				Registry.BLOCK.getKey(block) != Registry.BLOCK.getDefaultKey() &&
				Registry.ITEM.getKey(item) != Registry.ITEM.getDefaultKey();
		if (register) {
			Map<String, ItemStack> materials = ImmutableMap.of(
					"#", new ItemStack(block),
					"I", new ItemStack(item),
					"S", new ItemStack(BlocksRegistry.NETHER_REED));
			ItemStack result = new ItemStack(excavator);
			BNRecipeManager.addCraftingRecipe(id.getPath(), new String[] { " I ", "#S#", " S " }, materials, result);
		}
	}
}
