package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.world.BNWorldGenerator;

public class NetherWartForestEdge extends NetherWartForest
{
	public NetherWartForestEdge(String name)
	{
		super(name);
	}
	
	@Override
	public void genFloorObjects(World world, BlockPos pos, Random random)
	{
		if (random.nextFloat() <= plantDensity && world.getBlockState(pos).getBlock() == Blocks.SOUL_SAND)
		{
			if (BNWorldGenerator.hasWartTreeGen && random.nextInt(35) == 0)
			{
				BNWorldGenerator.wartTreeGen.generate(world, pos, random);
			}
			else if (BNWorldGenerator.hasWartsGen && random.nextInt(3) == 0 && world.getBlockState(pos).getBlock() == Blocks.SOUL_SAND && world.getBlockState(pos.up()).getBlock() == Blocks.AIR)
				world.setBlockState(pos.up(), Blocks.NETHER_WART.getDefaultState().withProperty(BlockNetherWart.AGE, Integer.valueOf(random.nextInt(4))));
			else if (BlocksRegister.BLOCK_BLACK_BUSH != Blocks.AIR && random.nextInt(3) == 0)
				world.setBlockState(pos.up(), BlocksRegister.BLOCK_BLACK_BUSH.getDefaultState());
		}
	}
}
