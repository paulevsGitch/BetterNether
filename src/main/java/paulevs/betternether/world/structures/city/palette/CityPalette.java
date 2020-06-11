package paulevs.betternether.world.structures.city.palette;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.util.registry.Registry;

public class CityPalette
{
	private static final Random RANDOM = new Random();
	
	List<Block> foundationBlocks = new ArrayList<Block>();
	List<Block> foundationSlabs = new ArrayList<Block>();
	List<Block> foundationStairs = new ArrayList<Block>();
	List<Block> foundationWalls = new ArrayList<Block>();
	
	List<Block> roofBlocks = new ArrayList<Block>();
	List<Block> roofSlabs = new ArrayList<Block>();
	List<Block> roofStairs = new ArrayList<Block>();
	
	List<Block> planksBlocks = new ArrayList<Block>();
	List<Block> planksSlabs = new ArrayList<Block>();
	List<Block> planksStairs = new ArrayList<Block>();
	
	List<Block> fences = new ArrayList<Block>();
	List<Block> walls = new ArrayList<Block>();
	List<Block> gates = new ArrayList<Block>();
	
	List<Block> logs = new ArrayList<Block>();
	List<Block> bark = new ArrayList<Block>();
	List<Block> stoneBlocks = new ArrayList<Block>();
	List<Block> stoneSlabs = new ArrayList<Block>();
	List<Block> stoneStairs = new ArrayList<Block>();
	
	private CityPalette putBlocks(Block[] blocks, List<Block> list)
	{
		for (Block b: blocks)
			list.add(b);
		return this;
	}
	
	public CityPalette addFoundationBlocks(Block... blocks)
	{
		return putBlocks(blocks, foundationBlocks);
	}
	
	public CityPalette addFoundationSlabs(Block... blocks)
	{
		return putBlocks(blocks, foundationSlabs);
	}
	
	public CityPalette addFoundationStairs(Block... blocks)
	{
		return putBlocks(blocks, foundationStairs);
	}
	
	public CityPalette addFoundationWalls(Block... blocks)
	{
		return putBlocks(blocks, foundationWalls);
	}
	
	public CityPalette addRoofBlocks(Block... blocks)
	{
		return putBlocks(blocks, roofBlocks);
	}
	
	public CityPalette addRoofSlabs(Block... blocks)
	{
		return putBlocks(blocks, roofSlabs);
	}
	
	public CityPalette addRoofStairs(Block... blocks)
	{
		return putBlocks(blocks, roofStairs);
	}
	
	public CityPalette addPlanksBlocks(Block... blocks)
	{
		return putBlocks(blocks, planksBlocks);
	}
	
	public CityPalette addPlanksSlabs(Block... blocks)
	{
		return putBlocks(blocks, planksSlabs);
	}
	
	public CityPalette addPlanksStairs(Block... blocks)
	{
		return putBlocks(blocks, planksStairs);
	}
	
	public CityPalette addFences(Block... blocks)
	{
		return putBlocks(blocks, fences);
	}
	
	public CityPalette addWalls(Block... blocks)
	{
		return putBlocks(blocks, walls);
	}
	
	public CityPalette addGates(Block... blocks)
	{
		return putBlocks(blocks, gates);
	}
	
	public CityPalette addLogs(Block... blocks)
	{
		return putBlocks(blocks, logs);
	}
	
	public CityPalette addBark(Block... blocks)
	{
		return putBlocks(blocks, bark);
	}
	
	public CityPalette addStoneBlocks(Block... blocks)
	{
		return putBlocks(blocks, stoneBlocks);
	}
	
	public CityPalette addStoneSlabs(Block... blocks)
	{
		return putBlocks(blocks, stoneSlabs);
	}
	
	public CityPalette addStoneStairs(Block... blocks)
	{
		return putBlocks(blocks, stoneStairs);
	}
	
	private Block getRandomBlock(BlockState state, List<Block> list)
	{
		String seed = Registry.BLOCK.getId(state.getBlock()).getPath();
		RANDOM.setSeed(seed.hashCode());
		return list.get(RANDOM.nextInt(list.size()));
	}
	
	private BlockState getFullState(BlockState input, List<Block> list)
	{
		if (list.isEmpty())
			return input;
		else if (list.size() == 1)
			return list.get(0).getDefaultState();
		else
		{
			return getRandomBlock(input, list).getDefaultState();
		}
	}
	
	private BlockState getSlabState(BlockState input, List<Block> list)
	{
		if (list.isEmpty())
			return input;
		else if (list.size() == 1)
			return copySlab(input, list.get(0));
		else
		{
			return copySlab(input, getRandomBlock(input, list));
		}
	}
	
	private BlockState getStairState(BlockState input, List<Block> list)
	{
		if (list.isEmpty())
			return input;
		else if (list.size() == 1)
			return copyStair(input, list.get(0));
		else
		{
			return copyStair(input, getRandomBlock(input, list));
		}
	}
	
	private BlockState copySlab(BlockState source, Block block)
	{
		BlockState state = block.getDefaultState()
		.with(SlabBlock.TYPE, source.get(SlabBlock.TYPE))
		.with(SlabBlock.WATERLOGGED, source.get(SlabBlock.WATERLOGGED));
		return state;
	}
	
