package paulevs.betternether.recipes;

import com.google.common.collect.ImmutableMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

public class RecipesHelper
{
	private static final String[] SHAPE_ROOF = new String[] {"# #", "###", " # "};
	private static final String[] SHAPE_STAIR = new String[] {"#  ", "## ", "###"};
	private static final String[] SHAPE_SLAB = new String[] {"###"};
	private static final String[] SHAPE_BUTTON = new String[] {"#"};
	private static final String[] SHAPE_PLATE = new String[] {"##"};
	private static final String[] SHAPE_X2 = new String[] {"##", "##"};
	private static final String[] SHAPE_FG = new String[] {"#I#", "#I#"};
	private static final String[] SHAPE_2X3 = new String[] {"##", "##", "##"};
	private static final String[] SHAPE_3X2 = new String[] {"###", "###"};
	private static final String[] SHAPE_COLORING = new String[] {"###", "#I#", "###"};
	private static final String[] SHAPE_ROUND = new String[] {"###", "# #", "###"};
	private static final String[] SHAPE_SIGN = new String[] {"###", "###", " I "};
	private static final String[] SHAPE_BARREL = new String[] {"#S#", "# #", "#S#"};
	private static final String[] SHAPE_LADDER = new String[] {"I I", "I#I", "I I"};
	private static final String[] SHAPE_TABURET = new String[] {"##", "II"};
	private static final String[] SHAPE_CHAIR = new String[] {"I ", "##", "II"};
	private static final String[] SHAPE_BAR_STOOL = new String[] {"##", "II", "II"};
	
