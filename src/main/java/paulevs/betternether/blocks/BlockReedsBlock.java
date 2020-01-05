package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.sound.BlockSoundGroup;

public class BlockReedsBlock extends PillarBlock
{
	public BlockReedsBlock()
	{
		super(FabricBlockSettings.of(Material.WOOD)
				.materialColor(MaterialColor.CYAN)
				.sounds(BlockSoundGroup.WOOD)
				.hardness(2F)
				.build());
	}
}
