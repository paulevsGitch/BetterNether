package paulevs.betternether.blocks.materials;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.sound.BlockSoundGroup;

public class Materials
{
	public static final Material COMMON_WOOD = new Material.Builder(MaterialColor.WOOD).build();
	public static final Material COMMON_GRASS = new Material.Builder(MaterialColor.FOLIAGE).allowsMovement().notSolid().replaceable().build();
	
	public static FabricBlockSettings makeWood(MaterialColor color)
	{
		return FabricBlockSettings.of(COMMON_WOOD)
				.sounds(BlockSoundGroup.WOOD)
				.breakByTool(FabricToolTags.AXES)
				.hardness(1)
				.materialColor(color);
	}
	
	public static FabricBlockSettings makeGrass(MaterialColor color)
	{
		return FabricBlockSettings.of(COMMON_GRASS)
				.sounds(BlockSoundGroup.GRASS)
				.materialColor(color)
				.noCollision()
				.nonOpaque()
				.breakInstantly();
	}
}
