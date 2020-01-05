package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.sound.BlockSoundGroup;

public class BlockStalagnatePlanks extends BlockBase
{
	public BlockStalagnatePlanks()
	{
		super(FabricBlockSettings.of(Material.WOOD)
				.materialColor(MaterialColor.LIME_TERRACOTTA)
				.sounds(BlockSoundGroup.WOOD)
				.hardness(2F)
				.build());
	}
}
