package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import ru.bclib.api.tag.NamedCommonBlockTags;
import ru.bclib.api.tag.NamedMineableTags;
import ru.bclib.api.tag.TagAPI;
import ru.bclib.blocks.BaseOreBlock;
import ru.bclib.interfaces.TagProvider;

import java.util.List;

public class BlockOre extends BaseOreBlock implements TagProvider {
	
	public BlockOre(Item drop, int minCount, int maxCount, int experience, int miningLevel) {
		super(
			FabricBlockSettings.of(Material.STONE, MaterialColor.COLOR_RED)
							   .hardness(3F)
							   .resistance(5F)
							   .requiresTool()
							   .sounds(SoundType.NETHERRACK),
			()->drop,
			minCount,
			maxCount,
			experience,
			miningLevel
		);
	}
	
	@Override
	public void addTags(List<TagAPI.TagLocation<Block>> blockTags, List<TagAPI.TagLocation<Item>> itemTags) {
		blockTags.add(NamedCommonBlockTags.NETHERRACK);
	}
}
