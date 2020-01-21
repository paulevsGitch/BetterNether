package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.structures.plants.StructureGrayMold;
import paulevs.betternether.structures.plants.StructureOrangeMushroom;
import paulevs.betternether.structures.plants.StructureRedMold;
import paulevs.betternether.structures.plants.StructureVanillaMushroom;

public class NetherMushroomForestEdge extends NetherBiome
{
	public NetherMushroomForestEdge(String name)
	{
		super(name);
		addStructure("orange_mushroom", new StructureOrangeMushroom(), StructureType.FLOOR, 0.05F, false);
		addStructure("vanilla_mushrooms", new StructureVanillaMushroom(), StructureType.FLOOR, 0.1F, false);
		addStructure("red_mold", new StructureRedMold(), StructureType.FLOOR, 0.3F, false);
		addStructure("gray_mold", new StructureGrayMold(), StructureType.FLOOR, 0.3F, false);
	}

	@Override
	public void genSurfColumn(IWorld world, BlockPos pos, Random random)
	{
		switch(random.nextInt(4))
		{
		case 0:
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SAND.getDefaultState());
			break;
		case 1:
			break;
		default:
			BlocksHelper.setWithoutUpdate(world, pos, BlocksRegister.BLOCK_NETHER_MYCELIUM.getDefaultState());
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