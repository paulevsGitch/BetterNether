package org.betterx.betternether.interfaces;

import org.betterx.bclib.interfaces.SurvivesOnTags;
import org.betterx.betternether.registry.NetherTags;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.List;

public interface SurvivesOnSoulGroundOrFarmLand extends SurvivesOnTags {
    List<TagKey<Block>> TAGS = List.of(
            org.betterx.worlds.together.tag.v3.CommonBlockTags.SOUL_GROUND,
            NetherTags.NETHER_FARMLAND
    );

    @Override
    default List<TagKey<Block>> getSurvivableTags() {
        return TAGS;
    }
}
