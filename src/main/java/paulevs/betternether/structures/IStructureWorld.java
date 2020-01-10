package paulevs.betternether.structures;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IStructureWorld
{
	static final boolean doBlockNotify = false;
	
	void generate(World world, BlockPos pos, Rotation rotation);
	
	default void setBlockAndNotifyAdequately(World world, BlockPos pos, IBlockState state)
    {
        if (this.doBlockNotify)
        {
            world.setBlockState(pos, state, 3);
        }
        else
        {
            int flag = net.minecraftforge.common.ForgeModContainer.fixVanillaCascading ? 2 | 16 : 2; //Forge: With bit 5 unset, it will notify neighbors and load adjacent chunks.
            world.setBlockState(pos, state, flag);
        }
    }
	
	default boolean isTopSolid(World world, BlockPos pos)
	{
		return world.getBlockState(pos).isSideSolid(world, pos, EnumFacing.UP);
	}
	
	default float getAirFraction(World world, BlockPos pos, Rotation rotation)
	{
		return 1;
	}
	
	default void generateSurface(World world, BlockPos pos, Random random)
	{
		Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
		if (getAirFraction(world, pos, rotation) > 0.8)
		{
			generate(world, pos, rotation);
		}
	}
	
	default void generateSubterrain(World world, BlockPos pos, Random random)
	{
		Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
		if (getAirFraction(world, pos, rotation) < 0.4)
		{
			generate(world, pos, rotation);
		}
	}
	
	default void generateLava(World world, BlockPos pos, Random random)
	{
		Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
		generate(world, pos, rotation);
	}
}
