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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStalagnateBase extends Block
{
	private static final AxisAlignedBB COLLIDE_AABB = new AxisAlignedBB(0.3125, 0, 0.3125, 0.6875, 1, 0.6875);
	private static final AxisAlignedBB SELECT_AABB = new AxisAlignedBB(0.25, 0, 0.25, 0.75, 1, 0.75);

	public BlockStalagnateBase()
	{
		super(Material.WOOD, MapColor.LIME_STAINED_HARDENED_CLAY);
		this.setUnlocalizedName("stalagnate");
		this.setSoundType(SoundType.WOOD);
		this.setHardness(2.0F);
		this.setResistance(2F);
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
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return COLLIDE_AABB;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SELECT_AABB;
    }
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();

        if (side == EnumFacing.UP || side == EnumFacing.DOWN)
        	if (iblockstate == blockState)
        		return false;
        return true;
    }
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(BlocksRegister.BLOCK_STALAGNATE_STEM);
    }
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(BlocksRegister.BLOCK_STALAGNATE_STEM);
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
    {
		if (worldIn.rand.nextInt(4) == 0)
			spawnSeeds(worldIn, pos);
		worldIn.destroyBlock(pos, true);
    }
	
	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn)
    {
		if (worldIn.rand.nextInt(4) == 0)
			spawnSeeds(worldIn, pos);
		worldIn.destroyBlock(pos, true);
    }
	
	private void spawnSeeds(World world, BlockPos pos)
	{
		ItemStack drop = new ItemStack(BlocksRegister.BLOCK_STALAGNATE_SEED, 1);
		EntityItem itemEntity = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop);
		world.spawnEntity(itemEntity);
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return face != EnumFacing.UP && face != EnumFacing.DOWN ? BlockFaceShape.MIDDLE_POLE_THICK : BlockFaceShape.CENTER_BIG;
    }
}
