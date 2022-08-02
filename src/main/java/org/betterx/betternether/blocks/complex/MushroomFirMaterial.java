package org.betterx.betternether.blocks.complex;

import org.betterx.bclib.complexmaterials.entry.BlockEntry;
import org.betterx.bclib.complexmaterials.entry.RecipeEntry;
import org.betterx.bclib.recipes.BCLRecipeBuilder;
import org.betterx.betternether.blocks.BlockMushroomFir;
import org.betterx.betternether.blocks.BlockMushroomFirSapling;
import org.betterx.betternether.blocks.BlockStem;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MaterialColor;

public class MushroomFirMaterial extends NetherWoodenMaterial {
    public final static String BLOCK_SAPLING = BLOCK_OPTIONAL_SAPLING;
    public final static String BLOCK_STEM = BLOCK_OPTIONAL_STEM;
    public final static String BLOCK_TRUNK = BLOCK_OPTIONAL_TRUNK;

    public MushroomFirMaterial() {
        super("mushroom_fir", MaterialColor.COLOR_BLUE, MaterialColor.COLOR_BLUE);
    }

    @Override
    public MushroomFirMaterial init() {
        return (MushroomFirMaterial) super.init();
    }

    @Override
    protected void initDefault(BlockBehaviour.Properties blockSettings, Item.Properties itemSettings) {
        super.initDefault(blockSettings, itemSettings);
        addBlockEntry(new BlockEntry(BLOCK_TRUNK, false, (complexMaterial, settings) -> {
            return new BlockMushroomFir();
        }));

        addBlockEntry(new BlockEntry(BLOCK_SAPLING, (complexMaterial, settings) -> {
            return new BlockMushroomFirSapling();
        }));

        addBlockEntry(new BlockEntry(BLOCK_STEM, (complexMaterial, settings) -> {
            return new BlockStem(MaterialColor.TERRACOTTA_BLACK);
        }));
    }

    @Override
    public void initDefaultRecipes() {
        super.initDefaultRecipes();
        addRecipeEntry(new RecipeEntry(BLOCK_LOG, (material, config, id) -> {
            final Block log = getBlock(BLOCK_LOG);
            final Block stem = getBlock(BLOCK_STEM);

            BCLRecipeBuilder.crafting(id, log)
                            .checkConfig(config)
                            .setOutputCount(1)
                            .setShape("##", "##")
                            .addMaterial('#', stem)
                            .setGroup(receipGroupPrefix + "_planks")
                            .build();
        }));

        initDefaultFurniture();
    }

    public Block getStem() {
        return getBlock(BLOCK_STEM);
    }

    public Block getSapling() {
        return getBlock(BLOCK_SAPLING);
    }

    public Block getTrunk() {
        return getBlock(BLOCK_TRUNK);
    }
}