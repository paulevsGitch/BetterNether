package org.betterx.betternether.blocks;

import org.betterx.bclib.blocks.BaseOreBlock;
import org.betterx.bclib.interfaces.CustomItemProvider;
import org.betterx.bclib.interfaces.TagProvider;
import org.betterx.worlds.together.tag.v3.CommonBlockTags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import java.util.List;

public class BlockOre extends BaseOreBlock implements TagProvider, CustomItemProvider {
    public final boolean fireproof;

    public BlockOre(Item drop, int minCount, int maxCount, int experience, int miningLevel, boolean fireproof) {
        super(
                FabricBlockSettings.of(Material.STONE, MaterialColor.COLOR_RED)
                                   .hardness(3F)
                                   .resistance(5F)
                                   .requiresTool()
                                   .sounds(SoundType.NETHERRACK),
                () -> drop,
                minCount,
                maxCount,
                experience,
                miningLevel
        );
        this.fireproof = fireproof;
    }

    @Override
    public void addTags(List<TagKey<Block>> blockTags, List<TagKey<Item>> itemTags) {
        super.addTags(blockTags, itemTags);
        blockTags.add(CommonBlockTags.NETHERRACK);
        blockTags.add(CommonBlockTags.NETHER_ORES);
    }

    @Override
    public BlockItem getCustomItem(ResourceLocation blockID, Item.Properties settings) {
        if (fireproof) settings = settings.fireResistant();
        return new BlockItem(this, settings);
    }
}
