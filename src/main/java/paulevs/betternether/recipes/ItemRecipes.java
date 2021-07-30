package paulevs.betternether.recipes;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import paulevs.betternether.blocks.complex.NetherWoodenMaterial;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.registry.NetherItems;

public class ItemRecipes {
	public static void register() {
		if (itemExists(NetherItems.GLOWSTONE_PILE)) {
			String[] shape = new String[] { "###", "###", "###" };
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(NetherItems.GLOWSTONE_PILE));
			BNRecipeManager.addCraftingRecipe("bn_glowstone_dust", "", shape, materials, new ItemStack(Items.GLOWSTONE_DUST));
		}

		if (itemExists(NetherItems.CINCINNASITE_INGOT) && blockExists(NetherBlocks.CINCINNASITE_CHAIN)) {
			String[] shape = new String[] { "#", "#", "#" };
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(NetherItems.CINCINNASITE_INGOT));
			BNRecipeManager.addCraftingRecipe("cincinnasite_chains", "", shape, materials, new ItemStack(NetherBlocks.CINCINNASITE_CHAIN, 3));
		}

		if (itemExists(NetherItems.LAPIS_PILE)) {
			String[] shape = new String[] { "###", "###", "###" };
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(NetherItems.LAPIS_PILE));
			BNRecipeManager.addCraftingRecipe("glowstone_pile_to_dust", "", shape, materials, new ItemStack(Items.LAPIS_LAZULI));
		}

		if (blockExists(NetherBlocks.BLOOMING_VINE)) {
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(NetherBlocks.BLOOMING_VINE));
			BNRecipeManager.addCraftingRecipe("bn_yellow_dye", "", new String[] { "#" }, materials, new ItemStack(Items.YELLOW_DYE, 2));
		}

		if (blockExists(NetherBlocks.GOLDEN_VINE) && itemExists(NetherItems.GLOWSTONE_PILE)) {
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(NetherBlocks.GOLDEN_VINE));
			BNRecipeManager.addCraftingRecipe("bn_golden_vine", "", new String[] { "#" }, materials, new ItemStack(NetherItems.GLOWSTONE_PILE, 2));
		}

		if (blockExists(NetherBlocks.WALL_MUSHROOM_BROWN)) {
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(NetherBlocks.WALL_MUSHROOM_BROWN));
			BNRecipeManager.addCraftingRecipe("wall_mushroom_brown_1", "wall_mushroom", new String[] { "#" }, materials, new ItemStack(Items.BROWN_MUSHROOM));

			materials = ImmutableMap.of("#", new ItemStack(Items.BROWN_MUSHROOM));
			BNRecipeManager.addCraftingRecipe("wall_mushroom_brown_2", "wall_mushroom", new String[] { "#" }, materials, new ItemStack(NetherBlocks.WALL_MUSHROOM_BROWN));
		}

		if (blockExists(NetherBlocks.WALL_MUSHROOM_RED)) {
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(NetherBlocks.WALL_MUSHROOM_RED));
			BNRecipeManager.addCraftingRecipe("wall_mushroom_red_1", "wall_mushroom", new String[] { "#" }, materials, new ItemStack(Items.RED_MUSHROOM));

			materials = ImmutableMap.of("#", new ItemStack(Items.RED_MUSHROOM));
			BNRecipeManager.addCraftingRecipe("wall_mushroom_red_2", "wall_mushroom", new String[] { "#" }, materials, new ItemStack(NetherBlocks.WALL_MUSHROOM_RED));
		}

		RecipesHelper.makeSimpleRecipe2(NetherBlocks.MUSHROOM_FIR_LOG, NetherBlocks.MUSHROOM_FIR_WOOD, 3, "nether_bark_striped");
		RecipesHelper.makeSimpleRecipe2(NetherBlocks.STRIPED_LOG_RUBEUS, NetherBlocks.STRIPED_BARK_RUBEUS, 3, "nether_bark_striped");
		RecipesHelper.makeSimpleRecipe2(NetherBlocks.MAT_STALAGNATE.getBlock(NetherWoodenMaterial.BLOCK_STRIPPED_LOG), NetherBlocks.MAT_STALAGNATE.getBlock(NetherWoodenMaterial.BLOCK_STRIPPED_BARK), 3, "nether_bark_striped");
		RecipesHelper.makeSimpleRecipe2(NetherBlocks.STRIPED_LOG_WART, NetherBlocks.STRIPED_BARK_WART, 3, "nether_bark_striped");
		RecipesHelper.makeSimpleRecipe2(NetherBlocks.STRIPED_LOG_WILLOW, NetherBlocks.STRIPED_BARK_WILLOW, 3, "nether_bark_striped");
	}

	private static boolean itemExists(Item item) {
		return Registry.ITEM.getKey(item) != Registry.ITEM.getDefaultKey();
	}

	private static boolean blockExists(Block block) {
		return Registry.BLOCK.getKey(block) != Registry.BLOCK.getDefaultKey();
	}
}
