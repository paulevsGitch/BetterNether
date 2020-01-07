package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.MaterialColor;
import paulevs.betternether.blocks.materials.Materials;

public class BlockFarmland extends BlockBase
{
	public BlockFarmland()
	{
		super(FabricBlockSettings.copyOf(Materials.COMMON_WOOD)
				.materialColor(MaterialColor.LIME_TERRACOTTA)
				.build());
	}
}
