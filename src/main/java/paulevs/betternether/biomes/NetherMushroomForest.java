package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.structures.plants.StructureGrayMold;
import paulevs.betternether.structures.plants.StructureLucis;
import paulevs.betternether.structures.plants.StructureMedBrownMushroom;
import paulevs.betternether.structures.plants.StructureMedRedMushroom;
import paulevs.betternether.structures.plants.StructureOrangeMushroom;
import paulevs.betternether.structures.plants.StructureRedMold;
import paulevs.betternether.structures.plants.StructureVanillaMushroom;

public class NetherMushroomForest extends NetherBiome
{
	public NetherMushroomForest(String name)
	{
		super(name);
		addStructure("large_red_mushroom", new StructureMedRedMushroom(), StructureType.FLOOR, 0.15F, false);
		addStructure("large_brown_mushroom", new StructureMedBrownMushroom(), StructureType.FLOOR, 0.15F, false);
		addStructure("vanilla_mushrooms", new StructureVanillaMushroom(), StructureType.FLOOR, 0.1F, false);
		addStructure("orange_mushroom", new StructureOrangeMushroom(), StructureType.FLOOR, 0.2F, false);
		addStructure("red_mold", new StructureRedMold(), StructureType.FLOOR, 0.3F, false);
		addStructure("gray_mold", new StructureGrayMold(), StructureType.FLOOR, 0.3F, false);
		addStructure("lucis", new StructureLucis(), StructureType.WALL, 0.05F, false);
	}

	@Override
	public void genSurfColumn(IWorld world, BlockPos pos, Random random)
	{
		switch(random.nextInt(10))
		{
		case 0:
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SAND.getDefaultState());
			break;
		default:
			BlocksHelper.setWithoutUpdate(world, pos, BlocksRegister.BLOCK_NETHER_MYCELIUM.getDefaultState());
			break;
		}
		for (int i = 1; i < 1 + random.nextInt(3); i++)
		{
			BlockPos p2 = pos.down(i);
			if (random.nextInt(3) == 0 && world.getBlockState(p2).getBlock() == Blocks.NETHERRACK)
				BlocksHelper.setWithoutUpdate(world, p2, Blocks.SOUL_SAND.getDefaultState());
		}
	}
}
