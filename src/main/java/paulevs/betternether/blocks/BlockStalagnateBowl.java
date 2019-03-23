package paulevs.betternether.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import paulevs.betternether.items.ItemsRegister;

public class BlockStalagnateBowl extends Block
{
	public static enum EnumFood implements IStringSerializable
	{
		NONE("none"),
		WART("wart"),
		MUSHROOM("mushroom"),
		APPLE("apple");

		private final String name;
		private final int index;

		private EnumFood(String name)
		{
			this.name = name;
			this.index = this.ordinal();
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
		
		public Item getItem()
		{
			switch(this)
			{
			case NONE:
				return ItemsRegister.STALAGNATE_BOWL;
			case WART:
				return ItemsRegister.STALAGNATE_BOWL_WART;
			case MUSHROOM:
				return ItemsRegister.STALAGNATE_BOWL_MUSHROOM;
			case APPLE:
				return ItemsRegister.STALAGNATE_BOWL_APPLE;
			}
			return ItemsRegister.STALAGNATE_BOWL;
		}
	}
	
	public static final PropertyEnum<EnumFood> FOOD = PropertyEnum.<EnumFood>create("food", EnumFood.class);
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.3125, 0, 0.3125, 0.6875, 0.1875, 0.6875);
	
	public BlockStalagnateBowl()
	{
		super(Material.WOOD, MapColor.LIME_STAINED_HARDENED_CLAY);
		this.setRegistryName("stalagnate_bowl_placed");
		this.setUnlocalizedName("stalagnate_bowl_placed");
		this.setHardness(0.1F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FOOD, EnumFood.NONE));
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
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
	
	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB;
    }
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
		return state.getValue(FOOD).getItem();
    }
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(state.getValue(FOOD).getItem());
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
		return BlockFaceShape.UNDEFINED;
    }
	
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(FOOD, EnumFood.values()[meta]);
	}

	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FOOD).index;
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {FOOD});
	}
}
