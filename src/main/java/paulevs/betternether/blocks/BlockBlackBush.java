package paulevs.betternether.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import paulevs.betternether.BetterNether;

public class BlockBlackBush extends Block
{
	public static final PropertyInteger SIZE = PropertyInteger.create("size", 0, 3);
	private static final AxisAlignedBB COLLIDE_AABB = new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 0.625, 0.8125);
	
	public BlockBlackBush()
	{
		super(Material.PLANTS, MapColor.OBSIDIAN);
		this.setCreativeTab(BetterNether.BN_TAB);
		this.setHardness(1.0F);
		this.setSoundType(SoundType.PLANT);
		this.setRegistryName("black_bush");
		this.setUnlocalizedName("black_bush");
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
	
	private boolean canStay(World world, BlockPos pos)
	{
		Block under = world.getBlockState(pos.down()).getBlock();
		return under instanceof BlockNetherrack || under == Blocks.SOUL_SAND;
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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return COLLIDE_AABB;
	}
	
	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}
	
	@Override
	public Block.EnumOffsetType getOffsetType()
    {
		return Block.EnumOffsetType.XZ;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
		Block under = worldIn.getBlockState(pos.down()).getBlock();
		return under instanceof BlockNetherrack || under == Blocks.SOUL_SAND;
    }
}
