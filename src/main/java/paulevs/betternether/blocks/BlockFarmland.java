package paulevs.betternether.blocks;

import net.minecraft.tags.Tag.Named;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.blocks.materials.Materials;
import ru.bclib.interfaces.TagProvider;

import java.util.List;

public class BlockFarmland extends BlockBase implements TagProvider {
	public BlockFarmland() {
		super(Materials.makeWood(MaterialColor.TERRACOTTA_LIGHT_GREEN));
	}
	
	@Override
	public void addTags(List<Named<Block>> blockTags, List<Named<Item>> itemTags) {
		//TODO: enable after BCLIb 1.1.4
		//blockTags.add(TagAPI.BLOCK_SOUL_GROUND);
	}
}
