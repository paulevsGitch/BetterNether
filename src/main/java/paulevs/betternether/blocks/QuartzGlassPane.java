package paulevs.betternether.blocks;

import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import paulevs.betternether.BetterNether;

public class QuartzGlassPane extends BlockPane
{
	protected QuartzGlassPane(String name)
	{
		super(Material.GLASS, true);
		this.setHardness(0.5F);
		this.setResistance(3.0F);
		this.setSoundType(SoundType.GLASS);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(BetterNether.BN_TAB);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
		return blockAccess.getBlockState(pos.offset(side)).getBlock() == this ? !blockState.equals(blockAccess.getBlockState(pos)) : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
}
