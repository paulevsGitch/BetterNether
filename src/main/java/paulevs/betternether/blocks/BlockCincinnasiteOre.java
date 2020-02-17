package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.sound.BlockSoundGroup;

public class BlockCincinnasiteOre extends OreBlock
{
	public BlockCincinnasiteOre()
	{
		super(FabricBlockSettings.of(Material.STONE)
				.hardness(3F)
				.resistance(5F)
				.sounds(BlockSoundGroup.STONE)
				.build());
	}
}
