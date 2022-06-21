package org.betterx.betternether.blocks;

import org.betterx.bclib.interfaces.TagProvider;
import org.betterx.betternether.blocks.materials.Materials;
import org.betterx.worlds.together.tag.v3.CommonBlockTags;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;

import java.util.List;

public class BlockFarmland extends BlockBase implements TagProvider {
    public BlockFarmland() {
        super(Materials.makeWood(MaterialColor.TERRACOTTA_LIGHT_GREEN));
    }

    @Override
    public void addTags(List<TagKey<Block>> blockTags, List<TagKey<Item>> itemTags) {
        blockTags.add(CommonBlockTags.SOUL_GROUND);
        blockTags.add(CommonBlockTags.NETHERRACK);
    }
}
