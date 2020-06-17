package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.sound.BlockSoundGroup;

public class BlockOre extends OreBlock
{
	public BlockOre()
	{
		super(FabricBlockSettings.of(Material.STONE)
				.requiresTool()
				.hardness(3F)
				.resistance(5F)
				.sounds(BlockSoundGroup.NETHERRACK));
	}
}
