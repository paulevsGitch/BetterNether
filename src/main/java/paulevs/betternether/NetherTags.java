package paulevs.betternether;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class NetherTags
{
	public static final Tag<Block> SOUL_GROUND = getOrCreateTag("soul_ground");
	public static final Tag<Block> NETHERRACK = getOrCreateTag("netherrack");
	public static final Tag<Block> MYCELIUM = getOrCreateTag("nether_mycelium");
	public static final Tag<Block> NYLIUM = BlockTags.NYLIUM;
	
	private static Tag<Block> getOrCreateTag(String name)
	{
		Identifier id = new Identifier("common", name);
		Tag<Block> tag = BlockTags.getContainer().get(id);
		return tag != null ? tag : TagRegistry.block(id);
	}

	public static void register() {}
}