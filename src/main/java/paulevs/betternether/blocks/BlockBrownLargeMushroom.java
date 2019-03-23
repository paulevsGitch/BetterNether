package paulevs.betternether.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
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

public class BlockBrownLargeMushroom extends Block
{
	public static enum EnumShape implements IStringSerializable
	{
		TOP("top"),
		SIDE_N("side_n"),
		SIDE_S("side_s"),
		SIDE_E("side_e"),
		SIDE_W("side_w"),
		CORNER_N("corner_n"),
		CORNER_S("corner_s"),
		CORNER_E("corner_e"),
		CORNER_W("corner_w"),
		MIDDLE("middle"),
		BOTTOM("bottom");

		private final String name;
		private final int index;

		private EnumShape(String name)
		{
			this.name = name;
			this.index = this.ordinal();
		}

		public int getIndex()
		{
			return this.index;
		}

		public String toString()
		{
			return this.name;
		}

		public String getName()
		{
			return this.name;
		}
	}

	private static final EnumShape[] SHAPES = EnumShape.values();
	public static final PropertyEnum<EnumShape> SHAPE = PropertyEnum.<EnumShape>create("shape", EnumShape.class);
	private static final AxisAlignedBB BOTTOM_AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 1.0, 0.75);
	private static final AxisAlignedBB TOP_CENTER_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
	private static final AxisAlignedBB TOP_SIDE_AABB = new AxisAlignedBB(0.0, 0.5, 0.0, 1.0, 1.0, 1.0);

	public BlockBrownLargeMushroom()
	{
		super(Material.WOOD, MapColor.BROWN_STAINED_HARDENED_CLAY);
		this.setRegistryName("brown_large_mushroom");
		this.setUnlocalizedName("brown_large_mushroom");
		this.setHardness(0.25F);
		this.setSoundType(SoundType.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, EnumShape.BOTTOM));
	}

	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(SHAPE, SHAPES[meta]);
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
		if (state.getValue(SHAPE) == EnumShape.TOP)
			return TOP_CENTER_AABB;
		else if (state.getValue(SHAPE).name.contains("side") || state.getValue(SHAPE).name.contains("corner"))
			return TOP_SIDE_AABB;
		else
			return BOTTOM_AABB;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		if (state.getValue(SHAPE) == EnumShape.TOP || state.getValue(SHAPE).name.contains("side") || state.getValue(SHAPE).name.contains("corner"))
			if (face == EnumFacing.UP)
				return BlockFaceShape.SOLID;
			else
				return BlockFaceShape.UNDEFINED;
		else
			if (face == EnumFacing.DOWN || face == EnumFacing.UP)
				return BlockFaceShape.MIDDLE_POLE;
			else
				return BlockFaceShape.UNDEFINED;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(Blocks.BROWN_MUSHROOM);
	}

	@Override
	public int quantityDropped(Random random)
	{
		return random.nextInt(3);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(Blocks.BROWN_MUSHROOM);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.SOLID;
	}
	
	/*public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        if (face == EnumFacing.UP || face == EnumFacing.DOWN)
        {
        	if (world.getBlockState(pos).getBlock() == this && (state.getValue(SHAPE) == EnumShape.BOTTOM || state.getValue(SHAPE) == EnumShape.MIDDLE))
        		return false;
        	else
        		return state.isOpaqueCube();
        }
        else
        {
        	if (world.getBlockState(pos).getBlock() == this)
        	{
        		String name = state.getValue(SHAPE).name;
        		return !(name.contains("side") || name.contains("corner"));
        	}
        	else
        		return state.isOpaqueCube();
        }
    }*/
}
