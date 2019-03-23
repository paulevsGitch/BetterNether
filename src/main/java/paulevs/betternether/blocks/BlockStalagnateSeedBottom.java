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
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStalagnateSeedBottom extends Block implements IGrowable
{
	protected static final AxisAlignedBB SEED_AABB = new AxisAlignedBB(0.25, 0.0D, 0.25, 0.75, 0.75, 0.75);
	
	public BlockStalagnateSeedBottom()
	{
		super(Material.PLANTS, MapColor.LIME_STAINED_HARDENED_CLAY);
		this.setTickRandomly(true);
		this.setSoundType(SoundType.PLANT);
		this.setRegistryName("stalagnate_seed_bottom");
		this.setUnlocalizedName("stalagnate_seed");
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SEED_AABB;
    }
	
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		for (int y = 1; y < 3; y++)
		{
			if (worldIn.getBlockState(pos.up(y)).getBlock() != Blocks.AIR)
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
		int h = 24;//rand.nextInt(19) + 3;
		int h2 = h;
		boolean interrupted = false;
		for (int y = 1; y < h; y++)
		{
			if (worldIn.getBlockState(pos.up(y)).getBlock() != Blocks.AIR)
			{
				h2 = y - 1;
				interrupted = true;
				break;
			}
		}
		if (interrupted && worldIn.getBlockState(pos.up(h2 + 1)).getBlock() instanceof BlockNetherrack)
		{
			IBlockState middleState = BlocksRegister.BLOCK_STALAGNATE_MIDDLE.getDefaultState();
			worldIn.setBlockState(pos, BlocksRegister.BLOCK_STALAGNATE_BOTTOM.getDefaultState());
			for (int y = 1; y < h2; y++)
				worldIn.setBlockState(pos.up(y), middleState);
			worldIn.setBlockState(pos.up(h2), BlocksRegister.BLOCK_STALAGNATE_TOP.getDefaultState());
		}
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
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(BlocksRegister.BLOCK_STALAGNATE_SEED);
    }
}
