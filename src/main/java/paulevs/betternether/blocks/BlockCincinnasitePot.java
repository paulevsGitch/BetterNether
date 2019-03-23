package paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCincinnasitePot extends BlockCincinnasite
{
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 0.5, 0.8125);
	
	public BlockCincinnasitePot()
	{
		super("cincinnasite_pot");
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return AABB;
	}

	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (player.getHeldItemMainhand().getItem() instanceof ItemBlock &&
		facing == EnumFacing.UP && world.getBlockState(pos.up()).getBlock() == Blocks.AIR)
		{
			Block b = ((ItemBlock) player.getHeldItemMainhand().getItem()).getBlock();
			if (BlocksRegister.BLOCK_POTTED_PLANT != null && ((BlockPottedPlant1) BlocksRegister.BLOCK_POTTED_PLANT).hasPlant(b))
			{
				IBlockState state2 = ((BlockPottedPlant1) BlocksRegister.BLOCK_POTTED_PLANT).getBlockState(b);
				world.setBlockState(pos.up(), state2);
				world.playSound(
						pos.getX() + 0.5,
						pos.getY() + 1.5,
						pos.getZ() + 0.5,
						b.getSoundType(state2, world, pos, player).getPlaceSound(),
						SoundCategory.BLOCKS,
						0.8F,
						1.0F,
						true);
				if (!player.isCreative())
					player.getHeldItemMainhand().shrink(1);
				return true;
			}
		}
		return false;
	}
}
