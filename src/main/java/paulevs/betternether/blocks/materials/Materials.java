package paulevs.betternether.blocks.materials;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class Materials {
	public static final Material COMMON_WOOD = new Material.Builder(MapColor.OAK_TAN).build();
	public static final Material COMMON_GRASS = new Material.Builder(MapColor.DARK_GREEN).allowsMovement().notSolid().replaceable().build();
	public static final Material COMMON_LEAVES = new Material.Builder(MapColor.DARK_GREEN).notSolid().build();

	public static FabricBlockSettings makeWood(MapColor color) {
		return FabricBlockSettings.of(Material.NETHER_WOOD)
				.sounds(BlockSoundGroup.WOOD)
				.breakByTool(FabricToolTags.AXES)
				.hardness(1)
				.materialColor(color);
	}

	public static FabricBlockSettings makeGrass(MapColor color) {
		return FabricBlockSettings.of(COMMON_GRASS)
				.allowsSpawning((state, world, pos, type) -> {
					return true;
				})
				.sounds(BlockSoundGroup.GRASS)
				.materialColor(color)
				.noCollision()
				.nonOpaque()
				.breakInstantly();
	}

	public static Settings makeLeaves(MapColor color) {
		return FabricBlockSettings.of(COMMON_LEAVES, color)
				.breakByHand(true)
				.breakByTool(FabricToolTags.SHEARS)
				.sounds(BlockSoundGroup.GRASS)
				.nonOpaque()
				.strength(0.2F)
				.allowsSpawning((state, world, pos, type) -> {
					return false;
				})
				.suffocates((state, worls, pos) -> {
					return false;
				})
				.blockVision((state, worls, pos) -> {
					return false;
				});
	}
}
