package paulevs.betternether.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import paulevs.betternether.BetterNether;

public class BlockNetherGrass extends BlockBush implements net.minecraftforge.common.IShearable
{
	protected static final AxisAlignedBB GRASS_AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.75, 0.75);
	
	public BlockNetherGrass()
	{
		super();
		this.setRegistryName("nether_grass");
		this.setUnlocalizedName("nether_grass");
		this.setCreativeTab(BetterNether.BN_TAB);
		this.setSoundType(SoundType.PLANT);
	}
	
	@Override
	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
        return true;
    }
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return GRASS_AABB;
    }
	
	@Override
	protected boolean canSustainBush(IBlockState state)
    {
        return state.getBlock() instanceof BlockNetherrack || state.getBlock() instanceof BlockSoulSand;
    }

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
	{
		return NonNullList.withSize(1, new ItemStack(this, 1));
	}
	
	@Override
	public int quantityDropped(Random random)
    {
        return 0;
    }
	
	@Override
	public Block.EnumOffsetType getOffsetType()
    {
		return Block.EnumOffsetType.XZ;
    }
}
