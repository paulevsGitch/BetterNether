package org.betterx.betternether.interfaces;

import org.betterx.bclib.interfaces.SurvivesOnTags;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.List;

public interface SurvivesOnNylium extends SurvivesOnTags {
    List<TagKey<Block>> TAGS = List.of(BlockTags.NYLIUM);

    @Override
    default List<TagKey<Block>> getSurvivableTags() {
        return TAGS;
    }
}
