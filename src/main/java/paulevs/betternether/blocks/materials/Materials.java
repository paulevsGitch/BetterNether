package paulevs.betternether.blocks.materials;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block.Settings;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class Materials
{
	public static final Settings COMMON_WOOD = FabricBlockSettings.of(Material.ORGANIC)
			.sounds(BlockSoundGroup.WOOD)
			.hardness(2F)
			.resistance(0.5F)
			.breakByTool(FabricToolTags.AXES)
			.build();
}
