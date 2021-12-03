package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import ru.bclib.blocks.BaseOreBlock;

public class BlockOre extends BaseOreBlock {
	
	public BlockOre(Item drop, int minCount, int maxCount, int experience, int miningLevel) {
		super(
			drop,
			minCount,
			maxCount,
			experience,
			miningLevel,
			
			FabricBlockSettings.of(Material.STONE, MaterialColor.COLOR_RED)
				.hardness(3F)
				.resistance(5F)
				.requiresTool()
				
							   .sounds(SoundType.NETHERRACK));
	}

}
