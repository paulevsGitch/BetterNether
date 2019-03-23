package paulevs.betternether.blocks;

import java.util.Random;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCincinnasitFireBowl extends BlockCincinnasite
{
	public static final PropertyBool STATE = PropertyBool.create("state");
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 0.75, 1);
	
	public BlockCincinnasitFireBowl()
	{
		super("cincinnasite_fire_bowl");
	}
	
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return state.getValue(STATE).booleanValue() ? 15 : 0;
    }
	
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(STATE, meta == 1);
	}

	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(STATE).booleanValue() ? 1 : 0;
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {STATE});
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
	public int damageDropped(IBlockState state)
    {
        return 0;
    }
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if (!world.isRemote && facing == EnumFacing.UP)
		{
			if (player.getHeldItemMainhand().getItem() instanceof ItemFlintAndSteel)
	        {
	        	world.setBlockState(pos, state.withProperty(STATE, !state.getValue(STATE).booleanValue()));
	        	if (!player.isCreative())
	        		player.getHeldItemMainhand().attemptDamageItem(1, world.rand, (EntityPlayerMP) player);
	        	return true;
	        }
			else if (player.getHeldItemMainhand().isEmpty() && state.getValue(STATE).booleanValue())
			{
				world.setBlockState(pos, state.withProperty(STATE, false));
				return true;
			}
		}
		return false;
    }
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return AABB;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
	
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random)
    {
		if (state.getValue(STATE).booleanValue())
		{
			if (random.nextInt(24) == 0)
			{
				world.playSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
			}
			for (int i = 0; i < 3; ++i)
			{
				double d0 = (double)pos.getX() + random.nextDouble();
				double d1 = (double)pos.getY() + random.nextDouble() * 0.18D + 0.82;
				double d2 = (double)pos.getZ() + random.nextDouble();
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
			}
		}
    }
}
