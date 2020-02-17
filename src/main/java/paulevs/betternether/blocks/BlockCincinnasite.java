package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.sound.BlockSoundGroup;

public class BlockCincinnasite extends BlockBase
{
	public BlockCincinnasite()
	{
		super(FabricBlockSettings.of(Material.METAL)
				.materialColor(MaterialColor.YELLOW)
				.hardness(3F)
				.resistance(10F)
				.sounds(BlockSoundGroup.METAL)
				.build());
	}
}
