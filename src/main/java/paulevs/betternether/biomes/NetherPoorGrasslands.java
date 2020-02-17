package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureBlackApple;
import paulevs.betternether.structures.plants.StructureBlackBush;
import paulevs.betternether.structures.plants.StructureInkBush;
import paulevs.betternether.structures.plants.StructureMagmaFlower;
import paulevs.betternether.structures.plants.StructureNetherGrass;
import paulevs.betternether.structures.plants.StructureNetherWart;
import paulevs.betternether.structures.plants.StructureReeds;
import paulevs.betternether.structures.plants.StructureSmoker;
import paulevs.betternether.structures.plants.StructureWartSeed;

public class NetherPoorGrasslands extends NetherBiome 
{
	public NetherPoorGrasslands(String name)
	{
		super(name);
		addStructure("nether_reed", new StructureReeds(), StructureType.FLOOR, 0.05F, false);
		addStructure("nether_wart", new StructureNetherWart(), StructureType.FLOOR, 0.005F, false);
		addStructure("magma_flower", new StructureMagmaFlower(), StructureType.FLOOR, 0.05F, false);
		addStructure("smoker", new StructureSmoker(), StructureType.FLOOR, 0.005F, false);
		addStructure("ink_bush", new StructureInkBush(), StructureType.FLOOR, 0.005F, false);
		addStructure("black_apple", new StructureBlackApple(), StructureType.FLOOR, 0.001F, false);
		addStructure("black_bush", new StructureBlackBush(), StructureType.FLOOR, 0.002F, false);
		addStructure("wart_seed", new StructureWartSeed(), StructureType.FLOOR, 0.002F, false);
		addStructure("nether_grass", new StructureNetherGrass(), StructureType.FLOOR, 0.04F, false);
	}

	@Override
	public void genSurfColumn(IWorld world, BlockPos pos, Random random)
	{
		switch(random.nextInt(3))
		{
		case 0:
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SAND.getDefaultState());
			break;
		case 1:
			BlocksHelper.setWithoutUpdate(world, pos, BlocksRegister.NETHERRACK_MOSS.getDefaultState());
			break;
		}
		for (int i = 1; i < random.nextInt(3); i++)
		{
			BlockPos down = pos.down(i);
			if (random.nextInt(3) == 0 && BlocksHelper.isNetherGround(world.getBlockState(down)))
			{
				BlocksHelper.setWithoutUpdate(world, down, Blocks.SOUL_SAND.getDefaultState());
			}
		}
	}
}