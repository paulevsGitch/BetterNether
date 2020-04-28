package paulevs.betternether.blocks.materials;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block.Settings;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.sound.BlockSoundGroup;
import paulevs.betternether.BetterNether;

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
				.sounds(BlockSoundGroup.CROP)
				.materialColor(color)
				.noCollision()
				.breakInstantly();
	}
	
	public static Settings makeLeaves(MaterialColor color)
	{
		return FabricBlockSettings.of(COMMON_WOOD, color)
				.breakByHand(true)
				.breakByTool(BetterNether.SHEARS)
				.sounds(BlockSoundGroup.GRASS)
				.nonOpaque()
				.strength(0.2F, 0F)
				.build();
	}
}
