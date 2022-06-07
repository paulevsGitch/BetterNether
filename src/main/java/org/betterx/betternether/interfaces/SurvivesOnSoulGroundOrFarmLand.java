package org.betterx.betternether.interfaces;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import org.betterx.bclib.api.v2.tag.CommonBlockTags;
import org.betterx.bclib.interfaces.SurvivesOnTags;
import org.betterx.betternether.registry.NetherTags;

import java.util.List;

public interface SurvivesOnSoulGroundOrFarmLand extends SurvivesOnTags {
    List<TagKey<Block>> TAGS = List.of(CommonBlockTags.SOUL_GROUND, NetherTags.NETHER_FARMLAND);

    @Override
    default List<TagKey<Block>> getSurvivableTags() {
        return TAGS;
    }
}
