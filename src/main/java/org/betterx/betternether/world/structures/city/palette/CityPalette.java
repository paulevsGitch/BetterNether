package org.betterx.betternether.world.structures.city.palette;

import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;

import org.betterx.betternether.blocks.BNBlockProperties;
import org.betterx.betternether.blocks.BlockPottedPlant;
import org.betterx.betternether.blocks.BlockSmallLantern;
import org.betterx.betternether.registry.NetherBlocks;

import java.util.ArrayList;
import java.util.List;

public class CityPalette {
    private static final RandomSource RANDOM = new LegacyRandomSource(130520220057l);
    private final String name;

    private final List<Block> foundationBlocks = new ArrayList<Block>();
    private final List<Block> foundationSlabs = new ArrayList<Block>();
    private final List<Block> foundationStairs = new ArrayList<Block>();
    private final List<Block> foundationWalls = new ArrayList<Block>();

    private final List<Block> roofBlocks = new ArrayList<Block>();
    private final List<Block> roofSlabs = new ArrayList<Block>();
    private final List<Block> roofStairs = new ArrayList<Block>();

    private final List<Block> planksBlocks = new ArrayList<Block>();
    private final List<Block> planksSlabs = new ArrayList<Block>();
    private final List<Block> planksStairs = new ArrayList<Block>();

    private final List<Block> fences = new ArrayList<Block>();
    private final List<Block> walls = new ArrayList<Block>();
    private final List<Block> gates = new ArrayList<Block>();

    private final List<Block> logs = new ArrayList<Block>();
    private final List<Block> bark = new ArrayList<Block>();
    private final List<Block> stoneBlocks = new ArrayList<Block>();
    private final List<Block> stoneSlabs = new ArrayList<Block>();
    private final List<Block> stoneStairs = new ArrayList<Block>();

    private final List<Block> glowingBlocks = new ArrayList<Block>();
    private final List<Block> wallLights = new ArrayList<Block>();
    private final List<Block> ceilingLights = new ArrayList<Block>();
    private final List<Block> floorLights = new ArrayList<Block>();

    private final List<Block> doors = new ArrayList<Block>();
    private final List<Block> trapdoors = new ArrayList<Block>();
    private final List<Block> platesStone = new ArrayList<Block>();
    private final List<Block> platesWood = new ArrayList<Block>();

    private final List<Block> glassBlocks = new ArrayList<Block>();
    private final List<Block> glassPanes = new ArrayList<Block>();

    private final List<Block> pots = new ArrayList<Block>();

