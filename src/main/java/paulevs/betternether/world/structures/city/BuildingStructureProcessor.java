package paulevs.betternether.world.structures.city;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StructureBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
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
		return new StructureBlockInfo(info.pos, state, info.nbt);
	}

	@Override
	public StructureBlockInfo processBlock(LevelReader worldView, BlockPos pos, BlockPos blockPos, StructureBlockInfo structureBlockInfo, StructureBlockInfo structureBlockInfo2, StructurePlaceSettings structurePlacementData) {
		BlockState state = structureBlockInfo.state;

		if (state.isAir())
			return structureBlockInfo2;

		Block block = state.getBlock();
		String name = Registry.BLOCK.getKey(block).getPath();

		if (name.startsWith("roof_tile")) {
			if (block instanceof StairBlock) {
				return setState(palette.getRoofStair(state), structureBlockInfo2);
			}
			else if (block instanceof SlabBlock) {
				return setState(palette.getRoofSlab(state), structureBlockInfo2);
			}
			return setState(palette.getRoofBlock(state), structureBlockInfo2);
		}
		else if (name.contains("nether") && name.contains("brick")) {
			if (block instanceof StairBlock) {
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
		else if (name.contains("plank") || name.contains("reed") || BlockTags.PLANKS.contains(block)) {
			if (block instanceof StairBlock) {
				return setState(palette.getPlanksStair(state), structureBlockInfo2);
			}
			else if (block instanceof SlabBlock) {
				return setState(palette.getPlanksSlab(state), structureBlockInfo2);
			}
			return setState(palette.getPlanksBlock(state), structureBlockInfo2);
		}
		else if (name.contains("glass") || name.contains("frame")) {
			if (block instanceof IronBarsBlock)
				return setState(palette.getGlassPane(state), structureBlockInfo2);
			return setState(palette.getGlassBlock(state), structureBlockInfo2);
		}
		else if (block instanceof RotatedPillarBlock) {
			if (name.contains("log")) {
				return setState(palette.getLog(state), structureBlockInfo2);
			}
			return setState(palette.getBark(state), structureBlockInfo2);
		}
		else if (block instanceof StairBlock) {
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
		else if (block instanceof TrapDoorBlock) {
			return setState(palette.getTrapdoor(state), structureBlockInfo2);
		}
		else if (block instanceof PressurePlateBlock) {
			if (block.getSoundType(state) == SoundType.WOOD)
				return setState(palette.getWoodenPlate(state), structureBlockInfo2);
			else
				return setState(palette.getStonePlate(state), structureBlockInfo2);
		}
		else if (block instanceof BlockSmallLantern) {
			if (state.getValue(BlockSmallLantern.FACING) == Direction.UP)
				return setState(palette.getCeilingLight(state), structureBlockInfo2);
			else if (state.getValue(BlockSmallLantern.FACING) != Direction.DOWN)
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
			return setState(Blocks.AIR.defaultBlockState(), structureBlockInfo2);
		}
		else if (!name.contains("nether") && !name.contains("mycelium") && state.isCollisionShapeFullBlock(worldView, structureBlockInfo.pos) && state.canOcclude() && !(state.getBlock() instanceof BaseEntityBlock)) {
			if (state.getLightEmission() > 0)
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
