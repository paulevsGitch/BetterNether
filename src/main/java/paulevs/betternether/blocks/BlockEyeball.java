package paulevs.betternether.blocks;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEyeball extends BlockEyeBase
{
	public BlockEyeball()
	{
		super();
		this.setRegistryName("block_eyeball");
        this.setUnlocalizedName("block_eyeball");
	}
	
	@Override
	public int quantityDropped(Random random)
    {
        return 1 + random.nextInt(4);
    }
	
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        super.randomDisplayTick(stateIn, worldIn, pos, rand);

        if (rand.nextInt(5) == 0)
        {
            worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, pos.getX() + rand.nextDouble(), pos.getY(), pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
        }
    }
}
