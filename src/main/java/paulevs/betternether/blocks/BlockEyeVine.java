package paulevs.betternether.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEyeVine extends Block
{
	private static final AxisAlignedBB SELECT_AABB = new AxisAlignedBB(0.25, 0, 0.25, 0.75, 1, 0.75);
	
	public BlockEyeVine()
	{
		super(Material.PLANTS, MapColor.RED);
		this.setRegistryName("eye_vine");
		this.setUnlocalizedName("eye_vine");
		this.setSoundType(SoundType.PLANT);
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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SELECT_AABB;
    }

	@Override
	public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity)
	{
		return true;
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
		//if (pos.getX() == fromPos.getX() && pos.getZ() == fromPos.getZ() && pos.getY() < fromPos.getY() && (blockIn != this && blockIn != Blocks.NETHERRACK))
		//{
		//	worldIn.destroyBlock(pos, false);
		//}
		Block up = worldIn.getBlockState(pos.up()).getBlock();
		if (up != this && up != Blocks.NETHERRACK)
		{
			worldIn.destroyBlock(pos, false);
			worldIn.scheduleBlockUpdate(pos.down(), Blocks.AIR, 0, 0);
		}
    }
	
	@Override
	public int quantityDropped(Random random)
    {
        return 0;
    }
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
