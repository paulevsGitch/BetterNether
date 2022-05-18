package org.betterx.betternether.interfaces;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import org.betterx.bclib.interfaces.SurvivesOnTags;
import org.betterx.betternether.registry.NetherTags;

import java.util.List;

public interface SurvivesOnNetherSand extends SurvivesOnTags {
    List<TagKey<Block>> TAGS = List.of(NetherTags.NETHER_SAND);

    @Override
    default List<TagKey<Block>> getSurvivableTags() {
        return TAGS;
    }
}