	private BlockState copyStair(BlockState source, Block block)
	{
		BlockState state = block.getDefaultState()
		.with(StairsBlock.FACING, source.get(StairsBlock.FACING))
		.with(StairsBlock.HALF, source.get(StairsBlock.HALF))
		.with(StairsBlock.SHAPE, source.get(StairsBlock.SHAPE))
		.with(StairsBlock.WATERLOGGED, source.get(StairsBlock.WATERLOGGED));
		return state;
	}
	
	private BlockState copyWall(BlockState source, Block block)
	{
		BlockState state = block.getDefaultState()
		.with(WallBlock.EAST_SHAPE, source.get(WallBlock.EAST_SHAPE))
		.with(WallBlock.NORTH_SHAPE, source.get(WallBlock.NORTH_SHAPE))
		.with(WallBlock.SOUTH_SHAPE, source.get(WallBlock.SOUTH_SHAPE))
		.with(WallBlock.WEST_SHAPE, source.get(WallBlock.WEST_SHAPE))
		.with(WallBlock.UP, source.get(WallBlock.UP))
		.with(StairsBlock.WATERLOGGED, source.get(StairsBlock.WATERLOGGED));
		return state;
	}
	
	private BlockState copyFence(BlockState source, Block block)
	{
		BlockState state = block.getDefaultState()
		.with(FenceBlock.EAST, source.get(FenceBlock.EAST))
		.with(FenceBlock.NORTH, source.get(FenceBlock.NORTH))
		.with(FenceBlock.SOUTH, source.get(FenceBlock.SOUTH))
		.with(FenceBlock.WEST, source.get(FenceBlock.WEST))
		.with(FenceBlock.WATERLOGGED, source.get(FenceBlock.WATERLOGGED));
		return state;
	}
	
	private BlockState copyGate(BlockState source, Block block)
	{
		BlockState state = block.getDefaultState()
		.with(FenceGateBlock.IN_WALL, source.get(FenceGateBlock.IN_WALL))
		.with(FenceGateBlock.OPEN, source.get(FenceGateBlock.OPEN))
		.with(FenceGateBlock.POWERED, source.get(FenceGateBlock.POWERED))
		.with(FenceGateBlock.FACING, source.get(FenceGateBlock.FACING));
		return state;
	}
	
	private BlockState copyPillar(BlockState source, Block block)
	{
		BlockState state = block.getDefaultState()
		.with(PillarBlock.AXIS, source.get(PillarBlock.AXIS));
		return state;
	}
	
	// Foundation
	
	public BlockState getFoundationBlock(BlockState input)
	{
		return getFullState(input, foundationBlocks);
	}
	
	public BlockState getFoundationSlab(BlockState input)
	{
		return getSlabState(input, foundationSlabs);
	}
	
	public BlockState getFoundationStair(BlockState input)
	{
		return getStairState(input, foundationStairs);
	}
	
	public BlockState getFoundationWall(BlockState input)
	{
		if (foundationWalls.isEmpty())
			return input;
		else if (foundationWalls.size() == 1)
			return foundationWalls.get(0).getDefaultState();
		else
		{
			return copyWall(input, getRandomBlock(input, foundationWalls));
		}
	}
	
	// Roofs
	
	public BlockState getRoofBlock(BlockState input)
	{
		return getFullState(input, roofBlocks);
	}
	
	public BlockState getRoofSlab(BlockState input)
	{
		return getSlabState(input, roofSlabs);
	}
	
	public BlockState getRoofStair(BlockState input)
	{
		return getStairState(input, roofStairs);
	}
	
	// Planks
	
	public BlockState getPlanksBlock(BlockState input)
	{
		return getFullState(input, planksBlocks);
	}
	
	public BlockState getPlanksSlab(BlockState input)
	{
		return getSlabState(input, planksSlabs);
	}
	
	public BlockState getPlanksStair(BlockState input)
	{
		return getStairState(input, planksStairs);
	}
	
	// Fences
	
	public BlockState getFence(BlockState input)
	{
		if (fences.isEmpty())
			return input;
		else if (fences.size() == 1)
			return fences.get(0).getDefaultState();
		else
		{
			return copyFence(input, getRandomBlock(input, fences));
		}
	}
	
	// Walls
	
	public BlockState getWall(BlockState input)
	{
		if (walls.isEmpty())
			return input;
		else if (walls.size() == 1)
			return walls.get(0).getDefaultState();
		else
		{
			return copyWall(input, getRandomBlock(input, walls));
		}
	}
	
	// Gates
	
	public BlockState getGate(BlockState input)
	{
		if (gates.isEmpty())
			return input;
		else if (gates.size() == 1)
			return gates.get(0).getDefaultState();
		else
		{
			return copyGate(input, getRandomBlock(input, gates));
		}
	}
	
	// Logs
	
	public BlockState getLog(BlockState input)
	{
		if (logs.isEmpty())
			return input;
		else if (logs.size() == 1)
			return logs.get(0).getDefaultState();
		else
		{
			return copyPillar(input, getRandomBlock(input, logs));
		}
	}
	
	public BlockState getBark(BlockState input)
	{
		if (bark.isEmpty())
			return input;
		else if (bark.size() == 1)
			return bark.get(0).getDefaultState();
		else
		{
			return copyPillar(input, getRandomBlock(input, bark));
		}
	}
	
	// Stone
	
	public BlockState getStoneBlock(BlockState input)
	{
		return getFullState(input, stoneBlocks);
	}
	
	public BlockState getStoneSlab(BlockState input)
	{
		return getSlabState(input, stoneSlabs);
	}
	
	public BlockState getStoneStair(BlockState input)
	{
		return getStairState(input, stoneStairs);
	}
}