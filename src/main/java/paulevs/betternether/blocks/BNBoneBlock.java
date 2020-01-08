package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class BNBoneBlock extends Block
{
	public BNBoneBlock()
	{
		super(FabricBlockSettings.copy(Blocks.BONE_BLOCK).build());
	}
}
