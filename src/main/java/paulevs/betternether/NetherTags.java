package paulevs.betternether;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class NetherTags {
	public static final Tag<Block> SOUL_GROUND_BLOCK = getOrCreateTagBlock("soul_ground");
	public static final Tag<Block> NETHERRACK = getOrCreateTagBlock("netherrack");
	public static final Tag<Block> MYCELIUM = getOrCreateTagBlock("nether_mycelium");
	public static final Tag<Block> NYLIUM = BlockTags.NYLIUM;

	public static final Tag<Item> SOUL_GROUND_ITEM = getOrCreateTagItem("soul_ground");

	private static Tag<Block> getOrCreateTagBlock(String name) {
		ResourceLocation id = new ResourceLocation("c", name);
		Tag<Block> tag = BlockTags.getAllTags().getTag(id);
		return tag != null ? tag : TagRegistry.block(id);
	}

	private static Tag<Item> getOrCreateTagItem(String name) {
		ResourceLocation id = new ResourceLocation("c", name);
		Tag<Item> tag = ItemTags.getAllTags().getTag(id);
		return tag != null ? tag : TagRegistry.item(id);
	}

	public static void register() {}
}