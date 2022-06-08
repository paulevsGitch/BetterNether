package org.betterx.betternether.blocks.complex;

import org.betterx.bclib.blocks.BaseBlock;
import org.betterx.bclib.blocks.BaseSlabBlock;
import org.betterx.bclib.blocks.BaseStairsBlock;
import org.betterx.bclib.complexmaterials.entry.BlockEntry;
import org.betterx.bclib.complexmaterials.entry.RecipeEntry;
import org.betterx.bclib.recipes.GridRecipe;

import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class RoofMaterial extends NetherWoodenMaterial {
    public final static String BLOCK_ROOF = BLOCK_OPTIONAL_ROOF;
    public final static String BLOCK_ROOF_STAIRS = BLOCK_OPTIONAL_ROOF_STAIRS;
    public final static String BLOCK_ROOF_SLAB = BLOCK_OPTIONAL_ROOF_SLAB;

    public RoofMaterial(String name, MaterialColor woodColor, MaterialColor planksColor) {
        super(name, woodColor, planksColor);
    }

    @Override
    public RoofMaterial init() {
        return (RoofMaterial) super.init();
    }

    @Override
    protected void initDefault(FabricBlockSettings blockSettings, FabricItemSettings itemSettings) {
        super.initDefault(blockSettings, itemSettings);

        addBlockEntry(new BlockEntry(BLOCK_ROOF, (complexMaterial, settings) -> {
            return new BaseBlock(FabricBlockSettings.copyOf(getBlock(BLOCK_PLANKS)));
        }));
        addBlockEntry(new BlockEntry(BLOCK_ROOF_STAIRS, (complexMaterial, settings) -> {
            return new BaseStairsBlock(getBlock(BLOCK_ROOF), false);
        }));
        addBlockEntry(new BlockEntry(BLOCK_ROOF_SLAB, (complexMaterial, settings) -> {
            return new BaseSlabBlock(getBlock(BLOCK_ROOF), false);
        }));
    }

    @Override
    public void initDefaultRecipes() {
        super.initDefaultRecipes();
        initRoofRecipes();
    }

    public void initRoofRecipes() {
        final Block planks = getBlock(BLOCK_PLANKS);
        final Block slab = getBlock(BLOCK_SLAB);
        final Block roof = getBlock(BLOCK_ROOF);

        if (Registry.BLOCK.getKey(slab) != Registry.BLOCK.getDefaultKey()) {
            addRecipeEntry(new RecipeEntry(BLOCK_ROOF, (material, config, id) -> {
                GridRecipe.make(id, getBlock(BLOCK_ROOF))
                          .checkConfig(config)
                          .setOutputCount(4)
                          .setShape("# #", "###", " # ")
                          .addMaterial('#', planks)
                          .setGroup(receipGroupPrefix + "_planks_roof")
                          .build();
            }));

            addRecipeEntry(new RecipeEntry(BLOCK_ROOF_STAIRS, (material, config, id) -> {
                GridRecipe.make(id, getBlock(BLOCK_ROOF_STAIRS))
                          .checkConfig(config)
                          .setOutputCount(4)
                          .setShape("#  ", "## ", "###")
                          .addMaterial('#', roof)
                          .setGroup(receipGroupPrefix + "_planks_roof_stairs")
                          .build();
            }));

            addRecipeEntry(new RecipeEntry(BLOCK_ROOF_SLAB, (material, config, id) -> {
                GridRecipe.make(id, getBlock(BLOCK_ROOF_SLAB))
                          .checkConfig(config)
                          .setOutputCount(6)
                          .setShape("###")
                          .addMaterial('#', roof)
                          .setGroup(receipGroupPrefix + "_planks_roof_slabs")
                          .build();
            }));
        }
    }
}
