package paulevs.betternether.world.structures.city.palette;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.Blocks;
import paulevs.betternether.registry.BlocksRegistry;

public class Palettes {
	private static final HashMap<String, CityPalette> REGISTRY = new HashMap<String, CityPalette>();
	private static final ArrayList<CityPalette> PALETTES = new ArrayList<CityPalette>();

	public static final CityPalette EMPTY = register(new CityPalette("empty"));

	public static final CityPalette RED = register(new CityPalette("red")
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
			.addStoneStairs(Blocks.NETHER_BRICK_STAIRS, BlocksRegistry.NETHER_BRICK_TILE_STAIRS)
			.addGlowingBlocks(Blocks.GLOWSTONE, BlocksRegistry.CINCINNASITE_LANTERN)
			.addCeilingLights(Blocks.LANTERN, BlocksRegistry.CINCINNASITE_LANTERN_SMALL)
			.addWallLights(Blocks.WALL_TORCH, BlocksRegistry.CINCINNASITE_LANTERN_SMALL)
			.addFloorLights(Blocks.TORCH, BlocksRegistry.CINCINNASITE_LANTERN_SMALL)
			.addDoors(BlocksRegistry.WART_DOOR)
			.addTrapdoors(BlocksRegistry.WART_TRAPDOOR)
			.addGlassBlocks(BlocksRegistry.QUARTZ_GLASS_FRAMED_COLORED.red, BlocksRegistry.QUARTZ_GLASS_COLORED.red, BlocksRegistry.CINCINNASITE_FRAME)
			.addGlassPanes(BlocksRegistry.QUARTZ_GLASS_FRAMED_PANE_COLORED.red, BlocksRegistry.QUARTZ_GLASS_PANE_COLORED.red, BlocksRegistry.CINCINNASITE_BARS)
			.addWoodPlates(BlocksRegistry.WART_PLATE)
			.addPotsPanes(BlocksRegistry.BRICK_POT));

	private static CityPalette register(CityPalette palette) {
		REGISTRY.put(palette.getName(), palette);
		PALETTES.add(palette);
		return palette;
	}

	public static CityPalette getPalette(String name) {
		CityPalette palette = REGISTRY.get(name);
		return palette == null ? EMPTY : palette;
	}

	public static CityPalette getRandom(Random random) {
		return random.nextBoolean() ? EMPTY : PALETTES.get(random.nextInt(PALETTES.size()));
	}
}
