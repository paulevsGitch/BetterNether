package paulevs.betternether.recipes;

import com.google.common.collect.ImmutableMap;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RecipesHelper
{
	private static final String[] SHAPE_ROOF = new String[] {"# #", "###", " # "};
	private static final String[] SHAPE_STAIR = new String[] {"#  ", "## ", "###"};
	private static final String[] SHAPE_SLAB = new String[] {"###"};
	private static Identifier id;
	
	public static void makeRoofRecipe(Block source, Block roof)
	{
		if ((id = Registry.BLOCK.getId(source)) != null)
		{
			String name = Registry.BLOCK.getId(roof).getPath();
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(source));
			BNRecipeManager.addCraftingRecipe(name, SHAPE_ROOF, materials, new ItemStack(roof, 6));
		}
	}
	
	public static void makeStairsRecipe(Block source, Block stairs)
	{
		if ((id = Registry.BLOCK.getId(source)) != null)
		{
			String name = Registry.BLOCK.getId(stairs).getPath();
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(source));
			BNRecipeManager.addCraftingRecipe(name, SHAPE_STAIR, materials, new ItemStack(stairs, 4));
		}
	}
	
	public static void makeSlabRecipe(Block source, Block slab)
	{
		if ((id = Registry.BLOCK.getId(source)) != null)
		{
			String name = Registry.BLOCK.getId(slab).getPath();
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(source));
			BNRecipeManager.addCraftingRecipe(name, SHAPE_SLAB, materials, new ItemStack(slab, 6));
		}
	}
	
	public static void slabAndStairs(Block source, Block stairs, Block slab)
	{
		if ((id = Registry.BLOCK.getId(source)) != null)
		{
			String startName = id.getPath();
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(source));
			if (Registry.BLOCK.getId(stairs) != null)
				BNRecipeManager.addCraftingRecipe(startName + "_stair", SHAPE_STAIR, materials, new ItemStack(stairs, 4));
			if (Registry.BLOCK.getId(slab) != null)
				BNRecipeManager.addCraftingRecipe(startName + "_slab", SHAPE_SLAB, materials, new ItemStack(slab, 6));
		}
	}
	
	public static void roof(Block source, Block tile, Block stairs, Block slab)
	{
		if ((id = Registry.BLOCK.getId(source)) != null && Registry.BLOCK.getId(tile) != null)
		{
			String startName = id.getPath();
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(source));
			BNRecipeManager.addCraftingRecipe(startName, SHAPE_ROOF, materials, new ItemStack(tile, 6));
			slabAndStairs(tile, stairs, slab);
		}
	}
}