package org.betterx.betternether.blocks;

import org.betterx.bclib.blocks.BaseDoorBlock;
import org.betterx.bclib.interfaces.TagProvider;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

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
        blockTags.add(BlockTags.WOODEN_DOORS);
        itemTags.add(ItemTags.WOODEN_DOORS);
    }
}
