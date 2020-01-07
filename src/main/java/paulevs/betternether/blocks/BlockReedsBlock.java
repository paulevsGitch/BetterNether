package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.PillarBlock;
import paulevs.betternether.blocks.materials.Materials;

public class BlockReedsBlock extends PillarBlock
{
	public BlockReedsBlock()
	{
		super(FabricBlockSettings.copyOf(Materials.COMMON_WOOD)
				.materialColor(MaterialColor.CYAN)
				.build());
	}
}
