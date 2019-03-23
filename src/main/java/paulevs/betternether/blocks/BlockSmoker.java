package paulevs.betternether.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import paulevs.betternether.BetterNether;

public class BlockSmoker extends Block
{
	public static enum EnumShape implements IStringSerializable
	{
		TOP(0, "top"),
		MIDDLE(1, "middle"),
		BOTTOM(2, "bottom");

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
	
	public static final PropertyEnum<EnumShape> SHAPE = PropertyEnum.<EnumShape>create("shape", EnumShape.class);
	private static AxisAlignedBB MIDDLE_AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 1.0, 0.75);
	private static AxisAlignedBB TOP_AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.5, 0.75);
	
	public BlockSmoker()
	{
		super(Material.WOOD, MapColor.BROWN);
		this.setCreativeTab(BetterNether.BN_TAB);
		this.setHardness(2.0F);
		this.setRegistryName("smoker");
		this.setUnlocalizedName("smoker");
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, EnumShape.TOP));
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return ((EnumShape) this.getActualState(state, source, pos).getValue(SHAPE)) == EnumShape.TOP ? TOP_AABB : MIDDLE_AABB;
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
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.up()).getBlock() == this ? (worldIn.getBlockState(pos.down()).getBlock() == this ? state.withProperty(SHAPE, EnumShape.MIDDLE) : state.withProperty(SHAPE, EnumShape.BOTTOM)) : state.withProperty(SHAPE, EnumShape.TOP);
    }
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}
	
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {SHAPE});
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
		if (worldIn.isAirBlock(pos.up()))
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0, 0);
    }
	
	private void checkDrop(World world, BlockPos pos)
	{
		if (!world.getBlockState(pos.down()).getMaterial().isSolid() && world.getBlockState(pos.down()).getBlock() != this)
			world.destroyBlock(pos, true);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
		checkDrop(worldIn, pos);
    }
}
