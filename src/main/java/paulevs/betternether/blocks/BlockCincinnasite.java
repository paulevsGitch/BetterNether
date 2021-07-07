package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class BlockCincinnasite extends BlockBase {
	public BlockCincinnasite() {
		super(FabricBlockSettings.of(Material.METAL)
				.materialColor(MaterialColor.COLOR_YELLOW)
				.destroyTime(3F)
				.explosionResistance(10F)
				.requiresCorrectToolForDrops()
				.sound(SoundType.METAL));
	}
}
