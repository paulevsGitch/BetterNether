package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.sound.BlockSoundGroup;

public class BlockStalagnateBark extends PillarBlock
{
	public BlockStalagnateBark()
	{
		super(FabricBlockSettings.of(Material.WOOD)
				.materialColor(MaterialColor.LIME_TERRACOTTA)
				.sounds(BlockSoundGroup.WOOD)
				.hardness(2F)
				.build());
	}
}