package paulevs.betternether;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class NetherTags
{
	public static final Tag<Block> SOUL_GROUND_BLOCK = getOrCreateTagBlock("soul_ground");
	public static final Tag<Block> NETHERRACK = getOrCreateTagBlock("netherrack");
	public static final Tag<Block> MYCELIUM = getOrCreateTagBlock("nether_mycelium");
	public static final Tag<Block> NYLIUM = BlockTags.NYLIUM;
	
	public static final Tag<Item> SOUL_GROUND_ITEM = getOrCreateTagItem("soul_ground");
	
	private static Tag<Block> getOrCreateTagBlock(String name)
	{
		Identifier id = new Identifier("common", name);
		Tag<Block> tag = BlockTags.getContainer().get(id);
		return tag != null ? tag : TagRegistry.block(id);
	}
	
	private static Tag<Item> getOrCreateTagItem(String name)
	{
		Identifier id = new Identifier("common", name);
		Tag<Item> tag = ItemTags.getContainer().get(id);
		return tag != null ? tag : TagRegistry.item(id);
	}

	public static void register() {}
}