    public CityPalette(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private CityPalette putBlocks(Block[] blocks, List<Block> list) {
        for (Block b : blocks)
            list.add(b);
        return this;
    }

    public CityPalette addFoundationBlocks(Block... blocks) {
        return putBlocks(blocks, foundationBlocks);
    }

    public CityPalette addFoundationSlabs(Block... blocks) {
        return putBlocks(blocks, foundationSlabs);
    }

    public CityPalette addFoundationStairs(Block... blocks) {
        return putBlocks(blocks, foundationStairs);
    }

    public CityPalette addFoundationWalls(Block... blocks) {
        return putBlocks(blocks, foundationWalls);
    }

    public CityPalette addRoofBlocks(Block... blocks) {
        return putBlocks(blocks, roofBlocks);
    }

    public CityPalette addRoofSlabs(Block... blocks) {
        return putBlocks(blocks, roofSlabs);
    }

    public CityPalette addRoofStairs(Block... blocks) {
        return putBlocks(blocks, roofStairs);
    }

    public CityPalette addPlanksBlocks(Block... blocks) {
        return putBlocks(blocks, planksBlocks);
    }

    public CityPalette addPlanksSlabs(Block... blocks) {
        return putBlocks(blocks, planksSlabs);
    }

    public CityPalette addPlanksStairs(Block... blocks) {
        return putBlocks(blocks, planksStairs);
    }

    public CityPalette addFences(Block... blocks) {
        return putBlocks(blocks, fences);
    }

    public CityPalette addWalls(Block... blocks) {
        return putBlocks(blocks, walls);
    }

    public CityPalette addGates(Block... blocks) {
        return putBlocks(blocks, gates);
    }

    public CityPalette addLogs(Block... blocks) {
        return putBlocks(blocks, logs);
    }

    public CityPalette addBark(Block... blocks) {
        return putBlocks(blocks, bark);
    }

    public CityPalette addStoneBlocks(Block... blocks) {
        return putBlocks(blocks, stoneBlocks);
    }

    public CityPalette addStoneSlabs(Block... blocks) {
        return putBlocks(blocks, stoneSlabs);
    }

    public CityPalette addStoneStairs(Block... blocks) {
        return putBlocks(blocks, stoneStairs);
    }

    public CityPalette addGlowingBlocks(Block... blocks) {
        return putBlocks(blocks, glowingBlocks);
    }

    public CityPalette addWallLights(Block... blocks) {
        return putBlocks(blocks, wallLights);
    }

    public CityPalette addCeilingLights(Block... blocks) {
        return putBlocks(blocks, ceilingLights);
    }

    public CityPalette addFloorLights(Block... blocks) {
        return putBlocks(blocks, floorLights);
    }

    public CityPalette addDoors(Block... blocks) {
        return putBlocks(blocks, doors);
    }

    public CityPalette addTrapdoors(Block... blocks) {
        return putBlocks(blocks, trapdoors);
    }

    public CityPalette addStonePlates(Block... blocks) {
        return putBlocks(blocks, platesStone);
    }

    public CityPalette addWoodPlates(Block... blocks) {
        return putBlocks(blocks, platesWood);
    }

    public CityPalette addGlassBlocks(Block... blocks) {
        return putBlocks(blocks, glassBlocks);
    }

    public CityPalette addGlassPanes(Block... blocks) {
        return putBlocks(blocks, glassPanes);
    }

    public CityPalette addPotsPanes(Block... blocks) {
        return putBlocks(blocks, pots);
    }

    private Block getRandomBlock(BlockState state, List<Block> list) {
        if (list.isEmpty())
            return state.getBlock();
        else if (list.size() == 1)
            return list.get(0);

        String seed = Registry.BLOCK.getKey(state.getBlock()).getPath();
        RANDOM.setSeed(seed.hashCode());
        return list.get(RANDOM.nextInt(list.size()));
    }

    private BlockState getFullState(BlockState input, List<Block> list) {
        if (list.isEmpty())
            return input;
        else if (list.size() == 1)
            return list.get(0).defaultBlockState();
        else {
            return getRandomBlock(input, list).defaultBlockState();
        }
    }

    private BlockState getSlabState(BlockState input, List<Block> list) {
        if (list.isEmpty())
            return input;
        else if (list.size() == 1)
            return copySlab(input, list.get(0));
        else {
            return copySlab(input, getRandomBlock(input, list));
        }
    }

    private BlockState getStairState(BlockState input, List<Block> list) {
        if (list.isEmpty())
            return input;
        else if (list.size() == 1)
            return copyStair(input, list.get(0));
        else {
            return copyStair(input, getRandomBlock(input, list));
        }
    }

    private BlockState copySlab(BlockState source, Block block) {
        BlockState state = block.defaultBlockState()
                                .setValue(SlabBlock.TYPE, source.getValue(SlabBlock.TYPE))
                                .setValue(SlabBlock.WATERLOGGED, source.getValue(SlabBlock.WATERLOGGED));
        return state;
    }

    private BlockState copyStair(BlockState source, Block block) {
        BlockState state = block.defaultBlockState()
                                .setValue(StairBlock.FACING, source.getValue(StairBlock.FACING))
                                .setValue(StairBlock.HALF, source.getValue(StairBlock.HALF))
                                .setValue(StairBlock.SHAPE, source.getValue(StairBlock.SHAPE))
                                .setValue(StairBlock.WATERLOGGED, source.getValue(StairBlock.WATERLOGGED));
        return state;
    }

    private BlockState copyWall(BlockState source, Block block) {
        BlockState state = block.defaultBlockState()
                                .setValue(WallBlock.EAST_WALL, source.getValue(WallBlock.EAST_WALL))
                                .setValue(WallBlock.NORTH_WALL, source.getValue(WallBlock.NORTH_WALL))
                                .setValue(WallBlock.SOUTH_WALL, source.getValue(WallBlock.SOUTH_WALL))
                                .setValue(WallBlock.WEST_WALL, source.getValue(WallBlock.WEST_WALL))
                                .setValue(WallBlock.UP, source.getValue(WallBlock.UP))
                                .setValue(StairBlock.WATERLOGGED, source.getValue(StairBlock.WATERLOGGED));
        return state;
    }

    private BlockState copyFence(BlockState source, Block block) {
        BlockState state = block.defaultBlockState()
                                .setValue(FenceBlock.EAST, source.getValue(FenceBlock.EAST))
                                .setValue(FenceBlock.NORTH, source.getValue(FenceBlock.NORTH))
                                .setValue(FenceBlock.SOUTH, source.getValue(FenceBlock.SOUTH))
                                .setValue(FenceBlock.WEST, source.getValue(FenceBlock.WEST))
                                .setValue(FenceBlock.WATERLOGGED, source.getValue(FenceBlock.WATERLOGGED));
        return state;
    }

    private BlockState copyGate(BlockState source, Block block) {
        BlockState state = block.defaultBlockState()
                                .setValue(FenceGateBlock.IN_WALL, source.getValue(FenceGateBlock.IN_WALL))
                                .setValue(FenceGateBlock.OPEN, source.getValue(FenceGateBlock.OPEN))
                                .setValue(FenceGateBlock.POWERED, source.getValue(FenceGateBlock.POWERED))
                                .setValue(FenceGateBlock.FACING, source.getValue(FenceGateBlock.FACING));
        return state;
    }

    private BlockState copyPillar(BlockState source, Block block) {
        BlockState state = block.defaultBlockState()
                                .setValue(RotatedPillarBlock.AXIS, source.getValue(RotatedPillarBlock.AXIS));
        return state;
    }

    private BlockState copyLanternWall(BlockState source, Block block) {
        if (source.getBlock() instanceof BlockSmallLantern && !(block instanceof BlockSmallLantern)) {
            Direction facing = source.getValue(BlockSmallLantern.FACING);
            BlockState state = block.defaultBlockState();
            if (block instanceof WallTorchBlock) {
                return state.setValue(WallTorchBlock.FACING, facing);
            }
            return state;
        }
        return source;
    }

    private BlockState copyLanternCeiling(BlockState source, Block block) {
        if (source.getBlock() instanceof BlockSmallLantern && !(block instanceof BlockSmallLantern)) {
            BlockState state = block.defaultBlockState();
            if (block instanceof LanternBlock) {
                return state.setValue(LanternBlock.HANGING, true);
            }
            return state;
        }
        return source;
    }

    private BlockState copyDoor(BlockState source, Block block) {
        BlockState state = block.defaultBlockState()
                                .setValue(DoorBlock.FACING, source.getValue(DoorBlock.FACING))
                                .setValue(DoorBlock.HALF, source.getValue(DoorBlock.HALF))
                                .setValue(DoorBlock.HINGE, source.getValue(DoorBlock.HINGE))
                                .setValue(DoorBlock.OPEN, source.getValue(DoorBlock.OPEN))
                                .setValue(DoorBlock.POWERED, source.getValue(DoorBlock.POWERED));
        return state;
    }

    private BlockState copyTrapdoor(BlockState source, Block block) {
        BlockState state = block.defaultBlockState()
                                .setValue(TrapDoorBlock.FACING, source.getValue(TrapDoorBlock.FACING))
                                .setValue(TrapDoorBlock.HALF, source.getValue(TrapDoorBlock.HALF))
                                .setValue(TrapDoorBlock.OPEN, source.getValue(TrapDoorBlock.OPEN))
                                .setValue(TrapDoorBlock.POWERED, source.getValue(TrapDoorBlock.POWERED))
                                .setValue(TrapDoorBlock.WATERLOGGED, source.getValue(TrapDoorBlock.WATERLOGGED));
        return state;
    }

    private BlockState copyPane(BlockState source, Block block) {
        BlockState state = block.defaultBlockState()
                                .setValue(IronBarsBlock.EAST, source.getValue(IronBarsBlock.EAST))
                                .setValue(IronBarsBlock.NORTH, source.getValue(IronBarsBlock.NORTH))
                                .setValue(IronBarsBlock.SOUTH, source.getValue(IronBarsBlock.SOUTH))
                                .setValue(IronBarsBlock.WEST, source.getValue(IronBarsBlock.WEST))
                                .setValue(IronBarsBlock.WATERLOGGED, source.getValue(IronBarsBlock.WATERLOGGED));
        return state;
    }

    // Foundation

    public BlockState getFoundationBlock(BlockState input) {
        return getFullState(input, foundationBlocks);
    }

    public BlockState getFoundationSlab(BlockState input) {
        return getSlabState(input, foundationSlabs);
    }

    public BlockState getFoundationStair(BlockState input) {
        return getStairState(input, foundationStairs);
    }

    public BlockState getFoundationWall(BlockState input) {
        if (foundationWalls.isEmpty())
            return input;
        else if (foundationWalls.size() == 1)
            return copyWall(input, foundationWalls.get(0));
        else {
            return copyWall(input, getRandomBlock(input, foundationWalls));
        }
    }

    // Roofs

    public BlockState getRoofBlock(BlockState input) {
        return getFullState(input, roofBlocks);
    }

    public BlockState getRoofSlab(BlockState input) {
        return getSlabState(input, roofSlabs);
    }

    public BlockState getRoofStair(BlockState input) {
        return getStairState(input, roofStairs);
    }

    // Planks

    public BlockState getPlanksBlock(BlockState input) {
        return getFullState(input, planksBlocks);
    }

    public BlockState getPlanksSlab(BlockState input) {
        return getSlabState(input, planksSlabs);
    }

    public BlockState getPlanksStair(BlockState input) {
        return getStairState(input, planksStairs);
    }

    // Fences

    public BlockState getFence(BlockState input) {
        if (fences.isEmpty())
            return input;
        else if (fences.size() == 1)
            return copyFence(input, fences.get(0));
        else {
            return copyFence(input, getRandomBlock(input, fences));
        }
    }

    // Walls

    public BlockState getWall(BlockState input) {
        if (walls.isEmpty())
            return input;
        else if (walls.size() == 1)
            return copyWall(input, walls.get(0));
        else {
            return copyWall(input, getRandomBlock(input, walls));
        }
    }

    // Gates

    public BlockState getGate(BlockState input) {
        if (gates.isEmpty())
            return input;
        else if (gates.size() == 1)
            return copyGate(input, gates.get(0));
        else {
            return copyGate(input, getRandomBlock(input, gates));
        }
    }

    // Logs

    public BlockState getLog(BlockState input) {
        if (logs.isEmpty())
            return input;
        else if (logs.size() == 1)
            return copyPillar(input, logs.get(0));
        else {
            return copyPillar(input, getRandomBlock(input, logs));
        }
    }

    public BlockState getBark(BlockState input) {
        if (bark.isEmpty())
            return input;
        else if (bark.size() == 1)
            return copyPillar(input, bark.get(0));
        else {
            return copyPillar(input, getRandomBlock(input, bark));
        }
    }

    // Stone

    public BlockState getStoneBlock(BlockState input) {
        return getFullState(input, stoneBlocks);
    }

    public BlockState getStoneSlab(BlockState input) {
        return getSlabState(input, stoneSlabs);
    }

    public BlockState getStoneStair(BlockState input) {
        return getStairState(input, stoneStairs);
    }

    // Lights

    public BlockState getGlowingBlock(BlockState input) {
        return getFullState(input, glowingBlocks);
    }

    public BlockState getWallLight(BlockState input) {
        if (wallLights.isEmpty())
            return input;
        else if (wallLights.size() == 1)
            return copyLanternWall(input, bark.get(0));
        else {
            return copyLanternWall(input, getRandomBlock(input, wallLights));
        }
    }

    public BlockState getCeilingLight(BlockState input) {
        return copyLanternCeiling(input, getRandomBlock(input, ceilingLights));
    }

    public BlockState getFloorLight(BlockState input) {
        return getFullState(input, ceilingLights);
    }

    // Doors

    public BlockState getDoor(BlockState input) {
        return copyDoor(input, getRandomBlock(input, doors));
    }

    public BlockState getTrapdoor(BlockState input) {
        return copyTrapdoor(input, getRandomBlock(input, trapdoors));
    }

    // Plates

    public BlockState getWoodenPlate(BlockState input) {
        return getFullState(input, platesWood);
    }

    public BlockState getStonePlate(BlockState input) {
        return getFullState(input, platesStone);
    }

    // Glass

    public BlockState getGlassBlock(BlockState input) {
        return getFullState(input, glassBlocks);
    }

    public BlockState getGlassPane(BlockState input) {
        return copyPane(input, getRandomBlock(input, glassPanes));
    }

    // Pots

    public BlockState getPot(BlockState input) {
        return getFullState(input, pots);
    }

    public BlockState getPlant(BlockState input) {
        String seed = Registry.BLOCK.getKey(input.getBlock()).getPath();
        RANDOM.setSeed(seed.hashCode());
        return NetherBlocks.POTTED_PLANT.defaultBlockState()
                                        .setValue(BlockPottedPlant.PLANT,
                                                BNBlockProperties.PottedPlantShape.values()[RANDOM.nextInt(
                                                        BNBlockProperties.PottedPlantShape.values().length)]);
    }
}