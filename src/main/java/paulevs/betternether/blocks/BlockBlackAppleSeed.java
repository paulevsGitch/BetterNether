package paulevs.betternether.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import paulevs.betternether.BetterNether;

public class BlockBlackAppleSeed extends Block implements IGrowable
{
	protected static final AxisAlignedBB SEED_AABB = new AxisAlignedBB(0.25, 0.0D, 0.25, 0.75, 0.75, 0.75);
	
	public BlockBlackAppleSeed()
	{
		super(Material.PLANTS, MapColor.BROWN_STAINED_HARDENED_CLAY);
		this.setTickRandomly(true);
		this.setSoundType(SoundType.PLANT);
		this.setRegistryName("black_apple_seed");
		this.setUnlocalizedName("black_apple_seed");
		this.setCreativeTab(BetterNether.BN_TAB);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SEED_AABB;
    }
	
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return this.canPlaceBlockAt(worldIn, pos);
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return (double)worldIn.rand.nextFloat() < 0.45D;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		worldIn.setBlockState(pos, BlocksRegister.BLOCK_BLACK_APPLE.getDefaultState());
	}
	
	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random)
    {
		if (!worldIn.isRemote)
        {
            super.updateTick(worldIn, pos, state, random);
			if (!canPlaceBlockAt(worldIn, pos))
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
		if (!canPlaceBlockAt(worldIn, pos))
			worldIn.destroyBlock(pos, true);
    }
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.down()).getBlock() instanceof BlockNetherrack;
    }
}
