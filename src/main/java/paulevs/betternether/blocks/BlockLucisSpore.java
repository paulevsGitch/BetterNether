package paulevs.betternether.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import paulevs.betternether.BetterNether;

public class BlockLucisSpore extends Block implements IGrowable
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	protected static final AxisAlignedBB SELECT_AABB = new AxisAlignedBB(0.0, 0.25D, 0.0, 1.0, 0.75, 1.0);

	public BlockLucisSpore()
	{
		super(Material.PLANTS, MapColor.LIME);
		this.setRegistryName("lucis_spore");
		this.setUnlocalizedName("lucis_spore");
		this.setLightLevel(0.75F);
		this.setCreativeTab(BetterNether.BN_TAB);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setTickRandomly(true);
	}

	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing direc = null;
		switch(meta)
		{
		case 0:
			direc = EnumFacing.NORTH;
			break;
		case 1:
			direc = EnumFacing.SOUTH;
			break;
		case 2:
			direc = EnumFacing.EAST;
			break;
		case 3:
			direc = EnumFacing.WEST;
			break;
		}
		return this.getDefaultState().withProperty(FACING, direc);
	}

	public int getMetaFromState(IBlockState state)
	{
		return ((EnumFacing)state.getValue(FACING)).getIndex() - 2;
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
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return NULL_AABB;
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		if (facing.getAxis().isHorizontal() && this.canAttachTo(worldIn, pos.offset(facing.getOpposite()), facing))
		{
			return this.getDefaultState().withProperty(FACING, facing);
		}
		else
		{
			for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
			{
				if (this.canAttachTo(worldIn, pos.offset(enumfacing.getOpposite()), enumfacing))
				{
					return this.getDefaultState().withProperty(FACING, enumfacing);
				}
			}

			return this.getDefaultState();
		}
	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
	{
		if (this.canAttachTo(worldIn, pos.west(), side))
		{
			return true;
		}
		else if (this.canAttachTo(worldIn, pos.east(), side))
		{
			return true;
		}
		else if (this.canAttachTo(worldIn, pos.north(), side))
		{
			return true;
		}
		else
		{
			return this.canAttachTo(worldIn, pos.south(), side);
		}
	}

	private boolean canAttachTo(World world, BlockPos pos, EnumFacing side)
	{
		return world.getBlockState(pos).getBlock() instanceof BlockNetherrack;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

		if (!this.canAttachTo(worldIn, pos.offset(enumfacing.getOpposite()), enumfacing))
		{
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}

		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return SELECT_AABB;
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
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
		IBlockState center = BlocksRegister.BLOCK_LUCIS_MUSHROOM.getDefaultState().withProperty(BlockLucisMushroom.SHAPE, BlockLucisMushroom.EnumShape.CENTER);
		IBlockState side = BlocksRegister.BLOCK_LUCIS_MUSHROOM.getDefaultState().withProperty(BlockLucisMushroom.SHAPE, BlockLucisMushroom.EnumShape.SIDE);
		IBlockState corner = BlocksRegister.BLOCK_LUCIS_MUSHROOM.getDefaultState().withProperty(BlockLucisMushroom.SHAPE, BlockLucisMushroom.EnumShape.CORNER);
		if (rand.nextInt(3) == 0)
		{
			worldIn.setBlockState(pos, center);
			if (worldIn.getBlockState(pos.north()).getBlock().isReplaceable(worldIn, pos.north()))
				worldIn.setBlockState(pos.north(), side.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.NORTH));
			if (worldIn.getBlockState(pos.south()).getBlock().isReplaceable(worldIn, pos.south()))
				worldIn.setBlockState(pos.south(), side.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.SOUTH));
			if (worldIn.getBlockState(pos.east()).getBlock().isReplaceable(worldIn, pos.east()))
				worldIn.setBlockState(pos.east(), side.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.EAST));
			if (worldIn.getBlockState(pos.west()).getBlock().isReplaceable(worldIn, pos.west()))
				worldIn.setBlockState(pos.west(), side.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.WEST));

			if (worldIn.getBlockState(pos.north().east()).getBlock().isReplaceable(worldIn, pos.north().east()))
				worldIn.setBlockState(pos.north().east(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.SOUTH));
			if (worldIn.getBlockState(pos.north().west()).getBlock().isReplaceable(worldIn, pos.north().west()))
				worldIn.setBlockState(pos.north().west(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.EAST));
			if (worldIn.getBlockState(pos.south().east()).getBlock().isReplaceable(worldIn, pos.south().east()))
				worldIn.setBlockState(pos.south().east(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.WEST));
			if (worldIn.getBlockState(pos.south().west()).getBlock().isReplaceable(worldIn, pos.south().west()))
				worldIn.setBlockState(pos.south().west(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.NORTH));
		}
		else
		{
			EnumFacing face = state.getValue(FACING);
			boolean offset = false;
			if (face == EnumFacing.SOUTH)
			{
				pos = pos.north();
				offset = true;
			}
			else if (face == EnumFacing.WEST)
			{
				pos = pos.east();
				offset = true;
			}
			if (!offset)
				worldIn.setBlockState(pos, corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.SOUTH));
			if (worldIn.getBlockState(pos.west()).getBlock().isReplaceable(worldIn, pos.west()) || worldIn.getBlockState(pos.west()).getBlock() == this)
				worldIn.setBlockState(pos.west(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.EAST));
			if (worldIn.getBlockState(pos.south()).getBlock().isReplaceable(worldIn, pos.south()) || worldIn.getBlockState(pos.south()).getBlock() == this)
				worldIn.setBlockState(pos.south(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.WEST));
			if (worldIn.getBlockState(pos.south().west()).getBlock().isReplaceable(worldIn, pos.south().west()))
				worldIn.setBlockState(pos.south().west(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.NORTH));
		}
	}
	
	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random)
    {
		if (!worldIn.isRemote)
        {
            super.updateTick(worldIn, pos, state, random);
			if (random.nextInt(16) == 0)
	        	grow(worldIn, random, pos, state);
        }
    }
}
