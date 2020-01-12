package paulevs.betternether.blocks;

import net.minecraft.block.MaterialColor;
import net.minecraft.block.PillarBlock;
import paulevs.betternether.blocks.materials.Materials;

public class BlockReedsBlock extends BNPillar
{
	public BlockReedsBlock()
	{
		super(Materials.makeWood(MaterialColor.CYAN).build());
	}
}