	private static void makeSingleRecipe(String group, Block source, Block result, String[] shape, int count)
	{
		if (Registry.BLOCK.getId(source) != Registry.BLOCK.getDefaultId())
		{
			String name = Registry.BLOCK.getId(source).getPath() + "_" + Registry.BLOCK.getId(result).getPath();
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(source));
			BNRecipeManager.addCraftingRecipe(name, group, shape, materials, new ItemStack(result, count));
		}
	}
	
	public static void makeRoofRecipe(Block source, Block roof)
	{
		makeSingleRecipe("roof_tile", source, roof, SHAPE_ROOF, 6);
	}
	
	public static void makeStairsRecipe(Block source, Block stairs)
	{
		String group = Registry.BLOCK.getId(stairs).getPath().contains("roof_tile") ?
				"roof_tile_stairs" :
					stairs.getSoundGroup(stairs.getDefaultState()) == BlockSoundGroup.WOOD ?
							"nether_wooden_stairs" : "nether_rock_stairs";
		makeSingleRecipe(group, source, stairs, SHAPE_STAIR, 4);
	}
	
	public static void makeSlabRecipe(Block source, Block slab)
	{
		String group = Registry.BLOCK.getId(slab).getPath().contains("roof_tile") ?
				"roof_tile_slab" :
					slab.getSoundGroup(slab.getDefaultState()) == BlockSoundGroup.WOOD ?
							"nether_wooden_slab" : "nether_rock_slab";
		makeSingleRecipe(group, source, slab, SHAPE_SLAB, 6);
	}
	
	public static void makeButtonRecipe(Block source, Block button)
	{
		String group = button.getSoundGroup(button.getDefaultState()) == BlockSoundGroup.WOOD ? "nether_wooden_button" : "nether_rock_button";
		makeSingleRecipe(group, source, button, SHAPE_BUTTON, 1);
	}
	
	public static void makePlateRecipe(Block source, Block plate)
	{
		String group = plate.getSoundGroup(plate.getDefaultState()) == BlockSoundGroup.WOOD ? "nether_wooden_plate" : "nether_rock_plate";
		makeSingleRecipe(group, source, plate, SHAPE_PLATE, 1);
	}
	
	public static void makeSimpleRecipe(Block source, Block result, int count, String group)
	{
		makeSingleRecipe(group, source, result, SHAPE_BUTTON, count);
	}
	
	public static void makeSimpleRecipe2(Block source, Block result, int count, String group)
	{
		makeSingleRecipe(group, source, result, SHAPE_X2, count);
	}
	
	public static void makeFenceRecipe(Block source, Block fence)
	{
		if (Registry.BLOCK.getId(source) != Registry.BLOCK.getDefaultId())
		{
			String name = Registry.BLOCK.getId(fence).getPath();
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(source), "I", new ItemStack(Items.STICK));
			BNRecipeManager.addCraftingRecipe(name, "nether_fence", SHAPE_FG, materials, new ItemStack(fence, 3));
		}
	}
	
	public static void makeGateRecipe(Block source, Block gate)
	{
		if (Registry.BLOCK.getId(source) != Registry.BLOCK.getDefaultId())
		{
			String name = Registry.BLOCK.getId(gate).getPath();
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("I", new ItemStack(source), "#", new ItemStack(Items.STICK));
			BNRecipeManager.addCraftingRecipe(name, "nether_gate", SHAPE_FG, materials, new ItemStack(gate));
		}
	}
	
	public static void makeDoorRecipe(Block source, Block result)
	{
		makeSingleRecipe("nether_door", source, result, SHAPE_2X3, 3);
	}
	
	public static void makeTrapdoorRecipe(Block source, Block result)
	{
		makeSingleRecipe("nether_trapdoor", source, result, SHAPE_3X2, 2);
	}
	
	public static void makeWallRecipe(Block source, Block wall)
	{
		if (Registry.BLOCK.getId(source) != Registry.BLOCK.getDefaultId())
		{
			String name = Registry.BLOCK.getId(wall).getPath();
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(source));
			BNRecipeManager.addCraftingRecipe(name, "nether_wall", SHAPE_3X2, materials, new ItemStack(wall, 6));
		}
	}
	
	public static void makeColoringRecipe(Block source, Block result, Item dye, String group)
	{
		if (Registry.BLOCK.getId(source) != Registry.BLOCK.getDefaultId())
		{
			String name = Registry.BLOCK.getId(result).getPath();
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(source), "I", new ItemStack(dye));
			BNRecipeManager.addCraftingRecipe(name, group, SHAPE_COLORING, materials, new ItemStack(result, 8));
		}
	}
	
	public static void makeRoundRecipe(Block source, Block result, String group)
	{
		if (Registry.BLOCK.getId(source) != Registry.BLOCK.getDefaultId())
		{
			String name = Registry.BLOCK.getId(result).getPath();
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(source));
			BNRecipeManager.addCraftingRecipe(name, group, SHAPE_ROUND, materials, new ItemStack(result));
		}
	}
	
	public static void makeSignRecipe(Block source, Block result)
	{
		if (Registry.BLOCK.getId(source) != Registry.BLOCK.getDefaultId())
		{
			String name = Registry.BLOCK.getId(result).getPath();
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(source), "I", new ItemStack(Items.STICK));
			BNRecipeManager.addCraftingRecipe(name, "nether_sign", SHAPE_SIGN, materials, new ItemStack(result, 3));
		}
	}
	
	public static void makeBarrelRecipe(Block source, Block slab, Block result)
	{
		if (Registry.BLOCK.getId(source) != Registry.BLOCK.getDefaultId())
		{
			String name = Registry.BLOCK.getId(result).getPath();
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(source), "S", new ItemStack(slab));
			BNRecipeManager.addCraftingRecipe(name, "nether_barrel", SHAPE_BARREL, materials, new ItemStack(result));
		}
	}

	public static void makeLadderRecipe(Block source, Block result)
	{
		if (Registry.BLOCK.getId(source) != Registry.BLOCK.getDefaultId())
		{
			String name = Registry.BLOCK.getId(result).getPath();
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(source), "I", new ItemStack(Items.STICK));
			BNRecipeManager.addCraftingRecipe(name, "nether_ladder", SHAPE_LADDER, materials, new ItemStack(result, 3));
		}
	}

	public static void makeTaburetRecipe(Block source, Block result)
	{
		if (Registry.BLOCK.getId(source) != Registry.BLOCK.getDefaultId())
		{
			String name = Registry.BLOCK.getId(result).getPath();
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(source), "I", new ItemStack(Items.STICK));
			BNRecipeManager.addCraftingRecipe(name, "nether_ladder", SHAPE_TABURET, materials, new ItemStack(result));
		}
	}

	public static void makeChairRecipe(Block source, Block result)
	{
		if (Registry.BLOCK.getId(source) != Registry.BLOCK.getDefaultId())
		{
			String name = Registry.BLOCK.getId(result).getPath();
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(source), "I", new ItemStack(Items.STICK));
			BNRecipeManager.addCraftingRecipe(name, "nether_ladder", SHAPE_CHAIR, materials, new ItemStack(result));
		}
	}
	
	public static void makeBarStoolRecipe(Block source, Block result)
	{
		if (Registry.BLOCK.getId(source) != Registry.BLOCK.getDefaultId())
		{
			String name = Registry.BLOCK.getId(result).getPath();
			ImmutableMap<String, ItemStack> materials = ImmutableMap.of("#", new ItemStack(source), "I", new ItemStack(Items.STICK));
			BNRecipeManager.addCraftingRecipe(name, "nether_ladder", SHAPE_BAR_STOOL, materials, new ItemStack(result));
		}
	}
}