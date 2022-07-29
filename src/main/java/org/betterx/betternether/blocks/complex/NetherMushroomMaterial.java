package org.betterx.betternether.blocks.complex;

import org.betterx.bclib.blocks.BaseLadderBlock;
import org.betterx.bclib.complexmaterials.entry.BlockEntry;
import org.betterx.bclib.complexmaterials.entry.RecipeEntry;
import org.betterx.bclib.recipes.GridRecipe;
import org.betterx.betternether.blocks.BlockStem;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class NetherMushroomMaterial extends NetherWoodenMaterial {
    public final static String BLOCK_STEM = BLOCK_OPTIONAL_STEM;

    public NetherMushroomMaterial() {
        super("nether_mushroom", MaterialColor.TERRACOTTA_WHITE, MaterialColor.COLOR_LIGHT_GRAY);
    }

    @Override
    public NetherMushroomMaterial init() {
        return (NetherMushroomMaterial) super.init();
    }

    @Override
    protected void initDefault(FabricBlockSettings blockSettings, FabricItemSettings itemSettings) {
        super.initDefault(blockSettings, itemSettings);

        addBlockEntry(new BlockEntry(BLOCK_STEM, (complexMaterial, settings) -> {
            return new BlockStem(MaterialColor.TERRACOTTA_WHITE);
        }));
    }

    @Override
    protected void _initBase(FabricBlockSettings blockSettings, FabricItemSettings itemSettings) {
        addBlockEntry(new BlockEntry(
                BLOCK_PLANKS,
                (complexMaterial, settings) -> new org.betterx.bclib.blocks.BaseBlock(settings)
        ).setBlockTags(BlockTags.PLANKS).setItemTags(ItemTags.PLANKS));

        addBlockEntry(new BlockEntry(
                BLOCK_STAIRS,
                (complexMaterial, settings) -> new org.betterx.bclib.blocks.BaseStairsBlock(
                        getBlock(BLOCK_PLANKS),
                        false
                )
        ).setBlockTags(BlockTags.WOODEN_STAIRS, BlockTags.STAIRS)
         .setItemTags(ItemTags.WOODEN_STAIRS, ItemTags.STAIRS));
        addBlockEntry(new BlockEntry(
                BLOCK_SLAB,
                (complexMaterial, settings) -> new org.betterx.bclib.blocks.BaseSlabBlock(getBlock(BLOCK_PLANKS), false)
        ).setBlockTags(BlockTags.WOODEN_SLABS, BlockTags.SLABS)
         .setItemTags(ItemTags.WOODEN_SLABS, ItemTags.SLABS));
        addBlockEntry(new BlockEntry(
                BLOCK_FENCE,
                (complexMaterial, settings) -> new org.betterx.bclib.blocks.BaseFenceBlock(getBlock(BLOCK_PLANKS))
        ).setBlockTags(BlockTags.FENCES, BlockTags.WOODEN_FENCES)
         .setItemTags(ItemTags.FENCES, ItemTags.WOODEN_FENCES));
        addBlockEntry(new BlockEntry(
                BLOCK_GATE,
                (complexMaterial, settings) -> new org.betterx.bclib.blocks.BaseGateBlock(getBlock(BLOCK_PLANKS))
        ).setBlockTags(BlockTags.FENCE_GATES));
        addBlockEntry(new BlockEntry(
                BLOCK_BUTTON,
                (complexMaterial, settings) -> new org.betterx.bclib.blocks.BaseWoodenButtonBlock(getBlock(BLOCK_PLANKS))
        ).setBlockTags(BlockTags.BUTTONS, BlockTags.WOODEN_BUTTONS)
         .setItemTags(ItemTags.BUTTONS, ItemTags.WOODEN_BUTTONS));
        addBlockEntry(new BlockEntry(
                BLOCK_PRESSURE_PLATE,
                (complexMaterial, settings) -> new org.betterx.bclib.blocks.WoodenPressurePlateBlock(getBlock(
                        BLOCK_PLANKS))
        ).setBlockTags(BlockTags.PRESSURE_PLATES, BlockTags.WOODEN_PRESSURE_PLATES)
         .setItemTags(ItemTags.WOODEN_PRESSURE_PLATES));
        addBlockEntry(new BlockEntry(
                BLOCK_TRAPDOOR,
                (complexMaterial, settings) -> new org.betterx.bclib.blocks.BaseTrapdoorBlock(getBlock(BLOCK_PLANKS))
        ).setBlockTags(BlockTags.TRAPDOORS, BlockTags.WOODEN_TRAPDOORS)
         .setItemTags(ItemTags.TRAPDOORS, ItemTags.WOODEN_TRAPDOORS));
        addBlockEntry(new BlockEntry(
                BLOCK_DOOR,
                (complexMaterial, settings) -> new org.betterx.bclib.blocks.BaseDoorBlock(getBlock(BLOCK_PLANKS))
        ).setBlockTags(BlockTags.DOORS, BlockTags.WOODEN_DOORS)
         .setItemTags(ItemTags.DOORS, ItemTags.WOODEN_DOORS));


        addBlockEntry(new BlockEntry(
                BLOCK_LADDER,
                (complexMaterial, settings) -> new BaseLadderBlock(getBlock(BLOCK_PLANKS))
        ).setBlockTags(BlockTags.CLIMBABLE));
        addBlockEntry(new BlockEntry(
                BLOCK_SIGN,
                (complexMaterial, settings) -> new org.betterx.bclib.blocks.BaseSignBlock(getBlock(BLOCK_PLANKS))
        ).setBlockTags(BlockTags.SIGNS).setItemTags(ItemTags.SIGNS));
    }

    @Override
    public void initDefaultRecipes() {
        Block planks = getBlock(BLOCK_PLANKS);
        addRecipeEntry(new RecipeEntry(BLOCK_PLANKS, (material, config, id) -> {
            Block log = getBlock(BLOCK_STEM);

            GridRecipe.make(id, planks)
                      .checkConfig(config)
                      .setOutputCount(4)
                      .setList("#")
                      .addMaterial('#', log)
                      .setGroup(receipGroupPrefix + "_planks")
                      .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_STAIRS, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_STAIRS))
                      .checkConfig(config)
                      .setOutputCount(4)
                      .setShape("#  ", "## ", "###")
                      .addMaterial('#', planks)
                      .setGroup(receipGroupPrefix + "_planks_stairs")
                      .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_SLAB, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_SLAB))
                      .checkConfig(config)
                      .setOutputCount(6)
                      .setShape("###")
                      .addMaterial('#', planks)
                      .setGroup(receipGroupPrefix + "_planks_slabs")
                      .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_FENCE, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_FENCE))
                      .checkConfig(config)
                      .setOutputCount(3)
                      .setShape("#I#", "#I#")
                      .addMaterial('#', planks)
                      .addMaterial('I', Items.STICK)
                      .setGroup(receipGroupPrefix + "_planks_fences")
                      .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_GATE, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_GATE))
                      .checkConfig(config)
                      .setShape("I#I", "I#I")
                      .addMaterial('#', planks)
                      .addMaterial('I', Items.STICK)
                      .setGroup(receipGroupPrefix + "_planks_gates")
                      .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_BUTTON, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_BUTTON))
                      .checkConfig(config)
                      .setList("#")
                      .addMaterial('#', planks)
                      .setGroup(receipGroupPrefix + "_planks_buttons")
                      .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_PRESSURE_PLATE, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_PRESSURE_PLATE))
                      .checkConfig(config)
                      .setShape("##")
                      .addMaterial('#', planks)
                      .setGroup(receipGroupPrefix + "_planks_plates")
                      .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_TRAPDOOR, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_TRAPDOOR))
                      .checkConfig(config)
                      .setOutputCount(2)
                      .setShape("###", "###")
                      .addMaterial('#', planks)
                      .setGroup(receipGroupPrefix + "_trapdoors")
                      .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_DOOR, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_DOOR))
                      .checkConfig(config)
                      .setOutputCount(3)
                      .setShape("##", "##", "##")
                      .addMaterial('#', planks)
                      .setGroup(receipGroupPrefix + "_doors")
                      .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_CRAFTING_TABLE, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_CRAFTING_TABLE))
                      .checkConfig(config)
                      .setShape("##", "##")
                      .addMaterial('#', planks)
                      .setGroup(receipGroupPrefix + "_tables")
                      .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_LADDER, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_LADDER))
                      .checkConfig(config)
                      .setOutputCount(3)
                      .setShape("I I", "I#I", "I I")
                      .addMaterial('#', planks)
                      .addMaterial('I', Items.STICK)
                      .setGroup(receipGroupPrefix + "_ladders")
                      .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_SIGN, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_SIGN))
                      .checkConfig(config)
                      .setOutputCount(3)
                      .setShape("###", "###", " I ")
                      .addMaterial('#', planks)
                      .addMaterial('I', Items.STICK)
                      .setGroup(receipGroupPrefix + "_signs")
                      .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_CHEST, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_CHEST))
                      .checkConfig(config)
                      .setShape("###", "# #", "###")
                      .addMaterial('#', planks)
                      .setGroup(receipGroupPrefix + "_chests")
                      .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_BARREL, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_BARREL))
                      .checkConfig(config)
                      .setShape("#S#", "# #", "#S#")
                      .addMaterial('#', planks)
                      .addMaterial('S', getBlock(BLOCK_SLAB))
                      .setGroup(receipGroupPrefix + "_barrels")
                      .build();
        }));

        initDefaultFurniture();
    }

    public Block getStem() {
        return getBlock(BLOCK_STEM);
    }
}
