package org.betterx.betternether.blocks.complex;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import org.betterx.bclib.complexmaterials.entry.BlockEntry;
import org.betterx.betternether.blocks.BlockWillowBranch;
import org.betterx.betternether.blocks.BlockWillowSapling;
import org.betterx.betternether.blocks.BlockWillowTorch;
import org.betterx.betternether.blocks.BlockWillowTrunk;

public class WillowMaterial extends RoofMaterial {
    public final static String BLOCK_TORCH = "torch";
    public final static String BLOCK_TRUNK = BLOCK_OPTIONAL_TRUNK;
    public final static String BLOCK_BRANCH = BLOCK_OPTIONAL_BRANCH;
    public final static String BLOCK_SAPLING = BLOCK_OPTIONAL_SAPLING;


    public WillowMaterial() {
        super("willow", MaterialColor.TERRACOTTA_RED, MaterialColor.TERRACOTTA_RED);
    }

    @Override
    public WillowMaterial init() {
        return (WillowMaterial) super.init();
    }

    @Override
    protected void initDefault(FabricBlockSettings blockSettings, FabricItemSettings itemSettings) {
        super.initDefault(blockSettings, itemSettings);

        addBlockEntry(new BlockEntry(BLOCK_TRUNK, false, (complexMaterial, settings) -> {
            return new BlockWillowTrunk();
        }));

        addBlockEntry(new BlockEntry(BLOCK_BRANCH, false, (complexMaterial, settings) -> {
            return new BlockWillowBranch();
        }));

        addBlockEntry(new BlockEntry(BLOCK_SAPLING, (complexMaterial, settings) -> {
            return new BlockWillowSapling();
        }));

        addBlockEntry(new BlockEntry(BLOCK_TORCH, (complexMaterial, settings) -> {
            return new BlockWillowTorch();
        }));
    }

    public Block getTrunk() {
        return getBlock(BLOCK_TRUNK);
    }

    public Block getBranch() {
        return getBlock(BLOCK_BRANCH);
    }

    public Block getSapling() {
        return getBlock(BLOCK_SAPLING);
    }

    public Block getTorch() {
        return getBlock(BLOCK_TORCH);
    }
}
