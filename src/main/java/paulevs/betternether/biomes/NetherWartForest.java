package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureBlackBush;
import paulevs.betternether.structures.plants.StructureNetherWart;
import paulevs.betternether.structures.plants.StructureSoulLily;
import paulevs.betternether.structures.plants.StructureWartSeed;
import paulevs.betternether.structures.plants.StructureWartTree;

public class NetherWartForest extends NetherBiome
{
	public NetherWartForest(String name)
	{
		super(name);
		addStructure("wart_tree", new StructureWartTree(), StructureType.FLOOR, 0.1F, false);
		addStructure("nether_wart", new StructureNetherWart(), StructureType.FLOOR, 0.2F, false);
		addStructure("wart_seed", new StructureWartSeed(), StructureType.FLOOR, 0.05F, false);
		addStructure("black_bush", new StructureBlackBush(), StructureType.FLOOR, 0.05F, false);
		
		addStructure("soul_lily_1", new StructureSoulLily(), StructureType.FLOOR, 0.2F, true);
		addStructure("soul_lily_2", new StructureSoulLily(), StructureType.FLOOR, 0.1F, true);
		addStructure("soul_lily_3", new StructureSoulLily(), StructureType.FLOOR, 0.05F, true);
	}
	
	@Override
	public void genSurfColumn(IWorld world, BlockPos pos, Random random)
	{
		switch(random.nextInt(3))
		{
		case 0:
		case 1:
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SAND.getDefaultState());
			break;
		case 2:
			BlocksHelper.setWithoutUpdate(world, pos, BlocksRegistry.NETHERRACK_MOSS.getDefaultState());
			break;
		}
		for (int i = 1; i < random.nextInt(3); i++)
		{
			BlockPos down = pos.down(i);
			if (random.nextInt(3) == 0 && BlocksHelper.isNetherGround(world.getBlockState(down)))
				BlocksHelper.setWithoutUpdate(world, down, Blocks.SOUL_SAND.getDefaultState());
		}
	}
}
