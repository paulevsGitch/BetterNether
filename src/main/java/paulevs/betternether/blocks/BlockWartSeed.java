package paulevs.betternether.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import paulevs.betternether.BetterNether;

public class BlockWartSeed extends Block implements IGrowable
{
	public static final PropertyDirection FACING = BlockDirectional.FACING;
	private static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.5, 0.75);
	private static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(0.25, 0.5, 0.25, 0.75, 1.0, 0.75);
	private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.25, 0.25, 0.5, 0.75, 0.75, 1.0);
	private static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.25, 0.25, 0.0, 0.75, 0.75, 0.5);
	private static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0, 0.25, 0.25, 0.5, 0.75, 0.75);
	private static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.5, 0.25, 0.25, 1.0, 0.75, 0.75);
	
	public BlockWartSeed()
	{
		super(Material.WOOD, MapColor.RED_STAINED_HARDENED_CLAY);
		this.setCreativeTab(BetterNether.BN_TAB);
		this.setHardness(1.0F);
		this.setSoundType(SoundType.WOOD);
		this.setRegistryName("wart_seed");
		this.setUnlocalizedName("wart_seed");
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		if (worldIn.getBlockState(pos.down()).getBlock() == Blocks.SOUL_SAND)
		{
			boolean b1 = worldIn.getBlockState(new BlockPos(pos.getX() - 4, pos.getY() + 5, pos.getZ())).getBlock() == Blocks.AIR;
			boolean b2 = worldIn.getBlockState(new BlockPos(pos.getX() + 4, pos.getY() + 5, pos.getZ())).getBlock() == Blocks.AIR;
			boolean b3 = worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY() + 5, pos.getZ() - 4)).getBlock() == Blocks.AIR;
			boolean b4 = worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY() + 5, pos.getZ() + 4)).getBlock() == Blocks.AIR;
			boolean b5 = worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY() + 10, pos.getZ())).getBlock() == Blocks.AIR;
			return b1 && b2 && b3 && b4 && b5;
		}
		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return (double)worldIn.rand.nextFloat() < 0.45D;
	}

	@Override
	public void grow(World world, Random random, BlockPos pos, IBlockState state)
	{
		IBlockState bricks = Blocks.NETHER_BRICK.getDefaultState();
		IBlockState redBricks = Blocks.RED_NETHER_BRICK.getDefaultState();
		IBlockState wart = Blocks.NETHER_WART_BLOCK.getDefaultState();
		
		world.setBlockState(pos, wart);
		List<BlockPos> seedBlocks = new ArrayList<BlockPos>();
		int height = 5 + random.nextInt(5);
		int h2 = height >>> 1;
		int h3 = height >>> 2;
		int width = (height >>> 2) + 1;
		int offset = width >>> 1;
		for (int x = 0; x < width; x++)
		{
			int px = x + pos.getX() - offset;
			for (int z = 0; z < width; z++)
			{
				int pz = z + pos.getZ() - offset;
				int rh = random.nextInt(h2);
				int rh2 = random.nextInt(h3);
				for (int y = 0; y < height; y++)
				{
					int py = y + pos.getY();
					BlockPos pos2 = new BlockPos(px, py, pz);
					if (world.getBlockState(pos2).getBlock().isReplaceable(world, pos2))
					{
						if (y < rh2 && random.nextInt(2) == 0)
							world.setBlockState(pos2, bricks);
						else if (y < rh &&random.nextInt(2) == 0)
							world.setBlockState(pos2, redBricks);
						else
						{
							world.setBlockState(pos2, wart);
							EnumFacing dir = EnumFacing.HORIZONTALS[random.nextInt(4)];
							seedBlocks.add(pos2.offset(dir));
						}
					}
				}
			}
		}
		int headWidth = width + 2;
		offset ++;
		height = height - width - 1 + pos.getY();
		for (int x = 0; x < headWidth; x++)
		{
			int px = x + pos.getX() - offset;
			for (int z = 0; z < headWidth; z++)
			{
				if (x != z && x != (headWidth - z - 1))
				{
					int pz = z + pos.getZ() - offset;
					for (int y = 0; y < width; y++)
					{
						int py = y + height;
						BlockPos pos2 = new BlockPos(px, py, pz);
						if (world.getBlockState(pos2) != wart && world.getBlockState(pos2).getBlock().isReplaceable(world, pos2))
							world.setBlockState(pos2, wart);
					}
				}
			}
		}
		for (BlockPos pos2 : seedBlocks)
			PlaceRandomSeed(world, pos2);
	}
	
	private static void PlaceRandomSeed(World world, BlockPos pos)
	{
		if (world.getBlockState(pos).getBlock() == Blocks.AIR)
		{
			IBlockState seed = BlocksRegister.BLOCK_WART_SEED.getDefaultState().withProperty(BlockDirectional.FACING, EnumFacing.UP);
			if (world.getBlockState(pos.north()).isFullBlock())
				seed = seed.withProperty(BlockDirectional.FACING, EnumFacing.SOUTH);
			else if (world.getBlockState(pos.south()).isFullBlock())
				seed = seed.withProperty(BlockDirectional.FACING, EnumFacing.NORTH);
			else if (world.getBlockState(pos.east()).isFullBlock())
				seed = seed.withProperty(BlockDirectional.FACING, EnumFacing.WEST);
			else if (world.getBlockState(pos.west()).isFullBlock())
				seed = seed.withProperty(BlockDirectional.FACING, EnumFacing.EAST);
			else if (world.getBlockState(pos.up()).isFullBlock())
				seed = seed.withProperty(BlockDirectional.FACING, EnumFacing.DOWN);
			world.setBlockState(pos, seed);
		}
	}
	
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta % 6));
	}

	public int getMetaFromState(IBlockState state)
	{
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
		return this.getDefaultState().withProperty(FACING, facing);
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
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
    {
		if (this.canAttachTo(worldIn, pos.up(), side))
        {
            return true;
        }
		else if (this.canAttachTo(worldIn, pos.down(), side))
        {
            return true;
        }
		else if (this.canAttachTo(worldIn, pos.west(), side))
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
	
	private boolean canAttachTo(World p_193392_1_, BlockPos p_193392_2_, EnumFacing p_193392_3_)
    {
        IBlockState iblockstate = p_193392_1_.getBlockState(p_193392_2_);
        boolean flag = isExceptBlockForAttachWithPiston(iblockstate.getBlock());
        return !flag && iblockstate.getBlockFaceShape(p_193392_1_, p_193392_2_, p_193392_3_) == BlockFaceShape.SOLID && !iblockstate.canProvidePower();
    }
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

        if (!this.canAttachTo(worldIn, pos.offset(enumfacing.getOpposite()), enumfacing))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
		Block under = worldIn.getBlockState(pos.down()).getBlock();
		return under instanceof BlockNetherrack || under == Blocks.SOUL_SAND;
    }
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		EnumFacing face = state.getValue(FACING);
		switch(face)
		{
		case UP:
			return UP_AABB;
		case DOWN:
			return DOWN_AABB;
		case NORTH:
			return NORTH_AABB;
		case SOUTH:
			return SOUTH_AABB;
		case EAST:
			return EAST_AABB;
		default:
			return WEST_AABB;
		}
	}
	
	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
