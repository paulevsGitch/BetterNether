package paulevs.betternether.blocks.materials;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.sound.BlockSoundGroup;

public class Materials
{
	public static final Material COMMON_WOOD = new Material.Builder(MaterialColor.WOOD).build();
	
	public static FabricBlockSettings makeWood(MaterialColor color)
	{
		return FabricBlockSettings.of(Materials.COMMON_WOOD)
				.sounds(BlockSoundGroup.WOOD)
				.breakByTool(FabricToolTags.AXES)
				.hardness(1)
				.materialColor(color);
	}
}
