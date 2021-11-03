package paulevs.betternether.blocks.materials;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class Materials {
	public static FabricBlockSettings makeWood(MaterialColor color) {
		return FabricBlockSettings.of(Material.NETHER_WOOD)
				.breakByTool(FabricToolTags.AXES)
				.mapColor(color)
				.sounds(SoundType.WOOD)
				.hardness(1);
	}

	public static FabricBlockSettings makeGrass(MaterialColor color) {
		return FabricBlockSettings.of(Material.GRASS)
				.allowsSpawning((state, world, pos, type) -> {
					return true;
				})
				.sounds(SoundType.GRASS)
				.mapColor(color)
				.noCollision()
				.nonOpaque()
				.breakInstantly();
	}
}
