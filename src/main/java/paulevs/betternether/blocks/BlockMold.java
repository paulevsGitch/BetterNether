package paulevs.betternether.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
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

public class BlockMold extends Block
{
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.5, 0.75);

	public BlockMold(String name)
	{
		super(Material.PLANTS, MapColor.GRASS);
		this.setCreativeTab(BetterNether.BN_TAB);
		this.setSoundType(SoundType.PLANT);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setTickRandomly(true);
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
		return AABB;
	}
	
	@Override
	@Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }
	
	private boolean canStay(World worldIn, BlockPos pos)
	{
		return worldIn.getBlockState(pos.down()).getBlock() instanceof BlockNetherMycelium;
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
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (!this.canStay(worldIn, pos))
		{
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
		else if (rand.nextInt(16) == 0)
		{
			int c = 0;
			c = worldIn.getBlockState(pos.north()) == this.getDefaultState() ? c++ : c;
			c = worldIn.getBlockState(pos.south()) == this.getDefaultState() ? c++ : c;
			c = worldIn.getBlockState(pos.east()) == this.getDefaultState() ? c++ : c;
			c = worldIn.getBlockState(pos.west()) == this.getDefaultState() ? c++ : c;
			if (c < 2)
			{
				BlockPos npos = new BlockPos(pos);
				switch (rand.nextInt(4))
				{
				case 0:
					npos = npos.add(-1, 0, 0);
					break;
				case 1:
					npos = npos.add(1, 0, 0);
					break;
				case 2:
					npos = npos.add(0, 0, -1);
					break;
				default:
					npos = npos.add(0, 0, 1);
					break;
				}
				if (worldIn.getBlockState(npos).getBlock() == Blocks.AIR && worldIn.getBlockState(npos.down()).getBlock() instanceof BlockNetherMycelium)
				{
					worldIn.setBlockState(npos, this.getDefaultState());
				}
			}
		}
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return canStay(worldIn, pos);
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
}
