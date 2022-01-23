package paulevs.betternether.blocks;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.blocks.materials.Materials;
import ru.bclib.api.tag.NamedCommonBlockTags;
import ru.bclib.api.tag.TagAPI;
import ru.bclib.interfaces.TagProvider;

import java.util.List;

public class BlockFarmland extends BlockBase implements TagProvider {
	public BlockFarmland() {
		super(Materials.makeWood(MaterialColor.TERRACOTTA_LIGHT_GREEN));
	}
	
	@Override
	public void addTags(List<TagAPI.TagLocation<Block>> blockTags, List<TagAPI.TagLocation<Item>> itemTags) {
		blockTags.add(NamedCommonBlockTags.SOUL_GROUND);
		blockTags.add(NamedCommonBlockTags.NETHERRACK);

	}
}
