package org.betterx.betternether.blocks.complex;

import org.betterx.bclib.complexmaterials.entry.BlockEntry;
import org.betterx.betternether.blocks.BlockNetherSakuraSapling;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MaterialColor;

public class NetherSakuraMaterial extends NetherWoodenMaterial {
    public final static String BLOCK_SAPLING = BLOCK_OPTIONAL_SAPLING;

    public NetherSakuraMaterial() {
        super("nether_sakura", MaterialColor.COLOR_PINK, MaterialColor.COLOR_BROWN);
    }

    @Override
    public NetherSakuraMaterial init() {
        return (NetherSakuraMaterial) super.init();
    }

    @Override
    protected void initDefault(BlockBehaviour.Properties blockSettings, Item.Properties itemSettings) {
        super.initDefault(blockSettings, itemSettings);

        addBlockEntry(new BlockEntry(BLOCK_SAPLING, (complexMaterial, settings) -> {
            return new BlockNetherSakuraSapling();
        }));
    }

    public Block getSapling() {
        return getBlock(BLOCK_SAPLING);
    }

    public boolean isTreeLog(Block block) {
        return block == getLog() || block == getBark();
    }
}
