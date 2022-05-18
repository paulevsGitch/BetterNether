package paulevs.betternether.blocks;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import ru.bclib.api.tag.NamedBlockTags;
import ru.bclib.api.tag.NamedItemTags;
import ru.bclib.api.tag.TagAPI;
import ru.bclib.blocks.BaseDoorBlock;
import ru.bclib.interfaces.TagProvider;

import java.util.List;

public class BNWoodlikeDoor extends BaseDoorBlock implements TagProvider {
	
	public BNWoodlikeDoor(Block source) {
		super(source);
	}
	
	public BNWoodlikeDoor(Properties properties) {
		super(properties);
	}
	
	@Override
	public void addTags(List<TagKey<Block>> blockTags, List<TagKey<Item>> itemTags) {
		blockTags.add(NamedBlockTags.WOODEN_DOORS);
		itemTags.add(NamedItemTags.WOODEN_DOORS);
	}
}
