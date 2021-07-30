package paulevs.betternether.world.structures.city.palette;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import net.minecraft.world.level.block.Blocks;
import paulevs.betternether.blocks.complex.WillowMaterial;
import paulevs.betternether.registry.NetherBlocks;

public class Palettes {
	private static final HashMap<String, CityPalette> REGISTRY = new HashMap<String, CityPalette>();
	private static final ArrayList<CityPalette> PALETTES = new ArrayList<CityPalette>();

	public static final CityPalette EMPTY = register(new CityPalette("empty"));

	public static final CityPalette RED = register(new CityPalette("red")
			.addRoofBlocks(NetherBlocks.ROOF_TILE_WART)
			.addRoofSlabs(NetherBlocks.ROOF_TILE_WART_SLAB)
			.addRoofStairs(NetherBlocks.ROOF_TILE_WART_STAIRS)
			.addPlanksBlocks(NetherBlocks.WART_PLANKS)
			.addPlanksSlabs(NetherBlocks.WART_SLAB)
			.addPlanksStairs(NetherBlocks.WART_STAIRS)
			.addFences(NetherBlocks.WART_FENCE)
			.addGates(NetherBlocks.WART_GATE)
			.addWalls(NetherBlocks.NETHER_BRICK_WALL)
			.addLogs(NetherBlocks.WART_LOG, NetherBlocks.MAT_WILLOW.getBlock(WillowMaterial.BLOCK_LOG), NetherBlocks.STRIPED_LOG_WART)
			.addBark(NetherBlocks.WART_BARK, NetherBlocks.MAT_WILLOW.getBlock(WillowMaterial.BLOCK_BARK), NetherBlocks.STRIPED_BARK_WART)
			.addStoneBlocks(Blocks.NETHER_BRICKS, Blocks.NETHER_WART_BLOCK, NetherBlocks.NETHER_BRICK_TILE_LARGE, NetherBlocks.NETHER_BRICK_TILE_SMALL)
			.addStoneSlabs(Blocks.NETHER_BRICK_SLAB, NetherBlocks.NETHER_BRICK_TILE_SLAB)
			.addStoneStairs(Blocks.NETHER_BRICK_STAIRS, NetherBlocks.NETHER_BRICK_TILE_STAIRS)
			.addGlowingBlocks(Blocks.GLOWSTONE, NetherBlocks.CINCINNASITE_LANTERN)
			.addCeilingLights(Blocks.LANTERN, NetherBlocks.CINCINNASITE_LANTERN_SMALL)
			.addWallLights(Blocks.WALL_TORCH, NetherBlocks.CINCINNASITE_LANTERN_SMALL)
			.addFloorLights(Blocks.TORCH, NetherBlocks.CINCINNASITE_LANTERN_SMALL)
			.addDoors(NetherBlocks.WART_DOOR)
			.addTrapdoors(NetherBlocks.WART_TRAPDOOR)
			.addGlassBlocks(NetherBlocks.QUARTZ_GLASS_FRAMED_COLORED.red, NetherBlocks.QUARTZ_GLASS_COLORED.red, NetherBlocks.CINCINNASITE_FRAME)
			.addGlassPanes(NetherBlocks.QUARTZ_GLASS_FRAMED_PANE_COLORED.red, NetherBlocks.QUARTZ_GLASS_PANE_COLORED.red, NetherBlocks.CINCINNASITE_BARS)
			.addWoodPlates(NetherBlocks.WART_PLATE)
			.addPotsPanes(NetherBlocks.BRICK_POT));

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
