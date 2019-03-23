package paulevs.betternether.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import paulevs.betternether.BetterNether;

public class BlockEyeSeed extends Block implements IGrowable
{
	protected static final AxisAlignedBB SEED_AABB = new AxisAlignedBB(0.25, 0.25D, 0.25, 0.75, 1.0, 0.75);
	
	public BlockEyeSeed()
	{
		super(Material.PLANTS, MapColor.RED);
		this.setRegistryName("eye_seed");
		this.setUnlocalizedName("eye_seed");
		this.setTickRandomly(true);
		this.setCreativeTab(BetterNether.BN_TAB);
		this.setSoundType(SoundType.PLANT);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SEED_AABB;
    }

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		for (int y = 1; y < 5; y++)
		{
			if (worldIn.getBlockState(pos.down(y)).getBlock() != Blocks.AIR)
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return (double)worldIn.rand.nextFloat() < 0.45D;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		int h = rand.nextInt(19) + 5;
		int h2 = h;
		boolean interrupted = false;
		for (int y = 1; y < h; y++)
		{
			if (worldIn.getBlockState(pos.down(y)).getBlock() != Blocks.AIR)
			{
				h2 = y;
				interrupted = true;
				break;
			}
		}
		if (interrupted)
		{
			if (h2 < 5)
				return;
			h = (h2 >> 2) + rand.nextInt(h2 >> 2);
			if (h < 5)
				h = 5;
		}
		IBlockState vineState = BlocksRegister.BLOCK_EYE_VINE.getDefaultState();
		IBlockState eyeState = null;
		if (rand.nextInt(2) == 0)
			eyeState = BlocksRegister.BLOCK_EYEBALL.getDefaultState();
		else
			eyeState = BlocksRegister.BLOCK_EYEBALL_SMALL.getDefaultState();
		for (int y = 0; y < h; y++)
			worldIn.setBlockState(pos.down(y), vineState, 2);
		worldIn.setBlockState(pos.down(h), eyeState);
	}
	
	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random)
    {
		if (!worldIn.isRemote)
        {
            super.updateTick(worldIn, pos, state, random);
			if (worldIn.getBlockState(pos.up()).getBlock() != Blocks.NETHERRACK)
				worldIn.destroyBlock(pos, true);
			else if (random.nextInt(16) == 0 && canGrow(worldIn, pos, state, false))
	        	grow(worldIn, random, pos, state);
        }
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
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
		if (worldIn.getBlockState(pos.up()).getBlock() != Blocks.NETHERRACK)
			worldIn.destroyBlock(pos, true);
    }
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.up()).getBlock() == Blocks.NETHERRACK;
    }
}
