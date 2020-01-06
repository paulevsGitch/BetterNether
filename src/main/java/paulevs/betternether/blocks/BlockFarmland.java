package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.sound.BlockSoundGroup;

public class BlockFarmland extends BlockBase
{
	public BlockFarmland()
	{
		super(FabricBlockSettings.of(Material.WOOD)
				.materialColor(MaterialColor.LIME_TERRACOTTA)
				.sounds(BlockSoundGroup.WET_GRASS)
				.hardness(2F)
				.resistance(0.5F)
				.build());
	}
}
