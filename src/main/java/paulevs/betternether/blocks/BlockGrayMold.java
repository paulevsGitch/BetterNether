package paulevs.betternether.blocks;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGrayMold extends BlockMold
{
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.5, 0.75);

	public BlockGrayMold()
	{
		super("gray_mold");
	}
	
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        super.randomDisplayTick(stateIn, worldIn, pos, rand);

        if (rand.nextInt(3) == 0)
        {
            worldIn.spawnParticle(EnumParticleTypes.TOWN_AURA, pos.getX() + rand.nextDouble(), pos.getY() + rand.nextDouble() * 0.5, pos.getZ() + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
        }
    }
}
