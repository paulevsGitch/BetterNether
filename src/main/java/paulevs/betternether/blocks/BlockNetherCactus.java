package paulevs.betternether.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import paulevs.betternether.BetterNether;

public class BlockNetherCactus extends Block
{
	public static final PropertyEnum<BlockNetherCactus.EnumShape> SHAPE = PropertyEnum.<BlockNetherCactus.EnumShape>create("shape", BlockNetherCactus.EnumShape.class);
	private static final AxisAlignedBB TOP_AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.5, 0.75);
	private static final AxisAlignedBB SIDE_AABB = new AxisAlignedBB(0.3125, 0.0, 0.3125, 0.6875, 1.0, 0.6875);

	public BlockNetherCactus()
	{
		super(Material.CACTUS, MapColor.ORANGE_STAINED_HARDENED_CLAY);
		this.setCreativeTab(BetterNether.BN_TAB);
		this.setRegistryName("nether_cactus");
		this.setUnlocalizedName("nether_cactus");
		this.setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, EnumShape.TOP));
		this.setSoundType(SoundType.CLOTH);
	}

	public static enum EnumShape implements IStringSerializable
	{
		TOP(0, "top"),
		SIDE(1, "side");

		private final String name;
		private final int index;

		private EnumShape(int index, String name)
		{
			this.name = name;
			this.index = index;
		}

		public String toString()
		{
			return this.name;
		}

		public String getName()
		{
			return this.name;
		}

		public int getIndex()
		{
			return this.index;
		}
	}

	public IBlockState getStateFromMeta(int meta)
	{
		EnumShape shape = EnumShape.TOP;
		if (meta == 1)
			shape = EnumShape.SIDE;
		return this.getDefaultState().withProperty(SHAPE, shape);
	}

	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(SHAPE).getIndex();
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {SHAPE});
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
		return state.getValue(SHAPE).getIndex() == 0 ? TOP_AABB : SIDE_AABB;
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
		if (canStay(worldIn, pos))
		{
			if (worldIn.getBlockState(pos.up()).getBlock() != this)
				worldIn.setBlockState(pos, state.withProperty(SHAPE, EnumShape.TOP));
			else
				worldIn.setBlockState(pos, state.withProperty(SHAPE, EnumShape.SIDE));
		}
	}

	private boolean canStay(World worldIn, BlockPos pos)
	{
		Block under = worldIn.getBlockState(pos.down()).getBlock();
		if (under != this && under != Blocks.GRAVEL)
		{
			worldIn.destroyBlock(pos, true);
			return false;
		}
		return true;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		IBlockState normal = this.getDefaultState().withProperty(SHAPE, EnumShape.SIDE);
		if (this.canStay(worldIn, pos))
		{
			if (worldIn.isAirBlock(pos.up()))
			{
				int i;

				for (i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this; ++i);

				if (i < 3)
				{
					if(net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, true))
					{
						if (rand.nextInt(16) == 0)
						{
							worldIn.setBlockState(pos.up(), this.getDefaultState());
							worldIn.setBlockState(pos, normal, 2);
						}
						net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
					}
				}
			}
		}
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		Block under = worldIn.getBlockState(pos.down()).getBlock();
		return under == this || under == Blocks.GRAVEL;
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		entityIn.attackEntityFrom(DamageSource.CACTUS, 1.5F);
	}
}
