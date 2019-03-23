package paulevs.betternether.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import paulevs.betternether.BetterNether;

public class BlockBarrelCactus extends Block
{
	private static final AxisAlignedBB BARREL_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.875, 1.0);
	private static final AxisAlignedBB BARREL_COLLIDE_AABB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.8125, 0.9375);
	
	public BlockBarrelCactus()
	{
		super(Material.CACTUS, MapColor.ORANGE_STAINED_HARDENED_CLAY);
		this.setCreativeTab(BetterNether.BN_TAB);
		this.setRegistryName("barrel_cactus");
		this.setUnlocalizedName("barrel_cactus");
		this.setTickRandomly(true);
		this.setSoundType(SoundType.CLOTH);
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
		return BARREL_AABB;
	}
	
	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return BARREL_COLLIDE_AABB;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		canStay(worldIn, pos);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

	private boolean canStay(World worldIn, BlockPos pos)
	{
		Block under = worldIn.getBlockState(pos.down()).getBlock();
		if (under != Blocks.GRAVEL)
		{
			worldIn.destroyBlock(pos, true);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
		return worldIn.getBlockState(pos.down()).getBlock() == Blocks.GRAVEL;
    }
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        entityIn.attackEntityFrom(DamageSource.CACTUS, 1.5F);
    }
	
	@Override
	public Block.EnumOffsetType getOffsetType()
    {
		return Block.EnumOffsetType.XYZ;
    }
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
