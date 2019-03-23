package paulevs.betternether.blocks;

import net.minecraft.block.BlockLadder;
import net.minecraft.block.SoundType;
import paulevs.betternether.BetterNether;

public class BlockReedsLadder extends BlockLadder
{
	public BlockReedsLadder()
	{
		super();
		this.setRegistryName("reeds_ladder");
		this.setUnlocalizedName("reeds_ladder");
		this.setCreativeTab(BetterNether.BN_TAB);
		this.setHardness(0.4F);
		this.setSoundType(SoundType.LADDER);
	}
}
