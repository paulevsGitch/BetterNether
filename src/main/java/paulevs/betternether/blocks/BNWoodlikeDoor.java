package paulevs.betternether.blocks;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag.Named;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import ru.bclib.blocks.BaseDoorBlock;

import java.util.List;

public class BNWoodlikeDoor extends BaseDoorBlock {
	
	public BNWoodlikeDoor(Block source) {
		super(source);
	}
	
	public BNWoodlikeDoor(Properties properties) {
		super(properties);
	}
	
	@Override
	public void addTags(List<Named<Block>> blockTags, List<Named<Item>> itemTags) {
		//TODO: Remove after BCLib 1.1.4
		blockTags.add(BlockTags.DOORS);
		itemTags.add(ItemTags.DOORS);
		
		blockTags.add(BlockTags.WOODEN_DOORS);
		itemTags.add(ItemTags.WOODEN_DOORS);
	}
}
