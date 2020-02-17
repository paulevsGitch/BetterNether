package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.sound.BlockSoundGroup;

public class BlockAshVineLeaves extends BlockBaseNotFull
{
	public BlockAshVineLeaves()
	{
		super(FabricBlockSettings.of(Material.PLANT)
				.materialColor(MaterialColor.LIGHT_GRAY)
				.sounds(BlockSoundGroup.GRASS)
				.nonOpaque()
				.hardness(0.4F)
				.build());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
	}
}
