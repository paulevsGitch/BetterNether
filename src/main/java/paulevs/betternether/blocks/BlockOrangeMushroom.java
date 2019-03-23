package paulevs.betternether.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import paulevs.betternether.BetterNether;

public class BlockOrangeMushroom extends Block implements IGrowable
{
	public static final PropertyInteger SIZE = PropertyInteger.create("size", 0, 3);
	private static final AxisAlignedBB[] AABB = new AxisAlignedBB[]
			{
					new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.5, 0.75),
					new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 0.5625, 0.8125),
					new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.75, 0.9375),
					new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)
			};

	public BlockOrangeMushroom()
	{
		super(Material.PLANTS, MapColor.ORANGE_STAINED_HARDENED_CLAY);
		this.setCreativeTab(BetterNether.BN_TAB);
		this.setHardness(0.5F);
		this.setSoundType(SoundType.WOOD);
		this.setRegistryName("orange_mushroom");
		this.setUnlocalizedName("orange_mushroom");
		this.setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SIZE, 0));
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
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return AABB[state.getValue(SIZE)];
	}

	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(SIZE, meta);
	}

	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(SIZE);
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {SIZE});
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return canStay(worldIn, pos) && state.getValue(SIZE) < 3;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return state.getValue(SIZE) < 3 && rand.nextInt(4) == 0;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		int s = state.getValue(SIZE) + 1;
		if (s < 4)
			worldIn.setBlockState(pos, state.withProperty(SIZE, state.getValue(SIZE) + 1));
	}
	
	private boolean canStay(World worldIn, BlockPos pos)
	{
		return worldIn.getBlockState(pos.down()).getBlock() instanceof BlockNetherMycelium;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (!this.canStay(worldIn, pos))
		{
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (!this.canStay(worldIn, pos))
		{
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
		else if (rand.nextInt(8) == 0)
		{
			int s = state.getValue(SIZE) + 1;
			if (s < 4)
				worldIn.setBlockState(pos, state.withProperty(SIZE, state.getValue(SIZE) + 1));
		}
	}
	
	private void spawnSeeds(World world, BlockPos pos, Random random)
	{
		if (!world.isRemote)
		{
			ItemStack drop = new ItemStack(this, 1 + random.nextInt(3));
			EntityItem itemEntity = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop);
			world.spawnEntity(itemEntity);
			drop = new ItemStack(Items.DYE, 1 + random.nextInt(2), 5);
			itemEntity = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop);
			world.spawnEntity(itemEntity);
			drop = new ItemStack(Items.DYE, 1 + random.nextInt(2), 14);
			itemEntity = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop);
			world.spawnEntity(itemEntity);
		}
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
	{
		if (state.getValue(SIZE) > 2)
			spawnSeeds(worldIn, pos, worldIn.rand);
		worldIn.destroyBlock(pos, true);
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return canStay(worldIn, pos);
	}
}
