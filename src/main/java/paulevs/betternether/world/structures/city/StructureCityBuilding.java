package paulevs.betternether.world.structures.city;

import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.Structure;
import net.minecraft.structure.Structure.StructureBlockInfo;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.RuleStructureProcessor;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorRule;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.StructureNBT;

public class StructureCityBuilding extends StructureNBT
{
	protected static final BlockState AIR = Blocks.AIR.getDefaultState();
	protected static final StructureProcessor REPLACE = makeProcessorReplace();
	
	private BoundingBox bb;
	public BlockPos[] ends;
	private Direction[] dirs;
	private BlockPos rotationOffset;
	private int offsetY;
	
	public StructureCityBuilding(String structure)
	{
		super(structure);
		this.offsetY = 0;
		init();
	}
	
	public StructureCityBuilding(String structure, int offsetY)
	{
		super(structure);
		this.offsetY = offsetY;
		init();
	}
	
	protected StructureCityBuilding(Identifier location, Structure structure)
	{
		super(location, structure);
		init();
	}
	
	private void init()
	{
		BlockPos size = structure.getSize();
		bb = new BoundingBox(size);
		List<StructureBlockInfo> map = structure.getInfosForBlock(BlockPos.ORIGIN, new StructurePlacementData(), Blocks.STRUCTURE_BLOCK, false);
		ends = new BlockPos[map.size()];
		dirs = new Direction[map.size()];
		int i = 0;
		BlockPos center = new BlockPos(size.getX() >> 1, size.getY(), size.getZ() >> 1);
		for(StructureBlockInfo info : map)
		{
			ends[i] = info.pos;
			dirs[i++] = getDir(info.pos.add(-center.getX(), 0, -center.getZ()));
		}
		rotationOffset = new BlockPos(0, 0, 0);
		rotation = BlockRotation.NONE;
	}
	
	private Direction getDir(BlockPos pos)
	{
		int ax = Math.abs(pos.getX());
		int az = Math.abs(pos.getZ());
		int mx = Math.max(ax, az);
		if (mx == ax)
		{
			if (pos.getX() > 0)
				return Direction.EAST;
			else
				return Direction.WEST;
		}
		else
		{
			if (pos.getZ() > 0)
				return Direction.SOUTH;
			else
				return Direction.NORTH;
		}
	}

	public BoundingBox getBoungingBox()
	{
		return bb;
	}
	
	public void place(WorldAccess world, BlockPos pos)
	{
		BlockPos p = pos.add(rotationOffset);
		structure.place(world, p, new StructurePlacementData().setRotation(rotation));
		for (BlockPos rep : ends)
		{
			BlocksHelper.setWithoutUpdate(world, rep.add(pos), AIR);
		}
		BlockState state;
		int d;
		for (int x = 0; x < bb.x2; x++)
			for (int z = 0; z < bb.z2; z++)
			{
				p = pos.add(x, 0, z);
				state = world.getBlockState(p);
				if (state.isFullCube(world, p))
				{
					for (d = 1; d < pos.getY() - 5; d++)
					{
						if (BlocksHelper.isNetherGroundMagma(world.getBlockState(p.down(d))))
							break;
					}
					for (int y = 1; y < d; y++)
					{
						BlocksHelper.setWithoutUpdate(world, p.down(y), state);
					}
				}
			}
	}
	
	protected BlockRotation mirrorRotation(BlockRotation r)
	{
		switch (r)
		{
		case CLOCKWISE_90:
			return BlockRotation.COUNTERCLOCKWISE_90;
		default:
			return r;
		}
	}
	
	public void placeInChunk(WorldAccess world, BlockPos pos, BlockBox boundingBox)
	{
		BlockPos p = pos.add(rotationOffset);
		structure.place(world, p, new StructurePlacementData()
				.setRotation(rotation)
				.setMirror(mirror)
				.setBoundingBox(boundingBox)
				.addProcessor(REPLACE));
	}

	public BlockPos[] getEnds()
	{
		return ends;
	}
	
	public int getEndsCount()
	{
		return ends.length;
	}
	
	public BlockPos getOffsettedPos(int index)
	{
		return ends[index].offset(dirs[index]);
	}

	public BlockPos getPos(int index)
	{
		return ends[index];
	}
	
	public StructureCityBuilding getRotated(BlockRotation rotation)
	{
		StructureCityBuilding building = this.clone();
		building.rotation = rotation;
		building.rotationOffset = building.structure.getSize().rotate(rotation);
		int x = building.rotationOffset.getX();
		int z = building.rotationOffset.getZ();
		if (x < 0)
			x = -x - 1;
		else
			x = 0;
		if (z < 0)
			z = -z - 1;
		else
			z = 0;
		building.rotationOffset = new BlockPos(x, 0, z);
		for (int i = 0; i < building.dirs.length; i++)
		{
			building.dirs[i] = rotated(building.dirs[i], rotation);
			building.ends[i] = building.ends[i].rotate(rotation).add(building.rotationOffset);
		}
		building.bb.rotate(rotation);
		building.offsetY = this.offsetY;
		return building;
	}
	
	public StructureCityBuilding getRandomRotated(Random random)
	{
		return getRotated(BlockRotation.values()[random.nextInt(4)]);
	}
	
	public StructureCityBuilding clone()
	{
		return new StructureCityBuilding(location, structure);
	}
	
	private Direction rotated(Direction dir, BlockRotation rotation)
	{
		Direction f;
		switch (rotation)
		{
		case CLOCKWISE_90:
			f = dir.rotateYClockwise();
			break;
		case CLOCKWISE_180:
			f = dir.getOpposite();
			break;
		case COUNTERCLOCKWISE_90:
			f = dir.rotateYCounterclockwise();
			break;
		default:
			f = dir;
			break;
		}
		return f;
	}
	
	public int getYOffset()
	{
		return offsetY;
	}

	public BlockRotation getRotation()
	{
		return rotation;
	}

	private static StructureProcessor makeProcessorReplace()
	{
		return new RuleStructureProcessor(
				ImmutableList.of(
						new StructureProcessorRule(
								new BlockMatchRuleTest(Blocks.STRUCTURE_BLOCK),
								AlwaysTrueRuleTest.INSTANCE,
								Blocks.AIR.getDefaultState()
						)
				)
		);
	}
	
	@Override
	public BlockBox getBoundingBox(BlockPos pos)
	{
		return structure.calculateBoundingBox(new StructurePlacementData().setRotation(this.rotation).setMirror(mirror), pos.add(rotationOffset));
	}
	
	@Override
	public StructureCityBuilding setRotation(BlockRotation rotation)
	{
		this.rotation = rotation;
		rotationOffset = structure.getSize().rotate(rotation);
		return this;
	}
}
