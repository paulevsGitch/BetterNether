package paulevs.betternether.blocks;

import java.util.Random;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEyeballSmall extends BlockEyeBase
{
	private static AxisAlignedBB EYE_AABB = new AxisAlignedBB(0.25, 0.5, 0.25, 0.75, 1.0, 0.75);

	public BlockEyeballSmall()
	{
		super();
		this.setRegistryName("block_eyeball_small");
		this.setUnlocalizedName("block_eyeball_small");
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return EYE_AABB;
    }
	
	@Override
	public int quantityDropped(Random random)
    {
        return random.nextInt(3);
    }
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
	
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        super.randomDisplayTick(stateIn, worldIn, pos, rand);

        if (rand.nextInt(5) == 0)
        {
            worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, pos.getX() + rand.nextFloat() * 0.5F + 0.25, pos.getY() + 0.5, pos.getZ() + rand.nextFloat() * 0.5 + 0.25, 0.0D, 0.0D, 0.0D);
        }
    }
}
