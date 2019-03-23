package paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import paulevs.betternether.BetterNether;
import paulevs.betternether.tileentities.TileEntityChestUniversal;

public class BlockChestOfDrawers extends BlockInventoryUniversal
{
	private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0, 0, 0.5, 1, 1, 1);
	private static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0, 0, 0, 1, 1, 0.5);
	private static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0, 0, 0, 0.5, 1, 1);
	private static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.5, 0, 0, 1, 1, 1);
	
	public BlockChestOfDrawers()
	{
		super(Material.IRON);
		this.setHardness(3.0F);
		this.setResistance(10.0F);
		this.setSoundType(SoundType.METAL);
		this.setUnlocalizedName("chest_of_drawers");
		this.setRegistryName("chest_of_drawers");
		this.setCreativeTab(BetterNether.BN_TAB);
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
        switch (state.getValue(FACING))
        {
        case NORTH:
        	return NORTH_AABB;
        case SOUTH:
        	return SOUTH_AABB;
        case EAST:
        	return EAST_AABB;
        case WEST:
        default:
        	return WEST_AABB;
        }
    }
}
