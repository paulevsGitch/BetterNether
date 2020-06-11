package paulevs.betternether.world.structures.city.palette;

import net.minecraft.block.Blocks;
import paulevs.betternether.registry.BlocksRegistry;

public class Palettes
{
	public static final CityPalette RED_PALETTE = new CityPalette()
			.addRoofBlocks(BlocksRegistry.ROOF_TILE_WART)
			.addRoofSlabs(BlocksRegistry.ROOF_TILE_WART_SLAB)
			.addRoofStairs(BlocksRegistry.ROOF_TILE_WART_STAIRS)
			.addPlanksBlocks(BlocksRegistry.WART_PLANKS)
			.addPlanksSlabs(BlocksRegistry.WART_SLAB)
			.addPlanksStairs(BlocksRegistry.WART_STAIRS)
			.addFences(BlocksRegistry.WART_FENCE)
			.addGates(BlocksRegistry.WART_GATE)
			.addWalls(BlocksRegistry.NETHER_BRICK_WALL)
			.addLogs(BlocksRegistry.WART_LOG, BlocksRegistry.WILLOW_LOG, BlocksRegistry.STRIPED_LOG_WART)
			.addBark(BlocksRegistry.WART_BARK, BlocksRegistry.WILLOW_BARK, BlocksRegistry.STRIPED_BARK_WART)
			.addStoneBlocks(Blocks.NETHER_BRICKS, Blocks.NETHER_WART_BLOCK, BlocksRegistry.NETHER_BRICK_TILE_LARGE, BlocksRegistry.NETHER_BRICK_TILE_SMALL)
			.addStoneSlabs(Blocks.NETHER_BRICK_SLAB, BlocksRegistry.NETHER_BRICK_TILE_SLAB)
			.addStoneStairs(Blocks.NETHER_BRICK_STAIRS, BlocksRegistry.NETHER_BRICK_TILE_STAIRS);
}
