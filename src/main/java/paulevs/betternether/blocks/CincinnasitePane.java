package paulevs.betternether.blocks;

import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import paulevs.betternether.BetterNether;

public class CincinnasitePane extends BlockPane
{
	protected CincinnasitePane(String name)
	{
		super(Material.IRON, true);
		this.setHardness(0.5F);
		this.setResistance(3.0F);
		this.setSoundType(SoundType.METAL);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(BetterNether.BN_TAB);
	}
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
		return blockAccess.getBlockState(pos.offset(side)).getBlock() == this ? !blockState.equals(blockAccess.getBlockState(pos)) : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
}