package paulevs.betternether.world.structures.city;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.StructureBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.structure.Structure.StructureBlockInfo;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldView;
import paulevs.betternether.blocks.BNPlanks;
import paulevs.betternether.blocks.BlockBNPot;
import paulevs.betternether.blocks.BlockPottedPlant;
import paulevs.betternether.blocks.BlockSmallLantern;
import paulevs.betternether.world.structures.city.palette.CityPalette;

public class BuildingStructureProcessor extends StructureProcessor {
	protected final CityPalette palette;

	public BuildingStructureProcessor(CityPalette palette) {
		this.palette = palette;
	}

	private StructureBlockInfo setState(BlockState state, StructureBlockInfo info) {
		return new StructureBlockInfo(info.pos, state, info.tag);
	}

	@Override
	public StructureBlockInfo process(WorldView worldView, BlockPos pos, BlockPos blockPos, StructureBlockInfo structureBlockInfo, StructureBlockInfo structureBlockInfo2, StructurePlacementData structurePlacementData) {
		BlockState state = structureBlockInfo.state;

		if (state.isAir())
			return structureBlockInfo2;

		Block block = state.getBlock();
		String name = Registry.BLOCK.getId(block).getPath();

		if (name.startsWith("roof_tile")) {
			if (block instanceof StairsBlock) {
				return setState(palette.getRoofStair(state), structureBlockInfo2);
			}
			else if (block instanceof SlabBlock) {
				return setState(palette.getRoofSlab(state), structureBlockInfo2);
			}
			return setState(palette.getRoofBlock(state), structureBlockInfo2);
		}
		else if (name.contains("nether") && name.contains("brick")) {
			if (block instanceof StairsBlock) {
				return setState(palette.getFoundationStair(state), structureBlockInfo2);
			}
			else if (block instanceof SlabBlock) {
				return setState(palette.getFoundationSlab(state), structureBlockInfo2);
			}
			else if (block instanceof WallBlock) {
				return setState(palette.getFoundationWall(state), structureBlockInfo2);
			}
			return setState(palette.getFoundationBlock(state), structureBlockInfo2);
		}
		else if (name.contains("plank") || name.contains("reed") || block instanceof BNPlanks) {
			if (block instanceof StairsBlock) {
				return setState(palette.getPlanksStair(state), structureBlockInfo2);
			}
			else if (block instanceof SlabBlock) {
				return setState(palette.getPlanksSlab(state), structureBlockInfo2);
			}
			return setState(palette.getPlanksBlock(state), structureBlockInfo2);
		}
		else if (name.contains("glass") || name.contains("frame")) {
			if (block instanceof PaneBlock)
				return setState(palette.getGlassPane(state), structureBlockInfo2);
			return setState(palette.getGlassBlock(state), structureBlockInfo2);
		}
		else if (block instanceof PillarBlock) {
			if (name.contains("log")) {
				return setState(palette.getLog(state), structureBlockInfo2);
			}
			return setState(palette.getBark(state), structureBlockInfo2);
		}
		else if (block instanceof StairsBlock) {
			return setState(palette.getStoneStair(state), structureBlockInfo2);
		}
		else if (block instanceof SlabBlock) {
			return setState(palette.getStoneSlab(state), structureBlockInfo2);
		}
		else if (block instanceof WallBlock) {
			return setState(palette.getWall(state), structureBlockInfo2);
		}
		else if (block instanceof FenceBlock) {
			return setState(palette.getFence(state), structureBlockInfo2);
		}
		else if (block instanceof FenceGateBlock) {
			return setState(palette.getGate(state), structureBlockInfo2);
		}
		else if (block instanceof DoorBlock) {
			return setState(palette.getDoor(state), structureBlockInfo2);
		}
		else if (block instanceof TrapdoorBlock) {
			return setState(palette.getTrapdoor(state), structureBlockInfo2);
		}
		else if (block instanceof PressurePlateBlock) {
			if (block.getSoundGroup(state) == BlockSoundGroup.WOOD)
				return setState(palette.getWoodenPlate(state), structureBlockInfo2);
			else
				return setState(palette.getStonePlate(state), structureBlockInfo2);
		}
		else if (block instanceof BlockSmallLantern) {
			if (state.get(BlockSmallLantern.FACING) == Direction.UP)
				return setState(palette.getCeilingLight(state), structureBlockInfo2);
			else if (state.get(BlockSmallLantern.FACING) != Direction.DOWN)
				return setState(palette.getWallLight(state), structureBlockInfo2);
			else
				return setState(palette.getFloorLight(state), structureBlockInfo2);
		}
		else if (block instanceof BlockBNPot) {
			return setState(palette.getPot(state), structureBlockInfo2);
		}
		else if (block instanceof BlockPottedPlant) {
			return setState(palette.getPlant(state), structureBlockInfo2);
		}
		else if (block instanceof StructureBlock) {
			return setState(Blocks.AIR.getDefaultState(), structureBlockInfo2);
		}
		else if (!name.contains("nether") && !name.contains("mycelium") && state.isFullCube(worldView, structureBlockInfo.pos) && state.isOpaque() && !(state.getBlock() instanceof BlockWithEntity)) {
			if (state.getLuminance() > 0)
				return setState(palette.getGlowingBlock(state), structureBlockInfo2);
			return setState(palette.getStoneBlock(state), structureBlockInfo2);
		}

		return structureBlockInfo2;
	}

	@Override
	protected StructureProcessorType<?> getType() {
		return StructureProcessorType.NOP;
	}
}
