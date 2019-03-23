package paulevs.betternether.blocks;

import net.minecraft.block.BlockNetherBrick;
import net.minecraft.block.SoundType;
import paulevs.betternether.BetterNether;

public class BNNetherBrick extends BlockNetherBrick
{
	public BNNetherBrick(String name)
	{
		super();
		this.setCreativeTab(BetterNether.BN_TAB);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setHardness(2.0F);
		this.setResistance(10.0F);
		this.setSoundType(SoundType.STONE);
	}
}
