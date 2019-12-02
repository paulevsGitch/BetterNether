package paulevs.betternether.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import paulevs.betternether.tileentities.TileEntityChestUniversal;

public class BlockInventoryUniversal extends BlockContainer
{
	public static final PropertyBool OPEN = PropertyBool.create("open");
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public BlockInventoryUniversal(Material materialIn)
	{
		super(materialIn);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityChestUniversal();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote)
		{
			return true;
		}
		else
		{
			ILockableContainer ilockablecontainer = (ILockableContainer) worldIn.getTileEntity(pos);
			if (ilockablecontainer != null)
				playerIn.displayGUIChest(ilockablecontainer);
			return true;
		}
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
	
	@Override
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING, OPEN});
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta >> 1);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(OPEN, (meta & 1) == 1);
    }
	
	@Override
    public int getMetaFromState(IBlockState state)
    {
        return (((EnumFacing)state.getValue(FACING)).getIndex() << 1) | (state.getValue(OPEN) ? 1 : 0);
    }
    
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        if (stack.hasDisplayName())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityChestUniversal)
            {
                ((TileEntityChestUniversal) tileentity).setCustomName(stack.getDisplayName());
            }
        }
    }
}
