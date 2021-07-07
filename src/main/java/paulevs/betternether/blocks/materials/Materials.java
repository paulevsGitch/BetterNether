package paulevs.betternether.blocks.materials;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class Materials {
	public static final Material COMMON_WOOD = new Material.Builder(MaterialColor.WOOD).build();
	public static final Material COMMON_GRASS = new Material.Builder(MaterialColor.PLANT).noCollider().nonSolid().replaceable().build();
	public static final Material COMMON_LEAVES = new Material.Builder(MaterialColor.PLANT).nonSolid().build();

	public static FabricBlockSettings makeWood(MaterialColor color) {
		return FabricBlockSettings.of(Material.NETHER_WOOD)
				.sound(SoundType.WOOD)
				.breakByTool(FabricToolTags.AXES)
				.destroyTime(1)
				.materialColor(color);
	}

	public static FabricBlockSettings makeGrass(MaterialColor color) {
		return FabricBlockSettings.of(COMMON_GRASS)
				.isValidSpawn((state, world, pos, type) -> {
					return true;
				})
				.sound(SoundType.GRASS)
				.materialColor(color)
				.noCollission()
				.noOcclusion()
				.instabreak();
	}

	public static Properties makeLeaves(MaterialColor color) {
		return FabricBlockSettings.of(COMMON_LEAVES, color)
				.breakByHand(true)
				.breakByTool(FabricToolTags.SHEARS)
				.sound(SoundType.GRASS)
				.noOcclusion()
				.strength(0.2F)
				.isValidSpawn((state, world, pos, type) -> {
					return false;
				})
				.isSuffocating((state, worls, pos) -> {
					return false;
				})
				.isViewBlocking((state, worls, pos) -> {
					return false;
				});
	}
}
