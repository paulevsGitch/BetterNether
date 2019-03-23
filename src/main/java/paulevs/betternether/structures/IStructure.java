package paulevs.betternether.structures;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public interface IStructure
{
	static final boolean doBlockNotify = false;
	
	public void generate(Chunk chunk, BlockPos pos, Random random);
	
	default void setBlockAndNotifyAdequately(World worldIn, BlockPos pos, IBlockState state)
    {
        if (this.doBlockNotify)
        {
            worldIn.setBlockState(pos, state, 3);
        }
        else
        {
            int flag = net.minecraftforge.common.ForgeModContainer.fixVanillaCascading ? 2 | 16 : 2; //Forge: With bit 5 unset, it will notify neighbors and load adjacent chunks.
            worldIn.setBlockState(pos, state, flag);
        }
    }
}
