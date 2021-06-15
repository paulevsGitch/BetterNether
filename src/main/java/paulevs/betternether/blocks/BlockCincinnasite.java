package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class BlockCincinnasite extends BlockBase {
	public BlockCincinnasite() {
		super(FabricBlockSettings.of(Material.METAL)
				.materialColor(MapColor.YELLOW)
				.hardness(3F)
				.resistance(10F)
				.requiresTool()
				.sounds(BlockSoundGroup.METAL));
	}
}
