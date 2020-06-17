package paulevs.betternether.recipes;

import paulevs.betternether.registry.BlocksRegistry;

public class BlocksRecipes
{
	public static void register()
	{
		//RecipesHelper.roof(BlocksRegistry.REEDS_BLOCK, BlocksRegistry.ROOF_TILE_REEDS, BlocksRegistry.ROOF_TILE_REEDS_STAIRS, BlocksRegistry.ROOF_TILE_REEDS_SLAB);
		//RecipesHelper.roof(BlocksRegistry.STALAGNATE_PLANKS, BlocksRegistry.ROOF_TILE_STALAGNATE, BlocksRegistry.ROOF_TILE_STALAGNATE_STAIRS, BlocksRegistry.ROOF_TILE_STALAGNATE_SLAB);
		//RecipesHelper.roof(Blocks.NETHER_BRICKS, BlocksRegistry.ROOF_TILE_NETHER_BRICKS, BlocksRegistry.ROOF_TILE_NETHER_BRICKS_STAIRS, BlocksRegistry.ROOF_TILE_NETHER_BRICKS_SLAB);
		//RecipesHelper.roof(BlocksRegistry.ROOF_TILE_CINCINNASITE, BlocksRegistry.ROOF_TILE_CINCINNASITE_STAIRS, BlocksRegistry.ROOF_TILE_CINCINNASITE_SLAB);
		//RecipesHelper.roof(BlocksRegistry.ROOF_TILE_WILLOW, BlocksRegistry.ROOF_TILE_WILLOW_STAIRS, BlocksRegistry.ROOF_TILE_WILLOW_SLAB);
		//RecipesHelper.roof(BlocksRegistry.ROOF_TILE_WART, BlocksRegistry.ROOF_TILE_WART_STAIRS, BlocksRegistry.ROOF_TILE_WART_SLAB);
		
		RecipesHelper.slabAndStairs(BlocksRegistry.NETHER_RUBY_BLOCK, BlocksRegistry.NETHER_RUBY_STAIRS, BlocksRegistry.NETHER_RUBY_SLAB);
	}
}