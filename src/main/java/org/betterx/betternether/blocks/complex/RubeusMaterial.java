package org.betterx.betternether.blocks.complex;

import org.betterx.bclib.complexmaterials.entry.BlockEntry;
import org.betterx.betternether.blocks.BlockRubeusCone;
import org.betterx.betternether.blocks.BlockRubeusSapling;
import org.betterx.betternether.blocks.RubeusBark;
import org.betterx.betternether.blocks.RubeusLog;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class RubeusMaterial extends NetherWoodenMaterial {
    public final static String BLOCK_SAPLING = BLOCK_OPTIONAL_SAPLING;
    public final static String BLOCK_CONE = "cone";

    public RubeusMaterial() {
        super("rubeus", MaterialColor.COLOR_MAGENTA, MaterialColor.COLOR_MAGENTA);
    }

    @Override
    public RubeusMaterial init() {
        return (RubeusMaterial) super.init();
    }

    @Override
    protected void initDefault(FabricBlockSettings blockSettings, FabricItemSettings itemSettings) {
        super.initDefault(blockSettings, itemSettings);
        final TagKey<Block> tagBlockLog = getBlockTag(TAG_LOGS);
        final TagKey<Item> tagItemLog = getItemTag(TAG_LOGS);

        addBlockEntry(new BlockEntry(BLOCK_SAPLING, (complexMaterial, settings) -> new BlockRubeusSapling()));

        addBlockEntry(new BlockEntry(BLOCK_CONE, (complexMaterial, settings) -> new BlockRubeusCone()));

        replaceOrAddBlockEntry(
                new BlockEntry(BLOCK_LOG, (complexMaterial, settings) -> new RubeusLog(woodColor, getStrippedLog()))
                        .setBlockTags(BlockTags.LOGS, BlockTags.LOGS_THAT_BURN, tagBlockLog)
                        .setItemTags(ItemTags.LOGS, ItemTags.LOGS_THAT_BURN, tagItemLog)
        );

        replaceOrAddBlockEntry(
                new BlockEntry(BLOCK_BARK, (complexMaterial, settings) -> new RubeusBark(woodColor, getStrippedBark()))
                        .setBlockTags(BlockTags.LOGS, BlockTags.LOGS_THAT_BURN, tagBlockLog)
                        .setItemTags(ItemTags.LOGS, ItemTags.LOGS_THAT_BURN, tagItemLog)
        );
    }

    public Block getCone() {
        return getBlock(BLOCK_CONE);
    }

    public Block getSapling() {
        return getBlock(BLOCK_SAPLING);
    }
}
