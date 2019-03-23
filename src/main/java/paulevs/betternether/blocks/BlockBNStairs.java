package paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.init.Blocks;
import paulevs.betternether.BetterNether;

public class BlockBNStairs extends BlockStairs
{
	protected BlockBNStairs(String name, Block block)
	{
		super(block == null ? Blocks.AIR.getDefaultState() : block.getDefaultState());
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(BetterNether.BN_TAB);
		this.useNeighborBrightness = true;
	}
}
