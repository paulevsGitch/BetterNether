package org.betterx.betternether.blocks.complex;

import org.betterx.bclib.complexmaterials.entry.BlockEntry;
import org.betterx.betternether.blocks.BlockAnchorTreeSapling;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MaterialColor;

public class AnchorTreeMaterial extends NetherWoodenMaterial {
    public final static String BLOCK_SAPLING = BLOCK_OPTIONAL_SAPLING;

    public AnchorTreeMaterial() {
        super("anchor_tree", MaterialColor.COLOR_BLUE, MaterialColor.COLOR_GREEN);
    }

    @Override
    public AnchorTreeMaterial init() {
        return (AnchorTreeMaterial) super.init();
    }

    @Override
    protected void initDefault(BlockBehaviour.Properties blockSettings, Item.Properties itemSettings) {
        super.initDefault(blockSettings, itemSettings);

        addBlockEntry(new BlockEntry(BLOCK_SAPLING, (complexMaterial, settings) -> new BlockAnchorTreeSapling()));
    }

    public Block getSapling() {
        return getBlock(BLOCK_SAPLING);
    }

    public boolean isTreeLog(Block block) {
        return block == getLog() || block == getBark();
    }
}
