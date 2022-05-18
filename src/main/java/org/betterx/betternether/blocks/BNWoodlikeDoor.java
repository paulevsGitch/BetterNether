package org.betterx.betternether.blocks;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import org.betterx.bclib.api.tag.NamedBlockTags;
import org.betterx.bclib.api.tag.NamedItemTags;
import org.betterx.bclib.blocks.BaseDoorBlock;
import org.betterx.bclib.interfaces.TagProvider;

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
