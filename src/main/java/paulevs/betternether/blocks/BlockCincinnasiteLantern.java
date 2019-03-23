package paulevs.betternether.blocks;

import net.minecraft.block.SoundType;

public class BlockCincinnasiteLantern extends BlockCincinnasite
{

	public BlockCincinnasiteLantern()
	{
		super("cincinnasite_lantern");
		this.setLightLevel(1.0F);
		this.setSoundType(SoundType.GLASS);
	}

}
