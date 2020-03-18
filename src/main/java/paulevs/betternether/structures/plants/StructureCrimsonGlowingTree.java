package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.IWorld;
import paulevs.betternether.structures.IStructure;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.StructureWorld;

public class StructureCrimsonGlowingTree implements IStructure
{
	private static final StructureWorld[] TREES = new StructureWorld[] {
			new StructureWorld("trees/crimson_glow_tree_01", -1, StructureType.FLOOR),
			new StructureWorld("trees/crimson_glow_tree_02", -1, StructureType.FLOOR),
			new StructureWorld("trees/crimson_glow_tree_03", -1, StructureType.FLOOR),
			new StructureWorld("trees/crimson_glow_tree_04", -1, StructureType.FLOOR)
		};
	private static final Mutable POS = new Mutable();
	
	@Override
	public void generate(IWorld world, BlockPos pos, Random random)
	{
		if (isGround(world.getBlockState(pos.down())) && noTreesNear(world, pos))
		{
			StructureWorld tree = TREES[random.nextInt(TREES.length)];
			tree.generate(world, pos, random);
		}
	}
	
	private boolean isGround(BlockState state)
	{
		return state.getBlock() == Blocks.NETHERRACK || state.getBlock() == Blocks.CRIMSON_NYLIUM;
	}
	
	private boolean noTreesNear(IWorld world, BlockPos pos)
	{
		int x1 = pos.getX() - 5;
		int z1 = pos.getZ() - 5;
		int x2 = pos.getX() + 5;
		int z2 = pos.getZ() + 5;
		POS.setY(pos.getY());
		for (int x = x1; x <= x2; x++)
		{
			POS.setX(x);
			for (int z = z1; z <= z2; z++)
			{
				POS.setZ(z);
				if (isInside(x - pos.getX(), z - pos.getZ(), 8) && isTree(world.getBlockState(POS)))
					return false;
			}
		}
		return true;
	}
	
	private boolean isTree(BlockState state)
	{
		return state.getBlock() == Blocks.CRIMSON_STEM || state.getBlock() == Blocks.SHROOMLIGHT;
	}
	
	private boolean isInside(int x, int z, int radius)
	{
		return Math.abs(x) + Math.abs(z) <= radius;
	}
}
